package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class ReadedArticleBean implements Serializable {

	private static final long serialVersionUID = -334046006386768308L;

	private int number;
	private long readed_time;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getReaded_time() {
		return readed_time;
	}

	public void setReaded_time(long readed_time) {
		this.readed_time = readed_time;
	}

}
