package com.mashibing.servicevaluation.dao;

import com.mashibing.internalcommon.entity.serviceorder.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * description  订单 数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 15:12
 **/
@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateByPrimaryKeySelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int batchUpdate(Map<String, Object> map);


}
