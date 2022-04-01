package com.example.newpubliccomments.share;

import cn.bmob.v3.BmobObject;

public class ShareMessage extends BmobObject {

    private String photo;
    private String phone;
    private String avatar;
    private String message;

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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

}
