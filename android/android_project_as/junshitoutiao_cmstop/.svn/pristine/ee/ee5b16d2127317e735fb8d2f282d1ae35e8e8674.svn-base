package com.cmstop.jstt.utils;

import android.content.Context;

import com.chengning.common.base.util.BaseSPHelper;
import com.cmstop.jstt.App;

public class SPHelper extends BaseSPHelper{
	
	public static final String PREFIX_TIME_UPDATE_CHANNEL = "time_update_channel_";
	
	public static final String PREFIX_KEY_CHANNEL_MAXPAGE = "channel_maxpage_";
	public static final String PREFIX_KEY_CHANNEL_IS_RECOM = "channel_is_recom_";
	public static final String PREFIX_KEY_CHANNEL_MAXID = "channel_maxid_";
	
	public static final String KEY_IS_FIRST_RUN = "is_first_run";
	
	public static final String KEY_CHANNEL_LIST = "channel_list";
	
	public static final String KEY_OFFLINE_DOWNLOAD_TIME = "offline_download_time";

	public static final String KEY_BAIDU_AD_APPID = "baidu_ad_appid";
	public static final String KEY_AD_START_IMAGE = "ad_start_image";
	public static final String KEY_AD_START_TITLE = "ad_start_title";
	public static final String KEY_AD_START_URL = "ad_start_url";
	public static final String KEY_DATEFORMAT = "dateformat";
	
	public static final String KEY_COMMENT_USERNAME = "comment_username";
	
	public static final String KEY_HOME_TAB_INDEX_CACHE = "home_tab_index_cache";

	public static final String KEY_HOME_NO_PIC_MODEL = "home_no_pic_model";
	public static final String KEY_HOME_VIDEO_NO_PIC_MODEL = "home_video_no_pic_model";
	public static final String KEY_HOME_NIGHT_MODEL = "home_night_model";
	public static final String KEY_ACCOUNT_CENTER_NIGHT_MODEL = "account_center_night_model";
	public static final String KEY_COUNT_COMMENT_SKIP_LOGIN = "count_comment_skip_login";
	public static final String KEY_FAVORITE_MERGE_KNOW = "favorite_merge_know";

	public static final String KEY_APP_VERSION = "app_version";

	public static final String KEY_PUSH_MSG_COUNT_PREFIX = "push_msg_count_";
	public static final String KEY_FIRST_COMMENT_LIST = "first_comment_list";
	public static final String KEY_VIDEO_URL = "video_url";
	public static final String KEY_MALL_URL = "mall_url";
	public static final String KEY_ORDER_URL = "order_url";
	public static final String KEY_MALL_LOGOUT_URL = "logout_url";
	// 首次引导 下载
	public static final String KEY_GUIDE_DOWNLOAD_SHOW = "guide_download_show";
	public static final String KEY_GUIDE_DOWNLOAD_IMAGE_URL = "guide_download_image_url";
	public static final String KEY_GUIDE_DOWNLOAD_URL = "guide_download_url";
	public static final String KEY_GUIDE_DOWNLOAD_NAME = "guide_download_name";
	public static final String KEY_GUIDE_DOWNLOAD_PACKAGE_NAME = "guide_download_package_name";
	
	public static final String BADGE_KEY_NEW_ARTICLE_IS_POLLING = "badge_new_article_is_polling";
	public static final String BADGE_KEY_NEW_ARTICLE_POLLING_TIME = "badge_new_article_polling_time";
	public static final String BADGE_KEY_NEW_ARTICLE_COUNT = "badge_new_article_count";
	public static final String BADGE_KEY_BADGE_ID = "badge_id";
	
	//搜索历史
	public static final String SEARCH_NEWS_HISTORY = "search_news_history";
	
	/**
	 * 独家导航
	 */
	public static final String KEY_CHANNEL_EXCLUSIVE_NAV = "channel_exclusive_nav_list";
	
	public static final String KEY_LEANCLOUD_DATA_VERSION = "leancloud_data_version";
	
	/**
	 * 登录还是绑定
	 */
	public static final String WX_LOGIN_TYPE = "wx_login_type";
	public static final String KEY_WEIBO_AUTH = "weibo_isAuthed";
	
	public static final String KEY_CACHE_SITE = "cache_site";
	
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
