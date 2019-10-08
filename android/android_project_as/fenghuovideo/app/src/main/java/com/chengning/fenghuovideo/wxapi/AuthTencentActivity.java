package com.chengning.fenghuovideo.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.LoginManager;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.activity.AccountEditActivity;
import com.chengning.fenghuovideo.data.bean.LoginUserBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.loopj.android.http.RequestParams;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

public class AuthTencentActivity extends BaseFragmentActivity {

	private static final int Auth_Login_Faild = 1;
	private static final int Auth_Login_Scuess = 0;

	private int mAction;

	private UMShareAPI mShareAPI;
	
	private Map<String, String> mBundle;

	private String mNickname;

	protected String mFace;

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
		Intent i = getIntent();
		mAction = i.getIntExtra("action", 0);
		mShareAPI = UMShareAPI.get(getActivity());
		getInfo();
	}

	protected void getInfo() {
		mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, new UMAuthListener() {
			@Override
			public void onComplete(SHARE_MEDIA arg0, int status, Map<String, String> info) {
				if (info != null) {
					String access_token = info.get("accessToken");
					LoginUserBean bean = LoginManager.getInst()
							.getLoginUserBean();
					bean.setQq_token(access_token);
					LoginManager.getInst().saveLoginUserBean(bean);
					mBundle = info;
//					Log.e("info", info.toString());
//					Log.e("info_access", info.get("access_token"));
					mNickname = info.get("name");
					mFace = info.get("iconurl");
					switch (mAction) {
					case JUrl.Action_Bind:
						Bind(mBundle);
						break;
					case JUrl.Action_Login:
						Login(mBundle);
						break;
					}
				} else {
					UIHelper.showToast(getActivity(), getString(R.string.umeng_share_err_qq_login));
					loginFailed();
				}
				
			}
			
			@Override
			public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
				loginFailed();
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0, int arg1) {
				UIHelper.showToast(getActivity(), getString(R.string.umeng_share_cancle_userinfo));
				loginFailed();
			}

			@Override
			public void onStart(SHARE_MEDIA arg0) {
			}
		});
	}

	@Override
	public void installListeners() {

	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
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

	String mResult = "";

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case Auth_Login_Scuess:
			switch (mAction) {
			case JUrl.Action_Bind:
				Intent i = new Intent();
				mResult = (String) msg.obj;
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
			Intent intent = new Intent();
			intent.putExtra("login_param", (Bundle) msg.obj);
			intent.putExtra("login_from", JUrl.Login_From_QQ);
			intent.setClass(getActivity(), AccountEditActivity.class);
			this.startActivity(intent);
			this.finish();
		}
	}

	/**
	 * 
	 * @param value
	 */
	public void Bind(Map<String, String> value) {

		RequestParams params = new RequestParams();
		params.put("accesstoken", value.get("accessToken"));
		params.put("openid", value.get("openid"));

		HttpUtil.get(getActivity(), JUrl.SITE + JUrl.Bind_QQ, params,
				new MyJsonHttpResponseHandler() {
					// 成功后返回一多个json
					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						Common.handleHttpFailure(getActivity(), throwable);
						loginFailed();
					};

					@Override
					public void onFinish() {
					}

					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {
						String token = data;
						Message msg = getHandler().obtainMessage(
								Auth_Login_Scuess, token);
						msg.sendToTarget();
					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {
						UIHelper.showToast(getActivity(), message);
						loginFailed();
					};
				});
	}

	/**
	 * 登录
	 * 
	 * @param value
	 */
	public void Login(final Map<String, String> value) {
		String url = JUrl.append(JUrl.SITE + JUrl.URL_LOGIN_QQ, "accesstoken",
				value.get("accessToken"));
		url = JUrl.append(url, "openid", value.get("openid"));
		RequestParams params = new RequestParams();
		params.put("devicetype", 2);
		params.put("devicetoken", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		params.put("objectid", AVInstallation.getCurrentInstallation()
				.getObjectId());

		if (Common.isMIUI()) {
			//小米推送唯一标识（暂定）
			params.put("regid", MiPushClient.getRegId(getActivity()));
		}

		HttpUtil.post(getActivity(), url.toString(), params, new MyJsonHttpResponseHandler() {

			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getActivity(), throwable);
				loginFailed();
			};

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Message msg = getHandler().obtainMessage(Auth_Login_Scuess,
						data);
				msg.sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				switch (status) {
				case 1:
					Bundle bundle = new Bundle();
					bundle.putString("nickname", mNickname);
					bundle.putString("face", mFace);
					Message msg = getHandler().obtainMessage(Auth_Login_Faild,
							bundle);
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

	private void loginFailed() {
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
