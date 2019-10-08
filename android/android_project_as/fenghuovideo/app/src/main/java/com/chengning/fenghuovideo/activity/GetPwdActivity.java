package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class GetPwdActivity extends BaseActivity {
 
	private int  mPhone_CountDown = 0; 
	private GetPwdActivity mContext;
	private Button mSubmitBtn;
	private Button mCodePushBtn;
	private EditText mCodeEt;
	private EditText mPhoneEt;
	private TitleBar mTitleBar;
	
	private StringBuffer mCount = new StringBuffer();
	private boolean mIsCountDownRun = false;
	private Thread mCounDownThread; 

	private static final int CountDownResult = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_get_pwd);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		UIHelper.removePD();
	}
	
	public void GetCodeViewChange(boolean b) {
		if(b) { 
			mPhone_CountDown = 0;  
			mIsCountDownRun = false; 
			mCodePushBtn.setText(R.string.get_regist_phone_key);
			mCodePushBtn.setTextColor(this.getResources().getColorStateList(R.color.white));
			mCodePushBtn.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_getcodebtn_bg_stated));
		} else {
			mPhone_CountDown = 60;  
			mIsCountDownRun = true;
			mCounDownThread = new Thread(new CountdownThread()); 
			mCounDownThread.start(); 
			mCodePushBtn.setText(R.string.get_regist_phone_key);
			mCodePushBtn.setTextColor(mContext.getResources().getColorStateList(R.color.regist_text_pushing));
			mCodePushBtn.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_timecodebtn_bg));
		}
		
	}
	
	public class CountdownThread implements Runnable {
        @Override  
        public void run(){  
            while(mIsCountDownRun){  
                try{  
                    Thread.sleep(1000);     // sleep 1000ms   
                    Message message = getHandler().obtainMessage(CountDownResult, null);
					message.sendToTarget();
                }catch (Exception e) {  
                }  
            }  
        }  
    }

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case CountDownResult:
			mPhone_CountDown--;
			if(mPhone_CountDown>0) { 
				mCount.setLength(0);
				mCount.append(mPhone_CountDown).append("秒后重新获取");
				mCodePushBtn.setText(mCount.toString());   
			} else { 
				GetCodeViewChange(true);
			}
			break;   
		}
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("找回密码");
		mTitleBar.showDefaultBack();
		mCodePushBtn= (Button)this.findViewById(R.id.getpwd_codepush_btn);
		mSubmitBtn = (Button)this.findViewById(R.id.getpwd_submit_btn);
		mCodeEt = (EditText)this.findViewById(R.id.getpwd_code_et);
		mPhoneEt = (EditText)this.findViewById(R.id.getpwd_phone_et);
	}

	@Override
	public void initDatas() {
		mContext = this; 
		GetCodeViewChange(true);
		
	}
	
	@Override
	public void installListeners() {
		mCodePushBtn.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View v) {
				
				if (Common.hasNet()) {
					if (!TextUtils.isEmpty(mPhoneEt.getText())) {
						if (mPhone_CountDown == 0) { 
							GetCodeViewChange(false);
							GetPhoneKey();
						} else {
							UIHelper.showToast(getActivity(),"60秒内仅发送一次，请稍候再试！");
						}
					} else {
						UIHelper.showToast(getActivity(), "手机号不能为空");
					}
				} else {
					UIHelper.showToast(getActivity(), R.string.intnet_fail);
				} 
			} 
		});
		
		mSubmitBtn.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View v) {
				String code = mCodeEt.getText().toString();
				String phone = mPhoneEt.getText().toString();
				if (Common.hasNet()) {
					if(!TextUtils.isEmpty(code) && !TextUtils.isEmpty(phone)) {
						VerPhoneKey();
					} else {
						UIHelper.showToast(getActivity(),"手机号码和验证码不能为空！");
					}
				} else {
					UIHelper.showToast(getActivity(), R.string.intnet_fail);
				} 
			} 
		}); 
	}
	
	/***
	 * 获得手机验证码
	 */
	protected void GetPhoneKey() { 
		mPhone_CountDown = 60;
		mCodePushBtn.setText(mPhone_CountDown + "秒后重新获取");  
		mCodePushBtn.setBackgroundResource(R.drawable.shape_timecodebtn_bg); 
//		UIHelper.addPD(mContext, "发送中..");
		String phone= mPhoneEt.getText().toString(); 
		RequestParams params = new RequestParams();
		params.put("sms_bind_num", phone); 
		HttpUtil.post(mContext, JUrl.SITE + JUrl.Get_Pwd_Key, params, new MyJsonHttpResponseHandler() {
	    	 
	         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//	             UIHelper.removePD();
	             GetCodeViewChange(true);
	             Common.handleHttpFailure(getActivity(), throwable);
	         }; 
	         
	         @Override
	         public void onFinish() {
//	        	 UIHelper.removePD();
	         }

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);  
//				mRegistKey = data;
//				mCodeEt.setText(mRegistKey);
//				UIHelper.removePD();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);   
				GetCodeViewChange(true);
//				UIHelper.removePD();
			};   
		});
		  
	}
	/**
	 * 验证手机
	 */
	protected void VerPhoneKey() {   
		UIHelper.addPD(mContext, getResources().getString(R.string.handle_hint));
		String phone = mPhoneEt.getText().toString();
		String code = mCodeEt.getText().toString(); 
		RequestParams params = new RequestParams();
		params.put("sms_bind_num", phone);   
		params.put("sms_bind_key", code);
		HttpUtil.post(mContext, JUrl.SITE + JUrl.Get_Pwd_Ver, params, new MyJsonHttpResponseHandler() {
	    	  
	         public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
	             UIHelper.removePD();
	             Common.handleHttpFailure(getActivity(),throwable);
	         }; 
	         
			@Override
			public void onFinish() {
				UIHelper.removePD();
				super.onFinish();
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), "手机验证成功"); 
				Intent i = new Intent();
				i.putExtra("phone", mPhoneEt.getText().toString());
				i.setClass(mContext, SetNewPwdActivity.class);
				mContext.startActivity(i);
				mContext.finish();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				switch(status)
				 { 
				 	default:
			 			UIHelper.showToast(getActivity(), message);   
			 			break;
					case -1:
						mCodeEt.setText("");
						UIHelper.showToast(getActivity(), message);   
						break; 
					case -2:
						mPhoneEt.setText("");
						UIHelper.showToast(getActivity(), message);   
						break;  
					case -5:
						mCodeEt.setText("");
						UIHelper.showToast(getActivity(), message);   
						break; 
					case -6:
						mCodeEt.setText("");
						UIHelper.showToast(getActivity(), message);  
						break;  
				}  
		 		UIHelper.removePD();
			};   
		});  
	}

	@Override
	public void uninstallListeners() {
		
	}
	
	@Override
	public Activity getActivity() {
		return GetPwdActivity.this;
	} 

}
