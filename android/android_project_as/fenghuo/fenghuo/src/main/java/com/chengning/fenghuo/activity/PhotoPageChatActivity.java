package com.chengning.fenghuo.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.widget.extend.HackyViewPager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.widget.PhotoContentRelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class PhotoPageChatActivity extends BaseFragmentActivity{

	HackyViewPager mViewPager;
	PhotoContentRelativeLayout mContentView;
	List<String> des_list = null;
	BaseFragmentActivity mContext;
	View mView;
	private BaseFragmentActivity mActivity;

	private boolean mPhotoHid;
	private RelativeLayout mHead;
	
	private View mSave;
	
	private String mImageUrl;
	
	private int mImageIndex;
	private ArrayList<String> imgList;
	
	public static void launch(Activity from, String url) {
		Intent intent = new Intent(from, PhotoPageChatActivity.class);
		intent.putExtra("imgUrl", url);
		from.startActivity(intent);
	}
	
	@Override
	public BaseFragmentActivity getActivity() {
		return PhotoPageChatActivity.this;
	}

	@Override
	public void initViews() {
		setContentView(R.layout.activity_photo_page_chat);

		mViewPager = new HackyViewPager(this);
		mContext = this;
		mActivity = this;
		mView = this.findViewById(R.id.activity_photo_root);
		mContentView = (PhotoContentRelativeLayout) this
				.findViewById(R.id.activity_photo_content);

		// AddFootBar();
		mHead = (RelativeLayout) this.findViewById(R.id.activity_photo_head);

//		mFav = this.findViewById(R.id.article_photo_fav);
		mSave = this.findViewById(R.id.article_photo_save);
//		mShare = this.findViewById(R.id.article_photo_share);
		mPhotoHid = false;
	}

	@Override
	public void initDatas() {

		mImageUrl = getIntent().getStringExtra("imgUrl");
		if (!TextUtils.isEmpty(mImageUrl)) {
			imgList = new ArrayList<String>();
			imgList.add(mImageUrl);
		}
		ThreadInitRefreshShow();
		
	}
	
	
	@Override
	public void installListeners() {

		mSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Common.saveImageToGallery(mActivity,
						imgList.get(mImageIndex));
			}

		});

	}

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

		}

	};
	
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

	// touchpage 后数据读取成功显示
	protected void ThreadInitRefreshShow() {
		if (!Common.isListEmpty(imgList)) {
			mContentView.addView(mViewPager);
			mViewPager.setAdapter(new SamplePagerAdapter(this, imgList, mTap));
			mViewPager.setOnPageChangeListener(mPageChangerListener);
			mViewPager.setCurrentItem(mImageIndex);
		} else {
			Toast.makeText(this, "加载失败 请检查网络", Toast.LENGTH_SHORT).show();
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
            
	 		if (!mCacheUrls.contains(imgList.get(position)) 
	 				&& !Common.hasImageCache(imgList.get(position))) {
	 			ImageLoader.getInstance().cancelDisplayTask(photoView);
	 			photoView.setVisibility(View.GONE);
//	 			noPicPress.setVisibility(View.VISIBLE);
//	 			noPicLoading.setVisibility(View.GONE);
//	 			noPicError.setVisibility(View.GONE);
//	 			
//	 			noPicPress.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						mCacheUrls.add(imgList.get(position));
//			 			noPicPress.setVisibility(View.GONE);
//			 			ImageLoader.getInstance().displayImage(imgList.get(position),photoView,options, new ImageLoadingListener() {
//							
//							@Override
//							public void onLoadingStarted(String arg0, View arg1) {
//					 			photoView.setVisibility(View.GONE);
//					 			noPicLoading.setVisibility(View.VISIBLE);
//							}
//							
//							@Override
//							public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//					 			photoView.setVisibility(View.GONE);
//					 			noPicLoading.setVisibility(View.GONE);
//					 			noPicError.setVisibility(View.VISIBLE);
//							}
//							
//							@Override
//							public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
//					 			photoView.setVisibility(View.VISIBLE);
//					 			noPicLoading.setVisibility(View.GONE);
//							}
//							
//							@Override
//							public void onLoadingCancelled(String arg0, View arg1) {
//							}
//						}); 
//					}
//				});
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
//			 			noPicLoading.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			 			photoView.setVisibility(View.GONE);
//			 			noPicLoading.setVisibility(View.GONE);
//			 			noPicError.setVisibility(View.VISIBLE);
					}
					
					@Override
					public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			 			photoView.setVisibility(View.VISIBLE);
//			 			noPicLoading.setVisibility(View.GONE);
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
	
	public void photoClick(View v) {
		int tag = Common.handleStrToInteger(v.getTag().toString());
		switch (tag) {
		case 0:
			this.finish();
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
