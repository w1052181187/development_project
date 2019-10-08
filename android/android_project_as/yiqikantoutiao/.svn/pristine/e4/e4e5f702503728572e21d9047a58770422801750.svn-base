package com.chengning.yiqikantoutiao.util;

import android.os.Environment;

import com.chengning.common.base.util.BaseJUrl;

public class JUrl extends BaseJUrl{
	
	// TODO(online)
	public static final boolean IS_ONLINE = true;

	public static final int ERR_CODE_SUCCESS = 0;
	public static final int ERR_CODE_NOT_LAUNCHED = -99;
	
	public static String FilePathTemp = Environment.getExternalStorageDirectory().getAbsolutePath()+"/yiqikantoutiao/temp";
	public static String FilePathImageCache = Environment.getExternalStorageDirectory().getAbsolutePath()+"/yiqikantoutiao/cache/image";
	public static String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/yiqikantoutiao";
	public static String sampleFilePath = FilePath + "/sample";
	public static String SHARE_IMG_FILE_PATH = FilePath + "/share_img";

	
	public static final int Login_From_Sina = 1;
	public static final int Login_From_QQ = 2;
	public static final int Login_From_WeiXin = 3;
	public static final int Login_From_Phone = 4;
	public static final int Action_Bind = 10;
	public static final int Action_Login = 11;
	public static final int Action_LoginAfterBindPhone = 12;

	public static final String KEY_TID = "tid";
	public static final String KEY_UID = "uid";
	public static final String KEY_NEWID = "newid";

	public static final String URL_CHANGE_SITE = "http://appif.7zhan.com/kantoutiao.js";

	public static String DEFAULT_SITE = "http://if.biyele.com/";
	public static final String SITE_TEST = "http://test.yiqikantoutiao001.com:802/";
	public static final String API = "api1.0/";
	public static String SITE = IS_ONLINE ? DEFAULT_SITE : SITE_TEST;

	public static final String URL_GET_CHANNEL_RECOMMEND = API + "topic.php?code=recdtopicnew&act=index";
	public static final String UPLOAD_MY_CHANNEL = API + "mobile_member.php?mod=channel_update";

	//专栏作家
	public static final String URL_GET_COLUMNIST = API + "mobile_member.php?mod=find_user_list&qid=6";
	
	// 启动广告
	public static final String URL_GET_AD_START = API + "mobile_member.php?mod=initial";
	
	/**
	 * 指挥部
	 */
	public static final String DRRECTORATE_URL = API + "mobile_member.php?mod=headquarters";
	public static final String DIRECTORATE_TASK_DETAIL = API + "mobile_member.php?mod=task_details";
	public static final String URL_GET_LEVEL_LIST = API + "mobile_member.php?mod=level_list";
	
	/**
	 * 获取用户信息
	 */
	public static final String Get_USER_INFO = API + "mobile_member.php?mod=user_info&uid=";
	public static final String Get_USER_INFO_BY_NICKNAME = API + "mobile_member.php?mod=user_info&nickname=";
	public static final String GET_USER_INFO_BY_IDS = API + "mobile_member.php?mod=user_info_arr";
	//登录相关接口
	/**
	 * 登录
	 */
	public static final String URL_LOGIN = API + "mobile_member.php?mod=login";

	public static final String URL_BIND_PHONE_BYUID = API + "mobile_member.php?mod=edit_phone";

	public static final String URL_REGISTER_PHONE = API + "mobile_member.php?mod=check_phone";
	/**
	 * 修改头像
	 */
	public static final String Edit_Face = API+"mobile_member.php?mod=upload_face";
	public static final String Get_Pwd_Key = API + "mobile_member.php?mod=get_password_smssend";
	public static final String Get_Pwd_Ver = API + "mobile_member.php?mod=get_password_smssend_ok";
	public static final String Set_NewPwd = API + "mobile_member.php?mod=get_password_smssend_edit";
	public static final String URL_GET_PHONE_VERCODE = API + "mobile_member.php?mod=send_sms";
	public static final String Bind_Edit_Sina = API + "mobile_member.php?mod=xwblogin_bind";
	public static final String Bind_Edit_WeiXin = API + "mobile_member.php?mod=wxlogin_bind";
	public static final String Bind_Edit_QQ = API + "mobile_member.php?mod=qqlogin_bind";
	public static final String Bind_User = API + "mobile_member.php?mod=register";
	public static final String Bind_QQ = API + "mobile_member.php?mod=edit_qq";
	public static final String URL_LOGIN_QQ = API + "mobile_member.php?mod=qqlogin";
	public static final String Bind_Sina = API + "mobile_member.php?mod=edit_xwb";
	public static final String URL_LOGIN_Sina = API + "mobile_member.php?mod=xwblogin";
	public static final String Bind_WeiXin = API + "mobile_member.php?mod=edit_wx";
	public static final String URL_LOGIN_WEIXIN = API + "mobile_member.php?mod=wxlogin";
	public static final String URL_GET_SHARE = API + "mobile_member.php?mod=topic_share";
	public static final String Get_Bind_State = API + "mobile_member.php?mod=show_bind&uid=";
	public static final String Edit_NewPwd = API + "mobile_member.php?mod=edit_password";
	public static final String TASK_HINT_URL = API + "mobile_member.php?mod=task_details_msg";
	public static final String OUT_LOGIN = API + "mobile_member.php?mod=loginout";
	public static final String URL_REMOVE_BIND = API + "mobile_member.php?mod=cancel_bind";

