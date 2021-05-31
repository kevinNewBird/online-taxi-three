package com.mashibing.serviceorder.mapper;

import com.github.pagehelper.PageInfo;
import com.mashibing.internalcommon.entity.serviceorder.ServiceType;
import com.mashibing.serviceorder.dao.ServiceTypeMapper;
import com.mashibing.serviceorder.manager.ServiceTypeMgr;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * description  服务类型数据层测试 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 11:04
 **/
@SpringBootTest
public class ServiceTypeMapperTests {

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    @Autowired
    private ServiceTypeMgr serviceTypeMgr;

    @Test
    public void testInsert() {
        ServiceType oInstance = ServiceType.builder()
                .serviceTypeName("多日租")
                .serviceTypeStatus(1)
                .isUse(1)
                .operatorId(3)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
//        ServiceType oInstance = new ServiceType();
//        oInstance.setServiceTypeName("全日租");
//        oInstance.setServiceTypeStatus(1);
//        oInstance.setIsUse(1);
//        oInstance.setOperatorId(2);
//        oInstance.setCreateTime(new Date());
//        oInstance.setUpdateTime(new Date());

        int pId = serviceTypeMapper.insert(oInstance);
        Integer pIdReal = oInstance.getId();
        System.out.println(pId);
        System.out.println(pIdReal);
    }


    @Test
    public void testFindByPage() {
        PageInfo<ServiceType> pageList = serviceTypeMgr.findByPage(1, 2);
        System.out.println(pageList);
    }
}

