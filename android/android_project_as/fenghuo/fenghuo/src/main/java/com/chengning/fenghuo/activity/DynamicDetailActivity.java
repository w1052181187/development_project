package com.chengning.fenghuo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.base.util.BaseUmengShare;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.Consts.ArticleType;
import com.chengning.fenghuo.LoadStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.adapter.DynamicCommentItemAdapter;
import com.chengning.fenghuo.adapter.LikeAdapter;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.CommentItemBean;
import com.chengning.fenghuo.data.bean.CommentListBean;
import com.chengning.fenghuo.data.bean.DigBean;
import com.chengning.fenghuo.data.bean.DynamicDetailBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.Image;
import com.chengning.fenghuo.event.CollectEvent;
import com.chengning.fenghuo.event.CommentSuccessEvent;
import com.chengning.fenghuo.util.ArticleContentUtil;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.ArticleManagerUtils.DeleteListener;
import com.chengning.fenghuo.util.ArticleManagerUtils.DataStateListener;
import com.chengning.fenghuo.util.ArticleWebView;
import com.chengning.fenghuo.util.ArticleWebView.OnWebViewListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.util.UmengShare;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.ArticleCommentBottom.FavChangeListenner;
import com.chengning.fenghuo.widget.CommentInputDialog;
import com.chengning.fenghuo.widget.DeleteDynamicDialog;
import com.chengning.fenghuo.widget.DynamicCommentBottom;
import com.chengning.fenghuo.widget.DynamicCommentInputDialog;
import com.chengning.fenghuo.widget.DynamicSettingDialog;
import com.chengning.fenghuo.widget.NoScrollGridView;
import com.chengning.fenghuo.widget.PicArticleDialog;
import com.chengning.fenghuo.widget.ProgressRefreshView;
import com.chengning.fenghuo.widget.PullToRefreshListView_FootLoad;
import com.chengning.fenghuo.widget.RemovePhoneBindDialog.RemovePhoneBindOkLitener;
import com.chengning.fenghuo.widget.TitleBar;
import com.chengning.fenghuo.widget.TitleBar.BackOnClickListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

@SuppressLint("ResourceAsColor")
public class DynamicDetailActivity extends BaseFragmentActivity {

	private static final int MSG_DATA_READY = 1;

	private static final int SUCCESS_COMMENT_DATA = 2;

	private static final int SUCCESS_COMMENT_MORE_DATA = 3;

	private View mRoot;
	private TitleBar mTitleBar;
	private ProgressRefreshView mProgressRefresh;
	private View mCoverLayout;
	private ImageView mAvatar;
	private TextView mDynamicName;
	private TextView mDynamicFrom;
	private TextView mDynamicTime;
	private TextView mDynamicTitle;
	private RelativeLayout mArticleContentLayout;
	private TextView mLikeTextView;
	private TextView mLinkTextView;
	private TextView mDynamicBottomTime;
	private Button mDynamicBottomCommentBtn;
	private View mDynamicBottomShareWxFriend;
	private View mDynamicBottomShareWxCircle;
	private View mDynamicBottomShareWeibo;
	private View mDynamicTriangle;
	private View mDynamicZanLayout;
	private View mLikeGrivdViewLine;
	private ArticleWebView<ArrayList<Image>> mDynamicWebView;
	private ArticleContentUtil<ArrayList<Image>> mArticleContentView;

	/**
	 * 列表bean
	 */
	private static DynamicItemBean mBean;
	private DynamicDetailBean mDynamicBean;

	private DynamicSettingDialog mSettingDialog;

	private LoadStateManager mLoadStateManager;
	private ArticleManagerUtils mArticleManagerUtils;

	private App app;

	private boolean isFavChange = false;

	private boolean isFavState;

	private DynamicCommentBottom mDynamicComment;

	private boolean mIsFromPush;

	private BroadcastReceiver mShareSuccessReceiver;
	private Runnable mShareSuccessRunnable;

