package com.fxyz.chebao.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Group implements Serializable{
    //便于生成固定格式的json向腾讯接口传送数据
    @JSONField(name = "GroupId")
    private String groupid;
    @JSONField(name = "Owner_Account")
    private String ownerAccount;
    @JSONField(name = "Type")
    private String type;
    @JSONField(name = "Name")
    private String name;
    @JSONField(name = "Introduction")
    private String introduction;
    @JSONField(name = "Notification")
    private String notification;
    @JSONField(name = "Faceurl")
    private String faceurl;
    @JSONField(name ="MaxMemberCount")
    private Integer maxMemberCount;
    @JSONField(name = "ApplyJoinOption")
    private String applyJoinOption;
    @JSONField(serialize = false)
    private Date createTime;
    @JSONField(serialize = false)
    private Date updateTime;
    @JSONField(serialize = false)
    private Integer valid;
    private Integer MemberNum;

    public Integer getMemberNum() {
        return MemberNum;
    }

    public void setMemberNum(Integer memberNum) {
        MemberNum = memberNum;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount == null ? null : ownerAccount.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification == null ? null : notification.trim();
    }

    public String getFaceurl() {
        return faceurl;
    }

    public void setFaceurl(String faceurl) {
        this.faceurl = faceurl == null ? null : faceurl.trim();
    }

    public Integer getMaxMemberCount() {
        return maxMemberCount;
    }

    public void setMaxMemberCount(Integer maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
    }

    public String getApplyJoinOption() {
        return applyJoinOption;
    }

    public void setApplyJoinOption(String applyJoinOption) {
        this.applyJoinOption = applyJoinOption == null ? null : applyJoinOption.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
}