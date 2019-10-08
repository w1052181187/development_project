package com.cmstop.jstt.beans.data;

import java.io.Serializable;
import java.util.ArrayList;

public class FavListBean implements Serializable{

	private static final long serialVersionUID = 8010474786588853677L;
	private int total_page;
	private ArrayList<MChannelItemBean>  list;
	public int getTotal_page() {
		return total_page;
	}
	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}
	public ArrayList<MChannelItemBean> getList() {
		return list;
	}
	public void setList(ArrayList<MChannelItemBean> list) {
		this.list = list;
	}
}
