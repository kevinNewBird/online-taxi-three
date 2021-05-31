package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description  夜间服务规则 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 15:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NightRule {

    /**
     * 夜间时间段 - 开始时间
     */
    private Date start;

    /**
     * 夜间时间段 - 结束时间
     */
    private Date end;

    /**
     * 夜间加收公里单价(元/公里)
     */
    private BigDecimal nightPerKiloPrice;

    /**
     * 夜间加收时间单价(元/分钟)
     */
    private BigDecimal nightPerMinutePrice;


}
