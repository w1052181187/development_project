package com.cmstop.jstt.fragment.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.IForceListenRefresh.OnRefreshStateListener;
import com.chengning.common.base.IForceListenRefresh.RefreshState;
import com.chengning.common.base.IForceRefresh;
import com.chengning.common.base.SimpleFragmentPagerAdapter;
import com.cmstop.jstt.Const;
import com.cmstop.jstt.LoadStateManager;
import com.cmstop.jstt.LoadStateManager.LoadState;
import com.cmstop.jstt.R;
import com.cmstop.jstt.SettingManager;
import com.cmstop.jstt.base.IForceListenRefreshExtend;
import com.cmstop.jstt.base.IScrollCallBack;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.SPHelper;
import com.cmstop.jstt.views.MyTabPageIndicator;
import com.cmstop.jstt.views.ProgressRefreshView;
import com.umeng.analytics.MobclickAgent;

public class HomeVideoFragment extends BaseFragment implements IForceListenRefreshExtend {
	
	private static final String TAG = HomeVideoFragment.class.getSimpleName();
	
	private static final int MSG_UI = 1;
	private static final int LOGIN_CHANGE = 2;
	
	private View mView;
	
	private View mTop;
	private View mContent;
	private MyTabPageIndicator mIndicator;
	private ViewPager mPager;

	private ProgressRefreshView mProgressRefresh;

	private FragmentManager mFragmentManager;

	private LoadStateManager mLoadStateManager;
	private SimpleFragmentPagerAdapter mAdapter;

    private RecommendFragment mRecommendFragment;
	private Fragment mLastFragment;
	private RefreshState mRefreshState = RefreshState.RefreshComplete;
	private OnRefreshStateListener mListener;
	
	private int mCurIndex;
	private ArrayList<String> fragmentStrsList;
	private ArrayList<Fragment> fragmentsList;

	/**
	 * 是否点击更多导航栏
	 */
	private boolean isNavMore;
	
