package com.shenyuan.militarynews.activity;

import java.util.HashSet;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.widget.extend.HackyViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.Const.PointActionType;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.ArticleManagerUtils;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.CollectState;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.TaskUpdateUtil;
import com.shenyuan.militarynews.utils.UmengShare;
import com.shenyuan.militarynews.utils.Utils;
import com.shenyuan.militarynews.views.PhotoContentRelativeLayout;
import com.shenyuan.militarynews.views.PicArticleDialog;
import com.umeng.analytics.MobclickAgent;

public class PhotoPageArticleActivity extends BaseFragmentActivity {
	private static final String TAG = PhotoPageArticleActivity.class
			.getSimpleName();

	private static final int FAV_HANDLE_SUCCESS = 0;

	HackyViewPager mViewPager;
	PhotoContentRelativeLayout mContentView;
	List<String> url_list = null;
	Activity mContext;
	private boolean mPhotoHid;
	private RelativeLayout mTitle;
	private RelativeLayout mHead;
	String title;
	TextView count;

	private View mPhotoToolBar;
	private View mFav;
	private View mSave;
	private View mShare;
	private boolean isFavState;
	private ArticlesBean mArticleBean;
	private int mImageIndex;

	private boolean isFavChange = false;

	private MChannelItemBean mChannelBean;
	
	private View mArticleCommentLayout;
	private BroadcastReceiver mShareSuccessReceiver;
	private Runnable mShareSuccessRunnable;
	
	private ArticleManagerUtils mArticleManagerUtils;
	
	public boolean isFavChange() {
		return isFavChange;
	}

	public void setFavChange(boolean isFavChange) {
		this.isFavChange = isFavChange;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(this);
		setContentView(R.layout.activity_photo_page);
		super.onCreate(savedInstanceState);
	}
	
	private void getData() {

		this.mImageIndex = getIntent().getIntExtra("index", 0);
		
		mChannelBean = (MChannelItemBean) getIntent().getSerializableExtra("channelBean");
		if (getIntent().getSerializableExtra("articleBean") != null) {
			this.mArticleBean = (ArticlesBean) getIntent()
					.getSerializableExtra("articleBean");			
		}

	}

