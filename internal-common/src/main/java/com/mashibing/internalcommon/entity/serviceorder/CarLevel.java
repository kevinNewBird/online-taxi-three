package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * description  车辆级别列表 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 14:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarLevel implements Serializable {

    private Integer id;

    /**
     * 车辆级别标签
     */
    private String label;

    /**
     * 车型图标
     */
    private String icon;

    /**
     * 状态:0未启用,1启用
     */
    private Integer enable;

    /**
     * 操作人id
     */
    private Integer operatorId;

    private Date createTime;

    private Date updateTime;


}
