package com.mashibing.service;

import com.mashibing.internalcommon.dto.ResponseResult;

/***********************
 * @Description: 验证码服务 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 23:53
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public interface VerificationCodeService {

    /**
     * Description: 发送验证码给手机 <BR>
     *
     * @param phoneNumber:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 23:55
     */
    ResponseResult send(String phoneNumber);

    /**
     * Description: 校验验证码(在用户输入验证码做登录时做校验) <BR>
     *
     * @param phoneNumber:
     * @param code:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 23:55
     */
    ResponseResult verifyCode(String phoneNumber, String code);
}
