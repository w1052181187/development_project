package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class FavListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2169351294378025860L;
	private int total_page;
	private ArrayList<ArticlesBean> list;
	
	public int getTotal_page() {
		return total_page;
	}
	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}
	public ArrayList<ArticlesBean> getList() {
		return list;
	}
	public void setList(ArrayList<ArticlesBean> list) {
		this.list = list;
	}
	
}
