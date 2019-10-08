/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.io.Serializable;

public class MediaLiveMsgItemModel implements Serializable {
    private String userName; //用户名
    private String content; //消息内容
    private String userId; //用户id
    private String liveId; //
    private String objectId; //

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String objectId) {
        this.liveId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}