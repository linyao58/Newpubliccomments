package com.example.newpubliccomments.commodity;

import cn.bmob.v3.BmobObject;

public class Commodity extends BmobObject {

    private String price;
    private String avatar;
    private String commodityname;
    private String businessname;
    private String classification;
    private String businessid;

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

    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getBusinessid() {
        return businessid;
    }
    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }


}
