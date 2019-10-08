package com.chengning.fenghuo.data.bean;

import java.io.Serializable;

public class ArticleDigBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5980299611984314093L;
	private int digcounts;
	private int badcounts;

	public int getDigcounts() {
		return digcounts;
	}

	public void setDigcounts(int digcounts) {
		this.digcounts = digcounts;
	}

	public int getBadcounts() {
		return badcounts;
	}

	public void setBadcounts(int badcounts) {
		this.badcounts = badcounts;
	}
}
