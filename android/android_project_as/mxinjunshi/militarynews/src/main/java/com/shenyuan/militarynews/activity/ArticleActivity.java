package com.shenyuan.militarynews.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.app.ActivityInfo.ActivityState;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.SerializeUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.Const.PointActionType;
import com.shenyuan.militarynews.LoadStateManager;
import com.shenyuan.militarynews.LoadStateManager.LoadState;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.SettingManager.FontSize;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.CommentItemBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.event.PayResultEvent;
import com.shenyuan.militarynews.utils.ArticleContentUtil;
import com.shenyuan.militarynews.utils.ArticleWebView;
import com.shenyuan.militarynews.utils.ArticleWebView.OnWebViewListener;
import com.shenyuan.militarynews.utils.CommentCheckUtil;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.chengning.common.util.StatusBarUtil;;
import com.shenyuan.militarynews.utils.TaskUpdateUtil;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.utils.UmengShare;
import com.shenyuan.militarynews.views.ArticleAdBottom;
import com.shenyuan.militarynews.views.ArticleCommentBottom;
import com.shenyuan.militarynews.views.ArticleMoreDialog;
import com.shenyuan.militarynews.views.ArticleRelativeBottom;
import com.shenyuan.militarynews.views.ArticleRelativeBottom2;
import com.shenyuan.militarynews.views.ArticleRewardDialog;
import com.shenyuan.militarynews.views.CommentInputDialog;
import com.shenyuan.militarynews.views.CommentReplyInputDialog;
import com.shenyuan.militarynews.views.ProgressRefreshView;
import com.shenyuan.militarynews.views.TitleBar;
import com.shenyuan.militarynews.views.TitleBar.BackOnClickListener;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class ArticleActivity extends BaseFragmentActivity {
	
	private static final String TAG = ArticleActivity.class.getSimpleName();
	
	private static final int MSG_DATA_READY = 1;
	
	private TitleBar mTitleBar;
	private View mCoverLayout;
	private ProgressRefreshView mProgressRefresh;
	private TextView mArticleTitle;
	private TextView mArticleTime;
	private TextView mArticleAuthor;
	private TextView mArticleClick;
	private RelativeLayout mArticleContentLayout;
	private ArticleCommentBottom mArticleComment;
	private ArticleRelativeBottom mArticleRelative;
	private ArticleRelativeBottom2 mArticleRelative2;
	private ArticleAdBottom mArticleAd;
	
	private ArticleWebView mArticleWebView;

	private boolean mIsFromPush;
	private MChannelItemBean mBean;  
	private String mArticleArea;
	private ArticlesBean mArticleBean;
	
	private Activity mContext;
	private LoadStateManager mLoadStateManager;
	private ArticleMoreDialog mSettingDialog;
	private ArticleRewardDialog mRewardDialog;
	private View mRoot;

	private App app;

	private boolean isFavChange = false;

	private boolean isFavState;
	private BroadcastReceiver mShareSuccessReceiver;
	private Runnable mShareSuccessRunnable;

	private BroadcastReceiver mCollectReceiver;
	
	public boolean isFavChange() {
		return isFavChange;
	}

	public void setFavChange(boolean isFavChange) {
		this.isFavChange = isFavChange;
	}

	public static void launch(Activity from, MChannelItemBean bean, String articleArea){
		Intent intent = new Intent(from, ArticleActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("article_area", articleArea);
		from.startActivity(intent);
	}
	
	public static void launchByReqCode(Activity from, MChannelItemBean bean, int code){
		Intent intent = new Intent(from, ArticleActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("article_area", "normal");
		from.startActivityForResult(intent, code);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_article);
		if(Common.isTrue(SettingManager.getInst().getNightModel())){  
			StatusBarUtil.setBar(this, getResources().getColor(R.color.night_bg_color), false);
        }else{  
        	StatusBarUtil.setBar(this, getResources().getColor(R.color.normalstate_bg), true);
        }
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putSerializable("bean", mBean);
    	savedInstanceState.putString("article_area", mArticleArea);
    	savedInstanceState.putBoolean("push", mIsFromPush);
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    	mBean = (MChannelItemBean) savedInstanceState.getSerializable("bean");
    	mArticleArea  = savedInstanceState.getString("article_area");
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
		mTitleBar.setRightButton(R.drawable.more_icon);
		
		mProgressRefresh = new ProgressRefreshView(getActivity(), true);
		mCoverLayout = findViewById(R.id.cover_layout);
		mArticleTitle = (TextView)findViewById(R.id.article_content_top_title);
		mArticleTime = (TextView)findViewById(R.id.article_content_top_time);
		mArticleAuthor = (TextView)findViewById(R.id.article_content_top_author);
		mArticleClick = (TextView)findViewById(R.id.article_content_top_click);
		mArticleContentLayout = (RelativeLayout) findViewById(R.id.article_content_content_layout);
		mArticleComment = new ArticleCommentBottom(this, mRoot);
		mArticleRelative = new ArticleRelativeBottom(this, mRoot);
		mArticleRelative2 = new ArticleRelativeBottom2(this, mRoot);
		mArticleAd = new ArticleAdBottom(this, mRoot);
		
	}

	@Override
	public void initDatas() {
		app = App.getInst();
		mContext = this;
		UmengShare.getInstance().checkInit(getActivity());
    	CommentCheckUtil.onActivityCreate();
		
		// data
		mBean = (MChannelItemBean) getIntent().getSerializableExtra("bean");
		mArticleArea = getIntent().getStringExtra("article_area");
		mIsFromPush = getIntent().getBooleanExtra("push", false);
		
		if (mBean == null) {
			finish();
		}
		
		if(mIsFromPush){
			LocalStateServer.getInst(getActivity()).setReadStateRead(LocalStateServer.PREFIX_CHANNEL_ITEM, mBean.getAid());
		}
		
		initLoadState();
		
		if(Common.hasNet()){
			getDataByHttp();
			getClicksByHttp(String.valueOf(mBean.getAid()));
		}else{
			boolean isArticleSaved = false;
			String aStr = LocalStateServer.getInst(getActivity()).queryLocalStateNotNull(LocalStateServer.PREFIX_CHANNEL_ITEM, String.valueOf(mBean.getAid())).getData_item_article();
			if(!TextUtils.isEmpty(aStr)){
				try {
					ArticlesBean aBean = SerializeUtil.deSerialize(aStr, ArticlesBean.class);
					if(aBean != null && !TextUtils.isEmpty(aBean.getTid())){
						isArticleSaved = true;
		           		getHandler().obtainMessage(MSG_DATA_READY, aBean).sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!isArticleSaved){
				getDataByHttp();
			}
		}
		initBroadcast();
		
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, String> map = new HashMap<String, String>();
				String area = mArticleArea;
				if(TextUtils.isEmpty(area)){
					area = "normal";
				}
				map.put("article_area", area);
//				map.put("article_id", String.valueOf(mBean.getAid()));
				MobclickAgent.onEvent(getActivity(), Const.UMEVENT_ARTICLE_CLICK, map);
			}
		}, 50);
		
		EventBus.getDefault().register(getActivity());
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

	@Subscribe
    public void onEventMainThread(PayResultEvent event) {
		if(event.getResultCode() == 0){
			mRewardDialog.dismissAllowingStateLoss();
			mRewardDialog = null;
			UIHelper.showToast(getActivity(), "打赏成功");
		}else if(event.getResultCode() == -2){
			UIHelper.showToast(getActivity(), "取消打赏");
		}else{
			UIHelper.showToast(getActivity(), "打赏失败");
		}
    }
	
	private void getClicksByHttp(String aid) {
		
		RequestParams params = new RequestParams();
		params.put("aid", aid);
		HttpUtil.get(JUrl.SITE + JUrl.URL_GET_CLICKS, params, new MyStatusResponseHandler() {
			

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
			}

			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
			}

			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				
			}
			
		});
		
	}

	@Override
	public void installListeners() {
		// listener
		mTitleBar.setBackOnClickListener(new BackOnClickListener() {
			
			@Override
			public void onClick() {
				handleBackClick();
			}
		});

		mTitleBar.getRightButton().setVisibility(View.GONE);
		mTitleBar.setRightButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleEventAnalyticsMoreBtnClick(getActivity());
				if (null == mArticleBean) {
					return;
				}
				if (null == mSettingDialog) {
					mSettingDialog = new ArticleMoreDialog();

					mSettingDialog.setBean(mArticleBean);
					mSettingDialog.setListener(new ArticleMoreDialog.OnArticleSettingListener() {

						@Override
						public void onFontChange(FontSize font) {
							if(mArticleBean == null){
								return;
							}
							if(mArticleWebView == null){
								getHandler().obtainMessage(MSG_DATA_READY, mArticleBean).sendToTarget();
							}else{
								mArticleWebView.setFont(font);
							}
						}

						@Override
						public void onNightModeChange(boolean mode) {
							mSettingDialog.dismissAllowingStateLoss();
							setDayAndNightModel(mode);
							finish();
							launch(getActivity(), mBean, mArticleArea);
						}
					});
				}

				mSettingDialog.showAllowingStateLoss(getActivity(), getSupportFragmentManager(),
							ArticleMoreDialog.class.getSimpleName());
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
		mArticleClick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = mArticleBean.getAd_url();
				if(!TextUtils.isEmpty(url)){
					AdDetailActivity.launch(getActivity(), AdDetailActivity.TYPE_ARTICLE, url, url);
				}
			}
		});
	}
	/**
	 * 事件统计(右上角更多)
	 * @param mContext
	 */
	protected void handleEventAnalyticsMoreBtnClick(Activity mContext) {
		Map<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("type", "righttop");
		MobclickAgent.onEvent(mContext, "article_panel", mHashMap);
	}

	protected void setResultOk() {
		Intent intent = new Intent();
		intent.putExtra("favState", isFavState ? Common.TRUE : Common.FALSE);
		setResult(Activity.RESULT_OK, intent);
	}


	/**
     * 设置白天黑夜模式
     * @param isChecked 
     */
    private void setDayAndNightModel(boolean isChecked) {
    	app.setNightModelChange(true);
//    	app.setmNightModel(isChecked ? Common.TRUE : Common.FALSE);
    	SettingManager.getInst().saveNightModel(isChecked ? Common.TRUE : Common.FALSE);
    }
	
	@SuppressLint("NewApi")
	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case MSG_DATA_READY:
			// data ready
			mArticleBean = (ArticlesBean) msg.obj;

			updateChannelItemBean();

			mArticleTitle.setText(mArticleBean.getTitle());
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
				// Call requires API level 11
				mArticleTitle.setTextIsSelectable(true);
			}
			mArticleTime.setText(Common.getDateYYYYMMDDNotNull(mArticleBean.getPubDate()));
			mArticleAuthor.setText(mArticleBean.getAuthor());
			if(!TextUtils.isEmpty(mArticleBean.getAd_name())){
				mArticleClick.setText(mArticleBean.getAd_name());
				mArticleClick.setTextColor(getResources().getColor(R.color.article_ad));
			}else{
				mArticleClick.setText("阅读  " + mArticleBean.getClick());
			}
			mArticleContentLayout.removeAllViews();
			
			// content
			if(mArticleWebView != null){
				mArticleWebView.destroy();
			}
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
				// Call requires API level 11
				mArticleWebView = new ArticleWebView();
				mArticleWebView.setChannelBean(mBean);
				View contentR = mArticleWebView.build(this, mArticleBean, new OnWebViewListener() {

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
								mLoadStateManager.setState(LoadState.Success);
							}
						}, Common.isTrue(SettingManager.getInst().getNightModel()) ? 500 : 20);
					}
					
					@Override
					public void onTimeout() {
						onPageFinished();
					}
				}, getHandler());
