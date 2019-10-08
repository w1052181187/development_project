package com.chengning.fenghuo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.fragment.MessageFragment;
import com.chengning.fenghuo.util.Common;

public class MessageActivity extends BaseFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_message);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initDatas() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		MessageFragment mFragment = new MessageFragment();
		ft.replace(R.id.activity_message_root, mFragment);
		ft.commitAllowingStateLoss(); 
	}

	@Override
	public void installListeners() {
		
	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

}