	private OnViewTapListener mTap = new OnViewTapListener() {

		@Override
		public void onViewTap(View arg0, float arg1, float arg2) {
			if (!mPhotoHid) {
				mHead.setVisibility(View.INVISIBLE);
//				mArticleCommentLayout.setVisibility(View.INVISIBLE);
				mPhotoHid = true;
			} else {
				mPhotoHid = false;
				mHead.setVisibility(View.VISIBLE);
//				mArticleCommentLayout.setVisibility(View.VISIBLE);
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
			mImageIndex = p;
			count.setText(String.valueOf(p + 1) + "/" + url_list.size());

		}

	};

	// touchpage 后数据读取成功显示
	protected void ThreadInitRefreshShow() {
		if (App.getInst().isLogin()) {
			setCollectState(Utils.isFavorite(mArticleBean.getIs_favor()));
		} else {
			setCollectState(LocalStateServer.getInst(mContext)
					.isFavorite(LocalStateServer.PREFIX_CHANNEL_ITEM,
							mArticleBean.getTid()));
		}
		if (mArticleBean.getPics() != null) {
			url_list = mArticleBean.getPics();
		}
		if (url_list != null) {
			count.setText(String.valueOf(1) + "/" + url_list.size());
			mContentView.addView(mViewPager);
			mViewPager.setAdapter(new SamplePagerAdapter(this, url_list, mTap));
			mViewPager.setOnPageChangeListener(mPageChangerListener);
			mViewPager.setCurrentItem(mImageIndex);
		} else {
			Toast.makeText(this, "加载失败 请检查网络", Toast.LENGTH_SHORT).show();
		}

	}

	public void photoClick(View v) {
		int tag = Integer.valueOf(v.getTag().toString());
		switch (tag) {
		case 0:
			if (isFavChange()) {
				setResultOk();
			}
			this.finish();
			break;
		}
	}

	static class SamplePagerAdapter extends PagerAdapter {
		
		private HashSet<String> mCacheUrls;
		
		List<String> imgList;
		TextView text;
		Context mContext;
		DisplayImageOptions options;
		private LayoutInflater mInflater;
		private OnViewTapListener tap;

		SamplePagerAdapter(Context context, List<String> list,
				OnViewTapListener tap) {

        	mCacheUrls = new HashSet<String>();
			imgList = list;
			mContext = context;
			options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.loading)
					.showImageForEmptyUri(R.drawable.loading)
					.showImageOnFail(R.drawable.loading)
					.resetViewBeforeLoading(true).cacheInMemory(true)
					.cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.considerExifParams(true)
					.displayer(new FadeInBitmapDisplayer(300)).build();
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
            final PhotoView photoView = (PhotoView) convertView.findViewById(R.id.item_img);
            photoView.setOnViewTapListener(tap);
            final View noPicPress = convertView.findViewById(R.id.no_pic_press_layout);
            final View noPicLoading = convertView.findViewById(R.id.no_pic_loading_layout);
            final View noPicError = convertView.findViewById(R.id.no_pic_error_layout);
            
	 		if (Common.isTrue(SettingManager.getInst().getNoPicModel())
	 				&& !mCacheUrls.contains(imgList.get(position)) 
	 				&& !Common.hasImageCache(imgList.get(position))) {
	 			ImageLoader.getInstance().cancelDisplayTask(photoView);
	 			photoView.setVisibility(View.GONE);
	 			noPicPress.setVisibility(View.VISIBLE);
	 			noPicLoading.setVisibility(View.GONE);
	 			noPicError.setVisibility(View.GONE);
	 			
	 			noPicPress.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mCacheUrls.add(imgList.get(position));
			 			noPicPress.setVisibility(View.GONE);
			 			ImageLoader.getInstance().displayImage(imgList.get(position),photoView,options, new ImageLoadingListener() {
							
							@Override
							public void onLoadingStarted(String arg0, View arg1) {
					 			photoView.setVisibility(View.GONE);
					 			noPicLoading.setVisibility(View.VISIBLE);
							}
							
							@Override
							public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					 			photoView.setVisibility(View.GONE);
					 			noPicLoading.setVisibility(View.GONE);
					 			noPicError.setVisibility(View.VISIBLE);
							}
							
							@Override
							public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					 			photoView.setVisibility(View.VISIBLE);
					 			noPicLoading.setVisibility(View.GONE);
							}
							
							@Override
							public void onLoadingCancelled(String arg0, View arg1) {
							}
						}); 
					}
				});
	 		} else {
	 			photoView.setVisibility(View.VISIBLE);
	 			noPicPress.setVisibility(View.GONE);
	 			noPicLoading.setVisibility(View.GONE);
	 			noPicError.setVisibility(View.GONE);
	 			ImageLoader.getInstance().cancelDisplayTask(photoView);
	 			ImageLoader.getInstance().displayImage(imgList.get(position),photoView,options, new ImageLoadingListener() {
					
					@Override
					public void onLoadingStarted(String arg0, View arg1) {
			 			photoView.setVisibility(View.GONE);
			 			noPicLoading.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			 			photoView.setVisibility(View.GONE);
			 			noPicLoading.setVisibility(View.GONE);
			 			noPicError.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			 			photoView.setVisibility(View.VISIBLE);
			 			noPicLoading.setVisibility(View.GONE);
					}
					
					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}
				}); 
	 			if (Common.isTrue(SettingManager.getInst().getNightModel())) {
	 				photoView.setColorFilter(mContext.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
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
		if(mShareSuccessRunnable != null){
			mShareSuccessRunnable.run();
			mShareSuccessRunnable = null;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 设置收藏状态
	 * 
	 * @param b
	 */
	protected void setCollectState(boolean b) {
		mArticleManagerUtils.setCollectState(b);
	}

	@Override
	public void onBackPressed() {
		if (isFavChange()) {
			setResultOk();
		}
		super.onBackPressed();
	}

	protected void setResultOk() {
		Intent intent = new Intent();
		intent.putExtra("favState", isFavState ? Common.TRUE : Common.FALSE);
		setResult(Activity.RESULT_OK,intent);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	private void initShareBroadcast(){
		mShareSuccessReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if(action.equals(Const.ACTION_WX_SHARE_SUCESS)){
					final String data = intent.getStringExtra("data");
					if(data != null){
						mShareSuccessRunnable = new Runnable(){

							@Override
							public void run() {
								 try{
									 TaskUpdateUtil.showHints(getActivity(), data,PointActionType.SHARE);
								 }catch (Exception e) {
									e.printStackTrace();
								}
								
							}
							
						};
					}
					mShareSuccessReceiver.abortBroadcast();
				}
				
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Const.ACTION_WX_SHARE_SUCESS);
		intentFilter.setPriority(500);
		registerReceiver(mShareSuccessReceiver, intentFilter);
	}
	public BaseFragmentActivity getActivity(){
		return this;
	}

	@Override
	public void initViews() {
		mViewPager = new HackyViewPager(this);
		mContext = this;
		mContentView = (PhotoContentRelativeLayout) this
				.findViewById(R.id.activity_photo_content);

		count = (TextView) this.findViewById(R.id.activity_photo_count);
		// AddFootBar();
		mHead = (RelativeLayout) this.findViewById(R.id.activity_photo_head);
		mTitle = (RelativeLayout) this
				.findViewById(R.id.activity_photo_footbar);
		mPhotoToolBar = this.findViewById(R.id.article_photo_toolbar);
		mFav = this.findViewById(R.id.article_photo_fav);
		mSave = this.findViewById(R.id.article_photo_save);
		mShare = this.findViewById(R.id.article_photo_share);
		mArticleCommentLayout = this.findViewById(R.id.photopage_comment_bottom_layout);
		
	}

	@Override
	public void initDatas() {
		UmengShare.getInstance().checkInit(getActivity());
		
		mPhotoHid = false;
		mTitle.setVisibility(View.GONE);
		mPhotoToolBar.setVisibility(View.VISIBLE);
		initCollectState();
		getData();
		initShareBroadcast();
		ThreadInitRefreshShow();
	}

	/**
	 * 初始化收藏状态
	 */
	private void initCollectState() {
		CollectState collectState = new CollectState() {
			
			@Override
			public void setState(boolean state) {
				getHandler().obtainMessage(FAV_HANDLE_SUCCESS, state).sendToTarget();
			}
		};
		
		mArticleManagerUtils = new ArticleManagerUtils();
		mArticleManagerUtils.setState(collectState);
	}
	
	@Override
	public void installListeners() {
		mFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (isFavState) {
					mArticleManagerUtils.cancleCollect(mContext, mChannelBean, Const.CHANNEL_ARTICLE_NORMAL);
				} else {
					mArticleManagerUtils.doCollect(mContext, mChannelBean, Const.CHANNEL_ARTICLE_NORMAL);	
				}
			}
		});

		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Common.isListEmpty(url_list)) {
					Common.saveImageToGallery(mContext,
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

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
			case FAV_HANDLE_SUCCESS:
				boolean isCollect = (Boolean)msg.obj;
				mFav.setSelected(isCollect);
				isFavState = isCollect;
				setFavChange(true);
				break;
			default:
				break;
		}
	}

	@Override
	public void onDestroy() {
		if(null != mShareSuccessReceiver){
			unregisterReceiver(mShareSuccessReceiver);
		}
		mArticleManagerUtils.destroyFavHandleThread();
		getHandler().removeMessages(FAV_HANDLE_SUCCESS);
		super.onDestroy();
	}
}
