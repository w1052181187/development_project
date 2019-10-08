package com.chengning.common.util;

import java.io.File;

import android.app.Activity;

import com.chengning.common.base.util.BaseUIHelper;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * 版本工具
 * @author wyg
 * @date 2015-9-15
 *
 */
public class VersionUtils {

	private static UpdateResponse updateInfo;
	
	/**
	 * 检查更新
	 * @param context
	 */
	public static void checkUpdate(final Activity context, final boolean isHintNoVersion) {
		// TODO 检查版本更新
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

			@Override
			public void onUpdateReturned(int updateCode, UpdateResponse updateInfo) {
				// TODO Auto-generated method stub
				 switch (updateCode) {
			        case UpdateStatus.Yes: // has update
			        	checkIsIgnore(context, updateInfo);
			            break;
			        case UpdateStatus.No: // has no update
			        	if (isHintNoVersion) {
			        		BaseUIHelper.showToast(context, "暂无新版本");
			        	}
			            break;
			        case UpdateStatus.NoneWifi: // none wifi
			            break;
			        case UpdateStatus.Timeout: // time out
			            break;
			        }
			}
		});
		UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

		    @Override
		    public void onClick(int status) {
		        switch (status) {
		        case UpdateStatus.Update:
		        	updateVersion(context, VersionUtils.updateInfo);
		            break;
		        case UpdateStatus.Ignore:
		        	ignoreVersion(context, VersionUtils.updateInfo);
		            break;
		        case UpdateStatus.NotNow:
		            break;
		        }
		    }
		});
		UmengUpdateAgent.setDownloadListener(new UmengDownloadListener(){

		    @Override
		    public void OnDownloadStart() {
		    }

		    @Override
		    public void OnDownloadUpdate(int progress) {
		    }

		    @Override
		    public void OnDownloadEnd(int result, String file) {
		    	UmengUpdateAgent.startInstall(context, new File(file));
		    }           
		});
		UmengUpdateAgent.update(context);
	}

	/**
	 * 忽略版本
	 * @param context
	 * @param updateInfo
	 */
	protected static void ignoreVersion(Activity context, UpdateResponse updateInfo) {
		// TODO 忽略版本
		UmengUpdateAgent.ignoreUpdate(context, updateInfo);
	}

	/**
	 * 更新版本
	 * @param context
	 * @param updateInfo
	 */
	protected static void updateVersion(Activity context, UpdateResponse updateInfo) {
		// TODO 更新版本
		
		//检测是否已经下载 null:未下载
		File file = UmengUpdateAgent.downloadedFile(context, updateInfo);
		if (null != file) {
			UmengUpdateAgent.startInstall(context, file);
		} else {
			UmengUpdateAgent.startDownload(context, updateInfo);
		}
	}

	/**
	 * 检查该版本是否忽略
	 * @param updateInfo 
	 * @param context 
	 */
	protected static void checkIsIgnore(Activity context, UpdateResponse updateInfo) {
		// TODO 检查该版本是否忽略
		if (!UmengUpdateAgent.isIgnore(context, updateInfo)) {
			VersionUtils.updateInfo = updateInfo;
			UmengUpdateAgent.showUpdateDialog(context, updateInfo);
		};
	}

}
