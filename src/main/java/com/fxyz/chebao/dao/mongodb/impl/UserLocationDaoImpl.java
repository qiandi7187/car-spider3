package com.fxyz.chebao.dao.mongodb.impl;

import com.fxyz.chebao.dao.mongodb.IUserLocationDao;
import com.fxyz.chebao.pojo.UserLocation;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.springframework.data.geo.Metrics.KILOMETERS;

/**
 * Created by Administrator on 2017/5/19.
 */
@Component
public class UserLocationDaoImpl implements IUserLocationDao {
    @Override
    public GeoResults<UserLocation> findByRange(double latitude, double longitude) {
        Point point = new Point(longitude,latitude);
        System.out.println(latitude+","+longitude);
        Distance distance = new Distance(5, KILOMETERS);
        NearQuery nearQuery = NearQuery.near(point, KILOMETERS).maxDistance(distance).num(50);
        GeoResults<UserLocation> results=mongoTemplate.geoNear(nearQuery,UserLocation.class,"user_location");
        return results;
    }

    @Resource
    private MongoTemplate mongoTemplate;
    @Override
    public void saveUser(UserLocation userLocation) {

        //UserLocation currentUserLocation = mongoTemplate.findOne(new Query(Criteria.where("uid").is(userLocation.getUid())),UserLocation.class,"user_location");
        //if(currentUserLocation==null) mongoTemplate.insert(userLocation,"user_location");
        mongoTemplate.upsert(new Query(Criteria.where("uid").is(userLocation.getUid())),new Update().set("location", userLocation.getLocation()).set("startTime",userLocation.getStartTime()),"user_location");
        //mongoTemplate.save(userLocation,"user_location");

    }
}
