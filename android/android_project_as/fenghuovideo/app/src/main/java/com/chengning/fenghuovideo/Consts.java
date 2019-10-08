package com.chengning.fenghuovideo;

import android.view.View;
import android.widget.ImageView;

public class Consts {

	// TODO(debug)
	public static final boolean DEBUG = true;
	
	public static final String UPDATE_APP_KEY = "fenghuovideo";
	public static final String USER_AGENT_PREFIX = "fenghuovideo";
	
	// 性别
	public static final int GENDER_UNKNOWN = 0;
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
	public static final long TIME_WAIT_REFRESH = 20;
	
	public static final String LEANCLOUD_APP_ID = "fV2viIIhEvqdLtMe8tiqvbTQ-gzGzoHsz";
	public static final String LEANCLOUD_APP_KEY = "QJ80vYDVT7910Ej7HlH72abL";
	// @杨洋，指定接收对应版本的推送。
	public static final int LEANCLOUD_DATA_VERSION = 1;

	public static final String XIAOMI_APP_ID = "2882303761517606012";
	public static final String XIAOMI_APP_KEY = "5731760620012";

	public static final String WX_APP_ID = "wx9c0a40c455ff96cc";
	public static final String WX_APP_SECRET = "964b04295ee87737493ae8572e8c27a2";
	public static final String QQ_APP_ID = "1106266387";
	public static final String QQ_APP_KEY = "eOHefXqjwcrZkctb";
	public static final String WEIBO_APP_KEY = "92273689";
	public static final String WEIBO_APP_SECRET = "284ca1fa07fec2a08cddb38b6bfa1bbc";
	public static final String WEIBO_REDIRECT_URL = "http://feng.pp78.com/weibo_cb.php";
	// 阿里
	public static final String ALI_APP_KEY = "24579315";
	public static final String ALI_APP_SECRET = "98f53612130a1bdbf129379a788b5e36";

	public static final String PACKAGE_NAME = Consts.class.getPackage().getName();
	public static final int ROUND_PIX = 10;

	public static final int BIND_RESULT_CODE = 11;

	public static final String ACTION_WX_SHARE_SUCESS = Consts.class.getPackage().getName() + ".action_share_success";
	public static final String ACTION_WX_BIND_SUCESS = Consts.class.getPackage().getName() + ".action_bind_success";
	public static final String ACTION_HOME_FINISH_ACTIVITY = Consts.class.getPackage().getName() + ".action_home_finish_activity";


	/**
	 * show_type 1 1小图 2 三图 3大图视频 4一小图视频类型(嵌套wap) 5 大图广告 6 大图文章 7 一大图二小图文章 
	 */
	public static final int SHOW_TYPE_ONE_SMALL = 1;
	public static final int SHOW_TYPE_THREE_SMALL  = 2;
	public static final int SHOW_TYPE_ONE_BIG_VIDEO = 3;
	public static final int SHOW_TYPE_ONE_SMALL_VIDEO = 4;
	public static final int SHOW_TYPE_ONE_BIG = 6;
	public static final int SHOW_TYPE_ONE_BIG_TWO_SMALL = 7;
//	public static final int NEWS_TYPE_SIX_SMALL_PIC  = 32;
//	public static final int NEWS_TYPE_TWO_BIG_PIC  = 40;
	public static final int SHOW_TYPE_ONE_SMALL_AD = SHOW_TYPE_ONE_SMALL;
	public static final int SHOW_TYPE_ONE_BIG_AD = 5;
	public static final int SHOW_TYPE_THREE_SMALL_AD = SHOW_TYPE_THREE_SMALL;
	
	public static final String  BASE_SCHEME = "com.chengning.fenghuovideo.";
	public static final String  REPLY_DETAIL_SCHEME = BASE_SCHEME + "replyDetail://";

	/**
	 * content_type 文章，视频，wap，广告，图文  
	 */
	public static final String CONTENT_TYPE_NORMAL = "0";
	public static final String CONTENT_TYPE_VIDEO = "1";
	public static final String CONTENT_TYPE_WEB = "2";
	public static final String CONTENT_TYPE_AD = "3";
	public static final String CONTENT_TYPE_TUWEN = "4";

	public enum ArticleCommentType {
		ARTICLE,
		REPLY
	}

	public static final int EMOTION_SIZE_MIDDLE = App.getInst().getResources().getDimensionPixelSize(R.dimen.emotion_size_middle);

	/**
	 * 匹配网址的正则表达式
	 */
	public static final String SITE_COMPILE_STR = "\\[URL\\](.+?)\\[/URL\\]";
	/**
	 * 匹配用户名的正则表达式
	 */
	public static final String NICKNAME_COMPILE_STR = "@([a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+)";
	/**
	 * 匹配主题的正则表达式
	 */
	public static final String TOPIC_COMPILE_STR = "#([a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+)#";
	/**
	 * 匹配表情的正则表达式
	 */
	public static final String EMOTION_COMPILE_STR = "\\[(\\S+?)\\]";

}
