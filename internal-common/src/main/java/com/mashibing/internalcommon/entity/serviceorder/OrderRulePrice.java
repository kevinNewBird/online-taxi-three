package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class OrderRulePrice {

    private Integer id;

    private Integer orderId;

    private String category;

    private BigDecimal totalPrice;

    private Double totalDistance;

    private Double totalTime;

    private String cityCode;

    private String cityName;

    private Integer serviceTypeId;

    private String serviceTypeName;

    private Integer channelId;

    private String channelName;

    private Integer carLevelId;

    private String carLevelName;

    private BigDecimal basePrice;

    private Double baseKilo;

    private Double baseMinute;

    private BigDecimal lowestPrice;

    private Date nightStart;

    private Date nightEnd;

    private BigDecimal nightPerKiloPrice;

    private BigDecimal nightPerMinutePrice;

    private Double nightDistance;

    private Double nightTime;

    private BigDecimal nightPrice;

    private Double beyondStartKilo;

    private BigDecimal beyondPerKiloPrice;

    private Double beyondDistance;

    private BigDecimal beyondPrice;

    private BigDecimal perKiloPrice;

    private Double path;

    private BigDecimal pathPrice;

    private BigDecimal perMinutePrice;

    private Double duration;

    private BigDecimal durationPrice;

    private Double restDuration;

    private BigDecimal restDurationPrice;

    private Double restDistance;

    private BigDecimal restDistancePrice;

    private BigDecimal roadPrice;

    private BigDecimal parkingPrice;

    private BigDecimal otherPrice;

    private Double dynamicDiscountRate;

    private BigDecimal dynamicDiscount;

    private BigDecimal supplementPrice;

    private Date createTime;

    private Date updateTime;
}