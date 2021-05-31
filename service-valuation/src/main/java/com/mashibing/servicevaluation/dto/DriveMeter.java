package com.mashibing.servicevaluation.dto;

import com.mashibing.internalcommon.constant.ChargingCategoryEnum;
import com.mashibing.internalcommon.dto.map.Distance;
import com.mashibing.internalcommon.dto.map.Route;
import com.mashibing.internalcommon.dto.servicevaluation.charging.Rule;
import com.mashibing.internalcommon.entity.serviceorder.Order;
import com.mashibing.internalcommon.util.UnitConverter;
import com.mashibing.servicevaluation.dto.request.CurrentPriceRequestDto;
import com.mashibing.servicevaluation.task.ValuationRequestTask;
import lombok.*;
import lombok.experimental.Accessors;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * description  行驶计价相关的请求参数: 距离时间 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 20:36
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class DriveMeter {

    /**
     * 订单
     */
    private Order order;

    /**
     * 计价规则
     */
    private Rule rule;

    /**
     * 预估时距离测量结果: 没行驶之前
     */
    private Route route;

    /**
     * 实际轨迹里程: 实际路程
     */
    private Distance distance;

    /**
     * 实时价格请求DTO
     */
    private CurrentPriceRequestDto currentPriceRequestDto;

    /**
     * 计价服务请求任务实体类
     */
    private ValuationRequestTask requestTask;

    /**
     * 计价规则种类枚举
     */
    private ChargingCategoryEnum chargingCategoryEnum;

    /**
     * 返回行驶距离（米）
     *
     * @return 行驶距离
     */
    public double getTotalDistance() {
        Double meters = 0D;
        switch (chargingCategoryEnum) {
            case Forecast:
                meters = route.getDistance();
                break;
            case Settlement:
            case RealTime:
                meters = distance.getDistance();
                break;
            default:
                break;
        }
        return Optional.ofNullable(meters).orElse(0D);
    }

    /**
     * 返回行驶时间（秒）
     *
     * @return 行驶时间
     */
    public double getTotalTime() {
        double seconds = 0;
        switch (chargingCategoryEnum) {
            case Forecast:
                seconds = route.getDuration();
                break;
            case Settlement:
                seconds = Duration.ofMillis(order.getPassengerGetOffTime().getTime() - order.getReceivePassengerTime().getTime()).getSeconds();
                break;
            case RealTime:
                seconds = Duration.ofMillis(currentPriceRequestDto.getEndTime() - currentPriceRequestDto.getStartTime()).getSeconds();
                break;
            default:
                break;
        }
        return seconds;
    }

    /**
     * 返回订单开始时间
     *
     * @return 订单开始时间
     */
    public LocalDateTime getStartDateTime() {
        Date startDate = new Date();
        switch (chargingCategoryEnum) {
            case Forecast:
                startDate = order.getOrderStartTime();
                break;
            case Settlement:
                startDate = order.getReceivePassengerTime();
                break;
            case RealTime:
                startDate = new Date(currentPriceRequestDto.getStartTime());
                break;
            default:
                break;
        }
        return UnitConverter.dateToLocalDateTime(startDate);
    }
}
