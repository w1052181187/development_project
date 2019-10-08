package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.IForceListenRefresh;
import com.chengning.common.update.UpdateVersionUtil;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HomeWatcher;
import com.chengning.common.util.HomeWatcher.OnHomePressedListener;
import com.chengning.common.widget.ImageRadioButton;
import com.chengning.common.widget.NestRadioGroup;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.adapter.FragmentTabAdapterExtend;
import com.chengning.yiqikantoutiao.fragment.HomeFragment;
import com.chengning.yiqikantoutiao.fragment.InviteFragment;
import com.chengning.yiqikantoutiao.fragment.MyFragment;
import com.chengning.yiqikantoutiao.fragment.VideoFragment;
import com.chengning.yiqikantoutiao.service.UpdateBadgeService;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.StatusBarUtil;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.util.UmengShare;
import com.chengning.yiqikantoutiao.widget.FirstRunPage;

import java.util.ArrayList;

public class MainActivity extends BaseFragmentActivity implements
		OnHomePressedListener {

	public static final String ACTION_FINISHHOME = App.class.getPackage()
			.getName() + ".action_finishhome";

	private static final int LOGIN_CHANGE = 0;
	
	private static final int INDEX_HOME = 0;
	private static final int INDEX_CIRCLE = 1;
	private static final int INDEX_INVITE = 2;
	private static final int INDEX_WODE = 3;

	/** 底部导航 */
	private static NestRadioGroup mRadioGroup;
	private ImageRadioButton mTabHome;
	private ImageRadioButton mTabVideo;
	private ImageRadioButton mTabInvite;
	private ImageRadioButton mTabWode;

	private ArrayList<Fragment> mFragmentsList;
	private HomeWatcher mHomeWatcher;
	private boolean mIsHomePressed;
	private boolean mIsHomeStop;
	private BroadcastReceiver finishReceiver;

	private long lastTime = 0;

	private int mCurIndex = 0;
	private int mPreTouchIndex = 0;
	private boolean mIsFromPush;

	private int lastNightModel;

	private boolean isLoginChange;

	private HomeFragment mHomeFragment;
	private VideoFragment mVideoFragment;
	private InviteFragment mInviteFragment;
	private MyFragment mMyFragment;
	private boolean isNightMode;
	private View mRoot;
	private  boolean isFirstLoad;
	private FirstRunPage firstRunPage;

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

	public static void setChangeView(int index) {
		View v = mRadioGroup.getChildAt(index);
		if (v instanceof ImageRadioButton) {
			((ImageRadioButton) v).setChecked(true);
		} else if (v instanceof RelativeLayout) {
			RelativeLayout rv = (RelativeLayout) v;
			int childCount = rv.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View v2 = rv.getChildAt(i);
				if (v2 instanceof ImageRadioButton) {
					((ImageRadioButton) v2).setChecked(true);
				}
			}
		}
	}

	@Override
	public void initViews() {
		mRoot = findViewById(R.id.home_root);
		mRadioGroup = (NestRadioGroup) findViewById(R.id.tabRadioGroup);
		mTabHome = (ImageRadioButton) findViewById(R.id.tab_home);
		mTabInvite = (ImageRadioButton) findViewById(R.id.tab_invite);
		mTabVideo = (ImageRadioButton) findViewById(R.id.tab_video);
		mTabWode = (ImageRadioButton) findViewById(R.id.tab_wode);
	}

	@Override
	public void initDatas() {
		isFirstLoad = true;
		lastNightModel = SettingManager.getInst().getNightModel();
		DisplayUtil.getInst().init(getActivity());
		isLoginChange = getIntent().getBooleanExtra("is_login_change", false);
		mCurIndex = getIntent().getIntExtra("night_index",0);

		if (mRoot.getVisibility() == View.GONE) {
			mRoot.setVisibility(View.VISIBLE);
		}
		addFragment();
        setChangeView(mCurIndex);

		final FirstRunPage.FirstRunListener firstRunListener = new FirstRunPage.FirstRunListener() {

			@Override
			public void firstRunEnd() {
				if (mHomeFragment != null) {
					mHomeFragment.initChannel(getActivity(), new HomeFragment.HttpDataLoadSuccessListener() {
						@Override
						public void onSuccess() {
							if (!isLoginChange && isFirstLoad) {
								isFirstLoad = false;
								LoginManager.getInst().everydayLogin(
										(BaseFragmentActivity) getActivity(),
										App.getInst().getUserInfoBean().getUid());

							}
						}
					});
				}
			}
		};
		firstRunPage = new FirstRunPage(getActivity(),firstRunListener);
		if (firstRunPage.isNeedShow()) {
			firstRunPage.init();
		} else {
			getHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					firstRunListener.firstRunEnd();
				}
			}, 200);
		}

		getHandler().postDelayed(new Runnable() {
			@Override
			public void run() {
				UpdateVersionUtil.checkUpdate(getActivity(), Consts.UPDATE_APP_KEY, false);
			}
		}, 2000);

		// 用户反馈通知
