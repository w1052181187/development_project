package com.shenyuan.militarynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.igexin.sdk.PushManager;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.SPHelper;
import com.xiaomi.mipush.sdk.MiPushClient;

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
//		if (Common.isMIUI()) {
			sb
			.append("xiaomi push RegId: ")
			.append(MiPushClient.getRegId(getActivity()))
			.append("\nxiaomi push Alias: ")
			.append(MiPushClient.getAllAlias(getActivity()))
			.append("\n\n\n\n")
			;
//		} else {
//			int channel = SPHelper.getInst().getInt(SPHelper.KEY_PUSH_CHANNEL);
//			switch (channel) {
//			case Const.PUSH_CHANNEL_LEANCLOUD:
//				sb
//				.append("leancloud InstallationId: ")
//				.append(AVInstallation.getCurrentInstallation().getInstallationId())
//				.append("\nleancloud ObjectId: ")
//				.append(AVInstallation.getCurrentInstallation().getObjectId())
//				.append("\nleancloud UpdatedAt: ")
//				.append(AVInstallation.getCurrentInstallation().getUpdatedAt())
//				.append("\nleancloud Uuid: ")
//				.append(AVInstallation.getCurrentInstallation().getUuid())
//				.append("\n\n\n\n")
//				;
//				
//				break;
//			case Const.PUSH_CHANNEL_GETUI:
//				sb
//				.append("getui push ClientId: ")
//				.append(PushManager.getInstance().getClientid(getActivity()))
//				.append("\n")
//				;
//				boolean isPushTurnon = PushManager.getInstance().isPushTurnedOn(getActivity());
//				sb
//				.append("getui push turn on/off: ")
//				.append(isPushTurnon)
//				.append("\n")
//				;
//				break;
//
//			default:
//				break;
//			}
//			
//		}
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
		.append("getui push ClientId: ")
		.append(PushManager.getInstance().getClientid(getActivity()))
		.append("\n")
		;
		boolean isPushTurnon = PushManager.getInstance().isPushTurnedOn(getActivity());
		sb
		.append("getui push turn on/off: ")
		.append(isPushTurnon)
		.append("\n")
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
