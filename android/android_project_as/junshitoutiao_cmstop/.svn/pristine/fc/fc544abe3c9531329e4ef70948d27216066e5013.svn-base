package com.cmstop.jstt.activity;

import java.util.ArrayList;

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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.IForceListenRefresh.OnRefreshStateListener;
import com.chengning.common.base.IForceListenRefresh.RefreshState;
import com.chengning.common.update.UpdateVersionUtil;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HomeWatcher;
import com.chengning.common.util.HomeWatcher.OnHomePressedListener;
import com.chengning.common.widget.ImageRadioButton;
import com.cmstop.jstt.App;
import com.cmstop.jstt.Const;
import com.cmstop.jstt.R;
import com.cmstop.jstt.SettingManager;
import com.cmstop.jstt.adapter.FragmentTabAdapter;
import com.cmstop.jstt.base.IForceListenRefreshExtend;
import com.cmstop.jstt.fragment.home.HomeVideoFragment;
import com.cmstop.jstt.fragment.home.ShopFragment;
import com.cmstop.jstt.fragment.home.WodeFragment;
import com.cmstop.jstt.fragment.home.ZiXunFragment;
import com.cmstop.jstt.interf.IFragmentBackListener;
import com.cmstop.jstt.service.UpdateBadgeService;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.SPHelper;
import com.chengning.common.util.StatusBarUtil;
import com.cmstop.jstt.utils.UIHelper;
import com.cmstop.jstt.utils.UmengShare;
import com.cmstop.jstt.views.FirstRunPage;

public class HomeActivity extends BaseFragmentActivity implements OnHomePressedListener {

	public static final String ACTION_FINISHHOME = App.class.getPackage().getName() + ".action_finishhome";
	
	public static final int SHOP_FRAGMENT = 2;
	
	/** 底部导航 */	
	private static RadioGroup mRadioGroup;
	private ImageRadioButton mTabHome;
	private ImageRadioButton mTabVideo;
	private ImageRadioButton mTabMall;
	private ImageRadioButton mTabWode;
	private ArrayList<Fragment> mFragmentsList;
	
	private HomeWatcher mHomeWatcher;
    private boolean mIsHomePressed;
    private boolean mIsHomeStop;
    private BroadcastReceiver finishReceiver;
    protected int mCurIndex = 0;
    private static int mPreTouchIndex = 0;
    private long lastTime = 0;
    private int lastNightModel;
    private boolean mIsFromPush;
    
    private Fragment mCurFragment;
    private Fragment mLastFragment;
    private RefreshState mRefreshState;
    
    private IFragmentBackListener mFragmentBackListener;
    
	public static void launch(Activity from){
		Intent intent = new Intent(from, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		from.startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_home_tab);
		if(Common.isTrue(SettingManager.getInst().getNightModel())){  
			StatusBarUtil.setBar(this, getResources().getColor(R.color.night_bg_color), false);
        }else{  
        	StatusBarUtil.setBar(this, getResources().getColor(R.color.mainbgcolor), true);
        }
		super.onCreate(savedInstanceState);
	}

	public static void setChangeView(int index) {
		if (mRadioGroup == null) {
			return;
		}
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
		mPreTouchIndex = index;
	}

	@Override
	public void initViews() {
		mRadioGroup = (RadioGroup) findViewById(R.id.tabRadioGroup);
		mTabHome = (ImageRadioButton) findViewById(R.id.tab_home);
		mTabVideo = (ImageRadioButton) findViewById(R.id.tab_video);
		mTabMall = (ImageRadioButton) findViewById(R.id.tab_mall);
		mTabWode = (ImageRadioButton) findViewById(R.id.tab_wode);
	}

	@Override
	public void initDatas() {
		lastNightModel = SettingManager.getInst().getNightModel();
		DisplayUtil.getInst().init(getActivity());
		
		addFragment();
		setChangeView(mCurIndex);
		getHandler().postDelayed(new Runnable() {	
			@Override
			public void run() {
				UpdateVersionUtil.checkUpdate(getActivity(), Const.UPDATE_APP_KEY, false);
			}
		}, 2000);

		FirstRunPage firstRunPage = new FirstRunPage(getActivity());
	
		// 用户反馈通知
//		new FeedbackAgent(getActivity()).sync();
		Intent intent = new Intent(this, UpdateBadgeService.class);
		startService(intent);
	}

