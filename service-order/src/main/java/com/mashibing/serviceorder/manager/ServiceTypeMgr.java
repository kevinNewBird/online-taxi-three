package com.mashibing.serviceorder.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mashibing.internalcommon.entity.serviceorder.ServiceType;
import com.mashibing.serviceorder.dao.ServiceTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description  服务类型 管理层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/28 0:21
 **/
@Component
@Slf4j
public class ServiceTypeMgr {

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;


    public PageInfo<ServiceType> findByPage(int curPage, int pageSize) {
        // PageHelper分页的方法,传入得时当前页和每页展示条数
        PageHelper.startPage(curPage, pageSize);
        List<ServiceType> list = serviceTypeMapper.findByPage();

        return new PageInfo<>(list);
    }
}
