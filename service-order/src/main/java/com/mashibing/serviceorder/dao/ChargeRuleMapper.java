package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.ChargeRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description  计价规则 数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 23:20
 **/
@Mapper
public interface ChargeRuleMapper {

    public int deleteByPrimaryKey(Integer id);

    public int insert(ChargeRule record);

    int insertSelective(ChargeRule record);

    int updateByPrimaryKey(ChargeRule record);

    int updateByPrimaryKeySelective(ChargeRule record);

    ChargeRule selectByPrimaryKey(Integer id);

    List<ChargeRule> selectByCondition(ChargeRule chargeRule);

    List<ChargeRule> selectByKeyRule(@Param("cityCode") String cityCode, @Param("serviceTypeId") Integer serviceTypeId
            , @Param("channelId") Integer channelId, @Param("carLevelId") Integer carLevelId);
}
