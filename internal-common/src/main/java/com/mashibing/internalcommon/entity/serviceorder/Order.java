package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/***********************
 * @Description: 订单类(即用作数据库的实体也作为顶层) <BR>
 * @author: zhao.song
 * @since: 2021/5/24 9:40
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Order implements Serializable {

    private static final long serialVersionUID = 80840001L;

    private Integer id;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 乘客Id
     */
    private Integer passengerInfoId;

    /**
     * 乘客电话
     */
    private String passengerPhone;

    /**
     * 乘客设备号
     */
    private String deviceCode;

    /**
     * 司机id
     */
    private Integer driverId;

    /**
     * 司机状态
     */
    private Integer driverStatus;

    /**
     * 司机电话
     */
    private String driverPhone;

    /**
     * 车辆id
     */
    private Integer carId;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 用户位置经度
     */
    private String userLongitude;

    /**
     * 用户位置纬度
     */
    private String userLatitude;

    /**
     * 乘客下单起点经度
     */
    private String startLongitude;

    /**
     * 乘客下班起点纬度
     */
    private String startLatitude;

    /**
     * 起点地址名称
     */
    private String startAddress;

    /**
     * 终点地址名称
     */
    private String endAddress;

    /**
     * 乘客下单时间
     */
    private Date startTime;

    /**
     * 乘客订单开始时间
     */
    private Date orderStartTime;

    /**
     * 乘客下单终点经度
     */
    private String endLongitude;

    /**
     * 乘客下单终点纬度
     */
    private String endLatitude;

    /**
     * 司机抢单时间
     */
    private Date driverGrabTime;

    /**
     * 司机去接乘客出发时间
     */
    private Date driverStartTime;

    /**
     * 司机到达时间
     */
    private Date driverArrivedTime;

    /**
     * 去接乘客时间
     */
    private Date pickUpPassengerTime;

    /**
     * 去接乘客经度
     */
    private String pickUpPassengerLongitude;

    /**
     * 去接乘客纬度
     */
    private String pickUpPassengerLatitude;

    /**
     * 去接乘客地点
     */
    private String pickUpPassengerAddress;

    /**
     * 接到乘客时间
     */
    private Date receivePassengerTime;

    /**
     * 接到乘客经度
     */
    private String receivePassengerLongitude;

    /**
     * 接到乘客纬度
     */
    private String receivePassengerLatitude;

    /**
     * 接到乘客地址
     */
    private String receivePassengerAddress;

    /**
     * 乘客下车时间
     */
    private Date passengerGetOffTime;

    /**
     * 乘客下车经度
     */
    private String passengerGetOffLongitude;

    /**
     * 乘客下车纬度
     */
    private String passengerGetOffLatitude;

    /**
     * 乘客下车地址
     */
    private String passengerGetOffAddress;

    /**
     * 他人姓名 （乘车人）
     */
    private String otherName;

    /**
     * 他人电话　(乘车人）
     */
    private String otherPhone;

    /**
     * 订单类型：1：自己叫车，2：他人叫车
     */
    private String orderType;

    /**
     * 叫车订单类型，1：实时订单，2：预约订单，3：接机单，4：送机单，5：日租，6：半日租
     */
    private Integer serviceType;

    /**
     * 订单渠道 1.自有订单 2.高德 3.飞猪
     */
    private Integer orderChannel;

    /**
     * 订单状态 0: 订单预估 1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程
     * 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9.乘客取消订单
     */
    private Integer status;

    /**
     * 1：儿童用车 2：女性用车
     */
    private String userFeature;

    /**
     * 商户交易id
     */
    private String transactionId;

    /**
     * 订单状态 0: 订单预估 1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程
     * 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9.订单取消
     */
    private String mappingId;

    /**
     * 关联号码
     */
    private String mappingNumber;

    private String otherMappingId;

    private String otherMappingNumber;

    /**
     * 商户id
     */
    private String merchantId;

    private Integer isEvaluateDriver;

    /**
     * 乘客是否评价，0：未评价，1：已评价
     */
    private Integer isEvaluate;

    /**
     * 发票状态：1：未开票，2：申请开票，3：开票中，4：已开票,5：退回,
     */
    private Integer invoiceType;

    /**
     * 通知客服状态0，未通知  1,  已通知
     */
    private Integer isAnnotate;

    /**
     * 设备来源 1: ios   2:android  3.other
     */
    private String source;

    /**
     * 是否使用优惠券 0:未使用  1:使用
     */
    private Integer useCoupon;

    /**
     * 取消原因类型id
     */
    private Integer cancelOrderType;

    /**
     * 1:余额 2.微信 3.支付宝
     */
    private Integer payType;

    /**
     * 是否已支付 0：未支付  1：已支付
     */
    private Integer isPaid;

    /**
     * 是否取消  0：未取消   1：已取消
     */
    private Integer isCancel;

    /**
     * 调帐状态  0：未调帐  1:调账中 2.调账完毕
     */
    private Integer isAdjust;

    /**
     * 是否疑义订单 0：否  1：是
     */
    private Integer isDissent;

    /**
     * 是否人工派单0 否 1 原来无司机, 人工派 2原来有司机，改派
     */
    private Integer isManual;

    /**
     * 是否是顺风单0否 1是
     */
    private Integer isFollowing;

    /**
     * 是否是假成功单0 否 1是
     */
    private Integer isFakeSuccess;

    /**
     * 备忘录
     */
    private String memo;

    private Integer isUseRisk;

    private Date createTime;

    private Date updateTime;
}
