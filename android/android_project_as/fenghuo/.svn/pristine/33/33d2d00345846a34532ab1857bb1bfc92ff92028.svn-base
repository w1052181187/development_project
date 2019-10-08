package com.chengning.fenghuo.widget;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.util.Common;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * 专栏特殊幻灯
 * @author Administrator
 *
 */

public class AutoPlayBigPicViewSpecial {
	
	private static final int MSG_AUTO_PLAY = 1;
	private static final long TIME_AUTO_PLAY = 5000;
	
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
	private Handler mHandler;
    
	public AutoPlayBigPicViewSpecial(Activity context,List<ChannelItemBean> topic, OnClickListener onclick)
	{
		mContext = context;
		mTopic = topic;
		mImageOnClickListener = onclick;
		mBigView = null;
		mHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg){
				switch (msg.what) {
				case MSG_AUTO_PLAY:
		            int index = mViewPager.getCurrentItem();
		            int next = (index + 1) % mViewPager.getAdapter().getCount();
					mViewPager.setCurrentItem(next);
					
//		            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_AUTO_PLAY), TIME_AUTO_PLAY);
					break;

				default:
					break;
				}
			}
		};
	} 

	public  View InitbigPic()
    {   
		 max=0; 
    	 if(mTopic != null && mTopic.size() >0 && mContext!=null)
    	 {
    		 //获得屏幕宽
    		 metric = new DisplayMetrics();  
    	     mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);  
//    	     RelativeLayout alpha =(RelativeLayout) mBigView.findViewById(R.id.layout_title_text);
//    	     alpha.getBackground().setAlpha(50); 
    	     mBigView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.item_topic_scroll_special, null);  
    	     RelativeLayout bigimgroot =(RelativeLayout) mBigView.findViewById(R.id.linearlayout_images_slide_root);
    	     if(metric !=null)
    	     {
    	          int width = metric.widthPixels;  //  
    	          int height = metric.heightPixels;
    	          max = height;
    	          if (width<height) {
    	        	  max = width; 
    	          }
//		          android.widget.AbsListView.LayoutParams rlp = new android.widget.AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//		          rlp.height = max/2;
//		          bigimgroot.setLayoutParams(rlp);
		          RelativeLayout fl = (RelativeLayout) mBigView.findViewById(R.id.linearlayout_images_slide);
		          LayoutParams flp = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//		          flp.height = max;
		          flp.height = max/3;
		          fl.setLayoutParams(flp);
    	     }
    	     mViewPager = (ViewPagerExtend) mBigView.findViewById(R.id.image_slide_page); 
    	     initViewPagerScroll(); 
	         mSlideLayout = new SlideImageLayout(mContext); 
	         mImageCircleViews = new ImageView[mTopic.size()];
	         mImageCircleView = (ViewGroup) mBigView.findViewById(R.id.layout_circle_images); 
	         mSlideLayout.setCircleImageLayout(mTopic.size());
	         mImagePageViewList = new ArrayList<View>(); 
//	         boolean isNoPicModel = Common.isTrue(SettingManager.getInst().getNoPicModel());
	         boolean isNoPicModel = false;
	         
        	 for (int i = 0; i < mTopic.size(); i++) 
	         { 
	        	  ImageView image = new ImageView(mContext); 
	 			  LinearLayout.LayoutParams img_param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				  image.setLayoutParams(img_param);
	        	  image.setOnClickListener(mImageOnClickListener);
	        	  image.setScaleType(ImageView.ScaleType.CENTER_CROP);
	        	  if (!isNoPicModel) {
		        	  ImageLoader.getInstance().displayImage(mTopic.get(i).getImages(), image, new ImageLoadingListener(){
							@Override
							public void onLoadingCancelled(String arg0, View arg1) {
								
							} 
							@SuppressLint("NewApi")
							@Override
							public void onLoadingComplete(String arg0, View arg1,
									Bitmap arg2) {
								if(Build.VERSION.SDK_INT > 11)
								{
									if(arg2 != null && (arg2.getHeight()>4000||arg2.getWidth()>4000))
									{ 
										arg1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
										if(arg1.isHardwareAccelerated())
										{
											Log.i("img","isHardwareAccelerated");
										}
									}else
									{
										arg1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
									} 
								}
							}

							@Override
							public void onLoadingFailed(String arg0, View arg1,
									FailReason arg2) {
							}

							@Override
							public void onLoadingStarted(String arg0, View arg1) {
							}
							
						});
		        	  if (Common.isTrue(SettingManager.getInst().getNightModel())) {
		        		  image.setColorFilter(mContext.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);  
		        	  }
	        	  }
	        	  image.setTag(mTopic.get(i)); 
	              mImagePageViewList.add(mSlideLayout.getSlideImageLayout(image));
	              mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
	              mImageCircleViews[i].setBackgroundResource(R.color.transparent);
	           	  mImageCircleViews[i].setImageResource(R.drawable.dot_circle_normal);
	           	  if (0 == i) {
	           		mImageCircleViews[i].setImageResource(R.drawable.dot_circle_focus);
	            	if (Common.isTrue(SettingManager.getInst().getNightModel())) {
	            		mImageCircleViews[i].setColorFilter(mContext.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);  
		        	  }
	           	  }
	           	  if (i == mTopic.size()-1 ){
	           		mImageCircleViews[i].setImageResource(R.drawable.dot_angle_normal);
	           	  }
	              mImageCircleView.addView(mSlideLayout.getLinearLayout(mImageCircleViews[i], 
	            		  mContext.getResources().getDimensionPixelSize(R.dimen.home_slide_dot_circle_width), 
	            		  mContext.getResources().getDimensionPixelSize(R.dimen.home_slide_dot_circle_width)));
	          } 
        	 
	         mSlideTitle = (TextView) mBigView.findViewById(R.id.tvSlideTitle); 
	         mSlideTitle.setText(mTopic.get(0).getTitle());
	         mSlideTitle.setVisibility(View.GONE);
	         mViewPager.setAdapter(new SlideImageAdapter());
	         mViewPager.setOnPageChangeListener(new ImagePageChangeListener()); 
	         
	         //去掉渐变..
	         LinearLayout mShadow = (LinearLayout) mBigView.findViewById(R.id.shadow_ll);
	         mShadow.setBackgroundDrawable(null);
	         
	         mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_AUTO_PLAY), TIME_AUTO_PLAY);
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
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			ViewPagerScroller scroller = new ViewPagerScroller(
					mViewPager.getContext());
			mScroller.set(mViewPager, scroller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
     
 
	/**
	 * 大图滑动
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
	            	mImageCircleViews[index].setImageResource(R.drawable.dot_circle_focus);
	            	if (Common.isTrue(SettingManager.getInst().getNightModel())) {
	            		mImageCircleViews[index].setColorFilter(mContext.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);  
		        	  }
	                if (index != i) {
	                	if(i == mImageCircleViews.length - 1)
	                		mImageCircleViews[i].setImageResource(R.drawable.dot_angle_normal);
	                	else
	                		mImageCircleViews[i].setImageResource(R.drawable.dot_circle_normal);
	                }
	                else if (i == mImageCircleViews.length - 1){
	                	mImageCircleViews[i].setImageResource(R.drawable.dot_angle_focus);
	                }
	            }
	            mHandler.removeMessages(MSG_AUTO_PLAY);
	            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_AUTO_PLAY), TIME_AUTO_PLAY);
	        }
	    }
	

}
