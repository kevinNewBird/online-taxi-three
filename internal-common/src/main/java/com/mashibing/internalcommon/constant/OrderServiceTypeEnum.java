package com.mashibing.internalcommon.constant;

import lombok.Getter;

/***********************
 * @Description: 订单服务类型 <BR>
 * @author: zhao.song
 * @since: 2021/5/23 22:41
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Getter
public enum OrderServiceTypeEnum {

    /**
     * 实时订单
     */
    REAL_TIME(1, "实时订单"),

    /**
     * 预约订单
     */
    MAKE_AN_APPOINTMENT(2, "预约订单"),

    /**
     * 接机
     */
    PICK_UP(3, "接机"),

    /**
     * 送机
     */
    SEND_MACHINE(4, "送机"),

    /**
     * 半日租
     */
    CHARTERED_CAR(5, "半日租"),

    /**
     * 全日租
     */
    THROUGHOUT_THE_DAY(6, "全日租");

    private int code;

    private String value;

    OrderServiceTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
