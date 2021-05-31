package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * description  渠道管理表 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 13:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Channel implements Serializable {

    private Integer id;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道状态:1开启,0暂停
     */
    private Integer channelStatus;

    /**
     * 操作人id
     */
    private Integer operatorId;

    private Date createTime;

    private Date updateTime;


}
