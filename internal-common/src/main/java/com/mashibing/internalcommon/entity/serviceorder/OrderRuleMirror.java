package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * description  订单计费规则镜像表 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 15:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class OrderRuleMirror implements Serializable {

    private Integer id;

    private Integer orderId;

    private Integer ruleId;

    /**
     * 规则镜像的json
     */
    private String rule;

    private Date createTime;

    private Date updateTime;

}
