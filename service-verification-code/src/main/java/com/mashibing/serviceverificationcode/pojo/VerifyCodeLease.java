package com.mashibing.serviceverificationcode.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/***********************
 * @Description: 验证码租约 <BR>
 * @author: zhao.song
 * @since: 2021/5/11 17:22
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@Builder
public class VerifyCodeLease implements Serializable {

    // 获取次数
    private long repeatNums;

    // 第一次获取验证码时间
    private long firstObtainTime;


    private boolean oneMinLimit;

    private boolean oneHourLimit;

    public void resetRepeatNums() {
        repeatNums++;
    }

}
