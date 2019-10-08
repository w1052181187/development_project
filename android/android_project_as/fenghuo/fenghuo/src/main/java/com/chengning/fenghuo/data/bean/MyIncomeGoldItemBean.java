package com.chengning.fenghuo.data.bean;

public class MyIncomeGoldItemBean extends BaseArticlesBean<String> {

	private static final long serialVersionUID = -7469861300852594330L;

	private String title;
	private String desc;
	private String num;

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
