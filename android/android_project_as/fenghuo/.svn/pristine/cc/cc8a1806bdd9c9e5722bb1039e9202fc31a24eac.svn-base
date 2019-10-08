package com.chengning.fenghuo.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;

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
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.Consts.ArticleType;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.DynamicDetailBean;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.util.UmengShare;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.PhotoContentRelativeLayout;
import com.chengning.fenghuo.widget.PicArticleDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

public class PhotoPageArticleActivity extends BaseFragmentActivity{

	HackyViewPager mViewPager;
	PhotoContentRelativeLayout mContentView;
	List<String> des_list = null;
	BaseFragmentActivity mContext;
	View mView;
	private BaseFragmentActivity mActivity;

	private boolean mPhotoHid;
	private RelativeLayout mTitle;
	private RelativeLayout mHead;
	String title;
	TextView count;
	private StringBuffer mCountBuffer = new StringBuffer();
	
	private View mFav;
	private View mSave;
	private View mShare;
	private boolean isFavState;
	private BaseArticlesBean mArticleBean;
	private int mImageIndex;
	private ArrayList<String> imgList;
	private ArticleManagerUtils mArticleManagerUtils;
	
	private boolean isFavChange = false;

	private BaseArticlesBean mChannelBean;
	protected boolean isFavBtnClick;
	private BroadcastReceiver mShareSuccessReceiver;
	protected Runnable mShareSuccessRunnable;

	public boolean isFavChange() {
		return isFavChange;
	}

	public void setFavChange(boolean isFavChange) {
		this.isFavChange = isFavChange;
	}
	
	@Override
	public BaseFragmentActivity getActivity() {
		return PhotoPageArticleActivity.this;
	}

	@Override
	public void initViews() {
		setContentView(R.layout.activity_photo_page);

		mViewPager = new HackyViewPager(this);
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

		mFav = this.findViewById(R.id.article_photo_fav);
		mSave = this.findViewById(R.id.article_photo_save);
		mShare = this.findViewById(R.id.article_photo_share);
		mPhotoHid = false;
		mTitle.setVisibility(View.GONE);
	}

	@Override
	public void initDatas() {
		this.mImageIndex = getIntent().getIntExtra("index", 0);
		mChannelBean = (BaseArticlesBean) getIntent().getSerializableExtra("channelBean");
		this.imgList = getIntent().getStringArrayListExtra("imgList");
		this.mArticleBean = (BaseArticlesBean) getIntent()
					.getSerializableExtra("articleBean");

		ThreadInitRefreshShow();
		
		mArticleManagerUtils = new ArticleManagerUtils(new ArticleManagerUtils.CollectState() {
			
			@Override
			public void setState(boolean b) {
				mFav.setSelected(b);
				isFavState = b;
				if (b && isFavBtnClick) {
					UIHelper.showToast(mContext, getString(R.string.collect_hint));
				}
			}
		});
		
		if (App.getInst().isLogin()) {
			setCollectState(Common.isTrue(mArticleBean.getIs_favor()));
		} else {
			setCollectState(LocalStateDA.getInst(mContext).isFavorite(LocalStateDA.PREFIX_CHANNEL_ITEM, mChannelBean.getTid()));
		}
		
		mShareSuccessReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if(action.equals(Consts.ACTION_WX_SHARE_SUCESS)){
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
					mShareSuccessReceiver.abortBroadcast();
				}
			}
		};
		IntentFilter intentFileter = new IntentFilter();
		intentFileter.addAction(Consts.ACTION_WX_SHARE_SUCESS);
		intentFileter.setPriority(500);
		registerReceiver(mShareSuccessReceiver, intentFileter);
	}
	
	public void setCollectState(boolean isFavState) {
		mArticleManagerUtils.setCollectState(isFavState);
	}
	
	@Override
	public void installListeners() {
		mFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isFavBtnClick = true;
				if (isFavState) {
					mArticleManagerUtils.cancleCollect(getActivity(), mChannelBean);
				} else {
					if (mArticleBean instanceof ArticlesBean){
						mArticleManagerUtils.doCollect(getActivity(), mChannelBean, ArticleType.ARTICLE);
					} else if (mArticleBean instanceof DynamicDetailBean){
						mArticleManagerUtils.doCollect(getActivity(), mChannelBean, ArticleType.DYNAMIC);
					}
					
				}
				setFavChange(true);
			}
		});

		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!Common.isListEmpty(imgList)) {
					Common.saveImageToGallery(mActivity,
							imgList.get(mImageIndex));
				}
			}

		});

		mShare.setOnClickListener(new OnClickListener() {
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
	}

	@Override
	public void processHandlerMessage(Message msg) {
		
	}
	
	private OnViewTapListener mTap = new OnViewTapListener() {

		@Override
		public void onViewTap(View arg0, float arg1, float arg2) {
			if (!mPhotoHid) {
				mHead.setVisibility(View.INVISIBLE);
				mPhotoHid = true;
			} else {
				mPhotoHid = false;
				mHead.setVisibility(View.VISIBLE);
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
			mCountBuffer.setLength(0);
			mCountBuffer.append(p + 1).append("/").append(imgList.size());
			count.setText(mCountBuffer.toString());

		}

	};

	// touchpage 后数据读取成功显示
	protected void ThreadInitRefreshShow() {
		if (!Common.isListEmpty(imgList)) {
			mCountBuffer.setLength(0);
			mCountBuffer.append(1).append("/").append(imgList.size());
			count.setText(mCountBuffer.toString());
			mContentView.addView(mViewPager);
			mViewPager.setAdapter(new SamplePagerAdapter(this, imgList, mTap));
			mViewPager.setOnPageChangeListener(mPageChangerListener);
			mViewPager.setCurrentItem(mImageIndex);
		} else {
			Toast.makeText(this, "加载失败 请检查网络", Toast.LENGTH_SHORT).show();
		}

	}

	public void photoClick(View v) {
		int tag = Common.handleStrToInteger(v.getTag().toString());
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
	
	@Override
	public void onBackPressed() {
		if (isFavChange()) {
			setResultOk();
		}
		super.onBackPressed();
	}

	protected void setResultOk() {
		Intent intent = new Intent();
		Bundle data = new Bundle();
		data.putInt("favState", isFavState ? Common.TRUE : Common.FALSE);
		intent.putExtras(data);
		setResult(Activity.RESULT_OK, intent);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	@Override
	public void onDestroy() {
		if (null != mShareSuccessReceiver) {
			unregisterReceiver(mShareSuccessReceiver);
		}
		mArticleManagerUtils.destoryFavHandleThread();
		super.onDestroy();
	}
}
