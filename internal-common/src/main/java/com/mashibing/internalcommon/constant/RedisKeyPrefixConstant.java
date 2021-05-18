package com.mashibing.internalcommon.constant;

/***********************
 * @Description: redis 存储前缀常量类 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 15:20
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public final class RedisKeyPrefixConstant {

    public static final String SEND_LIMIT_FREQ_CODE_KEY_PREFIX = "send_limit_freq_code_";

    /**
     * 乘客登录验证码 key 前缀
     */
    public static final String PASSENGER_LOGIN_CODE_KEY_PREFIX = "passenger_login_code_";

    public static final String PASSENGER_LOGIN_TOKEN_APP_KEY_PREFIX = "passenger_login_token_app_";

    public static final String PASSENGER_LOGIN_TOKEN_WEIXIN_KEY_PREFIX = "passenger_login_token_wechat_";


    /**
     * 司机登录验证码 key前缀
     */
    public static final String DRIVER_LOGIN_CODE_KEY_PREFIX = "driver_login_code_";
}
