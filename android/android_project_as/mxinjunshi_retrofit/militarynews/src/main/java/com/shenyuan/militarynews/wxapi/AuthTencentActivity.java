package com.shenyuan.militarynews.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseResponseBean;
import com.chengning.common.base.MyRetrofitResponseCallback;
import com.chengning.common.base.util.RetrofitManager;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.LoginManager;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.activity.LoginActivity;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UIHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

@SuppressLint("ResourceAsColor")
public class AuthTencentActivity extends BaseFragmentActivity {
	
	private static final int AUTH_LOGIN_FAILED = 1;
	private static final int AUTH_LOGIN_SUCCESS = 0;
	protected static final int AUTH_BIND_SUCCESS = 2;
	
	private Activity mContext;

	private UMShareAPI mShareAPI;

	private Map<String, String> mBundle;
	private String mNickname;
	protected String mFace; 
	private String mResult = "";
	
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
		mContext = this; 
		mShareAPI = UMShareAPI.get(getActivity());
		mParams = getIntent().getStringExtra("cmd");
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo = true;
		mShareAPI.setShareConfig(config);
		getInfo(SHARE_MEDIA.QQ);
	}

	protected void getInfo(SHARE_MEDIA platform){
		mShareAPI.getPlatformInfo(mContext, platform, new UMAuthListener() {
			
			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
				if(Const.DEBUG){
					Log.d("Throwable", arg1+ ":" + arg2.toString());
				}
				UIHelper.showToast(getActivity(), getResources().getString(R.string.umeng_share_err_userinfo));
				loginFailed();
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> info) {
				if(info != null){
					mBundle = info;
	                mNickname = info.get("name"); 
	                mFace = info.get("iconurl");
	                if (TextUtils.equals(mParams, "bind")) {
	                	bind(mBundle);
	                } else {
	                	login(mBundle);
	                }
					
	           }else{
	        	   UIHelper.showToast(getActivity(), getResources().getString(R.string.umeng_share_err_qq_login));
	        	   loginFailed();
	          }
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				UIHelper.showToast(getActivity(), getResources().getString(R.string.umeng_share_cancle_userinfo));
				loginFailed();
			}

			@Override
			public void onStart(SHARE_MEDIA arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 绑定qq
	 * @param bundle
	 */
	protected void bind(Map<String, String> bundle) {

		HashMap params = new HashMap<String, String>();
		params.put("accesstoken", bundle.get("accessToken"));
		params.put("openid", bundle.get("openid"));

		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().get(JUrl.URL_BIND_QQ, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {
			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
				getHandler().obtainMessage(
						AUTH_BIND_SUCCESS, data).sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getActivity(), message);
				loginFailed();
			}

			@Override
			public void onError(Throwable t) {
				Common.handleHttpFailure(getActivity(), t);
				loginFailed();
				super.onError(t);
			}
		});

	}

	@Override
	public void installListeners() {   
		
	}
	
	@Override
	public void onStart() {
		super.onStart(); 
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}

	public void onDestroy() { 
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
	}
 
  
	@Override
	public void processHandlerMessage(Message msg) { 
		switch(msg.what){ 
		case AUTH_LOGIN_SUCCESS: 
			Intent i = new Intent();
			mResult =  (String) msg.obj; 
			i.putExtra("result", mResult);
			setResult(LoginActivity.RESULTCODE_QQ_FINISH, i);
			finish();
			break;
		case AUTH_LOGIN_FAILED:
			break;
		case AUTH_BIND_SUCCESS: 
			Intent intent = new Intent();
			mResult = (String) msg.obj;
			intent.putExtra("result", mResult);
			setResult(Const.BIND_RESULT_CODE, intent);
			finish();
			break;
		}
	} 
	
	
	/**
	 * 
	 * @param mBundle
	 */
	public void login(final Map<String, String> mBundle) {

		String url = JUrl.append(JUrl.URL_LOGIN_QQ,"accesstoken", mBundle.get("accessToken"));
		url = JUrl.append(url,"openid", mBundle.get("openid"));

		HashMap params = new HashMap<String, String>();
		LoginManager.appendOtherInfo(getActivity(), params);
		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().post(url, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {
			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
				Message msg = getHandler().obtainMessage(AUTH_LOGIN_SUCCESS, data);
				msg.sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getActivity(), message);
				if(status == -4){
					Intent i = new Intent();
					i.putExtra(LoginActivity.THIRDNICK, mNickname);
					i.putExtra(LoginActivity.THIRDFACE, mFace);
					i.putExtra(LoginActivity.QQ_OPENID, mBundle.get("openid"));
					setResult(LoginActivity.RESULTCODE_QQ_COMPLETE, i);
					finish();
				}else{
					loginFailed();
				}
			}

			@Override
			public void onError(Throwable t) {
				Common.handleHttpFailure(getActivity(), t);
				loginFailed();
				super.onError(t);
			}
		});

	}
	
	private void loginFailed(){ 
		finish();
	}
	
	/**
	 * 授权中返回退出
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
