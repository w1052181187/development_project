package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;

public class XiaomiPushBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6271478805899297163L;
	
	private String tz_url;
	private int arc_type;
	private int t_type;
	private String tid;
	private String title;
	private String alert;
	private String badge;
	
	public int getArc_type() {
		return arc_type;
	}
	public void setArc_type(int arc_type) {
		this.arc_type = arc_type;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getT_type() {
		return t_type;
	}
	public void setT_type(int t_type) {
		this.t_type = t_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	public String getTz_url() {
		return tz_url;
	}
	public void setTz_url(String tz_url) {
		this.tz_url = tz_url;
	}
	
	

}
