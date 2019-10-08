package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseActivity;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.widget.TitleBar;

public class AboutActivity extends BaseActivity {

	private TitleBar titleBar;
	private View mLogo;
	private TextView mVersionName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Common.setTheme(this);
		setContentView(R.layout.activity_about);
		super.onCreate(savedInstanceState);
		
	}
	
	private class FastOnClickListener implements OnClickListener{
		
		private long startTime = 0;
		private int count = 0;

		@Override
		public void onClick(View v) {
			if(System.currentTimeMillis() - startTime < 800){
				if(count >= 4){
					DebugActivity.launch(getActivity());
				}else{
					count++;
				}
			}else{
				count = 0;
			}
			startTime = System.currentTimeMillis();
		}
		
	}


	@Override
	public Activity getActivity() {
		return AboutActivity.this;
	}


	@Override
	public void initViews() {
		titleBar = new TitleBar(getActivity(), true);
		titleBar.showDefaultBack();
		titleBar.setTitle("关于");
		
		mLogo = findViewById(R.id.about_logo);
		mVersionName = (TextView) findViewById(R.id.about_version_name);
	}


	@Override
	public void initDatas() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("android ").append(Common.getVersionName(getActivity())).append(" 版本");
		mVersionName.setText(buffer.toString());
	}


	@Override
	public void installListeners() {
		mLogo.setOnClickListener(new FastOnClickListener());
	}


	@Override
	public void processHandlerMessage(Message msg) {
		
	}
}
