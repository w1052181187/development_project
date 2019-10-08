package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.SPHelper;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.widget.TitleBar;
import com.chengning.yiqikantoutiao.wxapi.AuthTencentActivity;
import com.chengning.yiqikantoutiao.wxapi.AuthWeiboActivity;
import com.chengning.yiqikantoutiao.wxapi.WXEntryActivity;
import com.loopj.android.http.RequestParams;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginActivity extends BaseFragmentActivity {
 
	private LoginActivity mContext;
	private EditText mNameEdit;
	private EditText mPwdEdit;
	private RelativeLayout mQQBtn;
	private RelativeLayout mSinaBtn;
	private RelativeLayout mWeiXinBtn;
	private TextView mSubmitBtn;
	private TextView mForgetBtn;
	private RelativeLayout mRegistBtn;
	private String loginName;
	private TitleBar mTitleBar;
	 
	public static boolean isLoginedByLogin = false;

	public static void launch(Activity from) {
		Intent intent = new Intent(from, LoginActivity.class);
		from.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState); 
	}
	
	@Override
	public void onDestroy() {
		UIHelper.removePD();
		super.onDestroy(); 
	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	public void initViews() {
		
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("登录");
		mTitleBar.showDefaultBack();
		
		mNameEdit = (EditText)this.findViewById(R.id.login_name_edit);
		mPwdEdit = (EditText)this.findViewById(R.id.login_pwd_edit);
		mQQBtn = (RelativeLayout)this.findViewById(R.id.login_qq_btn);
		mSinaBtn = (RelativeLayout)this.findViewById(R.id.login_sina_btn);
		mWeiXinBtn = (RelativeLayout)this.findViewById(R.id.login_weixin_btn);
		mSubmitBtn = (TextView) this.findViewById(R.id.login_submit_btn);
		mForgetBtn = (TextView)this.findViewById(R.id.login_forget_btn);
		mRegistBtn = (RelativeLayout) this.findViewById(R.id.login_regist_btn);
	}

	@Override
	public void initDatas() {
		
		mContext = this;
		loginName = SPHelper.getInst().getString(SPHelper.KEY_LOGIN_NAME);
		if (!loginName.isEmpty()) {
			mNameEdit.setText(loginName);
		}
		mNameEdit.setSelection(loginName.length());
	} 
	
	@Override
	public void installListeners() {
		
		mForgetBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(mContext,GetPwdActivity.class));
			} 
		});
		mRegistBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(mContext, PhoneRegistActivity.class);
				mContext.startActivity(intent);
			} 
		});
		
		mSinaBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(mContext, AuthWeiboActivity.class); 
				mContext.startActivityForResult(intent, 1);
			} 
		}); 
		
		mQQBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(mContext,AuthTencentActivity.class);
				mContext.startActivityForResult(intent, 1); 
			} 
		}); 
		mWeiXinBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("cmd", "login");
				intent.putExtra("action", JUrl.Action_Login);
				intent.setClass(mContext, WXEntryActivity.class);
				mContext.startActivity(intent);
			} 
		}); 
		
		mSubmitBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (Common.hasNet()) {
					if (TextUtils.isEmpty(mNameEdit.getText()) || TextUtils.isEmpty(mPwdEdit.getText())) {
						UIHelper.showToast(getActivity(), R.string.login_fail_null);  
					} else {
						Login();
					}
				} else {
					UIHelper.showToast(getActivity(),R.string.intnet_fail);
				}
			}
		}); 
	}
	
	public void Login() {  
		UIHelper.addPD(this, "正在登录..");
		String name = mNameEdit.getText().toString();
		String pwd = mPwdEdit.getText().toString();
		RequestParams params = new RequestParams();
		params.put("username", name);
		params.put("password", pwd);
		
		if (Common.isMIUI()) {
			//小米推送唯一标识（暂定）
			params.put("regid", MiPushClient.getRegId(getActivity()));
		}
		params.put("devicetype", "2");
		params.put("devicetoken", AVInstallation.getCurrentInstallation().getInstallationId());
		params.put("objectid", AVInstallation.getCurrentInstallation().getObjectId());
		
		HttpUtil.post(mContext, JUrl.SITE + JUrl.URL_LOGIN, params, new MyJsonHttpResponseHandler() {
	         @Override
	         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
	             UIHelper.removePD(); 
	             Common.handleHttpFailure(getActivity(), throwable); 
	         };  
	         
			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				SPHelper.getInst().saveString(SPHelper.KEY_LOGIN_NAME, mNameEdit.getText().toString());
				LoginManager.getInst().getUserInfo(getActivity(), data);
				isLoginedByLogin = true;
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
						UIHelper.showToast(getActivity(),message); 
						UIHelper.removePD();
			};   
		});
 
	}
	
	@Override
	public void uninstallListeners() {
		
	}
	
	@Override
	public BaseFragmentActivity getActivity() {
		return LoginActivity.this;
	}
	
}
