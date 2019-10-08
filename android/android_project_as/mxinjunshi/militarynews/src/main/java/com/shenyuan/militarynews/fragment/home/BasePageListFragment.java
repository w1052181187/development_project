package com.shenyuan.militarynews.fragment.home;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.Clearable;
import com.chengning.common.util.HttpUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.shenyuan.militarynews.LoadStateManager;
import com.shenyuan.militarynews.LoadStateManager.LoadState;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.base.IAdapterData;
import com.shenyuan.militarynews.base.IForceListenRefreshExtend;
import com.shenyuan.militarynews.base.IScrollCallBack;
import com.shenyuan.militarynews.base.ReturnTopOnScrollListener;
import com.shenyuan.militarynews.beans.data.MChannelBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.ChannelItemListServer;
import com.shenyuan.militarynews.interf.IFragmentLazyLoadListener;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.views.PullToRefreshListView_FootLoad;
public abstract class BasePageListFragment<T> extends Fragment implements IForceListenRefreshExtend, IFragmentLazyLoadListener {

	private static final String TAG = BasePageListFragment.class.getSimpleName();
	
	public static final int INIT_NEWSDATA_UI = 10001;
	public static final int ADD_MORE_DATA_UI = 10002;
	public static final int INIT_WAIT_TIME = 500;
	
	private View mView;
	private ImageView mTouchRefresh;
	private ProgressBar mWait; 
	private PullToRefreshListView_FootLoad mPullListView;  

	private List<T> mDataList;
	private BaseAdapter mAdapter;
	private Activity mContext;
	private ChannelItemListServer mListServer;
	private LoadStateManager mLoadStateManager;
	
	private String mUrlWorkingUrl = null;
	private HashSet<String> mUrlWorking = new HashSet<String>();

	private IScrollCallBack mScrollCallback = null;
	
	private int mCurrentPage;
	private int mTarPage;
	private int mCachePage;
	
	private long mLastRequestTime;
	
	private boolean isDataFromDB = false;
	private boolean isVisibleToUserInViewPager = true;
	
	private RefreshState mRefreshState = RefreshState.RefreshComplete;;
	private OnRefreshStateListener mOnRefreshStateListener;
	
