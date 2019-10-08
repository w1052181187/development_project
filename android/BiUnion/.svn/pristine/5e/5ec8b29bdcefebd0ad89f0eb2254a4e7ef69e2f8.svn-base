package com.bibinet.biunion.project.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author wyg
 * @date 2018/5/25
 */

@Entity
@Keep
public class MediaMusicData {

    @Unique
    private String code;
    private String studyTime; //学习时间
    private int pitch; //
    private String content; //

    public MediaMusicData(String code, String studyTime, int pitch, String content) {
        this.code = code;
        this.studyTime = studyTime;
        this.pitch = pitch;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }
}
