package com.fxyz.chebao.pojo.carSpider;

public class CarModelTemp {
    private Integer id;

    private Integer manuId;

    private String imgurl;

    private String price;

    private Integer orl;

    private String state;

    private String name;

    private Integer interId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManuId() {
        return manuId;
    }

    public void setManuId(Integer manuId) {
        this.manuId = manuId;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public Integer getOrl() {
        return orl;
    }

    public void setOrl(Integer orl) {
        this.orl = orl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getInterId() {
        return interId;
    }

    public void setInterId(Integer interId) {
        this.interId = interId;
    }
}