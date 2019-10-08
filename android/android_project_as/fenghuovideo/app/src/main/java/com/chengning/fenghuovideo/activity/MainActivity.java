package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.IForceListenRefresh;
import com.chengning.common.update.UpdateVersionUtil;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HomeWatcher;
import com.chengning.common.util.HomeWatcher.OnHomePressedListener;
import com.chengning.common.util.StatusBarUtil;
import com.chengning.common.widget.ImageRadioButton;
import com.chengning.common.widget.NestRadioGroup;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.LoginManager;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.VideoModuleManager;
import com.chengning.fenghuovideo.adapter.FragmentTabAdapterExtend;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.event.CommentSuccessEvent;
import com.chengning.fenghuovideo.fragment.HomeFragment;
import com.chengning.fenghuovideo.nicevideo.FhItemData;
import com.chengning.fenghuovideo.nicevideo.FhNiceVideoWidget;
import com.chengning.fenghuovideo.nicevideo.FhVideoDetailWidget;
import com.chengning.fenghuovideo.nicevideo.FhVideoPlayerHelper;
import com.chengning.fenghuovideo.service.UpdateBadgeService;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.util.UmengShare;
import com.chengning.fenghuovideo.widget.VideoDetailWidget;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;

import de.greenrobot.event.Subscribe;

public class MainActivity extends BaseFragmentActivity implements
		OnHomePressedListener {

	public static final String ACTION_FINISHHOME = App.class.getPackage()
			.getName() + ".action_finishhome";

	private static final int LOGIN_CHANGE = 0;
//
//	private static final int INDEX_HOME = 0;
//	private static final int INDEX_INVITE = 2;
//	private static final int INDEX_CIRCLE = 1;
//	private static final int INDEX_WODE = 3;

//	/** 底部导航 */
//	private static NestRadioGroup mRadioGroup;
//	private ImageRadioButton mTabHome;
//	private ImageRadioButton mTabInvite;
//	private ImageRadioButton mTabCircle;
//	private ImageRadioButton mTabWode;
////	private View mContactRed;
	private HomeWatcher mHomeWatcher;
	private boolean mIsHomePressed;
	private boolean mIsHomeStop;
	private BroadcastReceiver finishReceiver;

	private long lastTime = 0;

//	private int mCurIndex = 0;
//	private int mPreTouchIndex = 0;
	private boolean mIsFromPush;

	private int lastNightModel;

	private boolean isLoginChange;

	private HomeFragment mHomeFragment;
//	private boolean isNightMode;
	private View mRoot;
	private  boolean isFirstLoad;
//	private FirstRunPage firstRunPage;
	
	private FhVideoDetailWidget mVideoDetail;

	public static void launch(Activity from) {
		Intent intent = new Intent(from, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		from.startActivity(intent);
	}

	public static void launch(Activity from, int index) {
		Intent intent = new Intent(from, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		intent.putExtra("night_index", index);
		from.startActivity(intent);
	}

	public static void launchAfterLoginChange(Activity from, String data) {
		Intent intent = new Intent(from, MainActivity.class);
		intent.putExtra("is_login_change", true);
		intent.putExtra("data", data);
		from.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_main);
		StatusBarUtil.setBar(getActivity(), getResources().getColor(R.color.home_red), false);
		super.onCreate(savedInstanceState);
	}

//	public static void setChangeView(int index) {
//		View v = mRadioGroup.getChildAt(index);
//		if (v instanceof ImageRadioButton) {
//			((ImageRadioButton) v).setChecked(true);
//		} else if (v instanceof RelativeLayout) {
//			RelativeLayout rv = (RelativeLayout) v;
//			int childCount = rv.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				View v2 = rv.getChildAt(i);
//				if (v2 instanceof ImageRadioButton) {
//					((ImageRadioButton) v2).setChecked(true);
//				}
//			}
//		}
//	}

	@Override
	public void initViews() {
		mRoot = findViewById(R.id.home_root);
//		mContactRed = findViewById(R.id.tab_contact_red);
	}

	@Override
	public void initDatas() {
		isFirstLoad = true;
		lastNightModel = SettingManager.getInst().getNightModel();
		DisplayUtil.getInst().init(getActivity());
		isLoginChange = getIntent().getBooleanExtra("is_login_change", false);
//		mCurIndex = getIntent().getIntExtra("night_index",0);

		if (mRoot.getVisibility() == View.GONE) {
			mRoot.setVisibility(View.VISIBLE);
		}
		mVideoDetail = new FhVideoDetailWidget(this);
		addFragment();

//		final FirstRunPage.FirstRunListener firstRunListener = new FirstRunPage.FirstRunListener() {
//
//			@Override
//			public void firstRunEnd() {
//				if (mHomeFragment != null) {
//					mHomeFragment.initChannel(getActivity(), new HomeFragment.HttpDataLoadSuccessListener() {
//						@Override
//						public void onSuccess() {
//							if (!isLoginChange && isFirstLoad) {
//								isFirstLoad = false;
//								LoginManager.getInst().everydayLogin(
//										(BaseFragmentActivity) getActivity(),
//										App.getInst().getUserInfoBean().getUid());
//
//							}
//						}
//					});
//				}
//			}
//		};
//		firstRunPage = new FirstRunPage(getActivity(),firstRunListener);
//		if (firstRunPage.isNeedShow()) {
//			firstRunPage.init();
//		} else {
//			getHandler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					firstRunEnd();
//				}
//			}, 200);
//		}

		getHandler().postDelayed(new Runnable() {
			@Override
			public void run() {
				UpdateVersionUtil.checkUpdate(getActivity(), Consts.UPDATE_APP_KEY, false);
			}
		}, 2000);
		
		Intent intent = new Intent(this, UpdateBadgeService.class);
		startService(intent);
//		isNightMode = Common.isTrue(SettingManager.getInst().getNightModel());

	}
	
	public void launchVideoDetailWidget(View view, FhItemData data, String type){
		mVideoDetail.launch(view, data, type, false);
	}
	
	public void firstRunEnd() {
		if (mHomeFragment != null) {
			mHomeFragment.initChannel(getActivity(), new HomeFragment.HttpDataLoadSuccessListener() {
				@Override
				public void onSuccess() {
					if (!isLoginChange && isFirstLoad) {
						isFirstLoad = false;
//						LoginManager.getInst().everydayLogin(
//								(BaseFragmentActivity) getActivity(),
//								App.getInst().getUserInfoBean().getUid());

					}
				}
			});
		}
	}

	private void addFragment() {
		mHomeFragment = new HomeFragment();
		FragmentManager mFragmentManager = getSupportFragmentManager();
		mFragmentManager.beginTransaction()
				.replace(R.id.contentLayout, mHomeFragment)
				.commitAllowingStateLoss();
	}

	@Subscribe
	public void onEventMainThread(CommentSuccessEvent event) {
		mVideoDetail.onEventMainThread(event);
	}

	@Override
	public void installListeners() {
		finishReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				finish();
			}
		};
		IntentFilter intentFileter = new IntentFilter(ACTION_FINISHHOME);
		registerReceiver(finishReceiver, intentFileter);

	}
	
	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent == null) {
			return;
		}
		boolean isLoginChange = intent
				.getBooleanExtra("is_login_change", false);
		if (isLoginChange) {
			String data = intent.getStringExtra("data");
//			setChangeView(mCurIndex);
			refreshHome(data);
		}
		super.onNewIntent(intent);
	}

	private void refreshHome(String data) {
		if (mHomeFragment.isAdded()) {
			mHomeFragment.refresh(data);
		}
	}

	public void onNav(String channel){

		if(mHomeFragment != null){
			mHomeFragment.onNav(channel);
		}

	}
	
