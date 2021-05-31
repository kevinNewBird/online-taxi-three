package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description  乘客信息表 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 13:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PassengerInfo implements Serializable {

    private Integer id;

    /**
     * 电话
     */
    private String phone;

    /**
     * 学历
     */
    private String education;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 乘客名
     */
    private String passengerName;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 性别:0女,1男
     */
    private Integer gender;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户类型:1个人用户 ;2企业用户
     */
    private Integer passengerType;

    /**
     * 会员等级
     */
    private Integer userLevel;

    /**
     * 注册渠道: 1安卓,2ios
     */
    private Integer registerType;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录方式: 1,验证码;2,密码
     */
    private Integer lastLoginMethod;

    /**
     * 上次登录大屏时间
     */
    private Date lastLoginScreenTime;

    /**
     * 上次登录大屏方式
     */
    private Integer lastLoginScreenMethod;

    /**
     * 最后一次下单时间
     */
    private Date lastOrderTime;

    private Date createTime;

    private Date updateTime;

}
