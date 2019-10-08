package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;

public class BadgeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3758290413326707436L;
	
	private int new_count;
	private String maxnewsid;
	
	public int getNew_count() {
		return new_count;
	}
	public void setNew_count(int new_count) {
		this.new_count = new_count;
	}
	public String getMaxnewsid() {
		return maxnewsid;
	}
	public void setMaxnewsid(String maxnewsid) {
		this.maxnewsid = maxnewsid;
	}

}
