package com.chengning.common.util.debug;

import java.lang.Thread.UncaughtExceptionHandler;

public class CNUncaughtExceptionHandlerUtil {
	
	private static final String TAG = CNUncaughtExceptionHandlerUtil.class.getSimpleName();
	
	public static void listenUncaughtException(){
		final UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();
		UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				try {
					oldHandler.uncaughtException(thread, ex);
					
//					CNLog.e(TAG, "ex.getMessage(): " + ex.getMessage() + "\nex.getCause(): " + ex.getCause()
//							+ "\nex.getStackTrace(): " + ex.getStackTrace());
//					ex.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Thread.setDefaultUncaughtExceptionHandler(handler);
	}
	
}
