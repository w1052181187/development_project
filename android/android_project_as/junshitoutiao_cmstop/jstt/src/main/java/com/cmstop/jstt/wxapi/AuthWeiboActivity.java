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

package com.cmstop.jstt.wxapi;

import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.cmstop.jstt.Const;
import com.cmstop.jstt.MyStatusResponseHandler;
import com.cmstop.jstt.R;
import com.cmstop.jstt.activity.LoginActivity;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.UIHelper;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 该类主要演示如何进行授权、SSO登录。
 * 
 * @author SINA
 * @since 2013-09-29
 */
public class AuthWeiboActivity extends BaseFragmentActivity {
  
	private static final int AUTH_LOGIN_SUCCESS = 0;
	private static final int AUTH_BIND_SUCCESS = 1;
	
    /**是绑定 还是登录**/
	private String mResult ="";
	private UMShareAPI mShareAPI;
	protected Map<String, String> bundle;
	private String mNickName;
	private String mFace;
	
	private String mParams;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Common.setTheme(this);
    	this.setContentView(R.layout.activity_tencent_login); 
        super.onCreate(savedInstanceState);   
    }
    
	@Override
	public void initViews() {
		
	}

	@Override
	public void initDatas() {
		mShareAPI = UMShareAPI.get(getActivity());
		mParams = getIntent().getStringExtra("cmd");
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo = true;
		mShareAPI.setShareConfig(config);
		getInfo();
	}

	@Override
	public void installListeners() {
		
	}
    
    protected void getInfo() {
    	mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA,  new UMAuthListener() {
			
			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
				handleComplete();
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> info) {
				if(info != null){
					bundle = info;
					mNickName = info.get("name").toString();
					mFace = info.get("iconurl").toString();
					handleOauthSuccess(bundle);
	            }else{
	            	handleComplete();
	            }
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				handleComplete();
			}

			@Override
			public void onStart(SHARE_MEDIA arg0) {
			}
		});
	}
    
	protected void handleOauthSuccess(Map<String, String> bundle) {
//		SPHelper.getInst().saveBoolean(SPHelper.KEY_WEIBO_AUTH, true);
		if (TextUtils.equals(mParams, "bind")) {
        	bind(bundle);
        } else {
        	login(bundle);
        }
		
	}
	
	/**
	 * 绑定微博
	 * @param values
	 */
	protected void bind(Map<String, String> values) {
    	RequestParams params = new RequestParams();
    	params.put("openid", values.get("openid"));
		params.put("expires_in", values.get("expiration"));
    	params.put("access_token", values.get("accessToken"));
    	
    	HttpUtil.get(getActivity(), JUrl.SITE + JUrl.URL_BIND_WEIBO, params, new MyStatusResponseHandler(){ 
	         @Override 
   	         public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) {
	             Common.handleHttpFailure(getActivity(), throwable);
	             handleComplete();
	         };
	         
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				getHandler().obtainMessage(AUTH_BIND_SUCCESS, data).sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				handleComplete();
				UIHelper.showToast(getActivity(), message); 
			}; 
			
		});  
    } 
	
	/**
	 * 登录微博
	 * @param bundle
	 */
	protected void login(final Map<String, String> bundle) {
		String url = JUrl.SITE + JUrl.URL_LOGIN_SINA;
		url = JUrl.append(url, "access_token", bundle.get("accessToken"));
		url = JUrl.append(url, "uid", bundle.get("uid"));
		RequestParams params = new RequestParams();
		params.put("expires_in", bundle.get("expiration"));
		HttpUtil.post(url.toString(), params, new MyStatusResponseHandler() {
			
			@Override
			public void onDataSuccess(int status, String mod, String message, String data, JSONObject obj) {
				String token = data.toString();
				Message msg = getHandler().obtainMessage(AUTH_LOGIN_SUCCESS, token);
				msg.sendToTarget();
			}
			@Override
			public void onDataFailure(int status, String mod, String message, String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
				if(status == -4){
					Intent i = new Intent();
					i.putExtra(LoginActivity.THIRDNICK, mNickName);
					i.putExtra(LoginActivity.THIRDFACE, mFace);
					i.putExtra(LoginActivity.SINAUID, bundle.get("uid"));
					setResult(LoginActivity.RESULTCODE_SINA_COMPLETE, i);
					handleComplete();
				}else{
					handleComplete();
				}
			};
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getActivity(), throwable);
				handleComplete();
			};
		});
    } 
     
	@Override
	public void processHandlerMessage(Message msg) {
		switch(msg.what) { 
		case AUTH_LOGIN_SUCCESS: 
			Intent i = new Intent();
			mResult =  (String) msg.obj; 
			i.putExtra("result", mResult);
			setResult(LoginActivity.RESULTCODE_SINA_FINISH, i);
			handleComplete();
			break;
		case AUTH_BIND_SUCCESS: 
			Intent intent = new Intent();
			mResult =  (String) msg.obj; 
			intent.putExtra("result", mResult);
			setResult(Const.BIND_RESULT_CODE, intent);
			handleComplete();
			break;
		}
		
	} 
	
    private void handleComplete() {
    	finish();
//		SPHelper.getInst().saveBoolean(SPHelper.KEY_WEIBO_AUTH, false);
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */  
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
        	handleComplete();
        }
    }
	
	/**
	 * 授权中返回推出
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			handleComplete();
			return true;
		} 
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public BaseFragmentActivity getActivity() {
		return this;
	}
}