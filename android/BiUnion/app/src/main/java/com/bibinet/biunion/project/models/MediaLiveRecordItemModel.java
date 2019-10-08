package com.bibinet.biunion.project.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MediaLiveRecordItemModel {
    String objectId;
    String liveName;
    String liveCover;
    String userId;
    String plugUrl;
    String playUrl;
    String recordUrl;
    int liveStatus; //0:直播中 1：直播结束
    String userLogo;
    String liveDuration;
    String liveStartTime;
    String liveEndTime;
    private String encryptionPlugUrl;

    public String getLiveStartTime() {
        return liveStartTime;
    }

    public void setLiveStartTime(String liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public String getLiveEndTime() {
        return liveEndTime;
    }

    public void setLiveEndTime(String liveEndTime) {
        this.liveEndTime = liveEndTime;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public String getLiveDuration() {
        return liveDuration;
    }

    public void setLiveDuration(String liveDuration) {
        this.liveDuration = liveDuration;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlugUrl() {
        return plugUrl;
    }

    public void setPlugUrl(String plugUrl) {
        this.plugUrl = plugUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getEncryptionPlugUrl() {
        return encryptionPlugUrl;
    }

    public void setEncryptionPlugUrl(String encryptionPlugUrl) {
        this.encryptionPlugUrl = encryptionPlugUrl;
    }

    public class RecordContentInfo implements Serializable{
        String ossEndpoint;
        String ossBucket;
        String ossObjectPrefix;
        String startTime;
        String endTime;
        String duration;

        public String getOssEndpoint() {
            return ossEndpoint;
        }

        public void setOssEndpoint(String ossEndpoint) {
            this.ossEndpoint = ossEndpoint;
        }

        public String getOssBucket() {
            return ossBucket;
        }

        public void setOssBucket(String ossBucket) {
            this.ossBucket = ossBucket;
        }

        public String getOssObjectPrefix() {
            return ossObjectPrefix;
        }

        public void setOssObjectPrefix(String ossObjectPrefix) {
            this.ossObjectPrefix = ossObjectPrefix;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
