package com.mashibing.servicevaluation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * description  实时价格请求DTO <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 20:45
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class CurrentPriceRequestDto {

    private Integer orderId;

    private Integer carId;

    private Long startTime;

    private Long endTime;
}
