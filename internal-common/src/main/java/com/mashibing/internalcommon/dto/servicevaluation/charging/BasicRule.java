package com.mashibing.internalcommon.dto.servicevaluation.charging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * description  基础计费规则 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 15:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicRule {

    /**
     * 基础价
     */
    private BigDecimal lowestPrice;

    /**
     * 起步价
     */
    private BigDecimal basePrice;

    /**
     * 包含公里数(公里)
     */
    private Double baseKilos;

    /**
     * 包含时长数(分钟)
     */
    private Double baseMinutes;


    /**
     * 是否采用基础套餐的计费规则
     *
     * @return 采用为true, 否则为false
     */
    //在json序列化时将pojo中的一些属性忽略掉，标记在属性或者方法上，返回的json数据即不包含该属性。
    @JsonIgnore
    public boolean isBasicCharging() {
        return !(ObjectUtils.nullSafeEquals(baseKilos, 0d) && ObjectUtils.nullSafeEquals(baseMinutes, 0d));
    }

}