	private FavChangeListenner mFavChangeListener;

	private TextView mReplyTextView;

	private boolean isSelfDynamic;

	private TextView mFollowBtn;

	private PullToRefreshListView_FootLoad mListView;

	private int mTarPage = JUrl.PAGE_START;

	protected boolean hasNextPage = false;

	private View mHeader;

	private List mReplyList;

	private DynamicCommentItemAdapter mAdapter;

	private ArticleType mCollectType;

	private static DynamicDetailActivity mContext;
	
	public boolean isFavChange() {
		return isFavChange;
	}

	public void setFavChange(boolean isFavChange) {
		this.isFavChange = isFavChange;
	}

	public static void launch(Activity from, BaseArticlesBean bean) {
		Intent intent = new Intent(from, DynamicDetailActivity.class);
		intent.putExtra("bean", bean);
		from.startActivity(intent);
	}

	public static void launchByReqCode(Activity from, BaseArticlesBean bean,
			ArticleType type) {
		Intent intent = new Intent(from, DynamicDetailActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("collect_type", type);
		from.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_dynamic_detail);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		mRoot = findViewById(R.id.root);
		mTitleBar = new TitleBar(this, true);
		mProgressRefresh = new ProgressRefreshView(getActivity(), true);
		mCoverLayout = findViewById(R.id.cover_layout);

		mHeader = getLayoutInflater().inflate(R.layout.header_dynamic_detail,
				null);
		initHeader(mHeader);

		mTitleBar.setTitle("圈子详情");
		mTitleBar.showDefaultBack();
		mTitleBar.setRightButton(R.drawable.nav_more);

		mListView = (PullToRefreshListView_FootLoad) findViewById(R.id.list);
		mListView.setMode(Mode.DISABLED);
		mListView.getRefreshableView().addHeaderView(mHeader);
	}

	private void initHeader(View view) {
		mAvatar = (ImageView) view.findViewById(R.id.dynamic_detail_top_avatar);
		mDynamicName = (TextView) view
				.findViewById(R.id.dynamic_detail_top_name);
		mDynamicFrom = (TextView) view
				.findViewById(R.id.dynamic_detail_top_from);
		mDynamicTime = (TextView) view
				.findViewById(R.id.dynamic_detail_top_time);
		mFollowBtn = (TextView) view
				.findViewById(R.id.dynamic_detail_follow_btn);
		mDynamicTitle = (TextView) view
				.findViewById(R.id.dynamic_detail_title_tv);
		mArticleContentLayout = (RelativeLayout) view
				.findViewById(R.id.dynamic_detail_content_layout);
		mLikeTextView = (TextView) view
				.findViewById(R.id.article_content_bottom_zan_text_view);
		mReplyTextView = (TextView) view
				.findViewById(R.id.article_content_bottom_report_text_view);
		mLinkTextView = (TextView) view
				.findViewById(R.id.dynamic_detail_showlink_tv);
		mDynamicBottomTime = (TextView) view
				.findViewById(R.id.article_content_bottom_time_text_view);
		mDynamicBottomCommentBtn = (Button) view
				.findViewById(R.id.article_content_bottom_comment_btn);
		mDynamicBottomShareWxFriend = view
				.findViewById(R.id.article_content_bottom_share_wx_friend);
		mDynamicBottomShareWxCircle = view
				.findViewById(R.id.article_content_bottom_share_wx_circle);
		mDynamicBottomShareWeibo = view
				.findViewById(R.id.article_content_bottom_share_weibo);
		mDynamicTriangle = view
				.findViewById(R.id.dynamic_detail_triangle);
		mLikeGrivdViewLine = view
				.findViewById(R.id.dynamic_detail_comment_link_scroll_line);
	}

