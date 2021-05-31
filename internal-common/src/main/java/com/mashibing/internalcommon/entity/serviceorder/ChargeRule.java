package com.mashibing.internalcommon.entity.serviceorder;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * description  计费规则表  <BR>
 * keyRule -> 整合表 Channel/City/ServiceType/CarLevel
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 22:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true) // 属性链式调用
public class ChargeRule implements Serializable {

    private Integer id;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 服务类型id
     */
    private Integer serviceTypeId;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 车辆级别id
     */
    private Integer carLevelId;

    /**
     * 基础价
     */
    private BigDecimal lowestPrice;

    /**
     * 起步价
     */
    private BigDecimal basePrice;

    /**
     * 基础价格包含公里数
     */
    private Double baseKilo;

    /**
     * 基础价格包含时长数(分钟)
     */
    private Double baseMinutes;

    /**
     * 超公里单价(每公里价格)
     */
    private BigDecimal perKiloPrice;

    /**
     * 超时长单价(每分钟)
     */
    private BigDecimal perMinutePrice;

    /**
     * 远程起算公里
     */
    private Double beyondStartKilo;

    /**
     * 远程单价(元/公里)
     */
    private BigDecimal beyondPerKiloPrice;

    /**
     * 夜间服务-开始时间
     */
    private Date nightStart;

    /**
     * 夜间服务-结束时间
     */
    private Date nightEnd;

    /**
     * 夜间服务-超公里加收单价(元/公里)
     */
    private BigDecimal nightPerKiloPrice;
    /**
     * 夜间服务-超时间加收单价(元/分钟)
     */
    private BigDecimal nightPerMinutePrice;

    /**
     * 生效时间
     */
    private Date effectiveTime;

    /**
     * 生效状态 0已生效,1未失效
     */
    private Integer activeStatus;

    /**
     * 是否不可用: 0可用,1不可用
     */
    private Integer isUnuse;

    /**
     * 创建人id
     */
    private Integer creatorId;

    /**
     * 操作人id
     */
    private Integer operatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date updateTime;
}
