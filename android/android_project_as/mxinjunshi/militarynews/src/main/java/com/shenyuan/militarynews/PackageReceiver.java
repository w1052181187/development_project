package com.shenyuan.militarynews;

import com.shenyuan.militarynews.utils.SPHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 安装
		if (TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_ADDED")) {
//			String packageName = intent.getDataString();
		}
		// 覆盖安装
		if (TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_REPLACED")) {
			//重置评论是否弹出登录
			SPHelper.getInst().saveInt(SPHelper.KEY_COUNT_COMMENT_SKIP_LOGIN, 0);
		}
		// 移除
		if (TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_REMOVED")) {
//			String packageName = intent.getDataString();
		}
	}

}
