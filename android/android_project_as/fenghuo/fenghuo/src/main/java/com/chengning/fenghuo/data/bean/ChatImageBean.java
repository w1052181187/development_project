package com.chengning.fenghuo.data.bean;

import java.io.Serializable;

public class ChatImageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1071054567820763155L;
	int id;
	String image;
	String image_small;
	String image_middle;
	String image_big;
	String image_original;
	int image_width;
	int image_height;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage_small() {
		return image_small;
	}

	public void setImage_small(String image_small) {
		this.image_small = image_small;
	}

	public String getImage_middle() {
		return image_middle;
	}

	public void setImage_middle(String image_middle) {
		this.image_middle = image_middle;
	}

	public String getImage_big() {
		return image_big;
	}

	public void setImage_big(String image_big) {
		this.image_big = image_big;
	}

	public String getImage_original() {
		return image_original;
	}

	public void setImage_original(String image_original) {
		this.image_original = image_original;
	}

	public int getImage_width() {
		return image_width;
	}

	public void setImage_width(int image_width) {
		this.image_width = image_width;
	}

	public int getImage_height() {
		return image_height;
	}

	public void setImage_height(int image_height) {
		this.image_height = image_height;
	}
}
