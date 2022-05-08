package com.example.newpubliccomments.live;

import cn.bmob.v3.BmobObject;

public class Live extends BmobObject {

    private String phone;
    private String live;
    private String synopsisid;

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLive() {
        return live;
    }
    public void setLive(String live) {
        this.live = live;
    }

    public String getSynopsisId() {
        return synopsisid;
    }
    public void setSynopsisId(String synopsisid) {
        this.synopsisid = synopsisid;
    }

}
