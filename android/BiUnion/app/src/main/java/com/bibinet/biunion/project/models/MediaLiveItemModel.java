/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.io.Serializable;

public class MediaLiveItemModel implements Serializable {
    private String title; //标题
    private String time; //时间
    private String avatar; //头像
    private String cover; //封面
    private String url; //直播地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}