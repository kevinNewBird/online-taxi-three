package com.mashibing.servicesms.dao;

import com.mashibing.servicesms.entity.ServiceSmsTemplate;
import org.apache.ibatis.annotations.Mapper;

/***********************
 * @Description: 模板类 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 22:30
 * @version: 1.0
 ***********************/
@Mapper
public interface ServiceSmsTemplateDao {

    int deleteByPrimaryKey(Integer id);

    int insert(ServiceSmsTemplate record);

    int insertSelective(ServiceSmsTemplate record);

    ServiceSmsTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceSmsTemplate record);

    int updateByPrimaryKey(ServiceSmsTemplate record);

    ServiceSmsTemplate selectByTemplateId(String templateId);
}