//	private void switchTab(String channel) {
//		for (int i = 0; i < mFragmentsList.size(); i++) {
//			View v = mRadioGroup.getChildAt(i);
//			String curChannel;
//			if (v instanceof ImageRadioButton) {
//				curChannel = ((ImageRadioButton) v).getText().toString();
//				if (TextUtils.equals(channel, curChannel)) {
//					setChangeView(i);
//					break;
//				}
//			}
//		}
//	}

	@Override
	public void onResume() {
		super.onResume();
		mHomeWatcher = new HomeWatcher(this);
		mHomeWatcher.setOnHomePressedListener(this);
		mHomeWatcher.startWatch();

		if (App.getInst().isNightModelChange()) {
			if (SettingManager.getInst().getNightModel() != lastNightModel) {
				finish();
				launch(getActivity());
			}
			App.getInst().setNightModelChange(false);
		}
		//取消桌面红点
		Common.cancleBadge(getActivity());
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
	}

	@Override
	public void onPause() {
		super.onPause();
		mHomeWatcher.setOnHomePressedListener(null);
		mHomeWatcher.stopWatch();
	}

	@Override
	public void onDestroy() {
		NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
		super.onDestroy();
		unregisterReceiver(finishReceiver);
	}

	@Override
	protected void onStart() {
		handleOnStart();
		super.onStart();
	}
	
	public static final int HANDLENUMMAX = 2;
	private int handleNum = 0;
	private void handleOnStart(){
//		if (firstRunPage.isNeedShow()) {
//			return;
//		}
		if(mIsHomeStop){
//			setChangeView(mCurIndex);
			Fragment f = mHomeFragment;
			if(null != f && f instanceof IForceListenRefresh){
				if(Common.hasNet()){
					((IForceListenRefresh) f).forceCheckRefresh();
					mIsHomePressed = false;
					mIsHomeStop = false;
				}else{
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							if(handleNum >= HANDLENUMMAX){
								mIsHomePressed = false;
								mIsHomeStop = false;
								return;
							}
							handleNum ++;
							handleOnStart();
						}
					}, 1000);
				}
			}
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		handleNum = HANDLENUMMAX;
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mIsHomeStop = mIsHomePressed;
		// 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
		if (mIsHomeStop) {
			NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
		} else {
			NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
//			NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
		}
	}

	@Override
	public MainActivity getActivity() {
		return MainActivity.this;
	}

	@Override
	public void onHomePressed() {
		mIsHomePressed = true;
	}

	@Override
	public void onHomeLongPressed() {
		mIsHomePressed = true;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean("push", mIsFromPush);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mIsFromPush = savedInstanceState.getBoolean("push", false);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public void onBackPressed() {
		if(FhVideoPlayerHelper.getInst().onBack()){
			return;
		}
		if(mVideoDetail.onBackPressed()){
			return;
		}

//		if (mHomeFragment.isEditChannelShow()) {
//			mHomeFragment.dismissEditChannel();
//		} else {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastTime < 1500) {
				finish();
			} else {
				lastTime = currentTime;
				UIHelper.showToast(this, "再按一次退出");
			}
//		}

		return;
	}

}
