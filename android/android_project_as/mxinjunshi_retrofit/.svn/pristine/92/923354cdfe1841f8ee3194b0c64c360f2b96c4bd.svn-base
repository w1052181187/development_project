package com.shenyuan.militarynews.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseActivity;
import com.chengning.common.base.BaseResponseBean;
import com.chengning.common.base.MyRetrofitResponseCallback;
import com.chengning.common.base.util.RetrofitManager;
import com.chengning.common.update.UpdateUtil;
import com.chengning.common.util.StatusBarUtil;
import com.google.gson.Gson;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.event.UserInfoUpdateEvent;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.views.TitleBar;
import com.shenyuan.militarynews.wxapi.AuthTencentActivity;
import com.shenyuan.militarynews.wxapi.AuthWeiboActivity;
import com.shenyuan.militarynews.wxapi.WXEntryActivity;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

;


public class LoginActivity extends BaseActivity {
	
	public static final int RESULTCODE_QQ_FINISH = 0x10;
	public static final int RESULTCODE_QQ_COMPLETE = 0x11;
	public static final int RESULTCODE_SINA_FINISH = 0x12;
	public static final int RESULTCODE_SINA_COMPLETE = 0x13;
	public static final int RESULTCODE_WX_FINISH = 0x14;
	public static final int RESULTCODE_WX_COMPLETE = 0x15;
	public static final int RESULTCODE_PHONEVERIFY = 0x20;
	public static final int RESULTCODE_SKIP = 0x30;
	public static final String ACTION_QQ = "action_qq";
	public static final String ACTION_SINA = "action_sina";
	public static final String ACTION_WX = "action_wx";
	public static final String THIRDNICK = "qqnick";
	public static final String QQ_OPENID = "qq_openid";
	public static final String THIRDFACE  = "qqface";
	public static final String SINAUID = "sinauid";
	public static final String WXID = "wxid";
	
	public static final String FROM = "from";
	public static final int NORMAL = 0x00;
	public static final int HOME = 0x01;
	public static final int COMMENT = 0x02;
	
	private int mFrom = NORMAL;
	private TitleBar mTitleBar;
	private ImageView mLoginWx;
	private ImageView mLoginQq;
	private ImageView mLoginWb;
	private EditText mLoginName;
	private EditText mLoginPw;
	private Button mLoginBtn;
	private Button mLoginSkipBtn;
	private TextView mRegister;
	private TextView mForgetPw;
	
	private String mStrName;
	private String mStrPw;
	
	private int REQUESTCODE_LOGIN_QQ  = 0x00;
	private int REQUESTCODE_LOGIN_SINA = 0x01;
	private int REQUESTCODE_LOGIN_REGISTER = 0x02;
	
