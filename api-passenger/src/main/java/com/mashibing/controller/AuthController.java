package com.mashibing.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.request.UserAuthRequest;
import com.mashibing.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***********************
 * @Description: 授权类 <BR>
 * @author: zhao.song
 * @since: 2021/5/14 7:42
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseResult auth(@RequestBody UserAuthRequest authRequest) {
        return authService.auth(authRequest);
    }

}
