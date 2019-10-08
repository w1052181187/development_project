package com.chengning.fenghuo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.IForceListenRefresh;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.PageHelper;
import com.chengning.common.util.ThreadHelper;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts.ArticleType;
import com.chengning.fenghuo.LoadStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.CircleChannelItemAdapter;
import com.chengning.fenghuo.base.IForceListenRefreshExtend;
import com.chengning.fenghuo.base.IScrollCallBack;
import com.chengning.fenghuo.base.ReturnTopOnScrollListener;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.UserInfoDiscussListBean;
import com.chengning.fenghuo.event.CollectEditStateEvent;
import com.chengning.fenghuo.event.CollectEvent;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.ProgressRefreshView;
import com.chengning.fenghuo.widget.PullToRefreshListView_FootLoad;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MyFavoriteDiscussFragment extends AppUiFragment {

	private static final String TAG = UserDynamicFragment.class.getSimpleName();
	public static boolean isForeground = false;
	private static final int DATA_SUCCESS_HTTP = 1;
	private static final int DELETE_SUCCESS = 2;
	private static final int DELETE_ONE = 3;
	private static final int REQ_CODE_ARTICLE_DISCUSS_DETAIL = 1002;
	private static final int DATA_SUCCESS_LOCAL = 4;

	private int pos;
	private PullToRefreshListView_FootLoad mPullListView;
	private List<DynamicItemBean> mDataList;
	private CircleChannelItemAdapter mAdapter;

	private TextView mCancelFavBtn;
	private RelativeLayout mBottomEdit;
	private View mView;
	private CheckBox mDeleteAllCbx;

	private String mUid;
	private ProgressRefreshView mProgressRefresh;
	private LoadStateManager mLoadStateManager;

	private PageHelper mPage;
	private boolean mIsInited;
	private ArrayList<String> idList;
    private HandlerThread mFavThread;
	private int mOffset = 0;
	private boolean hasNextPage;
	private CollectEditStateEvent editStateEvent;
	private boolean isPrepared;
	private IScrollCallBack mScrollCallback = null;
	private ReturnTopOnScrollListener mScrollListener;

	public static MyFavoriteDiscussFragment newInstance(String uid, boolean isInit) {
		MyFavoriteDiscussFragment fragment = new MyFavoriteDiscussFragment();
		Bundle bundle = new Bundle();
		bundle.putString("uid", uid);
		bundle.putBoolean("isInit", isInit);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mView = inflater.inflate(R.layout.fragment_my_favorite_discuss, container,
				false);
		isPrepared = true;
		return mView;
	}

	@Override
	public void initViews() {
		
		mBottomEdit = (RelativeLayout) mView
				.findViewById(R.id.circle_list_bottom_toolbar);
		mCancelFavBtn = (TextView) mView.findViewById(R.id.circle_list_cancle);
		mDeleteAllCbx = (CheckBox) mView
				.findViewById(R.id.circle_list_all_check);
		mPullListView = (PullToRefreshListView_FootLoad) mView
				.findViewById(R.id.list);
		mProgressRefresh = new ProgressRefreshView(getContext(), mView);

	}

	@Override
	public void initDatas() {
		mPage = new PageHelper();
		mUid = getArguments().getString("uid");
		mIsInited = getArguments().getBoolean("isInit");
		idList = new ArrayList<String>();
		initLoadState();
		showEmpty();

		EventBus.getDefault().register(this);
	}

	/**
	 * 初始化加载状态
	 */
	private void initLoadState() {
		editStateEvent = new CollectEditStateEvent();
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager
				.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {

					@Override
					public void OnStateChange(LoadState state, Object obj) {
						switch (state) {
							case Init:
								mProgressRefresh.setWaitVisibility(true);
								mProgressRefresh.setRefreshVisibility(false);
								mProgressRefresh.setNoDataTvVisibility(false);
								mPullListView.setVisibility(View.GONE);
								editStateEvent.setHasData(false);
								editStateEvent.setType(ArticleType.DYNAMIC);
								EventBus.getDefault().post(editStateEvent);
								break;
							case Success:
								mProgressRefresh.setRootViewVisibility(false);
								mPullListView.setVisibility(View.VISIBLE);
								editStateEvent.setHasData(true);
								editStateEvent.setType(ArticleType.DYNAMIC);
								EventBus.getDefault().post(editStateEvent);
								break;
							case NoData:
								mProgressRefresh.setWaitVisibility(false);
								mProgressRefresh.setRefreshVisibility(false);
								mProgressRefresh.setNoDataTvVisibility(true);
								mPullListView.setVisibility(View.GONE);
								editStateEvent.setHasData(false);
								editStateEvent.setType(ArticleType.DYNAMIC);
								EventBus.getDefault().post(editStateEvent);
								break;
							case Failure:
								mProgressRefresh.setWaitVisibility(false);
								mProgressRefresh.setRefreshVisibility(true);
								mProgressRefresh.setNoDataTvVisibility(false);
								mPullListView.setVisibility(View.GONE);
								break;
						}
					}
				});
		mLoadStateManager.setState(LoadState.Init);
		mProgressRefresh.setNoDataTvText("您还没有收藏讨论");
	}

	@Override
	public void installListeners() {
		mCancelFavBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAdapter != null) {
					if (App.getInst().isLogin()) {
						deleteFavoriteDiscussByHttp(getContext(),
								((CircleChannelItemAdapter) mAdapter)
										.getIsSelected());
					} else {
						deleteFavoriteDiscussByDB(((CircleChannelItemAdapter) mAdapter)
								.getIsSelected());
					}
				}

			}
		});
		mDeleteAllCbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				if (mAdapter != null) {
					if (isChecked) {
						((CircleChannelItemAdapter) mAdapter)
								.setAllSelected(true);
						mAdapter.notifyDataSetChanged();
					} else {
						((CircleChannelItemAdapter) mAdapter)
								.setAllSelected(false);
						mAdapter.notifyDataSetChanged();
					}

				}
			}
		});
		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getMyFavorite(true);
			}
		});

		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
