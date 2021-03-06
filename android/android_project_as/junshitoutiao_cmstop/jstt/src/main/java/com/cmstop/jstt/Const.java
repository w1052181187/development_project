package com.cmstop.jstt;



public class Const {
	
	// TODO(debug)
	public static final boolean DEBUG = false;
	
	// TODO(jstt)
	public static final String UPDATE_APP_KEY = "junshitoutiao";
	public static final String USER_AGENT_PREFIX = "junshitoutiao";
	
	// ad
	public static final String BAIDU_AD_APPID_DEFAULT = "cfe2775b";
	
	public static final String LEANCLOUD_APP_ID = "jIav8ADqAEfm1BtdIwubRi5g";
	public static final String LEANCLOUD_APP_KEY = "Qnx9z3s7IzSyFTaWsOVtJ956";

	public static final String WX_APP_ID = "wx31de95a6a9cde8c1";
	public static final String WX_APP_SECRET = "32e455c850a8d6bb08e6997d5112fb81";
	public static final String QQ_APP_ID = "1102303039";
	public static final String QQ_APP_KEY = "ZdlYS3XkkyfNAiqY";
	public static final String WEIBO_APP_KEY = "2107833768";
	public static final String WEIBO_APP_SECRET = "2ff46ffe5e0cea440ba7735039728d81";
//	public static final String WEIBO_REDIRECT_URL = "http://www.fenghuo001.com/weibo_cb.php";
	public static final String WEIBO_REDIRECT_URL = "http://www.pp78.com/weibo_cb.php";
	
	public static final String CHANNEL_TUIJIAN = "推荐";
	public static final String CHANNEL_SHOP = "商城";
	public static final String CHANNEL_REDIAN = "热点";
	
	// 资料库
	public static final int CHANNEL_SHOW_TYPE_ZILIAOKU = 1;	
	public static final int CHANNEL_SHOWTYPE_NOT = 2;
	// feeds
	public static final int CHANNEL_SHOWTYPE_BAIDU_FEEDS = 3;
	
	public static final String CHANNEL_ARTICLE_NORMAL = "1";
	public static final String CHANNEL_ARTICLE_TUWEN = "2";
	public static final String CHANNEL_ARTICLE_VIDEO = "18";
	public static final String CHANNEL_ARTICLE_WEB = "98";
	
	public static final int NEWS_TYPE_COMMON = 10;
	public static final int NEWS_TYPE_COMMON_NO_PIC = 11;
	/*** @deprecated 兼容ios */
	public static final int NEWS_TYPE_COMMON_SLIDE = 12;
	public static final int NEWS_TYPE_COMMON_AD = 13;
	public static final int NEWS_TYPE_COMMON_AD_ONE_BIG = 14;
	public static final int NEWS_TYPE_COMMON_AD_THREE_SMALL = 15;
	public static final int NEWS_TYPE_ONE_BIG_PIC = 20;
	public static final int NEWS_TYPE_ONE_BIG_TWO_SMALL_PIC = 30;
	public static final int NEWS_TYPE_THREE_SMALL_PIC  = 31;
	public static final int NEWS_TYPE_SIX_SMALL_PIC  = 32;
	public static final int NEWS_TYPE_TWO_BIG_PIC  = 40;
	
	public static final String ACTION_WX_SHARE_SUCESS = Const.class.getPackage().getName() + ".action_share_success";
	public static final String ACTION_WX_BIND_SUCESS = Const.class.getPackage().getName() + ".action_bind_success";
	
	// 首页手动下拉刷新次数
	public static final String UMEVENT_HOME_MANLOAD = "home_manload";
	// 文章点击		文章区块article_area: normal, new30, assoc, push  	文章id article_id:11111
	public static final String UMEVENT_ARTICLE_CLICK = "article_click";

	public static final String ACTION_COLLECT_SUCESS = Const.class.getPackage().getName() + ".action_collect_success";

	public static final String ALI_APP_KEY = "23575547";
	public static final String ALI_APP_SECRET = "6bd1b955ce97f315da8998b2293b4741";

	public enum PointActionType {
		LOGIN("login"),
		SHARE("share"),
		REPLY("reply"),
		;
		private String action;
		
		PointActionType(String action){
			this.setAction(action);
		}
		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}
	}
	
	public enum LikeAction {
		GOOD,
		BAD,
		;
		
	}
	public static class ArticleCommentType{
		public static final int COMMENT_TYPE_COMMON = 1;
		public static final int COMMENT_TYPE_COMMON_HOT = 2;
		public static final int COMMENT_TYPE_COMMON_NEWEST = 3;
	}
	
	public static final int BIND_RESULT_CODE = 11;
}
