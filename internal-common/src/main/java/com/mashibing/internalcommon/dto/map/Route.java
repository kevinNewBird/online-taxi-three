package com.mashibing.internalcommon.dto.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***********************
 * @Description: 路线 (系统生成的规划路线)<BR>
 * @author: zhao.song
 * @since: 2021/5/23 23:16
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route {

    /**
     * 行驶距离(米)
     */
    private Double distance;

    /**
     * 行驶时间
     */
    private Double duration;
}
