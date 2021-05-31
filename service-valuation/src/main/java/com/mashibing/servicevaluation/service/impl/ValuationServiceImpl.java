package com.mashibing.servicevaluation.service.impl;

import com.mashibing.internalcommon.constant.ChargingCategoryEnum;
import com.mashibing.internalcommon.dto.servicevaluation.charging.Rule;
import com.mashibing.internalcommon.dto.servicevaluation.discount.DiscountPrice;
import com.mashibing.internalcommon.entity.serviceorder.OrderRulePrice;
import com.mashibing.internalcommon.entity.serviceorder.OrderRulePriceDetail;
import com.mashibing.internalcommon.util.PriceHelper;
import com.mashibing.servicevaluation.dao.OrderMapper;
import com.mashibing.servicevaluation.dto.DriveMeter;
import com.mashibing.servicevaluation.dto.PriceMeter;
import com.mashibing.servicevaluation.service.ValuationService;
import com.mashibing.servicevaluation.task.ValuationRequestTask;
import com.mashibing.servicevaluation.task.ValuationTask;
import com.mashibing.servicevaluation.util.PriceCache;
import com.mashibing.servicevaluation.util.RuleCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * description  预估价格服务类  <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 17:32
 **/
@Service
@Slf4j
public class ValuationServiceImpl implements ValuationService {

    public static final String ERR_DISCOUNT_RATE_RANGE = "动态调价的折扣取值范围不在0-1内";
    public static final String ERR_EXPIRE_FORECAST = "预估超过有效期限";


    @Autowired
    private ValuationRequestTask requestTask;

    @Autowired
    private ValuationTask valuationTask;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PriceCache priceCache;

    @Autowired
    private RuleCache ruleCache;

    @Override
    public BigDecimal calcForecastPrice(Integer orderId) {
        // 1.设置驾驶参数
        DriveMeter driveMeter = generateDriveMeter(orderId, ChargingCategoryEnum.Forecast);

        Rule rule = driveMeter.getRule();
        PriceMeter priceMeter = priceCache.get(orderId);
        if (Objects.isNull(priceMeter) || Objects.isNull(rule)
                || !ObjectUtils.nullSafeEquals(rule.getId(), priceMeter.getRuleId())) {
            priceMeter = generatePriceMeter(driveMeter);
        }

        //更新缓存
        priceMeter.setTagPrices(rule != null ? rule.getTagPriceList() : null);

        //计算标签价格
        BigDecimal totalPrice = PriceHelper.add(priceMeter.getBasicPriceValue(), valuationTask.calcTagPrice(driveMeter));
        priceMeter.setTotalPriceValue(totalPrice);

        //缓存结果
        priceCache.set(orderId, priceMeter, 1, TimeUnit.HOURS);

        //TODO
        doneForecast(orderId);
        return totalPrice;
    }

    /**
     * 定义驾驶参数
     *
     * @param orderId
     * @param categoryEnum
     * @return
     */
    private DriveMeter generateDriveMeter(Integer orderId, ChargingCategoryEnum categoryEnum) {
        Rule rule = requestTask.requestRule(orderId);
        DriveMeter driveMeter = DriveMeter.builder()
                .chargingCategoryEnum(categoryEnum)
                .build();

        switch (categoryEnum) {
            case Forecast:
                driveMeter.setOrder(orderMapper.selectByPrimaryKey(orderId)).setRule(rule)
                        .setRequestTask(requestTask)
                        .setRoute(requestTask.requestRoute(driveMeter));
                break;
            case RealTime:
                driveMeter.setRule(rule).setRequestTask(requestTask);
                break;

            case Settlement:
                driveMeter.setOrder(orderMapper.selectByPrimaryKey(orderId)).setRule(rule)
                        .setRequestTask(requestTask)
                        .setDistance(requestTask.requestDistance(driveMeter));
                break;
            default:
                break;
        }
        return driveMeter;
    }

    public void doneForecast(Integer orderId) {
        //获取缓存
        PriceMeter priceMeter = priceCache.get(orderId);

        if (priceMeter == null) {
            throw new RuntimeException(ERR_EXPIRE_FORECAST);
        }

        //更新标签价格
        priceMeter.getRulePrice().setTotalPrice(priceMeter.getTotalPriceValue());

        //更新DB
        valuationTask.updateToDb(ChargingCategoryEnum.Forecast, priceMeter);
    }


    /**
     * 计算价格
     *
     * @param driveMeter 驾驶参数
     * @return 价格
     */
    @SneakyThrows
    private PriceMeter generatePriceMeter(DriveMeter driveMeter) {
        //分段计价任务
        CompletableFuture<List<OrderRulePriceDetail>> calcPriceDetailFuture = valuationTask.calcDetailPrice(driveMeter);

        //基础计价任务
        CompletableFuture<OrderRulePrice> calcPriceFuture = valuationTask.calcMasterPrice(driveMeter);

        //计算最终价格
        BigDecimal price = calcPriceDetailFuture.thenCombine(calcPriceFuture, (details, master) -> {
            //计算其他价格
            valuationTask.calcOtherPrice(driveMeter, master, details);

            //计算价格合计
            BigDecimal totalPrice = PriceHelper.add(master.getBasePrice(), master.getNightPrice(), master.getBeyondPrice(), master.getPathPrice(), master.getDurationPrice());

            //最低消费补足
            master.setSupplementPrice(BigDecimal.ZERO);
            if (totalPrice.compareTo(master.getLowestPrice()) < 0) {
                master.setSupplementPrice(PriceHelper.subtract(master.getLowestPrice(), totalPrice));
                totalPrice = master.getLowestPrice();
            }

            //动态调价
            DiscountPrice discount = valuationTask.calcDiscount(driveMeter);
            master.setDynamicDiscount(BigDecimal.ZERO);
            master.setDynamicDiscountRate(0D);
            if (null != discount) {
                master.setDynamicDiscountRate(discount.getDiscount());
                if (discount.getDiscount() < 0 || discount.getDiscount() > 1) {
                    throw new RuntimeException(ERR_DISCOUNT_RATE_RANGE);
                }
                master.setDynamicDiscount(PriceHelper.min(discount.getDiscountMaxPrice(), BigDecimal.valueOf(1 - discount.getDiscount()).multiply(totalPrice)));
            }

            totalPrice = PriceHelper.subtract(totalPrice, master.getDynamicDiscount());
            master.setTotalPrice(totalPrice);

            return master.getTotalPrice();
        }).join();

        //设置计算结果
        PriceMeter priceMeter = new PriceMeter();
        priceMeter.setRulePrice(calcPriceFuture.join()).setRulePriceDetails(calcPriceDetailFuture.join())
                .setTagPrices(driveMeter.getRule().getTagPriceList()).setBasicPriceValue(price).setRuleId(driveMeter.getRule().getId());

        return priceMeter;
    }

}
