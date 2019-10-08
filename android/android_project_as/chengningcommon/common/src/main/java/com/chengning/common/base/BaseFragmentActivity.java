package com.chengning.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.baidu.mobstat.StatService;
import com.chengning.common.app.ActivityInfo;
import com.chengning.common.app.ActivityInfoUtil;
import com.umeng.analytics.MobclickAgent;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseFragmentActivity extends SupportActivity implements
		IBaseActivity {

	private Handler mHandler;
	private ActivityInfo mActivityInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivityInfo = new ActivityInfo(this);
		ActivityInfoUtil.onCreate(mActivityInfo);

		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				processHandlerMessage(msg);
			}
		};

		initViews();
		initDatas();
		installListeners();
	}

	@Override
	protected void onStart() {
		super.onStart();
		ActivityInfoUtil.onStart(mActivityInfo);
	}

	@Override
	public void onResume() {
		super.onResume();
		ActivityInfoUtil.onResume(mActivityInfo);
		MobclickAgent.onResume(getActivity());
		if(BaseActivityConfig.getInst().isBaiduStatUsed()){
			StatService.onResume(getActivity());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		ActivityInfoUtil.onPause(mActivityInfo);
		MobclickAgent.onPause(getActivity());
		if(BaseActivityConfig.getInst().isBaiduStatUsed()){
			StatService.onPause(getActivity());
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		ActivityInfoUtil.onStop(mActivityInfo);
	}

	@Override
	public void onDestroy() {
		uninstallListeners();
		ActivityInfoUtil.onDestroy(mActivityInfo);
		super.onDestroy();
	}

	@Override
	public Handler getHandler() {
		return mHandler;
	}

	@Override
	public void launchThisActivity(Activity from) {
		from.startActivity(new Intent(from, getActivity().getClass()));
	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	public ActivityInfo getActivityInfo(){
		return mActivityInfo;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		ActivityInfoUtil.onSaveInstanceState(mActivityInfo);
		super.onSaveInstanceState(outState);
	}

}
