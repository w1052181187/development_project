/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.shenyuan.militarynews;

import android.content.Context;
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
import com.chengning.common.base.util.BaseUmengShare.QQPlatform;
import com.chengning.common.base.util.BaseUmengShare.SinaPlatform;
import com.chengning.common.base.util.BaseUmengShare.WeixinPlatform;
import com.chengning.common.util.HttpUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.SPHelper;
import com.shenyuan.militarynews.utils.UmengShare;

public class App extends MultiDexApplication {

	private static App xjsContext;

	private AppDetail mAppDetail;

	private DisplayImageOptions options;
	private boolean isNightModelChange = false;

	public static App getInst() {
		return xjsContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		xjsContext = this;
		mAppDetail = new AppDetail();
		
		//xinjunshi UA
		HttpUtil.getClient().removeHeader("User-Agent");
		HttpUtil.getClient().addHeader("User-Agent", Common.getUAAndroid(this, Const.USER_AGENT_PREFIX ));
		
		initBaiduStat();
		
		initUmengShare();

//		MobclickAgent.setDebugMode(true);
		
		initImageLoader();

		initPush();
		initAli();
		
		try {
			initPushChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initUmengShare() {
		UmengShare.getInstance().init(getApplicationContext(), 
				new WeixinPlatform(Const.WX_APP_ID, Const.WX_APP_SECRET),
				new SinaPlatform(Const.WEIBO_APP_KEY, Const.WEIBO_APP_SECRET, Const.WEIBO_REDIRECT_URL),
				new QQPlatform(Const.QQ_APP_ID, Const.QQ_APP_KEY));
	}

	private void initPushChannel() {
		initLeancloud();
	}

	public void initPush() {
		PushMsgManager.getInstance().init(this);
	}

	public AppDetail getAppDetail() {
		return mAppDetail;
	}

	public void initBaiduStat() {
		String umengChannel = Common.getUmengChannel(this);
		StatService.setAppChannel(this, umengChannel, true);
	}
	
	private void initAli(){
		// 用户反馈
		FeedbackAPI.init(this, Const.ALI_APP_KEY, Const.ALI_APP_SECRET);
		
		// 可以设置反馈消息自定义参数，方便在反馈后台查看自定义数据，参数是json对象，里面所有的数据都可以由开发者自定义
		// FeedbackAPI. setAppExtInfo(JSONObject extInfo)
	}

	/**
	 * 初始化leacloud推送
	 */
	private void initLeancloud() {
		AVOSCloud.initialize(this, Const.LEANCLOUD_APP_ID,
				Const.LEANCLOUD_APP_KEY);

		// 设置默认打开的 Activity
//		PushService.setDefaultPushCallback(this, HomeActivity.class);

		saveLeacloudInstallation();
		
	}

	private void saveLeacloudInstallation() {
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
	}
	
	private void changeLCVersion() {
		if (SPHelper.getInst().getInt(SPHelper.KEY_LEANCLOUD_DATA_VERSION) == Const.LEANCLOUD_DATA_VERSION) {
			return;
		}
		// @杨胜利，指定接收对应版本的推送。
		// leancloud里的 _Installation 表里新建了一个字段 version ，
		// APP每次启动烽火，都更新这个表的这个字段。
		AVObject install = new AVObject("_Installation");
		install.put("dataVersion", Const.LEANCLOUD_DATA_VERSION);
		install.put("deviceType", "android");
		install.put("installationId", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		install.saveInBackground(new SaveCallback() {

			@Override
			public void done(AVException e) {
				if (e == null) {
					Log.d("APP", Const.LEANCLOUD_DATA_VERSION
							+ " LEANCLOUD_DATA_VERSION success");

					SPHelper.getInst().saveInt(
							SPHelper.KEY_LEANCLOUD_DATA_VERSION,
							Const.LEANCLOUD_DATA_VERSION);
				} else {
					Log.d("APP", Const.LEANCLOUD_DATA_VERSION
							+ " LEANCLOUD_DATA_VERSION failed e: " + e);
				}
			}
		});
	}

	public void initImageLoader() {
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = null;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.loading)
				.showImageOnFail(R.drawable.loading)
				// .resetViewBeforeLoading(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565)
				// .considerExifParams(true)
				.build();
		if (imageLoader.isInited() == false) {
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
			imageLoader.init(config);
		}
	}

	public boolean isNightModelChange() {
		return isNightModelChange;
	}

	public void setNightModelChange(boolean isNightModelChange) {
		this.isNightModelChange = isNightModelChange;
	}

	public LoginBean getLoginBean() {
		return LoginManager.getInst().getLoginDbBean().getUserinfo();
	}

	public void saveLoginBean(LoginBean loginBean) {
		LoginManager.getInst().saveLoginBean(loginBean);
	}

	public void clearLoginBean() {
		LoginManager.getInst().clearData();
	}

	public boolean isLogin() {
		return LoginManager.getInst().isLogin();
	}

}
