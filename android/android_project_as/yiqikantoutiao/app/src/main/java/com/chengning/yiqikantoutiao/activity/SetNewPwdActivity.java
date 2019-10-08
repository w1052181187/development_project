package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.widget.SwitchButton;
import com.chengning.yiqikantoutiao.widget.TitleBar;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class SetNewPwdActivity extends BaseActivity {

	private SetNewPwdActivity mContext;
	private EditText mNewPwd;
	private EditText mNewPwdAgain;
	private Button mSubmitBtn;
	private Intent i;
	private String mPhone;
	private TitleBar mTitleBar;
	private SwitchButton mPwdSwitch;
	private SwitchButton mPwdSwitchAgain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_pwd_modify);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		UIHelper.removePD();
	}

	@Override
	public void processHandlerMessage(Message msg) {
		
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("设置新密码");
		mTitleBar.showDefaultBack();
		mNewPwd = (EditText)findViewById(R.id.pwdmodify_newpwd);
		mNewPwdAgain = (EditText)findViewById(R.id.pwdmodify_newpwd_again);
		
		mPwdSwitch = (SwitchButton)this.findViewById(R.id.pwdmodify_code_switch);
		mPwdSwitchAgain = (SwitchButton)this.findViewById(R.id.pwdmodify_code_switch_again);
		
		mNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
		mNewPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
		mSubmitBtn = (Button)findViewById(R.id.pwdmodify_submit_btn);
		
	}

	@Override
	public void initDatas() {
		mContext = this;
		i = this.getIntent();
		mPhone = i.getStringExtra("phone");
	}

	@Override
	public void installListeners() {
		
		mPwdSwitch.setChecked(true);
		mPwdSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int p1 = mNewPwd.getSelectionStart();
				
				if (isChecked) {
					mNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
				} else {
					mNewPwd.setTransformationMethod(null); 
				}
				mNewPwd.setSelection(p1);
			}  
		});
		
		mPwdSwitchAgain.setChecked(true);
		mPwdSwitchAgain.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int p2 = mNewPwdAgain.getSelectionStart();
				if (isChecked) {
					mNewPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。
				} else {
					mNewPwdAgain.setTransformationMethod(null);
				}
				mNewPwdAgain.setSelection(p2);
			}  
		});
		
		mSubmitBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String pwd = mNewPwd.getText().toString();
				String pwdAgain = mNewPwdAgain.getText().toString();
				if(Common.hasNet()) {
					if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdAgain)) {
						SetPwd();
					} else {
						UIHelper.showToast(getActivity(), "密码不能为空");
					}
				} else {
					UIHelper.showToast(getActivity(), R.string.intnet_fail);
				}
				
			} 
		}); 
		
		
	}
	
	protected void SetPwd() {
		String pwd = mNewPwd.getText().toString();
		String verpwd = mNewPwdAgain.getText().toString();
		String phone = mPhone;
		UIHelper.addPD(mContext, getResources().getString(R.string.handle_hint));
		RequestParams params = new RequestParams(); 
		params.put("sms_bind_num", phone);
		params.put("password", pwd); 
		params.put("confirm", verpwd); 
		HttpUtil.post(mContext, JUrl.SITE + JUrl.Set_NewPwd, params, new MyJsonHttpResponseHandler() {
	    	  
	         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
	             Common.handleHttpFailure(getActivity(), throwable);
	         }; 
	         
			@Override
			public void onFinish() {
				UIHelper.removePD();
				super.onFinish();
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), "您的新密码已重新设置，请注意保管");
				LoginManager.getInst().logout();
				mContext.finish();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
			};   
		});  
	}
	

	@Override
	public void uninstallListeners() {
		
	}

	@Override
	public Activity getActivity() {
		return SetNewPwdActivity.this;
	}

}
