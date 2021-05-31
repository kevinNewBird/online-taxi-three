package com.mashibing.servicevaluation.task;

import com.mashibing.internalcommon.constant.ChargingCategoryEnum;
import com.mashibing.internalcommon.dto.servicevaluation.charging.Rule;
import com.mashibing.internalcommon.dto.servicevaluation.discount.DiscountPrice;
import com.mashibing.internalcommon.entity.serviceorder.OrderRulePrice;
import com.mashibing.internalcommon.entity.serviceorder.OrderRulePriceDetail;
import com.mashibing.internalcommon.util.PriceHelper;
import com.mashibing.internalcommon.util.TimeSlice;
import com.mashibing.internalcommon.util.UnitConverter;
import com.mashibing.servicevaluation.dto.DriveMeter;
import com.mashibing.servicevaluation.dto.PriceMeter;
import com.mashibing.servicevaluation.dto.TimeMeter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * description  计价服务异步任务 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/31 17:42
 **/
@Component
@Slf4j
public class ValuationTask {

    /**
     * description: 计算基本价格 <BR>
     *
     * @param driveMeter: 行驶信息
     * @return {@link CompletableFuture<OrderRulePrice>}
     * @author zhao.song   2021/5/31 17:57
     */
    public CompletableFuture<OrderRulePrice> calcMasterPrice(DriveMeter driveMeter) {
        OrderRulePrice orderRulePrice = new OrderRulePrice();

        Rule rule = driveMeter.getRule();

        // key信息
        orderRulePrice.setOrderId(Objects.isNull(driveMeter.getOrder()) ? driveMeter.getCurrentPriceRequestDto().getOrderId() : driveMeter.getOrder().getId())
                .setCategory(String.valueOf(driveMeter.getChargingCategoryEnum().getCode()))
                .setTotalDistance(UnitConverter.meterToKilo(driveMeter.getTotalDistance()))
                .setTotalTime(UnitConverter.secondToMinute(driveMeter.getTotalTime()))
                .setCityCode(rule.getKeyRule().getCityCode())
                .setCityName(rule.getKeyRule().getCityName())
                .setServiceTypeId(rule.getKeyRule().getServiceTypeId())
                .setServiceTypeName(rule.getKeyRule().getServiceTypeName())
                .setChannelId(rule.getKeyRule().getChannelId())
                .setChannelName(rule.getKeyRule().getChannelName())
                .setCarLevelId(rule.getKeyRule().getCarLevelId())
                .setCarLevelName(rule.getKeyRule().getCarLevelName());

        // 基础价格
        orderRulePrice.setBasePrice(rule.getBasicRule().getBasePrice())
                .setBaseKilo(rule.getBasicRule().getBaseKilos())
                .setBaseMinute(rule.getBasicRule().getBaseMinutes())
                .setLowestPrice(rule.getBasicRule().getLowestPrice())
                .setPerKiloPrice(rule.getPriceRule().getPerKiloPrice())
                .setPerMinutePrice(rule.getPriceRule().getPerMinutePrice());

        //夜间价格
        orderRulePrice.setNightTime(0D);
        orderRulePrice.setNightDistance(0D);
        orderRulePrice.setNightPrice(BigDecimal.ZERO);
        if (rule.getNightRule().getStart() != null && rule.getNightRule().getEnd() != null) {
            orderRulePrice.setNightStart(rule.getNightRule().getStart());
            orderRulePrice.setNightEnd(rule.getNightRule().getEnd());
            orderRulePrice.setNightPerKiloPrice(rule.getNightRule().getNightPerKiloPrice());
            orderRulePrice.setNightPerMinutePrice(rule.getNightRule().getNightPerMinutePrice());

            //计算夜间价格
            TimeMeter.TimePriceUnit unit = generateTimePriceUnit(driveMeter);
            unit.setStart(UnitConverter.dateToLocalTime(orderRulePrice.getNightStart()));
            unit.setEnd(UnitConverter.dateToLocalTime(orderRulePrice.getNightEnd()));
            unit.setPerMeterPrice(UnitConverter.kiloToMeterPrice(orderRulePrice.getNightPerKiloPrice()));
            unit.setPerSecondPrice(UnitConverter.minuteToSecondPrice(orderRulePrice.getNightPerMinutePrice()));
            TimeMeter.TimePriceResult result = TimeMeter.measure(generateTimeSlice(driveMeter), unit);
            orderRulePrice.setNightTime(UnitConverter.secondToMinute(result.getDuration()));
            orderRulePrice.setNightDistance(UnitConverter.meterToKilo(result.getDistance()));
            orderRulePrice.setNightPrice(PriceHelper.add(result.getDistancePrice(), result.getTimePrice()));
        }

        //远途价格
        orderRulePrice.setBeyondStartKilo(rule.getBeyondRule().getBeyondStartKilo());
        orderRulePrice.setBeyondPerKiloPrice(rule.getBeyondRule().getBeyondPerKiloPrice());
        orderRulePrice.setBeyondDistance(PriceHelper.subtract(orderRulePrice.getTotalDistance(), orderRulePrice.getBeyondStartKilo()).doubleValue());
        orderRulePrice.setBeyondPrice(PriceHelper.multiply(orderRulePrice.getBeyondPerKiloPrice(), orderRulePrice.getBeyondDistance()));
        return new AsyncResult<>(orderRulePrice).completable();
    }


    public CompletableFuture<List<OrderRulePriceDetail>> calcDetailPrice(DriveMeter driveMeter) {
        return null;
    }

    public void calcOtherPrice(DriveMeter driveMeter, OrderRulePrice master, List<OrderRulePriceDetail> details) {

    }

    public DiscountPrice calcDiscount(DriveMeter driveMeter) {
        return null;
    }

    public BigDecimal calcTagPrice(DriveMeter driveMeter) {
        return null;
    }

    public void updateToDb(ChargingCategoryEnum forecast, PriceMeter priceMeter) {

    }


    /**
     * 行驶开始到结束的时间片
     *
     * @param driveMeter 行驶信息
     * @return 时间片
     */
    private TimeSlice generateTimeSlice(DriveMeter driveMeter) {
        TimeSlice totalSlice = new TimeSlice();
        totalSlice.setX(driveMeter.getStartDateTime());
        totalSlice.setY(totalSlice.getX().plusSeconds((long) Math.ceil(driveMeter.getTotalTime())));
        return totalSlice;
    }


    /**
     * 生成计算用参数
     *
     * @param driveMeter 行驶信息
     * @return 计算用参数实例
     */
    private TimeMeter.TimePriceUnit generateTimePriceUnit(DriveMeter driveMeter) {
        TimeMeter.TimePriceUnit unit = null;
        switch (driveMeter.getChargingCategoryEnum()) {
            case Forecast:
                BigDecimal speed = BigDecimal.valueOf(driveMeter.getTotalDistance()).divide(BigDecimal.valueOf(driveMeter.getTotalTime()), 5, RoundingMode.DOWN);
                unit = TimeMeter.TimePriceUnit.instanceByForecast(speed.doubleValue());
                break;
            case Settlement:
            case RealTime:
                int carId = driveMeter.getOrder() != null ? driveMeter.getOrder().getCarId() : driveMeter.getCurrentPriceRequestDto().getCarId();
                String cityCode = driveMeter.getRule().getKeyRule().getCityCode();
                unit = TimeMeter.TimePriceUnit.instanceBySettlement(driveMeter.getChargingCategoryEnum(), driveMeter.getRequestTask(), carId, cityCode);
                break;
            default:
                break;
        }
        return unit;
    }
}
