package com.chengning.fenghuo.util;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import android.app.Activity;

public class ActivityManagerUtil {

	private static ActivityManagerUtil mInst = null;
	private static LinkedList<WeakReference<Activity>> mLists = new LinkedList<WeakReference<Activity>>();
	
	public static ActivityManagerUtil getInst() {
		if (null == mInst) {
			mInst = new ActivityManagerUtil();
		}
		return mInst;
	}
	
	public void init() {
		mLists.clear();
	}
	
	public void addActivity(Activity activity) {
		mLists.add(new WeakReference<Activity>(activity));
	}
	
	public void finishAllActivitys() {
		if (null != mLists) {
			for (WeakReference<Activity> w : mLists) {
				if (null != w && w.get() != null) {
					w.get().finish();
				}
			}
			mLists.clear();
		}
	}

}
