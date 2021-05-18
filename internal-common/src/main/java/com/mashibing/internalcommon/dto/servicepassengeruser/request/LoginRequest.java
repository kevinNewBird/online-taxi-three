package com.mashibing.internalcommon.dto.servicepassengeruser.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 登录请求对象 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 21:51
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String passengerPhone;
}
