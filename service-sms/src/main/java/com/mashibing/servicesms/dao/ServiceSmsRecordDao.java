package com.mashibing.servicesms.dao;

import com.mashibing.servicesms.entity.ServiceSmsRecord;
import org.apache.ibatis.annotations.Mapper;

/***********************
 * @Description: 短信发送记录 数据层 <BR>
 * @author: zhao.song
 * @since: 2021/5/13 14:59
 * @version: 1.0
 ***********************/
@Mapper
public interface ServiceSmsRecordDao {

    int insert(ServiceSmsRecord smsRecord);

    int insertSelective(ServiceSmsRecord smsRecord);

    ServiceSmsRecord selectByPrimaryKey(Integer id);
}
