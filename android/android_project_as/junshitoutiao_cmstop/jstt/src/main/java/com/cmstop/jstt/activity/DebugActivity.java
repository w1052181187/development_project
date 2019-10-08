package com.cmstop.jstt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.cmstop.jstt.R;

public class DebugActivity extends BaseFragmentActivity {

	private TextView mInfo;
	
	public static void launch(Activity from){
		Intent intent = new Intent(from, DebugActivity.class);
		from.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState){
		setContentView(R.layout.activity_debug);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void initViews() {
		mInfo = (TextView) findViewById(R.id.debug_info);
	}

	@Override
	public void initDatas() {
		
		StringBuilder sb = new StringBuilder();
		sb
		.append("leancloud InstallationId: ")
		.append(AVInstallation.getCurrentInstallation().getInstallationId())
		.append("\nleancloud ObjectId: ")
		.append(AVInstallation.getCurrentInstallation().getObjectId())
		.append("\nleancloud UpdatedAt: ")
		.append(AVInstallation.getCurrentInstallation().getUpdatedAt())
		.append("\nleancloud Uuid: ")
		.append(AVInstallation.getCurrentInstallation().getUuid())
		;
		mInfo.setText(sb.toString());
	}

	@Override
	public void installListeners() {

	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

}