	@Override
	public void initDatas() {
		mContext = this;
		app = App.getInst();
		DisplayUtil.getInst().init(getActivity());
		UmengShare.getInstance().checkInit(getActivity());

		mBean = (DynamicItemBean) getIntent().getSerializableExtra("bean");
		mCollectType = (ArticleType) getIntent().getSerializableExtra("collect_type");
		mIsFromPush = getIntent().getBooleanExtra("push", false);

		initLoadState();
		initBottomComment();
		initContent();
		
		EventBus.getDefault().register(this);
		
	}

	/**
	 * 初始化内容
	 */
	private void initContent() {
		if (mIsFromPush) {
			LocalStateDA.getInst(getActivity()).setReadStateRead(
					LocalStateDA.PREFIX_CHANNEL_ITEM, mBean.getTid());
		}
		mArticleManagerUtils = new ArticleManagerUtils();

		if (Common.hasNet()) {
			getDataByHttp();
		} else {
			boolean isArticleSaved = false;
			String aStr = LocalStateDA
					.getInst(getActivity())
					.queryLocalStateNotNull(LocalStateDA.PREFIX_CHANNEL_ITEM,
							mBean.getTid()).getData_item_article();
			if (!TextUtils.isEmpty(aStr)) {
				try {
					ArticlesBean aBean = SerializeUtil.deSerialize(aStr,
							ArticlesBean.class);
					if (!TextUtils.isEmpty(aBean.getTid())) {
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

		mShareSuccessReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(Consts.ACTION_WX_SHARE_SUCESS)) {
					final String data = intent.getStringExtra("data");
					if (null != data) {
						mShareSuccessRunnable = new Runnable() {

							@Override
							public void run() {
								try {
									Utils.showHints(getActivity(), data);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						};
					}
				}
			}
		};
		IntentFilter intentFileter = new IntentFilter();
		intentFileter.addAction(Consts.ACTION_WX_SHARE_SUCESS);
		intentFileter.setPriority(200);
		registerReceiver(mShareSuccessReceiver, intentFileter);
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
							case Init :
								mProgressRefresh.setWaitVisibility(true);
								mProgressRefresh.setRefreshVisibility(false);
								mCoverLayout.setVisibility(View.VISIBLE);
								break;
							case Success :
								mProgressRefresh.setRootViewVisibility(false);
								mCoverLayout.setVisibility(View.INVISIBLE);
								break;
							case Failure :
								mProgressRefresh.setWaitVisibility(false);
								mProgressRefresh.setRefreshVisibility(true);
								mCoverLayout.setVisibility(View.VISIBLE);
								break;
							default :
								break;
						}
					}
				});
		mLoadStateManager.setState(LoadState.Init);
	}

	/**
	 * 初始化底部评论
	 */
	private void initBottomComment() {
		mFavChangeListener = new FavChangeListenner() {

			@Override
			public void setChange(boolean isFaved) {
				isFavState = isFaved;
				setFavChange(!isFavState);
				mDynamicBean.setIs_favor(isFaved ? 1 : 0);
			}
		};

		mDynamicComment = new DynamicCommentBottom(getActivity(), mRoot);
		mReplyList = new ArrayList<CommentItemBean>();
		mAdapter = new DynamicCommentItemAdapter(getActivity(), mReplyList);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void installListeners() {
		mAvatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDynamicBean != null)
					UserInfoActivity.launch(mContext,
							mDynamicBean.getNickname());
			}
		});
		mTitleBar.setBackOnClickListener(new BackOnClickListener() {

			@Override
			public void onClick() {
				if (mIsFromPush) {
					MainActivity.launch(getActivity());
				}
				if (isFavChange()) {
					setResultOk();
				}
			}
		});
		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null == mDynamicBean) {
					return;
				}
				mSettingDialog = new DynamicSettingDialog();
				mBean.setIs_favor(mDynamicBean.getIs_favor());
				mSettingDialog.setData(mBean, mFavChangeListener);
				mSettingDialog.show(getSupportFragmentManager(),
						DynamicSettingDialog.class.getSimpleName());
				// 小米手机上UI概率显示不全，强制延时刷新UI
				getHandler().postDelayed(new Runnable() {

					@Override
					public void run() {
						if (mSettingDialog != null) {
							mSettingDialog.shouldRefreshUI();
						}
					}
				}, 100);

			}
		});

		mProgressRefresh.setRefreshOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadState.Init);
				getDataByHttp();
			}
		});

		mFollowBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				follow(mDynamicBean.getUid());
			}
		});

		mListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader
				.getInstance(), false, true));
		mListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				if (hasNextPage) {
					mListView.setFootLoading();
					getNextPageComment(mTarPage);
				}
			}
		});

		mListView.getRefreshableView().setOnItemLongClickListener(
				new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						if (-1 == id) {
							return true;
						}
						View v = view
								.findViewById(R.id.item_article_comment_top_user_image);
						CommentItemBean bean = (CommentItemBean) v.getTag();
						// 有header 所以-2
						showDeleteDialog(bean, position - 2);
						return true;
					}
				});
		
		mDynamicBottomCommentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopupwindow(v);
			}
		});
		mDynamicBottomShareWxFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmengShare.getInstance().shareToWeixin(getActivity(), mDynamicBean);
			}
		});
		mDynamicBottomShareWxCircle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmengShare.getInstance().shareToCircle(getActivity(), mDynamicBean);
			}
		});
		mDynamicBottomShareWeibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmengShare.getInstance().shareToWeibo(getActivity(), mDynamicBean,new BaseUmengShare.BaseUMShareListener(getActivity()) {
				    @Override
				    public void onResult(SHARE_MEDIA platform) {
				    	if (App.getInst().isLogin()) {
							UmengShare.getInstance().getPointAndMoneyByWeibo(getActivity(), mBean);
						}
				    	super.onResult(platform);
				    }

				}); 
			}
		});
	}

	/**
	 * 显示删除对话框
	 * 
	 * @param bean
	 * @param pos
	 */
	protected void showDeleteDialog(final CommentItemBean bean, final int pos) {
		if (!LoginManager.getInst().checkLoginWithNotice(getActivity())) {
			return;
		}
		// 楼主或者自己才可以删
		boolean isCanDelete = bean.getUid().equals(
				App.getInst().getUserInfoBean().getUid())
				|| isSelfDynamic;
		if (isCanDelete) {
			DeleteDynamicDialog dialog = new DeleteDynamicDialog();
			dialog.setData(new RemovePhoneBindOkLitener() {

				@Override
				public void confirm() {
					mArticleManagerUtils.deleteDynamic(getActivity(), bean.getTid(), new DeleteListener() {

								@Override
								public void deleteSuccess(String data) {
									mReplyList.remove(pos);
									mAdapter.notifyDataSetChanged(isSelfDynamic);
								}
							});
				}

				@Override
				public void cancle() {
				}
			});
			dialog.show(getSupportFragmentManager(),
					DeleteDynamicDialog.class.getSimpleName());
		}

	}

	/**
	 * 关注
	 * 
	 * @param uid
	 */
	protected void follow(String uid) {
		if (TextUtils.isEmpty(uid)) {
			return;
		}
		ArticleManagerUtils.followUser(getActivity(), uid,
				new Runnable() {

					@Override
					public void run() {
						mFollowBtn.setVisibility(View.GONE);
					}
				});
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
		mBean = (DynamicItemBean) savedInstanceState.getSerializable("bean");
		mIsFromPush = savedInstanceState.getBoolean("push", false);
	}

	private void handleHttpFailure() {
		Common.showHttpFailureToast(getActivity());
		getHandler().post(new Runnable() {

			@Override
			public void run() {
				mLoadStateManager.setState(LoadState.Failure);
			}
		});
	}

	private void getDataByHttp() {
		final String aid = String.valueOf(mBean.getTid());
		String url = JUrl.SITE + JUrl.URL_GET_DYNAMICS_SUBCSRIBE_TUWEN + aid;
		BasicHeader header = new BasicHeader("Accept-Encoding", "gzip, deflate");
		Header[] headers = {new BasicHeader("Accept", "*/*"), header,
				new BasicHeader("Accept-Language", "zh-cn")};
		HttpUtil.getClient().get(getActivity(), url, headers, null,
				new MyJsonHttpResponseHandler() {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject error) {

						handleHttpFailure();
					}

					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {

						Gson gson = new Gson();
						DynamicDetailBean bean = gson.fromJson(data,
								DynamicDetailBean.class);
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
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
			case ArticleContentUtil.REQUEST_PHOTO_PAGE_CODE :
				if (Activity.RESULT_OK == arg1) {
					if (null == arg2) {
						return;
					} else {
						int state = arg2.getExtras().getInt("favState", -1);
						mDynamicBean.setIs_favor(state);
					}

				}
				break;

			default :
				break;
		}
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);

	}

	@Override
	public BaseFragmentActivity getActivity() {
		return DynamicDetailActivity.this;
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
			case MSG_DATA_READY :
				mDynamicBean = (DynamicDetailBean) msg.obj;
//				Utils.setCircleImage(mDynamicBean.getFace(), mAvatar);
				ImageLoader.getInstance().displayImage(mDynamicBean.getFace(), mAvatar);
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					mAvatar.setColorFilter(
							getResources().getColor(R.color.night_img_color),
							PorterDuff.Mode.MULTIPLY);
				}
				Common.handleUserNameDisplay(getActivity(), mDynamicBean,
						mDynamicName);
				if (!TextUtils.isEmpty(mDynamicBean.getFrom())) {
					mDynamicFrom.setText(mDynamicBean.getFrom());

					mDynamicFrom.setVisibility(View.GONE);
				} else {
					mDynamicFrom.setVisibility(View.GONE);
				}
				mDynamicTime.setText(Common.getDateMMDDHHMMNotNull(mDynamicBean
						.getDateline()));
				mDynamicBottomTime.setText(Common.getDateMMDDHHMMNotNull(mDynamicBean
						.getDateline()));
				mDynamicBottomTime.setVisibility(View.INVISIBLE);
				if (TextUtils.isEmpty(mDynamicBean.getTitle())) {
					mDynamicTitle.setVisibility(View.GONE);
				} else {
					mDynamicTitle.setText(mDynamicBean.getTitle());
				}
				mFollowBtn.setVisibility(Common.isTrue(mDynamicBean
						.getIs_follow()) ? View.GONE : View.VISIBLE);
				
				if (!TextUtils.isEmpty(mDynamicBean.getRedirecttitle())) {
					mLinkTextView.setVisibility(View.VISIBLE);
					mLinkTextView.setText(mDynamicBean.getRedirecttitle());
				} else {
					mLinkTextView.setVisibility(View.GONE);
				}
				mLinkTextView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						AdDetailActivity.launch(mContext, AdDetailActivity.TYPE_ARTICLE, mDynamicBean.getRedirecturl(), mDynamicBean.getRedirecturl());
					}
				});
				
				// content
				mArticleContentLayout.removeAllViews();
				if (null != mDynamicWebView) {
					mDynamicWebView.destroy();
				}
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
					// Call requires API level 11
					mDynamicWebView = new ArticleWebView<ArrayList<Image>>();
					mDynamicWebView.setChannelBean(mBean);
					View contentR = mDynamicWebView.build(this, mDynamicBean,
							new OnWebViewListener() {

								@Override
								public void onPageStarted() {
									mLoadStateManager.setState(LoadState.Init);
								}

								@Override
								public void onPageFinished() {
									// webview 延迟显示避免闪屏
									getHandler().postDelayed(new Runnable() {

										@Override
										public void run() {
											mLoadStateManager
													.setState(LoadState.Success);
										}
									}, Common.isTrue(SettingManager.getInst().getNightModel()) ? 500
											: 20);
								}

								@Override
								public void onTimeout() {
									onPageFinished();
								}
							}, getHandler());
					contentR.setFocusable(false);
					mDynamicWebView.getWebView().setFocusable(false);
					mArticleContentLayout.addView(contentR);
				} else {
					mArticleContentView = new ArticleContentUtil<ArrayList<Image>>();
					mArticleContentView.setChannelBean(mBean);
					View contentR = mArticleContentView.build(this,
							mDynamicBean);
					contentR.setFocusable(false);
					mArticleContentLayout.addView(contentR);
					mLoadStateManager.setState(LoadState.Success);
				}

				mLikeTextView.setText(String.valueOf(mDynamicBean
						.getDigcounts()));
				isSelfDynamic = app.isLogin()
						&& mDynamicBean.getUid().equals(
								app.getUserInfoBean().getUid());

				mReplyTextView
						.setText(String.valueOf(mDynamicBean.getReplys()));

