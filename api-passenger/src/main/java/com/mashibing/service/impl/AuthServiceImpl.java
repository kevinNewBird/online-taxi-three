package com.mashibing.service.impl;

import com.mashibing.api.PassengerUserAuthApi;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import com.mashibing.request.UserAuthRequest;
import com.mashibing.service.AuthService;
import com.mashibing.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***********************
 * @Description: 用户登录授权业务类 <BR>
 * @author: zhao.song
 * @since: 2021/5/14 10:14
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private PassengerUserAuthApi passengerUserAuthApi;

    /**
     * Description: 用户授权的登录 <BR>
     *
     * @param authRequest :
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/14 10:13
     */
    @Override
    public ResponseResult auth(UserAuthRequest authRequest) {
        // 1.校验验证码
        ResponseResult validationResult = verificationCodeService
                .verifyCode(authRequest.getPhoneNumber(), authRequest.getCode());
        if (validationResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("登录失败：验证码校验失败");
        }
        // 2.登录
        LoginRequest loginRequest = LoginRequest.builder().passengerPhone(authRequest.getPhoneNumber()).build();
        ResponseResult loginResult = passengerUserAuthApi.login(loginRequest);
        if (loginResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("登录失败");
        }
        // 3.返回结果:带token
        return loginResult;
    }
}
