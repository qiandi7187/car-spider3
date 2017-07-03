package com.fxyz.chebao.pojo.carSpider;

public class CarTypeTemp {
    private Integer id;

    private Integer modelId;

    private String name;

    private String groupName;

    private String drivingMode;

    private String transmission;

    private String guidePrice;

    private String dealerPrice;

    private String secondPrice;

    private String tax;

    private String state;

    private Integer orl;

    private Integer interId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getDrivingMode() {
        return drivingMode;
    }

    public void setDrivingMode(String drivingMode) {
        this.drivingMode = drivingMode == null ? null : drivingMode.trim();
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission == null ? null : transmission.trim();
    }

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice == null ? null : guidePrice.trim();
    }

    public String getDealerPrice() {
        return dealerPrice;
    }

    public void setDealerPrice(String dealerPrice) {
        this.dealerPrice = dealerPrice == null ? null : dealerPrice.trim();
    }

    public String getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(String secondPrice) {
        this.secondPrice = secondPrice == null ? null : secondPrice.trim();
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax == null ? null : tax.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getOrl() {
        return orl;
    }

    public void setOrl(Integer orl) {
        this.orl = orl;
    }

    public Integer getInterId() {
        return interId;
    }

    public void setInterId(Integer interId) {
        this.interId = interId;
    }
}