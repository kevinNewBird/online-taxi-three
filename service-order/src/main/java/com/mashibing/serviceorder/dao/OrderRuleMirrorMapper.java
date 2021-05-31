package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.OrderRuleMirror;
import org.apache.ibatis.annotations.Mapper;

/**
 * description  订单计费规则镜像 数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 15:25
 **/
@Mapper
public interface OrderRuleMirrorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderRuleMirror record);

    int insertSelective(OrderRuleMirror record);

    OrderRuleMirror selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRuleMirror record);

    int updateByPrimaryKeyWithBLOBs(OrderRuleMirror record);

    int updateByPrimaryKey(OrderRuleMirror record);
}
