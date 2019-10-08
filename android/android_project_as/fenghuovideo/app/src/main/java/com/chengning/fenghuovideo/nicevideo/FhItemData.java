package com.chengning.fenghuovideo.nicevideo;

import android.widget.ListView;

import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;

/**
 * Created by jiankai on 2017/8/22.
 */

public class FhItemData {
    private ListView itemListView;
    private int itemPosition;
    private BaseArticlesBean mBean;

    public FhItemData(ListView view, int position, BaseArticlesBean bean){
        this.itemListView = view;
        this.itemPosition = position;
        this.mBean = bean;
    }

    public ListView getListView(){
        return itemListView;
    }

    public int getPosition(){
        return itemPosition;
    }

    public BaseArticlesBean getBean(){
        return mBean;
    }
}
