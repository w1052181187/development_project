package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;

public class LoginDbBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617534707586133241L;
	LoginBean userinfo;
	String pwd;
	String cookie;
	String lasttime;

	public LoginBean getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(LoginBean userinfo) {
		this.userinfo = userinfo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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
