package com.bibinet.biunion.project.bean;

import java.io.Serializable;

/**
 * Created by Wh on 2017-7-7.
 */

public class InformationBean implements Serializable {
    //父类标识
    private int parentId;
    //唯一标识
    private int id;
    //请求数据参数分类
    private String requestDataP;
    //每日更新量请求参数
    private String DailyUpdatesCountP;
    //列表页需要显示的标题
    private String title;
    //详情页需要显示的标题
    private String detailsTitle;
    //刷新标题提示
    private String refreshAssist;

    public InformationBean(int parentId, int id, String requestDataP, String dailyUpdatesCountP, String title, String detailsTitle, String refreshAssist) {
        this.parentId = parentId;
        this.id = id;
        this.requestDataP = requestDataP;
        DailyUpdatesCountP = dailyUpdatesCountP;
        this.title = title;
        this.detailsTitle = detailsTitle;
        this.refreshAssist = refreshAssist;
    }

    public String getDetailsTitle() {
        return detailsTitle;
    }

    public void setDetailsTitle(String detailsTitle) {
        this.detailsTitle = detailsTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestDataP() {
        return requestDataP;
    }

    public void setRequestDataP(String requestDataP) {
        this.requestDataP = requestDataP;
    }

    public String getDailyUpdatesCountP() {
        return DailyUpdatesCountP;
    }

    public void setDailyUpdatesCountP(String dailyUpdatesCountP) {
        DailyUpdatesCountP = dailyUpdatesCountP;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getRefreshAssist() {
        return refreshAssist;
    }

    public void setRefreshAssist(String refreshAssist) {
        this.refreshAssist = refreshAssist;
    }
}
