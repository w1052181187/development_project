package com.appmf.net.appmf.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AdContentBean implements Serializable {

    /**
     * id  :
     * video  :
     * source  :
     * img  : []
     * type  : explorer
     * end_time  : 1546099200
     */
    @SerializedName("id")
    private String id;
    @SerializedName("video")
    private String video;
    @SerializedName("source")
    private String source;
    @SerializedName("img")
    private List<?> img;
    @SerializedName("type")
    private String type;
    @SerializedName("end_time")
    private long end_time;
    @SerializedName("format")
    private int format;
    @SerializedName("count_down")
    private int count_down;

    public void setId(String id) {
        this.id = id;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImg(List<?> img) {
        this.img = img;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public String getVideo() {
        return video;
    }

    public String getSource() {
        return source;
    }

    public List<?> getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public long getEnd_time() {
        return end_time;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getCount_down() {
        return count_down;
    }

    public void setCount_down(int count_down) {
        this.count_down = count_down;
    }
}
