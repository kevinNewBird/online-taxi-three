package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.ServiceType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description  服务类型数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/26 21:51
 **/
@Mapper
public interface ServiceTypeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ServiceType record);

    int insertSelective(ServiceType record);

    int updateByPrimaryKey(ServiceType record);

    int updateByPrimaryKeySelective(ServiceType record);

    ServiceType selectByPrimaryKey(Integer id);

    List<ServiceType> findByPage();
}
