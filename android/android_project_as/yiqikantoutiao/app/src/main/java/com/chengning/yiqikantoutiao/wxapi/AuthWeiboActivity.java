/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chengning.yiqikantoutiao.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.AccountEditActivity;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

/**
 * 该类主要演示如何进行授权、SSO登录。
 * 
 * @author SINA
 * @since 2013-09-29
 */
public class AuthWeiboActivity extends BaseFragmentActivity {
  
	private static final int Auth_Login_Faild = 1;
	private static final int Auth_Login_Scuess = 0;
	
    /**是绑定 还是登录**/
    private int mAction;

	private String mResult ="";

	private UMShareAPI mShareAPI;

	protected Bundle bundle;

     
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Common.setTheme(this);
    	this.setContentView(R.layout.activity_tencent_login); 
        super.onCreate(savedInstanceState);   

    }
    
	@Override
	public void initDatas() {
        Intent i = getIntent();
        mAction = i.getIntExtra("action", 0);
    	mShareAPI = UMShareAPI.get(getActivity());
    	getInfo();
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */  
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }
    
    protected void getInfo() {
    	mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, new UMAuthListener() {
			@Override
			public void onComplete(SHARE_MEDIA platform, int status, Map<String, String> info) {
				if(info != null){
					bundle = new Bundle();
                	bundle.putString("uid", info.get("uid"));
                	bundle.putString("expires_in", info.get("expiration"));
                	bundle.putString("access_token", info.get("accessToken"));
					handleOauthSuccess(bundle);
	            }else{
	            	finish();
	            }
			}
			
			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
				finish();
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				finish();
			}

			@Override
			public void onStart(SHARE_MEDIA arg0) {
			}
		});
    	
	}
	protected void handleOauthSuccess(Bundle value) {
    	if (mAction == JUrl.Action_Bind) { 
    		Bind(value);
        }else if (mAction == JUrl.Action_Login) { 
           	Login(value);
        } else {
           	UIHelper.showToast(getActivity(), "程序错误");
           	finish();
        }
		
	}
	
	protected void Bind(Bundle values) {
    	RequestParams params = new RequestParams();
    	params.put("xwuid", values.getString("uid"));
		params.put("expires_in", values.getString("expires_in"));
    	params.put("access_token", values.getString("access_token"));
    	
    	HttpUtil.get(getActivity(), JUrl.SITE + JUrl.Bind_Sina, params, new MyJsonHttpResponseHandler(){
	         @Override
   	         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
	             Common.handleHttpFailure(getActivity(), throwable);
	             finish();
	         };
	         
			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				String token = data.toString();
				Message msg = getHandler().obtainMessage(Auth_Login_Scuess, token);
				msg.sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				finish();
				UIHelper.showToast(getActivity(), message); 
			}; 
		});  
    } 
    
    protected void Login(Bundle values) {
    	
    	String url = JUrl.append(JUrl.SITE + JUrl.URL_LOGIN_Sina, "access_token", values.getString("access_token"));
    	url = JUrl.append(url, "expires_in", values.getString("expires_in"));
    	url = JUrl.append(url, "uid", values.getString("uid"));
       	RequestParams params = new RequestParams();
    	params.put("devicetoken", AVInstallation.getCurrentInstallation().getInstallationId());
    	params.put("devicetype", 2);
    	params.put("objectid", AVInstallation.getCurrentInstallation().getObjectId());
    	if (Common.isMIUI()) {
			//小米推送唯一标识（暂定）
			params.put("regid", MiPushClient.getRegId(getActivity()));
		}
    	
    	HttpUtil.post(getActivity(), url.toString(), params, new MyJsonHttpResponseHandler(){ 
	         @Override
   	         public void onFailure(int statusCode, Header[] headers,
                                   Throwable throwable, JSONObject errorResponse) {
	             Common.handleHttpFailure(getActivity(), throwable);
	             finish();
	         }; 

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				String token = data.toString();
				Message msg = getHandler().obtainMessage(Auth_Login_Scuess, token);
				msg.sendToTarget(); 
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				switch (status) {
				case 1:
					Message msg = getHandler().obtainMessage(Auth_Login_Faild, data);
				    msg.sendToTarget();
					break;
				default:
					UIHelper.showToast(getActivity(), message); 
					finish();
					break;
				} 				
			}; 
		});   
    } 
     
	@Override
	public void processHandlerMessage(Message msg) {
		switch(msg.what) { 
		case Auth_Login_Scuess: 
			switch(mAction) {
			case JUrl.Action_Bind:
				Intent i = new Intent();
				mResult =  (String) msg.obj;
				i.putExtra("result", mResult);
				setResult(Consts.BIND_RESULT_CODE, i);
				this.finish();
				break;
			case JUrl.Action_Login: 
				String uid = (String) msg.obj;
				LoginManager.getInst().getUserInfo(getActivity(), uid);
				break;
			} 
			break;
		case Auth_Login_Faild:
			String json = (String) msg.obj;
			Bundle bundle = new Bundle();
			bundle.putString("json", json);
			Intent intent = new Intent();
			intent.putExtra("login_param", bundle);
			intent.putExtra("login_from", JUrl.Login_From_Sina);
			intent.setClass(getActivity(), AccountEditActivity.class); 
			this.startActivity(intent);
			this.finish();
		}
	} 
	
	
	@Override
	public void initViews() {
		
	}

	@Override
	public void installListeners() {
		
	}

	@Override
	public void uninstallListeners() {
		
	}
	
	/**
	 * 授权中返回推出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		} 
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public BaseFragmentActivity getActivity() {
		return this;
	}
}