package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class ArticleItemPicBean implements Serializable {
	/*
	 * width: 980, height: 653, url:
	 * "http://m.xinjunshi.com/uploads/allimg/151003/8-151003162916.jpg",
	 * picstext: "10天10夜的鏖战，一幕幕战斗画面惊心上演……"
	 */
	private static final long serialVersionUID = 6512847239776320296L;
	/**
	 * 
	 */
	private String width;
	private String height;
	private String src;
	private String picstext;

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getPicstext() {
		return picstext;
	}

	public void setPicstext(String picstext) {
		this.picstext = picstext;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
