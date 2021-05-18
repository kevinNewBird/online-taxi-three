package com.mashibing.servicesms.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/***********************
 * @Description: 短信模板实体类 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 22:24
 * @version: 1.0
 ***********************/
@Data
@ToString
@Builder
@SuppressWarnings("all")
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSmsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String templateId;

    private String templateName;

    private String templateContent;

    private Date createTime;

    private Date updateTime;

    /**
     * 模板类型（1：营销；2：通知；3：订单）
     */
    private Boolean templateType;
}
