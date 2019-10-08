package com.chengning.fenghuovideo.data.bean;

import com.chengning.fenghuovideo.base.BaseListBean;

import java.util.ArrayList;

public class SearchNewsListBean extends BaseListBean<ChannelItemBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8161594810366105588L;
	private ArrayList<ChannelItemBean> item;
	private ArrayList<String> keywords;
	
	public ArrayList<ChannelItemBean> getList() {
		return item;
	}

	public void setList(ArrayList<ChannelItemBean> item) {
		this.item = item;
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}


	
	
}
