package com.mashibing.internalcommon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description  计价规则种类枚举 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 20:44
 **/
@Getter
@AllArgsConstructor
public enum ChargingCategoryEnum {
    /**
     * 预估订单: 0
     */
    Forecast(0, "预约"),

    /**
     * 结算订单: 1
     */
    Settlement(1, "结算"),

    /**
     * 实时订单: 2
     */
    RealTime(2, "实时");

    private int code;
    private String name;
}
