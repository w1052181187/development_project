package com.chengning.common.app;

import com.chengning.common.app.ActivityInfo.ActivityState;

public class ActivityInfoUtil {
	
	public static void onCreate(ActivityInfo info){
		info.setActivityState(ActivityState.Stopped);
	};
	
	public static void onStart(ActivityInfo info){
		info.setActivityState(ActivityState.Paused);
	};
	
	public static void onResume(ActivityInfo info){
		info.setActivityState(ActivityState.Running);
	};
	
	public static void onPause(ActivityInfo info){
		info.setActivityState(ActivityState.Paused);
	};
	
	public static void onStop(ActivityInfo info){
		info.setActivityState(ActivityState.Stopped);
	};
	
	public static void onDestroy(ActivityInfo info){
		info.setActivityState(ActivityState.Killed);
	}

	public static void onSaveInstanceState(ActivityInfo info) {
		info.setActivityState(ActivityState.SaveInstanceStated);
	};

}
