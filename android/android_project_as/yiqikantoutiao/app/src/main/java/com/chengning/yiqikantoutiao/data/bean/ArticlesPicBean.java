package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ArticlesPicBean implements Serializable {
	private static final long serialVersionUID = 8465118876667875725L;
	/**
	 * 
	 */
	private String tid;
	String title;
	String link;
	String image;
	ArrayList<ArticleItemPicBean> content;
	String category;
	String author;
	private String dateline;
	
	
	private int is_favor;

	private String replys;

	/**
	 * 看完每张图片的时间
	 */
	private float video_play_time;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getIs_favor() {
		return is_favor;
	}

	public void setIs_favor(int is_favor) {
		this.is_favor = is_favor;
	}

	public ArrayList<ArticleItemPicBean> getContent() {
		return content;
	}

	public void setContent(ArrayList<ArticleItemPicBean> content) {
		this.content = content;
	}

	public String getReplys() {
		return replys;
	}

	public void setReplys(String replys) {
		this.replys = replys;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public float getView_second() {
		return video_play_time;
	}

	public void setView_second(float view_second) {
		this.video_play_time = view_second;
	}
}
