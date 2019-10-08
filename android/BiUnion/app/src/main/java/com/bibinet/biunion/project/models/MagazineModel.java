/**
 * Copyright 2017 bejson.com
 */
package com.bibinet.biunion.project.models;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class MagazineModel extends PageModel {
    private List<MagazineChildModel> recommend;
    private List<MagazineChildModel> item;

    public List<MagazineChildModel> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<MagazineChildModel> recommend) {
        this.recommend = recommend;
    }

    public List<MagazineChildModel> getItem() {
        return item;
    }

    public void setItem(List<MagazineChildModel> item) {
        this.item = item;
    }


}