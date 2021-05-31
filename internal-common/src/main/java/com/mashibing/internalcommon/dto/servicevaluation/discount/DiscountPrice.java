package com.mashibing.internalcommon.dto.servicevaluation.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * description  动态调价 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/31 17:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class DiscountPrice {

    /**
     * 折扣最大价格
     */
    private BigDecimal discountMaxPrice;

    /**
     * 折扣率
     */
    private Double discount;
}
