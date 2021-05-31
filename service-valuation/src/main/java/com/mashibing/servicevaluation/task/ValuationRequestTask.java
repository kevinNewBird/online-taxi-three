package com.mashibing.servicevaluation.task;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.map.Distance;
import com.mashibing.internalcommon.dto.map.Route;
import com.mashibing.internalcommon.dto.servicevaluation.charging.Rule;
import com.mashibing.internalcommon.entity.serviceorder.OrderRuleMirror;
import com.mashibing.internalcommon.entity.serviceorder.OrderRulePriceDetail;
import com.mashibing.internalcommon.util.RestTemplateHelper;
import com.mashibing.internalcommon.util.UnitConverter;
import com.mashibing.servicevaluation.config.ServiceAddress;
import com.mashibing.servicevaluation.dao.OrderRuleMirrorMapper;
import com.mashibing.servicevaluation.dto.DriveMeter;
import com.mashibing.servicevaluation.util.RuleCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * description  估价请求任务 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 20:47
 **/
@Component
@Slf4j
public class ValuationRequestTask {

    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    @Autowired
    private RuleCache ruleCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServiceAddress serviceAddress;

    @SneakyThrows
    public Rule requestRule(Integer orderId) {
        Rule rule;
        try {
            rule = ruleCache.get(orderId);
            if (!Objects.isNull(rule)) {
                return rule;
            }
            OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper.selectByOrderId(orderId);
            String ruleJson = orderRuleMirror.getRule();
            log.info("orderId={},RuleJson={}", orderId, ruleJson);

            rule = RestTemplateHelper.parse(ruleJson, Rule.class);
            ruleCache.set(orderId, rule);
        } catch (Exception ex) {
            log.error("orderId={},解析RuleJson错误:", orderId, ex);
            throw ex;
        }
        return rule;
    }


    /**
     * 预估路线:没行驶前
     *
     * @param driveMeter
     * @return
     */
    @SneakyThrows
    public Route requestRoute(DriveMeter driveMeter) {
        Route route;
        try {
            Map<String, Object> map = new HashMap<>(4);
            map.put("originLongitude", driveMeter.getOrder().getStartLongitude());
            map.put("originLatitude", driveMeter.getOrder().getStartLatitude());
            map.put("destinationLongitude", driveMeter.getOrder().getEndLongitude());
            map.put("destinationLatitude", driveMeter.getOrder().getEndLatitude());
            log.info("orderId={}, Request Route={}", driveMeter.getOrder().getId(), map);

            String param = map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.joining("&"));
            String url = serviceAddress.getMapAddress() + "/distance?" + param;
            ResponseResult result = restTemplate.getForObject(url, ResponseResult.class, map);
            log.info("调用接口Route返回{}", result);
            route = RestTemplateHelper.parse(result, Route.class);

            if (null == route.getDuration() || null == route.getDistance()) {
                throw new Exception("Route内容为空：" + route);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("orderId={}, 调用接口Route错误:driveMeter={}", driveMeter.getOrder().getId(), driveMeter, e);
            throw e;
        }

        return route;
    }

    /**
     * 实际行驶轨迹路线
     *
     * @param driveMeter
     * @return
     */
    public Distance requestDistance(DriveMeter driveMeter) {
        try {
            int carId = driveMeter.getOrder().getCarId();
            String cityCode = driveMeter.getRule().getKeyRule().getCityCode();
            LocalDateTime startTime = UnitConverter.dateToLocalDateTime(driveMeter.getOrder().getReceivePassengerTime());
            LocalDateTime endTime = UnitConverter.dateToLocalDateTime(driveMeter.getOrder().getPassengerGetOffTime());
            log.info("orderId={}", driveMeter.getOrder().getId());

            return requestDistance(carId, cityCode, startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("orderId={}, 调用接口Distance错误:driveMeter={}", driveMeter.getOrder().getId(), driveMeter, e);
            throw e;
        }
    }

    /**
     * 获取路途长度
     *
     * @param carId     车辆ID
     * @param cityCode  城市编码
     * @param startTime 开始时间点
     * @param endTime   结束时间点
     * @return 距离
     */
    @SneakyThrows
    public Distance requestDistance(int carId, String cityCode, LocalDateTime startTime, LocalDateTime endTime) {
        Map<String, Object> map = new HashMap<>(4);

        Distance result = new Distance();
        result.setDistance(0D);

        try {
            //按天分割计算
            long totalSeconds = Duration.between(startTime, endTime).toMillis();
            long intervalSeconds = Duration.ofDays(1).minusSeconds(1).toMillis();

            //计算次数
            int times = (int) Math.ceil(1.0 * totalSeconds / intervalSeconds);
            for (int i = 0; i < times; i++) {
                long startSecond = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + (i * intervalSeconds);
                long endSecond = Math.min(endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), startSecond + intervalSeconds);

                map.clear();
                map.put("vehicleId", carId);
                map.put("city", cityCode);
                map.put("startTime", startSecond);
                map.put("endTime", endSecond);
                log.info("Request Distance={}", map);

                String param = map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.joining("&"));
                String url = serviceAddress.getMapAddress() + "/route/distance?" + param;
                ResponseResult responseResult = restTemplate.getForObject(url, ResponseResult.class, map);
                Distance distance = RestTemplateHelper.parse(responseResult, Distance.class);

                if (null == distance || null == distance.getDistance()) {
                    throw new Exception("distance内容为空：" + result);
                }

                result.setDistance(result.getDistance() + distance.getDistance());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调用接口Route Distance错误:carId={},cityCode={},startTime={},endTime={}", carId, cityCode, startTime, endTime, e);
            throw e;
        }

        return result;
    }

}
