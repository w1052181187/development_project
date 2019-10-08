package com.cmstop.jstt.utils;

import com.cmstop.jstt.LoginManager;
import com.cmstop.jstt.activity.LoginActivity;
import com.cmstop.jstt.beans.data.CommentItemBean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class CommentCheckUtil {
	
	public static final int REQUEST_CODE_LOGIN_COMMENT = 6201;
	public static final int REQUEST_CODE_LOGIN_COMMENT_CHILD = 6202;

	private static boolean isSkipInActivity = false;
	
	public static void onActivityCreate(){
		isSkipInActivity = false;
	}
	
	public static void onActivityStop(){
		isSkipInActivity = false;
	}
	
	public static boolean checkLoginNotNeededOfComment(Context context){
		return checkLoginNotNeededOfComment(context, false, null);
	}
	
	public static boolean checkLoginNotNeededOfComment(Context context, boolean isChild, CommentItemBean bean){
		if(isSkipInActivity){
			isSkipInActivity = false;
			return true;
		}
		
		boolean isNeedLogin = !LoginManager.getInst().isLogin() && (SPHelper.getInst().getInt(SPHelper.KEY_COUNT_COMMENT_SKIP_LOGIN) < 3);
		if(isNeedLogin){
			SPHelper.getInst().saveInt(SPHelper.KEY_COUNT_COMMENT_SKIP_LOGIN, SPHelper.getInst().getInt(SPHelper.KEY_COUNT_COMMENT_SKIP_LOGIN) + 1);
			
			Bundle extras = new Bundle();
			extras.putBoolean("show_skip", true);
			if (isChild) {
				extras.putSerializable("bean", bean);
			}
			LoginManager.startLoginActivityForResult(context, extras, isChild ? REQUEST_CODE_LOGIN_COMMENT_CHILD : REQUEST_CODE_LOGIN_COMMENT);
		}
		return !isNeedLogin;
	}
	
	public static boolean onActivityResult(int requestCode, int resultCode, Intent data, Handler handler, Runnable skipRunnable){
		if(requestCode == REQUEST_CODE_LOGIN_COMMENT || requestCode == REQUEST_CODE_LOGIN_COMMENT_CHILD){
			if(resultCode == LoginActivity.RESULTCODE_SKIP){
				isSkipInActivity = true;
				if(skipRunnable != null){
					handler.postDelayed(skipRunnable, 50);
				}
			}
			return true;
		}
		return false;
	}

}
