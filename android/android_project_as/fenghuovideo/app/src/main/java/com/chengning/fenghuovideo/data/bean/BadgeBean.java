package com.chengning.fenghuovideo.data.bean;

import java.io.Serializable;

public class BadgeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3758290413326707436L;
	
	private int new_count;
	private String maxtid;
	
	public int getNew_count() {
		return new_count;
	}
	public void setNew_count(int new_count) {
		this.new_count = new_count;
	}
	public String getMaxtid() {
		return maxtid;
	}
	public void setMaxtid(String maxtid) {
		this.maxtid = maxtid;
	}

}
