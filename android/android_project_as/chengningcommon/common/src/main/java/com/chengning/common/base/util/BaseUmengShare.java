package com.chengning.common.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.chengning.common.R;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public abstract class BaseUmengShare {
	
	private Context mContext;
	private UMShareAPI mShareAPI;
	
	private String aid;
	
	private SHARE_MEDIA mMedia;
	
	private BaseUmengShare inst;
	
	public static class BaseSocialPlatform{
		String name;
		int paramCount;
		String[] params;
		
		public BaseSocialPlatform(String name, int paramCount, String... params){
			this.name = name;
			this.paramCount = paramCount;
			this.params = params;
			
			checkParamCount();
		}
		
		public void checkParamCount(){
			if (params.length != paramCount) {
				throw new IllegalArgumentException("params length is " + params.length + ", not match paramCount "
						+ paramCount + " !");
			}
		}
	}
	
	public static class WeixinPlatform extends BaseSocialPlatform{
		
		/**
		 * 配置微信参数（两个） 0：id 1：secret
		 */
		public WeixinPlatform(String... params) {
			super("weixin", 2, params);
		}
	}
	
	public static class SinaPlatform extends BaseSocialPlatform{
		/**
		 * 配置新浪参数（三个） 0：key 1：secret 2：redirecturl
		 */
		public SinaPlatform(String... params) {
			super("sina", 3, params);
		}
	}

	/**
	 * 配置QQ参数（两个） 0：id 1：key
	 */
	public static class QQPlatform extends BaseSocialPlatform{
		public QQPlatform(String... params) {
			super("qq", 2, params);
		}
	}
	
	/**
	 * 初始化 ，必须在app启动时即进行初始化
	 */
	public void init(Context context, BaseSocialPlatform...  platforms){
		mContext = context;
		
		mShareAPI = UMShareAPI.get(mContext);
		
		if(platforms.length > 0){
			for(BaseSocialPlatform sp : platforms){
				if(sp instanceof WeixinPlatform){
					//配置微信
					PlatformConfig.setWeixin(sp.params[0], sp.params[1]);
				}else if(sp instanceof SinaPlatform){
					//配置新浪
					PlatformConfig.setSinaWeibo(sp.params[0], sp.params[1], sp.params[2]);
				}else if(sp instanceof QQPlatform){
					//配置QQ
					PlatformConfig.setQQZone(sp.params[0], sp.params[1]);
				}
			}
		}
		
		//去除友盟分享或授权时自带的dialog..
//		Config.dialogSwitch = false;
		//关闭友盟自带toast提示
//		Log.LOG ＝ false
//		Config.IsToastTip = false;
		
	}
	
	public void checkInit(Context context){
		if(mContext == null || mShareAPI == null){
			init(context);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		checkController();
		mShareAPI.onActivityResult(requestCode, resultCode, data);
	}
	
	public abstract BaseShareBean translateObjectToShareBean(Object... objs);

	/**
	 * 处理事件统计
	 * @param activity
	 * @param eventtype
	 * @param bean
	 */
	public void handleEventAnalytics(Activity activity, String eventtype, BaseShareBean bean){
		
	};
	
	public void shareToWeixin(Activity activity, BaseShareBean bean){
		
		handleEventAnalytics(activity, "weixin", bean);
		if (bean == null) {
			return;
		}
		setAid(bean.getAid());
		setShareMedia(SHARE_MEDIA.WEIXIN);
		String imgUrl = bean.getImageUrl();
		String title = bean.getTitle();
		String url = bean.getLink();

		shareToWeixin(activity, url, imgUrl, title);
	}
	
	public void shareToCircle(Activity activity, BaseShareBean bean){
		
		handleEventAnalytics(activity, "pengyou", bean);
		if (bean == null) {
			return;
		}
		setAid(bean.getAid());
		setShareMedia(SHARE_MEDIA.WEIXIN_CIRCLE);
		String imgUrl = bean.getImageUrl();
		String title = bean.getTitle();
		String url = bean.getLink();

		shareToCircle(activity, url, imgUrl, title);
	}
	
	public void shareToQzone(Activity activity, BaseShareBean bean){
		shareToQzone(activity, null, bean);
	}
	
	public void shareToQzone(Activity activity, UMShareListener listener, BaseShareBean bean){
		
		handleEventAnalytics(activity, "qqzone", bean);
		if (bean == null) {
			return;
		}
		setAid(bean.getAid());
		String imgUrl = bean.getImageUrl();
		String title = bean.getTitle();
		String url = bean.getLink();

		shareToQzone(activity, url, imgUrl, title , listener);
	}
	
	public void shareToWeibo(final Activity activity, final BaseShareBean bean){
        shareToWeibo(activity, null, bean);
	}
	
	public void shareToWeibo(final Activity activity, final UMShareListener listener, BaseShareBean bean){
		
		handleEventAnalytics(activity, "weibo", bean);
		if (bean == null) {
			return;
		}
		setAid(bean.getAid());
		String imgUrl = bean.getImageUrl();
		String title = bean.getTitle();
		String url = bean.getLink();

        shareToWeibo(activity, url, imgUrl, title , listener);
	}
	
	public void shareToQq(Activity activity, final BaseShareBean bean){
		shareToQq(activity, null, bean);
	}
	
	public void shareToQq(Activity activity, UMShareListener listener, BaseShareBean bean){
		handleEventAnalytics(activity, "qq", bean);
		if (bean == null) {
			return;
		}
		setAid(bean.getAid());
		String imgUrl = bean.getImageUrl();
		String title = bean.getTitle();
		String url = bean.getLink();

		shareToQq(activity, url, imgUrl, title , title,listener);
	}

	public void shareToQq(final Activity activity, String url,String imgUrl,String title,String desc,UMShareListener listener){
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.QQ);
		setShareContent(shareAction, url, imgUrl, title, desc);
		if (listener == null) {
			listener = new BaseUMShareListener(activity);
		}
		shareAction.setCallback(listener);
		shareAction.share();

	}

	public void shareImgToQq(Activity activity, String imgUrl, UMShareListener listener) {
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.QQ);
		setShareContent(shareAction, imgUrl);
		if (listener == null) {
			listener = new BaseUMShareListener(activity);
		}
		shareAction.share();
	}
	
	/*
	 * url and ad share
	 */
	public void shareToWeixin(Activity activity, String url,String imgUrl,String title){
		checkController();	
		
		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
		setShareContent(shareAction, url, imgUrl, title, title);
		shareAction.setCallback(new BaseUMShareListener(activity));
		shareAction.share();
	}
	public void shareToWeixin(Activity activity, String url,String imgUrl,String title, String desc){
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
		setShareContent(shareAction, url, imgUrl, title, desc);
		shareAction.setCallback(new BaseUMShareListener(activity));
		shareAction.share();
	}
	public void shareImgToWeixin(Activity activity, String imgUrl){
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
		setShareContent(shareAction, imgUrl);
		shareAction.setCallback(new BaseUMShareListener(activity));
		shareAction.share();
	}
	public void shareImgToCircle(Activity activity, String imgUrl){
		shareImgToCircleWithText(activity,imgUrl,"");
	}
	public void shareImgToCircle(Activity activity, File imgFile){
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		UMImage image = new UMImage(mContext, imgFile);
		shareAction.withMedia(image);
		shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
		shareAction.setCallback(new BaseUMShareListener(activity));
		shareAction.share();
	}
	public void shareImgToCircleWithText(Activity activity, String imgUrl, String text){
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		setShareContent(shareAction, imgUrl,text);
		shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
		shareAction.setCallback(new BaseUMShareListener(activity));
		shareAction.share();
	}
	
	public void shareToCircle(Activity activity,  String url,String imgUrl,String title){
		checkController();

		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
		setShareContent(shareAction, url, imgUrl, title, title);
		shareAction.setCallback(new BaseUMShareListener(activity));
		shareAction.share();
	}
	
	public void shareToWeibo(final Activity activity, String url,
			String imgUrl, String title, UMShareListener listener) {
		checkController();
		ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.SINA);
		setShareContent(shareAction, url, imgUrl, title, title + url);
		if (listener == null) {
			listener = new BaseUMShareListener(activity);
		}
		shareAction.setCallback(listener);
		shareAction.share();
	}
	
	public void shareToQzone(final Activity activity, String url,String imgUrl,String title,UMShareListener listener){
      	checkController();
		
      	ShareAction shareAction = new ShareAction(activity);
		shareAction.setPlatform(SHARE_MEDIA.QZONE);
		setShareContent(shareAction, url, imgUrl, title, title);
		if (listener == null) {
			listener = new BaseUMShareListener(activity);
		}
		shareAction.setCallback(listener);
		shareAction.share();
      	
	}
	
	private void checkController(){
		if(mShareAPI == null){
			mShareAPI = UMShareAPI.get(mContext);
		}
	}
	private void setShareContent(ShareAction shareAction, String imgUrl){
		setShareContent(shareAction,imgUrl,"");
	}
	private void setShareContent(ShareAction shareAction, String imgUrl,String text){

		if (!TextUtils.isEmpty(text)) {
			shareAction.withText(text);
		}
		if (!TextUtils.isEmpty(imgUrl)){
			UMImage image = new UMImage(mContext, imgUrl);
			shareAction.withMedia(image);
		}


	}


	private void setShareContent(ShareAction shareAction, String url,String imgUrl,String title,String content){

		if (shareAction.getPlatform() == SHARE_MEDIA.SINA) {
			if (!TextUtils.isEmpty(title)){
				shareAction.withText(title);
			}
			shareAction.withText(!TextUtils.isEmpty(content) ? content : "");
			if (!TextUtils.isEmpty(imgUrl)){
				shareAction.withMedia(handleShareImage(imgUrl));
			}
			return;
		}

		UMWeb  web = new UMWeb(url);
		if (!TextUtils.isEmpty(title)){
			web.setTitle(title);
		}

		web.setDescription(!TextUtils.isEmpty(content) ? content : "");
		web.setThumb(handleShareImage(imgUrl));
		//缩略图
		shareAction.withMedia(web);
	}
		
	public UMImage handleShareImage(String imgUrl){
        if(!TextUtils.isEmpty(imgUrl)){
        	return new UMImage(mContext, imgUrl);
        }else{
        	return new UMImage(mContext, R.mipmap.ic_launcher);
        }
	}
	
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	
	public SHARE_MEDIA getShareMedia(){
		return mMedia;
	}
	public UMShareAPI getShareApi(){
		return mShareAPI;
	}

	public void setShareMedia(SHARE_MEDIA media){
		this.mMedia = media;
	}
	
	public static class BaseUMShareListener implements UMShareListener{

		private Activity mContext;

		public BaseUMShareListener(Activity mContext) {
			this.mContext = mContext;
		}

		@Override
	    public void onResult(SHARE_MEDIA platform) {
	    	BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_success_share));

	    }

	    @Override
	    public void onError(SHARE_MEDIA platform, Throwable t) {
	    	if (UMShareAPI.get(mContext).isInstall((Activity) mContext, platform) ) {
	    		BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_err_share));
	    	} else {
	    		switch (platform) {
				case WEIXIN:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_wx));
					break;
				case WEIXIN_CIRCLE:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_wx));
					break;
				case QQ:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_qq));
					break;
				case QZONE:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_qq));
					break;

				default:
					break;
				}
	    	}
	    	
	    }

	    @Override
	    public void onCancel(SHARE_MEDIA platform) {
	    	BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_cancle_share));
	    }

		@Override
		public void onStart(SHARE_MEDIA share_media) {
			
		}
		
	}
	
	public static class BaseUMAuthListener implements UMAuthListener{

		private Activity mContext;
		
		public BaseUMAuthListener(Activity mContext) {
			super();
			this.mContext = mContext;
		}
		
		@Override
		public void onCancel(SHARE_MEDIA share_media, int i) {
			
		}

		@Override
		public void onComplete(SHARE_MEDIA share_media, int i,
				Map<String, String> map) {
			
		}

		@Override
		public void onError(SHARE_MEDIA share_media, int status, Throwable throwable) {
			if (UMShareAPI.get(mContext).isInstall((Activity) mContext, share_media) ) {
				BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_err_auth));
	    	} else {
	    		switch (share_media) {
				case WEIXIN:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_wx));
					break;
				case WEIXIN_CIRCLE:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_wx));
					break;
				case QQ:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_qq));
					break;
				case QZONE:
					BaseUIHelper.showToast(mContext, mContext.getString(R.string.umeng_share_install_qq));
					break;

				default:
					break;
				}
	    	}
		}

		@Override
		public void onStart(SHARE_MEDIA share_media) {
			
		}
		
	}
	
	public class BaseShareBean implements Serializable {

		private static final long serialVersionUID = -3050699387684472715L;
		private String aid;
		private String title;
		private String link;
		private String imageUrl;
		
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		
	}
	
}
