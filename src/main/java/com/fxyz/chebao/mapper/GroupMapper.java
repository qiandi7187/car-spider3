package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.Group;

import java.util.List;

public interface GroupMapper {
    int deleteByPrimaryKey(String groupid);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(String groupid);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    /**
     * 根据类型查找群组
     * @param type
     * @return
     */
    List<Group> findGroupsByType(String type);
}