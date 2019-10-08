package com.chengning.fenghuovideo;

import android.content.Context;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.data.access.SettingDA;
import com.chengning.fenghuovideo.data.bean.SettingBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class SettingManager {
	private static SettingManager mSetting = null;

	public static SettingManager getInst() {
		if (mSetting == null) {
            synchronized (SettingManager.class) {
                if (mSetting == null) {
                    mSetting = new SettingManager();
                }
            }
        }
		return mSetting;
	}

	private SettingBean bean;

	public void init() {
		SettingBean bean = getSettingBean();
	}

	private int config_push_enable;

	public static SettingBean getDefaultSettingBean() {
		SettingBean bean = new SettingBean();

		bean.setIs_push(Common.TRUE);
		bean.setFontsize(JUrl.DEFAULT_FONTSIZE);
		bean.setLasttime("");
		bean.setIs_night_model(Common.FALSE);
		bean.setConfig_push_enable(Common.TRUE);

		return bean;
	}

	public void saveSetting(SettingBean bean) {
		this.bean = bean;
		SettingDA.getInst(App.getInst()).deleteAll();
		SettingDA.getInst(App.getInst()).insertOne(bean);
	}

	public SettingBean getSettingBean() {
		if (null == bean) {
			bean = SettingDA.getInst(App.getInst()).getSettingBean();
			if (bean == null) {
				bean = getDefaultSettingBean();
				SettingDA.getInst(App.getInst()).insertOne(bean);
			}
		}

		return bean;
	}

	/**
	 * 获取精彩内容推送
	 * 
	 * @return
	 */
	public int getIsPush() {
		return getSettingBean().getIs_push();
	}

	/**
	 * 保存精彩内容推送
	 * 
	 * @param is_push
	 */
	public void saveIsPush(int is_push) {
		SettingBean bean = getSettingBean();
		bean.setIs_push(is_push);
		saveSetting(bean);
	}

	/**
	 * 获取字体
	 * 
	 * @return
	 */
	public int getFontSize() {
		return getSettingBean().getFontsize();
	}

	/**
	 * 保存字体
	 * 
	 * @param fontSize
	 */
	public void saveFontSize(int fontSize) {
		SettingBean bean = getSettingBean();
		bean.setFontsize(fontSize);
		saveSetting(bean);
	}

	/**
	 * 保存无图模式
	 * 
	 * @param model
	 */
	public void saveNoPicModel(int model) {
		SettingBean bean = getSettingBean();
		bean.setIs_no_pic_model(model);
		saveSetting(bean);
	}

	/**
	 * 获取无图模式
	 * 
	 * @return
	 */
	public int getNoPicModel() {
		return getSettingBean().getIs_no_pic_model();
	}

	/**
	 * 保存夜间模式
	 * 
	 * @param model
	 */
	public void saveNightModel(int model) {
		SettingBean bean = getSettingBean();
		bean.setIs_night_model(model);
		saveSetting(bean);
	}

	/**
	 * 获取夜间模式
	 * 
	 * @return
	 */
	public int getNightModel() {
		return getSettingBean().getIs_night_model();
	}

	public static enum FontSize {
		Small(14, "小"), Middle(18, "中"), Large(22, "大"), ExtraLarge(26, "特大"), ;

		private int size;
		private String str;

		private FontSize(int size, String str) {
			this.size = size;
			this.str = str;
		}

		public int getSize() {
			return size;
		}

		public String getStr() {
			return str;
		}

		public static FontSize getFontSize(int size) {
			FontSize f = Middle;
			for (FontSize v : values()) {
				if (v.size == size) {
					f = v;
					break;
				}
			}
			return f;
		}
	}
	
	public void getPushConfigByHttp(final Context context) {
		final SettingBean mSettingBean = getSettingBean();
		RequestParams params = new RequestParams();
		params.put("devicetoken", AVInstallation.getCurrentInstallation()
				.getInstallationId());

		HttpUtil.post(JUrl.SITE + JUrl.URL_GET_PUSH_CONFIG, params,
				new MyJsonHttpResponseHandler() {

					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {
//						Gson gson = new Gson();
//						PushConfigBean configBean = gson.fromJson(data,
//								new TypeToken<PushConfigBean>() {
//								}.getType());
//						mSettingBean.setConfig_push_enable(configBean
//								.getEnable());
//						mSettingBean.setConfig_push_start_time(configBean
//								.getStart_time());
//						mSettingBean.setConfig_push_end_time(configBean
//								.getEnd_time());
//						mSettingBean.setConfig_push_is_at(configBean.getIs_at());
//						mSettingBean.setConfig_push_is_dig(configBean
//								.getIs_dig());
//						mSettingBean.setConfig_push_is_reply(configBean
//								.getIs_reply());
//						mSettingBean.setConfig_push_is_pm(configBean.getIs_pm());
//						mSettingBean.setConfig_push_is_fan(configBean
//								.getIs_fan());
//						mSettingBean.setIs_push(configBean.getIs_splendid());
//						mSettingBean.setConfig_reply_access(configBean
//								.getReply_access());
//
//						SettingManager.getInst().saveSetting(mSettingBean);

					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {
						UIHelper.showToast(context, message);
					}
				});
	}
}
