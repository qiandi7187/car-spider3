package com.fxyz.chebao.dao.mongodb;

import com.fxyz.chebao.pojo.UserLocation;
import org.springframework.data.geo.GeoResults;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface IUserLocationDao {
    public void saveUser(UserLocation userLocation);
    GeoResults<UserLocation> findByRange(double latitude, double longitude);
}
