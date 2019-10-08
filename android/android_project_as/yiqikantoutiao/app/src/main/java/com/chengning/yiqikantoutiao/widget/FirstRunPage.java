package com.chengning.yiqikantoutiao.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chengning.common.util.SerializeUtil;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.SPHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class FirstRunPage {
	
	private static final String TAG = FirstRunPage.class.getSimpleName();
	private final FirstRunListener mListener;

	private Activity mActivity;
	
	private int mCurrentPage;
	private View mLayout;
	private ViewPager mViewPager;
	private View v5;
	
	private List<View> mViewList;
	private List<ImageView> mImageViewList;
	private MyViewPagerAdapter adapter;
	private int[] mDrawableIds = {R.mipmap.first1, R.mipmap.first2, R.mipmap.first3};

	public FirstRunPage(Activity activity, FirstRunListener listener){
		this.mActivity = activity;
		this.mListener = listener;
//		init(activity);
//		show();
	}

	public boolean isNeedShow() {
        if(Common.isFirstRun() && Common.hasNet() && Common.isTrue(SPHelper.getInst().getInt(
                SPHelper.KEY_GUIDE_DOWNLOAD_SHOW, Common.FALSE))){
            return true;
		}
		return false;
    }
	
	public void show(){
		mLayout.setVisibility(View.VISIBLE);
	}
	
	public void dismiss(){
		mLayout.setVisibility(View.GONE);
//		mActivity.finish();
//		MainActivity.launch(mActivity);
	}
	
	public void init(){
//		LayoutInflater inflater = LayoutInflater.from(mActivity);
//		View v4 = inflater.inflate(R.layout.first_run_page_guide_download, null);
//		ImageView image = (ImageView) v4.findViewById(R.id.guide_download_image);
//		image.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//        		Common.setIsFirstRun(false);
//				dismiss();
//				downloadApk();
//			}
//		});

		mLayout = mActivity.findViewById(R.id.first_run_layout);
		mViewPager = (ViewPager) mActivity.findViewById(R.id.first_run_viewpager);

		String imageUrls = SPHelper.getInst().getString(SPHelper.KEY_GUIDE_DOWNLOAD_IMAGE_URL);
		final ArrayList<String> imgList = (ArrayList<String>)SerializeUtil.deSerialize(imageUrls);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(R.color.transparent)
		.showImageForEmptyUri(R.color.transparent)
		.showImageOnFail(R.color.transparent)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(200))
		.build();

		if (Common.isListEmpty(imgList)) {
			dismiss();
		}

		mViewList = new ArrayList<View>();
		mImageViewList = new ArrayList<ImageView>();
		adapter = new MyViewPagerAdapter(mViewList);
		int size = imgList.size();
        for (int i = 0; i < size; i++) {
			final RelativeLayout layout = new RelativeLayout(mActivity);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			layout.setLayoutParams(params);
			layout.setBackgroundResource(R.color.common_bg);

			final ImageView imageView = new ImageView(mActivity);
			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//			imageView.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.splash_pic));
			layout.addView(imageView);
			mImageViewList.add(imageView);
			mViewList.add(layout);
			ImageLoader.getInstance().displayImage(imgList.get(i), imageView, options, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String s, View view) {

				}

				@Override
				public void onLoadingFailed(String s, View view, FailReason failReason) {
					int i = imgList.indexOf(s);
					if (i <= 3) {
						mImageViewList.get(i).setBackgroundDrawable(mActivity.getResources().getDrawable(mDrawableIds[i]));
					}
					mViewList.set(imgList.indexOf(s), layout);
					if (adapter != null) {
						adapter.notifyDataSetChanged();
					}
				}

				@Override
				public void onLoadingComplete(String s, View view, Bitmap bitmap) {
					mImageViewList.get(imgList.indexOf(s)).setBackgroundDrawable(new BitmapDrawable(bitmap));
					mViewList.set(imgList.indexOf(s), layout);
					if (adapter != null) {
						adapter.notifyDataSetChanged();
					}
				}

				@Override
				public void onLoadingCancelled(String s, View view) {

				}
			});
        }

		v5 = new LinearLayout(mActivity);
		v5.setBackgroundResource(R.color.transparent);
		mViewList.add(v5);

		if (size >= 1) {
			mViewList.get(size - 1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					handleSelectEnd();
				}
			});
		}

		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		show();
	}

	private class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mList;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mList = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mList.get(position), 0);
			return mList.get(position);
		}

		@Override
		public int getCount() {
			if (mList == null) {
				return 0;
			}
			return mList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {
		
		@Override
		public void onPageSelected(int page) {
			mCurrentPage = page;
//			Log.d(TAG, "onPageSelected page: " + page);
			int size = mViewList.size();
            if(mCurrentPage == size - 1){
//	            Log.d(TAG, "onFling go right downloadApk");
				handleSelectEnd();
            }

		}
		
		@Override
		public void onPageScrolled(int page, float positionOffset,
                int positionOffsetPixels) {

		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
//			Log.d(TAG, "onPageScrollStateChanged state: " + state);
		}
	}

	public void handleSelectEnd() {
		Common.setIsFirstRun(false);
		dismiss();
		if (mListener != null) {
			mListener.firstRunEnd();
		}
	}

	public interface FirstRunListener {
		void firstRunEnd();
	}
}
