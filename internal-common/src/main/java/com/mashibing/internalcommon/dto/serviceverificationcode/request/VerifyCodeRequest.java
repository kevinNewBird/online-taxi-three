package com.mashibing.internalcommon.dto.serviceverificationcode.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 验证码统一请求 <BR>
 * @author: zhao.song
 * @since: 2021/5/11 7:57
 * @version: 1.0
 ******************a*****/
@SuppressWarnings("all")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeRequest {

    private int identify;
    private String phoneNumber;
    private String code;
}
