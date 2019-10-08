package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class PushConfigBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7186351485121798990L;
	private int enable;
	private String start_time;
	private String end_time;
	private int is_at;
	private int is_dig;
	private int is_reply;
	private int is_pm;
	private int is_fan;
	private int is_splendid;
	private int reply_access;
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getIs_at() {
		return is_at;
	}
	public void setIs_at(int is_at) {
		this.is_at = is_at;
	}
	public int getIs_dig() {
		return is_dig;
	}
	public void setIs_dig(int is_dig) {
		this.is_dig = is_dig;
	}
	public int getIs_reply() {
		return is_reply;
	}
	public void setIs_reply(int is_reply) {
		this.is_reply = is_reply;
	}
	public int getIs_pm() {
		return is_pm;
	}
	public void setIs_pm(int is_pm) {
		this.is_pm = is_pm;
	}
	public int getIs_fan() {
		return is_fan;
	}
	public void setIs_fan(int is_fan) {
		this.is_fan = is_fan;
	}
	public int getIs_splendid() {
		return is_splendid;
	}
	public void setIs_splendid(int is_splendid) {
		this.is_splendid = is_splendid;
	}
	public int getReply_access() {
		return reply_access;
	}
	public void setReply_access(int reply_access) {
		this.reply_access = reply_access;
	}

}
