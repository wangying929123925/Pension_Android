package com.warehouse.arch_demo.bean;

import java.util.List;

public class FamilyDto {

    /**
     * searchValue : null
     * createBy : null
     * createTime : 2020-11-25 19:26:36
     * updateBy : null
     * updateTime : 2020-12-02 17:56:59
     * remark : null
     * beginTime : null
     * endTime : null
     * params : {}
     * id : 16
     * identityId : 1
     * userName : 张苞
     * country : 1
     * province : 1
     * city : 1
     * region : 1
     * address : 1
     * longitude : 1
     * latitude : 1
     * phoneNumber : 1
     * email : null
     * healthCare : true
     * orgId : 1
     * healthState : false
     * residueMoney : 1
     * photo : http://10.112.196.254:8006//profile//2020-12/e0a9e14612def1e908969f4006e0cb07.PNG
     * createdBy : 1
     * updatedBy : 1
     */

    private String searchValue;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    private String beginTime;
    private Object endTime;
    private ParamsBean params;
    private String id;
    private String identityId;
    private String userName;
    private String country;
    private String province;
    private String city;
    private String region;
    private String address;
    private int longitude;
    private int latitude;
    private String phoneNumber;
    private Object email;
    private boolean healthCare;
    private String orgId;
    private boolean healthState;
    private String residueMoney;
    private String photo;
    private String createdBy;
    private String updatedBy;
    private List<FamilyDto> rows;

    public List<FamilyDto> getRows() {
        return rows;
    }

    public void setRows(List<FamilyDto> rows) {
        this.rows = rows;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public boolean isHealthCare() {
        return healthCare;
    }

    public void setHealthCare(boolean healthCare) {
        this.healthCare = healthCare;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public boolean isHealthState() {
        return healthState;
    }

    public void setHealthState(boolean healthState) {
        this.healthState = healthState;
    }

    public String getResidueMoney() {
        return residueMoney;
    }

    public void setResidueMoney(String residueMoney) {
        this.residueMoney = residueMoney;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    private class ParamsBean {
    }
}

