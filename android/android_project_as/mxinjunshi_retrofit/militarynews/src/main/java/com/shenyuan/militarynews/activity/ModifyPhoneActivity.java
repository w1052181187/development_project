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

import com.chengning.common.base.BaseActivity;
import com.chengning.common.base.BaseResponseBean;
import com.chengning.common.base.MyRetrofitResponseCallback;
import com.chengning.common.base.util.RetrofitManager;
import com.chengning.common.util.StatusBarUtil;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.views.TitleBar;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

;

public class ModifyPhoneActivity extends BaseActivity {

	private TitleBar mTitleBar;
	private EditText mAccount;
	private EditText mCode;
	private Button mGetCode;
	private Button mRegister;
	
	private String mStrAccount;
	private String mStrCode;
	
	
	private int mPhone_CountDown = 0;
	private Runnable mRunnable;
	private static final int COUNTDOWN = 0x01;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_modifyphone);
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
		
		String mStrPhone = App.getInst().getLoginBean().getMobile();
		if(mStrPhone == null || mStrPhone.equals("") || mStrPhone.length() != 11)
			mTitleBar.setTitle(getResources().getString(R.string.bind_phone));
		else
			mTitleBar.setTitle(getResources().getString(R.string.change_phone));
		
		mAccount = (EditText) findViewById(R.id.pv_et_account);
		mCode = (EditText) findViewById(R.id.pv_et_code);
		mGetCode = (Button) findViewById(R.id.pv_btn_getcode);
		mRegister = (Button) findViewById(R.id.pv_btn_res);
	}

	@Override
	public void initDatas() {
		mRunnable = new Runnable() {
			
			@Override
			public void run() {
                try{    
                    Message message = getHandler().obtainMessage(COUNTDOWN, null);
					message.sendToTarget();
                }catch (Exception e) {  
                	e.printStackTrace();
                }  
			}
		};
	}

	@Override
	public void installListeners() {
		mGetCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getCode();
			}
		});
		mRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}
	
	private void getCode(){
		mStrAccount = mAccount.getText().toString().trim();
		if(TextUtils.isEmpty(mStrAccount)){
			UIHelper.showToast(getActivity(), getResources().getString(R.string.please_input_account));
			return;
		}
		
		if (mPhone_CountDown != 0) { 
			return;
		}
		
		mPhone_CountDown = 60;
		setRequestEnable(false);
		
		HashMap params = new HashMap<String, String>();
		params.put("sms_bind_num", mStrAccount);

		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().post(JUrl.URL_GETPHONECODE, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {

			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getActivity(), message);
			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getActivity(), message);
				setRequestEnable(true);
			}

			@Override
			public void onError(Throwable t) {
				Common.showHttpFailureToast(getActivity());
				setRequestEnable(true);
				super.onError(t);
			}
		});

	}
	
	private void register(){
		mStrAccount = mAccount.getText().toString().trim();
		mStrCode = mCode.getText().toString().trim();
		if(TextUtils.isEmpty(mStrAccount)){
			UIHelper.showToast(getActivity(), getResources().getString(R.string.please_input_phone));
			return;
		}
		if(TextUtils.isEmpty(mStrCode)){
			UIHelper.showToast(getActivity(), getResources().getString(R.string.please_input_code));
			return;
		}

		HashMap params = new HashMap<String,String>();
		params.put("sms_bind_num", mStrAccount);
		params.put("sms_bind_key", mStrCode);

		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().post(JUrl.URL_PHONEVERIFY, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {

			@Override
			public void onSubscribe(Disposable d) {
				UIHelper.addPD(getActivity(), "正在提交...");
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
				LoginBean mBean = App.getInst().getLoginBean();
				mBean.setMobile(data);
				App.getInst().saveLoginBean(mBean);
				Intent intent = new Intent();
				intent.putExtra("result", data);
				setResult(Const.BIND_RESULT_CODE,intent);
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
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case COUNTDOWN:
			mPhone_CountDown--;
			if (mPhone_CountDown > 0) { 
				setRequestEnable(false);
			} else { 
				setRequestEnable(true);
			}
			break;  
		}
	}
	
	private void setRequestEnable(boolean value){
		if(value){
			mPhone_CountDown = 0;
			mGetCode.setText(getResources().getString(R.string.get_code));
			mGetCode.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.common_btn_bg_blue));
		}else{
			mGetCode.setText(mPhone_CountDown + "秒后重新获取");
			mGetCode.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.shape_blue_btn_unenable_bg));
			getHandler().postDelayed(mRunnable, 1000);
		}
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}

}
