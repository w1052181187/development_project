package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;

public class BindDataBean implements Serializable{
    private static final long serialVersionUID = -6946689163522222017L;

    String mobile;
    String wx;
    String qq;
    String sina;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
