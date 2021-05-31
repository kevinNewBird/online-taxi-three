package com.mashibing.servicevaluation.controller;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.servicevaluation.PriceResult;
import com.mashibing.servicevaluation.service.ValuationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * description  计价规则控制器 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 17:28
 **/
@RestController
@Slf4j
@RequestMapping("/valuation")
public class ValuationController {

    private static final String ERR_CALC_FORECAST_PRICE = "订单预估价格计算错误";
    private static final String ERR_DONE_FORECAST = "订单结束预估错误";
    private static final String ERR_CALC_CURRENT_PRICE = "计算订单价格错误";
    private static final String ERR_CALC_SETTLEMENT_PRICE = "订单结算价格计算错误";

    @Autowired
    private ValuationService valuationService;

    /**
     * 订单预估价格计算
     *
     * @param orderId 订单ID
     * @return 预估价格
     */
    @GetMapping("/forecast/{orderId}")
    public ResponseResult forecast(@PathVariable(value = "orderId") Integer orderId) {
        BigDecimal price;
        try {
            price = valuationService.calcForecastPrice(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}：orderId={}", ERR_CALC_FORECAST_PRICE, orderId, e);
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(), ERR_CALC_FORECAST_PRICE);
        }

        return ResponseResult.success(new PriceResult(Optional.ofNullable(price).orElse(BigDecimal.ZERO).doubleValue()));
    }
}
