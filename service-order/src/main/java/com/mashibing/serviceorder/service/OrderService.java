package com.mashibing.serviceorder.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.serviceorder.OrderPrice;
import com.mashibing.internalcommon.dto.serviceorder.request.OrderDtoRequest;

/***********************
 * @Description: 订单业务 <BR>
 * @author: zhao.song
 * @since: 2021/5/26 7:59
 * @version: 1.0
 ***********************/
public interface OrderService {

    /**
     * 订单预估
     * @param request
     * @return
     * @throws Exception
     */
    ResponseResult<OrderPrice> forecastOrderCreate(OrderDtoRequest request) throws Exception;
}
