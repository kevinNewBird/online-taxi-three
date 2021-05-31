package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * description  主键规则 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 15:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyRule {

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名
     */
    private String cityName;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 渠道名
     */
    private String channelName;

    /**
     * 服务类型id
     */
    private Integer serviceTypeId;

    /**
     * 服务类型名
     */
    private String serviceTypeName;

    /**
     * 车辆等级id
     */
    private Integer carLevelId;

    /**
     * 车辆等级名
     */
    private String carLevelName;


}
