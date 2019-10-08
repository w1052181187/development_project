package com.appmf.net.appmf.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DbConfigBean implements Serializable {

    private String drop;
    private String create;
    private ArrayList<String> index;

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public ArrayList<String> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<String> index) {
        this.index = index;
    }
}
