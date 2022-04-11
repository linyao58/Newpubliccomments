package com.example.newpubliccomments.complaint;

import cn.bmob.v3.BmobObject;

public class Complaint extends BmobObject {

    private String businessid;

    private String businessname;

    private String content;

    public String getBusinessid() {
        return businessid;
    }
    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

    public String getBusinessname() {
        return businessname;
    }
    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
