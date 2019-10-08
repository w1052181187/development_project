package com.shenyuan.militarynews.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVInstallation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chengning.common.app.ActivityInfo.ActivityState;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseResponseBean;
import com.chengning.common.base.MyRetrofitResponseCallback;
import com.chengning.common.base.util.RetrofitManager;
import com.chengning.common.util.SerializeUtil;
import com.chengning.common.widget.extend.HackyViewPager;
import com.google.gson.Gson;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.ArticleItemPicBean;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.ArticlesPicBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.ArticleManagerUtils;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.CollectState;
import com.shenyuan.militarynews.utils.CommentCheckUtil;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.TaskUpdateUtil;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.utils.UmengShare;
import com.shenyuan.militarynews.utils.Utils;
import com.shenyuan.militarynews.views.CommentInputDialog;
import com.shenyuan.militarynews.views.PhotoContentRelativeLayout;
import com.shenyuan.militarynews.views.PhotoPageCommentBottom;
import com.shenyuan.militarynews.views.PicArticleDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;

public class PhotoPageActivity<mHandler> extends BaseFragmentActivity {

	private static final int DATA_SUCCESS = 1;
	private static final int DATA_FAIL = 4;
	private static final int FAV_HANDLE_SUCCESS = 0;
	HackyViewPager mViewPager;
	PhotoContentRelativeLayout mContentView;
	List<String> url_list = null;
	List<String> des_list = null;
	Activity mContext;
	View mView;
	Handler mHandler;
	private Activity mActivity;
	private TextView mTitleTxt;
	private TextView mDescTxt;
	private TextView mTitleBottomTxt;

	private boolean mPhotoHid;
	private RelativeLayout mTitle;
	private RelativeLayout mHead;
	TextView count;
	private View mToolBar;
	private View mFav;
	private View mSave;
	private View mShare;
	private int mImageIndex;
	private PhotoPageCommentBottom mArticleComment;
	private View mArticleCommentLayout;

	private boolean isFavState;
	private ArticlesBean mArticleBean;
	private String topicId;
	private String title;
	private String mArticleArea;

	private boolean isFavChange = false;
	private MChannelItemBean mChannelBean;
	private ArticleManagerUtils mArticleManagerUtils;
	private boolean mIsFromPush;
	
	public static void launch(Activity context, MChannelItemBean bean, String articleArea) {
		Intent intent = new Intent();
		intent.putExtra("bean", bean);
		intent.putExtra("article_area", articleArea);
		intent.setClass(context, PhotoPageActivity.class);
		context.startActivity(intent);
	}

	public static void launchByReqCode(Activity context, MChannelItemBean bean,
			int code) {
		Intent intent = new Intent();
		intent.putExtra("bean", bean);
		intent.putExtra("article_area", "normal");
		intent.setClass(context, PhotoPageActivity.class);
		context.startActivityForResult(intent, code);
	}
	
	public boolean isFavChange() {
		return isFavChange;
	}

