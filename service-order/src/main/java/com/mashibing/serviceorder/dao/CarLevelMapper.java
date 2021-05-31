package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.CarLevel;
import org.apache.ibatis.annotations.Mapper;

/**
 * description  车辆级别列表 数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 14:36
 **/
@Mapper
public interface CarLevelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CarLevel record);

    int insertSelective(CarLevel record);

    int updateByPrimaryKey(CarLevel record);

    int updateByPrimaryKeySelective(CarLevel record);

    CarLevel selectByPrimaryKey(Integer id);
}
