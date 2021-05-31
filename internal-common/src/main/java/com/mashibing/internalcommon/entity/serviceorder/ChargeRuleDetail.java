package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description  计费规则时间段表 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 18:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargeRuleDetail {

    private Integer id;

    private Integer ruleId;

    /**
     * 时间段开始
     */
    private Integer start;

    /**
     * 时间段结束
     */
    private Integer end;

    /**
     * 超公里单价
     */
    private BigDecimal perKiloPrice;

    /**
     * 超时间单价
     */
    private BigDecimal perMinutePrice;

    /**
     * 是否删除: 0未删除,1删除
     */
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;
}
