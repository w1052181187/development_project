package com.chengning.fenghuo.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.PageHelper;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.LoadStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.ArticleActivity;
import com.chengning.fenghuo.activity.ColumnistActivity;
import com.chengning.fenghuo.activity.DynamicDetailActivity;
import com.chengning.fenghuo.activity.UserInfoActivity;
import com.chengning.fenghuo.adapter.ColumnistArticleListAdapter;
import com.chengning.fenghuo.adapter.ColumnistArticleListAdapter.NameFollowListener;
import com.chengning.fenghuo.adapter.MyFansListAdapter;
import com.chengning.fenghuo.base.BaseListBean;
import com.chengning.fenghuo.data.access.ChannelItemListDA;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuo.data.bean.ChanBean;
import com.chengning.fenghuo.data.bean.ChannelBean;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.data.bean.ColumnistListBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.SPHelper;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.AutoPlayBigPicViewSpecial;
import com.chengning.fenghuo.widget.ProgressRefreshView;
import com.chengning.fenghuo.widget.PullToRefreshListView_FootLoad;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ColumnFragment extends BaseFragment {

	private static final int DATA_SUCCESS = 0;
	private static final int HEADER_SUCCESS = 5;
	private static final int NO_LOGIN_SUCCESS = 6;
	// 跳转类型 1个人中心 2文章详情 3 列表 4 无跳转
	private static final int LIST_JUMP_TYPE = 3;
	private static final int PERSON_JUMP_TYPE = 1;
	private static final int ARTICLE_JUMP_TYPE = 2;
	private static final int NO_JUMP_TYPE = 4;

	private static final String KEY_BEAN = "bean";
	public static final String KEY_CHANNEL_SLIDE = "column_slide";
	public static final int INIT_WAIT_TIME = 500;

	private ProgressRefreshView mProgressRefresh;
	private PullToRefreshListView_FootLoad mPullListView;
	private LoadStateManager mLoadStateManager;
	private BaseAdapter mAdapter;

	private List mCommentList;

	private PageHelper mPageHelper;
	private int mCachePage;

	private View mView;
	private ArrayList<PageHelper> mPageHelpers;
	private ArrayList<BaseAdapter> mAdapters;
	private View mSlideView;
	private ChanBean mBean;
	private RadioGroup mRadioGroup;
	private ColumnType type = ColumnType.ARTICLE;
	private int mCurIndex = 0;
	private LinearLayout mPullHeader;
	protected ArrayList<ChannelItemBean> mHeaderList;
	private View mSlideViewGroup;

	private Runnable LOAD_DATA = new Runnable() {
		@Override
		public void run() {
			getDataOnInit();
		}
	};
	private ChannelItemListDA mListServer;
	private boolean isDataFromDB = false;
	private boolean isHeaderSeted = false;
	private RadioButton mRadioArticle;
	private RadioButton mRadioMy;
	private RadioButton mRadioMyColumnist;
	private boolean isFooterSetted = false;
	private View mFooter;

	public static ColumnFragment newInstance(ChanBean bean) {

		ColumnFragment newFragment = new ColumnFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(KEY_BEAN, bean);
		newFragment.setArguments(bundle);
		return newFragment;
	}

	private class FollowOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
				UserInfoBean bean = (UserInfoBean) v.getTag();
				Follow(bean.getUid(), v);
			}
		}
	}

	public void Follow(String uid, final View v) {
		ArticleManagerUtils.followUser(getActivity(), uid, new Runnable() {

			@Override
			public void run() {
				v.setSelected(!v.isSelected());
				if (v instanceof TextView) {
					((TextView)v).setText(!v.isSelected() ? getString(R.string.str_userinfo_already_focused)
							: getString(R.string.str_columnist_not_focused));
				}
			}
		});
	}

	@Override
	public void initViews() {
		mProgressRefresh = new ProgressRefreshView(getActivity(), mView);
		mPullListView = (PullToRefreshListView_FootLoad) getView()
				.findViewById(R.id.list);

		setView();
		// mProgressRefresh.setNoDataTvText(configNoData());
	}

	@Override
	public void initDatas() {

		mBean = (ChanBean) getArguments().getSerializable(KEY_BEAN);
		mListServer = ChannelItemListDA.getInst(getActivity());
		isHeaderSeted = false;
		isFooterSetted = false;
		initPager();
		initList();
		mCommentList = new ArrayList<Serializable>();

		initLoadState();
		initAdapter();
		getHandler().postDelayed(LOAD_DATA, INIT_WAIT_TIME);

	}

	private void getDataOnInit() {
		if (!Common.hasNet()) {
			getDataByDB();
		} else {
			if (ColumnType.ARTICLE == type || ColumnType.MY == type) {
				long oldTime = SPHelper.getInst().getLong(
						SPHelper.PREFIX_TIME_UPDATE_CHANNEL
								+ buildChannel(type));
				if (Common.isTargetTimeBefore(oldTime)) {
					List list = mListServer.queryChannelItemList(
							buildChannel(type), JUrl.PAGE_START);

					if (!Common.isListEmpty(list)) {
						isDataFromDB = true;
						// onGetNewsByDB(mTarPage);
						// sendListMessage(INIT_NEWSDATA_UI, list);
						mPageHelper.clear();
						mPageHelper.setCurrentPage(JUrl.PAGE_START);
						mPageHelper.setPageData(JUrl.PAGE_START, list);
						mPageHelper.setMaxPage(SPHelper.getInst().getInt(SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + buildChannel(type)));
						getHandler().obtainMessage(HEADER_SUCCESS)
								.sendToTarget();
						getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();

					} else {
						getDataByHttp(true);
					}
				} else {
					getDataByDB();
				}
			} else if (ColumnType.MY_COLUMN == type) {
				getDataByHttp(true);
			}

		}
	}

	private String buildChannel(ColumnType type) {
		String channel = "";
		switch (type) {
		case ARTICLE:
			channel = "channel_" + mBean.getId();
			break;
		case MY:
			channel = "channel_" + mBean.getId() + "_my";
			break;
		default:
			break;
		}
		return channel;
	}

	private void getDataByDB() {
		List list = new ArrayList<Serializable>();
		if (ColumnType.ARTICLE == type || ColumnType.MY == type) {
			list = mListServer.queryChannelItemList(buildChannel(type),
					JUrl.PAGE_START);
		}

		if (null != list) {
			isDataFromDB = true;
			mPageHelper.clear();
			mPageHelper.setCurrentPage(JUrl.PAGE_START);
			mPageHelper.setPageData(JUrl.PAGE_START, list);
			mPageHelper.setMaxPage(SPHelper.getInst().getInt(SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + buildChannel(type)));
			getHandler().obtainMessage(HEADER_SUCCESS).sendToTarget();
			getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();

		} else {
			Common.showHttpFailureToast(getActivity());
			mLoadStateManager.setState(LoadState.Failure);
		}
	}

	private void initAdapter() {
		mAdapters = new ArrayList<BaseAdapter>();
		mAdapters.add(new ColumnistArticleListAdapter(getActivity(),
				mCommentList, SettingManager.getInst().getNoPicModel(), true, new NameFollowListener() {

					@Override
					public void followChange() {
						SPHelper.getInst().saveBoolean(
								SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY, true);
						SPHelper.getInst().saveBoolean(
								SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY_COLUMN,
								true);
					}
				}));
		mAdapters.add(new ColumnistArticleListAdapter(getActivity(),
				mCommentList, SettingManager.getInst().getNoPicModel(), true, new NameFollowListener() {

					@Override
					public void followChange() {

						SPHelper.getInst().saveBoolean(
								SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY, true);
						SPHelper.getInst().saveBoolean(
								SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY_COLUMN,
								true);
					}
				}));
		mAdapters.add(new MyFansListAdapter(getContext(), mCommentList,
				new FollowOnClickListener()));
		mAdapter = mAdapters.get(mCurIndex);
	}

	private void initList() {

	}

	private void initPager() {
		if (null == mPageHelpers) {
			mPageHelpers = new ArrayList<PageHelper>();
			for (int i = 0; i < 3; i++) {
				mPageHelpers.add(new PageHelper());
			}
		}
		
		mPageHelper = mPageHelpers.get(mCurIndex);
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
				getDataOnInit();
			}
		});

		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				handleItemClick(view, type);
			}
		});

		mPullListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader
				.getInstance(), false, true));
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				getDataByHttp(true);
			}

		});
		mPullListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if (mPageHelper.hasNextPage()) {
							mPullListView.setFootLoading();
							if (isDataFromDB) {
								if (mCachePage < mPageHelper.getCurrentPage()) {
									getDataByHttp();
									return;
								}
								getNextDataByDB();
							} else {
								getDataByHttp();
							}
						}
					}

				});

	}

	protected void getNextDataByDB() {
		int mTarPage = mPageHelper.getCurrentPage() + 1;
		List list = new ArrayList<Serializable>();
		if (ColumnType.ARTICLE == type || ColumnType.MY == type) {
			list = mListServer.queryChannelItemList(buildChannel(type),
					mTarPage);
		}

		if (!Common.isListEmpty(list)) {
			isDataFromDB = true;
			mPageHelper.setCurrentPage(mTarPage);
			mPageHelper.setPageData(mTarPage, list);
			getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();

		} else {
			handleRefreshSuccess();
		}
	}

	protected void handleItemClick(View view, ColumnType type) {
		switch (type) {
		case ARTICLE:
			handleBeanClick(view);
			break;
		case MY:
			handleBeanClick(view);
			break;
		case MY_COLUMN:
			UserInfoBean u = (UserInfoBean) view.findViewById(
					R.id.follow_fans_user_name).getTag();
			UserInfoActivity.launch(getActivity(), u.getNickname());
			break;

		default:
			break;
		}
	}

	private void handleBeanClick(View view) {

		TextView title = (TextView) view
				.findViewById(R.id.item_channel_item_title);
		if (title == null) {
			title = (TextView) view
					.findViewById(R.id.item_circlechannel_content);
		}
		Common.handleTextViewReaded(getContext(), view,
				R.id.item_channel_item_title, true);
		BaseChannelItemBean bean = (BaseChannelItemBean) title.getTag();
		if (bean == null) {
			return;
		}
		if (bean instanceof ChannelItemBean) {
			LocalStateDA.getInst(getContext()).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM,
					((ChannelItemBean) bean).getTid());
			ArticleActivity.launch(getContext(), (ChannelItemBean) bean);
		} else if (bean instanceof DynamicItemBean) {
			LocalStateDA.getInst(getContext()).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM,
					((DynamicItemBean) bean).getTid());
			DynamicDetailActivity.launch(getContext(), (DynamicItemBean) bean);
		}
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case DATA_SUCCESS:

			int position = mPullListView.getRefreshableView()
					.getFirstVisiblePosition();
			mCommentList.clear();
			mCommentList.addAll(mPageHelper.getDataList());
			if (!Common.isListEmpty(mCommentList)) {
				mPageHelper.setMaxid(buildMaxId(mCommentList, type));

			}
			mLoadStateManager.setState(LoadState.Success);
			// else {
			// mLoadStateManager.setState(LoadState.NoData);
			// }
			mAdapter = mAdapters.get(mCurIndex);
			mPullListView.setAdapter(mAdapter);
			mPullListView.setSelection(position);
			mAdapter.notifyDataSetChanged();
			handleRefreshSuccess();
			
			if (!isFooterSetted && mCommentList.isEmpty()) {
				mFooter = LayoutInflater.from(getContext()).inflate(
						R.layout.footer_dynamic_detail, null);
				TextView noData = (TextView) mFooter.findViewById(R.id.article_comment_bottom_no_data);
				noData.setText("暂无数据");
				//截取footer的onItemClickListener监听事件
				mFooter.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
					}
				});
				mPullListView.getRefreshableView().addFooterView(mFooter);
				isFooterSetted = true;
			} else if (!mCommentList.isEmpty() && null != mFooter) {
				mPullListView.getRefreshableView().removeFooterView(mFooter);
				isFooterSetted = false;
			}
			
			break;
		case HEADER_SUCCESS:

			if (!isHeaderSeted) {
				List<ChannelItemBean> topicList = ChannelItemListDA.getInst(
						getContext()).queryChannelItemList(KEY_CHANNEL_SLIDE,
						JUrl.PAGE_START);
				if (topicList != null) {
					setHeader(topicList);
					isHeaderSeted = true;
				}
			}

			break;
			
		default:
			break;
		}
	}

	private void initHeaderTab() {
		if (null != mRadioArticle) {
			LinearLayout.LayoutParams params = (LayoutParams) mRadioArticle.getLayoutParams();
			params.weight = App.getInst().isLogin() ? 1.0f : 0.0f;
			mRadioArticle.setLayoutParams(params);
		}
		
		if (null != mRadioMy) {
			mRadioMy.setVisibility(App.getInst().isLogin() ? View.VISIBLE : View.GONE);
		}
		
		if (null != mRadioMyColumnist) {
			mRadioMyColumnist.setVisibility(App.getInst().isLogin() ? View.VISIBLE : View.GONE);
		}
	}

	private void setHeader(List<ChannelItemBean> listSlide) {
		if (!Common.isListEmpty(listSlide)) {
			if (mSlideView != null) {
				mPullHeader.removeView(mSlideView);
			}
			mSlideView = new AutoPlayBigPicViewSpecial(getContext(), listSlide,
					new ImageOnClickListener()).InitbigPic();
			mPullHeader.addView(mSlideView);
		} else {
			mPullHeader.removeView(mSlideView);
		}

		if (mSlideViewGroup != null) {
			mPullHeader.removeView(mSlideViewGroup);
		}

//		mSlideViewGroup = LayoutInflater.from(getContext()).inflate(
//				R.layout.header_fragment_column, null);
//		mRadioGroup = (RadioGroup) mSlideViewGroup
//				.findViewById(R.id.heander_column_radioGroup);
//		mRadioArticle = (RadioButton) mSlideViewGroup
//				.findViewById(R.id.heander_column_article);
//		mRadioMy = (RadioButton) mSlideViewGroup
//				.findViewById(R.id.heander_column_my);
//		mRadioMyColumnist = (RadioButton) mSlideViewGroup
//				.findViewById(R.id.heander_column_my_columnist);
//		
//		initHeaderTab();
//		
//		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				switch (checkedId) {
//				case R.id.heander_column_article:
//					mCurIndex = 0;
//					type = ColumnType.ARTICLE;
//					break;
//				case R.id.heander_column_my:
//					mCurIndex = 1;
//					type = ColumnType.MY;
//					break;
//				case R.id.heander_column_my_columnist:
//					mCurIndex = 2;
//					type = ColumnType.MY_COLUMN;
//					break;
//
//				default:
//					break;
//				}
//				handleColumn(type, mCurIndex);
//				// setChangeView(mCurIndex);
//			}
//		});
		setChangeView(mCurIndex);
		mPullHeader.addView(mSlideViewGroup);

	}

	private class ImageOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ChannelItemBean bean = (ChannelItemBean) v.getTag();
			handleBeanClick(bean);

		}
	}

	public void handleBeanClick(ChannelItemBean bean) {
		// 跳转类型 1个人中心 2文章详情 3 列表 4 无跳转
		if (bean != null) {
			try {
				switch (Integer.valueOf(bean.getJump_type())) {
				case PERSON_JUMP_TYPE:
					UserInfoActivity.launch(getContext(), bean.getJump_id());
					break;
				case ARTICLE_JUMP_TYPE:
					if (!TextUtils.isEmpty(bean.getJump_id())) {
						bean.setTid(bean.getJump_id());
						ArticleActivity.launch(getContext(), bean);
					}
					break;
				case LIST_JUMP_TYPE:
					if (!LoginManager.getInst().checkLoginWithNotice(
							getActivity())) {

					} else {
						UserInfoBean userInfo = App.getInst().getUserInfoBean();
						if (userInfo != null)
							ColumnistActivity.launch(getContext());
					}
					break;
				case NO_JUMP_TYPE:
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private String buildMaxId(List mCommentList, ColumnType type) {
		String maxid = "";
		switch (type) {
		case ARTICLE:
			maxid = ((ChannelItemBean) mCommentList.get(0)).getTid();
			break;
		case MY:
			maxid = ((ChannelItemBean) mCommentList.get(0)).getTid();
			break;
		case MY_COLUMN:
			maxid = ((UserInfoBean) mCommentList.get(0)).getUid();
			break;

		default:
			break;
		}
		return maxid;
	}

	private void getDataByHttp() {
		getDataByHttp(false);
	}

	private void getDataByHttp(final boolean isFirstPage) {
		getHandler().postDelayed(new Runnable() {

			@Override
			public void run() {
				getDataByHttp(false, isFirstPage);
			}
		}, Common.TIME_WAIT_COMPLETE);
	}

	@Deprecated
	private void getDataByHttp(boolean isIgnoreRequestRunning,
			final boolean isFirstPage) {
		String url = buildUrl(type);
		url = isFirstPage ? mPageHelper.appendFirstPage(url) : mPageHelper
				.appendNextPageAndMaxid(url);
		if (!isIgnoreRequestRunning && mPageHelper.isRequestRunning()) {
			if (!mPageHelper.equalsRunningUrl(url.toString())) {
				if (isFirstPage) {
					mPullListView.onRefreshComplete();
				} else {
					mPullListView.setFootLoadFull();
				}
			}
			return;
		}
		mPageHelper.setRequestStart(url.toString());

		HttpUtil.get(url.toString(), null, new MyJsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				Common.handleHttpFailure(getActivity(), throwable);
				handleRefreshSuccess();
				if (mPageHelper.isCurrentPageFirst()) {
					mLoadStateManager.setState(LoadState.Failure);
				}
			};

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
				isDataFromDB = false;
				Gson gson = new Gson();
				BaseListBean bean = null;
				switch (type) {
				case ARTICLE:
					bean = gson.fromJson(data, ChannelBean.class);
					if (isFirstPage) {
						isHeaderSeted = false;
						mHeaderList = ((ChannelBean) bean).getColumnlist();
						ChannelItemListDA.getInst(getContext())
								.setChannelAndPage(mHeaderList,
										KEY_CHANNEL_SLIDE, JUrl.PAGE_START);
						ChannelItemListDA.getInst(getContext()).clearChannel(
								KEY_CHANNEL_SLIDE);
						ChannelItemListDA.getInst(getContext())
								.insertChannelItemList(mHeaderList,
										KEY_CHANNEL_SLIDE, JUrl.PAGE_START);
					}
					break;
				case MY:
					bean = gson.fromJson(data, ChannelBean.class);
					SPHelper.getInst().saveBoolean(
							SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY, false);
					break;
				case MY_COLUMN:
					bean = gson.fromJson(data, ColumnistListBean.class);
					SPHelper.getInst().saveBoolean(
							SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY_COLUMN, false);
					break;

				default:
					break;
				}
				if (isFirstPage) {
					mPageHelper.clear();
					getHandler().obtainMessage(HEADER_SUCCESS).sendToTarget();
				}
				int currentPage = (null == bean ? 1 : mPageHelper
						.getCurrentPage() + 1);
				mPageHelper.setCurrentPage(currentPage);
				mPageHelper.setPageData(currentPage, (null == bean ? null
						: bean.getList()));
				mPageHelper.setMaxPage((null == bean ? 0 : bean.getTotal_page()));

				if (ColumnType.ARTICLE == type || ColumnType.MY == type) {
					mListServer.setChannelAndPage(bean.getList(),
							buildChannel(type), currentPage);
					mListServer.insertChannelItemList(bean.getList(),
							buildChannel(type), currentPage);
					SPHelper.getInst().saveLong(SPHelper.PREFIX_TIME_UPDATE_CHANNEL + buildChannel(type),
							System.currentTimeMillis());
					SPHelper.getInst().saveInt(SPHelper.PREFIX_KEY_CHANNEL_MAXPAGE + buildChannel(type),
							bean.getTotal_page());
				} else if (ColumnType.MY_COLUMN == type) {

				}

				getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();

			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(getActivity(), message);
				if (-99 == status) {
					getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();
				}
				handleRefreshSuccess();
			}
		});
	}

	private String buildUrl(ColumnType type) {
		String url = null;
		switch (type) {
		case ARTICLE:
			url = new StringBuilder(JUrl.SITE).append(mBean.getUrl()).toString();
			break;
		case MY:
			url = JUrl.SITE + JUrl.URL_GET_COLUMN_MY_FOCUSE;
			break;
		case MY_COLUMN:
			url = JUrl.SITE + JUrl.URL_GET_MILITARY_FANS_FOLLOW;
			url = JUrl.append(url, "subscribe", "1");
			break;

		default:
			break;
		}
		return url;
	}

	protected void handleRefreshSuccess() {
		mPullListView.setFootLoadFull();
		mPullListView.onRefreshComplete();
		mPageHelper.setRequestEnd();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_base_list_no_top, container,
				false);
		return mView;
	}

	private void setView() {
		mPullHeader = new LinearLayout(getContext());
		mPullHeader.setOrientation(LinearLayout.VERTICAL);
		mPullListView.getRefreshableView().addHeaderView(mPullHeader);
	}

	protected void handleColumn(ColumnType type, int index) {
		mPageHelper = mPageHelpers.get(index);
		if ((ColumnType.MY == type && SPHelper.getInst().getBoolean(
				SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY))
				|| (ColumnType.MY_COLUMN == type && SPHelper
						.getInst()
						.getBoolean(SPHelper.KEY_COLUMN_FOLLOW_CHANGE_MY_COLUMN))) {
			getDataByHttp(true);
		} else {
			if (Common.isListEmpty(mPageHelper.getDataList())) {
				getDataByHttp(true);
			} else {
				getHandler().obtainMessage(DATA_SUCCESS).sendToTarget();
			}
		}

	}

	public void setChangeView(int index) {
		((RadioButton) mRadioGroup.getChildAt(index)).setChecked(true);
	}

	enum ColumnType {
		ARTICLE, MY, MY_COLUMN;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mCachePage = savedInstanceState.getInt("cache_page");
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("cache_page", mPageHelper.getCurrentPage());
		super.onSaveInstanceState(outState);
	}

	public void forceRefresh() {
		mCurIndex = 0;
		type = ColumnType.ARTICLE;
//		setChangeView(mCurIndex);
		getDataByHttp(true);
	}
	
	@Override
	public void onResume() {
//		if (SPHelper.getInst().getBoolean(SPHelper.KEY_LOGOUT_CHANGE)) {
//			forceRefresh();
//			SPHelper.getInst().saveBoolean(SPHelper.KEY_LOGOUT_CHANGE, false);
//		}
		if (SPHelper.getInst().getBoolean(SPHelper.KEY_LAST_LOGIN_STATE_COLUMN) != App.getInst().isLogin()) {
			forceRefresh();
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		SPHelper.getInst().saveBoolean(SPHelper.KEY_LAST_LOGIN_STATE_COLUMN, App.getInst().isLogin());
		super.onPause();
	}
	
}
