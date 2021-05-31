package com.mashibing.internalcommon.entity.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * description  城市表  <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/26 21:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City implements Serializable {

    private Integer id;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市中心经纬度
     */
    private String cityLongitudeLatitude;

    /**
     * 下单风险上限值
     */
    private Integer orderRiskTop;

    /**
     * 是否开通 0未开通  1开通
     */
    private Integer cityStatus;

    /**
     * 操作人id
     */
    private Integer operatorId;

    private Date createTime;

    private Date updateTime;


}
