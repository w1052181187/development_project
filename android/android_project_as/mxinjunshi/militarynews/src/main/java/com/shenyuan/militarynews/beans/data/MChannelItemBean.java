package com.shenyuan.militarynews.beans.data;

import java.io.Serializable;
import java.util.ArrayList;

public class MChannelItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1635510657839637428L;
	String title;
	String aid;
	String description;
	String image;
	String pubDate;
	String category;
	String author;
	String comments;
	ArrayList<String> image_arr;

	String local_channel;
	int local_page;

	String channel;
	String digg;
	String pl;
	String nav;
	int news_show_type;
	String more;
	String local_nav;
	private String tz_nav;

	String red_tag;
	String is_favor;
	String ad_place_id;
	
	/**
	 * 广告类型
	 * 1为百度广告2为腾讯广告 
	 */
	private int ad_type;

	private long pubTimestamp;
	private String tips;
	private String redirecturl;

	/**
	 * 是否跳转首页视频标签
	 */
	private int is_redirect;

	/**
	 * 是否改变背景颜色
	 */
	private int is_color;

	public String getLocal_nav() {
		return local_nav;
	}

	public void setLocal_nav(String local_nav) {
		this.local_nav = local_nav;
	}

	public int getNews_show_type() {
		return news_show_type;
	}

	public void setNews_show_type(int news_show_type) {
		this.news_show_type = news_show_type;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDigg() {
		return digg;
	}

	public void setDigg(String digg) {
		this.digg = digg;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ArrayList<String> getImage_arr() {
		return image_arr;
	}

	public void setImage_arr(ArrayList<String> image_arr) {
		this.image_arr = image_arr;
	}

	public String getLocal_channel() {
		return local_channel;
	}

	public void setLocal_channel(String local_channel) {
		this.local_channel = local_channel;
	}

	public int getLocal_page() {
		return local_page;
	}

	public void setLocal_page(int local_page) {
		this.local_page = local_page;
	}

	public String getNav() {
		return nav;
	}

	public void setNav(String nav) {
		this.nav = nav;
	}

	public String getRed_tag() {
		return red_tag;
	}

	public void setRed_tag(String red_tag) {
		this.red_tag = red_tag;
	}

	public String getIs_favor() {
		return is_favor;
	}

	public void setIs_favor(String is_favor) {
		this.is_favor = is_favor;
	}

	public String getAd_place_id() {
		return ad_place_id;
	}

	public void setAd_place_id(String ad_place_id) {
		this.ad_place_id = ad_place_id;
	}

	public long getPubTimestamp() {
		return pubTimestamp;
	}

	public void setPubTimestamp(long pubTimestamp) {
		this.pubTimestamp = pubTimestamp;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getRedirecturl() {
		return redirecturl;
	}

	public void setRedirecturl(String redirecturl) {
		this.redirecturl = redirecturl;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTz_nav() {
		return tz_nav;
	}

	public void setTz_nav(String tz_nav) {
		this.tz_nav = tz_nav;
	}

	public int getAd_type() {
		return ad_type;
	}

	public void setAd_type(int ad_type) {
		this.ad_type = ad_type;
	}

	public int getIs_color() {
		return is_color;
	}

	public void setIs_color(int is_color) {
		this.is_color = is_color;
	}

	public int getIs_redirect() {
		return is_redirect;
	}

	public void setIs_redirect(int is_redirect) {
		this.is_redirect = is_redirect;
	}
}
