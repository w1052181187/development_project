package com.chengning.yiqikantoutiao.data.bean;

import java.util.ArrayList;

public class ArticlesBean extends BaseArticlesBean<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7806335819104996155L;
	// subscribe
	ArrayList<SubscribeContentItemBean> content;
	//是否专栏文章
	private int is_special;
	
	private int is_subscribe;
	
	//文章相关推荐
	private ChannelItemBean add_code;
	private ChannelItemBean add_code_2;
	private ChannelItemBean add_code_big;
	private ArrayList<ChannelItemBean> relations;
	
	//打赏人数
	private String rewardcounts;
	
	private ArrayList<String> image_arr;
	
	private int show_type;
	private int total_page;

	/***
	 * 看完普通文章每屏的时间（看完视频总时间）
	 */
	private float video_play_time;
	
	public ArrayList<SubscribeContentItemBean> getContent() {
		return content;
	}

	public void setContent(ArrayList<SubscribeContentItemBean> content) {
		this.content = content;
	}

	public int getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(int is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public int getIs_special() {
		return is_special;
	}

	public void setIs_special(int is_special) {
		this.is_special = is_special;
	}

	public ChannelItemBean getAdd_code() {
		return add_code;
	}

	public void setAdd_code(ChannelItemBean add_code) {
		this.add_code = add_code;
	}

	public ArrayList<ChannelItemBean> getRelations() {
		return relations;
	}

	public void setRelations(ArrayList<ChannelItemBean> relations) {
		this.relations = relations;
	}

	public ChannelItemBean getAdd_code_big() {
		return add_code_big;
	}

	public void setAdd_code_big(ChannelItemBean add_code_big) {
		this.add_code_big = add_code_big;
	}

	public ChannelItemBean getAdd_code_2() {
		return add_code_2;
	}

	public void setAdd_code_2(ChannelItemBean add_code_2) {
		this.add_code_2 = add_code_2;
	}

	public String getRewardcounts() {
		return rewardcounts;
	}

	public void setRewardcounts(String rewardcounts) {
		this.rewardcounts = rewardcounts;
	}
	
	public ArrayList<String> getImage_arr() {
		return image_arr;
	}

	public void setImage_arr(ArrayList<String> image_arr) {
		this.image_arr = image_arr;
	}

	public int getShow_type() {
		return show_type;
	}

	public void setShow_type(int show_type) {
		this.show_type = show_type;
	}

	public int getTotal_page() {
		return total_page;
	}

	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}

	public float getView_second() {
		return video_play_time;
	}

	public void setView_second(float view_second) {
		this.video_play_time = view_second;
	}
}
