package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.PassengerInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * description  乘客信息 数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/30 14:43
 **/
@Mapper
public interface PassengerInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PassengerInfo record);

    int insertSelective(PassengerInfo record);

    int updateByPrimaryKey(PassengerInfo record);

    int updateByPrimaryKeySelective(PassengerInfo record);

    PassengerInfo selectByPrimaryKey(Integer id);

}