	public static final String URL_USEPROTO = "http://www.biyele.com/proto.html";
	
	//文章相关接口
	public static final String URL_GET_ARTICLES_SUBCSRIBE = API + "topic_subscribe.php?code=details&tid=";
	public static final String URL_DO_LIKE = API + "topicaction.php?code=dig";
	public static final String URL_CANCLE_LIKE = API + "topicaction.php?code=cancledig";
	public static final String URL_DONOT_LIKE = API + "topicaction.php?code=dobadpost";
	public static final String URL_DO_COMMENT_PUBLISH = API + "topicaction.php?code=reply";
	public static final String URL_GET_ARTICLE_COMMENT = API + "topic_subscribe.php?code=reply_list";
	public static final String URL_DO_REPORT = API + "topicaction.php?code=do_report";
	public static final String URL_CANCEL_COLLECT = API + "topicaction.php?code=canclefavor";
	public static final String URL_DO_COLLECT = API  + "topicaction.php?code=favor";
	public static final String URL_GET_MY_FAVORITE = API + "topic.php?code=favorite";
	public static final String URL_GET_REPLY_DETAIL = API + "topic_subscribe.php?code=reply_detail";

	public static final String URL_GET_VIDEO_CHANNEL =API + "topic.php?code=videotopic";
	public static final String URL_GET_NEW_ARTICLE_COUNT =API + "topic.php?code=newtopic";
	
	//打赏相关接口
	public static final String URL_GET_REWARD = API + "topic.php?code=reward_money";
	public static final String URL_DO_CREATEORDER = API + "topicaction.php?code=create_orderno";
	
	/**
	 * 圈子相关接口
	 */
	public static final String URL_FOLLOW_CHANGE = API + "mobile_member.php?mod=buddyfollow";
	public static final String URL_DO_DELETE_DYNAMIC = API + "topicaction.php?code=delete";
	public static final String URL_CIRCLE_EDIT_BG = API + "mobile_member.php?mod=profile_image";
	
	/**
	 *  我的推送配置
	 */
	public static final String URL_GET_PUSH_CONFIG = API + "topicaction.php?code=push_setting_info";
	public static final String URL_DO_PUSH_CONFIG = API + "topicaction.php?code=push_setting";
	public static final String URL_DO_PUSH_CONFIG_TIME = API + "topicaction.php?code=push_time_setting";
	/**
	 * 评论配置
	 */
	public static final String URL_DO_COMMENT_PERMISSION_CONFIG = API + "topicaction.php?code=reply_setting";
	/**
	 * 	修改用户信息
	 */
	public static final String URL_EDIT_GENDER = API + "mobile_member.php?mod=sex_edit";
	public static final String URL_EDIT_MYDESCRIPTION= API + "mobile_member.php?mod=aboutme_edit";
	public static final String URL_EDIT_NICKNAME = API + "mobile_member.php?mod=edit_nickname";
	
	//专栏作家文章
	public static final String URL_GET_MILITARY_FANS_FANS = API + "mobile_member.php?mod=fansids_list";
	// 军迷（关注）
	public static final String URL_GET_MILITARY_FANS_FOLLOW = API + "mobile_member.php?mod=buddyids_list";
	// 军迷（关注的人 + 粉丝列表）
	public static final String URL_GET_MILITART_FANS = API + "mobile_member.php?mod=buddyandfans_user";
	
	// 消息相关接口
	// 消息的推送
	public static final String URL_PUSH = API + "topic.php?code=polling";
	
	//聊天
	public static final String URL_GET_USER_REPORT_REASON = API + "topicaction.php?code=report_member";
	public static final String URL_DO_REPORT_USER = API + "topicaction.php?code=do_report_member";
	public static final String URL_GET_COLUMN_MY_FOCUSE = API + "topic.php?code=myfollowcolumntopic";
	public static final String URL_GET_STATE_IOS = API + "mobile_member.php?mod=is_pm";

	public static final String URL_GET_SEARCH = API + "topic.php?code=searchtopic";
	public static final String URL_ARTICLE_READ_FINISH = API + "member_related.php?mod=read";
	public static final String URL_VIDEO_READ_FINISH = API + "member_related.php?mod=read";

	//task
	public static final String URL_TASK_SHARE_CALLBACK = API + "share_callback.php";
	//task

	public static String appendTid(String str, String id){
		return append(str, KEY_TID, String.valueOf(id));
	} 
	public static String appendUid(String str, String id){
		return append(str, KEY_UID, String.valueOf(id));
	} 
	public static String appendNewid(String str, String cid){
		return append(str, KEY_NEWID, String.valueOf(cid));
	} 
	
}
