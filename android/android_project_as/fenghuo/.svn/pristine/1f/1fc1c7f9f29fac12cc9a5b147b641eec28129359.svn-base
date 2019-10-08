package com.chengning.fenghuo.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.service.OfflineDownloadService;
import com.chengning.fenghuo.service.OfflineDownloadService.LocalBinder;
import com.chengning.fenghuo.service.OfflineDownloadService.OfflineDownloadState;
import com.chengning.fenghuo.service.OfflineDownloadService.OnOfflineDownloadListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.SPHelper;

public class SettingOfflineProgress {
	
private static final String TAG = SettingOfflineProgress.class.getSimpleName();
	
	private static final int MSG_PROGRESS = 1;
	
	private BaseFragmentActivity mContext;
	private Handler mHandler;
	private ServiceConnection mConnection;
	private OfflineDownloadService mService;
	
	private View mRoot;
	private RoundProgressBar mProgressBar;
	private TextView mText;
	
	private OnOfflineDownloadListener mOutListener;

	private boolean isDownloading;
	private int mProgress;
	
	public SettingOfflineProgress(BaseFragmentActivity context, View root){
		mContext = context;
		mRoot = root;
		mHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg){
			}
		};
		mProgressBar = (RoundProgressBar) context.findViewById(R.id.setting_offline_round_progress);
		mText = (TextView) context.findViewById(R.id.setting_offline_text);
		
		init();
	}
	
	private void init(){
		mRoot.setOnClickListener(mListener);
		
		mText.setText("");
		mText.setVisibility(View.INVISIBLE);
		
		mProgressBar.setProgress(0);
		mProgressBar.setVisibility(View.INVISIBLE);
		mProgressBar.setOnClickListener(mListener);
		
		isDownloading = false;
		mProgress = 0;
		
		mConnection = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "onServiceDisconnected");
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				
				mService = ((LocalBinder)service).getService();
				mDownloadListener.onState(mService.getState(), mService.getProgress());
				mService.setOnOfflineDownloadListener(mDownloadListener);
			}
		};
		mContext.startService(new Intent(mContext, OfflineDownloadService.class));
		mContext.bindService(new Intent(mContext, OfflineDownloadService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void setOnOfflineDownloadListener(OnOfflineDownloadListener listener){
		this.mOutListener = listener;
	}
	
	public void destroy(){
		if(mConnection != null){
			mContext.unbindService(mConnection);
		}
	}
	
	public void setProgress(int progress){
		mProgress = progress;
		mProgressBar.setProgress(progress);
	}
	
	public void start(){
		if(mService != null){
			OfflineProgressDialog dialog = new OfflineProgressDialog();
			dialog.setmTitle("离线下载会占用一部分存储空间，点击确定继续下载。");
			dialog.setListenner(new Runnable() {
				@Override
				public void run() {
					mDownloadListener.onState(OfflineDownloadState.Downloading, mProgress);
					mService.startOrResume();
				}
			});
			dialog.show(mContext.getSupportFragmentManager(), TAG + "_ConfirmDialog");
		}
	}
	
	public void pause(){
		if(mService != null){
			mDownloadListener.onState(OfflineDownloadState.Pause, mProgress);
			mService.pause();
		}
	}
	
	public void cancel(){
		if(mService != null){
			OfflineProgressDialog dialog = new OfflineProgressDialog();
			dialog.setmTitle("不想继续下载了吗？");
			dialog.setListenner(new Runnable() {
				@Override
				public void run() {
					mDownloadListener.onState(OfflineDownloadState.Init, 0);
					mService.cancel();
				}
			});
			dialog.show(mContext.getSupportFragmentManager(), TAG + "_CancelDialog");
		}
	}
	
	private OnClickListener mListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(isDownloading){
				cancel();
			}else{
				start();
			}
		}
	};
	private OnOfflineDownloadListener mDownloadListener = new OnOfflineDownloadListener() {
		
		@Override
		public void onState(OfflineDownloadState state, Object obj) {
//			Log.d(TAG, "OfflineDownloadState state: " + state + ", obj: " + obj);
			switch (state) {
			case Init:
			case Finish:
				mProgress = 0;
				
				mText.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.INVISIBLE);
				
				long time = SPHelper.getInst().getLong(SPHelper.KEY_OFFLINE_DOWNLOAD_TIME);
				// 上次离线时间
				if(time > 0){
					String timeStr = Common.getDateMMDDNotNull(time);
					mText.setText("上次离线时间: " + timeStr);
				}else{
					mText.setText("");
				}
				
				isDownloading = false;
				break;
			case Downloading:
				mText.setVisibility(View.INVISIBLE);
				mProgressBar.setVisibility(View.VISIBLE);
				
				int progress = (Integer) obj;
				mProgress = progress;
				setProgress(mProgress);
				
				isDownloading = true;
				break;
			case Pause:
				mText.setVisibility(View.VISIBLE);
				mProgressBar.setVisibility(View.INVISIBLE);
				 
				mProgress = (Integer) obj;
				mText.setText(mProgress + "% 继续下载");
				isDownloading = false;
				break;
			default:
				break;
			}

			if(mOutListener != null){
				mOutListener.onState(state, obj);
			}
		}
	
	};
	
}
