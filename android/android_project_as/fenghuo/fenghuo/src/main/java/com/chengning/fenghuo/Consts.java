package com.chengning.fenghuo;

import android.view.View;
import android.widget.ImageView;

public class Consts {

	// TODO(debug)
	public static final boolean DEBUG = true;
	
	public static final String UPDATE_APP_KEY = "fenghuo";
	public static final String USER_AGENT_PREFIX = "fenghuo";
	
	// 性别
	public static final int GENDER_UNKNOWN = 0;
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
	public static final long TIME_WAIT_REFRESH = 20;
	
	public static final String LEANCLOUD_APP_ID = "rvPiCR2oGb1ItWJtki0vdUmp";
	public static final String LEANCLOUD_APP_KEY = "9AoXw4vjAYVccSAppH2RROMv";
	// @杨洋，指定接收对应版本的推送。
	public static final int LEANCLOUD_DATA_VERSION = 4;

	public static final String WX_APP_ID = "wxbe677c7d3274fd43";
	public static final String WX_APP_SECRET = "964b04295ee87737493ae8572e8c27a2";
	public static final String QQ_APP_ID = "1103523411";
	public static final String QQ_APP_KEY = "WqX7fF0ccGBufb58";
	public static final String WEIBO_APP_KEY = "1734089476";
	public static final String WEIBO_APP_SECRET = "75fa508c51ed229a84e2a6a545f4ad27";
	public static final String WEIBO_REDIRECT_URL = "http://www.pp78.com/weibo_cb.php";

	public static final String PACKAGE_NAME = Consts.class.getPackage().getName();
	public static final int ROUND_PIX = 10;

	public static final int BIND_RESULT_CODE = 11;

	public static final String ACTION_WX_SHARE_SUCESS = Consts.class.getPackage().getName() + ".action_share_success";
	public static final String ACTION_WX_BIND_SUCESS = Consts.class.getPackage().getName() + ".action_bind_success";
	public static final String ACTION_HOME_FINISH_ACTIVITY = Consts.class.getPackage().getName() + ".action_home_finish_activity";

	public enum ChatAction{
		LIST,
		USER_INFO
		;
	}
	public enum ArticleType {
		ARTICLE,
		DYNAMIC
		;
	}
	
	public enum MessageType{
		AT,
		ZAN,
		COMMENT
		;
	}
	
	public enum CirclePublishType {
		DUANPING("短评"),
		CHANGWEN("长文")
		;
		
		private String str;

		private CirclePublishType(String str) {
			this.setStr(str);
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
		
		
	}
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	public static final String CONVERSATION_ID = "conversitionId";

	public static final String MEMBER_ID = "memberId";
	public static final String CHAT_ENTRY = "action";
	
	
	public static class UserVip{
		
		public static void showVip(String vipPic, ImageView imageView){
			imageView.setVisibility(View.GONE);
//			if(!TextUtils.isEmpty(vipPic)){
//				ImageLoader.getInstance().displayImage(vipPic, imageView);
//				imageView.setVisibility(View.VISIBLE);
//			}else{
//				imageView.setVisibility(View.INVISIBLE);
//			}
		}
	}
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
	
	public static final String  BASE_SCHEME = "com.chengning.fenghuo.";
	public static final String  REPLY_DETAIL_SCHEME = BASE_SCHEME + "replyDetail://";
	/**
	 * 
	 */
	public static final int MSG_STATUS_SUCCESS = 2;
	public static final int MSG_STATUS_FAITURE = 4;
	public static final int MSG_STATUS_SENDING = 1;
	
	/**
	 * content_type 文章，视频，wap，广告，图文  
	 */
	public static final String CONTENT_TYPE_NORMAL = "0";
	public static final String CONTENT_TYPE_VIDEO = "1";
	public static final String CONTENT_TYPE_WEB = "2";
	public static final String CONTENT_TYPE_AD = "3";
	public static final String CONTENT_TYPE_TUWEN = "4";

	// 阿里
	public static final String ALI_APP_KEY = "23584069";
	public static final String ALI_APP_SECRET = "c300d752b9538241d9ec0571d9661bfe";

	public static final String XIAOMI_APP_ID = "2882303761517391894";
	public static final String XIAOMI_APP_KEY = "5121739150894";

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

	public enum ArticleCommentType {
		ARTICLE,
		REPLY
		;
	}

	public static final int TIME_EVERY_SCREEN = 6*1000;
	public static final float EXPECT_RATIO_IMG_HEIGHT = 0.4f;
	public static final double EXPECT_VARIANCE = 0.09d;
	public static final double EXPECT_AVERAGE = 0.5d;
	public static final double EXPECT_AVERAGE_ERROR_RATIO = 0.4;
	public static final double EXPECT_VARIANCE_ERROR_RATIO = 0.4;

	public static final String ENCODE_KEY = "chengning";

	public static final int TASK_SHARE_FRIENDS_QUAN = 1;
	public static final int TASK_SHARE_FRIENDS_QUN = 2;
	public static final int TASK_SHARE_FRIENDS = 3;
	public static final int TASK_SHARE_ARTICLE = 4;


}
