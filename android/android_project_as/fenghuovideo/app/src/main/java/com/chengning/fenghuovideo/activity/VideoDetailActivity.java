package com.chengning.fenghuovideo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.HomeWatcher;
import com.chengning.fenghuovideo.LoadStateManager;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.data.bean.ArticlesBean;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.event.CommentSuccessEvent;
import com.chengning.fenghuovideo.nicevideo.FhItemData;
import com.chengning.fenghuovideo.nicevideo.FhVideoPlayerController;
import com.chengning.fenghuovideo.nicevideo.FhVideoPlayerHelper;
import com.chengning.fenghuovideo.nicevideo.FhNiceVideoWidget;
import com.chengning.fenghuovideo.nicevideo.FhVideo;
import com.chengning.fenghuovideo.util.ArticleManagerUtils;
import com.chengning.fenghuovideo.util.ArticleManagerUtils.DataStateListener;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.UmengShare;
import com.chengning.fenghuovideo.widget.ArticleCommentBottom;
import com.chengning.fenghuovideo.widget.NoFocusingBottomListenerScrollView;
import com.chengning.fenghuovideo.widget.ProgressRefreshView;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.chengning.fenghuovideo.widget.TitleBar.BackOnClickListener;
import com.chengning.fenghuovideo.widget.VideoArticleRelativeBottom;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import de.greenrobot.event.Subscribe;

public class VideoDetailActivity extends BaseFragmentActivity implements HomeWatcher.OnHomePressedListener{

	private static final String TAG = VideoDetailActivity.class.getSimpleName();
	public static final String TYPE_CLICK_COMMENT = "cmt";

	private static final int MSG_DATA_READY = 1;
	private static final int LOAD_VIDEO_FINISH = 2;

//	private TitleBar mTitleBar;
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
	private FhNiceVideoWidget mVideoWidget;

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
	
	private HomeWatcher mHomeWatcher;
	private boolean mIsHomePressed;
	private boolean mIsHomeStop;

	private VideoDetailBottomFragment mFragment;

	public static void launch(Activity from, BaseArticlesBean bean) {
		Intent intent = new Intent(from, VideoDetailActivity.class);
		intent.putExtra("bean", bean);
		from.startActivity(intent);
	}

	public static void launch(Activity from, BaseArticlesBean bean, String type) {
		Intent intent = new Intent(from, VideoDetailActivity.class);
		intent.putExtra("bean", bean);
		intent.putExtra("type", type);
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
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
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
//		mTitleBar = new TitleBar(this, true);
//		mTitleBar.setBackgroundColor(getResources().getColor(
//				R.color.transparent));
//		mTitleBar.setBackText("");
//		mTitleBar.setBackText("", getResources().getColor(R.color.transparent),
//				getResources().getDrawable(R.drawable.fv_back));
//		mTitleBar.setBackTextBackgroundColor(getResources().getColor(R.color.transparent));
	}

	@Override
	public void initDatas() {
		mContext = this;
		UmengShare.getInstance().checkInit(mContext);

		// data
		mBean = (BaseArticlesBean) getIntent().getSerializableExtra("bean");
		mClickType = getIntent().getStringExtra("type");

		mIsFromPush = getIntent().getBooleanExtra("push", false);

		mFragment = VideoDetailBottomFragment.newInstance(mBean, mClickType, mIsFromPush);
		mFragment.setOnLoadFinishListener(new VideoDetailBottomFragment.OnLoadFinishListener() {
			@Override
			public void onFinish(BaseArticlesBean bean) {
				if(mContext == null){
					return;
				}
				mVideoWidget = FhNiceVideoWidget.createVideoItemWidget(getActivity(), mRoot, null, new FhItemData(null, 0, bean), FhVideoPlayerController.CtrlMode.Detail);
				mVideoWidget.getHolder().getController().setBackListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(mVideoWidget.getHolder().getPlayer().isNormal()){
							onBackPressed();
						}
					}
				});
			}
		});
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.contentLayout, mFragment)
                .commitAllowingStateLoss();

	}


	@Override
	public void installListeners() {
//		mTitleBar.setBackOnClickListener(new BackOnClickListener() {
//			@Override
//			public void onClick() {
//
//			}
//		});
	}

	@SuppressLint("NewApi")
	@Override
	public void processHandlerMessage(Message msg) {
	}

	@Subscribe
	public void onEventMainThread(CommentSuccessEvent event) {
		mFragment.onEventMainThread(event);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	@Override
	public void onResume() {
		super.onResume();
		mHomeWatcher = new HomeWatcher(this);
		mHomeWatcher.setOnHomePressedListener(this);
		mHomeWatcher.startWatch();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
	}

	@Override
	public void onPause() {
		super.onPause();
		mHomeWatcher.setOnHomePressedListener(null);
		mHomeWatcher.stopWatch();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mIsHomeStop = mIsHomePressed;
		// 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
		if (mIsHomeStop) {
			NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
		} else {
			NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
		}
	}

	@Override
	public void onHomePressed() {
		mIsHomePressed = true;
	}

	@Override
	public void onHomeLongPressed() {
		mIsHomePressed = true;
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	
	@Override
	public void onBackPressed() {
		if(FhVideoPlayerHelper.getInst().onBack()){
			return;
		}
		super.onBackPressed();
	}

}