	private boolean mIsShowSkip;
	private Intent mIntent;
	private Gson gson;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_login);
		if(Common.isTrue(SettingManager.getInst().getNightModel())){  
			StatusBarUtil.setBar(this, getResources().getColor(R.color.night_bg_color), false);
        }else{  
        	StatusBarUtil.setBar(this, getResources().getColor(R.color.normalstate_bg), true);
        }
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setTitle(getResources().getString(R.string.login));
		
		mLoginWx = (ImageView) findViewById(R.id.login_iv_wx);
		mLoginQq = (ImageView) findViewById(R.id.login_iv_qq);
		mLoginWb = (ImageView) findViewById(R.id.login_iv_weibo);
		mLoginName = (EditText) findViewById(R.id.login_account);
		mLoginPw = (EditText) findViewById(R.id.login_pw);
		mLoginBtn = (Button) findViewById(R.id.login_loginbtn);
		mLoginSkipBtn = (Button) findViewById(R.id.login_skipbtn);
		mRegister = (TextView) findViewById(R.id.login_register);
		mForgetPw = (TextView) findViewById(R.id.login_forget);
	}

	@Override
	public void initDatas() {
		mIntent = getIntent();
		mFrom = mIntent.getIntExtra(FROM, NORMAL);
		mIsShowSkip = mIntent.getBooleanExtra("show_skip", false);
		mLoginSkipBtn.setVisibility(mIsShowSkip ? View.VISIBLE : View.GONE);
		EventBus.getDefault().register(this);
		gson = new Gson();
	}

	@Override
	public void installListeners() {
		mLoginWx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SPHelper.getInst().saveString(SPHelper.WX_LOGIN_TYPE, "login");
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.setClass(getActivity(), WXEntryActivity.class);
				startActivity(intent);
			}
		});
		mLoginQq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), AuthTencentActivity.class);
				startActivityForResult(intent, REQUESTCODE_LOGIN_QQ); 
			}
		});
		mLoginWb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), AuthWeiboActivity.class); 
				startActivityForResult(intent, REQUESTCODE_LOGIN_SINA);
			}
		});
		mLoginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
		mRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getActivity(), RegisterActivity.class), REQUESTCODE_LOGIN_REGISTER);
			}
		});
		mForgetPw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), PwdFindActivity.class));
			}
		});
		mLoginSkipBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULTCODE_SKIP,mIntent);
				finish();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	private void login(){
		mStrName = mLoginName.getText().toString().trim();
		mStrPw = mLoginPw.getText().toString().trim();
		if(TextUtils.isEmpty(mStrName)){
			UIHelper.showToast(getActivity(), getResources().getString(R.string.please_input_account));
			return;
		}
		if(TextUtils.isEmpty(mStrPw)){
			UIHelper.showToast(getActivity(), getResources().getString(R.string.please_input_password));
			return;
		}
		HashMap params = new HashMap<String, String>();
		params.put("userid", mStrName);
		params.put("userpwd", mStrPw);
		params.put("mod", "login");

		if (Common.isMIUI()) {
			//小米推送唯一标识（暂定）
			params.put("regid", MiPushClient.getRegId(getActivity()));
		}
		AVInstallation avInstallation = AVInstallation.getCurrentInstallation();
		params.put("devicetype", 1);
		params.put("devicetoken", avInstallation.getInstallationId());
		params.put("objectid", avInstallation.getObjectId());
		params.put("version", UpdateUtil.getVersionName(getActivity()));

		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().post(JUrl.URL_LOGIN, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {

			@Override
			public void onSubscribe(Disposable d) {
				UIHelper.addPD(getActivity(), "正在登录...");
				super.onSubscribe(d);
			}

			@Override
			public void onComplete() {
				UIHelper.removePD();
				super.onComplete();
			}

			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getActivity(), message);
				loginSuccess(gson, data);
				finish();
			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getActivity(), message);
			}

			@Override
			public void onError(Throwable t) {
				Common.showHttpFailureToast(getActivity());
				super.onError(t);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUESTCODE_LOGIN_QQ && resultCode == RESULTCODE_QQ_FINISH){
			String result = data.getStringExtra("result");
			loginSuccess(gson, result);
			finish();
		}else if(requestCode == REQUESTCODE_LOGIN_QQ && resultCode == RESULTCODE_QQ_COMPLETE){
			startActivityForResult(new Intent(getActivity(), CompleteInfoActivity.class)
				.putExtra(THIRDNICK, data.getStringExtra(THIRDNICK))
				.putExtra(THIRDFACE, data.getStringExtra(THIRDFACE))
				.putExtra(QQ_OPENID, data.getStringExtra(QQ_OPENID))
				.setAction(ACTION_QQ), REQUESTCODE_LOGIN_QQ);
		}else if(requestCode == REQUESTCODE_LOGIN_QQ && resultCode == RESULTCODE_PHONEVERIFY){
			String result = data.getStringExtra("result");
			loginSuccess(gson, result);
			startActivity(new Intent(getActivity(), PhoneVerifyActivity.class));
			finish();
		}else if(requestCode == REQUESTCODE_LOGIN_SINA && resultCode == RESULTCODE_SINA_FINISH){
			String result = data.getStringExtra("result");
			loginSuccess(gson, result);
			finish();
		}else if(requestCode == REQUESTCODE_LOGIN_SINA && resultCode == RESULTCODE_SINA_COMPLETE){
			startActivityForResult(new Intent(getActivity(), CompleteInfoActivity.class)
				.putExtra(THIRDNICK, data.getStringExtra(THIRDNICK))
				.putExtra(THIRDFACE, data.getStringExtra(THIRDFACE))
				.putExtra(SINAUID, data.getStringExtra(SINAUID))
				.setAction(ACTION_SINA), REQUESTCODE_LOGIN_SINA); 
		}else if(requestCode == REQUESTCODE_LOGIN_SINA && resultCode == RESULTCODE_PHONEVERIFY){
			String result = data.getStringExtra("result");
			loginSuccess(gson, result);
			startActivity(new Intent(getActivity(), PhoneVerifyActivity.class));
			finish();
		}else if(requestCode == REQUESTCODE_LOGIN_REGISTER && resultCode == RESULT_OK){
			UIHelper.addPD(getActivity(), "正在登录...");
			String result = data.getStringExtra("result");
			loginSuccess(gson, result);
			UIHelper.removePD();
			finish();
		}
	}
	
    @Subscribe
    public void onEventMainThread(UserInfoUpdateEvent event) {
        //TODO 这里去处理微信回调..
    	if(mFrom == NORMAL){
    		finish();
    	}else if(mFrom == HOME){
    		
    	}else if(mFrom == COMMENT){
    		
    	}
    }

	private void loginSuccess(Gson gson, String data){
		MobclickAgent.onEvent(this, "user_login");
		LoginBean mBean = gson.fromJson(data, LoginBean.class);
		App.getInst().saveLoginBean(mBean);
		EventBus.getDefault().post(new UserInfoUpdateEvent());
	}
	
	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	
	@Override
	public Activity getActivity() {
		return this;
	}
}
