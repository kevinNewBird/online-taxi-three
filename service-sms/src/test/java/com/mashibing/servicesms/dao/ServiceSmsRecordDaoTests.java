package com.mashibing.servicesms.dao;

import com.mashibing.servicesms.entity.ServiceSmsRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/***********************
 * @Description: 模板数据层测试 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 23:26
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@SpringBootTest
public class ServiceSmsRecordDaoTests {

    @Autowired
    private ServiceSmsRecordDao recordDao;

//    @Autowired
//    private ServiceSmsTemplateCustomDao templateCustomDao;

    @Test
    public void testSelectByPrimaryKey() {
        System.out.println(recordDao.selectByPrimaryKey(5));
    }

    @Test
    public void testInsertSelective() {
        ServiceSmsRecord record = ServiceSmsRecord.builder().smsContent("111111").createTime(new Date())
                .phoneNumber("111111").updateTime(new Date()).sendTime(new Date()).sendFlag(true).build();
        System.out.println(recordDao.insertSelective(record));
    }

    @Test
    public void testInsert() {
        ServiceSmsRecord record = ServiceSmsRecord.builder()
                .smsContent("22222")
                .createTime(new Date())
                .phoneNumber("222222")
                .updateTime(new Date())
                .sendTime(new Date())
                .sendFlag(true)
//                .sendNumber(1)
                .operator("")
                .build();
        System.out.println(recordDao.insert(record));
    }
}
