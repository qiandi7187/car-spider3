package com.fxyz.chebao.pojo.carSpider;

public class CarBrandTemp {
    private Integer id;

    private String letter;

    private String name;

    private String imgurl;

    private String orl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter == null ? null : letter.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getOrl() {
        return orl;
    }

    public void setOrl(String orl) {
        this.orl = orl == null ? null : orl.trim();
    }
}