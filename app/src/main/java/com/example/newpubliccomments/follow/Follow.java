package com.example.newpubliccomments.follow;

import cn.bmob.v3.BmobObject;

public class Follow extends BmobObject {

    private String phone;
    private String follow;
    private String synopsisid;

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFollow() {
        return follow;
    }
    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getSynopsisId() {
        return synopsisid;
    }
    public void setSynopsisId(String synopsisid) {
        this.synopsisid = synopsisid;
    }

}
