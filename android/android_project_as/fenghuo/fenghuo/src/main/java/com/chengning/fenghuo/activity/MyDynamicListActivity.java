package com.chengning.fenghuo.activity;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.fragment.UserDynamicFragment;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.widget.TitleBar;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("ResourceAsColor")
public class MyDynamicListActivity extends BaseFragmentActivity {
	
	public static boolean isForeground = false;
	private TitleBar mTitleBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_fragment_with_top);
		super.onCreate(savedInstanceState); 
	}
	
	//在 子类 oncreate 之前运行
	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setTitle("我的圈子");
	} 
	
	@Override
	public void initDatas() {
		FragmentManager mFragmentManager = getSupportFragmentManager();
		UserDynamicFragment fragment =  UserDynamicFragment.newInstace(App.getInst().getUserInfoBean().getUid(), false);
		mFragmentManager.beginTransaction()
			.replace(R.id.activity_content_layout, fragment)
			.commit();
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
	public void onStop(){
		super.onStop();
	}

	public void onDestroy() { 
		super.onDestroy();
	}
 
	public void onResume() {
		super.onResume();
	}
	
	public void onPause() {
		super.onPause();
	}

	@Override
	public void processHandlerMessage(Message msg) {
	}

	@Override
	public FragmentActivity getActivity() {
		return MyDynamicListActivity.this;
	}

}
