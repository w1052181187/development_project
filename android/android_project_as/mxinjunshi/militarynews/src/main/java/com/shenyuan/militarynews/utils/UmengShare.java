package com.shenyuan.militarynews.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.util.BaseUmengShare;
import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.RequestParams;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.Const.PointActionType;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.umeng.analytics.MobclickAgent;

public class UmengShare extends BaseUmengShare{
	
	private static UmengShare share;
	
	public static UmengShare getInstance(){
		if(share == null){
			share = new UmengShare();
		}
		return share;
	}
	
	public static String handleImageUrlWithBean(ArticlesBean bean){
		String imgUrl = null;
		if(!Common.isListEmpty(bean.getPics())){
			imgUrl = handleImageUrl(bean.getPics());
		}else{
			imgUrl = bean.getImage();
		}
		return imgUrl;
	}
	
	public static String handleImageUrl(ArrayList<String> imgUrls){
		String imgUrl = null;
		if(imgUrls != null){
			for(String s : imgUrls){
				if(!TextUtils.isEmpty(s)){
					imgUrl = s;
					break;
				}
			}
		}
		return imgUrl;
	}
	
	/**
	 * 微信好友或者朋友圈分享成功后获取积分、金币
	 * @param context
	 * @param id
	 */
	// 分享类型 1微信朋友 2微信朋友圈 3QQ3 4QQ空间 5新浪 6腾讯微博
	public static void getPointByShare(final BaseFragmentActivity context, String id, int type) {
		RequestParams params = new RequestParams();
		params.put("aid", id);
		HttpUtil.get(context, JUrl.SITE + JUrl.URL_GET_SHARE, params, new MyStatusResponseHandler() {			
			
			@Override
	        public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) { 
	             
	            Common.handleHttpFailure(context,throwable);
				context.finish();
	        };

			@Override
	        public void onFinish() { 
	        };

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				context.finish();
				Intent intent = new Intent(Const.ACTION_WX_SHARE_SUCESS);
				intent.putExtra("data", data);
				context.sendOrderedBroadcast(intent, null);
				
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(context, message);
				context.finish();
			}; 
	    }); 
		
	}

	public static void getPointByHttp(final BaseFragmentActivity context, String id, int type) {
		RequestParams params = new RequestParams();
		params.put("aid", id);
		HttpUtil.getClient().get(context, JUrl.SITE + JUrl.URL_GET_SHARE, params, new MyStatusResponseHandler() {			
			
			@Override
	        public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) { 	             

	        };

			@Override
	        public void onFinish() { 
	        };

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				try {
					TaskUpdateUtil.showHints(context, data, PointActionType.SHARE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(context, message);
			}; 
	    }); 
		
	}

	@Override
	public void handleEventAnalytics(Activity activity, String eventtype, BaseShareBean bean) {
		Map<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("type", eventtype);
//		mHashMap.put("article_id", bean.getTid());
		MobclickAgent.onEvent(activity, "artilce_share", mHashMap);
	}

	@Override
	public BaseShareBean translateObjectToShareBean(Object... objs) {
		BaseShareBean bean = null;
		Object obj = objs[0];
		if (obj instanceof ArticlesBean) {
			bean = new BaseShareBean();
			ArticlesBean articlesBean = (ArticlesBean) obj;
			bean.setAid(articlesBean.getTid());
			bean.setTitle(articlesBean.getTitle());
			bean.setLink(articlesBean.getLink());
			bean.setImageUrl(handleImageUrlWithBean(articlesBean));
		}
		return bean;
	}

}