//		new FeedbackAgent(getActivity()).sync();
		
		Intent intent = new Intent(this, UpdateBadgeService.class);
		startService(intent);
		isNightMode = Common.isTrue(SettingManager.getInst().getNightModel());

	}

	private void addFragment() {
		mHomeFragment = new HomeFragment();
		mVideoFragment = new VideoFragment();
		mInviteFragment = new InviteFragment();
		mMyFragment = new MyFragment();
		mFragmentsList = new ArrayList<Fragment>();
		mFragmentsList.add(mHomeFragment);
		mFragmentsList.add(mVideoFragment);
		mFragmentsList.add(mInviteFragment);
		mFragmentsList.add(mMyFragment);

		if (!Common.isListEmpty(mFragmentsList)) {
			FragmentTabAdapterExtend tabAdapter = new FragmentTabAdapterExtend(
					this, mFragmentsList, R.id.contentLayout, mRadioGroup);
			tabAdapter
					.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapterExtend.OnRgsExtraCheckedChangedListener() {
						@Override
						public void OnRgsExtraCheckedChanged(
								NestRadioGroup radioGroup, int checkedId,
								int index) {

							switch (index) {
								case INDEX_HOME:
									if (null != mHomeFragment) {
										mHomeFragment.setChangeToHome();
										StatusBarUtil.setBar(getActivity(), getResources().getColor(R.color.home_red), false);
									}
                                    mCurIndex = index;
									break;
								case INDEX_INVITE:
									StatusBarUtil.setBar(getActivity(), getResources().getColor(isNightMode
											? R.color.night_bg_color : R.color.nav_bg), isNightMode ? false : true);
									if (!App.getInst().isLogin()) {
										mCurIndex = mPreTouchIndex;
										setChangeView(mCurIndex);
										LoginActivity.launch(getActivity());
									} else {
										mCurIndex = index;
									}
									break;
								case INDEX_CIRCLE:
									if (null != mVideoFragment) {

										StatusBarUtil.setBar(getActivity(), getResources().getColor(isNightMode ?
												R.color.night_bg_color : R.color.nav_bg), isNightMode ? false : true);
									}
                                    mCurIndex = index;
									break;
								case INDEX_WODE:
									StatusBarUtil.setBar(getActivity(), getResources().getColor(R.color.wode_statusbar_color), false);
                                    mCurIndex = index;
                                    break;
								default:
									break;
							}

						}
					});
		} else {
			throw new IllegalStateException(
					"main fragmentsList is null or size = 0");
		}

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
		
		mTabHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(INDEX_HOME);
			}
		});
		mTabInvite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(INDEX_INVITE);
			}
		});
		mTabVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(INDEX_CIRCLE);
			}
		});
		mTabWode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(INDEX_WODE);
			}
		});

	}

	/**
	 * 点击当前tab，刷新当前栏目页
	 * @param index
	 */
	protected void handleTouchRefresh(int index) {
		if(mPreTouchIndex == index && index != INDEX_WODE){ 
    		switch (index) {
				case INDEX_HOME :
					mHomeFragment.refreshCurChannel();
					break;
				case INDEX_INVITE:
					mInviteFragment.refresh();
					break;
				case INDEX_CIRCLE :
					mVideoFragment.refreshVideo();
					break;

				default :
					break;
			}
		}
		mPreTouchIndex = mCurIndex;
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
			setChangeView(mCurIndex);
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
		for(Fragment f: mFragmentsList){
			if(f instanceof HomeFragment){
				((HomeFragment)f).onNav(channel);
				switchTab(channel);
			}
		}
	}
	
	private void switchTab(String channel) {
		for (int i = 0; i < mFragmentsList.size(); i++) {
			View v = mRadioGroup.getChildAt(i);
			String curChannel;
			if (v instanceof ImageRadioButton) {
				curChannel = ((ImageRadioButton) v).getText().toString();
				if (TextUtils.equals(channel, curChannel)) {
					setChangeView(i);
					break;
				}
			}
		}
	}

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
	}

	@Override
	public void onPause() {
		super.onPause();
		mHomeWatcher.setOnHomePressedListener(null);
		mHomeWatcher.stopWatch();
	}

	@Override
	public void onDestroy() {
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
		if (firstRunPage.isNeedShow()) {
			return;
		}
		if(mIsHomeStop){
			setChangeView(mCurIndex);
			Fragment f = mFragmentsList.get(mCurIndex);
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

		if (mHomeFragment.isEditChannelShow()) {
			mHomeFragment.dismissEditChannel();
		} else {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastTime < 1500) {
				finish();
			} else {
				lastTime = currentTime;
				UIHelper.showToast(this, "再按一次退出");
			}
		}

		return;
	}

}
