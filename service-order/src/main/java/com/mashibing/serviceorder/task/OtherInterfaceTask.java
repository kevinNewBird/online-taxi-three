package com.mashibing.serviceorder.task;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DistanceEnum;
import com.mashibing.internalcommon.constant.OrderEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.map.Route;
import com.mashibing.internalcommon.dto.map.request.DistanceRequest;
import com.mashibing.internalcommon.dto.serviceorder.request.OrderDtoRequest;
import com.mashibing.internalcommon.dto.servicevaluation.PriceResult;
import com.mashibing.internalcommon.dto.servicevaluation.charging.*;
import com.mashibing.internalcommon.entity.serviceorder.ChargeRule;
import com.mashibing.internalcommon.entity.serviceorder.ChargeRuleDetail;
import com.mashibing.internalcommon.util.RestTemplateHelper;
import com.mashibing.serviceorder.config.ServicesConfig;
import com.mashibing.serviceorder.dao.ChargeRuleDetailMapper;
import com.mashibing.serviceorder.dao.ChargeRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***********************
 * @Description: 其它接口任务 <BR>
 * @author: zhao.song
 * @since: 2021/5/26 8:34
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Component
@Slf4j
public class OtherInterfaceTask {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ChargeRuleMapper chargeRuleMapper;

    @Autowired
    private ChargeRuleDetailMapper chargeRuleDetailMapper;

    @Autowired
    private ServicesConfig servicesConfig;


    public static void main(String[] args) {
        DistanceRequest distanceRequest = DistanceRequest.builder()
                .originLongitude("1")
                .originLatitude("2")
                .destinationLongitude("3")
                .destinationLatitude("4")
                .build();
        Map<String, Object> param = object2Map(distanceRequest);
        System.out.println(param);
        String paramLineStr = param.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.joining("&"));
        System.out.println(paramLineStr);
        String paramLine2Str = String.join("&", param.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        System.out.println(paramLine2Str);

    }