	private void addFragment() {
		ZiXunFragment mHomeFragment1 =  new ZiXunFragment();
		HomeVideoFragment mHomeFragment2 =  new HomeVideoFragment();
		ShopFragment mHomeFragment3 =  new ShopFragment();
		WodeFragment mHomeFragment4 =  new WodeFragment();
		mFragmentsList = new ArrayList<Fragment>();
		mFragmentsList.add(mHomeFragment1);
		mFragmentsList.add(mHomeFragment2);
        mFragmentsList.add(mHomeFragment3);
		mFragmentsList.add(mHomeFragment4);
		
		if( null != mFragmentsList && 0 < mFragmentsList.size()) {
			FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, mFragmentsList, R.id.contentLayout, mRadioGroup);
	        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
	            @Override
	            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) { 
	            	mCurIndex = index;
	            	mCurFragment = mFragmentsList.get(mCurIndex);
	            	if(mCurIndex == 3){            		
	            		StatusBarUtil.setBar(getActivity(), getResources().getColor(R.color.ac_bg_color), false);
	            	}else{
	            		if(Common.isTrue(SettingManager.getInst().getNightModel())){  
	            			StatusBarUtil.setBar(getActivity(), getResources().getColor(R.color.night_bg_color), false);
	                    }else{  
	                    	StatusBarUtil.setBar(getActivity(), getResources().getColor(R.color.mainbgcolor), true);
	                    }
	            	}           	
	            }
	        });
		} else { 
			throw new IllegalStateException("main fragmentsList is null or size = 0");  
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
				handleTouchRefresh(0);
			}
		});
		mTabVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(1);
			}
		});
		mTabMall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(2);
			}
		});
		mTabWode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleTouchRefresh(3);
			}
		});
	}

	
	
	/**
	 * 点击当前tab，刷新当前栏目页
	 * @param index
	 */
	protected void handleTouchRefresh(int index) {
		if(mPreTouchIndex == index && index != 3){ 
    		if(mLastFragment != null && mLastFragment instanceof IForceListenRefreshExtend){
    			((IForceListenRefreshExtend)mLastFragment).setOnRefreshStateListener(null);
    		}
    		Fragment f = mFragmentsList.get(mPreTouchIndex);
    		mLastFragment = f;
    		if(f instanceof IForceListenRefreshExtend){
    			setRefreshState(((IForceListenRefreshExtend)f).getRefreshState());
    			((IForceListenRefreshExtend) f).setOnRefreshStateListener(mControlListener);
    			((IForceListenRefreshExtend) f).forceSetPageSelected(true);
    			((IForceListenRefreshExtend) f).forceRefresh();
    		}
		}
		mPreTouchIndex = mCurIndex;
	}
	
	@Override
	public void processHandlerMessage(Message msg) {
		
	}

	public void onNav(String channel){
		for(Fragment f: mFragmentsList){
			if(f instanceof ZiXunFragment){
				((ZiXunFragment)f).onNav(channel);
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
        	if (SettingManager.getInst().getNightModel() != lastNightModel  ) {
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
		super.onStart();
		handleOnStart();
	}
	
	public static final int HANDLENUMMAX = 2;
	private int handleNum = 0;
	private void handleOnStart(){
		if(mIsHomeStop){
			Fragment f = mFragmentsList.get(mCurIndex);
			if(null != f && f instanceof IForceListenRefreshExtend){
				if(Common.hasNet()){
					((IForceListenRefreshExtend) f).forceCheckRefresh();
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
		SPHelper.getInst().saveInt(SPHelper.KEY_HOME_TAB_INDEX_CACHE, mCurIndex);
	}

	@Override
	public Activity getActivity() {
		return this;
	}
	
	@Override
	public void onHomePressed() {
		mIsHomePressed = true;
		handleNum = 0;
	}

	@Override
	public void onHomeLongPressed() {
		mIsHomePressed = true;
		handleNum = 0;
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
//        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putBoolean("push", mIsFromPush);
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
    	mIsFromPush = savedInstanceState.getBoolean("push",false);
    }

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	
	
	private OnRefreshStateListener mControlListener = new OnRefreshStateListener() {
		
		@Override
		public void onStart() {
			mRefreshState = RefreshState.Refreshing;
		}
		
		@Override
		public void onFinish() {
			mRefreshState = RefreshState.RefreshComplete;
		}
	};
	
	private void setRefreshState(RefreshState state){
		this.mRefreshState = state;
		switch (mRefreshState) {
		case Refreshing:
			mControlListener.onStart();
			break;
		case RefreshComplete:
			mControlListener.onFinish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if(mCurFragment != null && mCurFragment instanceof IFragmentBackListener){
			IFragmentBackListener iBack = (IFragmentBackListener)mCurFragment;
			if(iBack.canGoBack()){
				iBack.goBack();
				return;
			}
		}
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastTime < 1500) {
			finish();
		} else {
			lastTime = currentTime;
			UIHelper.showToast(this, "再按一次退出");
		}	
		return;
	}
	
}
