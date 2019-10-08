package com.appmf.net.appmf.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AdStatisticsModel {
    @Id
    private String id;
    private long click;
    private long shows;
    // 过期时间
    private long end_time;
    // 当前时间
    private long time;
    @Generated(hash = 5614664)
    public AdStatisticsModel(String id, long click, long shows, long end_time, long time) {
        this.id = id;
        this.click = click;
        this.shows = shows;
        this.end_time = end_time;
        this.time = time;
    }
    @Generated(hash = 483570734)
    public AdStatisticsModel() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public long getClick() {
        return this.click;
    }
    public void setClick(long click) {
        this.click = click;
    }
    public long getShows() {
        return this.shows;
    }
    public void setShows(long show) {
        this.shows = show;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
