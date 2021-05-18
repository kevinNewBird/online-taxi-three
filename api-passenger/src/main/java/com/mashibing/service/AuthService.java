package com.mashibing.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.request.UserAuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/***********************
 * @Description: 用户授权登录<BR>
 * @author: zhao.song
 * @since: 2021/5/14 10:12
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public interface AuthService {

    /**
     * Description: 用户授权的登录 <BR>
     *
     * @param authRequest:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/14 10:13
     */
    ResponseResult auth(UserAuthRequest authRequest);
}