	private IScrollCallBack mScrollCallBack;
	private Button mTopBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Common.setTheme(getContext());
	    mView = inflater.inflate(R.layout.fragment_home_video, container, false);
	    return mView ;
	}

	@Override
	public void initViews() {
		
		mProgressRefresh = new ProgressRefreshView(getContext(), mView);

		mTop = mView.findViewById(R.id.top);
		mContent = mView.findViewById(R.id.content);
		mIndicator = (MyTabPageIndicator)mView.findViewById(R.id.indicator);
		mPager = (ViewPager)mView.findViewById(R.id.viewpager);
		mTopBtn = (Button) mView.findViewById(R.id.topbtn);
		
		mFragmentManager = getChildFragmentManager();
		
		mTop.setSelected(true);
	}

	@Override
	public void initDatas() {
		
		mScrollCallBack = new IScrollCallBack() {
			@Override
			public void show() {
				mTopBtn.setVisibility(View.VISIBLE);
			}
			@Override
			public void hidden() {
				mTopBtn.setVisibility(View.GONE);
			}
		};
		
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mProgressRefresh.setWaitVisibility(true);
					mProgressRefresh.setRefreshVisibility(false);
					mContent.setVisibility(View.INVISIBLE);
					break;
				case Success:
					mProgressRefresh.setRootViewVisibility(false);
					mContent.setVisibility(View.VISIBLE);
					break;
				case Failure:
					mProgressRefresh.setWaitVisibility(false);
					mProgressRefresh.setRefreshVisibility(true);
					mContent.setVisibility(View.INVISIBLE);
					break;
				default:
					break;
				}
			}
		});
		
		mLoadStateManager.setState(LoadState.Success);
		addFragments();
		
	}
	
	@Override
	public void installListeners() {
		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Success);
				addFragments();
			}
		});
		
		mTopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forceTop();
				mTopBtn.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch(msg.what){
		case LOGIN_CHANGE:
			
			break;
		default:
			break;
		}
	}
	
	private void addFragments(){
		ArrayList<String> strs = new ArrayList<String>();
		ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
		
		strs.add("视频");
		VideoFragment cf = new VideoFragment();
		cf.setmScrollCallback(mScrollCallBack);
		fragmentsList.add(cf);
		mLastFragment = cf;
		this.fragmentStrsList = strs;
		this.fragmentsList = fragmentsList;
		
	    mAdapter = new SimpleFragmentPagerAdapter(mFragmentManager,
	    		fragmentsList,strs); 
	    
		mPager.setAdapter(mAdapter);
	    mPager.setOffscreenPageLimit(2);
	    mIndicator.setViewPager(mPager);
	    mIndicator.setOnPageChangeListener(mOnPageChangeListener);
	    mIndicator.setCurrentItem(0);
	    mIndicator.notifyDataSetChanged();
	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			
			handleNavEventAnalytics(isNavMore ? "homemore" : "normal", Common.isListEmpty(fragmentStrsList) ? "" :fragmentStrsList.get(index));
			
			mCurIndex = index;
			 
			if(mLastFragment != null && mLastFragment instanceof IForceListenRefreshExtend){
				((IForceListenRefreshExtend)mLastFragment).setOnRefreshStateListener(null);
			}
			Fragment f = fragmentsList.get(index);
			mLastFragment = f;
			if(f instanceof IForceListenRefreshExtend){
				setRefreshState(((IForceListenRefreshExtend)f).getRefreshState());
				((IForceListenRefreshExtend) f).setOnRefreshStateListener(mControlListener);

				((IForceListenRefreshExtend) f).forceSetPageSelected(true);
			}else{
				setRefreshState(RefreshState.RefreshComplete);
			}
			
			if(f instanceof AbstractChannelItemListFragment){
				((AbstractChannelItemListFragment)f).dealScrollY();
			}else{
				mTopBtn.setVisibility(View.GONE);
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	
	/**
	 * 切换导航
	 * @param nav
	 */
	public void onNav(String nav){
		
		isNavMore = true;
		
		int index = -1;
		for(int i = 0; i < fragmentStrsList.size(); i++){
			if(nav.equals(fragmentStrsList.get(i))){
				index = i;
				break;
			}
		}
		if(index >= 0){
			mIndicator.setCurrentItem(index);
		}
	}
	
	/**
	 * 处理导航栏点击事件统计
	 * @param enter
	 * @param nav
	 */
	private void handleNavEventAnalytics(String enter, String nav) {
		this.isNavMore = false;
		Map<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("enter", enter);
		mHashMap.put("nav_index", nav);
		MobclickAgent.onEvent(getContext(), "nav_click", mHashMap);
	}

	@Override
	public void onStart(){
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (SettingManager.getInst().getNoPicModel() != SPHelper.getInst().getInt(SPHelper.KEY_HOME_VIDEO_NO_PIC_MODEL)) {
			if (null != mAdapter) {
				mAdapter.notifyDataSetChanged();
			}
			SPHelper.getInst().saveInt(SPHelper.KEY_HOME_VIDEO_NO_PIC_MODEL,
					SettingManager.getInst().getNoPicModel());
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}
	
	private void setRefreshState(RefreshState state){
		this.mRefreshState = state;
		if(mControlListener != null){
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
	}

	@Override
	public void forceRefresh() {
		if (mCurIndex == 0) {
			MobclickAgent.onEvent(getActivity(), Const.UMEVENT_HOME_MANLOAD);
		}
		if(mLastFragment != null && mLastFragment instanceof IForceListenRefreshExtend
				&& !(((IForceListenRefreshExtend)mLastFragment).getRefreshState() == RefreshState.Refreshing)){
			((IForceListenRefreshExtend)mLastFragment).forceRefresh();
			mTopBtn.setVisibility(View.GONE);
		}
	}

	@Override
	public void forceCheckRefresh() {
		if(mLastFragment != null && mLastFragment instanceof IForceListenRefreshExtend){
			((IForceListenRefreshExtend)mLastFragment).forceCheckRefresh();
		}
	}

	@Override
	public void forceTop() {
		if(mLastFragment != null && mLastFragment instanceof IForceListenRefreshExtend
				&& !(((IForceListenRefreshExtend)mLastFragment).getRefreshState() == RefreshState.Refreshing)){
			((IForceListenRefreshExtend)mLastFragment).forceTop();
		}
	}

	@Override
	public RefreshState getRefreshState() {
		return mRefreshState;
	}
	
	@Override
	public void setOnRefreshStateListener(OnRefreshStateListener listener) {
		this.mListener = listener;
	}

	@Override
	public void forceSetPageSelected(boolean isSelected) {
		if(mLastFragment != null && mLastFragment instanceof IForceListenRefreshExtend){
			((IForceListenRefreshExtend)mLastFragment).forceSetPageSelected(true);
		}
	}
	
	private OnRefreshStateListener mControlListener = new OnRefreshStateListener() {
		
		@Override
		public void onStart() {
			if(mListener != null){
				mListener.onStart();
			}
		}
		
		@Override
		public void onFinish() {
			if(mListener != null){
				mListener.onFinish();
			}
		}
	};
	
	/**
	 * 刷新首页
	 */
	private void refreshHome(){
		if(mAdapter != null){
			mIndicator.setCurrentItem(0);
			Fragment f = mAdapter.getItem(0);
			if(f instanceof IForceRefresh){
				((IForceRefresh)f).forceRefresh();
			}
		}
	}

}
