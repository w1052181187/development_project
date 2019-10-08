package com.chengning.fenghuovideo.base;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.PageHelper;
import com.chengning.fenghuovideo.LoadStateManager;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.widget.ProgressRefreshView;
import com.chengning.fenghuovideo.widget.PullToRefreshListView_FootLoad;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.chengning.fenghuovideo.LoadStateManager.LoadState;

public abstract class BaseNetListFragment<T> extends BaseFragment {

public static final int DATA_SUCCESS = 10002;
	
	private TitleBar mTitleBar;
	private ProgressRefreshView mProgressRefresh;
	private PullToRefreshListView_FootLoad mPullListView;
	private LoadStateManager mLoadStateManager;
	private BaseAdapter mAdapter;

	private List mCommentList;
	
	private PageHelper mPage;
	
	private View mView;
	
	public abstract BaseFragmentActivity buildContext();
	public abstract String buildUrl();
	public abstract BaseAdapter buildAdapter(FragmentActivity activity, List list);
	public abstract String buildMaxId(List list);
	public abstract String configTitle();
	public abstract String configNoData();
	public abstract View configContentView();
	public abstract void initExtraView();
	public abstract void initExtraListener();
	public abstract void handleItemClick(AdapterView<?> parent, View view, int position, long id);
	public abstract BaseListBean<T> handleHttpSuccess(Gson gson, String data);

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mProgressRefresh = new ProgressRefreshView(getActivity(), true);
		mPullListView = (PullToRefreshListView_FootLoad) getView().findViewById(R.id.list);
		initExtraView();

		mTitleBar.showDefaultBack();
		mProgressRefresh.setNoDataTvText(configNoData());
	}

	@Override
	public void initDatas() {
		
		mPage = new PageHelper();
		
		mTitleBar.setTitle(configTitle());
		
		mCommentList = new ArrayList<T>();
		
		initLoadState();
		
		mAdapter = buildAdapter(getActivity(), mCommentList);;
		mPullListView.setAdapter(mAdapter);
		
		if (!Common.hasNet()) {
         	Common.showHttpFailureToast(getActivity());
         	mLoadStateManager.setState(LoadState.Failure);
		} else {
			getDataByHttp(true);
		}
		
	}

	/**
	 * 初始化加载状态
	 */
	private void initLoadState() {
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mProgressRefresh.setWaitVisibility(true);
					mProgressRefresh.setRefreshVisibility(false);
					mProgressRefresh.setNoDataTvVisibility(false);
					mPullListView.setVisibility(View.GONE);
					break;
				case Success:
					mProgressRefresh.setRootViewVisibility(false);
					mPullListView.setVisibility(View.VISIBLE);
					break;
				case NoData:
					mProgressRefresh.setWaitVisibility(false);
					mProgressRefresh.setRefreshVisibility(false);
					mProgressRefresh.setNoDataTvVisibility(true);
					mPullListView.setVisibility(View.GONE);
					break;
				case Failure:
					mProgressRefresh.setWaitVisibility(false);
					mProgressRefresh.setNoDataTvVisibility(false);
					mProgressRefresh.setRefreshVisibility(true);
					mPullListView.setVisibility(View.GONE);
					break;
				}
			}
		});
		mLoadStateManager.setState(LoadState.Init);
	}

	@Override
	public void installListeners() {
		
		mProgressRefresh.setRefreshOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getDataByHttp(true);
			}
		});
		
		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				handleItemClick(parent, view, position, id);
			}
		});
		
		mPullListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true)); 
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>(){
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				getDataByHttp(true);
			}
			
		}); 
		mPullListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener(){

			@Override
			public void onLastItemVisible() {
				if(mPage.hasNextPage()){
					mPullListView.setFootLoading();
					getDataByHttp();
				}
			}
			
		}); 
		
		initExtraListener();
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case DATA_SUCCESS:
			
			mCommentList.clear();
			mCommentList.addAll(mPage.getDataList());
			if(!Common.isListEmpty(mCommentList)){
				mPage.setMaxid(buildMaxId(mCommentList));
				mLoadStateManager.setState(LoadState.Success);
			}else{
				mLoadStateManager.setState(LoadState.NoData);
			}
			mAdapter.notifyDataSetChanged();
			handleRefreshSuccess();
			break; 
		default:
			break;
		}
	}
	
	private void getDataByHttp(){
		getDataByHttp(false);
	}
	
	private void getDataByHttp(final boolean isFirstPage){
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				getDataByHttp(false, isFirstPage);
			}
		}, Common.TIME_WAIT_COMPLETE);
	}
	
	@Deprecated
	private void getDataByHttp(boolean isIgnoreRequestRunning, final boolean isFirstPage){
		String url = buildUrl();
		url = isFirstPage ? mPage.appendFirstPage(url) : mPage.appendNextPageAndMaxid(url);
		if(!isIgnoreRequestRunning && mPage.isRequestRunning()){
			if(!mPage.equalsRunningUrl(url.toString())){
				if(isFirstPage){
					mPullListView.onRefreshComplete();
				}else{
					mPullListView.setFootLoadFull();
				}
			}
			return;
		}
		mPage.setRequestStart(url.toString());
		
		HttpUtil.get(url.toString(), null, new MyJsonHttpResponseHandler() {
			
			@Override
	        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getActivity(), throwable);
				handleRefreshSuccess();
				if(mPage.isCurrentPageFirst()){
					mLoadStateManager.setState(LoadState.Failure);
				}
	        };
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				Gson gson = new Gson();
				BaseListBean<T> bean = handleHttpSuccess(gson, data);
				if(isFirstPage){
					mPage.clear();
				}
				int currentPage = (null == bean ? 1 : mPage.getCurrentPage() + 1);
				mPage.setCurrentPage(currentPage);
				mPage.setPageData(currentPage, (null == bean ? null : bean.getList()));
				mPage.setMaxPage((null == bean ? 0 :bean.getTotal_page()));
				getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();
				
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
				handleRefreshSuccess();
			}
		}); 
	}
	
	protected void handleRefreshSuccess() {
		mPullListView.setFootLoadFull();
		mPullListView.onRefreshComplete();
		mPage.setRequestEnd();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = configContentView();
		return mView;
	}

}
