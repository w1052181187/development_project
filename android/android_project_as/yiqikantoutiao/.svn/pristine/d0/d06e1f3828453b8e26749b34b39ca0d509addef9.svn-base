package com.chengning.yiqikantoutiao.base;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class BaseListBean<T> implements Serializable {

	private static final long serialVersionUID = -5161470199749179637L;
	private int total_page;
	private int size;
	
	public int getTotal_page() {
		return total_page;
	}
	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}
	public abstract ArrayList<T> getList();
	public abstract void setList(ArrayList<T> list);
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
