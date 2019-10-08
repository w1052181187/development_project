package com.shenyuan.militarynews.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.DisplayUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.activity.MyFavoriteActivity.DeleteListener;
import com.shenyuan.militarynews.adapter.AdChannelItemRecommendAdapter.ChannelItemType;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.MyCoverRoundedBitmapDisplayer;
import com.shenyuan.militarynews.utils.MyRoundedBitmapDisplayer;

public class MyFavoriteAdapter extends BaseTypeListAdapter {
	
	private static final int TYPE_ONE_PIC = 0;
	private static final int TYPE_THREE_PIC = 1;

	private boolean isDeleteFlag = false;
	private DeleteListener listener;
	private DisplayImageOptions mOptions;
	private DisplayImageOptions mOptionsWithImage;
	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSmallHeight;
	
	public MyFavoriteAdapter(Activity activity, List list, DeleteListener deleteListener, boolean isDeleteFlag) {
		super(activity, list);
		this.isDeleteFlag = isDeleteFlag;
		this.listener = deleteListener;
		mOptions = new DisplayImageOptions.Builder() 
		.showStubImage(R.drawable.loading)
		.showImageForEmptyUri(R.drawable.loading)
		.showImageOnFail(R.drawable.loading)
		.resetViewBeforeLoading(true)
		.cacheInMemory()  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new MyRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner)))
		.build();
		mOptionsWithImage = new DisplayImageOptions.Builder() 
		.showStubImage(R.drawable.loading)
		.showImageForEmptyUri(R.drawable.loading)
		.showImageOnFail(R.drawable.loading)
		.resetViewBeforeLoading(true)
		.cacheInMemory()  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new MyCoverRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner), R.drawable.video_list_icon_small))
		.build();
		DisplayUtil.getInst().init(activity);
		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		int gapThreeSmall = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin_three_small); 
		float imageLayoutWidth = (float)(DisplayUtil.getInst().getScreenWidth() - gapWidth * 2);
		
		mImageThreeSmallWidth = ((int)imageLayoutWidth - gapThreeSmall * 2) / 3;
		mImageThreeSmallHeight = (int)(mImageThreeSmallWidth / 1.5);
		
		mImageOneSmallWidth = mImageThreeSmallWidth;
		mImageOneSmallHeight = mImageThreeSmallHeight;
	}

	@Override
	public int getItemViewType(int position) {
		if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
			// 无图模式固定样式
			return ChannelItemType.OneSmall.getType();
		}else{
			MChannelItemBean bean= (MChannelItemBean) getItem(position);
			if(Const.CHANNEL_ARTICLE_TUWEN.equals(bean.getChannel())){
				return TYPE_THREE_PIC;
			}else{
				return TYPE_ONE_PIC;
			}
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int buildLayoutId(int position, int type) {
		switch (type) {
		default:
		case TYPE_ONE_PIC:
			return R.layout.item_my_favorite_one_pic;
		case TYPE_THREE_PIC:
			return R.layout.item_my_favorite_three_pic;
		}
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj,
			int type) {
		MChannelItemBean bean= (MChannelItemBean) obj;
		
		View currentLayout;
		ImageView delete;
		ImageView img;
		TextView titleTag;
		TextView title;
		TextView date;
		TextView cmt;
		ImageView img2 = null;
		ImageView img3 = null;

		currentLayout = convertView;
		titleTag = BaseViewHolder.get(convertView, R.id.item_favorite_title);
		
		switch (type) {
		default:
		case TYPE_ONE_PIC:
			date = BaseViewHolder.get(currentLayout, R.id.item_favorite_date);
			cmt = BaseViewHolder.get(currentLayout, R.id.item_favorite_cmt);
			date.setText(Common.dateCompareNow(bean.getPubDate()));
			cmt.setText(bean.getAuthor());
//			date.setVisibility(!isDeleteFlag ? View.VISIBLE : View.GONE);
//			cmt.setVisibility(!isDeleteFlag ? View.VISIBLE : View.GONE);
			break;
		case TYPE_THREE_PIC:
			img2 = BaseViewHolder.get(currentLayout, R.id.item_my_favorite_image2);
			img3 = BaseViewHolder.get(currentLayout, R.id.item_my_favorite_image3);
			break;
		}
		
		title = BaseViewHolder.get(currentLayout, R.id.item_favorite_title);
		delete = BaseViewHolder.get(currentLayout, R.id.item_favorite_delete);
		img = BaseViewHolder.get(currentLayout, R.id.item_favorite_img);
		
		if (Common.isTrue(SettingManager.getInst().getNoPicModel())) {
			ImageLoader.getInstance().cancelDisplayTask(img);
			img.setVisibility(View.GONE);
			if (type == TYPE_THREE_PIC) {
				img2.setVisibility(View.GONE);
				img3.setVisibility(View.GONE);
			}
		} else {
			switch (type) {
			case TYPE_ONE_PIC:
				updateSize(img, mImageOneSmallWidth, mImageOneSmallHeight);
				if (!TextUtils.isEmpty(bean.getImage())) {
					img.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(bean.getImage(), img, getImageOptions(bean));
				} else {
					ImageLoader.getInstance().cancelDisplayTask(img);
					img.setVisibility(View.GONE);
				}
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					img.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			case TYPE_THREE_PIC:
				updateSize(img, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(img2, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(img3, mImageThreeSmallWidth, mImageThreeSmallHeight);
				img.setVisibility(View.VISIBLE);
				img2.setVisibility(View.VISIBLE);
				img3.setVisibility(View.VISIBLE);
				ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), img, mOptions);
				ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), img2, mOptions);
				ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), img3, mOptions);
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					img.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
					img2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
					img3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			default:
				break;
			}
			
		}
		title.setText(bean.getTitle());
		titleTag.setTag(bean);
		delete.setOnClickListener(listener);
		delete.setTag(position);
		delete.setVisibility(isDeleteFlag ? View.VISIBLE : View.GONE);
		
	}

	public void setDelFlag(boolean b) {
		isDeleteFlag = b;
	}
	
	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	private DisplayImageOptions getImageOptions(MChannelItemBean bean){
		if(Const.CHANNEL_ARTICLE_VIDEO.equals(bean.getChannel())){
			return mOptionsWithImage;
		}else{
			return mOptions;
		}
	}
}
