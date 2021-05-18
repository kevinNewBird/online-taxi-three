package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import com.mashibing.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***********************
 * @Description: 此类对用户的登录、权限和登出进行控制 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 21:49
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PassengerUserService passengerUserService;

    /**
     * Description: 用户登录 <BR>
     *
     * @param loginRequest:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 22:51
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest) {
        return passengerUserService.login(loginRequest.getPassengerPhone());
    }

    /**
     * Description: 用户退出登录 <BR>
     *
     * @param passengerId:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 22:51
     */
    @GetMapping("/logout")
    public ResponseResult logout(long passengerId) {
        return passengerUserService.logout(passengerId);
    }
}
