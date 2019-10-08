package com.chengning.common.update;

import android.app.Activity;

import com.chengning.common.base.util.BaseCommon;
import com.chengning.common.base.util.BaseUIHelper;

public class UpdateVersionUtil {

	
	/**
	 * 检查更新
	 */
	public static void checkUpdate(final Activity context, final String appkey, final boolean isHintNoVersion) {
		UpdateAgent.update(context, appkey, BaseCommon.getUmengChannel(context), new UpdateListener() {

			@Override
			public void onUpdateReturned(
					com.chengning.common.update.UpdateStatus status,
					UpdateBean bean) {
				switch (status) {
				case Yes:
					break;
				case No:
		        	if (isHintNoVersion) {
		        		BaseUIHelper.showToast(context, "暂无新版本");
		        	}
					break;
				case NoneWifi:
					break;
				case Error:
					break;
				}
			}
		});
	}

}
