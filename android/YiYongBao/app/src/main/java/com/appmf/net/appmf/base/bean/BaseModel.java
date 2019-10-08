package com.appmf.net.appmf.base.bean;

import com.google.gson.JsonElement;

import java.io.Serializable;

/**
 * Created by Wh on 2017-12-4.
 */

public class BaseModel implements Serializable {
    private int status;
    private String msg;
    private JsonElement data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
