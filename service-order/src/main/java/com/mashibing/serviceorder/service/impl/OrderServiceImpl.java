package com.mashibing.serviceorder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashibing.internalcommon.constant.*;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceorder.BaseInfoDto;
import com.mashibing.internalcommon.dto.serviceorder.OrderPrice;
import com.mashibing.internalcommon.dto.serviceorder.request.OrderDtoRequest;
import com.mashibing.internalcommon.dto.servicevaluation.PriceResult;
import com.mashibing.internalcommon.dto.servicevaluation.charging.KeyRule;
import com.mashibing.internalcommon.dto.servicevaluation.charging.Rule;
import com.mashibing.internalcommon.entity.serviceorder.Order;
import com.mashibing.internalcommon.entity.serviceorder.OrderRuleMirror;
import com.mashibing.internalcommon.entity.serviceorder.PassengerInfo;
import com.mashibing.internalcommon.util.IdWorker;
import com.mashibing.internalcommon.util.RestTemplateHelper;
import com.mashibing.serviceorder.dao.OrderMapper;
import com.mashibing.serviceorder.dao.OrderRuleMirrorMapper;
import com.mashibing.serviceorder.dao.PassengerInfoMapper;
import com.mashibing.serviceorder.service.OrderService;
import com.mashibing.serviceorder.task.OrderRequestTask;
import com.mashibing.serviceorder.task.OtherInterfaceTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * description  订单业务 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/26 8:04
 ***********************/
