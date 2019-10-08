package com.chengning.fenghuovideo.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.app.ActivityInfo.ActivityState;
import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.IForceListenRefresh;
import com.chengning.common.base.SimpleFragmentPagerAdapter;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.LoadStateManager;
import com.chengning.fenghuovideo.LoadStateManager.LoadState;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.activity.LoginActivity;
import com.chengning.fenghuovideo.activity.MyprofileActivity;
import com.chengning.fenghuovideo.activity.SearchNewsActivity;
import com.chengning.fenghuovideo.activity.SettingActivity;
import com.chengning.fenghuovideo.activity.WodeActivity;
import com.chengning.fenghuovideo.base.AbstractChannelItemListFragment;
import com.chengning.fenghuovideo.base.AbstractChannelItemListFragment.OnRecommendHttpListener;
import com.chengning.fenghuovideo.base.IForceListenRefreshExtend;
import com.chengning.fenghuovideo.base.IScrollCallBack;
import com.chengning.fenghuovideo.data.access.ChannelListDA;
import com.chengning.fenghuovideo.data.bean.ChanBean;
import com.chengning.fenghuovideo.data.bean.ChannelBean;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.SPHelper;
import com.chengning.fenghuovideo.util.Utils;
import com.chengning.fenghuovideo.widget.MyTabPageIndicator;
import com.chengning.fenghuovideo.widget.ProgressRefreshView;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HomeFragment extends BaseFragment implements IForceListenRefreshExtend {

	protected static final int EVERYDAY_LOGIN_SUCCESS = 2;

	private static final int LOGIN_CHANGE = 3;

	private View view;
	private View mView;

	private View mUserImgLayout;
	private ImageView mUserImg;

	private View mContent;
	private View mArrow;
	private MyTabPageIndicator mIndicator;
	private ViewPager mPager;
//	private HomeEditChannelList mEdit;
	private ImageView mTopBtn;
	private ImageView mLogo;
	private TextView mSearchBtn;
	private ImageView mSetting;

	private FragmentManager mFragmentManager;

	private LoadStateManager mLoadStateManager;
	private SimpleFragmentPagerAdapter mAdapter;

	private ProgressRefreshView mProgressRefresh;

	/**
	 * 是否上传过频道
	 */
	private boolean isUploadChannel;

	private View mIndicatorTop;

	protected int mCurIndex;

	private ArrayList<String> fragmentStrsList;
	private ArrayList<Fragment> fragmentsList;
	
	/**
	 * 是否点击更多导航栏
	 */
	private boolean isNavMore;
	
	private IForceListenRefresh.RefreshState mRefreshState = IForceListenRefresh.RefreshState.RefreshComplete;
	private IForceListenRefresh.OnRefreshStateListener mListener;
	
	private IScrollCallBack mScrollCallBack;
	private HttpDataLoadSuccessListener mLoadSuccessListener;


	/**
	 * 刷新首页
	 */
	public void refreshHome() {
		if (isAdapterValid(mAdapter, 0)) {
			mIndicator.setCurrentItem(0);
			Fragment f = mAdapter.getItem(0);
			if (f instanceof IForceListenRefresh) {
				((IForceListenRefresh) f).forceRefresh();
			}

		}
	}
	
	/**
	 * 刷新当前频道
	 */
	public void refreshCurChannel() {
		if (isAdapterValid(mAdapter, mCurIndex)) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefresh && 
					!(((IForceListenRefresh)f).getRefreshState() == IForceListenRefresh.RefreshState.Refreshing)) {
				setRefreshState(((IForceListenRefresh)f).getRefreshState());
				((IForceListenRefresh) f).forceRefresh();
			}

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void initViews() {

		mView = view.findViewById(R.id.root);
		mProgressRefresh = new ProgressRefreshView(getActivity(), mView);

		mUserImgLayout = mView.findViewById(R.id.top_user_rl);
		mUserImg = (ImageView) mView.findViewById(R.id.top_user_btn);
		mSetting = (ImageView) mView.findViewById(R.id.title_bar_setting);

		mContent = mView.findViewById(R.id.content);
		mIndicator = (MyTabPageIndicator) mView.findViewById(R.id.indicator);
		mPager = (ViewPager) mView.findViewById(R.id.viewpager);

		mTopBtn = (ImageView) mView.findViewById(R.id.topbtn);
		mLogo = (ImageView) mView.findViewById(R.id.title_bar_logo);
		mSearchBtn = (TextView) mView.findViewById(R.id.title_bar_search);
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			mLogo.setColorFilter(getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
		}
		
		mFragmentManager = getChildFragmentManager();

	}

	private void initViewPager() {
		fragmentsList = new ArrayList<>();
		fragmentStrsList = new ArrayList<>();
		if (mFragmentManager == null) {
			mFragmentManager = getChildFragmentManager();
		}
		mAdapter = new SimpleFragmentPagerAdapter(mFragmentManager,
				fragmentsList, fragmentStrsList);

		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(0);
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(mOnPageChangeListener);
		mIndicator.setCurrentItem(0);
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

		initViewPager();
		initChannel(getActivity(), null);
	}

	/**
	 * 初始化用户头像
	 */
	private void initUserAvatar() {
		if (App.getInst().isLogin()) {
			changeUserState(0);
		} else {
			changeUserState(1);
		}
	}

	/**
	 * 初始化频道
	 */
	public void initChannel(FragmentActivity activity, HttpDataLoadSuccessListener listener) {
		this.mLoadSuccessListener = listener;
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager
				.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {

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
		List<ChanBean> channelList = ChannelListDA.getInst(activity)
				.queryMyChannelList();
		if (!Common.isListEmpty(channelList)) {
			addFragments(channelList);
			mLoadStateManager.setState(LoadState.Success);
		} else {
			mLoadStateManager.setState(LoadState.Init);
			addRecommendFragment();
		}
	}

	@Override
	public void installListeners() {

		mUserImgLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//			if (App.getInst().isLogin()) {
//				MyprofileActivity.launchByUid(getActivity(), App.getInst()
//					.getUserInfoBean().getUid(), "direct");
//			} else {
//				LoginActivity.launch(getActivity());
//			}
				WodeActivity.launch(getActivity());
			}
		});

		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				addRecommendFragment();
			}
		});
		
		mTopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				forceTop();
				mTopBtn.setVisibility(View.GONE);
			}
		});

		mSearchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchNewsActivity.launch(getActivity());
			}
		});

		mSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingActivity.launch(getActivity());
			}
		});

	}

	@Override
	public void processHandlerMessage(Message msg) {
	}

	private void addRecommendFragment() {
		removeLastFragment();
		fragmentStrsList.add("首页");
		RecommendFragment f1 = new RecommendFragment();
		f1.setOnRecommendHttpListener(mOnRecommendHttpListener);
		f1.setmScrollCallback(mScrollCallBack);
		fragmentsList.add(f1);
		mAdapter.notifyDataSetChanged();
		mIndicator.notifyDataSetChanged();
	}

	private void addFragments(List<ChanBean> channelList) {

		if (!Common.isListEmpty(channelList)) {
			removeLastFragment();
			int channelCount = channelList.size();
			for (int i = 0; i < channelCount; i++) {
				ChanBean b = channelList.get(i);
				if (i == 0) {
					fragmentStrsList.add(b.getName());
					RecommendFragment f1 = new RecommendFragment();
					f1.setOnRecommendHttpListener(mOnRecommendHttpListener);
					f1.setmScrollCallback(mScrollCallBack);
					// mRecommendFragment = f1;
					fragmentsList.add(f1);
				} else {
					fragmentStrsList.add(b.getName());
					ChannelFragment f2 = ChannelFragment.newInstance(b);
					f2.setmScrollCallback(mScrollCallBack);
					fragmentsList.add(f2);
				}
			}
			mAdapter.notifyDataSetChanged();
			mIndicator.notifyDataSetChanged();
		}

		if (mLoadSuccessListener != null) {
			mLoadSuccessListener.onSuccess();
		}
	}

	private void removeLastFragment() {
		try {
			// 在数据源更新前增加的代码，将上一次数据源的fragment对象从FragmentManager中删除
			if (mAdapter != null) {
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				List<Fragment> fragments = mFragmentManager.getFragments();
				if(fragments != null && fragments.size() > 0){
					Fragment f;
					for (int i = 0; i < fragments.size(); i++) {
						f = fragments.get(i);
						if (f != null) {
							ft.remove(fragments.get(i));
						}
					}
				}
				ft.commitAllowingStateLoss();
			}
		} catch (Exception ignored){

		}
		fragmentStrsList.clear();
		fragmentsList.clear();
	}

	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			mCurIndex = index;
			if (isAdapterValid(mAdapter, mCurIndex)) {
				Fragment f = mAdapter.getItem(mCurIndex);
				if(f instanceof AbstractChannelItemListFragment){
					((AbstractChannelItemListFragment)f).dealScrollY();
				}else{
					mTopBtn.setVisibility(View.GONE);
				}
			}
			// video
			NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	private OnRecommendHttpListener mOnRecommendHttpListener = new OnRecommendHttpListener() {

		@Override
		public void onHttpSucess(ChannelBean bean) {
			if (App.getInst().isLogin()) {
				handleIsLoginSuccess(bean);
			} else {
				handleNoLoginSuccess(bean);
			}
		}

		@Override
		public void onHttpFailure() {
			if (mLoadStateManager.getState() == LoadState.Init) {
				mLoadStateManager.setState(LoadState.Failure);
			}
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

	private String mCurVersion;

//	private boolean isUserSetted = true;

	/**
	 * 登录导航栏处理
	 * 
	 * @param bean
	 */
	protected void handleIsLoginSuccess(ChannelBean bean) {
		String myNav = bean.getUser_nav_list();

		String[] myChannels = null;
		if (!TextUtils.isEmpty(myNav)) {
			if ("0".equals(myNav)) {
				myNav = bean.getChan_list2();
//				isUserSetted = false;
			}
			if (!TextUtils.isEmpty(myNav)) {
				myChannels = TextUtils.split(myNav, ",");
			}
		} else {
			if (null == myNav) {
				myNav = bean.getChan_list2();
				if (!TextUtils.isEmpty(myNav)) {
					myChannels = TextUtils.split(myNav, ",");
				}
			} else {
				myChannels = new String[0];
			}

		}
		if (bean != null && myChannels != null) {
//			updateChannel(bean, myChannels);
		}
	}

	/**
	 * 更新频道
	 *
	 * @param bean
	 * @param myChannels
	 */
	private void updateChannel(ChannelBean bean, String[] myChannels) {
		String netTimeStr = bean.getUser_nav_list_time();
		// 避免localTime默认为0时的时间判断错误
		long netTime = 1;
		if (!TextUtils.isEmpty(netTimeStr)) {
			try {
				netTime = Long.parseLong(netTimeStr);
				netTime *= 1000;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		long localTime = SPHelper.getInst().getLong(
				SPHelper.KEY_USER_NAV_LIST_TIME_LOCAL);
		String localSaveId = SPHelper.getInst().getString(
				SPHelper.KEY_USER_ID_LOCAL);
		String netId = App.getInst().getUserInfoBean().getUid();
		String localSaveNav = SPHelper.getInst().getString(
				SPHelper.KEY_USER_NAV_LOCAL);
		String netNav = bean.getUser_nav_list();
//		boolean isNetNew = (netTime > localTime) || !isUserSetted
//				|| "0".equals(netNav)
//				|| (!TextUtils.isEmpty(netId) && !netId.equals(localSaveId));
		boolean isNetNew = (netTime > localTime)
				|| "0".equals(netNav)
				|| (!TextUtils.isEmpty(netId) && !netId.equals(localSaveId));
		// boolean isNetNew = (netTime > localTime) ||
		// (!TextUtils.isEmpty(netId) && !netId.equals(localSaveId));

		ArrayList<ChanBean> netChanList = new ArrayList<ChanBean>();
		ArrayList<ChanBean> netAllChanList = bean.getAll_chan_list();

		String allChanStr = SerializeUtil.serialize(netAllChanList);
		String allChanStrOld = SPHelper.getInst().getString(
				SPHelper.KEY_CHANNEL_LIST_ALL);

		// 接口返回数据new
//		if (isNetNew) {
			 if (isNetNew && !localSaveNav.equals(netNav)) {
//				 if ((isNetNew && !localSaveNav.equals(netNav)) || isUpdated) {
			// 记录频道名、索引
			HashMap<String, Integer> myChannelMap = new HashMap<String, Integer>();
			for (int i = 0; i < myChannels.length; i++) {
				myChannelMap.put(myChannels[i], i);
			}

			for (int i = 0; i < netAllChanList.size(); i++) {
				ChanBean cBean = netAllChanList.get(i);
				// local_other_index 保存全部列表时的索引
				cBean.setLocal_other_index(i + 1);
				if (i == 0) {
					// 我的频道 - 首页
					cBean.setLocal_my_index(1);
					netChanList.add(cBean);
				} else if (myChannelMap.containsKey(cBean.getId())) {
					// 我的频道 - 其他
					cBean.setLocal_my_index(myChannelMap.get(cBean.getId()) + 2);
					netChanList.add(cBean);
				} else {
					// 可选频道
					cBean.setLocal_my_index(-1);
				}
			}
			// 我的频道排序
			Collections.sort(netChanList, new Comparator<ChanBean>() {

				@Override
				public int compare(ChanBean lhs, ChanBean rhs) {
					return lhs.getLocal_my_index() - rhs.getLocal_my_index();
				}
			});

			ChannelListDA.getInst(getActivity()).deleteAll();
			ChannelListDA.getInst(getActivity()).insertList(netAllChanList);

			SPHelper.getInst().saveString(SPHelper.KEY_CHANNEL_LIST_ALL,
					allChanStr);
			SPHelper.getInst().saveLong(SPHelper.KEY_USER_NAV_LIST_TIME_LOCAL,
					netTime);
			SPHelper.getInst().saveString(SPHelper.KEY_USER_ID_LOCAL,
					App.getInst().getUserInfoBean().getUid());
			SPHelper.getInst().saveString(SPHelper.KEY_USER_NAV_LOCAL, netNav);

			mLoadStateManager.setState(LoadState.Success);

			if(getActivityInfo().getActivityState() != ActivityState.Killed){
				addFragments(netChanList);
			}

		} else {
			List<ChanBean> mySaveList = ChannelListDA.getInst(getActivity())
					.queryMyChannelList();

			boolean isSame = !TextUtils.isEmpty(allChanStr)
					&& allChanStr.equals(allChanStrOld);
			if (!isSame) {

				// 升级app,新增的频道加入到我的频道
				mySaveList = handleAddNewChan(bean, mySaveList);

				// 记录频道名、索引
				HashMap<String, Integer> myChannelMap = new HashMap<String, Integer>();
				HashSet<String> netAllChanSet = new HashSet<String>();
				for (ChanBean b : netAllChanList) {
					netAllChanSet.add(b.getId());
				}
				int myIndex = 0;
				for (ChanBean b : mySaveList) {
					if (netAllChanSet.contains(b.getId())) {
						myIndex++;
						myChannelMap.put(b.getId(), myIndex);
					}
				}

				for (int i = 0; i < netAllChanList.size(); i++) {
					ChanBean cBean = netAllChanList.get(i);
					// local_other_index 保存全部列表时的索引
					cBean.setLocal_other_index(i + 1);
					if (i == 0) {
						// 我的频道 - 首页
						cBean.setLocal_my_index(1);
						netChanList.add(cBean);
					} else if (myChannelMap.containsKey(cBean.getId())) {
						// 我的频道 - 其他
						cBean.setLocal_my_index(myChannelMap.get(cBean.getId()) + 2);
						netChanList.add(cBean);
					} else {
						// 可选频道
						cBean.setLocal_my_index(-1);
					}
				}

				// 我的频道排序
				Collections.sort(netChanList, new Comparator<ChanBean>() {

					@Override
					public int compare(ChanBean lhs, ChanBean rhs) {
						return lhs.getLocal_my_index()
								- rhs.getLocal_my_index();
					}
				});

				ChannelListDA.getInst(getActivity()).deleteAll();
				ChannelListDA.getInst(getActivity()).insertList(netAllChanList);

				SPHelper.getInst().saveString(SPHelper.KEY_CHANNEL_LIST_ALL,
						allChanStr);

				localTime = System.currentTimeMillis();
				SPHelper.getInst().saveLong(SPHelper.KEY_USER_NAV_LIST_TIME_LOCAL, localTime);

				if(getActivityInfo().getActivityState() != ActivityState.Killed){
					addFragments(netChanList);
				}
			} else {
				netChanList.addAll(mySaveList);
			}

			mLoadStateManager.setState(LoadState.Success);

			if (netTime < localTime
					&& (!TextUtils.isEmpty(netId) && netId.equals(localSaveId))) {
				// (上传本地列表顺序)
//				uploadChannel(netChanList);
			}
		}
	}

	/**
	 * 升级app,新增的频道加入到我的频道
	 * @param bean
	 * @param mySaveList
	 * @return
	 */
	private List<ChanBean> handleAddNewChan(ChannelBean bean, List<ChanBean> mySaveList) {
		ArrayList<ChanBean> newChanList = bean.getAll_chan_list();
		ArrayList<ChanBean> oldChanList = (ArrayList<ChanBean>) ChannelListDA.getInst(getActivity()).queryAll();
		
		ArrayList<String> list = new ArrayList<String>();
		for (ChanBean chanBean : oldChanList) {
			list.add(chanBean.getId());
		}
		
		// 0为首页，所以从1开始
		int addedCount = 1;
		for (ChanBean bean2 : newChanList) {
			if (!list.contains(bean2.getId())) {
				mySaveList.add(addedCount, bean2);
				addedCount++;
			}
		}
		return mySaveList;
	}

	/**
	 * 未登录导航栏处理
	 *
	 * @param bean
	 */
	protected void handleNoLoginSuccess(ChannelBean bean) {

		String myChannel = bean.getChan_list2();
		if (myChannel != null) {
			final String[] myChannels = TextUtils.split(myChannel, ",");
			updateChannel(bean, myChannels);
		}

	}

	@Override
	public void onStart() {
		super.onStart();
	}


	@Override
	public void onResume() {
		super.onResume();
		initUserAvatar();
//		if (SettingManager.getInst().getNoPicModel() != SPHelper.getInst().getInt(SPHelper.KEY_HOME_NOPIC_MODEL)) {
//			if (isAdapterValid(mAdapter, 0)) {
//				mAdapter.notifyDataSetChanged();
//			}
//
//			SPHelper.getInst().saveInt(SPHelper.KEY_HOME_NOPIC_MODEL,
//					SettingManager.getInst().getNoPicModel());
//		}
		
	}

	/**
	 * adapter是否有效
	 * @param mAdapter
	 * @param index
	 * @return
	 */
	private boolean isAdapterValid(SimpleFragmentPagerAdapter mAdapter, int index) {
		return mAdapter != null && mAdapter.getCount() > index;
	}

	/**
	 * 改变用户状态
	 * 
	 * @param i
	 */
	private void changeUserState(int i) {
		switch (i) {
		case 0:
			Utils.handleAvatar(App.getInst().getUserInfoBean().getFace(),
					mUserImg, Utils.mCircleOptions);

			break;
		case 1:
			Utils.handleAvatar("drawable://"+ R.mipmap.denglu,
					mUserImg, Utils.mCircleOptions);
			break;

		default:
			break;
		}
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			mUserImg.setColorFilter(getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		view = inflater.inflate(R.layout.fragment_home, null);
		return view;
	}

	/**
	 * 登录改变更新
	 * 
	 * @param data
	 */
	public void refresh(String data) {
		initUserAvatar();
		if (!TextUtils.isEmpty(data)) {
			getHandler().obtainMessage(LOGIN_CHANGE, data).sendToTarget();
		}
		refreshHome();
	}

	public void setChangeToHome() {
		if (null == mIndicator) {
			return;
		}
		mIndicator.setCurrentItem(0);
	}

	private IForceListenRefresh.OnRefreshStateListener mControlListener = new IForceListenRefresh.OnRefreshStateListener() {
		
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
	
	private void setRefreshState(IForceListenRefresh.RefreshState state){
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
		refreshCurChannel();
	}

	@Override
	public void forceCheckRefresh() {
		if (isAdapterValid(mAdapter, mCurIndex)) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefresh) {
				((IForceListenRefresh) f).forceCheckRefresh();
			}

		}
	}

	@Override
	public IForceListenRefresh.RefreshState getRefreshState() {
		return mRefreshState;
	}

	@Override
	public void setOnRefreshStateListener(IForceListenRefresh.OnRefreshStateListener listener) {
		this.mListener = listener;
		
	}

	@Override
	public void forceSetPageSelected(boolean isSelected) {
		if (isAdapterValid(mAdapter, mCurIndex)) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefreshExtend) {
				((IForceListenRefreshExtend) f).forceSetPageSelected(true);
			}

		}
	}

	@Override
	public void forceTop() {
		if (isAdapterValid(mAdapter, mCurIndex)) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefreshExtend) {
				((IForceListenRefreshExtend) f).forceTop();
			}

		}
	}

	public interface HttpDataLoadSuccessListener{
		void onSuccess();
	}
}
