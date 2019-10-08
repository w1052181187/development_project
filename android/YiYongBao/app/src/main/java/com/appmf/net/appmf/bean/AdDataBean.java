package com.appmf.net.appmf.bean;

import java.io.Serializable;

public class AdDataBean implements Serializable {
    private String appId;
    private AdContentBean ad;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public AdContentBean getAd() {
        return ad;
    }

    public void setAd(AdContentBean ad) {
        this.ad = ad;
    }
}