//				contentR.setFocusable(false);
//				mArticleWebView.getWebView().setFocusable(false);
				mArticleContentLayout.addView(contentR);
			}else{
				View contentR = ArticleContentUtil.build(this, mArticleBean, mBean);
				contentR.setFocusable(false);
				mArticleContentLayout.addView(contentR);
				mLoadStateManager.setState(LoadState.Success);
			}
			
			mArticleBean.setChannel(mBean.getChannel());
			mArticleComment.setData(mArticleBean);
			mArticleRelative.setData(mArticleBean);
			mArticleRelative2.setData(mArticleBean);
			mArticleAd.setData(mArticleBean);
			mTitleBar.getRightButton().setVisibility(View.VISIBLE);
			mArticleComment.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	/**
	 * 更新channelbean（避免收藏的时候缺少相关字段）
	 */
	private void updateChannelItemBean() {
		if (TextUtils.isEmpty(mBean.getAuthor())) {
			mBean.setAuthor(mArticleBean.getAuthor());
		}
		if (TextUtils.isEmpty(mBean.getPubDate())) {
			mBean.setPubDate(mArticleBean.getPubDate());
		}
		mArticleComment.setChannelBean(mBean);
		mArticleRelative.setChannelBean(mBean);
		mArticleRelative2.setChannelBean(mBean);
		mArticleAd.setChannelBean(mBean);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		if (mShareSuccessRunnable != null) {
			mShareSuccessRunnable.run();
			mShareSuccessRunnable = null;
		}
		//登录后显示每日登录
		TaskUpdateUtil.LoginShowUpdate(getActivity());
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	public void onDestroy() {
		if(mArticleWebView != null){
			mArticleContentLayout.removeAllViews();
			mArticleWebView.destroy();
		}
		if (null != mShareSuccessReceiver) {
			unregisterReceiver(mShareSuccessReceiver);
		}
		if (null != mCollectReceiver) {
			unregisterReceiver(mCollectReceiver);
		}
		
		if (mArticleComment != null) {
			mArticleComment.destroy();
		}
		EventBus.getDefault().unregister(getActivity());
		super.onDestroy();
	}
    
    private void handleHttpFailure(){
    	Common.showHttpFailureToast(mContext);
    	getHandler().post(new Runnable() {
			
			@Override
			public void run() {
				mLoadStateManager.setState(LoadState.Failure);
			}
		});
    }
    
    private void getDataByHttp(){
		if (mBean == null) {
			finish();
		}
        final String aid = String.valueOf(mBean.getAid());
		String url = JUrl.SITE + JUrl.URL_GET_ARTICLE_NEW + aid;
//		String url = "http://if02.haoshanxi.com/api2.4/app_debug.php?mod=show&aid=250074";
		url = JUrl.appendType(url, mBean.getChannel());
//		String zipUrl = "http://if.wap.xinjunshi.com/ifios/show_yy.php?aid=122200";
		BasicHeader header = new BasicHeader("Accept-Encoding", "gzip, deflate");
		Header[] headers = {new BasicHeader("Accept", "*/*"), header,
				new BasicHeader("Accept-Language", "zh-cn")};
		
		RequestParams params = new RequestParams();
		params.put("devicetoken", AVInstallation
				.getCurrentInstallation()
				.getInstallationId());
		HttpUtil.getClient().post(this, url.toString(), headers, params, "text/html", new MyStatusResponseHandler() {
			
			@Override
	        public void onFailure(int statusCode, Header[] headers,
	    			Throwable throwable, JSONObject error) {
	            
	            handleHttpFailure();
			}
			
			@Override
			public void onDataSuccess(int status, String mod, String message,
					String data, JSONObject obj) {
	            try {
	            	 Gson gson = new Gson();
	            	 ArticlesBean bean = gson.fromJson(data, ArticlesBean.class);
	            	 bean.setTid(String.valueOf(mBean.getAid()));
	            	 LocalStateServer.getInst(getActivity()).setArticle(LocalStateServer.PREFIX_CHANNEL_ITEM, aid,SerializeUtil.serialize(bean));
	            	 getHandler().obtainMessage(MSG_DATA_READY, bean).sendToTarget();
				} catch (Exception e) {
					e.printStackTrace();
					
		            handleHttpFailure();
				}
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
					String data, JSONObject obj) {
				UIHelper.showToast(mContext, message);

				mLoadStateManager.setState(LoadState.Failure);
			}
		});
    }

    @Override
	public void onBackPressed() {
		try {
			
			handleBackClick();
			super.onBackPressed();
		} catch (IllegalStateException ignored) {

		}
		
	}
    
    private void handleBackClick() {
    	if(isFavChange()){
			setResultOk();
		}
		if(mIsFromPush){
        	HomeActivity.launch(mContext);
		}
	}

	@Override
    public void onStop(){
    	CommentCheckUtil.onActivityStop();
    	super.onStop();
    }

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
		case ArticleContentUtil.REQUEST_PHOTO_PAGE_CODE:
			if (Activity.RESULT_OK == arg1) {
				
				isFavState = Common.isTrue(arg2.getIntExtra("favState", -1));
				mArticleComment.setCollectState(isFavState);
				setFavChange(!isFavState);
				
			}
			break;
		case CommentCheckUtil.REQUEST_CODE_LOGIN_COMMENT:
			CommentCheckUtil.onActivityResult(arg0, arg1, arg2, getHandler(), new Runnable() {
				
				@Override
				public void run() {
					if(getActivityInfo().getActivityState() != ActivityState.Killed){
						CommentInputDialog dialog = new CommentInputDialog();
						dialog.setAid(mBean.getAid());
						dialog.showAllowingStateLoss(getActivity(), getActivity().getSupportFragmentManager(),
								CommentInputDialog.class.getSimpleName());
					}
				}
			});
			break;
		case CommentCheckUtil.REQUEST_CODE_LOGIN_COMMENT_CHILD:
			try {
				final CommentItemBean bean = (CommentItemBean) arg2.getExtras().getSerializable("bean");
				CommentCheckUtil.onActivityResult(arg0, arg1, arg2, getHandler(), new Runnable() {
					
					@Override
					public void run() {
						if(getActivityInfo().getActivityState() != ActivityState.Killed){
							CommentReplyInputDialog dialog = new CommentReplyInputDialog();
							dialog.setData(bean);
							dialog.showAllowingStateLoss(getActivity(), getActivity().getSupportFragmentManager(),
									CommentReplyInputDialog.class.getSimpleName());
						}
					
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	
	/**
	 * 初始化广播
	 */
	protected void initBroadcast(){
		mShareSuccessReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(Const.ACTION_WX_SHARE_SUCESS)) {
					final String data = intent.getStringExtra("data");
					if (null != data) {
						mShareSuccessRunnable = new Runnable() {

							@Override
							public void run() {
								try {
									TaskUpdateUtil.showHints((FragmentActivity) getActivity(), data,PointActionType.SHARE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						};
					}
				}
			}
		};
		IntentFilter intentFileter = new IntentFilter();
		intentFileter.addAction(Const.ACTION_WX_SHARE_SUCESS);
		intentFileter.setPriority(200);
		registerReceiver(mShareSuccessReceiver, intentFileter);
		
		mCollectReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(Const.ACTION_COLLECT_SUCESS)) {
					isFavState = intent.getBooleanExtra("data", false);
					setFavChange(true);
				}
			}
		};
		registerReceiver(mCollectReceiver, new IntentFilter(Const.ACTION_COLLECT_SUCESS));
		
	}
	
}
