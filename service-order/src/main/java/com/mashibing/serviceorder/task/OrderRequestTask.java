package com.mashibing.serviceorder.task;

import com.mashibing.internalcommon.constant.EnableDisableEnum;
import com.mashibing.internalcommon.constant.OrderEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceorder.BaseInfoDto;
import com.mashibing.internalcommon.dto.serviceorder.request.OrderDtoRequest;
import com.mashibing.internalcommon.entity.serviceorder.CarLevel;
import com.mashibing.internalcommon.entity.serviceorder.Channel;
import com.mashibing.internalcommon.entity.serviceorder.City;
import com.mashibing.internalcommon.entity.serviceorder.ServiceType;
import com.mashibing.serviceorder.dao.CarLevelMapper;
import com.mashibing.serviceorder.dao.ChannelMapper;
import com.mashibing.serviceorder.dao.CityMapper;
import com.mashibing.serviceorder.dao.ServiceTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * description  订单任务请求任务 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/26 21:15
 **/
@Component
@Slf4j
public class OrderRequestTask {

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private CarLevelMapper carLevelMapper;


    public ResponseResult checkBaseInfo(OrderDtoRequest orderDtoRequest) {
        // 1.检查城市
        City city = cityMapper.selectByCityCode(orderDtoRequest.getCityCode());
        if (Objects.isNull(city)) {
            return ResponseResult.fail(OrderEnum.CITIES_DON_EXIST.getCode(), OrderEnum.CITIES_DON_EXIST.getValue());
        } else {
            // 状态校验
            if (city.getCityStatus() != null
                    && city.getCityStatus() != EnableDisableEnum.ENABLE.getCode()) {
                return ResponseResult.fail(OrderEnum.CITIES_IS_NOT_ENABLED.getCode(), OrderEnum.CITIES_IS_NOT_ENABLED.getValue());
            }
        }

        // 2.检查渠道
        Channel channel = channelMapper.selectByPrimaryKey(orderDtoRequest.getChannelId());
        if (Objects.isNull(channel)) {
            return ResponseResult.fail(OrderEnum.CHANNEL_IS_NO.getCode(), OrderEnum.CHANNEL_IS_NO.getValue());
        } else {
            // 状态校验
            if (channel.getChannelStatus() != null
                    && channel.getChannelStatus() != EnableDisableEnum.ENABLE.getCode()) {
                return ResponseResult.fail(OrderEnum.CHANNEL_IS_NOT_ENABLED.getCode()
                        , OrderEnum.CHANNEL_IS_NOT_ENABLED.getValue());
            }
        }
        // 3.检查服务类型
        ServiceType serviceType = serviceTypeMapper.selectByPrimaryKey(orderDtoRequest.getServiceTypeId());
        if (Objects.isNull(serviceType)) {
            return ResponseResult.fail(OrderEnum.SERVICE_TYPE_IS_NO.getCode(), OrderEnum.SERVICE_TYPE_IS_NO.getValue());
        } else {
            // 状态校验
            if (serviceType.getServiceTypeStatus() != null
                    && serviceType.getServiceTypeStatus() != EnableDisableEnum.ENABLE.getCode()) {
                return ResponseResult.fail(OrderEnum.SERVICE_TYPE_IS_NOT_ENABLED.getCode()
                        , OrderEnum.SERVICE_TYPE_IS_NOT_ENABLED.getValue());
            }
        }

        // 4.检查车辆级别
        CarLevel carLevel = carLevelMapper.selectByPrimaryKey(orderDtoRequest.getCarLevelId());
        if (Objects.isNull(carLevel)) {
            return ResponseResult.fail(OrderEnum.CAR_LEVEL_IS_NO.getCode(), OrderEnum.CAR_LEVEL_IS_NO.getValue());
        } else {
            // 状态校验
            if (carLevel.getEnable() != null
                    && carLevel.getEnable() != EnableDisableEnum.ENABLE.getCode()) {
                return ResponseResult.fail(OrderEnum.CAR_LEVEL_IS_NOT_ENABLED.getCode()
                        , OrderEnum.CAR_LEVEL_IS_NOT_ENABLED.getValue());
            }
        }
        // 5.组装基础信息
        BaseInfoDto baseInfoDto = BaseInfoDto.builder()
                .cityName(city.getCityName())
                .channelName(channel.getChannelName())
                .serviceTypeName(serviceType.getServiceTypeName())
                .carLevelName(carLevel.getLabel())
                .build();
        return ResponseResult.success(baseInfoDto);
    }
}
