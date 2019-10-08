/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import java.util.List;

public class HotWordsModel extends BaseModel{
    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}