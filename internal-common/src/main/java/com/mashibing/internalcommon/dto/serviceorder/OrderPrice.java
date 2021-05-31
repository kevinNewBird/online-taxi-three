package com.mashibing.internalcommon.dto.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 订单价格 <BR>
 * @author: zhao.song
 * @since: 2021/5/26 8:01
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPrice {

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单价格
     */
    private Double price;
}
