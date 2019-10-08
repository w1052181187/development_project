package com.appmf.net.appmf.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdConfigBean implements Serializable {

    /**
     * statistics  : 1
     * source : appmf
     * time : 3
     * type : 1
     * url : /ad/get_ad_list
     * timeStatus  : 1
     * status : 1
     */
    @SerializedName("statistics")
    private int statistics;
    @SerializedName("source")
    private String source;
    @SerializedName("time")
    private int time;
    @SerializedName("url")
    private String url;
    @SerializedName("timeStatus")
    private int timeStatus;
    @SerializedName("ad")
    private AdContentBean ad;

    public void setStatistics(int statistics) {
        this.statistics = statistics;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimeStatus(int timeStatus) {
        this.timeStatus = timeStatus;
    }

    public int getStatistics() {
        return statistics;
    }

    public String getSource() {
        return source;
    }

    public int getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public int getTimeStatus() {
        return timeStatus;
    }

    public AdContentBean getAd() {
        return ad;
    }

    public void setAd(AdContentBean content) {
        this.ad = content;
    }
}
