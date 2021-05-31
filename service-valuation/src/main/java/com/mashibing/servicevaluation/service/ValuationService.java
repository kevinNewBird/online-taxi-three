package com.mashibing.servicevaluation.service;

import java.math.BigDecimal;

/**
 * description  预估价格服务类 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 17:30
 **/
public interface ValuationService {

    BigDecimal calcForecastPrice(Integer orderId);
}
