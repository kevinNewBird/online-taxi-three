package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRulePriceDetail {

    private Integer id;

    private Integer orderId;

    private String category;

    private Integer startHour;

    private Integer endHour;

    private BigDecimal perKiloPrice;

    private BigDecimal perMinutePrice;

    private Double duration;

    private BigDecimal timePrice;

    private Double distance;

    private BigDecimal distancePrice;

    private Date createTime;

    private Date updateTime;

}