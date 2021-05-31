package com.mashibing.internalcommon.dto.serviceorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description  基础信息 传输对象 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 15:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseInfoDto {

    private String cityName;

    private String channelName;

    private String serviceTypeName;

    private String carLevelName;
}
