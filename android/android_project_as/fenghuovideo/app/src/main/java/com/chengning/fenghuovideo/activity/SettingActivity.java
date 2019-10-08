package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.update.UpdateVersionUtil;
import com.chengning.common.util.ThreadHelper;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.SettingManager.FontSize;
import com.chengning.fenghuovideo.data.access.LocalStateDA;
import com.chengning.fenghuovideo.data.bean.SettingBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.SPHelper;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.widget.SwitchButton;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.DecimalFormat;

public class SettingActivity extends BaseFragmentActivity {
	
	private static final int SET_CACHE_SUCCESS = 0;

	private TitleBar mTitleBar;
	private View mAccountBind;
	private View mChangePassword;
	private View mCache;
	private TextView mCacheText;
	private View mAbout;
	private View mUpdate;
	private View mFeedback;

	private App app;

	private View mAccountBindLine;

	private View mChangePasswordLine;
	
	private RelativeLayout mNotifySet;
	private HandlerThread mSettingThread;

	public static void launch(Activity from) {
		Intent intent = new Intent(from,SettingActivity.class);
		from.startActivity(intent);
	}


	@Override
	public void onCreate(Bundle savedInstanceState){
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_setting); 
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Activity getActivity() {
		return SettingActivity.this;
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.showDefaultBack();
		mTitleBar.setTitle("设置");
		
		mAccountBind = findViewById(R.id.setting_account_bind_rl);
		mAccountBindLine = findViewById(R.id.setting_account_bind_view);
		mChangePassword = findViewById(R.id.setting_change_password_rl);
		mChangePasswordLine = findViewById(R.id.setting_change_password_view);
		mCache = findViewById(R.id.setting_cache_rl);
		mCacheText = (TextView) findViewById(R.id.setting_cache_text);

		mAbout = findViewById(R.id.setting_about_rl);
		mUpdate = findViewById(R.id.setting_update_rl);
		mFeedback = findViewById(R.id.setting_feedback_rl);
		mNotifySet = (RelativeLayout) findViewById(R.id.setting_notify_set_rl);
	}

	@Override
	public void initDatas() {
		
		// data
		app = App.getInst();
		
		initSettedData();
		
		mAccountBind.setVisibility(app.isLogin() ? View.VISIBLE : View.GONE);
		mAccountBindLine.setVisibility(app.isLogin() ? View.VISIBLE : View.GONE);
		mChangePassword.setVisibility(app.isLogin() ? View.VISIBLE : View.GONE);
		mChangePasswordLine.setVisibility(app.isLogin() ? View.VISIBLE : View.GONE);
		
	}
	
	/**
	 * 初始化设置的数据
	 */
	private void initSettedData() {
		if (mSettingThread == null) {
			mSettingThread = ThreadHelper.creatThread("my_setting");
		}
		ThreadHelper.handle(mSettingThread, new Runnable() {
	
			@Override
			public void run() {
				showCacheSize();
			}

		});
	}

	private void showCacheSize(){
		// cache
		long size = 0;
		try {
			size = Common.getFileSize(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Android"+File.separator+"Data"+File.separator+"com.chengning.fenghuo"+File.separator+"cache"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int temp = (int) (size/1024);
	    float mb = temp/(float)1024;
	    DecimalFormat to= new DecimalFormat("0.00");
	    getHandler().obtainMessage(SET_CACHE_SUCCESS, String.valueOf(to.format(mb)+" MB")).sendToTarget();
	}

	@Override
	public void installListeners() {
		mAccountBind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AccountBindActivity.class));
			}
		});
		mChangePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), PwdModifyActivity.class));
			}
		});
		
		mCache.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clearCache(getActivity());
			}
		});

		mAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AboutActivity.class);
				startActivityForResult(intent,0);
			}
		});
		
		mUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UpdateVersionUtil.checkUpdate(getActivity(), Consts.UPDATE_APP_KEY, true);
			}
		});
		
		mFeedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FeedbackAPI.openFeedbackActivity();
			}
		});
		mNotifySet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SetNotificationActivity.class);
				startActivity(intent);
			}
		});

	}
	
	/**
	 * 清除缓存
	 * @param context
	 */
	protected void clearCache(final Activity context) {
		UIHelper.addPD(context, "清理中..");
		if (mSettingThread == null) {
			mSettingThread = ThreadHelper.creatThread("my_setting");
		}
		ThreadHelper.handle(mSettingThread, new Runnable() {
	
			@Override
			public void run() {
				SPHelper.getInst().saveLong(SPHelper.KEY_OFFLINE_DOWNLOAD_TIME, -1);
//				LocalStateDA.getInst(context).clearArticle();
				
				ImageLoader.getInstance().clearDiscCache();
				Common.clearCacheFolder(context.getCacheDir(),System.currentTimeMillis());
				getHandler().post(new Runnable() {
				
					@Override
					public void run() {
						getHandler().obtainMessage(SET_CACHE_SUCCESS, "0.00 MB").sendToTarget();
					}
				});
			}

		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
			case SET_CACHE_SUCCESS :
				mCacheText.setText((String)msg.obj);
				UIHelper.removePD();
				break;
			default :
				break;
		}
	}
	
	@Override
	public void onDestroy() {
//		mOfflineProgress.destroy();
		ThreadHelper.destory(mSettingThread);
		getHandler().removeMessages(SET_CACHE_SUCCESS);
//		getHandler().removeMessages(SET_SUCCESS);
		super.onDestroy();
	}
}
