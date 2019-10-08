package com.chengning.fenghuo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.Consts;

public class HomeFinishActivityUtil {
	
	public static void sendBroadcast(BaseFragmentActivity activity){
		Intent intent = new Intent(Consts.ACTION_HOME_FINISH_ACTIVITY);
		activity.sendBroadcast(intent);
	}
	
	public static BroadcastReceiver registerReceiver(final BaseFragmentActivity activity){
		BroadcastReceiver mReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if(action.equals(Consts.ACTION_HOME_FINISH_ACTIVITY)){
					if(activity != null){
						activity.finish();
					}
				}
			}
		};
		IntentFilter intentFileter = new IntentFilter();
		intentFileter.addAction(Consts.ACTION_HOME_FINISH_ACTIVITY);
		activity.registerReceiver(mReceiver, intentFileter);
		return mReceiver;
	}
	
	public static void unregisterReceiver(BaseFragmentActivity activity, BroadcastReceiver receiver){
		if (null != receiver) {
			activity.unregisterReceiver(receiver);
		}
		
	}

}
