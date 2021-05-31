package com.mashibing.serviceorder.dao;

import com.mashibing.internalcommon.entity.serviceorder.Channel;
import org.apache.ibatis.annotations.Mapper;

/**
 * description  渠道管理表数据层 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/27 13:45
 **/
@Mapper
public interface ChannelMapper {

    public int deleteByPrimaryKey(Integer id);

    public int insert(Channel record);

    public int insertSelective(Channel record);

    public int updateByPrimaryKey(Channel record);

    public int updateByPrimaryKeySelective(Channel record);

    public Channel selectByPrimaryKey(Integer id);
}
