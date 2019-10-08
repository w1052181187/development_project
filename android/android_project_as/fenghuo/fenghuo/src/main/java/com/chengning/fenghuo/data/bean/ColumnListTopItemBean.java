package com.chengning.fenghuo.data.bean;

import java.io.Serializable;

public class ColumnListTopItemBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6221059817220675861L;
	private String title;
	private String images;
	private String url;
	private String jump_type;
	private String jump_id;
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
	public String getJump_type() {
		return jump_type;
	}
	public void setJump_type(String jump_type) {
		this.jump_type = jump_type;
	}
	public String getJump_id() {
		return jump_id;
	}
	public void setJump_id(String jump_id) {
		this.jump_id = jump_id;
	}
	
	
}
