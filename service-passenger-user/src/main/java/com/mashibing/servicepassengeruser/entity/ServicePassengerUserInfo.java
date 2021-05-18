package com.mashibing.servicepassengeruser.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/***********************
 * @Description: 用户实体类 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 22:10
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicePassengerUserInfo implements Serializable {

    private static final long serialVersionUID = 3L;

    private Long id;

    /**
     * 注册日期
     */
    private Date registerDate;

    /**
     * 乘客手机号
     */
    private String passengerPhone;

    /**
     * 乘客姓名
     */
    private String passengerName;

    /**
     * 性别。1：男，0：女
     */
    private Byte passengerGender;

    /**
     * 用户状态：1：有效，0：失效
     */
    private Byte userState;

    private Date createTime;

    private Date updateTime;


}
