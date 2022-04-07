package com.example.newpubliccomments.order;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {

    private String price;
    private String avatar;
    private String commodityname;
    private String businessname;
    private String number;
    private String state;
    private String businessid;
    private String commodityid;

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCommodityname() {
        return commodityname;
    }
    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }

    public String getBusinessname() {
        return businessname;
    }
    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getBusinessid() {
        return businessid;
    }
    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

    public String getCommodityid() {
        return commodityid;
    }
    public void setCommodityid(String commodityid) {
        this.commodityid = commodityid;
    }

}
