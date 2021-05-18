package com.mashibing.servicesms.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/***********************
 * @Description: 模板数据层测试 <BR>
 * @author: zhao.song
 * @since: 2021/5/12 23:26
 * @version: 1.0
 ***********************/
@SuppressWarnings("all")
@SpringBootTest
public class ServiceSmsTemplateDaoTests {

    @Autowired
    private ServiceSmsTemplateDao templateDao;

//    @Autowired
//    private ServiceSmsTemplateCustomDao templateCustomDao;

    @Test
    public void testSelectByPrimaryKey() {
        System.out.println(templateDao.selectByPrimaryKey(5));
    }
}
