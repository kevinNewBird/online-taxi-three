package com.mashibing.internalcommon.dto.map.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 路程请求 <BR>
 * @author: zhao.song
 * @since: 2021/5/26 8:40
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistanceRequest {

    private String originLongitude;

    private String originLatitude;

    private String destinationLongitude;

    private String destinationLatitude;
}
