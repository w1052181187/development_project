package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class SettingBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8937510577754525856L;
	int is_push;
	int fontsize;
	private int is_no_pic_model;
	String lasttime;
	private int is_night_model;

	private int config_push_enable;
	private String config_push_start_time;
	private String config_push_end_time;
	public int getIs_push() {
		return is_push;
	}

	public void setIs_push(int is_push) {
		this.is_push = is_push;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public int getIs_no_pic_model() {
		return is_no_pic_model;
	}

	public void setIs_no_pic_model(int is_no_pic_model) {
		this.is_no_pic_model = is_no_pic_model;
	}

	public int getIs_night_model() {
		return is_night_model;
	}

	public void setIs_night_model(int is_night_model) {
		this.is_night_model = is_night_model;
	}

	public String getConfig_push_start_time() {
		return config_push_start_time;
	}

	public void setConfig_push_start_time(String config_push_start_time) {
		this.config_push_start_time = config_push_start_time;
	}

	public String getConfig_push_end_time() {
		return config_push_end_time;
	}

	public void setConfig_push_end_time(String config_push_end_time) {
		this.config_push_end_time = config_push_end_time;
	}

	public int getConfig_push_enable() {
		return config_push_enable;
	}

	public void setConfig_push_enable(int config_push_enable) {
		this.config_push_enable = config_push_enable;
	}

}
