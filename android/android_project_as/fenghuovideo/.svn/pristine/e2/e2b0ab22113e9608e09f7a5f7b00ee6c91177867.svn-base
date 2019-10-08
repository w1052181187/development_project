package com.chengning.fenghuovideo.util;

import android.content.Context;

import com.chengning.common.base.util.BaseSPHelper;
import com.chengning.fenghuovideo.App;

public class SPHelper extends BaseSPHelper{
	
	public static final String PREFIX_TIME_UPDATE_CHANNEL = "time_update_channel_";
	public static final String PREFIX_KEY_CHANNEL_MAXPAGE = "channel_maxpage_";
	
	public static final String KEY_AD_START_IMAGE = "ad_start_image";
	public static final String KEY_AD_START_TITLE = "ad_start_title";
	public static final String KEY_DATEFORMAT = "dateformat";
	
	public static final String KEY_CHANNEL_LIST = "channel_list";
	public static final String KEY_CHANNEL_LIST_ALL = "channel_list_all";
	public static final String KEY_CIRCLE_CHANNEL_LIST_ALL = "circle_channel_list_all";
	
	public static final String KEY_OFFLINE_DOWNLOAD_TIME = "offline_download_time";
	public static final String KEY_LOGIN_TIME = "LOGIN_TIME";
	public static final String KEY_LOGIN_NAME = "LOGIN_NAME";
	
	public static final String KEY_USER_NAV_LIST_TIME_LOCAL = "user_nav_list_time_local";
	public static final String KEY_USER_ID_LOCAL = "user_id_local";;
	public static final String KEY_USER_NAV_LOCAL = "user_NAV_local";;
	
	public static final String PREFIX_TIME_UPDATE_CIRCLE = "time_update_circle_";
	public static final String PREFIX_KEY_CIRCLE_MAXPAGE = "circle_maxpage_";
	
	public static final String KEY_TIME_UPDATE_MY_MESSAGE_LIST = "time_update_my_message_list";
	
	public static final String KEY_HOME_NOPIC_MODEL = "home_nopic_model";
	public static final String KEY_CIRCLE_NOPIC_MODEL = "circle_nopic_model";
	
	public static final String KEY_CIRCLE_LOGIN_STATE = "circle_login_state";
	
	public static final String KEY_LEANCLOUD_DATA_VERSION = "leancloud_data_version";
	public static final String KEY_REPORT_REASON = "report_reson";
	
	public static final String KEY_PUSH_MSG_COUNT_PREFIX = "push_msg_count_";
	public static final String KEY_COLUMN_FOLLOW_CHANGE_MY = "column_follow_change_my";
	public static final String KEY_COLUMN_FOLLOW_CHANGE_MY_COLUMN = "column_follow_change_my_column";
	public static final String KEY_LOGOUT_CHANGE = "LOGOUT_CHANGE";
	public static final String KEY_LAST_LOGIN_STATE_COLUMN = "LAST_LOGIN_STATE_COLUMN";
	public static final String KEY_LAST_LOGIN_STATE_CIRCLE = "LAST_LOGIN_STATE_CIRCLE";
	public static final String KEY_HOME_NAV_DATA_VERSION = "home_nav_data_version";
	public static final String KEY_APP_VERSION = "app_version";
	
	public static final String PREFIX_KEY_CHANNEL_IS_RECOM = "channel_is_recom_";
	public static final String PREFIX_KEY_CHANNEL_MAXID = "channel_maxid_";
	public static final String KEY_HOME_VIDEO_NO_PIC_MODEL = "home_video_nopic_model";
	
	
	public static final String BADGE_KEY_NEW_ARTICLE_IS_POLLING = "badge_new_article_is_polling";
	public static final String BADGE_KEY_NEW_ARTICLE_POLLING_TIME = "badge_new_article_polling_time";
	public static final String BADGE_KEY_NEW_ARTICLE_COUNT = "badge_new_article_count";
	public static final String BADGE_KEY_BADGE_ID = "badge_id";
	
	public static final String POINT_DIALOG_KEY_IS_SHOW = "point_dialog_is_show";
	
	public static final String KEY_FAV_VERSION = "key_fav_version";
	
	//搜索历史
	public static final String SEARCH_NEWS_HISTORY = "search_news_history";
	public static final String SEARCH_NEWS_KEYWORDS = "search_news_keywords";

	public static final String KEY_CACHE_SITE = "cache_site";

	// 首次引导 下载
	public static final String KEY_IS_FIRST_RUN = "is_first_run";
	public static final String KEY_GUIDE_DOWNLOAD_SHOW = "guide_download_show";
	public static final String KEY_GUIDE_DOWNLOAD_IMAGE_URL = "guide_download_image_url";


	public static final String KEY_REGIST_MONEY = "regist_money";
	public static final String KEY_DIALOG_TIME_NOLOGIN = "dialog_time_nologin";

    public static final String KEY_MY_PRENTICE_URL = "key_my_prentice_url";
    public static final String KEY_MY_INCOME_URL = "key_my_income_url";
    public static final String KEY_MY_TASK_URL = "key_my_task_url";
    public static final String KEY_MY_EXCHANGE_URL = "key_my_exchange_url";
    public static final String KEY_MY_EXCHANGE_LIST_URL = "key_my_exchange_list_url";

    private static SPHelper helper;
	
	public SPHelper(Context context) {
		super(context);
	}
	
	public static SPHelper getInst(){
		if(helper == null){
			synchronized (SPHelper.class) {
				if(helper == null){
					helper = new SPHelper(App.getInst());
				}
			}
		}
		return helper;
	}
	
	public void removeByKey(String key){
		helper.getSharedPreferences().edit().remove(key).commit();
	}
}
