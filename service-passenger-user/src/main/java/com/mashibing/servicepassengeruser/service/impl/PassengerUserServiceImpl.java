package com.mashibing.servicepassengeruser.service.impl;

import com.mashibing.internalcommon.constant.RedisKeyExpirationConstant;
import com.mashibing.internalcommon.constant.RedisKeyPrefixConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.util.JwtUtil;
import com.mashibing.servicepassengeruser.dao.ServicePassengerUserDao;
import com.mashibing.servicepassengeruser.entity.ServicePassengerUserInfo;
import com.mashibing.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/***********************
 * @Description: 乘客用户管理类 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 22:09
 * @version: 1.0
 ***********************/
@Service
@SuppressWarnings("all")
public class PassengerUserServiceImpl implements PassengerUserService {

    @Autowired
    private ServicePassengerUserDao passengerUserDao;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public ResponseResult login(String passengerPhone) {
        // 如果数据库没有此用户，插库。可以用分布锁，也可以用 唯一索引。
        // 为什么此时用手机号？
        // 查出用户id
        long passengerId;
        ServicePassengerUserInfo dbPassenger = passengerUserDao.selectByPhoneNumber(passengerPhone);
        if (!Objects.isNull(dbPassenger)) {
            passengerId = dbPassenger.getId();
        }else{
            ServicePassengerUserInfo newPassenger = ServicePassengerUserInfo.builder()
                    .createTime(new Date())
                    .passengerGender((byte) 1)
                    .passengerName("kevin")
                    .registerDate(new Date())
                    .userState((byte) 1)
                    .passengerPhone(passengerPhone)
                    .build();
            passengerId = passengerUserDao.insertSelective(newPassenger);
        }

        // 生成 token 的时候，如果要服务端控制，要把它 存到 redis中，在设置过期时间。
        String token = JwtUtil.createToken(String.valueOf(passengerId), new Date());

        // 存入redis，设置过期时间。
        redisTemplate.opsForValue().set(generateLoginCacheKey(passengerId), token
                , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getDuration()
                , RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MIN.getTimeUnit());
        return ResponseResult.success(token);
    }

    @Override
    public ResponseResult logout(long passengerId) {
        redisTemplate.delete(generateLoginCacheKey(passengerId));
        return null;
    }

    private String generateLoginCacheKey(long passengerId) {
        return RedisKeyPrefixConstant.PASSENGER_LOGIN_TOKEN_APP_KEY_PREFIX + passengerId;
    }
}
