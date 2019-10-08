/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.io.Serializable;
import java.util.List;

public class MediaVideoDetailChapterModel implements Serializable {
    private String sectionName; //课程名称

    private String sectionNo; //第几章

    private List<MediaVideoDetailSectionModel> hours;

    public List<MediaVideoDetailSectionModel> getHours() {
        return hours;
    }

    public void setHours(List<MediaVideoDetailSectionModel> hours) {
        this.hours = hours;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(String sectionNo) {
        this.sectionNo = sectionNo;
    }
}