package com.example.newpubliccomments.signregister;

import cn.bmob.v3.BmobObject;

public class PublicMyUser extends BmobObject {

    private String name;
    private String phone;
    private String password;
    private String mailbox;
    private String avatar;
    private String sex;
    private String rongid;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailbox(){
        return mailbox;
    }
    public void setMailbox(String mailbox){
        this.mailbox = mailbox;
    }

    public String getAvatar(){
        return avatar;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public String getSex(){
        return sex;
    }
    public void setSex(String sex){
        this.sex = sex;
    }

    public String getRongid(){
        return rongid;
    }
    public void setRongid(String rongid){
        this.rongid = rongid;
    }

}
