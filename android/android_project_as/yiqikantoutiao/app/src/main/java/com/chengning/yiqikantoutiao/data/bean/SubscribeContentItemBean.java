package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class SubscribeContentItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5431932011558528602L;
	String type;
	// txt
	String content;
	// img
	int width;
	int height;
	String src;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