@SuppressWarnings("all")
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final String PASSENGERS_IS_NULL = "乘客信息为空";

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;

    @Autowired
    private OrderRequestTask orderRequestTask;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PassengerInfoMapper passengerInfoMapper;

    @Autowired
    private OrderRuleMirrorMapper orderRuleMirrorMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    private boolean flag;

    private boolean sate;

    /**
     * description: 订单预估 <BR>
     *
     * @param orderDtoRequest:
     * @return {@link ResponseResult<OrderPrice>}
     * @author zhao.song   2021/5/26 18:40
     */
    @Override
    public ResponseResult<OrderPrice> forecastOrderCreate(OrderDtoRequest orderDtoRequest) throws Exception {
        log.info("orderDtoRequest:{}", orderDtoRequest);
        OrderPrice orderPrice = OrderPrice.builder().build();
        // 1.判断订单是否存在,不存在做新建
        // TIP:半日租和全日租的类型无需预估价格
        ResponseResult responseResult;
        if (orderDtoRequest.getOrderId() == null) {
            if (OrderServiceTypeEnum.CHARTERED_CAR.getCode() != orderDtoRequest.getServiceTypeId()
                    && OrderServiceTypeEnum.THROUGHOUT_THE_DAY.getCode() != orderDtoRequest.getServiceTypeId()) {
                // 1.1.第三方测距
                try {
                    responseResult = otherInterfaceTask.requestRoute(orderDtoRequest);
                    if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
                        return responseResult;
                    }
                } catch (Exception ex) {
                    log.error("测量距离失败", ex);
                    throw ex;
                }

                // 1.2.校验基础信息
                responseResult = orderRequestTask.checkBaseInfo(orderDtoRequest);
                if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
                    return responseResult;
                }
                try {
                    BaseInfoDto baseInfoDto = RestTemplateHelper.parse(responseResult, BaseInfoDto.class);
                    orderDtoRequest.setCityName(baseInfoDto.getCityName());
                    orderDtoRequest.setChannelName(baseInfoDto.getChannelName());
                    orderDtoRequest.setServiceTypeName(baseInfoDto.getServiceTypeName());
                    orderDtoRequest.setCarLevelName(baseInfoDto.getCarLevelName());
                } catch (JsonProcessingException e) {
                    log.error("请求参数基础信息封装错误", e);
                }

                // 1.3.获取计费规则
                responseResult = otherInterfaceTask.getOrderChargeRule(orderDtoRequest);
                if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
                Rule rule = RestTemplateHelper.parse(responseResult, Rule.class);
                log.info("Rule1{}", rule);
                // 1.4.创建订单
                try {
                    responseResult = createOrderAndOrderRuleMirror(orderDtoRequest, rule);
                    if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                        return responseResult;
                    }
                    orderDtoRequest.setOrderId(Integer.valueOf(responseResult.getData().toString()));
                } catch (Exception ex) {
                    log.error("创建订单失败!", ex);
                }
            }
        } else {
            if (StringUtils.isNotBlank(orderDtoRequest.getUserFeature())) {
                responseResult = updateOrderServiceAndUserFeature(orderDtoRequest);
                if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                    return responseResult;
                }
            }
            responseResult = updateOrderRuleMirror(orderDtoRequest);
            if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            sate = true;
        }

        // 2.估价
        if (sate) {
            try {
                PriceResult priceResult = otherInterfaceTask.getOrderPrice(orderDtoRequest.getOrderId());
                orderPrice.setPrice(priceResult.getPrice());
            } catch (Exception ex) {
                log.error("计价接口发生错误", ex);
                throw ex;
            }
        }
        orderPrice.setOrderId(orderPrice.getOrderId());
        return ResponseResult.success(orderPrice);
    }

    /**
     * description: 创建订单和计价规则 <BR>
     *
     * @param orderDtoRequest:
     * @param rule:
     * @return {@link ResponseResult}
     * @author zhao.song   2021/5/29 23:46
     */
    public ResponseResult createOrderAndOrderRuleMirror(OrderDtoRequest orderDtoRequest
            , Rule rule) throws Exception {
        ResponseResult responseResult;
        Integer orderId;
        // 1.半日租全日租
        if (OrderServiceTypeEnum.CHARTERED_CAR.getCode() == orderDtoRequest.getServiceTypeId()
                || OrderServiceTypeEnum.CHARTERED_CAR.getCode() == orderDtoRequest.getServiceTypeId()) {
            orderDtoRequest.setEndAddress("-");
            orderDtoRequest.setEndLongitude(orderDtoRequest.getStartLongitude());
            orderDtoRequest.setEndLatitude(orderDtoRequest.getStartLatitude());
        }
        // 2.创建订单
        responseResult = createOrder(orderDtoRequest);
        if (CommonStatusEnum.SUCCESS.getCode() == responseResult.getCode()) {
            flag = true;
            Order order = RestTemplateHelper.parse(responseResult, Order.class);
            orderId = order.getId();
            // 插入更新计价规则
            responseResult = insertOrUpdateOrderRuleMirror(rule, orderId);
            if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            sate = true;
            // 缓存
            redisTemplate.opsForValue()
                    .set(String.format("%s:%s:%s", OrderRuleNames.PREFIX, OrderRuleNames.RULE, orderId)
                            , new ObjectMapper().writeValueAsString(rule), 1, TimeUnit.HOURS);

        } else {
            return responseResult;
        }
        return responseResult.setData(orderId);
    }

    /**
     * description: 创建订单 <BR>
     *
     * @param orderDtoRequest:
     * @return {@link ResponseResult}
     * @author zhao.song   2021/5/29 23:09
     */
    private ResponseResult createOrder(OrderDtoRequest orderDtoRequest) {
        // 1.判断乘客信息是否存在
        if (null == orderDtoRequest.getPassengerInfoId()) {
            // 1.1.根据id获取乘客信息
            PassengerInfo passengerInfo = passengerInfoMapper.selectByPrimaryKey(orderDtoRequest.getPassengerInfoId());
            if (null == passengerInfo) {
                return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), PASSENGERS_IS_NULL);
            }
            Order order = Order.builder()
                    .orderNumber(IdWorker.nextId())
                    .passengerInfoId(orderDtoRequest.getPassengerInfoId())
                    .passengerPhone(passengerInfo.getPhone())
                    .deviceCode(orderDtoRequest.getDeviceCode())
                    .userLongitude(orderDtoRequest.getUserLongitude())
                    .userLatitude(orderDtoRequest.getUserLatitude())
                    .startLongitude(orderDtoRequest.getStartLongitude())
                    .startLatitude(orderDtoRequest.getStartLatitude())
                    .startAddress(orderDtoRequest.getStartAddress())
                    .serviceType(orderDtoRequest.getServiceTypeId())
                    .status(OrderStatusEnum.CALL_ORDER_FORECAST.getCode())
                    .orderStartTime(OrderEnum.SERVICE_TYPE.getCode() != orderDtoRequest.getServiceTypeId()
                            ? orderDtoRequest.getOrderStartTime() : new Date())
                    .orderChannel(orderDtoRequest.getChannelId())
                    .endLongitude(orderDtoRequest.getEndLongitude())
                    .endLatitude(orderDtoRequest.getEndLatitude())
                    .endAddress(orderDtoRequest.getEndAddress())
                    .source(orderDtoRequest.getSource())
                    .createTime(new Date())
                    .build();
            if (null != orderDtoRequest.getUserFeature()) {
                order = order.setUserFeature(orderDtoRequest.getUserFeature());
            }
            orderMapper.insertSelective(order);
            return ResponseResult.success(order);
        } else {// 2.不存在抛出异常返回
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), PASSENGERS_IS_NULL);
        }

    }

    /**
     * description: 插入更新计价规则 <BR>
     *
     * @param rule:
     * @param orderId:
     * @return {@link ResponseResult}
     * @author zhao.song   2021/5/29 23:10
     */
    public ResponseResult insertOrUpdateOrderRuleMirror(Rule rule, Integer orderId) {
        log.info("Rule{}", rule);
        try {
            OrderRuleMirror orderRuleMirror = OrderRuleMirror.builder()
                    .orderId(orderId)
                    .ruleId(rule.getId())
                    .rule(new ObjectMapper().writeValueAsString(rule))
                    .build();
            int up = 0;
            if (!flag) {
                up = orderRuleMirrorMapper.updateByPrimaryKeySelective(orderRuleMirror);
            } else {
                up = orderRuleMirrorMapper.insertSelective(orderRuleMirror);
            }
            if (up == 0) {
                return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), "更新计价规则失败");
            }
        } catch (JsonProcessingException e) {
            log.error("插入或更新计价规则镜像失败!", e);
        }
        return ResponseResult.success(null);
    }

    /**
     * 修改订单服务类型与标签
     *
     * @param orderDtoRequest
     * @return
     */
    public ResponseResult updateOrderServiceAndUserFeature(OrderDtoRequest orderDtoRequest) {
        log.info("OrderRequest={}", orderDtoRequest);
        int i;
        Order newOrder = orderMapper.selectByPrimaryKey(orderDtoRequest.getOrderId());
        newOrder.setUserFeature(orderDtoRequest.getUserFeature());
        i = orderMapper.updateByPrimaryKeySelective(newOrder);
        if (i == 0) {
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), "更新失败");
        }
        return ResponseResult.success(null);
    }

    /**
     * description: 修改计价规则 <BR>
     *
     * @param orderDtoRequest:
     * @return {@link ResponseResult}
     * @author zhao.song   2021/5/30 17:09
     */
    public ResponseResult updateOrderRuleMirror(OrderDtoRequest orderDtoRequest) {
        try {
            OrderRuleMirror orderRuleMirror = orderRuleMirrorMapper
                    .selectByPrimaryKey(orderDtoRequest.getOrderId());
            String originRule = orderRuleMirror.getRule();
            Rule rule = RestTemplateHelper.parse(originRule, Rule.class);
            KeyRule keyRule = rule.getKeyRule();

            orderDtoRequest.setServiceTypeId(keyRule.getServiceTypeId());
            orderDtoRequest.setServiceTypeName(keyRule.getServiceTypeName());
            orderDtoRequest.setChannelId(keyRule.getChannelId());
            orderDtoRequest.setChannelName(keyRule.getChannelName());
            orderDtoRequest.setCityCode(keyRule.getCityCode());
            orderDtoRequest.setCityName(keyRule.getCityName());

            ResponseResult responseResult = otherInterfaceTask.getOrderChargeRule(orderDtoRequest);
            if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }

            Rule newRule = RestTemplateHelper.parse(responseResult, Rule.class);
            flag = false;
            responseResult = insertOrUpdateOrderRuleMirror(newRule, orderDtoRequest.getOrderId());
            if (CommonStatusEnum.SUCCESS.getCode() != responseResult.getCode()) {
                return responseResult;
            }
            redisTemplate.opsForValue()
                    .set(String.format("%s:%s:%s", OrderRuleNames.PREFIX, OrderRuleNames.RULE, orderDtoRequest.getOrderId())
                            , new ObjectMapper().writeValueAsString(newRule), 1, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            log.error("更新订单计费规则错误", e);
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), "更新订单计费规则错误");
        }
        return ResponseResult.success(null);
    }
}
