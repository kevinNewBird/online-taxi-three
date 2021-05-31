package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * description  计费方法 规则 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 15:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceRule {

    /**
     * 超公里单价(元/公里)
     */
    private BigDecimal perKiloPrice;

    /**
     * 超时间单价(元/分钟)
     */
    private BigDecimal perMinutePrice;

    /**
     * 分段计时规则
     */
    private List<TimeRule> timeRuleList;
}
