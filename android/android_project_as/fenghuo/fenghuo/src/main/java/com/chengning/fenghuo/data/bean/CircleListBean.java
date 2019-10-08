package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @description 圈子Bean
 * @author wangyungang
 * @date 2015.7.13 9:10
 */
public class CircleListBean extends BaseChannelItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3475873041548138371L;
	public static String TOPIC = "2";
	public static String UNSELECT = "3";

	/**
	 * 讨论列表
	 */
	private ArrayList<DynamicItemBean> list;

	/**
	 * 在数据保存用来区分是否首页
	 */
	private String local_type;
	private int local_page;

	public String getLocal_type() {
		return local_type;
	}

	public void setLocal_type(String local_type) {
		this.local_type = local_type;
	}

	public int getLocal_page() {
		return local_page;
	}

	public void setLocal_page(int local_page) {
		this.local_page = local_page;
	}

	public ArrayList<DynamicItemBean> getList() {
		return list;
	}

	public void setList(ArrayList<DynamicItemBean> list) {
		this.list = list;
	}

}
