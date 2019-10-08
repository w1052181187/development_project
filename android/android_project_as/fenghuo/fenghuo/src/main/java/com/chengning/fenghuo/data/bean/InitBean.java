package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

public class InitBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -79255060642736202L;

	private JSONObject reason_list;
	private AdStartBean ad;
	
	//是否显示桌面红点
	private int is_polling;
	
	//是否显示积分弹窗
	private int is_alert_credits;
	
	//红点轮询时间
	private int check_news_interval;
	private String dateformat;
	
	//是否登录
	private int is_login;

	private ArrayList<String> hot_keywords;

	private int is_boot;
	private ArrayList<String> boot_images;
	private String reg_money;

	//任务系统相关地址
	private InitWapUrlBean wap_url;


	public JSONObject getReason_list() {
		return reason_list;
	}

	public void setReason_list(JSONObject reason_list) {
		this.reason_list = reason_list;
	}

	public AdStartBean getAd() {
		return ad;
	}

	public void setAd(AdStartBean ad) {
		this.ad = ad;
	}

	public int getIs_polling() {
		return is_polling;
	}

	public void setIs_polling(int is_polling) {
		this.is_polling = is_polling;
	}

	public int getIs_alert_credits() {
		return is_alert_credits;
	}

	public void setIs_alert_credits(int is_alert_credits) {
		this.is_alert_credits = is_alert_credits;
	}

	public int getCheck_news_interval() {
		return check_news_interval;
	}

	public void setCheck_news_interval(int check_news_interval) {
		this.check_news_interval = check_news_interval;
	}

	public String getDateformat() {
		return dateformat;
	}

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public int getIs_login() {
		return is_login;
	}

	public void setIs_login(int is_login) {
		this.is_login = is_login;
	}


	public ArrayList<String> getHot_keywords() {
		return hot_keywords;
	}

	public void setHot_keywords(ArrayList<String> hot_keywords) {
		this.hot_keywords = hot_keywords;
	}

	public int getIs_boot() {
		return is_boot;
	}

	public void setIs_boot(int is_boot) {
		this.is_boot = is_boot;
	}

	public ArrayList<String> getBoot_images() {
		return boot_images;
	}

	public void setBoot_images(ArrayList<String> boot_images) {
		this.boot_images = boot_images;
	}

	public String getReg_money() {
		return reg_money;
	}

	public void setReg_money(String reg_money) {
		this.reg_money = reg_money;
	}

	public InitWapUrlBean getWap_url() {
		return wap_url;
	}

	public void setWap_url(InitWapUrlBean wap_url) {
		this.wap_url = wap_url;
	}

	public class InitWapUrlBean {
		private String my_prentice;
		private String my_income;
		private String task;
		private String exchange;
		private String exchange_list;

		public String getMy_prentice() {
			return my_prentice;
		}

		public void setMy_prentice(String my_prentice) {
			this.my_prentice = my_prentice;
		}

		public String getMy_income() {
			return my_income;
		}

		public void setMy_income(String my_income) {
			this.my_income = my_income;
		}

		public String getTask() {
			return task;
		}

		public void setTask(String task) {
			this.task = task;
		}

		public String getExchange() {
			return exchange;
		}

		public void setExchange(String exchange) {
			this.exchange = exchange;
		}

		public String getExchange_list() {
			return exchange_list;
		}

		public void setExchange_list(String exchange_list) {
			this.exchange_list = exchange_list;
		}
	}
}
