package com.fxyz.chebao.pojo;

/**
 * Created by Administrator on 2017/5/19.
 */
public class UserLocation {
    private String uid;
    private Double[] location;
    private String startTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public Double[] getLocation() {
        return location;
    }

    public void setLocation(Double[] location) {
        this.location = location;
    }
}