	private MChannelBean mMChannelBean;
	
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			processHandlerMessage(msg);
		}
	};
	private Runnable LOAD_DATA = new Runnable(){
		@Override
		public void run() {
			getDataOnInit();
		}
	};

	private String mCurVersion;

	private ReturnTopOnScrollListener mScrollListener;
	private boolean isPrepared = false;
	private boolean mHasLoadedOnce =false;

	public abstract BaseAdapter buildAdapter(Activity activity, List<T> list);
	public abstract String buildTAG();
	public abstract String buildChannel();
	public abstract String buildUrl(int tarPage);
	public abstract void configPullToRefreshListView_FootLoad(PullToRefreshListView v);
	public abstract List<T> onHttpSuccess(Gson gson, String data, MChannelBean channelBean,int tarPage);
	public abstract void onInitNewsDataUI();
	public abstract void onListItemClick(AdapterView<?> parent, View view,
			int position, long id);
	
	public Activity getContext(){
		return mContext;
	}
	
	public List<T> getDataList(){
		return mDataList;
	}
	
	public BaseAdapter getAdapter(){
		return mAdapter;
	}
	
	public View getRootView(){
		return mView;
	}
	
	public Handler getHandler(){
		return mHandler;
	}

	public IScrollCallBack getmScrollCallback() {
		return mScrollCallback;
	}
	public void setmScrollCallback(IScrollCallBack mScrollCallback) {
		this.mScrollCallback = mScrollCallback;
	}
	
	public boolean isEmpty(){
		boolean isEmpty = false;
		isEmpty = mDataList == null || mDataList.size() == 0;
		return isEmpty;
	}
	
	public boolean isVisibleToUserInViewPager(){
		return isVisibleToUserInViewPager;
	}

	@Override
	public void forceRefresh(){
		if(mPullListView != null && !mPullListView.isRefreshing()){
			mPullListView.setRefreshing();
		}
	}

	@Override
	public void forceCheckRefresh(){
		if(mPullListView != null && !mPullListView.isRefreshing() 
				&& Common.hasNet()
				&& mLoadStateManager.getState() == LoadState.Success){
			long oldTime = SPHelper.getInst().getLong(SPHelper.PREFIX_TIME_UPDATE_CHANNEL + buildChannel());
			if(Common.isTargetTimeBefore(oldTime)){
				if(mAdapter.getCount() > 0){
					mPullListView.setRefreshing();
				}else{
					mLoadStateManager.setState(LoadState.Init);
					getListByHttp();
				}
//				mTarPage = JUrl.PAGE_START;
//				List<MChannelItemBean> list = mListServer.queryChannelItemList(buildChannel(), JUrl.PAGE_START);
//				if(!Common.isListEmpty(list)){
//					isDataFromDB = true;
//					onGetNewsByDB(mTarPage);
//					sendListMessage(INIT_NEWSDATA_UI, list);
//					
//					mHandler.postDelayed(new Runnable() {
//						
//						@Override
//						public void run() {
//							mPullListView.setRefreshing();
//						}
//					}, Common.TIME_WAIT_COMPLETE);
//				}else{
//					getListByHttp();
//				}
			}
		}
	}
	
	@Override
	public void forceSetPageSelected(final boolean isSelected){
		if (isSelected && !mHasLoadedOnce) {
			getDataOnInit();
		}
	}
	
	@Override
	public void forceTop(){
		mPullListView.setSelection(0);
	}
	
	private void privateForceSetPageSelected(final boolean isSelected){
		if(isSelected){
			if(!Common.isListEmpty(mDataList) && mAdapter != null && mAdapter instanceof IAdapterData){
				// 恢复时加载下一页数据
				IAdapterData i = (IAdapterData) mAdapter;
				int lastPosition = i.getViewLastPostion();
				if(Math.abs(mDataList.size() - lastPosition) < 8){
					mPullListView.setFootLoading();
					getNextNewsByDB();
				}
			}
		}
	}
	
	public RefreshState getRefreshState(){
		return mRefreshState;
	}
	
	public void setOnRefreshStateListener(OnRefreshStateListener listener){
		this.mOnRefreshStateListener = listener;
	}
	
	private void setRefreshState(RefreshState state){
		this.mRefreshState = state;
		if(mOnRefreshStateListener != null){
			switch (mRefreshState) {
			case Refreshing:
				mOnRefreshStateListener.onStart();
				break;
			case RefreshComplete:
				mOnRefreshStateListener.onFinish();
				break;
			default:
				break;
			}
		}
	}
	
	public void onGetNewsByDB(int page, List list){
		
	}
	
	public void onHttpFailure(){
		
	}
	
	public void onRefreshPull(PullToRefreshBase refreshView) {
		mLoadStateManager.setState(LoadState.Success);
		getListByHttp();
	}
	
	public void showToast(int rosourceId){
		UIHelper.showToast(getContext(), getContext().getString(rosourceId));
	}
	
	public void showToast(String text){
		UIHelper.showToast(getContext(), text);
	}
	
	public void getDataOnInit(){
		mTarPage = JUrl.PAGE_START;
		if(Common.hasNet()){
			mCurVersion = Common.getVersionName(getActivity());
			long oldTime = SPHelper.getInst().getLong(SPHelper.PREFIX_TIME_UPDATE_CHANNEL + buildChannel());
			
			if(Common.isTargetTimeBefore(oldTime) || !SPHelper.getInst().getString(SPHelper.KEY_APP_VERSION)
					.equals(mCurVersion)){
				if (!SPHelper.getInst().getString(SPHelper.KEY_APP_VERSION).equals(mCurVersion)) {
					SPHelper.getInst().saveString(SPHelper.KEY_APP_VERSION, mCurVersion);
				}
				List<MChannelItemBean> list = mListServer.queryChannelItemList(buildChannel(), JUrl.PAGE_START);
				if(!Common.isListEmpty(list)){
					isDataFromDB = true;
					onGetNewsByDB(mTarPage, list);
					sendListMessage(INIT_NEWSDATA_UI, list);
					
					mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							mPullListView.setRefreshing();
						}
					}, Common.TIME_WAIT_COMPLETE);
				}else{
					getListByHttp();
				}
			}else{
				getNewsByDB();
			}
		}else{
			getNewsByDB();
		}
	}
	
	public void getNewsByDB(){
		mTarPage = JUrl.PAGE_START;
		List<MChannelItemBean> list = mListServer.queryChannelItemList(buildChannel(), JUrl.PAGE_START);
		if(!Common.isListEmpty(list)){
			isDataFromDB = true;
			onGetNewsByDB(mTarPage, list);
			
			// viewpager 未缓存界面只自动缓存数据 然后恢复的情况
			if(mCachePage > mCurrentPage){
				while(mCachePage >= mCurrentPage){
					mCurrentPage = mTarPage;
					mTarPage = mCurrentPage + 1;
					List<MChannelItemBean> tempList = mListServer.queryChannelItemList(buildChannel(), mTarPage);
					if(!Common.isListEmpty(tempList)){
						onGetNewsByDB(mTarPage, tempList);
						list.addAll(tempList);
					}else{
						break;
					}
				}
				if(getHandler() != null){
					getHandler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							privateForceSetPageSelected(true);
						}
					}, 100);
				}
			}
			sendListMessage(INIT_NEWSDATA_UI, list);
		}else{
			getListByHttp();
		}
	}
	
	public void getNextNewsByDB(){
		mTarPage = mCurrentPage + 1;
		List<MChannelItemBean> list = mListServer.queryChannelItemList(buildChannel(), mTarPage);
		if(!Common.isListEmpty(list)){
			isDataFromDB = true;
			onGetNewsByDB(mTarPage, list);
			sendListMessage(ADD_MORE_DATA_UI, list);
		}else{
			getListByHttp(mTarPage);
		}
	}
	
	private void getListByHttp()
	{
		mCurrentPage = JUrl.PAGE_START;
		getListByHttp(mCurrentPage);
	}
	
	private void getListByHttp(int page)
	{
		handleHomeEventAnalytics(page);
		mTarPage = page;
		getNewsListByHttp();
	}
	
	/**
	 * 处理首页刷新或者加载更多事件
	 * @param page
	 *
	 */
	public void handleHomeEventAnalytics(int page) {
	}
	
	private void handleHttpFailure(Throwable throwable){
		mPullListView.setFootLoadFull();
		mPullListView.onRefreshComplete();
		setRefreshState(RefreshState.RefreshComplete);
		if (!Common.hasNet()) {
			showToast("加载失败，请检查网络");
			if ((mTarPage > JUrl.PAGE_START) || (mDataList.size() > 0)) {
				mLoadStateManager.setState(LoadState.Success);
			} else {
				mLoadStateManager.setState(LoadState.Failure);
			}
		} else {
			if(throwable!=null&&throwable.getClass().isInstance(new ConnectTimeoutException())){  
       			showToast(R.string.intent_timeout);  
            } else {
           		showToast(R.string.server_fail);
            }  
			if ((mTarPage > JUrl.PAGE_START) || (mDataList.size() > 0)) {
				mLoadStateManager.setState(LoadState.Success);
			} else {
				mLoadStateManager.setState(LoadState.Failure);
			}
		}
	}

	private void getNewsListByHttp()
	{
		final String url = buildUrl(mTarPage);
		if(mUrlWorking.contains(url)){
			return;
		}
		if(!Common.hasNet()){
            handleHttpFailure(null);
            onHttpFailure();
            return;
		}
		// 限制请求时间间隔至少1s
		long time = System.currentTimeMillis();
		if(time - mLastRequestTime < 500){
			return;
		}else{
			mLastRequestTime = time;
		}
		mUrlWorkingUrl = url;
		mUrlWorking.add(url);
		setRefreshState(RefreshState.Refreshing);
		RequestParams params = new RequestParams();
		params.put("devicetoken", AVInstallation
				.getCurrentInstallation()
				.getInstallationId());
		HttpUtil.post(url, params, new MyStatusResponseHandler() {
			
			@Override
	        public void onFailure(int statusCode, Header[] headers,Throwable throwable,JSONObject errorResponse) { 
	            Log.e(TAG, "HttpUtil onFailure");   
	            
	            handleHttpFailure(throwable);
	            onHttpFailure();
	            mUrlWorking.remove(url);
	        };

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
	            
	            Gson gson = new Gson();
	            MChannelBean channelBean = null;
	            try {
	            	channelBean = gson.fromJson(data, MChannelBean.class);
				} catch (Exception e) {
					e.printStackTrace();
					channelBean = null;
				}
	            if(channelBean != null){
	            	isDataFromDB = false;
	            	mMChannelBean = channelBean;
		            List list = onHttpSuccess(gson, data, channelBean, mTarPage);
		            	
		            if(mTarPage > JUrl.PAGE_START){
		            	mListServer.setChannelAndPage(list, buildChannel(), mTarPage);
		            	mListServer.insertChannelItemList(list, buildChannel(), mTarPage);
		            	
		            	sendListMessage(ADD_MORE_DATA_UI, list);
		            }else{
		            	mListServer.setChannelAndPage(list, buildChannel(), mTarPage);
		            	mListServer.clearChannel(buildChannel());
		            	mListServer.insertChannelItemList(list, buildChannel(), mTarPage);
		            	
		            	sendListMessage(INIT_NEWSDATA_UI, list);
		            }

		    		setRefreshState(RefreshState.RefreshComplete);
	            }else{
		            handleHttpFailure(null);
		            onHttpFailure();
		            mUrlWorking.remove(url);
	            }

			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				if(status == -2){
					// (yangyang)栏目文章列表 GET提交 status: -2  (message) 当前页没有数据！
//					if(isVisibleToUserInViewPager()){
//						UIHelper.showToast(getContext(), message);
//					}
					UIHelper.showToast(getContext(), message);
					
					mPullListView.setFootLoadFull();
					mPullListView.onRefreshComplete();
					setRefreshState(RefreshState.RefreshComplete);
					mLoadStateManager.setState(LoadState.Success);
		            onHttpFailure();
				}else{
		            handleHttpFailure(null);
		            onHttpFailure();
				}
	            mUrlWorking.remove(url);
			}; 
	    }); 
	}
	
	public void sendListMessage(int what, List list){
		sendListMessage(what, list, 0);
	}
	
	public void sendListMessage(int what, List list, int arg2){
		int listSize = 0;
		if(list != null){
			listSize = list.size();
		}
		if(getContext() != null){
			Message message = getHandler().obtainMessage(what, list);
			message.arg1 = listSize;
			message.arg2 = arg2;
		    message.sendToTarget();  
		}
	}
	
	public void processHandlerMessage(Message msg) {
		switch (msg.what) 
		{
		case INIT_NEWSDATA_UI:
			List list = (List) msg.obj;
			int listSize = msg.arg1;
			if(!Common.isListEmpty(list)){
				mDataList.clear();
				mDataList.addAll(list);
				if(mAdapter instanceof Clearable){
					((Clearable)mAdapter).clear();
				}
				mAdapter.notifyDataSetChanged();
			}else{
				mDataList.clear();
				if(mAdapter instanceof Clearable){
					((Clearable)mAdapter).clear();
				}
				mAdapter.notifyDataSetChanged();
			}
			mCurrentPage = mTarPage;
			mLoadStateManager.setState(LoadState.Success);
			
			mPullListView.setFootLoadFull();
			mPullListView.onRefreshComplete();
			
			onInitNewsDataUI();
			
            mUrlWorking.remove(mUrlWorkingUrl);
            mHasLoadedOnce = true;
			break;
		case ADD_MORE_DATA_UI:
			List nextlist = (List) msg.obj;
			int nextListSize = msg.arg1;
			if(!Common.isListEmpty(nextlist)){
				mDataList.addAll(nextlist);
				mAdapter.notifyDataSetChanged();
			}
			mCurrentPage = mTarPage;
			mLoadStateManager.setState(LoadState.Success);
			
			mPullListView.setFootLoadFull();
			mPullListView.onRefreshComplete();
			
            mUrlWorking.remove(mUrlWorkingUrl);
			break; 
		default:
			break;
		}
	}
	
	public void initFragment() {
		initViews();
		installListeners();
		initDatas();
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		if(savedInstanceState != null){
			mCachePage = savedInstanceState.getInt("cache_page");
		}
        super.onCreate(savedInstanceState); 
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);  
		initFragment(); 
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Common.setTheme(getContext());
		mView = inflater.inflate(R.layout.fragment_base_list, container, false);
	    return mView ;
	}
	
	@Override  
	public void setUserVisibleHint(boolean isVisibleToUser) {  
		super.setUserVisibleHint(isVisibleToUser);  
		isVisibleToUserInViewPager = isVisibleToUser;
		if(!isVisibleToUser){
			mHandler.removeCallbacks(LOAD_DATA);
		}
	}

	@Override
	public void lazyLoad() {
		if (!isPrepared || !getUserVisibleHint()) {
			return;
		}
		getDataOnInit();
	}

	protected void initViews() {
		mContext = this.getContext();

		mPullListView = (PullToRefreshListView_FootLoad) mView.findViewById(R.id.list);
		configPullToRefreshListView_FootLoad(mPullListView);
		
		mTouchRefresh = (ImageView)mView.findViewById(R.id.touchRefrush); 
		mWait = (ProgressBar)mView.findViewById(R.id.wait);
	} 
	 
	protected void initDatas() {
		
		mCurrentPage = JUrl.PAGE_START;
		mDataList = new ArrayList<T>();
		mListServer = ChannelItemListServer.getInst(mContext);
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mWait.setVisibility(View.VISIBLE);
					mTouchRefresh.setVisibility(View.GONE);
					mPullListView.setVisibility(View.GONE);
					break;
				case Success:
					mWait.setVisibility(View.GONE);
					mTouchRefresh.setVisibility(View.GONE);
					mPullListView.setVisibility(View.VISIBLE);
					break;
				case Failure:
					mWait.setVisibility(View.GONE);
					mTouchRefresh.setVisibility(View.VISIBLE);
					mPullListView.setVisibility(View.GONE);
					break;
				}
			}
		});

		mAdapter = buildAdapter(mContext, mDataList);
		mPullListView.setAdapter(mAdapter);

		mLoadStateManager.setState(LoadState.Init);

		isPrepared = true;
		mHasLoadedOnce = false;
		lazyLoad();
	}

	protected void installListeners() {
		mTouchRefresh.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
//				getListByHttp();
				getDataOnInit();
			}
		});   
		
		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onListItemClick(parent, view, position, id);
			}
		});
		//加入滑动距离的判断..
		mScrollListener = new ReturnTopOnScrollListener(mScrollCallback, mPullListView);
		mPullListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true, mScrollListener)); 
		mPullListView.setOnRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh(final PullToRefreshBase refreshView) {
				getHandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						onRefreshPull(refreshView);
					}
				}, 20);
			}
			
		}); 
		mPullListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener(){

			@Override
			public void onLastItemVisible() {
				onLoadMore();
			}
			
		}); 
	}
	
	public void dealScrollY(){
		if (mScrollListener == null) {
			return;
		}
		mScrollListener.dealScrollY();
	}
	
	/**
	 * 加载更多
	 */
	protected void onLoadMore() {
		if(SPHelper.getInst().getInt(SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + buildChannel()) > mCurrentPage){
			if(isDataFromDB){
				mPullListView.setFootLoading();
				getNextNewsByDB();
			}else{
				if(Common.hasNet()){
					mPullListView.setFootLoading();
					mTarPage = mCurrentPage + 1;
					getListByHttp(mTarPage);
				}else{
					handleHttpFailure(null);
					onHttpFailure();
				}
			}
		}else{
			mPullListView.setFootLoadFull();
		}
	}
	@Override
	public void onAttach(Activity activity){
		mContext = activity;
		super.onAttach(activity);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override 
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		outState.putInt("cache_page", mCurrentPage);
		super.onSaveInstanceState(outState);
	}	

}
