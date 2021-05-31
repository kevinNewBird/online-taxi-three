package com.mashibing.internalcommon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***********************
 * @Description: 行程枚举 <BR>
 * @author: zhao.song
 * @since: 2021/5/26 11:31
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Getter
@AllArgsConstructor
public enum DistanceEnum {

    /**
     * 是否超出24小时
     */
    DURATION(86400, "duration"),
    /**
     * 判断是否超出设定距离
     */
    DISTANCE(260000, "distance");

    private int code;
    private String value;
}
