package com.mashibing.internalcommon.constant;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/***********************
 * @Description: redis key过期时间常量类<BR>
 * @author: zhao.song
 * @since: 2021/5/12 15:46
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
public enum RedisKeyExpirationConstant {
    /**
     * 过期时间60s, 正常的过期时间为60秒.
     * 在验证码的生成过程中使用受限, 根据需求: 1分钟三次,5分钟生成受限;1小时10次,24小时受限.
     * 所以,验证码的有效期最少为 1小时
     */
    CODE_EXPIRE_TIME_SIXTY_SEC(60, TimeUnit.SECONDS),

    CODE_EXPIRE_TIME_TEN_MIN(10, TimeUnit.MINUTES),

    CODE_EXPIRE_TIME_ONE_HOUR(1, TimeUnit.HOURS),
   ;

    @Getter
    int duration;
    @Getter
    TimeUnit timeUnit;

    RedisKeyExpirationConstant(int duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
    }
}
