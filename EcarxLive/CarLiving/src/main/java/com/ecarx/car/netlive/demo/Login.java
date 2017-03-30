package com.ecarx.car.netlive.demo;

import java.io.Serializable;

public class Login implements Serializable {

    public String userName;
    public String img;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
