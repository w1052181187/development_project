package com.chengning.yiqikantoutiao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.baidu.mobstat.StatService;
import com.chengning.common.base.util.BaseUmengShare;
import com.chengning.common.util.HttpUtil;
import com.chengning.yiqikantoutiao.activity.LoginActivity;
import com.chengning.yiqikantoutiao.activity.MainActivity;
import com.chengning.yiqikantoutiao.data.bean.UserInfoBean;
import com.chengning.yiqikantoutiao.emotion.weibo.EmotionsDB;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.SPHelper;
import com.chengning.yiqikantoutiao.util.UmengShare;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.smtt.sdk.QbSdk;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONObject;

public class App extends MultiDexApplication {

	private static App app;

	private AppDetail mAppDetail;

	private boolean isLogined;
	private UserInfoBean mUserBean = null;

	private boolean mIsLoginChange = false;
	private boolean isUserInfoChange = false;

	private boolean isNightModelChange = false;
	private boolean isUserAvatarChange = false;

	private JSONObject mReportReason;
	private static final int TAG_LC_PUSH_ON = 1;
	private static final int TAG_LC_PUSH_OFF = 0;
	private String mReadGold;

	public static App getInst() {
		return app;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		isLogined = false;
		mAppDetail = new AppDetail();

		initX5Webview();
		
		//yiqikantoutiao UA
		HttpUtil.getClient().removeHeader("User-Agent");
		HttpUtil.getClient().addHeader("User-Agent", Common.getUAAndroid(this, Consts.USER_AGENT_PREFIX ));
		
		initUmengShare();

		initUser();
		initImageLoader();
		
		switchPushTunnel();
		
		initEmotion();
		initBaiduStat();
		
		initAli();

		setReadGold(SPHelper.getInst().getString(SPHelper.KEY_NO_LOGIN_READ_GOLD));

		// queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
//		SophixManager.getInstance().queryAndLoadNewPatch();
	}

