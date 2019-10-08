package com.shenyuan.militarynews;

import com.shenyuan.militarynews.beans.data.SettingBean;
import com.shenyuan.militarynews.data.access.SettingServer;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;

public class SettingManager {
	private static SettingManager mSetting = null;
	
	public static SettingManager getInst(){
		
		if (mSetting == null) {
            synchronized (SettingManager.class) {
                if(mSetting == null){
                    mSetting = new SettingManager();
                }
            }
        }
		return mSetting;
	}

	private SettingBean bean;
	
	public void init(){
		SettingBean bean = getSettingBean();
	}
	
	public static SettingBean getDefaultSettingBean(){
		SettingBean bean = new SettingBean();
		
		// 默认设置
		bean.setIs_push(Common.TRUE);
		bean.setFontsize(JUrl.DEFAULT_FONTSIZE);
		bean.setIs_night_model(Common.FALSE);
		bean.setIs_no_pic_model(Common.FALSE);
		bean.setLasttime("");
		return bean;
	}

	
	public void saveSetting(SettingBean bean){
		this.bean = bean;
		SettingServer.getInst(App.getInst()).deleteAll();
		SettingServer.getInst(App.getInst()).insertOne(bean);
	}
	
	public SettingBean getSettingBean(){
		
		if (null == bean) {
			bean = SettingServer.getInst(App.getInst()).getSettingBean();
			if(bean == null){
				bean = getDefaultSettingBean();
				SettingServer.getInst(App.getInst()).insertOne(bean);
			}
		}
		
		return bean;
	}
	
	/**
	 * 获取推送
	 * @return
	 */
	public int getIsPush(){
		return getSettingBean().getIs_push();
	}
	
	/**
	 * 保存推送
	 * @param is_push
	 */
	public void saveIsPush(int is_push){
		SettingBean bean = getSettingBean();
		bean.setIs_push(is_push);
		saveSetting(bean);
	}
	
	/**
	 * 获取字体
	 * @return
	 */
	public int getFontSize(){
		return getSettingBean().getFontsize();
	}
	
	/**
	 * 保存字体
	 * @param fontSize
	 */
	public void saveFontSize(int fontSize){
		SettingBean bean = getSettingBean();
		bean.setFontsize(fontSize);
		saveSetting(bean);
	}
	
	/**
	 * 保存无图模式
	 * @param model
	 */
	public void saveNoPicModel(int model){
		SettingBean bean = getSettingBean();
		bean.setIs_no_pic_model(model);
		saveSetting(bean);
	}
	
	/**
	 * 获取无图模式
	 * @return
	 */
	public int getNoPicModel(){
		return getSettingBean().getIs_no_pic_model();
	}
	
	/**
	 * 保存夜间模式
	 * @param model
	 */
	public void saveNightModel(int model){
		SettingBean bean = getSettingBean();
		bean.setIs_night_model(model);
		saveSetting(bean);
	}
	
	/**
	 * 获取夜间模式
	 * @return
	 */
	public int getNightModel(){
		return getSettingBean().getIs_night_model();
	}
	
	public static enum FontSize{
		Small(14, "小"),
		Middle(18, "中"),
		Large(22, "大"),
		ExtraLarge(26, "超大"),
		;
		
		private int size;
		private String str;
		private FontSize(int size, String str){
			this.size = size;
			this.str = str;
		}
		
		public int getSize(){
			return size;
		}
		
		public String getStr(){
			return str;
		}
		
		public static FontSize getFontSize(int size){
			FontSize f = Middle;
			for(FontSize v : values()){
				if(v.size == size){
					f = v;
					break;
				}
			}
			return f;
		}
	}
}
