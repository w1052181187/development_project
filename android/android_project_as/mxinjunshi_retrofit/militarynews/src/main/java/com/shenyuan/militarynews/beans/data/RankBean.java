package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;

public class RankBean implements Serializable {
//	 "titles": "列兵",
//     "icon": "1",
//     "integral": "0"

	private static final long serialVersionUID = -8540111684933396411L;
	private String titles;
	private String icon;
	private String integral;
	public String getTitles() {
		return titles;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}

	
	
}
