package com.bibinet.biunion.project.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressLint("NewApi")
public class StatusBarUtil {

	public static final int Build_VERSION_CODES_M = Build.VERSION_CODES.M;
	public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
	
	/**
	 * 需要在activity布局中根容器中加入android:fitsSystemWindows="true"
	 * @param activity
	 * @param color
	 */
	public static void setBar(Activity activity, int color, boolean isDark){
		setRootView(activity);
		transBar(activity, color);
		setLightBar(activity, isDark);
	}
	
	private static void setRootView(Activity activity)  {  
		View view = ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			view.setFitsSystemWindows(true);
		}
    }  
	
	
	/**
	 * Android 4.4 及以上支持,这里只针对android 6.0及以上
	 * 修改状态栏为全透明并 变更背景色
	 * @param activity
	 */
	public static void transBar(Activity activity, int color){
		if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_M){
			Window window = activity.getWindow();
	        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
	                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//	                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
	        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	        window.setStatusBarColor(Color.TRANSPARENT);
//	        window.setNavigationBarColor(Color.TRANSPARENT);
	        //状态栏背景色..
	        window.setStatusBarColor(color);
		}
	}
	
	public static void setLightBar(Activity activity, boolean isDark){
		Window window = activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_M){
			if(StatusBarUtil.MIUISetStatusBarLightMode(window, isDark)){
				
			}else{
				if(isDark)
					window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				else{
					int ui = window.getDecorView().getSystemUiVisibility();
					window.getDecorView().setSystemUiVisibility(ui &= ~SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				}
			}
		}
	}
	
	/**
	 * 设置状态栏字体图标为深色，需要MIUIV6以上
	 * 这里需要注意：底层版本为Android6.0.1的MIUI7.1系统不支持View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置
	 * @param window 需要设置的窗口
	 * @param dark 是否把状态栏字体及图标颜色设置为深色
	 * @return  boolean 成功执行返回true
	 *
	 */
	public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
	    if (window != null) {
	    	// 20171205 liujiankai
//	        Class clazz = window.getClass();
//	        try {
//	            int darkModeFlag = 0;
//	            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
//	            Field  field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
//	            darkModeFlag = field.getInt(layoutParams);
//	            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//	            if(dark){
//	                extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
//	            }else{
//	                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
//	            }
//	            result=true;
//	        }catch (Exception e){
//
//	        }
			Class<? extends Window> clazz = window.getClass();
			try {
				int darkModeFlag = 0;
				Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
				Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
				darkModeFlag = field.getInt(layoutParams);
				Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
				extraFlagField.invoke(window, dark ? darkModeFlag : 0, darkModeFlag);
//				if (dark) {
//					window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//					window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//					window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//				} else {
//					int flag = window.getDecorView().getSystemUiVisibility()
//							& ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//					window.getDecorView().setSystemUiVisibility(flag);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
