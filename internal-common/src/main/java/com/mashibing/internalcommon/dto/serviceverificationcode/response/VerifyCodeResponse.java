package com.mashibing.internalcommon.dto.serviceverificationcode.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 验证码统一有返回对象<BR>
 * @author: zhao.song
 * @since: 2021/5/11 7:55
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeResponse {

    private String code;
}
