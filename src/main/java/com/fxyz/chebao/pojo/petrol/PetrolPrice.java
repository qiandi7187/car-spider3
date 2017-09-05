package com.fxyz.chebao.pojo.petrol;

import java.util.Date;

public class PetrolPrice {
    private Integer id;

    private String city;

    private String price90;

    private String price93;

    private String price97;

    private String price0;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getPrice90() {
        return price90;
    }

    public void setPrice90(String price90) {
        this.price90 = price90 == null ? null : price90.trim();
    }

    public String getPrice93() {
        return price93;
    }

    public void setPrice93(String price93) {
        this.price93 = price93 == null ? null : price93.trim();
    }

    public String getPrice97() {
        return price97;
    }

    public void setPrice97(String price97) {
        this.price97 = price97 == null ? null : price97.trim();
    }

    public String getPrice0() {
        return price0;
    }

    public void setPrice0(String price0) {
        this.price0 = price0 == null ? null : price0.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}