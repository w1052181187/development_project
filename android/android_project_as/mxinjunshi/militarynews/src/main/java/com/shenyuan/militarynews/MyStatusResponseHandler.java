package com.shenyuan.militarynews;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.shenyuan.militarynews.utils.JUrl;

public abstract class MyStatusResponseHandler extends JsonHttpResponseHandler {
	
	public MyStatusResponseHandler(){
		super();
		initMyJsonHttpResponseHandler();
	}
	private void initMyJsonHttpResponseHandler(){
		PersistentCookieStore myCookieStore = new PersistentCookieStore(App.getInst());
		HttpUtil.getClient().setCookieStore(myCookieStore);
		
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, JSONObject errorResponse) {
		Log.w("JsonHttpResponseHandler",
				"onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received",
				throwable);
	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			String responseString, Throwable throwable) {
		JSONObject jobj = new JSONObject();
		onFailure(statusCode, headers, throwable, jobj);
	}
	
    @Override 
    public void onSuccess(int statusCode, Header[] headers , JSONObject obj) {
    	if(Const.DEBUG){
    		Log.d("MyStatusResponseHandler", "onSuccess statusCode:" + statusCode + ", obj:" + obj);
    	}
		if(obj != null && obj.has("status")){
			String mod = obj.optString("mod", "");
			String msg = obj.optString("message", "");
			int status = obj.optInt("status");
			String data = obj.optString("data", "");
			switch (status) {
			case 0:
				onDataSuccess(status, mod, msg, data, obj);
				break;
			default:
				onDataFailure(status, mod, msg, data, obj);
				break;
			}
		}else{
			onFailure(statusCode, headers, obj.toString(), null);
		}
    	onSuccess(obj);
    }
    
    public void onSuccess(JSONObject response) {
    	
    }

    public abstract void onDataSuccess(int status, String mod, String message, String data, JSONObject obj);
    public abstract void onDataFailure(int status, String mod, String message, String data, JSONObject obj);

}