	private void initX5Webview() {
		QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
			@Override
			public void onCoreInitFinished() {
				Log.e("X5Webview", "coreInitFinished");
			}

			@Override
			public void onViewInitFinished(boolean b) {
				Log.e("X5Webview", "viewInitFinished is " + b);
			}
		});
	}

	private void initUmengShare() {
		UmengShare.getInstance().init(getApplicationContext(),
				new BaseUmengShare.WeixinPlatform(Consts.WX_APP_ID, Consts.WX_APP_SECRET),
				new BaseUmengShare.SinaPlatform(Consts.WEIBO_APP_KEY, Consts.WEIBO_APP_SECRET, Consts.WEIBO_REDIRECT_URL),
				new BaseUmengShare.QQPlatform(Consts.QQ_APP_ID, Consts.QQ_APP_KEY));
	}
	/**
	 * 切换推送通道
	 */
	private void switchPushTunnel() {
		
		AVOSCloud.initialize(this, Consts.LEANCLOUD_APP_ID,
				Consts.LEANCLOUD_APP_KEY);
		if (Common.isMIUI()) {
			initeXiaomiPush(this);
		} else {
			initLeancloud();
		}
	}

	/**
	 * 初始化小米推送
	 * @param context 
	 */
	private void initeXiaomiPush(Context context) {
		MiPushClient.registerPush(context, Consts.XIAOMI_APP_ID, Consts.XIAOMI_APP_KEY);
		
		//关闭leancloud推送
		saveLeacloudInstallation(TAG_LC_PUSH_OFF);
		
	}
	
	private void saveLeacloudInstallation(final int pushState) {
		AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
			public void done(AVException e) {
				if (e == null) {
					// 保存成功
					changeLCPushState(pushState);
				} else {
					// 保存失败，输出错误信息
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void changeLCPushState(int pushState) {
		AVObject install = new AVObject("_Installation");
		install.put("isOn", pushState);
		install.put("deviceType", "android");
		install.put("installationId", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		install.saveInBackground(new SaveCallback() {

			@Override
			public void done(AVException e) {
			}
		});
	}

	private void initAli(){
		// 用户反馈
		FeedbackAPI.init(this, Consts.ALI_APP_KEY, Consts.ALI_APP_SECRET);
		
		// 可以设置反馈消息自定义参数，方便在反馈后台查看自定义数据，参数是json对象，里面所有的数据都可以由开发者自定义
		// FeedbackAPI. setAppExtInfo(JSONObject extInfo)
	}
	
	private void initEmotion() {
		try {
			EmotionsDB.checkEmotions();
		} catch (Exception e) {
		}

	}

	public UserInfoBean getUserInfoBean() {
		if (null == mUserBean) {
			mUserBean = new UserInfoBean();
		}
		return mUserBean;
	}

	public void setUserInfoBean(UserInfoBean userBean) {
		mIsLoginChange = true;
		mUserBean = userBean;
	}

	public boolean getIsLoginChange() {
		return mIsLoginChange;
	}

	public void setIsLoginChange(boolean isLogin) {
		this.mIsLoginChange = isLogin;
	}

	public boolean getUserInfoChange() {
		return isUserInfoChange;
	}

	public void setUserInfoChange(boolean isUserInfoChange) {
		this.isUserInfoChange = isUserInfoChange;
	}

	public AppDetail getAppDetail() {
		return mAppDetail;
	}

	private void initLeancloud() {
		// AVOSCloud.setDebugLogEnabled(true);
		

		// 设置默认打开的 Activity
		com.avos.avoscloud.PushService.setDefaultPushCallback(this,
				MainActivity.class);
		// 订阅频道，当该频道消息到来的时候，打开对应的 Activity
		com.avos.avoscloud.PushService.subscribe(this, "public",
				MainActivity.class);
		com.avos.avoscloud.PushService.subscribe(this, "private",
				MainActivity.class);
		com.avos.avoscloud.PushService.subscribe(this, "protected",
				MainActivity.class);
		
		saveLeacloudInstallation(TAG_LC_PUSH_ON);
		//关闭小米推送
		MiPushClient.unregisterPush(this);

		AVInstallation.getCurrentInstallation().saveInBackground(
				new SaveCallback() {
					public void done(AVException e) {
						if (e == null) {
							// 保存成功
							String installationId = AVInstallation
									.getCurrentInstallation()
									.getInstallationId();
							// 关联 installationId 到用户表等操作……
							Log.i(App.class.getSimpleName(), "installationId: "
									+ installationId);

							changeLCVersion();
						} else {
							// 保存失败，输出错误信息
							e.printStackTrace();
						}
					}
				});

		// PushReceiver mReceiver = new PushReceiver();
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(Intent.ACTION_BOOT_COMPLETED);
		// filter.addAction(Intent.ACTION_USER_PRESENT);
		// filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		// filter.addAction("com.avos.UPDATE_STATUS");
		// filter.addAction(Consts.PACKAGE_NAME + ".PUSH");
		// registerReceiver(mReceiver, filter);

		// changeLCVersion();
	}

	private void changeLCVersion() {
		if (SPHelper.getInst().getInt(SPHelper.KEY_LEANCLOUD_DATA_VERSION) == Consts.LEANCLOUD_DATA_VERSION) {
			return;
		}
		// @杨洋，指定接收对应版本的推送。
		// leancloud里的 _Installation 表里新建了一个字段 version ，
		// 1代表 1.1版本，
		// 2代表1.2版本，3代表1.3版本.............................................10代表2.0版本。
		// APP每次启动烽火，都更新这个表的这个字段。
		AVObject install = new AVObject("_Installation");
		install.put("version", Consts.LEANCLOUD_DATA_VERSION);
		install.put("deviceType", "android");
		install.put("installationId", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		install.saveInBackground(new SaveCallback() {

			@Override
			public void done(AVException e) {
				if (e == null) {
					Log.d("APP", Consts.LEANCLOUD_DATA_VERSION
							+ " LEANCLOUD_DATA_VERSION success");

					SPHelper.getInst().saveInt(
							SPHelper.KEY_LEANCLOUD_DATA_VERSION,
							Consts.LEANCLOUD_DATA_VERSION);
				} else {
					Log.d("APP", Consts.LEANCLOUD_DATA_VERSION
							+ " LEANCLOUD_DATA_VERSION failed e: " + e);
				}
			}
		});
	}

	public void initImageLoader() {
		ImageLoaderConfiguration config = null;
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.loading)
				.showImageOnFail(R.drawable.loading)
				// .resetViewBeforeLoading(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .considerExifParams(true)
				.build();
		if (ImageLoader.getInstance().isInited() == false) {
			WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();

			config = new ImageLoaderConfiguration.Builder(this)
					.threadPriority(Thread.NORM_PRIORITY - 30)
					.threadPoolSize(3).memoryCache(new WeakMemoryCache())
					.memoryCacheSize(4 * 1024 * 1024)
					.denyCacheImageMultipleSizesInMemory()
					.discCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					// .memoryCacheExtraOptions(10000, 10000)
					.memoryCacheExtraOptions(screenWidth, screenHeight) // 用屏幕
					.writeDebugLogs().defaultDisplayImageOptions(options)
					// .imageDecoder(new ImageDecodeRegion(this,true))
					.build();
			ImageLoader.getInstance().init(config);
		}
	}

	public void initUser() {
		LoginManager.getInst().init();
		setUserAvatarChange(false);
	}

	
	public void initBaiduStat() {
		// 调试Log开关
//		StatService.setDebugOn(true);
		String umengChannel = Common.getUmengChannel(this);
		StatService.setAppChannel(this, umengChannel, true);
		
//		// 小流量发布，调试
//		StatUpdateAgent.setTestMode();
//		// 小流量发布，启动时调用
//		StatUpdateAgent.checkUpdate(this, true, new CheckUpdateListener() {
//			
//			@Override
//			public void checkUpdateResponse(KirinCheckState arg0,
//					HashMap<String, String> arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}

	public static void startLoginActivity(Activity context) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(intent);
	}

	public static void startLoginActivity() {
		Context context = App.getInst();
		Intent intent = new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(intent);
	}

	public boolean isLogin() {
		return isLogined;
	}

	public void setIsLogin(boolean login) {
		mIsLoginChange = true;
		this.isLogined = login;
	}

	public boolean isNightModelChange() {
		return isNightModelChange;
	}

	public void setNightModelChange(boolean isNightModelChange) {
		this.isNightModelChange = isNightModelChange;
	}

	public void setReportReason(JSONObject reason) {
		this.mReportReason = reason;
	}

	public JSONObject getReportReason() {
		return mReportReason;
	}

	public boolean isUserAvatarChange() {
		return isUserAvatarChange;
	}

	public void setUserAvatarChange(boolean isUserAvatarChange) {
		this.isUserAvatarChange = isUserAvatarChange;
	}

	public void setReadGold(String readGold) {
		this.mReadGold = readGold;
	}

	public String getReadGold() {
		return mReadGold;
	}
}
