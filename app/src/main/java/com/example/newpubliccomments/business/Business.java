package com.example.newpubliccomments.business;

import cn.bmob.v3.BmobObject;

public class Business extends BmobObject {

    private String license;
    private String address;
    private String bavatar;
    private String title;
    private String grade;
    private String introduce;
    private Boolean collection;

    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getBavatar() {
        return bavatar;
    }
    public void setBavatar(String bavatar) {
        this.bavatar = bavatar;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Boolean getCollection() {
        return collection;
    }
    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

}
