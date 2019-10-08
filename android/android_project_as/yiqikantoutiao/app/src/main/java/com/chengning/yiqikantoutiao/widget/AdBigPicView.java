package com.chengning.yiqikantoutiao.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.chengning.common.widget.SlideImageLayout;
import com.chengning.common.widget.extend.ViewPagerExtend;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.ad.AdDataDummy;
import com.chengning.yiqikantoutiao.ad.AdDataListener;
import com.chengning.yiqikantoutiao.ad.AdDataManager;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.MyRoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AdBigPicView {

	private ViewPagerExtend mViewPager;
	private ImageView[] mImageCircleViews;
	private ViewGroup mImageCircleView = null;
	private SlideImageLayout mSlideLayout;
	private ArrayList<View> mImagePageViewList;
	private TextView mSlideTitle;
	private View mBigView;
	private Activity mContext;
	private List<ChannelItemBean> mTopic;
	private OnClickListener mImageOnClickListener;
	private int max;
	private DisplayMetrics metric;
	
	private AdDataManager mAdDataManage;

	public AdBigPicView(Activity context, List<ChannelItemBean> topic, OnClickListener onclick) {
		mContext = context;
		mTopic = topic;
		mImageOnClickListener = onclick;
		mBigView = null;
		mAdDataManage = new AdDataManager(context);
	}

	public View InitbigPic() {
		max = 0;
		if (mTopic != null && mTopic.size() > 0 && mContext != null) {
			// 获得屏幕宽
			metric = new DisplayMetrics();
			mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);
			// RelativeLayout alpha =(RelativeLayout)
			// mBigView.findViewById(R.id.layout_title_text);
			// alpha.getBackground().setAlpha(50);
			mBigView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_topic_scroll, null);
			RelativeLayout bigimgroot = (RelativeLayout) mBigView.findViewById(R.id.linearlayout_images_slide_root);
			if (metric != null) {
				int width = metric.widthPixels; //
				int height = metric.heightPixels;
				max = height;
				if (width < height) {
					max = width;
				}
				// android.widget.AbsListView.LayoutParams rlp = new
				// android.widget.AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
				// ViewGroup.LayoutParams.FILL_PARENT);
				// rlp.height = max/2;
				// bigimgroot.setLayoutParams(rlp);
  	          	int gapWidth = mContext.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
  	          	max = max - gapWidth * 2;
				RelativeLayout fl = (RelativeLayout) mBigView.findViewById(R.id.linearlayout_images_slide);
				LayoutParams flp = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.FILL_PARENT);
				// flp.height = max;
				flp.height = flp.height = (int) (max/1.77) +  gapWidth * 2;
				fl.setLayoutParams(flp);
			}
			mViewPager = (ViewPagerExtend) mBigView.findViewById(R.id.image_slide_page);
			initViewPagerScroll();
			mSlideLayout = new SlideImageLayout(mContext);
			mImageCircleViews = new ImageView[mTopic.size()];
			mImageCircleView = (ViewGroup) mBigView.findViewById(R.id.layout_circle_images);
			mSlideLayout.setCircleImageLayout(mTopic.size());
			mImagePageViewList = new ArrayList<View>();
			boolean isNoPicModel = Common.isTrue(SettingManager.getInst().getNoPicModel());

			int size = mTopic.size();
			for (int i = 0; i < size; i++) {
				final ChannelItemBean bean = mTopic.get(i);
				final ImageView image = new ImageView(mContext);
				LinearLayout.LayoutParams img_param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
				image.setLayoutParams(img_param);
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setTag(mTopic.get(i));
				mImagePageViewList.add(mSlideLayout.getSlideImageLayout(image));
				if (!TextUtils.isEmpty(bean.getAd_place_id())) {
					// ad
					final int position = i;
					mAdDataManage.getData(bean.getAd_place_id(), position, new AdDataListener() {
						
						@Override
						public void onData(final AdDataDummy.NativeResponseDummy adBean) {
							if(mContext == null || image == null){
								return;
							}
							String title = adBean.getTitle();
							String imageUrl = adBean.getImageUrl();
							bean.setTitle(title);
							adBean.recordImpression(image);
							if(!Common.isTrue(SettingManager.getInst().getNoPicModel())){
								displayImage(imageUrl, image);
							}
							
							if(mViewPager.getCurrentItem() == position){
								mSlideTitle.setText(title);
							}
							image.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									adBean.handleClick(v);
								}
							});
						}
					});
				} else {
					image.setOnClickListener(mImageOnClickListener);
					if(!isNoPicModel){
						displayImage(mTopic.get(i).getImage_list(), image);
					}
				}
				
				mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
				mImageCircleViews[i].setBackgroundResource(R.color.transparent);
				mImageCircleViews[i].setImageResource(R.drawable.shouye_img_page_icon_normal);
				if (0 == i) {
					mImageCircleViews[i].setImageResource(R.drawable.shouye_img_page_icon_focus);
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						mImageCircleViews[i].setColorFilter(mContext.getResources().getColor(R.color.night_img_color),
								PorterDuff.Mode.MULTIPLY);
					}
				}

				mImageCircleView.addView(mSlideLayout.getLinearLayout(mImageCircleViews[i], mContext.getResources()
						.getDimensionPixelSize(R.dimen.home_slide_dot_width), mContext.getResources()
						.getDimensionPixelSize(R.dimen.home_slide_dot_height)));
			}

			mSlideTitle = (TextView) mBigView.findViewById(R.id.tvSlideTitle);
			mSlideTitle.setText(mTopic.get(0).getTitle());
			mSlideTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SettingManager.getInst().getFontSize());
			
			mViewPager.setAdapter(new SlideImageAdapter());
			mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		}
		return mBigView;
	}

	/**
	 * 设置ViewPager的滑动速度
	 * 
	 * */
	private void initViewPagerScroll() {
		try {
			Field mScroller = null;
			mScroller = ViewPagerExtend.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			ViewPagerScroller scroller = new ViewPagerScroller(mViewPager.getContext());
			mScroller.set(mViewPager, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 大图滑动
	 * 
	 * @author swh
	 * 
	 */
	private class SlideImageAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return mImagePageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View view, int arg1, Object arg2) {
			((ViewPagerExtend) view).removeView(mImagePageViewList.get(arg1));
		}

		@Override
		public Object instantiateItem(View view, int position) {
			((ViewPagerExtend) view).addView(mImagePageViewList.get(position));

			return mImagePageViewList.get(position);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}
	}

	/**
	 * 大图点击事件
	 * 
	 * @author swh
	 * 
	 */
	private class ImagePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			mSlideLayout.setPageIndex(index);
			mSlideTitle.setText(mTopic.get(index).getTitle());

			for (int i = 0; i < mImageCircleViews.length; i++) {
				mImageCircleViews[index].setImageResource(R.drawable.shouye_img_page_icon_focus);
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					mImageCircleViews[index].setColorFilter(mContext.getResources().getColor(R.color.night_img_color),
							PorterDuff.Mode.MULTIPLY);
				}
				if (index != i) {
					mImageCircleViews[i].setImageResource(R.drawable.shouye_img_page_icon_normal);
				}
			}
		}
	}
	private DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.loading)
			.showImageForEmptyUri(R.drawable.loading)
			.showImageOnFail(R.drawable.loading)
			.resetViewBeforeLoading(true)
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true)
			.displayer(new MyRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner)))
			.build();
	private void displayImage(String url, ImageView image){
//		ImageLoader.getInstance().displayImage(url, image);
		ImageLoader.getInstance().displayImage(url, image,new ImageLoadingListener() {
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
			}

			@SuppressLint("NewApi")
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if (Common.getApiLevel() > 11 && arg2 != null) {

					if (arg2.getHeight() > 4000 || arg2.getWidth() > 4000) {
						arg1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
						if (arg1.isHardwareAccelerated()) {
							Log.i("img", "isHardwareAccelerated");
						}
					} else {
						arg1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
					}
				}
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			}

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
			}

		});
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			image.setColorFilter(mContext.getResources().getColor(R.color.night_img_color),
					PorterDuff.Mode.MULTIPLY);
		}
	}

}