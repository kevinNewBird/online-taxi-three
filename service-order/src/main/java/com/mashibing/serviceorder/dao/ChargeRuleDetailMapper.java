package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.ChargeRuleDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description  计费规则时间段 数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 18:27
 **/
@Mapper
public interface ChargeRuleDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ChargeRuleDetail record);

    int insertSelective(ChargeRuleDetail record);

    int updateByPrimaryKey(ChargeRuleDetail record);

    int updateByPrimaryKeySelective(ChargeRuleDetail record);

    List<ChargeRuleDetail> selectByRuleId(Integer ruleId);
}
