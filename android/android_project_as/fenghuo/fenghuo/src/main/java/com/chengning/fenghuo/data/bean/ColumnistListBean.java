package com.chengning.fenghuo.data.bean;

import java.util.ArrayList;

import com.chengning.fenghuo.base.BaseListBean;

public class ColumnistListBean extends BaseListBean<UserInfoBean> {

	private static final long serialVersionUID = 7537774365929178058L;
	private ArrayList<UserInfoBean> list;
	
	public ArrayList<UserInfoBean> getList() {
		return list;
	}
	public void setList(ArrayList<UserInfoBean> list) {
		this.list = list;
	}

}