    /**
     * Description: 获取路途长度和形式时间 <BR>
     *
     * @param orderDtoRequest:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/26 11:36
     */
    public ResponseResult requestRoute(OrderDtoRequest orderDtoRequest) throws Exception {
        // 0.构造Rest风格的请求数据
        DistanceRequest distanceRequest = DistanceRequest.builder()
                .originLongitude(orderDtoRequest.getStartLongitude())
                .originLatitude(orderDtoRequest.getStartLatitude())
                .destinationLongitude(orderDtoRequest.getEndLongitude())
                .destinationLatitude(orderDtoRequest.getEndLatitude())
                .build();
        Map<String, Object> paramRequestMap = object2Map(distanceRequest);
        String paramLineStr = paramRequestMap.keySet().stream()
                .map(k -> k.concat("{").concat(k))
                .collect(Collectors.joining("&"));

        // 1.请求,并解析返回结果
        try {
            // TODO 切换为feign
            ResponseResult responseResult = restTemplate.getForObject("".concat(paramLineStr), ResponseResult.class, paramRequestMap);
            if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                throw new RuntimeException("请求第三方测距错误,".concat(responseResult.getMessage()));
            }
            // 1.1.解析从第三方请求回来的数据 -> Route.class
            Route route = RestTemplateHelper.parse(responseResult, Route.class);
            if (route.getDistance() == null || route.getDuration() == null) {
                return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), "测试距离失败");
            }

            // 1.2. 距离的判断
            if (route.getDistance() <= 0) {
                return ResponseResult.fail(OrderEnum.TOO_CLOSE.getCode(), OrderEnum.TOO_CLOSE.getValue());
            }
            if (route.getDistance() > DistanceEnum.DISTANCE.getCode()) {
                return ResponseResult.fail(OrderEnum.TOO_FAR_AWAY.getCode(), OrderEnum.TOO_FAR_AWAY.getValue());
            }
            // 1.3. 时间判断
            if (route.getDuration() >= DistanceEnum.DURATION.getCode()) {
                return ResponseResult.fail(OrderEnum.TOO_FAR_AWAY.getCode(), "时间不能超过24小时");
            }
            return ResponseResult.success(route);
        } catch (RestClientException e) {
            log.error("调用接口Distance错误!", e);
            return ResponseResult.fail("测量距离失败");
        }
    }

    /**
     * Description: 转换pojo对象为map <BR>
     *
     * @param obj:
     * @return: {@link Map< String, Object>}
     * @author: zhao.song   2021/5/26 10:09
     */
    public static Map<String, Object> object2Map(Object obj) {
        Objects.requireNonNull(obj, "转换对象不可为空!");
        return JSON.parseObject(JSON.toJSONString(obj), Map.class);
    }


    /**
     * description: 计价规则 <BR>
     *
     * @param orderDtoRequest:
     * @return {@link ResponseResult}
     * @author zhao.song   2021/5/27 21:45
     */
    public ResponseResult getOrderChargeRule(OrderDtoRequest orderDtoRequest) {
        Rule rule = Rule.builder().build();
        // 1.获取数据库的计价规则表数据
        List<ChargeRule> chargeRuleList = chargeRuleMapper.selectByKeyRule(
                orderDtoRequest.getCityCode()
                , orderDtoRequest.getServiceTypeId()
                , orderDtoRequest.getChannelId()
                , orderDtoRequest.getCarLevelId());
        if (chargeRuleList.size() == 0 || chargeRuleList.size() > 1) {
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), "无计价规则");
        }
        ChargeRule chargeRule = chargeRuleList.get(0);
        // 2.计价规则封装
        rule.setId(chargeRule.getId());
        // 2.1. 计价规则主键封装
        KeyRule keyRule = KeyRule.builder()
                .cityCode(orderDtoRequest.getCityCode())
                .cityName(orderDtoRequest.getCityName())
                .serviceTypeId(orderDtoRequest.getServiceTypeId())
                .serviceTypeName(orderDtoRequest.getServiceTypeName())
                .channelName(orderDtoRequest.getChannelName())
                .channelId(orderDtoRequest.getChannelId())
                .carLevelId(orderDtoRequest.getCarLevelId())
                .carLevelName(orderDtoRequest.getCarLevelName())
                .build();
        rule.setKeyRule(keyRule);
        // 2.2. 基础计费规则封装
        BasicRule basicRule = BasicRule.builder()
                .lowestPrice(chargeRule.getLowestPrice())
                .basePrice(chargeRule.getBasePrice())
                .build();
        rule.setBasicRule(basicRule);

        // 2.3.计费规则封装
        List<TimeRule> timeRuleList = new ArrayList<>();
        PriceRule priceRule = PriceRule.builder()
                .perKiloPrice(chargeRule.getPerKiloPrice())
                .perMinutePrice(chargeRule.getPerMinutePrice())
                .timeRuleList(timeRuleList)
                .build();
        rule.setPriceRule(priceRule);
        if (!ObjectUtils.nullSafeEquals(chargeRule.getBaseKilo(), BigDecimal.ZERO.doubleValue())
                && !ObjectUtils.nullSafeEquals(chargeRule.getBaseMinutes(), BigDecimal.ZERO.doubleValue())) {
            //
            basicRule.setBaseKilos(chargeRule.getBaseKilo());
            basicRule.setBaseMinutes(chargeRule.getBaseMinutes());
        } else {
            // 分时规则
            basicRule.setBaseKilos(0d);
            basicRule.setBaseMinutes(0d);
            List<ChargeRuleDetail> ruleDetailList = chargeRuleDetailMapper.selectByRuleId(chargeRule.getId());
            for (ChargeRuleDetail chargeRuleDetail : ruleDetailList) {
                if (chargeRuleDetail.getStart() >= 24 || chargeRuleDetail.getEnd() >= 24
                        || chargeRuleDetail.getStart() > chargeRuleDetail.getEnd()) {
                    return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), "计价规则错误");
                }
                TimeRule timeRule = TimeRule.builder()
                        .start(chargeRuleDetail.getStart())
                        .end(chargeRuleDetail.getEnd())
                        .perKiloPrice(chargeRuleDetail.getPerKiloPrice())
                        .perMinutePrice(chargeRuleDetail.getPerMinutePrice())
                        .build();
                timeRuleList.add(timeRule);
            }
        }

        // 2.4.远途服务费 规则
        BeyondRule beyondRule = BeyondRule.builder()
                .beyondPerKiloPrice(chargeRule.getBeyondPerKiloPrice())
                .beyondStartKilo(chargeRule.getBeyondStartKilo())
                .build();
        rule.setBeyondRule(beyondRule);

        // 2.5.夜间服务费规则
        NightRule nightRule = NightRule.builder()
                .start(chargeRule.getNightStart())
                .end(chargeRule.getNightEnd())
                .nightPerKiloPrice(chargeRule.getNightPerKiloPrice())
                .nightPerMinutePrice(chargeRule.getNightPerMinutePrice())
                .build();

        // 2.6. 标签价格
        List<TagPrice> tagList = new ArrayList<>();
        if (StringUtils.isNotBlank(orderDtoRequest.getUserFeature())) {
            tagList.add(TagPrice.builder()
                    .tagName("D#1")
                    .tagPrice(new BigDecimal("10.0"))
                    .build());
        }
        rule.setNightRule(nightRule);

        return ResponseResult.success(rule);
    }


    /**
     * description: 获取预估价格 <BR>
     *
     * @param orderId:
     * @return {@link PriceResult}
     * @author zhao.song   2021/5/30 16:46
     */
    public PriceResult getOrderPrice(Integer orderId) throws Exception {
        log.info("orderId={}", orderId);
        try {
            ResponseResult responseResult = restTemplate.getForObject(servicesConfig.getValuation() + "/valuation/forecast/" + orderId, ResponseResult.class);
            PriceResult priceResult = RestTemplateHelper.parse(responseResult, PriceResult.class);
            log.info("预估价格返回数据={}", priceResult);
            return priceResult;
        } catch (Exception e) {
            log.error("调用接口Distance错误:", e);
            throw e;
        }
    }
}
