package com.mashibing.internalcommon.dto.servicevaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * description  几家结果DTO <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 16:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PriceResult {
    private Double price;
}
