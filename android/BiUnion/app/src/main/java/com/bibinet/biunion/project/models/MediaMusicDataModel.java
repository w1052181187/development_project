package com.bibinet.biunion.project.models;

import java.io.Serializable;

/**
 * @author wyg
 * @date 2018/5/25
 */

public class MediaMusicDataModel implements Serializable{


    private String studyTime; //学习时间
    private int pitch; //节数
    private String content; //


    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
