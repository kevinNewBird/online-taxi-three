package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * description  分时计费规则 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 16:48
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeRule {

    /**
     * 计费时间段 - 开始时间点
     */
    private Integer start;

    /**
     * 计费时间段 - 结束时间点
     */
    private Integer end;

    /**
     * 超公里单价（元/公里）
     */
    private BigDecimal perKiloPrice;

    /**
     * 超时间单价（元/分钟）
     */
    private BigDecimal perMinutePrice;
}
