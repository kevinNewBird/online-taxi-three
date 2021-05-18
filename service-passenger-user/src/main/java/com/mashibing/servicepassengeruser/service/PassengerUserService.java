package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;

/***********************
 * @Description: 登录接口 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 22:07
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public interface PassengerUserService {


    ResponseResult login(String passengerPhone);

    ResponseResult logout(long passengerId);


}
