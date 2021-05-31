package com.mashibing.internalcommon.dto.servicevaluation.charging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description  计价规则 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 11:43
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rule {

    private Integer id;

    /**
     * 计费规则主键类
     */
    private KeyRule keyRule;
    /**
     * 基础计费
     */
    private BasicRule basicRule;

    /**
     * 计费方法
     */
    private PriceRule priceRule;

    /**
     * 远途服务费
     */
    private BeyondRule beyondRule;

    /**
     * 夜间服务费
     */
    private NightRule nightRule;

    /**
     * 标签费
     */
    private List<TagPrice> tagPriceList;

}
