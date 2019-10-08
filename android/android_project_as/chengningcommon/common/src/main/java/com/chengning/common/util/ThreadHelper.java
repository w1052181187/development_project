package com.chengning.common.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

/**
 * 适用于本地耗时操作
 * @author Administrator
 *
 */
public class ThreadHelper {

	/**
	 * 创建线程
	 * @param tName
	 * @return
	 */
	public static HandlerThread creatThread(String tName) {
		HandlerThread mThread = new HandlerThread(tName);
		mThread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
		if (!mThread.isAlive()) {
			mThread.start();
		};
		return mThread;
	}
	
	/**
	 * 执行具体操作
	 * @param thread
	 * @param runable
	 */
	public static void handle(HandlerThread thread, final Runnable runable) {
		Handler mHandler = new Handler(thread.getLooper());
		mHandler.post(runable);
	}
	
	/**
	 * 销毁线程
	 * @param thread
	 */
	public static void destory(HandlerThread thread) {
		if (thread != null) {
			thread.quit();
			thread = null;
		}
	}
	
}
