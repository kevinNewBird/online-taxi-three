package com.mashibing.internalcommon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description  TODO <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 15:29
 **/
@Getter
@AllArgsConstructor
public enum EnableDisableEnum {

    DISABLE(0, "未启用"),
    ENABLE(1, "启用");

    private int code;

    private String value;
}
