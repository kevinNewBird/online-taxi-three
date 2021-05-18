package com.mashibing.servicesms.constant;

import lombok.Getter;

/***********************
 * @Description: 第三方短信 错误码 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 11:00
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public enum ThirdSmsStatusEnum {

    /**
     * 发送成功
     */
    SEND_SUCCESS(0, "sms send success"),

    /**
     * 操作异常
     */
    INTERNAL_SERVER_EXCEPTION(-1, "exception"),

    /**
     * 操作失败
     */
    SEND_FAIL(1, "sms send fail"),
    ;
    @Getter
    private int code;

    @Getter
    private String value;

    ThirdSmsStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
