package com.fxyz.chebao.mapper;

import com.fxyz.chebao.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);
    User selectByUsername(String username);

    int updateByPrimaryKeySelective(User record);
    int updateByUsernameSelective(User record);

    int updateByPrimaryKey(User record);
    List<User> findUserByPage(User user);
    List<User> findUserByCondition(User user);
}