//				pos = position - 1;
//				DynamicDetailActivity.launchByReqCode(getContext(),
//						mDataList.get(position - 1),
//						ArticleType.DYNAMIC);
			}
		});

		//加入滑动距离的判断..
		mScrollListener = new ReturnTopOnScrollListener(getmScrollCallback(), mPullListView);
		mPullListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true, mScrollListener));
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (!App.getInst().isLogin()) {
					mOffset = 0;
				}
				getMyFavorite(true);
			}

		});
		
		mPullListView.setOnLastPageVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				boolean isNextPage = false;
				isNextPage = App.getInst().isLogin() ? mPage.hasNextPage() : hasNextPage;
				if (isNextPage) {
					mPullListView.setFootLoading();
					getMyFavorite(false);
				}
			}

		});
	}

	private void getDataByHttp(final boolean isFirstPage) {
		getHandler().postDelayed(new Runnable() {

			@Override
			public void run() {
				getMyFavoriteByHttp(false, isFirstPage);
			}
		}, Common.TIME_WAIT_COMPLETE);
	}

	private void getMyFavoriteByHttp(boolean isIgnoreRequestRunning,
			final boolean isFirstPage) {
		String url = JUrl.SITE + JUrl.URL_GET_MYFAVORITE_DISCUSS;
		url = JUrl.appendUid(url, mUid);
		url = isFirstPage ? mPage.appendFirstPage(url) : mPage
				.appendNextPageAndMaxid(url);
		if (!isIgnoreRequestRunning && mPage.isRequestRunning()) {
			if (!mPage.equalsRunningUrl(url.toString())) {
				if (isFirstPage) {
					mPullListView.onRefreshComplete();
				} else {
					mPullListView.setFootLoadFull();
				}
			}
			return;
		}
		mPage.setRequestStart(url.toString());

		HttpUtil.get(url.toString(), null, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getContext(), throwable);
				handleRefreshSuccess();
				if (mPage.isCurrentPageFirst()) {
					mLoadStateManager.setState(LoadState.Failure);
				}
			}

			;

			@Override
			public void onDataSuccess(int status, String mod, String message,
									  String data, JSONObject obj) {
				Gson gson = new Gson();
				UserInfoDiscussListBean bean = gson.fromJson(data,
						UserInfoDiscussListBean.class);
				if (isFirstPage) {
					mPage.clear();
				}
				int currentPage = (null == bean ? 1
						: mPage.getCurrentPage() + 1);
				mPage.setCurrentPage(currentPage);
				mPage.setPageData(currentPage,
						(null == bean ? null : bean.getList()));
				mPage.setMaxPage((null == bean ? 0 : bean.getTotal_page()));
				getHandler().obtainMessage(DATA_SUCCESS_HTTP).sendToTarget();

			}

			@Override
			public void onDataFailure(int status, String mod, String message,
									  String data, JSONObject obj) {
				UIHelper.showToast(getContext(), message);
				handleRefreshSuccess();
			}
		});
	}

	protected void handleRefreshSuccess() {
		mPullListView.setFootLoadFull();
		mPullListView.onRefreshComplete();
		notifyAppUiFragmentOnUpdateFinish();
		mPage.setRequestEnd();
	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
    public void onDestroy() {
        ThreadHelper.destory(mFavThread);
        getHandler().removeMessages( DATA_SUCCESS_HTTP );
        getHandler().removeMessages( DELETE_SUCCESS );
        getHandler().removeMessages( DELETE_ONE );
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

	public boolean isEmpty() {
		boolean isEmpty = false;
		isEmpty = mDataList == null || mDataList.size() == 0;
		return isEmpty;
	}

	public void sendListMessage(int what, List<ChannelItemBean> list) {
		int listSize = 0;
		if (list != null) {
			listSize = list.size();
		}
		if (getContext() != null) {
			Message message = getHandler().obtainMessage(what, list);
			message.arg1 = listSize;
			message.sendToTarget();
		}
	}

	private void showEmpty() {
		mDataList = new ArrayList<DynamicItemBean>();
		mAdapter = new CircleChannelItemAdapter(getContext(), mDataList, false,
				false);
		mPullListView.setAdapter(mAdapter);
		if (!mIsInited) {
			getMyFavorite(true);
		}

	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
			case DATA_SUCCESS_LOCAL:
				// TODO 获取本地收藏数据成功
				ArrayList beans = (ArrayList) msg.obj;

				if (0 == mOffset) {
					mDataList.clear();
				}
				int size = beans.size();
				if (size < 20) {
					hasNextPage = false;
				} else {
					hasNextPage = true;
				}
				mDataList.addAll(beans);
				mOffset = Common.calculateOffset(mDataList);
				mAdapter.notifyDataSetChanged();
				handleRefreshSuccess();

				mLoadStateManager.setState(Common.isListEmpty(mDataList) ? LoadState.NoData : LoadState.Success);
				break;

		case DATA_SUCCESS_HTTP:

			mDataList.clear();
			mDataList.addAll(mPage.getDataList());
			if (!Common.isListEmpty(mDataList)) {
				mPage.setMaxid(mDataList.get(0).getId());
				mLoadStateManager.setState(LoadState.Success);
			} else {
				mLoadStateManager.setState(LoadState.NoData);
			}
			mAdapter.notifyDataSetChanged();
			handleRefreshSuccess();
			break;
		case DELETE_SUCCESS:
			mBottomEdit.setVisibility(View.GONE);
			editStateEvent.setEditFlag(false);
			editStateEvent.setType(ArticleType.DYNAMIC);
			EventBus.getDefault().post(editStateEvent);
			((CircleChannelItemAdapter) mAdapter).clearList();
			((CircleChannelItemAdapter) mAdapter).setDelFavFlag(false);
			if (!Common.isListEmpty(idList)) {
				for (int i = 0; i < idList.size(); i++) {
					for (int j = 0; j < mDataList.size(); j++) {
						if (mDataList.get(j).getTid().equals(idList.get(i))) {
							mDataList.remove(j);
						}
					}
				}
			}
			mAdapter.notifyDataSetChanged();
			// 强制数据刷新处理
			if (App.getInst().isLogin()) {
				mOffset = 0;
			}
			getMyFavorite(true);
			mDeleteAllCbx.setChecked(false);
			break;
		case DELETE_ONE:
			if (!Common.isListEmpty(mDataList)) {
				mDataList.remove(pos);
				mAdapter.notifyDataSetChanged();
				// 强制数据刷新处理
				if (App.getInst().isLogin()) {
					mOffset = 0;
				}
				getMyFavorite(true);
			}
			
			break;
		default:
			break;
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (!isPrepared || !getUserVisibleHint()) {
			return;
		}
		editStateEvent.setHasData(!Common.isListEmpty(mDataList));
		EventBus.getDefault().post(editStateEvent);
	}
	
	@Subscribe
    public void onEventMainThread(CollectEvent event) {
    	switch (event.getArticleType()) {
			case DYNAMIC :
				if (!Common.isTrue(event.getFavState())) {
					Message message = getHandler().obtainMessage(DELETE_ONE);
					message.sendToTarget();
				}
				break;

			default :
				break;
		}
    	
    }

	private void getMyFavorite() {
		getMyFavorite(false);
	}

	private void getMyFavorite(boolean isFirstPage) {
		if (App.getInst().isLogin()) {
			if (!Common.hasNet()) {
				Common.showHttpFailureToast(getContext());
				mLoadStateManager.setState(LoadState.Failure);
			} else {
				getDataByHttp(isFirstPage);
			}
		} else {
			getMyFavoriteByDB();
		}

	}

	/**
	 * 从数据库获取收藏列表
	 *
	 */
	private void getMyFavoriteByDB() {
		if (mFavThread == null) {
			mFavThread = ThreadHelper.creatThread("myFav_discuss");
		}
		ThreadHelper.handle(mFavThread, new Runnable() {

			@Override
			public void run() {
				ArrayList<DynamicItemBean> list = LocalStateDA.getInst(getContext()).getFavDiscuss(mOffset);
				getHandler().obtainMessage(DATA_SUCCESS_LOCAL, list).sendToTarget();
			}
		});

	}

	private void deleteFavoriteDiscussByDB(final SparseBooleanArray sparseBooleanArray) {

		if (mFavThread == null) {
			mFavThread = ThreadHelper.creatThread("myFav_news");
		}
		ThreadHelper.handle(mFavThread,  new Runnable() {
		    
		    @Override
		    public void run()  {
		    	getDeleteIds(sparseBooleanArray);
				LocalStateDA.getInst(getContext()).deleteFavArticleByBatch(
						LocalStateDA.PREFIX_CHANNEL_ITEM, idList);
				Message msg = getHandler().obtainMessage(DELETE_SUCCESS);
				msg.sendToTarget();
		    }
		} );
		
	}

	/**
	 * 删除讨论收藏
	 */
	private void deleteFavoriteDiscussByHttp(final Activity context,
			SparseBooleanArray sparseBooleanArray) {
		getDeleteIds(sparseBooleanArray);
		RequestParams params = new RequestParams();
		String url = "";
		if (!Common.isListEmpty(idList) && idList.size() == 1) {
			params.put("tid", Integer.valueOf(idList.get(0)));
			url = JUrl.SITE + JUrl.URL_DO_DELETE_MYFAVORITE_DISCUSS_SINGLE;
		} else if (!Common.isListEmpty(idList) && idList.size() > 1) {
			JSONArray array = new JSONArray();
			for (int i = 0; i < idList.size(); i++) {
				array.put(Integer.valueOf(idList.get(i)));
			}
			JSONObject object = new JSONObject();
			try {
				object.put("tids", array);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			params.put("tids", object);
			url = JUrl.SITE + JUrl.URL_DO_DELETE_MYFAVORITE_DISCUSS_BATCH;
		}

		HttpUtil.post(url, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onDataSuccess(int status, String mod, String message,
									  String data, JSONObject obj) {
				Message msg = getHandler().obtainMessage(DELETE_SUCCESS);
				msg.sendToTarget();
				UIHelper.showToast(context, "删除成功");
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
									  String data, JSONObject obj) {
				UIHelper.showToast(context, message);

			}
		});
	}

	/**
	 * 获取要删除的所有圈子id
	 * @param sparseBooleanArray
	 */
	private void getDeleteIds(SparseBooleanArray sparseBooleanArray) {
		if (sparseBooleanArray != null && !Common.isListEmpty(mDataList)) {
			if (idList == null) {
				idList = new ArrayList<String>();
			}
			idList.clear();
			for (int i = 0; i < sparseBooleanArray.size(); i++) {
				if (sparseBooleanArray.get(i)) {
					idList.add(i, mDataList.get(i).getTid());
					i++;
				}
			}
		}
	}

	public void notifyStateChange(boolean isEditFlag) {
		if (mAdapter == null || mDeleteAllCbx == null || mBottomEdit == null) {
			return;
		}
		
		if (!isEditFlag){
			((CircleChannelItemAdapter) mAdapter).clearList();
			mDeleteAllCbx.setChecked(false);
		}
		mBottomEdit.setVisibility(isEditFlag ? View.VISIBLE : View.GONE);
		((CircleChannelItemAdapter) mAdapter).setDelFavFlag(isEditFlag);
		mAdapter.notifyDataSetChanged();
	}

	public IScrollCallBack getmScrollCallback() {
		return mScrollCallback;
	}
	public void setmScrollCallback(IScrollCallBack mScrollCallback) {
		this.mScrollCallback = mScrollCallback;
	}

	public void forceTop() {
		mPullListView.setSelection(0);
	}

	public void dealScrollY(){
		if (mScrollListener == null) {
			return;
		}
		mScrollListener.dealScrollY();
	}
}
