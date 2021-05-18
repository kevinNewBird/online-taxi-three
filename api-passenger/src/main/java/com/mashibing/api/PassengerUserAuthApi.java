package com.mashibing.api;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/***********************
 * @Description: 乘客用户授权登录api <BR>
 * @author: zhao.song
 * @since: 2021/5/14 10:18
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@FeignClient(name = "service-passenger-user")
public interface PassengerUserAuthApi {


    /**
     * Description: 用户登录 <BR>
     *
     * @param loginRequest:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 22:51
     */
    @PostMapping("/auth/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest);

    /**
     * Description: 用户退出登录 <BR>
     *
     * @param passengerId:
     * @return: {@link ResponseResult}
     * @author: zhao.song   2021/5/13 22:51
     */
    @GetMapping("/auth/logout")
    public ResponseResult logout(long passengerId) ;

}
