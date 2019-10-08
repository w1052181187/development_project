package com.chengning.fenghuo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;

import com.chengning.common.base.util.BaseUIHelper;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.widget.LoadProgressDialog;

public class UIHelper extends BaseUIHelper {

	/**
	 * 添加 浮动窗口
	 */
	static LoadProgressDialog pd = null;

	public static void addPD(final Activity context, String str) {
		addPD(context, str, false, false, null, false);
	}

	public static void addPD(final Activity context, String str,
			boolean cancelable, final boolean cancelHttpRequestsOnCancel,
			final OnCancelListener listener, final boolean finishOnCancel) {
		removePD();
		pd = LoadProgressDialog.createDialog(context);
		pd.setMessage(str);
		pd.setCancelable(cancelable);
		if (cancelHttpRequestsOnCancel || (listener != null) || finishOnCancel) {
			pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (context != null) {
						if (cancelHttpRequestsOnCancel) {
							HttpUtil.getClient().cancelRequests(context, true);
						}
						if (listener != null) {
							listener.onCancel(dialog);
						}
						if (finishOnCancel) {
							context.finish();
						}
					}
				}
			});
		}

		pd.show();
	}

	/**
	 * 移除
	 */
	public static void removePD() {
		try {
			if (pd != null) {
				pd.dismiss();
				pd = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
