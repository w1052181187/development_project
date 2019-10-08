package com.chengning.fenghuovideo.data.bean;

import java.io.Serializable;

public class AdStartBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3091763410202269470L;
	String title;
	String images;
	String url;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
