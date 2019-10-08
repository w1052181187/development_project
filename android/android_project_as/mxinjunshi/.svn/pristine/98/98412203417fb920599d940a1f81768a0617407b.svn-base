package com.shenyuan.militarynews.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.shenyuan.militarynews.ad.AdDataDummy;
import com.shenyuan.militarynews.ad.AdDataDummy.NativeResponseDummy;
import com.shenyuan.militarynews.ad.AdDataListener;
import com.shenyuan.militarynews.ad.AdDataManager;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.MyCoverRoundedBitmapDisplayer;
import com.shenyuan.militarynews.utils.Utils;

public class ArticleRelativeItemAdapter extends BaseTypeListAdapter {

	private Activity mContext;
	private DisplayImageOptions mOptions;
	private AdDataManager mAdDataManager;
	private int mAdOffset;
	private DisplayImageOptions mOptionsWithImage;
	private DisplayImageOptions mOptionsWithImageSmall;

	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSamllHeight;

	public ArticleRelativeItemAdapter(Activity activity, List list) {
		super(activity, list);

		mContext = activity;

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
		// TODO(不显示圆角)
		// .displayer(new MyRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner)))
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
		.displayer(new MyCoverRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner), R.drawable.video_list_icon))
		.build();
		
		mOptionsWithImageSmall = new DisplayImageOptions.Builder() 
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
		
		mAdDataManager = new AdDataManager(getContext());
		
		DisplayUtil.getInst().init(activity);
		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		int gapThreeSmall = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin_three_small); 
		mImageThreeSmallWidth = (DisplayUtil.getInst().getScreenWidth() -gapWidth*2 - gapThreeSmall * 2) / 3;
		mImageThreeSmallHeight = (int) (mImageThreeSmallWidth / 1.5);
		mImageOneSmallWidth = mImageThreeSmallWidth;
		mImageOneSamllHeight = mImageThreeSmallHeight;
	}
	
	public ArticleRelativeItemAdapter(Activity activity, List list, AdDataManager ad, int adOffet) {
		super(activity, list);

		mContext = activity;
		this.mAdOffset = adOffet;

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
		// TODO(不显示圆角)
		// .displayer(new MyRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner)))
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
		.displayer(new MyCoverRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner), R.drawable.video_list_icon))
		.build();
		
		mOptionsWithImageSmall = new DisplayImageOptions.Builder() 
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
		
		mAdDataManager = ad;
		
		DisplayUtil.getInst().init(activity);
		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		int gapThreeSmall = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin_three_small); 
		mImageThreeSmallWidth = (DisplayUtil.getInst().getScreenWidth() -gapWidth*2 - gapThreeSmall * 2) / 3;
		mImageThreeSmallHeight = (int) (mImageThreeSmallWidth / 1.5);
		mImageOneSmallWidth = mImageThreeSmallWidth;
		mImageOneSamllHeight = mImageThreeSmallHeight;
	}

	public static enum ChannelItemType {
		/**
		 * OneSmall，1个小图，默认type，type必须从0开始
		 */
		OneSmall(0, Const.NEWS_TYPE_COMMON, Const.NEWS_TYPE_COMMON_NO_PIC),	// 1个小图
		OneBig(1, Const.NEWS_TYPE_ONE_BIG_PIC),	// 1个大图
		OneSmallAD(2, Const.NEWS_TYPE_COMMON_AD),	// 1个小图广告
		OneBigAD(3, Const.NEWS_TYPE_COMMON_AD_ONE_BIG),	// 1个大图广告
		ThreeSmall(4, Const.NEWS_TYPE_THREE_SMALL_PIC), // 3个小图
		AdThreeSmall(7, Const.NEWS_TYPE_COMMON_AD_THREE_SMALL), // 广告
		;
		
		private int type;
		private int showType[];
		
		private ChannelItemType(int type, int... showType){
			this.type = type;
			this.showType = showType;
		}
		
		public int getType(){
			return type;
		}
		
		public int[] getShowType(){
			return showType;
		}
		
		public static ChannelItemType parseType(int type){
			ChannelItemType it = ChannelItemType.OneSmall;
			for(ChannelItemType c : values()){
				if(c.type == type){
					it = c;
					break;
				}
			}
			return it;
		}
		
		public static ChannelItemType parseShowType(int showType){
			ChannelItemType it = ChannelItemType.OneSmall;
			for(ChannelItemType c : values()){
				for(int i : c.showType){
					if(i == showType){
						it = c;
						break;
					}
				}
			}
			return it;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
			// 无图模式固定样式
			return ChannelItemType.OneSmall.getType();
		}else{
			MChannelItemBean bean = (MChannelItemBean) getItem(position);
			if(bean == null){
				return ChannelItemType.OneSmall.getType();
			}else{
				return ChannelItemType.parseShowType(bean.getNews_show_type()).getType();
			}
		}
	}

	@Override
	public int getViewTypeCount() {
		if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
			return 1;
		}else{
			return ChannelItemType.values().length;
		}
	}
	
	@Override
	public View buildLayoutView(int position, int type) {
		View view = null;
		ChannelItemType ct = ChannelItemType.parseType(type);
		switch (ct) {
		default:
		case OneSmall:
			view = View.inflate(getContext(), R.layout.item_article_relative_ad_one_small, null);
			break;
		case OneBig:
			view = View.inflate(getContext(), R.layout.item_article_relative_one_big, null);
			break;
		case OneSmallAD:
			view = View.inflate(getContext(), R.layout.item_article_relative_ad_one_small, null);
			break;
		case OneBigAD:
			view = View.inflate(getContext(), R.layout.item_article_relative_one_big, null);
			break;
		case ThreeSmall:
			view = View.inflate(getContext(), R.layout.item_article_relative_three_small, null);
			break;
		case AdThreeSmall:
			view = View.inflate(getContext(), R.layout.item_article_relative_three_small_ad, null);
			break;
		}
		return view;
	}

	@Override
	public int buildLayoutId(int position, int type) {
		return -1;
	}

	@Override
	public void handleLayout(final View convertView, final int position, Object obj,
			int type) {
		MChannelItemBean bean = (MChannelItemBean) getItem(position);

		// type
		View contentLayout = convertView;
		contentLayout.setVisibility(View.VISIBLE);
		if(bean == null){
			contentLayout.setVisibility(View.GONE);
			return;
		}
		final boolean isNoPic = Common.isTrue(SettingManager.getInst().getNoPicModel());
		final ChannelItemType itemType = ChannelItemType.parseType(type);

		boolean isAd = itemType == ChannelItemType.OneSmallAD || itemType == ChannelItemType.OneBigAD
				|| itemType == ChannelItemType.AdThreeSmall;
		final int mShowType = bean.getNews_show_type();
		isAd = isAd || (isNoPic && (mShowType == Const.NEWS_TYPE_COMMON_AD
				|| mShowType == Const.NEWS_TYPE_COMMON_AD_ONE_BIG
				|| mShowType == Const.NEWS_TYPE_COMMON_AD_THREE_SMALL));
		if (isAd) {
			contentLayout.setVisibility(View.GONE);
			// 标记位置，防止异步ad返回后view位置变化
			BaseViewHolder.get(convertView, R.id.item_channel_item_title).setTag(position);
			final MChannelItemBean finalBean = bean;

			mAdDataManager.getData(finalBean.getAd_type(), finalBean.getAd_place_id(), position + mAdOffset, new AdDataListener() {

				@Override
				public void onData(NativeResponseDummy obj) {
					if(convertView == null){
						return;
					}
					View tagObjView = BaseViewHolder.get(convertView, R.id.item_channel_item_title);
					Object tagObj = tagObjView != null ? tagObjView.getTag() : null;
					if(tagObj == null || !(tagObj instanceof Integer) || (Integer)tagObj != position){
						return;
					}

					View contentLayout = convertView;
					contentLayout.setVisibility(View.VISIBLE);

					NativeResponseDummy adBean = obj;

					// 处理腾讯广告动态3图1图的数据
					View currentLayout = convertView;
					ChannelItemType itemTypeAd = itemType;
					int showTypeAd = mShowType;
					if(itemTypeAd == ChannelItemType.AdThreeSmall){
						if(AdDataDummy.NATIVE_3IMAGE == adBean.getAdPatternType()){
							itemTypeAd = ChannelItemType.AdThreeSmall;
							showTypeAd = Const.NEWS_TYPE_COMMON_AD_THREE_SMALL;
							ViewGroup group = BaseViewHolder.get(convertView, R.id.item_article_relative_three_small_ad_layout);
							if(group == null){
								contentLayout.setVisibility(View.GONE);
								return;
							}
							group.removeAllViews();
							currentLayout = View.inflate(getContext(), R.layout.item_article_relative_three_small, null);
							group.addView(currentLayout);
						}else{
							itemTypeAd = ChannelItemType.OneSmallAD;
							showTypeAd = Const.NEWS_TYPE_COMMON_AD;
							ViewGroup group = BaseViewHolder.get(convertView, R.id.item_article_relative_three_small_ad_layout);
							if(group == null){
								contentLayout.setVisibility(View.GONE);
								return;
							}
							group.removeAllViews();
							currentLayout = View.inflate(getContext(), R.layout.item_article_relative_ad_one_small, null);
							group.addView(currentLayout);
						}
					}
					final TextView title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
					final ImageView image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
					final ImageView image1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image1);
					final ImageView image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
					final TextView commentnum = BaseViewHolder.get(currentLayout, R.id.item_channel_item_commentnum);

					title.setText(adBean.getTitle());

					TextView desc = BaseViewHolder.get(convertView, R.id.item_channel_item_desc);
					if(desc != null){
						desc.setText(adBean.getDesc());
					}

					if (!isNoPic) {
						if(showTypeAd == Const.NEWS_TYPE_COMMON_AD_THREE_SMALL){
							updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
							updateSize(image1, mImageThreeSmallWidth, mImageThreeSmallHeight);
							updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
							if(!Common.isListEmpty(adBean.getMultiPicUrls()) && adBean.getMultiPicUrls().size() > 2){
								image.setVisibility(View.VISIBLE);
								image1.setVisibility(View.VISIBLE);
								image2.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(0), image, getImageOptions(finalBean));
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(1), image1, getImageOptions(finalBean));
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(2), image2, getImageOptions(finalBean));
								if (Common.isTrue(SettingManager.getInst().getNightModel())) {
									image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
									image1.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
									image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
								}
							}else{
								ImageLoader.getInstance().cancelDisplayTask(image);
								ImageLoader.getInstance().cancelDisplayTask(image1);
								ImageLoader.getInstance().cancelDisplayTask(image2);
								image.setVisibility(View.GONE);
								image1.setVisibility(View.GONE);
								image2.setVisibility(View.GONE);
							}
						}else{
							if(showTypeAd == Const.NEWS_TYPE_COMMON_AD ){
								updateSize(image, mImageOneSmallWidth, mImageOneSamllHeight);
							}
							if (!TextUtils.isEmpty(obj.getImageUrl())) {
								image.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(obj.getImageUrl(), image, mOptions);
							} else if (!TextUtils.isEmpty(obj.getIconUrl())) {
								image.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(obj.getIconUrl(), image, mOptions);
							} else {
								ImageLoader.getInstance().cancelDisplayTask(image);
								image.setVisibility(View.GONE);
							}
							if (Common.isTrue(SettingManager.getInst().getNightModel())) {
								image.setColorFilter(mContext.getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							}
						}
					} else {
						ImageLoader.getInstance().cancelDisplayTask(image);
						if(image != null){
							image.setVisibility(View.GONE);
						}
					}
					image.setTag(adBean);

					adBean.recordImpression(convertView);
					convertView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							View tagView = v.findViewById(R.id.item_channel_item_image);
							Object tag = tagView.getTag();
							if(tag instanceof NativeResponseDummy){
								NativeResponseDummy adBean = (NativeResponseDummy) tag;
								adBean.handleClick(v);
							}
						}
					});
				}
			});
		} else {
			View currentLayout = convertView;
			final TextView title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
			final ImageView image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
			final ImageView image1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image1);
			final ImageView image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
			final TextView commentnum = BaseViewHolder.get(convertView, R.id.item_channel_item_commentnum);
			if (!isNoPic) {
				switch (itemType) {
					default:
					case OneSmall:
						updateSize(image, mImageOneSmallWidth, mImageOneSamllHeight);
						if (!TextUtils.isEmpty(bean.getImage())) {
							image.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage(), image, getImageOptions(bean));
						} else {
							ImageLoader.getInstance().cancelDisplayTask(image);
							image.setVisibility(View.GONE);
						}
						if (Common.isTrue(SettingManager.getInst().getNightModel())) {
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
						}
						break;
					case OneBig:
						if (!TextUtils.isEmpty(bean.getImage())) {
							image.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage(), image, getImageOptions(bean));
						} else {
							ImageLoader.getInstance().cancelDisplayTask(image);
							image.setVisibility(View.GONE);
						}
						if (Common.isTrue(SettingManager.getInst().getNightModel())) {
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
						}
						break;
					case ThreeSmall:
						List<String> imgs = bean.getImage_arr();
						image.setVisibility(View.VISIBLE);
						image1.setVisibility(View.VISIBLE);
						image2.setVisibility(View.VISIBLE);
						updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
						updateSize(image1, mImageThreeSmallWidth, mImageThreeSmallHeight);
						updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
						ImageLoader.getInstance().displayImage(imgs.get(0), image, getImageOptions(bean));
						ImageLoader.getInstance().displayImage(imgs.get(1), image1, getImageOptions(bean));
						ImageLoader.getInstance().displayImage(imgs.get(2), image2, getImageOptions(bean));
						if (Common.isTrue(SettingManager.getInst().getNightModel())) {
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							image1.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
						}
						break;
					case OneBigAD:

				}
			} else {
				ImageLoader.getInstance().cancelDisplayTask(image);
				if(image != null){
					image.setVisibility(View.GONE);
				}
			}
			title.setText(bean.getTitle());
			image.setTag(bean);
			handleComment(commentnum, bean);
			convertView.setOnClickListener(clickListener);
		}

	}
	
	private void handleComment(TextView commentnum, MChannelItemBean bean) {
		if (commentnum == null) {
			return;
		}
		if (TextUtils.equals(bean.getChannel(), Const.CHANNEL_ARTICLE_WEB)) {
			commentnum.setText(bean.getRed_tag());
		} else if(bean.getComments() != null) {
			commentnum.setText(bean.getComments() + "评");
		}
	}
	
	private DisplayImageOptions getImageOptions(MChannelItemBean bean){
		return getImageOptions(bean, false);
	}
	
	private DisplayImageOptions getImageOptions(MChannelItemBean bean, boolean forceBig){
		if(Const.CHANNEL_ARTICLE_VIDEO.equals(bean.getChannel())){
			if(forceBig || Const.NEWS_TYPE_ONE_BIG_PIC == bean.getNews_show_type()){
				return mOptionsWithImage;
			}else{
				return mOptionsWithImageSmall;
			}
		}else{
			return mOptions;
		}
	}
	
	public void handleBeanClick(MChannelItemBean bean){
		LocalStateServer.getInst(getContext()).setReadStateRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid());
		Utils.handleBeanClick(getContext(), bean, "assoc");
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			View tagView = view.findViewById(R.id.item_channel_item_image);
			Common.handleTextViewReaded(getContext(), view, R.id.item_channel_item_title, true);
			
			MChannelItemBean bean = (MChannelItemBean) tagView.getTag();
			handleBeanClick(bean);
		}
	};
	
	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	
}
