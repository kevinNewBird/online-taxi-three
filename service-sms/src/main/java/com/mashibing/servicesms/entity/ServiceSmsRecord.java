package com.mashibing.servicesms.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/***********************
 * @Description: 消息记录 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 11:07
 * @version: 1.0
 ***********************/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@SuppressWarnings("all")
public class ServiceSmsRecord implements Serializable {
    private static final long serialVersionUID = 2L;

    private Integer id;

    private String phoneNumber;

    private String smsContent;

    private Date sendTime;

    private String operator;

    /**
     * 发送状态 0:失败  1: 成功
     */
    private Boolean sendFlag;

    /**
     * 发送失败次数
     */
    private Integer sendNumber;

    private Date createTime;

    private Date updateTime;

}
