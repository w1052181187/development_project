package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.chengning.fenghuo.base.BaseListBean;

public class DynamicListBean extends BaseListBean<DynamicItemBean> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1732060321390921388L;
	private ArrayList<DynamicItemBean> list;

	public ArrayList<DynamicItemBean> getList() {
		return list;
	}

	public void setList(ArrayList<DynamicItemBean> list) {
		this.list = list;
	}

}