//				if (!app.isLogin()) {
//					mDynamicBean.setImage_list(mBean.getImage_list());
//				}
				
				DataStateListener mState = new ArticleManagerUtils.DataStateListener() {
					@Override
					public void success(Object... objects) {
						if (objects == null) {
							return;
						}
						if(popZan != null){
							popZan.setEnabled(true);
						}
						
						boolean isDig = Common.isTrue(mDynamicBean.getIs_dig());
						ArrayList<DigBean> digList = mDynamicBean.getDig_list();
						
						mDynamicComment.getLikeButton().setSelected(true);
						mDynamicComment.getLikeButton().setEnabled(true);
						mDynamicBean.setIs_dig(isDig ? 0 : 1);
						int digCount = mDynamicBean.getDigcounts();
						if (isDig) {
							if (digCount > 0) {
								mDynamicBean.setDigcounts(digCount-1);
							}
						} else {
							mDynamicBean.setDigcounts(digCount+1);
						}
						if (digList != null) {
							if (!isDig) {
								DigBean bean = new DigBean();
								bean.setNickname(App.getInst().getUserInfoBean().getNickname());
								bean.setUid(App.getInst().getUserInfoBean().getUid());
								bean.setFace(App.getInst().getUserInfoBean().getFace());
								digList.add(0,bean);
							} else {
								for (DigBean bean : digList) {
									if (bean.getUid().equals(App.getInst().getUserInfoBean().getUid())) {
										digList.remove(bean);
										break;
									}
								}
							}
							mDynamicBean.setDig_list(digList);
						}
						updateScrollLike();
						updateDynamicTriangle();
						updateCommentBottom();
						mLikeAdapter.notifyDataSetChanged();
					}

					@Override
					public void init() {
						mDynamicComment.getLikeButton().setEnabled(false);	
						if(popZan != null){
							popZan.setEnabled(false); 
						}
					}

					@Override
					public void failure() {
						mDynamicComment.getLikeButton().setEnabled(true);
						if(popZan != null){
							popZan.setEnabled(true);
						}
					}
				};		
				mDynamicComment.setLikeState(mState);
				mDynamicComment.setData(mDynamicBean);
				
				mReplyList.clear();
				getHandler().obtainMessage(SUCCESS_COMMENT_DATA,
						mDynamicBean.getReply_list()).sendToTarget();

				break;
			case SUCCESS_COMMENT_DATA :
				List list = (List) msg.obj;
				mReplyList.addAll(list);
				mAdapter.notifyDataSetChanged(isSelfDynamic);
				if (mReplyList.size() < mDynamicBean.getReplys2()) {
					mTarPage += 1;
					hasNextPage = true;
				} else {
					hasNextPage = false;
				}
				if (mDynamicBean.getReplys() == 0) {
					View mFooter = getLayoutInflater().inflate(
							R.layout.footer_dynamic_detail, null);
					mListView.getRefreshableView().addFooterView(mFooter);
					mLikeGrivdViewLine.setVisibility(View.INVISIBLE);
				}else{
					mLikeGrivdViewLine.setVisibility(View.VISIBLE);
				}
				initScorllLike();
				updateDynamicTriangle();

				break;
			case SUCCESS_COMMENT_MORE_DATA :
				CommentListBean bean = (CommentListBean) msg.obj;
				mReplyList.addAll(bean.getList());
				mAdapter.notifyDataSetChanged(isSelfDynamic);
				if (mTarPage < bean.getTotal_page()) {
					mTarPage++;
					hasNextPage = true;
				} else {
					hasNextPage = false;
				}
				
				handleListViewLoadComplete();
				break;
			default :
				break;
		}
	}

	private void handleListViewLoadComplete() {
		mListView.setFootLoadFull();
		mListView.onRefreshComplete();
	}

	private void getNextPageComment(int page) {
		RequestParams params = new RequestParams();
		params.put("tid", mDynamicBean.getTid());
		params.put("page", page);
		params.put("minid", ((ArrayList<CommentItemBean>) mReplyList).get(0)
				.getTid());
		HttpUtil.get(JUrl.SITE + JUrl.URL_GET_DYNAMIC_COMMENT, params,
				new MyJsonHttpResponseHandler() {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						Common.showHttpFailureToast(getActivity());
						handleListViewLoadComplete();
						super.onFailure(statusCode, headers, throwable,
								errorResponse);
					}

					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {
						Gson gson = new Gson();
						CommentListBean bean = gson.fromJson(data,
								CommentListBean.class);
						getHandler().obtainMessage(SUCCESS_COMMENT_MORE_DATA,
								bean).sendToTarget();
					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {
						UIHelper.showToast(getActivity(), message);
						handleListViewLoadComplete();
					}
				});
	}
	
	private PopupWindow popupWindow;
	private View popView;
	private TextView popShare;
	private TextView popZan;
	private TextView popComment;
	private void showPopupwindow(View v){
		if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            popView = layoutInflater.inflate(R.layout.popupwindow_circle_detail_comment, null);
            popShare = (TextView) popView.findViewById(R.id.poppupwindow_left);
            popZan = (TextView) popView.findViewById(R.id.poppupwindow_middle);
            popComment = (TextView) popView.findViewById(R.id.poppupwindow_right);
            popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        }
		
		popZan.setText(Common.isTrue(mDynamicBean.getIs_dig()) ? "取消" : "赞");
		
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        popView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupHeight =  popView.getMeasuredHeight();
        int popupWidth =  popView.getMeasuredWidth();
        int width = v.getMeasuredWidth();
        int height = v.getMeasuredHeight();
        
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - popupWidth - width / 8, location[1] - height/4);
        
        popShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				PicArticleDialog dialog = new PicArticleDialog();
				dialog.setBean(mDynamicBean);					
				dialog.show(mContext.getSupportFragmentManager(),
						PicArticleDialog.class.getSimpleName());
				popupWindow.dismiss();
            }
        });
        
        popZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				if (null != mDynamicBean) {
					final boolean isDig = Common.isTrue(mDynamicBean.getIs_dig());
					final ArrayList<DigBean> digList = mDynamicBean.getDig_list();
					DataStateListener mState = new DataStateListener() {
						@Override
						public void success(Object... objects) {
							popZan.setEnabled(true);
							mDynamicBean.setIs_dig(isDig ? 0 : 1);
							int digCount = mDynamicBean.getDigcounts();
							if (isDig) {
								if (digCount > 0) {
									mDynamicBean.setDigcounts(digCount-1);
								}
								
							} else {
								mDynamicBean.setDigcounts(digCount+1);
							}
							if (digList != null) {
								if (!isDig) {
									DigBean bean = new DigBean();
									bean.setNickname(App.getInst().getUserInfoBean().getNickname());
									bean.setUid(App.getInst().getUserInfoBean().getUid());
									bean.setFace(App.getInst().getUserInfoBean().getFace());
									digList.add(0,bean);
								} else {
									for (DigBean bean : digList) {
										if (bean.getUid().equals(App.getInst().getUserInfoBean().getUid())) {
											digList.remove(bean);
											break;
										}
									}
								}
								mDynamicBean.setDig_list(digList);
							}
							updateScrollLike();
							updateDynamicTriangle();
							updateCommentBottom();
							mLikeAdapter.notifyDataSetChanged();
						}

						@Override
						public void init() {
							popZan.setEnabled(false); 
						}

						@Override
						public void failure() {
							popZan.setEnabled(true);
						}
					};
					if (!isDig) {
						new ArticleManagerUtils().doLike(mContext, mDynamicBean, mState);
					} else {
						new ArticleManagerUtils().cancleLike(mContext, mDynamicBean, mState);
					}
					popupWindow.dismiss();

				}
            }
        });
        
        popComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				if (null != mDynamicBean && LoginManager.getInst().checkLoginWithNotice(mContext)) {
					DynamicCommentInputDialog dynamicDialog = new DynamicCommentInputDialog();
					dynamicDialog.setBean(mDynamicBean);
					dynamicDialog.show(((BaseFragmentActivity)mContext).getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
					popupWindow.dismiss();
				}
            }
        });
	}
	
	private NoScrollGridView mLikeGridView;
	private LikeAdapter mLikeAdapter;

	private void initScorllLike(){
		mDynamicZanLayout = findViewById(R.id.dynamic_detail_zan_layout);
		mLikeGridView = (NoScrollGridView) findViewById(R.id.dynamic_detail_comment_link_scroll);
		mLikeAdapter = new LikeAdapter(getActivity(), mDynamicBean.getDig_list());
		mLikeGridView.setAdapter(mLikeAdapter);
		mLikeGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UserInfoActivity.launch(getActivity(), mDynamicBean.getDig_list().get(position).getNickname());
			}
		});
		updateScrollLike();
	}
	
	private void updateScrollLike(){
		if(Common.isListEmpty(mDynamicBean.getDig_list())){
			mDynamicZanLayout.setVisibility(View.GONE);
		}else{
			mDynamicZanLayout.setVisibility(View.VISIBLE);
		}
	}
	
	private void updateDynamicTriangle(){
		if(mDynamicBean.getDigcounts() == 0 && mDynamicBean.getReplys() == 0){
			mDynamicTriangle.setVisibility(View.GONE);
		}else{
			mDynamicTriangle.setVisibility(View.VISIBLE);
		}
	}
	
	private void updateCommentBottom(){
		mDynamicComment.setData(mDynamicBean);
	}
	
	protected void setResultOk() {
		
		if (mCollectType != null) {
			CollectEvent result = new CollectEvent();
			result.setFavState(isFavState ? Common.TRUE : Common.FALSE);
			result.setArticleType(mCollectType);
			EventBus.getDefault().post(result);
		}
		
	}

	@Override
	public void onBackPressed() {

		if (mIsFromPush) {
			MainActivity.launch(getActivity());
		}
		if (isFavChange()) {
			setResultOk();
		}
		super.onBackPressed();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mShareSuccessRunnable != null) {
			mShareSuccessRunnable.run();
			mShareSuccessRunnable = null;
		}
	}
	
	@Subscribe
	public void onEventMainThread(CommentSuccessEvent event){
		mReplyList.add(event.getmBean());
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (null != mShareSuccessReceiver) {
			unregisterReceiver(mShareSuccessReceiver);
		}
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public static void updateData() {
		if (null == mContext) {
			return;
		}
		mContext.getDataByHttp();
	}

}
