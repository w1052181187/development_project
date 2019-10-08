package com.cmstop.jstt.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class BrightnessTools {
	
	private Activity mActivity;
	private ContentResolver mContentResolver;
	
	public BrightnessTools(Activity activity, ContentResolver contentResolver){
		this.mActivity = activity;
		this.mContentResolver = contentResolver;
	}
	
	private ContentResolver getContentResolver(){
		return mContentResolver;
	}
	
	/**
	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	private int getScreenMode() {
	    int screenMode = 0;
	    try {
	        screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
	    } catch (Exception localException) {

	    }
	    return screenMode;
	}

	/**
	 * 设置当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	public void setScreenMode(int paramInt) {
	    try {
	        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
	    } catch (Exception localException) {
	        localException.printStackTrace();
	    }
	}

	/**
	 * 获得当前屏幕亮度值 0--255
	 */
	public int getScreenBrightness() {
	    int screenBrightness = 255;
	    try {
	        screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
	    } catch (Exception localException) {

	    }
	    return screenBrightness;
	}

	/**
	 * 设置当前屏幕亮度值 0--255
	 */
	public void saveScreenBrightness(int paramInt) {
	    try {
	        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
	    } catch (Exception localException) {
	        localException.printStackTrace();
	    }
	}

	/**
	 * 保存当前的屏幕亮度值，并使之生效
	 */
	public void setScreenBrightness(int paramInt) {
	    Window localWindow = mActivity.getWindow();
	    WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
	    float f = paramInt / 255.0F;
	    localLayoutParams.screenBrightness = f;
	    localWindow.setAttributes(localLayoutParams);
	}
}
