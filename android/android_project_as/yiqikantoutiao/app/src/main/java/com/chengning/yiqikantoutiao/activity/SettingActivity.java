package com.chengning.yiqikantoutiao.activity;

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
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.SettingManager.FontSize;
import com.chengning.yiqikantoutiao.data.access.LocalStateDA;
import com.chengning.yiqikantoutiao.data.bean.SettingBean;
import com.chengning.yiqikantoutiao.service.OfflineDownloadService.OfflineDownloadState;
import com.chengning.yiqikantoutiao.service.OfflineDownloadService.OnOfflineDownloadListener;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.SPHelper;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.widget.SettingFontDialog;
import com.chengning.yiqikantoutiao.widget.SettingFontDialog.SettingFontDialogItemClick;
import com.chengning.yiqikantoutiao.widget.SettingOfflineProgress;
import com.chengning.yiqikantoutiao.widget.SwitchButton;
import com.chengning.yiqikantoutiao.widget.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.DecimalFormat;

public class SettingActivity extends BaseFragmentActivity {
	
	private static final int SET_CACHE_SUCCESS = 0;
	private static final int SET_SUCCESS = 1;

	private TitleBar mTitleBar;
	private View mAccountBind;
	private View mChangePassword;
	private View mFont;
	private TextView mFontText;
	private View mCache;
	private TextView mCacheText;
	private SwitchButton mNoImageModeSwitch;
	private View mOffline;
	private View mAbout;
	private View mUpdate;
	private View mFeedback;

	private App app;
	
	// 离线阅读进度
	private SettingOfflineProgress mOfflineProgress;

	private View mAccountBindLine;

	private View mChangePasswordLine;
	
	public static SettingActivity _instance = null;
	
	private RelativeLayout mNotifySet;
	private HandlerThread mSettingThread;
	private boolean mNoImageState;
	@Override
	public void onCreate(Bundle savedInstanceState){
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_setting); 
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Activity getActivity() {
		_instance = this;
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
		mFont = findViewById(R.id.setting_font_rl);
		mFontText = (TextView) findViewById(R.id.setting_font_text);
		mCache = findViewById(R.id.setting_cache_rl);
		mCacheText = (TextView) findViewById(R.id.setting_cache_text);
		mNoImageModeSwitch = (SwitchButton) findViewById(R.id.setting_no_image_mode_switch);
		
		mOffline = findViewById(R.id.setting_offline_rl);
		mAbout = findViewById(R.id.setting_about_rl);
		mUpdate = findViewById(R.id.setting_update_rl);
		mFeedback = findViewById(R.id.setting_feedback_rl);
		mNotifySet = (RelativeLayout) findViewById(R.id.setting_notify_set_rl);
		mOfflineProgress = new SettingOfflineProgress(this, mOffline);
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
				SettingBean bean = SettingManager.getInst().getSettingBean();
				getHandler().obtainMessage(SET_SUCCESS, bean).sendToTarget();
			}

		});
	}

	private void showCacheSize(){
		// cache
		long size = 0;
		try {
			size = Common.getFileSize(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"Android"+ File.separator+"Data"+ File.separator+"com.chengning.yiqikantoutiao"+ File.separator+"cache"));
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
		mFont.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setFontSize(getActivity());
			}
		});
		
		mCache.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clearCache(getActivity());
			}
		});

	    mNoImageModeSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (mNoImageState != isChecked) {
					mNoImageState = isChecked;
					setNoPicModel(!isChecked);
				}
			}
		});
	    // TODO(离线阅读)
//	    mOfflineProgress.setProgress(progress)
	    
	    mOfflineProgress.setOnOfflineDownloadListener(new OnOfflineDownloadListener() {
			
			@Override
			public void onState(OfflineDownloadState state, Object obj) {
				switch (state) {
				case Finish:
					if(mCacheText != null){
						showCacheSize();
					}
					break;
				default:
					break;
				}
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
				LocalStateDA.getInst(context).clearArticle();
				
				ImageLoader.getInstance().clearDiscCache();
				Common.clearCacheFolder(context.getCacheDir(), System.currentTimeMillis());
				getHandler().post(new Runnable() {
				
					@Override
					public void run() {
						getHandler().obtainMessage(SET_CACHE_SUCCESS, "0.00MB").sendToTarget();
					}
				});
			}

		});
	}

	/**
	 * 设置字体
	 */
    protected void setFontSize(final Activity context) {
    	SettingFontDialog dialog = new SettingFontDialog();
		dialog.setData(new SettingFontDialogItemClick() {
			
			@Override
			public void onItemClick(int position) {
				FontSize f = FontSize.Middle;
				switch (position) {
				case 0:
					f = FontSize.Small;
					break;
				case 1:
					f = FontSize.Middle;
					break;
				case 2:
					f = FontSize.Large;
					break;
				case 3:
					f = FontSize.ExtraLarge;
					break;
				}
				
				saveFontSize(f.getSize());
				
				mFontText.setText(f.getStr());
			}
		}, new String[]{
				FontSize.Small.getStr(), 
				FontSize.Middle.getStr(), 
				FontSize.Large.getStr(), 
				FontSize.ExtraLarge.getStr(), 
				});
		dialog.show(getSupportFragmentManager(), SettingFontDialog.class.getSimpleName());
	}
    
    protected void saveFontSize(final int size) {
    	if (mSettingThread == null) {
			mSettingThread = ThreadHelper.creatThread("my_setting");
		}
		ThreadHelper.handle(mSettingThread, new Runnable() {
	
			@Override
			public void run() {
				SettingManager.getInst().saveFontSize(size);
			}

		});
    	
	}

	/**
	 * 设置无图模式
	 */
	private void setNoPicModel(final boolean b) {
		// TODO 设置无图模式
		if (mSettingThread == null) {
			mSettingThread = ThreadHelper.creatThread("my_setting");
		}
		ThreadHelper.handle(mSettingThread, new Runnable() {
	
			@Override
			public void run() {
				SettingManager.getInst().saveNoPicModel(b ? Common.TRUE : Common.FALSE);
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
			case SET_SUCCESS :
				SettingBean bean = (SettingBean) msg.obj;
				if (bean != null) {
					mFontText.setText(FontSize.getFontSize(bean.getFontsize()).getStr());
//					mNightState = !Common.isTrue(bean.getIs_night_model());
					mNoImageState = !Common.isTrue(bean.getIs_no_pic_model());
//					mNightModeSwitch.setChecked(mNightState);
					mNoImageModeSwitch.setChecked(mNoImageState);
					
				}
				
				break;
			default :
				break;
		}
	}
	
	@Override
	public void onDestroy() {
		mOfflineProgress.destroy();
		ThreadHelper.destory(mSettingThread);
		getHandler().removeMessages(SET_CACHE_SUCCESS);
		getHandler().removeMessages(SET_SUCCESS);
		super.onDestroy();
	}
}
