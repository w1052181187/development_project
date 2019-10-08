package com.shenyuan.militarynews.service;

import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.chengning.common.util.HttpUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.beans.data.BadgeBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;

/**
 * @description 更新桌面红点服务
 * @author Administrator
 *
 */
public class UpdateBadgeService extends Service {
	
	public final int MSG_UPDATE_BADGE = 1001;
	public final long UPDATE_TIME = 60*60*1000;
//	public final long UPDATE_TIME = 1*60*1000;
	private int timeInterval; 

	Handler handler = new Handler() {  
        public void handleMessage(Message msg) {  
            switch (msg.what) {
			case MSG_UPDATE_BADGE:
				BadgeBean bean = (BadgeBean) msg.obj;
				Common.showBadge(getApplicationContext(), bean.getNew_count());
				SPHelper.getInst().saveString(SPHelper.BADGE_KEY_BADGE_ID, bean.getMaxnewsid());
				break;

			default:
				break;
			}
            super.handleMessage(msg);  
        };  
    };
    
    Runnable runnable=new Runnable() {  
	    @Override  
	    public void run() {  
	    	String badgeId = SPHelper.getInst().getString(SPHelper.BADGE_KEY_BADGE_ID);
	    	if (TextUtils.isEmpty(badgeId)) {
	    		badgeId = SPHelper.getInst().getString(SPHelper.PREFIX_KEY_CHANNEL_MAXID + "recommend");
	    	}
	    	getBadgeCount(badgeId);
	        handler.postDelayed(this, timeInterval*UPDATE_TIME);  
	    }  
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		if (Common.isTrue(SPHelper.getInst().getInt(SPHelper.BADGE_KEY_NEW_ARTICLE_IS_POLLING))) {
			timeInterval = SPHelper.getInst().getInt(SPHelper.BADGE_KEY_NEW_ARTICLE_POLLING_TIME);
			if (timeInterval > 0) {
				handler.post(runnable);
			}
		}
	    super.onCreate();
	}
	@Override  
    public void onDestroy() {
		handler.removeCallbacks(runnable);   
        super.onDestroy();
    } 

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	void getBadgeCount(String maxid) {
		if (TextUtils.isEmpty(maxid)) {
			return;
		}
		RequestParams params = new RequestParams();
		params.put("maxid", maxid);
		
		HttpUtil.get(JUrl.URL_GET_NEW_ARTICLE_COUNT, params, new MyStatusResponseHandler() {
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Gson gson = new Gson();
				BadgeBean bean = gson.fromJson(data, BadgeBean.class);
				handler.obtainMessage(MSG_UPDATE_BADGE, bean).sendToTarget();
				
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				
			}
		});
	}
}