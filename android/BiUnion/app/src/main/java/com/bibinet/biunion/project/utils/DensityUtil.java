package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.bibinet.biunion.project.application.BiUnionApplication;

public class DensityUtil {

	public static int dip2px(float dpValue) {
		final float scale = getDensity(BiUnionApplication.context);
		return (int) (dpValue * scale + 0.5f);
	}

	public static float getDensity(Context activity) {
		return activity.getResources().getDisplayMetrics().density;
	}

	public static int getScreenWidth(Activity activity) {
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		return display.getWidth();
	}
	public static int getScreenHeight(Activity activity) {
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 *
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 *
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}