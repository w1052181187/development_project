package com.chengning.fenghuovideo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.LoadStateManager;
import com.chengning.fenghuovideo.LoadStateManager.LoadState;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.VideoModuleManager;
import com.chengning.fenghuovideo.data.access.LocalStateDA;
import com.chengning.fenghuovideo.data.bean.ArticlesBean;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.ChanBean;
import com.chengning.fenghuovideo.event.CommentSuccessEvent;
import com.chengning.fenghuovideo.fragment.ChannelFragment;
import com.chengning.fenghuovideo.nicevideo.FhNiceVideoWidget;
import com.chengning.fenghuovideo.util.ArticleCommentListeners;
import com.chengning.fenghuovideo.util.ArticleManagerUtils;
import com.chengning.fenghuovideo.util.ArticleManagerUtils.DataStateListener;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.UIHelper;
import com.chengning.fenghuovideo.util.UmengShare;
import com.chengning.fenghuovideo.widget.ArticleCommentBottom;
import com.chengning.fenghuovideo.widget.ArticleShareDialog;
import com.chengning.fenghuovideo.widget.NoFocusingBottomListenerScrollView;
import com.chengning.fenghuovideo.widget.ProgressRefreshView;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.chengning.fenghuovideo.widget.TitleBar.BackOnClickListener;
import com.chengning.fenghuovideo.widget.VideoArticleRelativeBottom;
import com.chengning.fenghuovideo.widget.VideoItemWidget;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class VideoDetailBottomFragment extends BaseFragment {

	private static final String TAG = VideoDetailBottomFragment.class.getSimpleName();
	public static final String TYPE_CLICK_COMMENT = "cmt";

	private static final int MSG_DATA_READY = 1;
	private static final int LOAD_VIDEO_FINISH = 2;

	private View mView;

	private View mCoverLayout;
	private ProgressRefreshView mProgressRefresh;
	private TextView mArticleTitle;
	private TextView mArticleTime;
	private View mShangTouTiaoView;
	private TextView mShangTouTiaoTv;
	private View mBuXiHuanView;
	private TextView mBuXiHuanTv;
	private ArticleCommentBottom mArticleComment;
	private VideoArticleRelativeBottom mArticleRelative;
//	private VideoItemWidget mVideoWidget;

	private boolean mIsFromPush;
	private BaseArticlesBean mBean;
	private ArticlesBean mArticleBean;

	private Activity mContext;
	private LoadStateManager mLoadStateManager;

	private View mRoot;

	private ArticleManagerUtils mArticleManagerUtils;
	private NoFocusingBottomListenerScrollView mScrollView;
	private ImageView mShareImg;

	private int mScrollY;
	private View mArticleCommentLayout;
	private String mClickType;
	private DataStateListener mLikeState;
	private DataStateListener mNotLikeState;
	
	private OnLoadFinishListener mLoadFinishListener;

	public static VideoDetailBottomFragment newInstance(BaseArticlesBean bean, String type, boolean isFromPush){
		VideoDetailBottomFragment newFragment = new VideoDetailBottomFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("bean", bean);
		bundle.putString("type", type);
		bundle.putBoolean("push", isFromPush);
		newFragment.setArguments(bundle);
		return newFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		mView = inflater.inflate(R.layout.fragment_video_detail_bottom, null);
		return mView;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putSerializable("bean", mBean);
		savedInstanceState.putString("type", mClickType);
		savedInstanceState.putBoolean("push", mIsFromPush);
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
		mBean = (BaseArticlesBean) savedInstanceState.getSerializable("bean");
		mIsFromPush = savedInstanceState.getBoolean("push", false);
	}
	
	public void setOnLoadFinishListener(OnLoadFinishListener listener){
		this.mLoadFinishListener = listener;
	}
	
	public static interface OnLoadFinishListener{
		public void onFinish(BaseArticlesBean bean);
	}

	@Override
	public void initViews() {
		// view
		DisplayUtil.getInst().init(getActivity());
		mRoot = mView.findViewById(R.id.root);

//		mVideoWidget = VideoItemWidget.createVideoItemWidget(getActivity(), mRoot, null, null);
		
		mProgressRefresh = new ProgressRefreshView(getActivity(), true);
		mCoverLayout = mView.findViewById(R.id.cover_layout);
		mArticleTitle = (TextView) mView.findViewById(R.id.article_content_top_title);
		mArticleTime = (TextView) mView.findViewById(R.id.article_content_top_time);
		mShangTouTiaoView = mView.findViewById(R.id.article_content_bottom_zan_layout);
		mShangTouTiaoTv = (TextView) mView.findViewById(R.id.article_content_bottom_zan_text_view);
		mBuXiHuanView = mView.findViewById(R.id.article_content_bottom_cai_layout);
		mBuXiHuanTv = (TextView) mView.findViewById(R.id.article_content_bottom_cai_text_view);
		mShareImg = (ImageView) mView.findViewById(R.id.article_content_bottom_share_img);
		mArticleCommentLayout = mView.findViewById(R.id.article_comment_bottom_layout);

		mScrollView = (NoFocusingBottomListenerScrollView) mView.findViewById(R.id.article_content_scroll);

	}

	@Override
	public void initDatas() {
		mContext = getActivity();
		UmengShare.getInstance().checkInit(mContext);

		// data
		Bundle bundle = getArguments();
		mBean = (BaseArticlesBean) bundle.getSerializable("bean");
		mClickType = bundle.getString("type");

		mIsFromPush = bundle.getBoolean("push", false);

		initLoadState();

		initContent();
		initBottom();
		EventBus.getDefault().register(getActivity());
	}

	private void initBottom() {
		mArticleComment = new ArticleCommentBottom(getActivity(), mRoot);
		mArticleRelative = new VideoArticleRelativeBottom(getActivity(), mRoot);
		mArticleComment.setChannelBean(mBean, Consts.ArticleCommentType.ARTICLE);
		mArticleComment.setCommentClickListener(new Runnable() {
			@Override
			public void run() {
				scrollToComment();
			}
		});
		mArticleRelative.setChannelBean(mBean);

	}

	private void scrollToComment() {
		int top = mArticleCommentLayout.getTop();
		mScrollView.smoothScrollTo(0, top == mScrollY ? 0 : top);
	}

	private void initContent() {
		if (mIsFromPush) {
			LocalStateDA.getInst(getActivity()).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM, mBean.getTid());
		}
		mLikeState = new DataStateListener() {

			@Override
			public void success(Object... objects) {
				String digCounts = (String) objects[0];
				handleZanTV(Common.trimZero(digCounts));
				mShangTouTiaoTv.setSelected(true);
				mShangTouTiaoTv.setEnabled(true);
				mArticleBean.setIs_dig(Common.TRUE);

			}

			@Override
			public void init() {
				mShangTouTiaoTv.setEnabled(false);
			}

			@Override
			public void failure() {
				mShangTouTiaoTv.setEnabled(true);
			}
		};

		mNotLikeState = new DataStateListener() {
			@Override
			public void init() {
				mBuXiHuanTv.setEnabled(false);
			}

			@Override
			public void success(Object... objects) {
				String digCounts = (String) objects[0];
				handleNotLikeTV(Common.trimZero(digCounts));
				mBuXiHuanTv.setSelected(true);
				mBuXiHuanTv.setEnabled(true);
				mArticleBean.setIs_bad(Common.TRUE);
			}

			@Override
			public void failure() {
				mBuXiHuanTv.setEnabled(true);
			}
		};

		mArticleManagerUtils = new ArticleManagerUtils();
		if (Common.hasNet()) {
			getDataByHttp();
		} else {
			boolean isArticleSaved = false;
			String aStr = LocalStateDA
					.getInst(getActivity())
					.queryLocalStateNotNull(LocalStateDA.PREFIX_CHANNEL_ITEM,
							String.valueOf(mBean.getTid()))
					.getData_item_article();
			if (!TextUtils.isEmpty(aStr)) {
				try {
					ArticlesBean aBean = SerializeUtil.deSerialize(aStr,
							ArticlesBean.class);
					if (aBean != null && !TextUtils.isEmpty(aBean.getTid())) {
						isArticleSaved = true;
						getHandler().obtainMessage(MSG_DATA_READY, aBean)
								.sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!isArticleSaved) {
				getDataByHttp();
			}
		}
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
								mCoverLayout.setVisibility(View.VISIBLE);
								mProgressRefresh.setWaitVisibility(true);
								mProgressRefresh.setRefreshVisibility(false);
								mProgressRefresh.setNoDataTvVisibility(false);
								break;
							case Success:
								mCoverLayout.setVisibility(View.INVISIBLE);
								mProgressRefresh.setRootViewVisibility(false);
								break;
							case Failure:
								mCoverLayout.setVisibility(View.VISIBLE);
								mProgressRefresh.setWaitVisibility(false);
								mProgressRefresh.setRefreshVisibility(true);
								mProgressRefresh.setNoDataTvVisibility(false);
								break;
							default:
								break;
						}
					}
				});

		mLoadStateManager.setState(LoadState.Init);
	}

	@Override
	public void installListeners() {
		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getDataByHttp();
			}
		});

		mShangTouTiaoView.setOnClickListener(new ArticleCommentListeners.
				LikeOnClickListener(getActivity(), mArticleManagerUtils, mLikeState));
		mBuXiHuanView.setOnClickListener(new ArticleCommentListeners.
				NotLikeOnClickListener(getActivity(), mArticleManagerUtils, mNotLikeState));

		mShareImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					ArticleShareDialog dialog = new ArticleShareDialog();
					dialog.setBean(mArticleBean);
					dialog.showAllowingStateLoss(((BaseFragmentActivity)getContext()), ((BaseFragmentActivity)getContext()).getSupportFragmentManager(),
							ArticleShareDialog.class.getSimpleName());
				}
			}
		});

		mScrollView.setOnScrollToBottomLintener(new NoFocusingBottomListenerScrollView.OnScrollToBottomListener() {
			@Override
			public void onScrollBottomListener(boolean isBottom) {
				mScrollView.setLoading();
				mArticleComment.getNextPageComment(getContext(), new DataStateListener() {

					@Override
					public void init() {
					}

					@Override
					public void success(Object... objects) {
						mScrollView.setLoadFinish();
					}

					@Override
					public void failure() {
						mScrollView.setLoadFinish();
					}
				});
			}

			@Override
			public void onScrollY(int scrollY) {
				mScrollY = scrollY;
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case MSG_DATA_READY:
			// data ready
			mArticleBean = (ArticlesBean) msg.obj;

			if(mIsFromPush && mBean != null){
				mBean.setTitle(mArticleBean.getTitle());
				mBean.setDateline(mArticleBean.getDateline());
				mBean.setContent_type(Consts.CONTENT_TYPE_VIDEO);
				mBean.setImage_list(mArticleBean.getVideo_photo());
				mArticleComment.setChannelBean(mBean, Consts.ArticleCommentType.ARTICLE);
			}

			mArticleTitle.setText(mArticleBean.getTitle());
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				// Call requires API level 11
				mArticleTitle.setTextIsSelectable(true);
			}
			mArticleTime.setText(mArticleBean.getDateline());

			handleZanTV(Common.trimZero(mArticleBean.getDigcounts()));
			handleNotLikeTV(Common.trimZero(mArticleBean.getBadcounts()));
			mShangTouTiaoTv.setSelected(Common.isTrue(mArticleBean.getIs_dig()));
			mBuXiHuanTv.setSelected(Common.isTrue(mArticleBean.getIs_bad()));
			mShangTouTiaoView.setTag(mArticleBean);
			mBuXiHuanView.setTag(mArticleBean);

			mArticleBean.setContent_type(mBean.getContent_type());
			mArticleComment.setData(mArticleBean);
			mArticleComment.setIsHasShare(true);
			mArticleRelative.setData(mArticleBean);
			if(mLoadFinishListener != null){
				mLoadFinishListener.onFinish(mArticleBean);
			}
			// TODO(debug)
//			mVideoWidget.setUrl(mArticleBean.getVideo_address());
//			mVideoWidget.setUrlNotStart(mArticleBean.getVideo_play());
			
			getHandler().obtainMessage(LOAD_VIDEO_FINISH).sendToTarget();

			break;
		case LOAD_VIDEO_FINISH:
			mLoadStateManager
					.setState(LoadState.Success);
			if (TextUtils.equals(mClickType,TYPE_CLICK_COMMENT)) {
				scrollToComment();
			}
			break;
		default:
			break;
		}
	}

	private void handleNotLikeTV(String badCount) {
		mBuXiHuanTv.setText(TextUtils.isEmpty(badCount) ? "不喜欢"
				: badCount);
	}

	private void handleZanTV(String digCount) {
		mShangTouTiaoTv.setText(TextUtils.isEmpty(digCount) ? "上头条"
				: digCount);
	}

	@Subscribe
    public void onEventMainThread(CommentSuccessEvent event) {
		mArticleBean.getReply_list().add(event.getmBean());
		mArticleBean.setReplys(mArticleBean.getReplys() + 1);
		mArticleComment.setData(mArticleBean);
    }

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(getActivity());
		mArticleManagerUtils.destoryFavHandleThread();
		super.onDestroy();
	}

	private void handleHttpFailure() {
		Common.showHttpFailureToast(mContext);
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				mLoadStateManager.setState(LoadState.Failure);
			}
		});
	}

	private void getDataByHttp() {
		final String aid = String.valueOf(mBean.getTid());
		String url = JUrl.SITE + JUrl.URL_GET_ARTICLES_SUBCSRIBE + aid;
		BasicHeader header = new BasicHeader("Accept-Encoding", "gzip, deflate");
		Header[] headers = { new BasicHeader("Accept", "*/*"), header,
				new BasicHeader("Accept-Language", "zh-cn") };

		RequestParams params = new RequestParams();
		params.put("devicetoken", AVInstallation.getCurrentInstallation()
				.getInstallationId());
		HttpUtil.getClient().post(getActivity(), url.toString(), headers, params,
				"text/html", new MyJsonHttpResponseHandler() {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject error) {

						handleHttpFailure();
					}

					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {

						Gson gson = new Gson();
						ArticlesBean bean = gson.fromJson(data,
								ArticlesBean.class);
//						LocalStateDA.getInst(getActivity()).setArticle(
//								LocalStateDA.PREFIX_CHANNEL_ITEM,
//								bean.getTid(), SerializeUtil.serialize(bean));
						getHandler().obtainMessage(MSG_DATA_READY, bean)
								.sendToTarget();
					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {

						UIHelper.showToast(getActivity(), message);
						mLoadStateManager.setState(LoadState.Failure);
					}
				});
	}

}
