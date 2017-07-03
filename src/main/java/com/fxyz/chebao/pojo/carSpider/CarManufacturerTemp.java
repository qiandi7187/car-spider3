package com.fxyz.chebao.pojo.carSpider;

public class CarManufacturerTemp {
    private Integer id;

    private Integer brandId;

    private String name;

    private String orl;

    private Integer interId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrl() {
        return orl;
    }

    public void setOrl(String orl) {
        this.orl = orl == null ? null : orl.trim();
    }

    public Integer getInterId() {
        return interId;
    }

    public void setInterId(Integer interId) {
        this.interId = interId;
    }
}