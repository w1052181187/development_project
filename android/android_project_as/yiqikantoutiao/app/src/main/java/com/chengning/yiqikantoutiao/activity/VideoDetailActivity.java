package com.chengning.yiqikantoutiao.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.SerializeUtil;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.Consts.ArticleType;
import com.chengning.yiqikantoutiao.LoadStateManager;
import com.chengning.yiqikantoutiao.LoadStateManager.LoadState;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.data.access.LocalStateDA;
import com.chengning.yiqikantoutiao.data.bean.ArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.BaseArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.event.CollectEvent;
import com.chengning.yiqikantoutiao.event.CommentSuccessEvent;
import com.chengning.yiqikantoutiao.util.ArticleContentUtil;
import com.chengning.yiqikantoutiao.util.ArticleManagerUtils;
import com.chengning.yiqikantoutiao.util.ArticleManagerUtils.DataStateListener;
import com.chengning.yiqikantoutiao.util.ArticleVideoView;
import com.chengning.yiqikantoutiao.util.ArticleVideoView.FullScreenShowListener;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.util.UmengShare;
import com.chengning.yiqikantoutiao.widget.ArticleCommentBottom;
import com.chengning.yiqikantoutiao.widget.ArticleCommentBottom.FavChangeListenner;
import com.chengning.yiqikantoutiao.widget.ArticleReadFinishDialog;
import com.chengning.yiqikantoutiao.widget.NoFocusingBottomListenerScrollView;
import com.chengning.yiqikantoutiao.widget.PicArticleDialog;
import com.chengning.yiqikantoutiao.widget.ProgressRefreshView;
import com.chengning.yiqikantoutiao.widget.TitleBar;
import com.chengning.yiqikantoutiao.widget.TitleBar.BackOnClickListener;
import com.chengning.yiqikantoutiao.widget.VideoArticleRelativeBottom;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class VideoDetailActivity extends BaseFragmentActivity {

	private static final String TAG = VideoDetailActivity.class.getSimpleName();

	private static final int MSG_DATA_READY = 1;
	private static final int LOAD_VIDEO_FINISH = 2;
	private static final int READ_FINISHED = 3;

	private TitleBar mTitleBar;
	private View mCoverLayout;
	private ProgressRefreshView mProgressRefresh;
	private TextView mArticleTitle;
	private TextView mArticleTime;
	private View mShangTouTiaoView;
	private TextView mShangTouTiaoTv;
	private View mBuXiHuanView;
	private TextView mBuXiHuanTv;
	private RelativeLayout mArticleContentLayout;
	private ArticleCommentBottom mArticleComment;
	private VideoArticleRelativeBottom mArticleRelative;

	private ArticleVideoView mArticleVideoView;

	private boolean mIsFromPush;
	private BaseArticlesBean mBean;
	private ArticlesBean mArticleBean;

	private Activity mContext;
	private LoadStateManager mLoadStateManager;

	private View mRoot;

	private App app;

	private boolean isFavChange = false;

	private boolean isFavState;

	private ArticleManagerUtils mArticleManagerUtils;

	private RelativeLayout contentR;

	private NoFocusingBottomListenerScrollView mScrollView;

	private View mCmtToolbar;

	private int mScreenWidth;

	private ImageView mShareImg;

	private ArticleType mCollectType;

	private int lastNightModel;

	private FavChangeListenner mFavChangeListener;
	private int mScrollY;
	private View mArticleCommentLayout;
	private Timer mTimer;
	private TimerTask mTimerTask;
	private long sTime;
	private DataStateListener mLikeState;
	private DataStateListener mNotLikeState;

	public boolean isFavChange() {
		return isFavChange;
	}

	public void setFavChange(boolean isFavChange) {
		this.isFavChange = isFavChange;
	}

	public static void launch(Activity from, BaseArticlesBean bean) {
		Intent intent = new Intent(from, VideoDetailActivity.class);
		intent.putExtra("bean", bean);
		from.startActivity(intent);
	}

	public static void launchByReqCode(Activity from, BaseArticlesBean bean, ArticleType type) {
		Intent intent = new Intent(from, VideoDetailActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("collect_type", type);
		from.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_video_detail);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putSerializable("bean", mBean);
		savedInstanceState.putBoolean("push", mIsFromPush);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mBean = (BaseArticlesBean) savedInstanceState.getSerializable("bean");
		mIsFromPush = savedInstanceState.getBoolean("push", false);
	}

	@Override
	public BaseFragmentActivity getActivity() {
		return this;
	}

	@Override
	public void initViews() {
		// view
		DisplayUtil.getInst().init(getActivity());
		mRoot = findViewById(R.id.root);
		mTitleBar = new TitleBar(this, true);
		mTitleBar.showDefaultBack();
		mTitleBar.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		mTitleBar.setBackText("", getResources().getColor(R.color.transparent),
				getResources().getDrawable(R.drawable.nav_back_white));
		mTitleBar.setBackTextBackgroundColor(getResources().getColor(R.color.transparent));

		mProgressRefresh = new ProgressRefreshView(getActivity(), true);
		mCoverLayout = findViewById(R.id.cover_layout);
		mArticleTitle = (TextView) findViewById(R.id.article_content_top_title);
		mArticleTime = (TextView) findViewById(R.id.article_content_top_time);
		mShangTouTiaoView = findViewById(R.id.article_content_bottom_zan_layout);
		mShangTouTiaoTv = (TextView) findViewById(R.id.article_content_bottom_zan_text_view);
		mBuXiHuanView = findViewById(R.id.article_content_bottom_cai_layout);
		mBuXiHuanTv = (TextView) findViewById(R.id.article_content_bottom_cai_text_view);
		mShareImg = (ImageView) findViewById(R.id.article_content_bottom_share_img);
		mArticleContentLayout = (RelativeLayout) findViewById(R.id.article_content_content_layout);
		mArticleCommentLayout = findViewById(R.id.article_comment_bottom_layout);

		mFavChangeListener = new FavChangeListenner() {

			@Override
			public void setChange(boolean isFaved) {
				isFavState = isFaved;
				setFavChange(!isFavState);
			}
		};

		mScrollView = (NoFocusingBottomListenerScrollView) findViewById(R.id.article_content_scroll);
		mCmtToolbar = findViewById(R.id.article_comment_bottom_toolbar);

	}

	@Override
	public void initDatas() {
		app = App.getInst();
		mContext = this;
		UmengShare.getInstance().checkInit(mContext);

		// data
		mBean = (BaseArticlesBean) getIntent().getSerializableExtra("bean");
		mCollectType = (ArticleType) getIntent().getSerializableExtra(
				"collect_type");

		mIsFromPush = getIntent().getBooleanExtra("push", false);

		lastNightModel = SettingManager.getInst().getNightModel();

		
		initLoadState();

		initContent();

		mScreenWidth = DisplayUtil.getInst().getScreenWidth();
		
		initBottom();
		EventBus.getDefault().register(getActivity());
		initTimer();
	}

	private void initTimer(){
		if (mTimer == null) {
			mTimer = new Timer();
		}

		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					getHandler().obtainMessage(READ_FINISHED).sendToTarget();
				}
			};
		}
	}

	private void initBottom() {
		mArticleComment = new ArticleCommentBottom(this, mRoot,
				mFavChangeListener);
		mArticleRelative = new VideoArticleRelativeBottom(this, mRoot);
		mArticleComment.setChannelBean(mBean, Consts.ArticleCommentType.ARTICLE);
		mArticleComment.setCommentClickListener(new Runnable() {
			@Override
			public void run() {
				int top = mArticleCommentLayout.getTop();
				mScrollView.smoothScrollTo(0, top == mScrollY ? 0 : top);
			}
		});
		mArticleRelative.setChannelBean(mBean);

	}

	private void initContent() {
		if (mIsFromPush) {
			LocalStateDA.getInst(getActivity()).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM, mBean.getTid());
		}
		mLikeState = new DataStateListener() {

			@Override
			public void success(Object... objects) {
				if (objects == null) {
					return;
				}
				String digCounts = (String) objects[0];
				handleZanTV(Common.trimZero(digCounts));
				mShangTouTiaoTv.setSelected(true);
				mShangTouTiaoTv.setEnabled(true);
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
				if (objects == null) {
					return;
				}
				String digCounts = (String) objects[0];
				handleNotLikeTV(Common.trimZero(digCounts));
				mBuXiHuanTv.setSelected(true);
				mBuXiHuanTv.setEnabled(true);
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
		// listener
		mTitleBar.setBackOnClickListener(new BackOnClickListener() {

			@Override
			public void onClick() {
				if (!handleBackClick()) {
					finish();
				}
			}
		}, false);

		mProgressRefresh.setRefreshOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getDataByHttp();
			}
		});

		mShangTouTiaoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mArticleBean == null || mArticleManagerUtils == null) {
					return;
				}
				mArticleManagerUtils.doLike(getActivity(), mArticleBean, mLikeState);
			}
		});

		mBuXiHuanView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mArticleBean == null || mArticleManagerUtils == null) {
					return;
				}
				mArticleManagerUtils.doNotLike(getActivity(), mArticleBean, mNotLikeState);
			}
		});
		mShareImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					PicArticleDialog dialog = new PicArticleDialog();
					dialog.setBean(mArticleBean);
					dialog.show(getSupportFragmentManager(),
							PicArticleDialog.class.getSimpleName());
				}
			}
		});

		mScrollView.setOnScrollToBottomLintener(new NoFocusingBottomListenerScrollView.OnScrollToBottomListener() {
			@Override
			public void onScrollBottomListener(boolean isBottom) {
				mScrollView.setLoading();
				mArticleComment.getNextPageComment(getActivity(), new DataStateListener() {

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

	/**
	 * 处理返回点击
	 */
	protected boolean handleBackClick() {
		if (mArticleVideoView == null) {
			return false;
		}
		if (!mArticleVideoView.onBackPressed()) {
			if (isFavChange()) {
				setResultOk();
			}
			if (mIsFromPush) {
				MainActivity.launch(mContext);
			}
			return false;
		}
		return true;
	}

	protected void setResultOk() {
		if (mCollectType != null) {
			CollectEvent result = new CollectEvent();
			result.setFavState(isFavState ? Common.TRUE : Common.FALSE);
			result.setArticleType(mCollectType);
			EventBus.getDefault().post(result);
		}
	}

	/**
	 * 设置白天黑夜模式
	 *
	 * @param isChecked
	 */
	private void setDayAndNightModel(boolean isChecked) {
		app.setNightModelChange(true);
		// app.setmNightModel(isChecked ? Common.TRUE : Common.FALSE);
		SettingManager.getInst().saveNightModel(
				isChecked ? Common.TRUE : Common.FALSE);
	}

	@SuppressLint("NewApi")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case MSG_DATA_READY:
			// data ready
			mArticleBean = (ArticlesBean) msg.obj;

			//分享时候用的图片
			mArticleBean.setImage_list(mArticleBean.getVideo_photo());

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
			mArticleContentLayout.removeAllViews();

			// content
			if (mArticleVideoView != null) {
				mArticleVideoView.destroy();
			}
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				// Call requires API level 11
				mArticleVideoView = new ArticleVideoView();
				mArticleVideoView
						.setFullScreenListener(new FullScreenShowListener() {

							@Override
							public void showFullScreen() {
								setFullScreen(true);
							}

							@Override
							public void hideFullScreen() {
								setFullScreen(false);
							}
						});
				contentR = mArticleVideoView.build(this, mArticleBean,
						new ArticleVideoView.OnWebViewVideoListener() {

							@Override
							public void onPageStarted() {
								mLoadStateManager.setState(LoadState.Init);
							}

							@Override
							public void onPageFinished() {
								// webview 延迟显示避免闪屏
								getHandler().postDelayed(
										new Runnable() {

											@Override
											public void run() {
												getHandler().obtainMessage(LOAD_VIDEO_FINISH).sendToTarget();
											}
										},
										Common.isTrue(SettingManager.getInst()
												.getNightModel()) ? 500 : 20);
							}

							@Override
							public void onTimeout() {
								onPageFinished();
							}

						}, getHandler());
				// contentR.setFocusable(false);
				// mArticleWebView.getWebView().setFocusable(false);
				mArticleContentLayout.addView(contentR);
			} else {
				ArticleContentUtil<String> mArticleContentView = new ArticleContentUtil<String>();
				mArticleContentView.setChannelBean(mBean);
				View contentR = mArticleContentView.build(this, mArticleBean);
				contentR.setFocusable(false);
				mArticleContentLayout.addView(contentR);
				mLoadStateManager.setState(LoadState.Success);
			}
//			mLikeTextView.setText(String.valueOf(mArticleBean.getDigcounts()));
//			mLikeTextView.setSelected(Common.isTrue(mArticleBean.getIs_dig()));

			mArticleBean.setContent_type(mBean.getContent_type());
			mArticleComment.setData(mArticleBean);
			mArticleComment.setIsHasShare(true);
			mArticleRelative.setData(mArticleBean);
			mArticleRelative.setTitle("相关视频");
			sTime = Common.getCurTimeMillis();
			if(mTimer != null && mTimerTask != null && mArticleBean != null) {
				try {
					mTimer.schedule(mTimerTask, (long)(mArticleBean.getView_second() * 1000));
//					mTimer.schedule(mTimerTask, 3000 *60);
				} catch (Exception e) {
				}

			}
			break;
		case LOAD_VIDEO_FINISH:
			mLoadStateManager
					.setState(LoadState.Success);
			break;
		case READ_FINISHED:
			handleReadFinish();
			break;
		default:
			break;
		}
	}

	/**
	 * 阅读完成
	 */
	private void handleReadFinish() {

		float diff = Common.getCurTimeMillis() - sTime;
		String encodeStr = null;
		try {
			StringBuilder builder = new StringBuilder(mArticleBean.getTid());
			builder.append("|").append(String.valueOf(diff / 1000));
			encodeStr = Common.encrypt(builder.toString(), Consts.ENCODE_KEY);
			RequestParams params = new RequestParams();
			params.put("key",encodeStr);
			HttpUtil.get(getActivity(), JUrl.SITE + JUrl.URL_VIDEO_READ_FINISH, params, new MyJsonHttpResponseHandler() {
				@Override
				public void onDataSuccess(int status, String mod, String message, String data, JSONObject obj) {

					try {
						JSONObject object = new JSONObject(data);
						String amount = object.getString("amount");
						if (!TextUtils.isEmpty(amount) && TextUtils.isDigitsOnly(amount) && Integer.valueOf(amount) != 0) {
							ArticleReadFinishDialog dialog = new ArticleReadFinishDialog();
							dialog.setData(amount);
							dialog.show(getSupportFragmentManager(), ArticleReadFinishDialog.class.getSimpleName());
							dialog.cancle(getHandler());
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

				@Override
				public void onDataFailure(int status, String mod, String message, String data, JSONObject obj) {

				}
			});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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

	/**
	 * 设置全屏试图相关试图可见性
	 *
	 * @param isFullScreen
	 */
	protected void setFullScreen(boolean isFullScreen) {
		mScrollView.setVisibility(isFullScreen ? View.GONE : View.VISIBLE);
		mCmtToolbar.setVisibility(isFullScreen ? View.GONE : View.VISIBLE);
		if (isFullScreen) {
			WindowManager.LayoutParams attrs = getWindow().getAttributes();
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			getWindow().setAttributes(attrs);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

			RelativeLayout.LayoutParams rlp = (LayoutParams) contentR
					.getLayoutParams();
			rlp.height = mScreenWidth;
			contentR.setLayoutParams(rlp);
		} else {
			WindowManager.LayoutParams attrs = getWindow().getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setAttributes(attrs);
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

			RelativeLayout.LayoutParams rlp = (LayoutParams) contentR
					.getLayoutParams();
			rlp.height = mScreenWidth * 9 / 16;
			contentR.setLayoutParams(rlp);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (SettingManager.getInst().getNightModel() != lastNightModel) {
			finish();
			if (null != mBean) {
				launch(getActivity(), mBean);
			}

		}

		mArticleComment.updateCollectState(mArticleBean);

		if (mArticleVideoView != null) {
			mArticleVideoView.onResume();
		}
	}
	
	@Subscribe
    public void onEventMainThread(CommentSuccessEvent event) {
		mArticleBean.getReply_list().add(event.getmBean());
		mArticleBean.setReplys(mArticleBean.getReplys() + 1);
		mArticleComment.setData(mArticleBean);
    }

	@Override
	public void onPause() {
		super.onPause();
		if (mArticleVideoView != null) {
			mArticleVideoView.onPause();
		}
	}

	@Override
	public void onDestroy() {
		if (mArticleVideoView != null) {
			mArticleContentLayout.removeAllViews();
			mArticleVideoView.destroy();
		}

		EventBus.getDefault().unregister(getActivity());
		if (mArticleComment != null) {
			mArticleComment.destory();
		}
		stopTimer();
		super.onDestroy();
	}

	private void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
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
		HttpUtil.getClient().post(this, url.toString(), headers, params,
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
						LocalStateDA.getInst(getActivity()).setArticle(
								LocalStateDA.PREFIX_CHANNEL_ITEM,
								bean.getTid(), SerializeUtil.serialize(bean));
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

	@Override
	public void onBackPressed() {
		try {
			if (!handleBackClick()) {
				super.onBackPressed();
			}

		} catch (IllegalStateException ignored) {

		}

	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
		case ArticleContentUtil.REQUEST_PHOTO_PAGE_CODE:
			if (Activity.RESULT_OK == arg1) {
				if (null == arg2) {
					return;
				} else {
					int state = arg2.getExtras().getInt("favState", -1);
					// isFavState = Common.isTrue(state);
					mArticleComment.setCollectState(Common.isTrue(state));
					mArticleBean.setIs_favor(state);
					// setFavChange(!isFavState);
					// mFavChangeListener.setChange(Common.isTrue(state));
				}

			}
			break;

		default:
			break;
		}
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}


}
