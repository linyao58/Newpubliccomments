package com.example.newpubliccomments.NewMessage;

import cn.bmob.v3.BmobObject;

public class Messages extends BmobObject {

    private String avatar;
    private String name;
    private String message;
    private String myuserid;
    private String evaluateid;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMyuserid() {
        return myuserid;
    }
    public void setMyuserid(String myuserid) {
        this.myuserid = myuserid;
    }

    public String getEvaluateid() {
        return evaluateid;
    }
    public void setEvaluateid(String evaluateid) {
        this.evaluateid = evaluateid;
    }

}
