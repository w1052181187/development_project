package com.shenyuan.militarynews.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chengning.common.base.util.GlideHelper;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;

public class FirstRunPage {
	
	private static final String TAG = FirstRunPage.class.getSimpleName();
	
	private Activity mActivity;
	
	private int mCurrentPage;
	private View mLayout;
	private ViewPager mViewPager;
	private View v5;
	
	private List<View> mViewList;
	
	public FirstRunPage(Activity activity){
		mActivity = activity;
		mLayout = activity.findViewById(R.id.first_run_layout);
		mViewPager = (ViewPager) activity.findViewById(R.id.first_run_viewpager);
		if(Common.isFirstRun() && Common.hasNet()){
			String packageName = SPHelper.getInst().getString(SPHelper.KEY_GUIDE_DOWNLOAD_PACKAGE_NAME);
			boolean isAppNeedDownload = !Common.isInstalled(mActivity,
					packageName)
					&& Common.isTrue(SPHelper.getInst().getInt(
							SPHelper.KEY_GUIDE_DOWNLOAD_SHOW, Common.FALSE));
			if(isAppNeedDownload){
				init(activity);
				show();
			}else{
				dismiss();
			}
		}else{
			dismiss();
		}
	}
	
	public void show(){
		mLayout.setVisibility(View.VISIBLE);
	}
	
	public void dismiss(){
		mLayout.setVisibility(View.GONE);
	}
	
	public void init(Activity activity){
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		mViewList = new ArrayList<View>();

		View v4 = inflater.inflate(R.layout.first_run_page_guide_download, null);
		ImageView image = (ImageView) v4.findViewById(R.id.guide_download_image);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
        		Common.setIsFirstRun(false);
				dismiss();
				downloadApk();
			}
		});
		String imageUrl = SPHelper.getInst().getString(SPHelper.KEY_GUIDE_DOWNLOAD_IMAGE_URL);
		RequestOptions options = new RequestOptions();
		options.placeholder(R.color.transparent)
				.error(R.color.transparent);
		GlideHelper.getInst().loadImageWithPlace(mActivity, imageUrl, options, image);
		ImageView skip = (ImageView) v4.findViewById(R.id.guide_download_skip);
		skip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Common.setIsFirstRun(false);
				dismiss();
			}
		});
		mViewList.add(v4);
		
		v5 = new LinearLayout(mActivity);
		v5.setBackgroundResource(R.color.half_transparent);
		mViewList.add(v5);
		
		MyViewPagerAdapter adapter = new MyViewPagerAdapter(mViewList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
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
			return mList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener{
		
		@Override
		public void onPageSelected(int page) {
			mCurrentPage = page;
			Log.d(TAG, "onPageSelected page: " + page);
			
            if(mCurrentPage == (mViewList.size() - 1)){
	            Log.d(TAG, "onFling go right downloadApk");
	            
	            Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.fade_out);
	            animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
			            
		        		Common.setIsFirstRun(false);
						dismiss();
						downloadApk();
					}
				});
	            v5.startAnimation(animation);
            }
		}
		
		@Override
		public void onPageScrolled(int page, float positionOffset,
                int positionOffsetPixels) {
//			Log.d(TAG, "onPageScrolled page: " + page + ", positionOffset: " + positionOffset + ", positionOffsetPixels: " + positionOffsetPixels);
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {
//			Log.d(TAG, "onPageScrollStateChanged state: " + state);
		}
	}
	
	private void downloadApk(){
		final String appName = SPHelper.getInst().getString(SPHelper.KEY_GUIDE_DOWNLOAD_NAME);
		final String downloadUrl = SPHelper.getInst().getString(SPHelper.KEY_GUIDE_DOWNLOAD_URL);
		if(Common.isUmengChannelNeedNoticeDownload(mActivity)){
			final AlertDialog dialog = new AlertDialog.Builder(mActivity)
			.setTitle("注意")
			.setMessage("是否下载" + appName + "？")
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Common.downloadApk(mActivity, downloadUrl, "正在下载" + appName);
					dialog.dismiss();
				}
			}).create();
			dialog.show();
		}else{
			Common.downloadApk(mActivity, downloadUrl, "正在下载" + appName);
		}
	}
}
