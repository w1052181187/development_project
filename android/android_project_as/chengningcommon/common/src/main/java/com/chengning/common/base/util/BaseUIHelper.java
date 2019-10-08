package com.chengning.common.base.util;

import android.content.Context;
import android.widget.Toast;

public class BaseUIHelper {
	
	private static Toast mToast;
	
	public static void showToast(Context context, String text){
		if(mToast != null){
			mToast.cancel();
		}
		mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		mToast.show();
	}
	
	public static void showToast(Context context, int id){
		if(mToast != null){
			mToast.cancel();
		}
		mToast = Toast.makeText(context, context.getResources().getString(id), Toast.LENGTH_LONG);
		mToast.show();
	}

 
}
