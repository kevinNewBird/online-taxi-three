package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * description  远途服务费规则  <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 15:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeyondRule {

    /**
     * 远途起算公里
     */
    private Double beyondStartKilo;

    /**
     * 远途单价(元/公里)
     */
    private BigDecimal beyondPerKiloPrice;
}
