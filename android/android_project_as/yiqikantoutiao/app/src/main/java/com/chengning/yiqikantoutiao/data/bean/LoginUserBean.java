package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class LoginUserBean implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -4022492248730550021L;

	UserInfoBean userinfo;
	String cookie;
	String lasttime;

	public UserInfoBean getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfoBean userinfo) {
		this.userinfo = userinfo;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

}
