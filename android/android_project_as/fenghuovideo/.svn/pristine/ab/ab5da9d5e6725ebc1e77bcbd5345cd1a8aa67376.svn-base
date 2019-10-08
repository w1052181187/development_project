package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.LoginManager;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.widget.ModifyPwdSuccessDialog;
import com.chengning.fenghuovideo.widget.ModifyPwdSuccessDialog.ModifyPwdSunccessListener;
import com.chengning.fenghuovideo.widget.SwitchButton;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class PwdModifyActivity extends BaseFragmentActivity {

	protected static final int MODIFY_SUCCESS = 0;
	private EditText mVerPwdEt;
	private Button mSubmitBtn;
	private EditText mPwdEt;
	private SwitchButton mPwdSwitch;
	private TitleBar mTitleBar;
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
		switch (msg.what) {
		case MODIFY_SUCCESS:
			showHintDialog((BaseFragmentActivity) getActivity());
			break;

		default:
			break;
		}
	}

	private void showHintDialog(BaseFragmentActivity activity) {
		ModifyPwdSuccessDialog dialog= new ModifyPwdSuccessDialog();
		dialog.setData(new ModifyPwdSunccessListener() {
			
			@Override
			public void confirm() {
				LoginManager.getInst().logout();
				MainActivity.launchAfterLoginChange(getActivity(), "");
			}
		});
		dialog.showAllowingStateLoss(activity, getSupportFragmentManager(), ModifyPwdSuccessDialog.class.getSimpleName());
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setTitle("修改密码");
		
		mPwdEt = (EditText)this.findViewById(R.id.pwdmodify_newpwd);
		mVerPwdEt = (EditText)this.findViewById(R.id.pwdmodify_newpwd_again);
		mSubmitBtn = (Button)this.findViewById(R.id.pwdmodify_submit_btn); 
		mPwdSwitch = (SwitchButton)this.findViewById(R.id.pwdmodify_code_switch);
		mPwdSwitchAgain = (SwitchButton)this.findViewById(R.id.pwdmodify_code_switch_again);
		
		mPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。  
		mVerPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。  
	}

	@Override
	public void initDatas() {
		mPwdEt.setHint("请输入旧密码");
		mVerPwdEt.setHint("请输入新密码");
	}

	@Override
	public void installListeners() {
		mSubmitBtn.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View v) {
				if (Common.hasNet()) {
					Modify(getActivity());
				} else {
					UIHelper.showToast((BaseFragmentActivity)getActivity(), R.string.intnet_fail);
				}
				 
			} 
		});
		
		mPwdSwitch.setChecked(true);
		mPwdSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener(){ 
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int p1 = mPwdEt.getSelectionStart();
				
				if (isChecked) {
					mPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。  
				} else {
					mPwdEt.setTransformationMethod(null); 
				}
				mPwdEt.setSelection(p1);
			}  
		});
		
		mPwdSwitchAgain.setChecked(true);
		mPwdSwitchAgain.setOnCheckedChangeListener(new OnCheckedChangeListener(){ 
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				int p2 = mVerPwdEt.getSelectionStart();
				if (isChecked) {
					mVerPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());//设置密码为不可见。  
				} else {
					mVerPwdEt.setTransformationMethod(null);
				}
				mVerPwdEt.setSelection(p2);
			}  
		});
	}
	
	protected void Modify(final Activity mContext) { 
		String pwd = mPwdEt.getText().toString();
		String verpwd = mVerPwdEt.getText().toString(); 
		UIHelper.addPD(mContext, getResources().getString(R.string.handle_hint));
		RequestParams params = new RequestParams();  
		params.put("old_password", pwd); 
		params.put("new_password", verpwd); 
		HttpUtil.post(mContext, JUrl.SITE + JUrl.Edit_NewPwd, params, new MyJsonHttpResponseHandler() {
	    	 // 成功后返回一多个json 
	         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
	             Common.handleHttpFailure(mContext, throwable);
				 UIHelper.removePD();
	         };
	         @Override
	         public void onFinish() {
	        	 UIHelper.removePD();
	         }
	         
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				getHandler().obtainMessage(MODIFY_SUCCESS).sendToTarget();
			}
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
				UIHelper.removePD();
			};   
		});
	}
	
	

	@Override
	public void uninstallListeners() {
		
	}

	@Override
	public Activity getActivity() {
		return PwdModifyActivity.this;
	}

}
