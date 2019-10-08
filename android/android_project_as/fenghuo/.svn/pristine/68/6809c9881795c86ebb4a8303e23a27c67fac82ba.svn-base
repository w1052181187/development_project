package com.chengning.fenghuo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.app.ActivityInfo.ActivityState;
import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.IForceListenRefresh;
import com.chengning.common.base.SimpleFragmentPagerAdapter;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts.CirclePublishType;
import com.chengning.fenghuo.LoadStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.DiscussActivity;
import com.chengning.fenghuo.adapter.CircleChannelItemRecommendAdapter;
import com.chengning.fenghuo.base.AbstractChannelItemListFragment;
import com.chengning.fenghuo.base.BasePageCircleListFragment;
import com.chengning.fenghuo.base.IForceListenRefreshExtend;
import com.chengning.fenghuo.base.IScrollCallBack;
import com.chengning.fenghuo.data.access.CircleChannelListDA;
import com.chengning.fenghuo.data.bean.CircleChanBean;
import com.chengning.fenghuo.event.CircleBgChangeEvent;
import com.chengning.fenghuo.fragment.CircleHomeChannelFragment.OnRecommendHttpListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.SPHelper;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.NoScrollViewPager;
import com.chengning.fenghuo.widget.ProgressRefreshView;
import com.chengning.fenghuo.widget.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.TabPageIndicator;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public class CircleFragment extends BaseFragment implements IForceListenRefresh{

	private static final String TAG = CircleFragment.class.getSimpleName();
	private static final int TITLE_ALL = 0x10;
	private static final int TITLE_HOT = 0x11;
	
	
	private static final int DATA_SUCCESS = 2;

	private View mView;
	private TitleBar mTitleBar;
	private TabPageIndicator mIndicator;
	private NoScrollViewPager mPager;

	private Activity mContext;

	private SimpleFragmentPagerAdapter mAdapter;

	private LoadStateManager mLoadStateManager;

	private ProgressRefreshView mProgressRefresh;

	protected int mCurIndex;

	private View view;
	private PopupWindow popupWindow;
	private TextView popDuanping;
	private TextView popChangwen;
	
	private View viewTitle;
	private LinearLayout viewContent;
	private PopupWindow popupWindowTitle;
	private TextView popTitleAll;
	private List<TextView> popTitleTvList = new ArrayList<TextView>();;
	
	protected CirclePublishType mType;
	
	private OnRecommendHttpListener mOnRecommendHttpListener = new OnRecommendHttpListener() {

		@Override
		public void onHttpSucess(ArrayList<CircleChanBean> chanList) {
			CircleChannelListDA.getInst(mContext).insertList(chanList);
			getHandler().obtainMessage(DATA_SUCCESS,chanList).sendToTarget();
		}
	};

	private FragmentManager mFragmentManager;

	protected View mTopRl;

	private ImageView mTopBtn;
	private IScrollCallBack mScrollCallBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_circle, container, false);
		return mView;
	}

	@Override
	public void initViews() {
		mContext = getContext();
		mProgressRefresh = new ProgressRefreshView(getContext(), mView);

		mIndicator = (TabPageIndicator) mView
				.findViewById(R.id.home_circle_indicator);
		mIndicator.setVisibility(View.GONE);
		mPager = (NoScrollViewPager) mView.findViewById(R.id.home_circle_pager);
		mTopRl = (RelativeLayout) mView
				.findViewById(R.id.home_circle_content_layout);
		mTitleBar = new TitleBar(mContext, mView);
		mTitleBar.setTitle("圈子·全部");
		mTitleBar.getTitle().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nav_arrow_down, 0);
		mTitleBar.getTitle().setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.titile_bar_circle_title_drawablepadding));
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			mTitleBar.setRightButton(R.drawable.circle_publish_white);
		} else {
			mTitleBar.setRightButton(R.drawable.circle_publish);
		}

		mTopBtn = (ImageView) mView.findViewById(R.id.topbtn);
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

		mPager.setNoScroll(true);
		initLoadState();
		initChannel();
		handleCircleBg();
		EventBus.getDefault().register(this);

	}

	/**
	 * 初始化加载状态
	 */
	private void initLoadState() {
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager
				.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
					@Override
					public void OnStateChange(LoadState state, Object obj) {
						switch (state) {
							case Init:
								mProgressRefresh.setWaitVisibility(true);
								mProgressRefresh.setRefreshVisibility(false);
								mTopRl.setVisibility(View.GONE);
								break;
							case Success:
								mProgressRefresh.setRootViewVisibility(false);
								mTopRl.setVisibility(View.VISIBLE);
								break;
							case Failure:
								mProgressRefresh.setWaitVisibility(false);
								mProgressRefresh.setRefreshVisibility(true);
								mTopRl.setVisibility(View.GONE);
								break;
							default:
								break;
						}
					}
				});
	}

	@Override
	public void installListeners() {
		
		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				addRecommendFragment();
			}
		});
		
		mTitleBar.setTitleOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTitlePupop(v);
			}
		});
		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPupop(v);
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

	public void forceTop() {
		if (mAdapter != null) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefreshExtend) {
				((IForceListenRefreshExtend) f).forceTop();
			}

		}
	}

	/**
	 * 
	 * @param v
	 */
	protected void showPupop(View v) {
		
		if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.popupwindow_circle_publish, null);
            popDuanping = (TextView) view.findViewById(R.id.poppupwindow_circle_publish_duanping);
            popChangwen = (TextView) view.findViewById(R.id.poppupwindow_circle_publish_changwen);
            int popWidth = getContext().getResources().getDimensionPixelSize(R.dimen.dynamic_publish_pop_width);
            popupWindow = new PopupWindow(view, popWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        }

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight =  view.getMeasuredHeight();

        popupWindow.showAsDropDown(v, location[0], 0);
        popDuanping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	mType = CirclePublishType.DUANPING;
            	handleClick(getContext(), mType);
                popupWindow.dismiss();
            }
        });

        popChangwen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	mType = CirclePublishType.CHANGWEN;
            	handleClick(getContext(), mType);
                popupWindow.dismiss();
            }
        });
	}
	
	/**
	 * 
	 * @param v
	 */
	protected void showTitlePupop(View v) {
		int popWidth = getContext().getResources().getDimensionPixelSize(R.dimen.circle_title_pop_width);;
		if (popupWindowTitle == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewTitle = layoutInflater.inflate(R.layout.popupwindow_circle_title, null);
            popTitleAll = (TextView) viewTitle.findViewById(R.id.popupwindow_circle_title_all);
            popupWindowTitle = new PopupWindow(viewTitle, popWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
		
		popupWindowTitle.setFocusable(true);
		popupWindowTitle.setOutsideTouchable(true);
		popupWindowTitle.setBackgroundDrawable(new BitmapDrawable());
		popupWindowTitle.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mTitleBar.getTitle().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nav_arrow_down, 0);
			}
		});
		
        int[] location = new int[2];
        mTitleBar.getTitle().getLocationOnScreen(location);

        viewTitle.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight =  viewTitle.getMeasuredHeight();

        popupWindowTitle.showAsDropDown(v, -(popWidth-v.getWidth())/2, 0);
        
        
        for(int i = 0; i < popTitleTvList.size(); i++){
        	if(mCurIndex == i){
        		popTitleTvList.get(i).setTextColor(getResources().getColor(R.color.article_cmt_name));
        	}else{
        		popTitleTvList.get(i).setTextColor(getResources().getColor(R.color.black));
        	}
        }
                
        popTitleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	mTitleBar.setTitle("圈子·全部");
            	mCurIndex = 0;
            	mPager.setCurrentItem(0, false);
            	popupWindowTitle.dismiss();
            }
        });
        
        mTitleBar.getTitle().setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nav_arrow_up, 0);
        
	}

	protected void handleClick(Activity activity,CirclePublishType type) {
		if(LoginManager.getInst().checkLoginWithLoginDialog((FragmentActivity)activity)){
			DiscussActivity.launch(activity, type);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		mContext = activity;
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (SettingManager.getInst().getNoPicModel() != SPHelper.getInst()
				.getInt(SPHelper.KEY_CIRCLE_NOPIC_MODEL)) {
			if (null != mAdapter) {
				mAdapter.notifyDataSetChanged();
			}
			SPHelper.getInst().saveInt(
					SPHelper.KEY_CIRCLE_NOPIC_MODEL, SettingManager.getInst().getNoPicModel());
		}
		
		if (App.getInst().isLogin() != SPHelper.getInst()
				.getBoolean(SPHelper.KEY_CIRCLE_LOGIN_STATE)) {
			handleCircleBg();
			SPHelper.getInst().saveBoolean(
					SPHelper.KEY_CIRCLE_LOGIN_STATE, App.getInst().isLogin());
		}
		
		refreshAllAvatar();
	}

	private void handleCircleBg(){
		refreshAllBg();
	}
	
	@Subscribe
    public void onEventMainThread(CircleBgChangeEvent event) {
		handleCircleBg();
	}

	/**
	 * 登录之后强制刷新
	 */
	public void refreshCircleLoginChange() {
		if (SPHelper.getInst().getBoolean(
				SPHelper.KEY_LAST_LOGIN_STATE_CIRCLE) == App
				.getInst().isLogin()) {
			return;
		}
		refreshCircle();
	}
	
	/**
	 * 刷新首页
	 */
	public void refreshCircle(){
		if (mAdapter != null) {
			mIndicator.setCurrentItem(0);
			mTitleBar.setTitle("圈子·全部");
			for (Fragment f : mAdapter.getFragmentsList()) {
				if (null == f) {
					break;
				} else {
					if (f instanceof IForceListenRefresh) {
						((IForceListenRefresh) f).forceRefresh();
					}
				}
			}

		}
	}
	
	/**
	 * 刷新当前频道
	 */
	public void refreshCurChannel() {
		if (mAdapter != null) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefresh && 
					!(((IForceListenRefresh)f).getRefreshState() == RefreshState.Refreshing)) {
				setRefreshState(((IForceListenRefresh)f).getRefreshState());
				((IForceListenRefresh) f).forceRefresh();
				mTopBtn.setVisibility(View.GONE);
			}

		}
	}
	
	public void refreshAllChannel() {
		if (mAdapter != null) {
			int size = mAdapter.getCount();
			for(int i = 0; i < size; i++){
				Fragment f = mAdapter.getItem(i);
				if (f instanceof IForceListenRefresh) {
					((IForceListenRefresh) f).forceRefresh();
				}
			}
		}
	}
	
	public void refreshAllBg(){
		if (mAdapter != null) {
			int size = mAdapter.getCount();
			for(int i = 0; i < size; i++){
				Fragment f = mAdapter.getItem(i);
				if(f instanceof CircleHomeChannelFragment){
					CircleChannelItemRecommendAdapter adapter = ((CircleHomeChannelFragment) f).getCircleAdapter();
					if(adapter != null){
						ImageLoader.getInstance().displayImage(App.getInst().isLogin() ? 
								App.getInst().getUserInfoBean().getProfile_image() : "drawable://" 
										+ R.drawable.home_circle_head_bg, adapter.getCircleBg());
					}
				}
				if(f instanceof CircleChannelFragment){
					CircleChannelItemRecommendAdapter adapter = ((CircleChannelFragment) f).getCircleAdapter();
					if(adapter != null){
						ImageLoader.getInstance().displayImage(App.getInst().isLogin() ? 
								App.getInst().getUserInfoBean().getProfile_image() : "drawable://" 
								+ R.drawable.home_circle_head_bg, adapter.getCircleBg());
					}
				}
			}
		}
	}
	
	public void refreshAllAvatar(){
		if (mAdapter != null) {
			int size = mAdapter.getCount();
			for(int i = 0; i < size; i++){
				Fragment f = mAdapter.getItem(i);
				if(f instanceof CircleHomeChannelFragment){
					CircleChannelItemRecommendAdapter adapter = ((CircleHomeChannelFragment) f).getCircleAdapter();
					if(adapter != null){
						ImageLoader.getInstance().displayImage(App.getInst().isLogin() ? 
								App.getInst().getUserInfoBean().getFace() : "drawable://" 
										+ R.drawable.home_circle_default_avatar, adapter.getCircleHead());
					}
				}
				if(f instanceof CircleChannelFragment){
					CircleChannelItemRecommendAdapter adapter = ((CircleChannelFragment) f).getCircleAdapter();
					if(adapter != null){
						ImageLoader.getInstance().displayImage(App.getInst().isLogin() ?
								App.getInst().getUserInfoBean().getFace() : "drawable://" 
								+ R.drawable.home_circle_default_avatar, adapter.getCircleHead());
					}
				}
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		SPHelper.getInst().saveBoolean(
				SPHelper.KEY_LAST_LOGIN_STATE_CIRCLE,
				App.getInst().isLogin());
	}

	private void initChannel() {
		List<CircleChanBean> list = CircleChannelListDA.getInst(mContext)
				.queryAll();
		if (Common.isListEmpty(list)) {
			addRecommendFragment();
		} else {
			addFragments(list);
		}
	}

	private void handleHttpFailure(Throwable throwable) {
		Utils.handleHttpFailure(mContext, throwable);
	}
	
	private void addRecommendFragment() {
		ArrayList<String> mPageTitles = new ArrayList<String>();
		ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

		mPageTitles.add("全部");
		CircleHomeChannelFragment f1 = new CircleHomeChannelFragment();
		f1.setOnRecommendHttpListener(mOnRecommendHttpListener);
		f1.setmScrollCallback(mScrollCallBack);
		// mRecommendFragment = f1;
		fragmentsList.add(f1);
		if (mFragmentManager == null) {
			mFragmentManager = getChildFragmentManager();
		}
		mAdapter = new SimpleFragmentPagerAdapter(mFragmentManager,
				fragmentsList, mPageTitles);

		mPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(mOnPageChangeListener);
		mIndicator.setCurrentItem(0);
		mIndicator.notifyDataSetChanged();

	}

	private void addFragments(List<CircleChanBean> list) {
		
		ArrayList<String> mPageTitles = new ArrayList<String>();
		ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
		if (!Common.isListEmpty(list)) {
			mPageTitles.clear();
			fragmentsList.clear();
			int channelCount = list.size();
			for (int i = 0; i < channelCount; i++) {
				CircleChanBean bean = list.get(i);
				if (i == 0) {
					mPageTitles.add("全部");
					CircleHomeChannelFragment f1 = new CircleHomeChannelFragment();
					f1.setOnRecommendHttpListener(mOnRecommendHttpListener);
					f1.setmScrollCallback(mScrollCallBack);
					fragmentsList.add(f1);
				} else {
					mPageTitles.add(bean.getName());
					CircleChannelFragment mCircleFragment = CircleChannelFragment.newInstance(
							bean);
					mCircleFragment.setmScrollCallback(mScrollCallBack);
					fragmentsList.add(mCircleFragment);
				}
				
			}
		}
		if (mFragmentManager == null) {
			mFragmentManager = getChildFragmentManager();
		}
		mAdapter = new SimpleFragmentPagerAdapter(mFragmentManager,
				fragmentsList, mPageTitles);

		mPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(mOnPageChangeListener);
		mIndicator.setCurrentItem(0);
		mIndicator.notifyDataSetChanged();
//		mIndicator.setVisibility(View.VISIBLE);
		
		popupWindowTitle = null;
		popTitleTvList.removeAll(popTitleTvList);
		int popWidth = getContext().getResources().getDimensionPixelSize(R.dimen.circle_title_pop_width);;
		if (popupWindowTitle == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewTitle = layoutInflater.inflate(R.layout.popupwindow_circle_title, null);
            viewContent = (LinearLayout) viewTitle.findViewById(R.id.popupwindow_circle_ll);
            popTitleAll = (TextView) viewTitle.findViewById(R.id.popupwindow_circle_title_all);
            popTitleTvList.add(popTitleAll);
            
            int channelCount = list.size();
            for (int i = 1; i < channelCount; i++) {
            	final CircleChanBean bean = list.get(i);
            	View viewItem = layoutInflater.inflate(R.layout.popupwindow_circle_title_item, null);
            	final TextView popTitleItem = (TextView) viewItem.findViewById(R.id.popupwindow_circle_title_item);
            	popTitleItem.setText(bean.getName());
            	popTitleItem.setTag(i);
            	popTitleItem.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
		            	mTitleBar.setTitle("圈子·" + popTitleItem.getText().toString());
		            	mCurIndex = (Integer)popTitleItem.getTag();;
		            	mPager.setCurrentItem(mCurIndex, false);
		            	popupWindowTitle.dismiss();
					}
				});
            	viewContent.addView(viewItem);
            	popTitleTvList.add(popTitleItem);
            }
            popupWindowTitle = new PopupWindow(viewTitle, popWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
		
	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int index) {
			mCurIndex = index;
			if (mAdapter != null) {
				Fragment f = mAdapter.getItem(mCurIndex);
				if(f instanceof BasePageCircleListFragment){
					((BasePageCircleListFragment)f).dealScrollY();
				}else{
					mTopBtn.setVisibility(View.GONE);
				}
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case DATA_SUCCESS:
			ArrayList<CircleChanBean> list = (ArrayList<CircleChanBean>) msg.obj;
			
			if(getActivityInfo().getActivityState() != ActivityState.Killed){
				String allChanStr = SerializeUtil.serialize(list);
				String allChanStrOld = SPHelper.getInst().getString(
						SPHelper.KEY_CIRCLE_CHANNEL_LIST_ALL);
				if (!TextUtils.equals(allChanStr, allChanStrOld)) {
					addFragments(list);
					SPHelper.getInst().saveString(SPHelper.KEY_CIRCLE_CHANNEL_LIST_ALL,
							SerializeUtil.serialize(list));
				}
			}
			mLoadStateManager.setState(LoadState.Success);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
	private RefreshState mRefreshState = RefreshState.RefreshComplete;
	private OnRefreshStateListener mListener;
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
		refreshCurChannel();
	}

	@Override
	public void forceCheckRefresh() {
		if (mAdapter != null) {
			Fragment f = mAdapter.getItem(mCurIndex);
			if (f != null && f instanceof IForceListenRefresh) {
				((IForceListenRefresh) f).forceCheckRefresh();
			}

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

}