	public void setFavChange(boolean isFavChange) {
		this.isFavChange = isFavChange;
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putSerializable("bean", mChannelBean);
		savedInstanceState.putString("article_area", mArticleArea);
		savedInstanceState.putBoolean("push", mIsFromPush);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mChannelBean = (MChannelItemBean) savedInstanceState.getSerializable("bean");
    	mArticleArea  = savedInstanceState.getString("article_area");
    	mIsFromPush = savedInstanceState.getBoolean("push", false);
	}

	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(this);
		setContentView(R.layout.activity_photo_page);
		super.onCreate(savedInstanceState);

	}

	/**
	 * 初始化视图
	 */
	@Override
	public void initViews() {
		
		mViewPager = new HackyViewPager(this);
		mHandler = getHandler();
		mContext = this;
		mActivity = this;
		mView = this.findViewById(R.id.activity_photo_root);
		mContentView = (PhotoContentRelativeLayout) this
				.findViewById(R.id.activity_photo_content);
		count = (TextView) this.findViewById(R.id.activity_photo_count);
		// AddFootBar();
		mHead = (RelativeLayout) this.findViewById(R.id.activity_photo_head);
		mTitle = (RelativeLayout) this
				.findViewById(R.id.activity_photo_footbar);

		mTitleTxt = (TextView) this.findViewById(R.id.activity_photo_title);
		mDescTxt = (TextView) this.findViewById(R.id.activity_photo_desc);
		mTitleBottomTxt = (TextView) this
				.findViewById(R.id.activity_photo_title_bottom);

		mToolBar = this.findViewById(R.id.article_photo_toolbar);
		mFav = this.findViewById(R.id.article_photo_fav);
		mSave = this.findViewById(R.id.article_photo_save);
		mShare = this.findViewById(R.id.article_photo_share);

		mArticleComment = new PhotoPageCommentBottom(this, mView);
		mArticleCommentLayout = this
				.findViewById(R.id.photopage_comment_bottom_layout);
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initDatas() {
		
		UmengShare.getInstance().checkInit(getActivity());
    	CommentCheckUtil.onActivityCreate();
		mChannelBean = (MChannelItemBean) getIntent().getSerializableExtra(
				"bean");
		mIsFromPush = getIntent().getBooleanExtra("push", false);
		topicId = mChannelBean.getAid();
		title = mChannelBean.getTitle();
		mArticleArea = getIntent().getStringExtra("article_area");
		mArticleComment.setAid(topicId);
		
		init();

		if (Common.hasNet()) {
			ThreadInitRefreshDB();
			getClicksByHttp(topicId);

		} else {
			String aStr = LocalStateServer
					.getInst(mContext)
					.queryLocalStateNotNull(
							LocalStateServer.PREFIX_CHANNEL_ITEM_PIC, topicId)
					.getData_item_article();
			if (!TextUtils.isEmpty(aStr)) {
				ArticlesPicBean aBean = SerializeUtil.deSerialize(aStr,
						ArticlesPicBean.class);
				if (!TextUtils.isEmpty(aBean.getTid())) {
					Message msg = new Message();
					msg.what = DATA_SUCCESS;
					msg.obj = aBean;
					mHandler.sendMessage(msg);
				}
			} else {
				Message msg = new Message();
				msg.what = DATA_FAIL;
				mHandler.sendMessage(msg);
			}

		}

		mPhotoHid = false;

		
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				HashMap<String, String> map = new HashMap<String, String>();
				String area = mArticleArea;
				if(TextUtils.isEmpty(area)){
					area = "normal";
				}
				map.put("article_area", area);
//				map.put("article_id", String.valueOf(topicId));
				MobclickAgent.onEvent(getActivity(), Const.UMEVENT_ARTICLE_CLICK, map);
			}
		}, 50);
	}
	
	private void init() {
		CollectState collectState = new CollectState() {
			
			@Override
			public void setState(boolean state) {
				mHandler.obtainMessage(FAV_HANDLE_SUCCESS, state).sendToTarget();
			}
		};
		
		mArticleManagerUtils = new ArticleManagerUtils();
		mArticleManagerUtils.setState(collectState);
	}

	private void getClicksByHttp(String aid) {

		HashMap params = new HashMap<String, String>();
		params.put("aid", aid);

		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().get(JUrl.URL_GET_CLICKS, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {
			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {

			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {

			}
		});

	}

	/**
	 * 初始化监听器
	 */
	@Override
	public void installListeners() {
		
		mFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFavState) {
					mArticleManagerUtils.cancleCollect(mContext, mChannelBean, Const.CHANNEL_ARTICLE_TUWEN);
				} else {
					mArticleManagerUtils.doCollect(mContext, mChannelBean, Const.CHANNEL_ARTICLE_TUWEN);	
				}
			}
		});

		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Common.isListEmpty(url_list)) {
					Common.saveImageToGallery(mActivity,
							url_list.get(mImageIndex));
				}
			}

		});

		mShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (null != mArticleBean) {
					PicArticleDialog dialog = new PicArticleDialog();
					dialog.setBean(mArticleBean);
					dialog.showAllowingStateLoss(getActivity(), getSupportFragmentManager(),
							PicArticleDialog.class.getSimpleName());
				}

			}
		});
	}

	/**
	 * 设置收藏状态
	 * 
	 * @param b
	 */
	protected void setCollectState(boolean b) {
		mArticleManagerUtils.setCollectState(b);
	}

	private OnViewTapListener mTap = new OnViewTapListener() {

		@Override
		public void onViewTap(View arg0, float arg1, float arg2) {
			if (!mPhotoHid) {
				mHead.setVisibility(View.INVISIBLE);
				mTitle.setVisibility(View.INVISIBLE);
				mArticleCommentLayout.setVisibility(View.INVISIBLE);
				mPhotoHid = true;
			} else {
				mPhotoHid = false;
				mHead.setVisibility(View.VISIBLE);
				mTitle.setVisibility(View.VISIBLE);
				mArticleCommentLayout.setVisibility(View.VISIBLE);
			}
		}
	};

	private OnPageChangeListener mPageChangerListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int p) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int p) {
			count.setText(String.valueOf(p + 1) + "/" + url_list.size());
			if (des_list.get(p).equals("")) {
				mTitleTxt.setVisibility(View.INVISIBLE);
				mTitleBottomTxt.setVisibility(View.VISIBLE);
				mDescTxt.setVisibility(View.INVISIBLE);

				mTitleBottomTxt.setText(title);
			} else {
				mTitleTxt.setVisibility(View.VISIBLE);
				mTitleBottomTxt.setVisibility(View.INVISIBLE);
				mDescTxt.setVisibility(View.VISIBLE);
				mTitleTxt.setText(title);
				mDescTxt.setText(des_list.get(p));
			}
			mImageIndex = p;
		}

	};

	// 显示 touchpage
	protected void ShowTouchRefresh() {
		mToolBar.setVisibility(View.GONE);
		Utils.removeProssBar(mView, R.id.activity_photo_content);
		Utils.addTouchPhoto(this, mView, R.id.activity_photo_content);
		ImageView img = (ImageView) this.findViewById(R.id.touch_refresh_bk);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Common.hasNet()) {
					Utils.removeTouchPhoto(mView, R.id.activity_photo_content);
					Utils.addProssBar(mContext, mView,
							R.id.activity_photo_content);
					ThreadInitRefreshDB();
				} else {
					Toast.makeText(mContext, "加载失败 请检查网络", Toast.LENGTH_SHORT)
							.show();
				}
			}

		});
	}

	// touchpage后读取数据
	protected void ThreadInitRefreshDB() {
		InitNewsPage();
	}

	// touchpage 后数据读取成功显示
	protected void ThreadInitRefreshShow(ArticlesPicBean bean) {

		mArticleComment.setData(bean);

		mArticleBean = new ArticlesBean();
		mArticleBean.setTid(topicId);
		mArticleBean.setTitle(bean.getTitle());
		mArticleBean.setDescription(bean.getDescription());
		mArticleBean.setLink(bean.getLink());

		ArrayList<String> articlePics = new ArrayList<String>();
		if (!Common.isListEmpty(bean.getPics())) {
			for (int i = 0; i < bean.getPics().size(); i++) {
				articlePics.add(bean.getPics().get(i).getUrl());
			}
		}

		mArticleBean.setPics(articlePics);

		if (bean.getPics() != null) {
			initDataList(bean.getPics(), bean);
		}

		mToolBar.setVisibility(View.VISIBLE);
		Utils.removeProssBar(mView, R.id.activity_photo_content);

		if (Common.isListEmpty(des_list) || des_list.get(0).equals("")) {
			mTitleTxt.setVisibility(View.INVISIBLE);
			mTitleBottomTxt.setVisibility(View.VISIBLE);
			mDescTxt.setVisibility(View.INVISIBLE);
			mTitleBottomTxt.setText(title);
		} else {
			mTitleTxt.setVisibility(View.VISIBLE);
			mTitleBottomTxt.setVisibility(View.INVISIBLE);
			mDescTxt.setVisibility(View.VISIBLE);
			mDescTxt.setText(des_list.get(0));
			mTitleTxt.setText(title);
		}
		if (url_list != null) {
			count.setText(String.valueOf(1) + "/" + url_list.size());
			mContentView.addView(mViewPager);
			mViewPager.setAdapter(new SamplePagerAdapter(this, url_list, mTap));
			mViewPager.setOnPageChangeListener(mPageChangerListener);
		} else {
			ShowTouchRefresh();
			UIHelper.showToast(this, "加载失败 请检查网络");
		}
		if (App.getInst().isLogin()) {
			setCollectState(Utils.isFavorite(bean.getIs_favor()));
		} else {
			setCollectState(LocalStateServer.getInst(mContext).isFavorite(
					LocalStateServer.PREFIX_CHANNEL_ITEM_PIC, topicId));
		}

	}

	public void LoadError() {
		ShowTouchRefresh();
		UIHelper.showToast(mActivity, "载入发生错误");
	}

	public void InitNewsPage() {

		Utils.addProssBar(this, this.findViewById(R.id.activity_photo_root),
				R.id.activity_photo_content);

		String url = JUrl.append(JUrl.GET_ARTICLES_PIC,"aid", topicId);
		HashMap params = new HashMap<String, String>();
		params.put("devicetoken", AVInstallation
				.getCurrentInstallation()
				.getInstallationId());

		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().post(url, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {
			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
				Gson gson = new Gson();
				ArticlesPicBean mArticlePicBean = gson.fromJson(data, ArticlesPicBean.class);
				mArticlePicBean.setTid(topicId);

				LocalStateServer.getInst(mContext).setArticle(
						LocalStateServer.PREFIX_CHANNEL_ITEM_PIC,
						topicId,
						SerializeUtil.serialize(data));

				Message msg = new Message();
				msg.what = DATA_SUCCESS;
				msg.obj = data;
				mHandler.sendMessage(msg);

			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
				Message msg = new Message();
				msg.what = DATA_FAIL;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onError(Throwable t) {
				Message msg = new Message();
				msg.what = DATA_FAIL;
				mHandler.sendMessage(msg);
				super.onError(t);
			}
		});

	}

	public void photoClick(View v) {
		int tag = Integer.valueOf(v.getTag().toString());
		switch (tag) {
		case 0:

			handleBackClick();
			this.finish();
			break;
		}
	}

	static class SamplePagerAdapter extends PagerAdapter {

		private HashSet<String> mCacheUrls;

		List<String> imgList;
		TextView text;
		Context mContext;
		private LayoutInflater mInflater;
		private OnViewTapListener tap;

		SamplePagerAdapter(Context context, List<String> list,
				OnViewTapListener tap) {

			mCacheUrls = new HashSet<String>();
			imgList = list;
			mContext = context;
			mInflater = LayoutInflater.from(context);
			this.tap = tap;
		}

		@Override
		public int getCount() {
			return imgList.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			View convertView = mInflater.inflate(R.layout.item_img, null);
			final PhotoView photoView = (PhotoView) convertView
					.findViewById(R.id.item_img);
			photoView.setOnViewTapListener(tap);
			final View noPicPress = convertView
					.findViewById(R.id.no_pic_press_layout);
			final View noPicLoading = convertView
					.findViewById(R.id.no_pic_loading_layout);
			final View noPicError = convertView
					.findViewById(R.id.no_pic_error_layout);
			Glide.with(mContext).clear(photoView);
			final SimpleTarget<Drawable> target = new SimpleTarget<Drawable>() {

				@Override
				public void onLoadStarted(Drawable placeholder) {
                    photoView.setVisibility(View.GONE);
                    noPicLoading.setVisibility(View.VISIBLE);
					super.onLoadStarted(placeholder);
				}

				@Override
				public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
					photoView.setVisibility(View.VISIBLE);
					noPicLoading.setVisibility(View.GONE);
					photoView.setImageDrawable(resource);
				}

				@Override
				public void onLoadFailed(@Nullable Drawable errorDrawable) {
					photoView.setVisibility(View.GONE);
					noPicLoading.setVisibility(View.GONE);
					noPicError.setVisibility(View.VISIBLE);
					super.onLoadFailed(errorDrawable);
				}

			};

			if (Common.isTrue(SettingManager.getInst().getNoPicModel())
					&& !mCacheUrls.contains(imgList.get(position))) {
//					&& !Common.hasImageCache(imgList.get(position))) {
				photoView.setVisibility(View.GONE);
				noPicPress.setVisibility(View.VISIBLE);
				noPicLoading.setVisibility(View.GONE);
				noPicError.setVisibility(View.GONE);

				noPicPress.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mCacheUrls.add(imgList.get(position));
						noPicPress.setVisibility(View.GONE);
						Glide.with(mContext).load(imgList.get(position)).into(target);
					}
				});
			} else {
				photoView.setVisibility(View.VISIBLE);
				noPicPress.setVisibility(View.GONE);
				noPicLoading.setVisibility(View.GONE);
				noPicError.setVisibility(View.GONE);
				Glide.with(mContext).load(imgList.get(position)).into(target);
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					photoView.setColorFilter(
							mContext.getResources().getColor(
									R.color.night_img_color),
							PorterDuff.Mode.MULTIPLY);
				}
			}

			container.addView(convertView);
			return convertView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		//登录后显示每日登录
		TaskUpdateUtil.LoginShowUpdate(getActivity());
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void initDataList(ArrayList<ArticleItemPicBean> articlePics,
			ArticlesPicBean mArticleBean) {
		ArrayList<ArticleItemPicBean> pics = articlePics;
		String picstr = "";
		if (pics != null) {
			for (int j = 0; j < pics.size(); j++) {
				picstr += pics.get(j).getUrl() + ",";
			}
		}
		url_list = new ArrayList();
		url_list.clear();

		des_list = new ArrayList();
		des_list.clear();
		for (int i = 0; i < pics.size(); i++) {
			if (pics.get(i) != null) {
				url_list.add(pics.get(i).getUrl());
				if (i == 0) {
					if (pics.get(i).getPicstext() == null
							|| pics.get(i).getPicstext().equals("")) {
						if (mArticleBean.getContent() != null) {
							String str = Common.convertArraylistToString(
									mArticleBean.getContent()).toString();
							des_list.add(str);
						} else {
							des_list.add("");
						}
					} else {
						des_list.add(pics.get(i).getPicstext());
					}
				} else {
					if (pics.get(i).getPicstext() == null
							|| pics.get(i).getPicstext().equals("")) {
						des_list.add(des_list.get(i - 1));
					} else {
						des_list.add(pics.get(i).getPicstext());
					}
				}
			}
		}
	}

	@Override
	public void onBackPressedSupport() {
		super.onBackPressedSupport();
		handleBackClick();
	}

	private void handleBackClick() {
		if (isFavChange()) {
			setResultOk();
		}
		if(mIsFromPush){
        	HomeActivity.launch(mContext);
		}
	}

	protected void setResultOk() {
		Intent intent = new Intent();
		intent.putExtra("favState", isFavState ? Common.TRUE : Common.FALSE);
		setResult(Activity.RESULT_OK, intent);
	}
    
    @Override
    public void onStop(){
    	CommentCheckUtil.onActivityStop();
    	super.onStop();
    }

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg0) {
		case CommentCheckUtil.REQUEST_CODE_LOGIN_COMMENT:
			CommentCheckUtil.onActivityResult(arg0, arg1, arg2, mHandler, new Runnable() {
				
				@Override
				public void run() {
					if(getActivityInfo().getActivityState() != ActivityState.Killed){
						CommentInputDialog dialog = new CommentInputDialog();
						dialog.setAid(mChannelBean.getAid());
						dialog.showAllowingStateLoss(getActivity(),getActivity().getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
					}
				}
			});
			break;
		default:
			break;
		}
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public BaseFragmentActivity getActivity() {
		return this;
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
			case DATA_SUCCESS:
				ThreadInitRefreshShow((ArticlesPicBean) msg.obj);
				break;
			case FAV_HANDLE_SUCCESS:
				boolean isCollect = (Boolean)msg.obj;
				mFav.setSelected(isCollect);
				isFavState = isCollect;
				setFavChange(true);
				break;
			case DATA_FAIL:
				LoadError();
				break;
		}
	}

	@Override
	public void onDestroy() {
		mArticleManagerUtils.destroyFavHandleThread();
		mHandler.removeMessages(DATA_SUCCESS);
		mHandler.removeMessages(DATA_FAIL);
		mHandler.removeMessages(FAV_HANDLE_SUCCESS);
		super.onDestroy();
	}

}
