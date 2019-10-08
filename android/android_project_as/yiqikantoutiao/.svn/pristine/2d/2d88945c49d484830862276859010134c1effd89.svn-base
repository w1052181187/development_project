package com.chengning.yiqikantoutiao.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.PageHelper;
import com.chengning.common.util.ThreadHelper;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.Consts.ArticleType;
import com.chengning.yiqikantoutiao.LoadStateManager;
import com.chengning.yiqikantoutiao.LoadStateManager.LoadState;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.ArticleActivity;
import com.chengning.yiqikantoutiao.activity.PhotoPageActivity;
import com.chengning.yiqikantoutiao.activity.VideoDetailActivity;
import com.chengning.yiqikantoutiao.adapter.MyFavoriteAdapter;
import com.chengning.yiqikantoutiao.base.IScrollCallBack;
import com.chengning.yiqikantoutiao.base.ReturnTopOnScrollListener;
import com.chengning.yiqikantoutiao.data.access.LocalStateDA;
import com.chengning.yiqikantoutiao.data.bean.BaseArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.data.bean.FavListBean;
import com.chengning.yiqikantoutiao.event.CollectEditStateEvent;
import com.chengning.yiqikantoutiao.event.CollectEvent;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.widget.ProgressRefreshView;
import com.chengning.yiqikantoutiao.widget.PullToRefreshListView_FootLoad;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MyFavoriteNewsFragment extends BaseFragment {
	
	protected static final int DATA_SUCCESS_HTTP = 0;
	protected static final int DELETE_SUCCESS = 1;
	protected static final int FAILURE = 2;
	protected static final int REQ_CODE_ARTICLE_DETAIL = 1001;
	protected static final int DATA_SUCCESS_LOCAL = 3;
	
	private View mView;
	private PullToRefreshListView_FootLoad mListView;
	private ProgressRefreshView mProgressRefresh;
	private LoadStateManager mLoadStateManager;
	private ArrayList<BaseArticlesBean> mFavoriteDatas;
	private MyFavoriteAdapter adapter;
	private App mApp;
	protected int pos;
	
	private PageHelper mPage;
    private HandlerThread mFavThread;
	protected int mOffset = 0;
	private boolean hasNextPage;
	private CollectEditStateEvent editStateEvent;
	private boolean isPrepared;

	private IScrollCallBack mScrollCallback = null;
	private ReturnTopOnScrollListener mScrollListener;

	public MyFavoriteNewsFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		
		mProgressRefresh = new ProgressRefreshView(getContext(), mView);
		mListView = (PullToRefreshListView_FootLoad) mView.findViewById(R.id.my_favorite_listview);

	}
	
	@Override
	public void initDatas() {
		mPage = new PageHelper();
		
		mApp = App.getInst();
		mLoadStateManager = new LoadStateManager();
		editStateEvent = new CollectEditStateEvent();
		mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mProgressRefresh.setWaitVisibility(true);
					mProgressRefresh.setRefreshVisibility(false);
					mProgressRefresh.setNoDataTvVisibility(false);
					mListView.setVisibility(View.GONE);
					editStateEvent.setHasData(false);
					editStateEvent.setType(ArticleType.ARTICLE);
					EventBus.getDefault().post(editStateEvent);
					break;
				case Success:
					mProgressRefresh.setRootViewVisibility(false);
					mListView.setVisibility(View.VISIBLE);
					editStateEvent.setHasData(true);
					editStateEvent.setType(ArticleType.ARTICLE);
					EventBus.getDefault().post(editStateEvent);
					break;
				case NoData:
					mProgressRefresh.setWaitVisibility(false);
					mProgressRefresh.setRefreshVisibility(false);
					mProgressRefresh.setNoDataTvVisibility(true);
					mListView.setVisibility(View.GONE);
					editStateEvent.setHasData(false);
					editStateEvent.setType(ArticleType.ARTICLE);
					EventBus.getDefault().post(editStateEvent);
					break;
				case Failure:
					mProgressRefresh.setWaitVisibility(false);
					mProgressRefresh.setRefreshVisibility(true);
					mProgressRefresh.setNoDataTvVisibility(false);
					mListView.setVisibility(View.GONE);
					break;
				}
			}
		});
		mLoadStateManager.setState(LoadState.Init);
		mProgressRefresh.setNoDataTvText("您还没有收藏文章");
		
		mFavoriteDatas = new ArrayList<BaseArticlesBean>();
		adapter = new MyFavoriteAdapter(getContext(), mFavoriteDatas, new DeleteListener(), false);
		mListView.setAdapter(adapter);
		getMyFavorite(true);
		EventBus.getDefault().register(this);
	}
	
	/**
	 * 我的收藏
	 */
	private void getMyFavorite() {
		getMyFavorite(false);
	}
	/**
	 * 我的收藏
	 */
	private void getMyFavorite(final boolean isFirstPage) {
		if (mApp.isLogin()) {
			if (!Common.hasNet()) {
				Common.showHttpFailureToast(getContext());
				mLoadStateManager.setState(LoadState.Failure);
			} else {
				getMyFavoriteByHttp(false, isFirstPage);
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
			mFavThread = ThreadHelper.creatThread("myFav_news");
		}
		ThreadHelper.handle(mFavThread,  new Runnable() {
		    
		    @Override
		    public void run()  {
				
				ArrayList<ChannelItemBean> list = LocalStateDA.getInst(getContext()).getFavArticles(mOffset);
				getHandler().obtainMessage(DATA_SUCCESS_LOCAL, list).sendToTarget();
		    }
		} );
		
	}

	public void forceTop() {
		mListView.setSelection(0);
	}

	/**
	 * 删除收藏监听类
	 *
	 */
	public class DeleteListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int pos = (Integer) v.findViewById(R.id.item_favorite_delete).getTag();
			if (mApp.isLogin()) {
				deleteFavoriteByHttp(getContext(), mFavoriteDatas.get(pos).getTid(), pos);
			} else {
				deleteFavoriteByDB(getContext(), pos);
			}
		}
		
	}
	
	/**
	 * 从服务器获取数据
	 */
	private void getMyFavoriteByHttp(boolean isIgnoreRequestRunning, final boolean isFirstPage) {
		
		mPage.clearIfDataEmpty();
		String url = JUrl.SITE + JUrl.URL_GET_MY_FAVORITE;
		url = isFirstPage ? mPage.appendFirstPage(url) : mPage.appendNextPageAndMaxid(url);
		if(!isIgnoreRequestRunning && mPage.isRequestRunning()){
			if(!mPage.equalsRunningUrl(url.toString())){
				if(isFirstPage){
					mListView.onRefreshComplete();
				}else{
					mListView.setFootLoadFull();
				}
			}
			return;
		}
		mPage.setRequestStart(url.toString());
    	HttpUtil.get(url.toString(), null, new MyJsonHttpResponseHandler() {
    		
			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
				getHandler().obtainMessage(FAILURE).sendToTarget();
				Common.handleHttpFailure(getContext(), throwable);
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				Gson gson = new Gson();
				FavListBean bean = gson.fromJson(data, FavListBean.class);
				
				if(isFirstPage){
					mPage.clear();
				}
				int currentPage = (null == bean ? 1 : mPage.getCurrentPage() + 1);
				mPage.setCurrentPage(currentPage);
				mPage.setPageData(currentPage, (null == bean ? null : bean.getList()));
				mPage.setMaxPage((null == bean ? 0 :bean.getTotal_page()));
				
				getHandler().obtainMessage(DATA_SUCCESS_HTTP).sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
//				Common.handleHttpFailure(getContext(), null);
				UIHelper.showToast(getContext(), message);
				handleRefreshSuccess();
				mPage.setRequestEnd();
			}
		});
	}

	@Override
	public void installListeners() {
		
		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getMyFavorite(true);
			}
		});

		//加入滑动距离的判断..
		mScrollListener = new ReturnTopOnScrollListener(getmScrollCallback(), mListView);
		mListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true, mScrollListener));
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (!App.getInst().isLogin()) {
					mOffset = 0;
				}
				getMyFavorite(true);
			}

		}); 
		
		mListView.setOnLastPageVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {

				boolean isNextPage = false;
				isNextPage = App.getInst().isLogin() ? mPage.hasNextPage() : hasNextPage;
				if (isNextPage) {
					mListView.setFootLoading();
					getMyFavorite(false);
				}

			}

		}); 
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				pos = position - 1;
				ChannelItemBean bean = new ChannelItemBean();
				bean.setTid(mFavoriteDatas.get(pos).getTid());
				bean.setTitle(mFavoriteDatas.get(pos).getTitle());
				bean.setContent_type(mFavoriteDatas.get(pos).getContent_type());
				String articleType = Consts.CONTENT_TYPE_NORMAL;
				if (bean.getContent_type() != null) {
					articleType = bean.getContent_type();
				}
				if (articleType.equals(Consts.CONTENT_TYPE_TUWEN)) {
					PhotoPageActivity.launch(getContext(), bean);
					return;
				}
				if (TextUtils.equals(articleType,
						Consts.CONTENT_TYPE_VIDEO)) {
					VideoDetailActivity.launchByReqCode(getContext(), bean, ArticleType.ARTICLE);
				} else {
					ArticleActivity.launchByReqCode(getContext(), bean, ArticleType.ARTICLE);
				}


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
	 * 删除收藏（未登录）
	 * @param context
	 */
	protected void deleteFavoriteByDB(final Activity context, final int pos) {
		
		if (mFavThread == null) {
			mFavThread = ThreadHelper.creatThread("myFav_news");
		}
		ThreadHelper.handle(mFavThread,  new Runnable() {
		    
		    @Override
		    public void run()  {
		    	LocalStateDA.getInst(context).deleteFavArticle(LocalStateDA.PREFIX_CHANNEL_ITEM, mFavoriteDatas.get(pos).getTid());
				Message msg = getHandler().obtainMessage(DELETE_SUCCESS);
				msg.arg1 = pos;
				msg.sendToTarget();
		    }
		} );
		
	}

	/**
	 * 删除收藏(登录)
	 * @param context
	 * @param id
	 * @param position 
	 */
	protected void deleteFavoriteByHttp(final Activity context, final String id, final int position) {
		// TODO 删除收藏
		RequestParams params = new RequestParams();
		params.put("tid", id);
		HttpUtil.post(JUrl.SITE + JUrl.URL_CANCEL_COLLECT, params, new MyJsonHttpResponseHandler(){
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(context, throwable);
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				Message msg = getHandler().obtainMessage(DELETE_SUCCESS);
				msg.arg1 = position;
				msg.sendToTarget();
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				UIHelper.showToast(context, message);
			}
			
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
			case DATA_SUCCESS_LOCAL:
			// TODO 获取本地收藏数据成功
			ArrayList beans = (ArrayList) msg.obj;

			if (0 == mOffset) {
				mFavoriteDatas.clear();
			}
			int size = beans.size();
			if (size < 20) {
				hasNextPage = false;
			} else {
				hasNextPage = true;
			}
			mFavoriteDatas.addAll(beans);
			mOffset = Common.calculateOffset(mFavoriteDatas);
			adapter.notifyDataSetChanged();
			handleRefreshSuccess();

			mLoadStateManager.setState(Common.isListEmpty(mFavoriteDatas) ? LoadState.NoData : LoadState.Success);
			break;
		case DATA_SUCCESS_HTTP:
			// TODO 获取网络收藏数据成功
			mFavoriteDatas.clear();
			mFavoriteDatas.addAll(mPage.getDataList());
			if(!Common.isListEmpty(mFavoriteDatas)){
				mPage.setMaxid((mFavoriteDatas.get(0)).getId());
				mLoadStateManager.setState(LoadState.Success);
			}else{
				mLoadStateManager.setState(LoadState.NoData);
			}
			adapter.notifyDataSetChanged();
			handleRefreshSuccess();
			
			mPage.setRequestEnd();
			
			break;
		case DELETE_SUCCESS:
			// TODO 删除收藏成功
			if (!Common.isListEmpty(mFavoriteDatas)) {
				mFavoriteDatas.remove(msg.arg1);
				adapter.notifyDataSetChanged();
				if (0 == mFavoriteDatas.size()) {
					mLoadStateManager.setState(LoadState.NoData);
				}
			}
			
			break;
		case FAILURE:
			
			handleRefreshSuccess();
			if (1 == mPage.getCurrentPage()) {
				mLoadStateManager.setState(LoadState.Failure);
			}
			mPage.setRequestEnd();
			
			break;

		default:
			break;
		}

	}
	
	private void handleRefreshSuccess() {
		mListView.setFootLoadFull();
		mListView.onRefreshComplete();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_my_favorite_news, null);
		isPrepared = true;
		return mView;
	}
	
	@Subscribe
    public void onEventMainThread(CollectEvent event) {
    	switch (event.getArticleType()) {
			case ARTICLE :
				if (!Common.isTrue(event.getFavState())) {
					Message message = getHandler().obtainMessage(DELETE_SUCCESS);
					message.arg1 = pos;
					message.sendToTarget();
				}
				break;

			default :
				break;
		}
    	
    }
	
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (!isPrepared || !getUserVisibleHint()) {
			return;
		}
		editStateEvent.setHasData(!Common.isListEmpty(mFavoriteDatas));
		editStateEvent.setType(ArticleType.ARTICLE);
		EventBus.getDefault().post(editStateEvent);
	}

	@Override
	public void onDestroy() {
		ThreadHelper.destory(mFavThread);
		getHandler().removeMessages( DATA_SUCCESS_LOCAL );
		getHandler().removeMessages( DATA_SUCCESS_HTTP );
		getHandler().removeMessages( DELETE_SUCCESS );
		getHandler().removeMessages( FAILURE );
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public void notifyStateChange(boolean isEditFlag) {
		if (adapter == null) {
			return;
		}
		adapter.setDelFlag(isEditFlag);
		adapter.notifyDataSetChanged();
	}

	public IScrollCallBack getmScrollCallback() {
		return mScrollCallback;
	}
	public void setmScrollCallback(IScrollCallBack mScrollCallback) {
		this.mScrollCallback = mScrollCallback;
	}

}
