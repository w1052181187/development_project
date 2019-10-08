package com.shenyuan.militarynews.base;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class BaseListBean<T> implements Serializable{

	private static final long serialVersionUID = 7110140493404230822L;
	private int maxpage;
	private int count;
	
	public abstract ArrayList<T> getList();
	public abstract void setList(ArrayList<T> list);
	
	public int getMaxpage() {
		return maxpage;
	}
	public void setMaxpage(int maxpage) {
		this.maxpage = maxpage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
