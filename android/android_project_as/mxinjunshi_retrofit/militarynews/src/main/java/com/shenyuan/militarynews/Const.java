package com.shenyuan.militarynews;



public class Const {
	
	// TODO(debug)
	public static final boolean DEBUG = false;
	public static final boolean DEBUG_PUSH_HEAD = false;
	
	public static final String UPDATE_APP_KEY = "xinjunshi";
	public static final String USER_AGENT_PREFIX = "xinjunshi";
	
	public static final String LEANCLOUD_APP_ID = "zja4gnztqflo4xca7y0npre7k64tll6v5snovsx8swvr4a6d";
	public static final String LEANCLOUD_APP_KEY = "k69h2lesdfbd78bcwzzkvf0xa8404hij14dff1wlcledp2jr";
	
	public static final String WX_APP_ID = "wxedd32dba646e749f";
	public static final String WX_APP_SECRET = "0d6890d94cd51ab376804512e70453ba";
	public static final String QQ_APP_ID = "100990706";
	public static final String QQ_APP_KEY = "c1103ff6707df3082756ba9b3f353cd9";
	public static final String WEIBO_APP_KEY = "3496790318";
	public static final String WEIBO_APP_SECRET = "365f0cc17d381d6379e4c748ece8136c";
//	public static final String WEIBO_REDIRECT_URL = "http://www.fenghuo001.com/weibo_cb.php";
	public static final String WEIBO_REDIRECT_URL = "http://www.pp78.com/weibo_cb.php";
	
	public static final String ALI_APP_KEY = "23557478";
	public static final String ALI_APP_SECRET = "b9ccab4e324a42fb9f9a3e3449ad954b";

	public static final String CHANNEL_TUIJIAN = "推荐";
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
	
	// user your xiaomi appid.
    public static final String XIAOMI_APP_ID = "2882303761517133598";
    // user your xiaomi appkey.
    public static final String XIAOMI_APP_KEY = "5551713356598";
    
    // @杨胜利，指定接收对应版本的推送。
 	public static final int LEANCLOUD_DATA_VERSION = 2;
 	
 	public static final int PUSH_TUNNEL_LEANCLOUD = 1;
 	public static final int PUSH_TUNNEL_GETUI = 2;
 	public static final int PUSH_TUNNEL_XIAOMI = 3;
 	
 	public static final int BIND_RESULT_CODE = 11;
}
