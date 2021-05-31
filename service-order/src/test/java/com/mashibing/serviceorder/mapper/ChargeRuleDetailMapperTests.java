package com.mashibing.serviceorder.mapper;

import com.mashibing.internalcommon.entity.serviceorder.ChargeRuleDetail;
import com.mashibing.serviceorder.config.ServicesConfig;
import com.mashibing.serviceorder.dao.ChargeRuleDetailMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * description  计费规则时间段表 数据层测试 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/29 0:10
 **/
@SpringBootTest
public class ChargeRuleDetailMapperTests {

    @Autowired
    private ChargeRuleDetailMapper chargeRuleDetailMapper;

    @Autowired
    private ServicesConfig servicesConfig;

    @Test
    public void testInsert() {
        ChargeRuleDetail detail = ChargeRuleDetail.builder()
                .ruleId(1)
                .start(1)
                .end(2)
                .perMinutePrice(new BigDecimal("10"))
                .perKiloPrice(new BigDecimal("20"))
                .isDelete(1)
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        int success = chargeRuleDetailMapper.insert(detail);
        System.out.println("ID: " + detail.getId());
    }

    @Test
    public void testServicesConfig() {

        System.out.println(servicesConfig.address);
    }
}
