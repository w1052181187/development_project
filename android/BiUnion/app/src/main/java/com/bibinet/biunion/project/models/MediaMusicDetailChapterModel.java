/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.io.Serializable;
import java.util.List;

public class MediaMusicDetailChapterModel implements Serializable {
    private String audioName; //集名称

    private String audioSize; //集时长

    private String filePath; //播放路径

    private String createDate; //创建日期

    //本地
    private boolean isStuded;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(String audioSize) {
        this.audioSize = audioSize;
    }

    public String getFilePath() {
        return "download?filePath=" + filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isStuded() {
        return isStuded;
    }

    public void setStuded(boolean studed) {
        isStuded = studed;
    }
}