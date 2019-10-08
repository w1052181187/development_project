package com.chengning.fenghuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.util.Common;
import com.xiaomi.mipush.sdk.MiPushClient;

public class DebugActivity extends BaseFragmentActivity {

	private TextView mInfo;
	
	public static void launch(Activity from){
		Intent intent = new Intent(from, DebugActivity.class);
		from.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState){
		Common.setTheme(this);
		setContentView(R.layout.activity_debug);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Activity getActivity() {
		return DebugActivity.this;
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
		.append("\n\n\n\n")
		;
		
		sb
		.append("xiaomi push RegId: ")
		.append(MiPushClient.getRegId(getActivity()))
		.append("\nxiaomi push Alias: ")
		.append(MiPushClient.getAllAlias(getActivity()))
		.append("\n\n\n\n")
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
