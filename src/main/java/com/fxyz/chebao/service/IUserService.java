package com.fxyz.chebao.service;

import com.fxyz.chebao.pojo.User;
import com.fxyz.chebao.pojo.UserLocation;
import org.springframework.data.geo.GeoResults;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */
public interface IUserService {
    User selectByPrimaryKey(Integer id);
    int insertSelective(User record) throws Exception;
    int login(User user);
    int updateByUsernameSelective(User user);
    int updateByPrimaryKeySelective(User record);
    int saveLocation(UserLocation userLocation);

    /**
     *
     * @param latitude 纬度
     * @param longitude 经度
     * @return
     */
    GeoResults<UserLocation> findByRange(double latitude, double longitude);
    List<User> findUserByCondition(User user);
}
