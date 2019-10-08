package com.chengning.yiqikantoutiao.widget;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.LoginActivity;

public class LoginHintDialog extends BaseDialogFragment{
	
	private View mLoginHint;
	private View mEmptyView;
	private Button mBtnNext;
	private Button mBtnLogin;
	
	@Override
	public void initView() {
		mEmptyView = mLoginHint.findViewById(R.id.loginhint_dialog_empty_view);
		mBtnNext = (Button) mLoginHint.findViewById(R.id.loginhint_dialog_next);
		mBtnLogin = (Button) mLoginHint.findViewById(R.id.loginhint_dialog_login);
	}
	
	@Override
	public void initListener() {
		mEmptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		mBtnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		
		mBtnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
		});

	}

	@Override
	public void initData() {

	}
	
	@Override
	public View configContentView() {
		mLoginHint = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login_hint, null);
		return mLoginHint;
	}
	
	
}
