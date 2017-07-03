package com.fxyz.chebao.service;

import com.fxyz.chebao.pojo.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */
public interface IGroupService {

    int addGroup(Group group) throws Exception;
    int deleteByPrimaryKey(Group group) throws Exception;
    //JSONObject updateByPrimaryKeySelective(Group group) throws Exception;

    /**
     * 根据类型查询群组
     * @param type 群组类型
     * @return
     */
    List<Group> findGroupsByType(String type) throws Exception;



}
