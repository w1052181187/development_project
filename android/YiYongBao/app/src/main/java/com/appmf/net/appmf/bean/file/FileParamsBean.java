package com.appmf.net.appmf.bean.file;

import com.google.gson.JsonElement;

import java.io.Serializable;
import java.util.ArrayList;

public class FileParamsBean implements Serializable {
    private String method;
    private JsonElement path;
    private String name;
    private String unit = "auto";
    private int limit = 50;
    private int page;
    private String order = "time";

    /** 文件类型（具体看文档说明）
     */
    private String type;
    private ArrayList<String> extension;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    public ArrayList<String> getExtension() {
        return extension;
    }

    public void setExtension(ArrayList<String> extension) {
        this.extension = extension;
    }

    public JsonElement getPath() {
        return path;
    }

    public void setPath(JsonElement path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}