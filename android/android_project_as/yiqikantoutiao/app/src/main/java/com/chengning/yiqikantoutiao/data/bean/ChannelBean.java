package com.chengning.yiqikantoutiao.data.bean;

import com.chengning.yiqikantoutiao.base.BaseListBean;

import java.util.ArrayList;

public class ChannelBean extends BaseListBean<ChannelItemBean> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3570845321508294899L;
	private String chan_list2;
	private ArrayList<ChanBean> all_chan_list;
	private ArrayList<ChannelItemBean> list;
	private ChannelListBean all_data;
	private String user_nav_list;
	private String user_nav_list_time;
	private ArrayList<ChannelItemBean> columnlist;
	private int is_recom;

	public ArrayList<ChanBean> getAll_chan_list() {
		return all_chan_list;
	}

	public void setAll_chan_list(ArrayList<ChanBean> all_chan_list) {
		this.all_chan_list = all_chan_list;
	}

	
	public String getUser_nav_list() {
		return user_nav_list;
	}

	public void setUser_nav_list(String user_nav_list) {
		this.user_nav_list = user_nav_list;
	}

	public String getChan_list2() {
		return chan_list2;
	}

	public void setChan_list2(String chan_list2) {
		this.chan_list2 = chan_list2;
	}

	public String getUser_nav_list_time() {
		return user_nav_list_time;
	}

	public void setUser_nav_list_time(String user_nav_list_time) {
		this.user_nav_list_time = user_nav_list_time;
	}

	public ArrayList<ChannelItemBean> getList() {
		return list;
	}

	public void setList(ArrayList<ChannelItemBean> list) {
		this.list = list;
	}

	public ChannelListBean getAll_data() {
		return all_data;
	}

	public void setAll_data(ChannelListBean all_data) {
		this.all_data = all_data;
	}

	public ArrayList<ChannelItemBean> getColumnlist() {
		return columnlist;
	}

	public void setColumnlist(ArrayList<ChannelItemBean> columnlist) {
		this.columnlist = columnlist;
	}

	public int getIs_recom() {
		return is_recom;
	}

	public void setIs_recom(int is_recom) {
		this.is_recom = is_recom;
	}

}
