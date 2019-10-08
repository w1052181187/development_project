package com.cmstop.jstt;

import org.apache.http.Header;
import org.json.JSONObject;

import android.util.Log;

import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

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
