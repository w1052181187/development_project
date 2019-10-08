package com.appmf.net.appmf.bean.file;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static com.appmf.net.appmf.app.Constants.FILE_MANAGER_RESOURSE_DIR;

public class FastFileParamsBean {
    String method;
    int pas = 0;
    String extension;
    long time = 3600;
    String path = FILE_MANAGER_RESOURSE_DIR;
    String name;
    JsonElement data;
    private int limit = 20;
    private int page;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPas() {
        return pas;
    }

    public void setPas(int pas) {
        this.pas = pas;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
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
}
