package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.City;
import org.apache.ibatis.annotations.Mapper;

/**
 * description  城市数据 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/26 21:29
 **/
@Mapper
public interface CityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    City selectByCityCode(String cityCode);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

}
