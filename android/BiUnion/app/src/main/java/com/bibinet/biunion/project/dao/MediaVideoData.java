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
public class MediaVideoData{

    @Unique
    private String code;
    private String studyTime; //学习时间
    private String hourNo; //章数
    private String hourTime; //时长
    private String hourName; //
    private String sectionCode;
    private String video;

    public MediaVideoData(String code, String sectionCode, String studyTime, String hourNo, String hourName, String hourTime,String video) {
        this.code = code;
        this.sectionCode = sectionCode;
        this.studyTime = studyTime;
        this.hourNo = hourNo;
        this.hourName = hourName;
        this.hourTime = hourTime;
        this.video = video;
    }

    public MediaVideoData() {

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

    public String getHourName() {
        return hourName;
    }

    public void setHourName(String hourName) {
        this.hourName = hourName;
    }

    public String getHourNo() {
        return hourNo;
    }

    public void setHourNo(String hourNo) {
        this.hourNo = hourNo;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getHourTime() {
        return hourTime;
    }

    public void setHourTime(String hourTime) {
        this.hourTime = hourTime;
    }
}
