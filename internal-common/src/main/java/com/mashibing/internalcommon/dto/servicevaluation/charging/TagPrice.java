package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * description  标签费用 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 16:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagPrice {
    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签价格
     */
    private BigDecimal tagPrice;
}
