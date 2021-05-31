package com.mashibing.internalcommon.dto.serviceorder.request;

import com.mashibing.internalcommon.entity.serviceorder.Order;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/***********************
 * @Description: 订单请求 <BR>
 * @author: zhao.song
 * @since: 2021/5/24 11:02
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "childBuilder")
public class OrderDtoRequest extends Order {

    private Integer orderId;

    private String orderPrice;

    /**
     * 城市编码
     */
    private String cityCode;

    private String cityName;

    private Integer serviceTypeId;

    private String serviceTypeName;

    private Integer channelId;

    private String channelName;

    private Integer carLevelId;

    private String carLevelName;

    /**
     * 用车时长(分)
     */
    private Integer userCarTime;

    /**
     * 过路费
     */
    private BigDecimal roadPrice;

    /**
     * 停车费
     */
    private BigDecimal parkingPrice;

    /**
     * 其它费用
     */
    private BigDecimal otherPrice;

    private Integer driverIdNow;

    private String operator;

    private Integer reasonType;

    /**
     * 改派内容
     */
    private String reasonText;

    /**
     * 订单ids
     */
    private String orderIds;

    private Integer updateType;

    /**
     * 司机经度
     */
    private String driverLongitude;

    /**
     * 司机纬度
     */
    private String driverLatitude;
}
