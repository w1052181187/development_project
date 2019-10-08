package com.chengning.common.util.debug;

import android.util.Log;

public class CNLog {
	
	public static void d(String tag, String msg){
		Log.d(tag, msg);
	}
	
	public static void e(String tag, String msg){
		Log.e(tag, msg);
	}
	
}
