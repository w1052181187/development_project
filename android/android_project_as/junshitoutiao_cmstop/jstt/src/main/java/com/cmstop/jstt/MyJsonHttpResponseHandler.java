package com.cmstop.jstt;

import org.apache.http.Header;
import org.json.JSONObject;

import android.util.Log;

import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class MyJsonHttpResponseHandler extends JsonHttpResponseHandler {
	
	public MyJsonHttpResponseHandler()	{
		super();
		initMyJsonHttpResponseHandler();
	}

	public MyJsonHttpResponseHandler(String encoding)	{
		super(encoding);
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
    public void onSuccess(int statusCode, Header[] headers , JSONObject response) {
    	if(Const.DEBUG){
    		Log.d("MyJsonHttpResponseHandler", "onSuccess statusCode:" + statusCode + ", obj:" + response);
    	}
    	onSuccess(response);
    }
    
    public void onSuccess(JSONObject response) {
    	
    }

}
