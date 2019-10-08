package com.chengning.yiqikantoutiao.data.bean;

import com.chengning.yiqikantoutiao.base.BaseListBean;

import java.util.ArrayList;

public class CommentListBean extends BaseListBean<CommentItemBean> {

	private static final long serialVersionUID = -2311897747508119144L;
	private ArrayList<CommentItemBean> list;
	
	public ArrayList<CommentItemBean> getList() {
		return list;
	}
	public void setList(ArrayList<CommentItemBean> list) {
		this.list = list;
	}
	
	
}
