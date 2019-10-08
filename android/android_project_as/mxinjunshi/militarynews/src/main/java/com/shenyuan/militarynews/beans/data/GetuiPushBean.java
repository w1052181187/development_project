package com.shenyuan.militarynews.beans.data;

public class GetuiPushBean {
	private String title;
	private String alert;
	private String tid;
	private int t_type;
	
	//文章类型 （1普通文章2图文 3视频 4自定义）
	private int arc_type;
	private String badge;
	private String tz_url;

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

	public int getArc_type() {
		return arc_type;
	}

	public void setArc_type(int arc_type) {
		this.arc_type = arc_type;
	}

	public String getTz_url() {
		return tz_url;
	}

	public void setTz_url(String tz_url) {
		this.tz_url = tz_url;
	}

}
