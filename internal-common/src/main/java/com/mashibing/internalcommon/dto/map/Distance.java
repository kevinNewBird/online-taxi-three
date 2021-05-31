package com.mashibing.internalcommon.dto.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 路程 (车辆真实的行驶记录, 行驶时间=结束时间-开始时间)<BR>
 * @author: zhao.song
 * @since: 2021/5/23 23:18
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Distance {

    private Double distance;
}
