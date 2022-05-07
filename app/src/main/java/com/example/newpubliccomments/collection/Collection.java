package com.example.newpubliccomments.collection;

import cn.bmob.v3.BmobObject;

public class Collection extends BmobObject {

    private String phone;
    private String colle;
    private String businessid;

    public String getCollection() {
        return colle;
    }
    public void setCollection(String collection) {
        this.colle = collection;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessid() {
        return businessid;
    }
    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

}
