package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ChannelListBean implements Serializable{

	/**
	 * list:slide
	 * 		quan_topic
	 * 		recd_topic
	 */
	private static final long serialVersionUID = -473757294709934369L;

	private ArrayList<ChannelItemBean> slide;
	private ArrayList<DynamicItemBean> quan_topic;
	private ArrayList<ChannelItemBean> recd_topic;
	public ArrayList<ChannelItemBean> getSlide() {
		return slide;
	}
	public void setSlide(ArrayList<ChannelItemBean> slide) {
		this.slide = slide;
	}
	public ArrayList<DynamicItemBean> getQuan_topic() {
		return quan_topic;
	}
	public void setQuan_topic(ArrayList<DynamicItemBean> quan_topic) {
		this.quan_topic = quan_topic;
	}
	public ArrayList<ChannelItemBean> getRecd_topic() {
		return recd_topic;
	}
	public void setRecd_topic(ArrayList<ChannelItemBean> recd_topic) {
		this.recd_topic = recd_topic;
	}
}
