package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * description  服务类型表 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/26 21:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceType implements Serializable {

    private Integer id;

    /**
     * 服务类型名称
     */
    private String serviceTypeName;

    /**
     * 服务类型状态 1开启 0暂停
     */
    private Integer serviceTypeStatus;

    /**
     * 使用状态 1使用 0未使用
     */
    private Integer isUse;

    /**
     * 操作人id
     */
    private Integer operatorId;

    private Date createTime;

    private Date updateTime;


}
