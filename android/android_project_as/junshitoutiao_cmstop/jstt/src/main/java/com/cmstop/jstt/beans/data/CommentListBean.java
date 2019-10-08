package com.cmstop.jstt.beans.data;

import java.util.ArrayList;

import com.cmstop.jstt.base.BaseListBean;

public class CommentListBean extends BaseListBean<CommentItemBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 390605598700978450L;
	private ArrayList<CommentItemBean> list;
	private ArrayList<CommentItemBean> hot_list;
	public ArrayList<CommentItemBean> getList() {
		return list;
	}

	public void setList(ArrayList<CommentItemBean> list) {
		this.list = list;
	}

	public ArrayList<CommentItemBean> getHot_list() {
		return hot_list;
	}

	public void setHot_list(ArrayList<CommentItemBean> hot_list) {
		this.hot_list = hot_list;
	}
	
}
