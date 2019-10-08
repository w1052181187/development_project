package com.chengning.yiqikantoutiao.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.DisplayUtil;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.activity.ArticleActivity;
import com.chengning.yiqikantoutiao.ad.AdDataDummy.NativeResponseDummy;
import com.chengning.yiqikantoutiao.ad.AdDataListener;
import com.chengning.yiqikantoutiao.ad.AdDataManager;
import com.chengning.yiqikantoutiao.data.access.LocalStateDA;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.MyCoverRoundedBitmapDisplayer;
import com.chengning.yiqikantoutiao.util.MyRoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class ArticleRelativeItemAdapter extends BaseTypeListAdapter {

	private Activity mContext;
	private DisplayImageOptions mOptions;
	private AdDataManager mAdDataManager;
	private int mAdOffset;
	
	
	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSamllHeight;
	private DisplayImageOptions mOptionsWithImage;
	private DisplayImageOptions mOptionsWithImageSmall;

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
	
	public ArticleRelativeItemAdapter(Activity activity, List list, AdDataManager ad, int adOffset) {
		super(activity, list);

		this.mContext = activity;
		this.mAdOffset = adOffset;
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
		OneSmall(0, Consts.SHOW_TYPE_ONE_SMALL),	// 1个小图
		OneBig(1, Consts.SHOW_TYPE_ONE_BIG),	// 1个大图
		OneBigAD(2, Consts.SHOW_TYPE_ONE_BIG_AD),	// 1个大图广告
		ThreeSmall(3, Consts.SHOW_TYPE_THREE_SMALL), // 3个小图
		OneSmallVideo(4, Consts.SHOW_TYPE_ONE_SMALL_VIDEO), // 1小视频
		OneBigVideo(5, Consts.SHOW_TYPE_ONE_BIG_VIDEO), // 一大视频
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
			ChannelItemBean bean = (ChannelItemBean) getItem(position);
			return ChannelItemType.parseShowType(bean.getShow_type()).getType();
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
			view = View.inflate(getContext(), R.layout.item_article_relative_one_small, null);
			break;
		case OneSmallVideo:
			view = View.inflate(getContext(), R.layout.item_article_relative_one_small, null);
			break;
		case OneBig:
			view = View.inflate(getContext(), R.layout.item_article_relative_one_big, null);
			break;
		case OneBigAD:
			view = View.inflate(getContext(), R.layout.item_article_ad_big, null);
			break;
		case OneBigVideo:
			view = View.inflate(getContext(), R.layout.item_article_relative_one_big, null);
			break;
		case ThreeSmall:
			view = View.inflate(getContext(), R.layout.item_article_relative_three_small, null);
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
                             final int type) {
		ChannelItemBean bean = (ChannelItemBean) getItem(position);

		// type
		View contentLayout = convertView;
		contentLayout.setVisibility(View.VISIBLE);
		final ChannelItemType itemType = ChannelItemType.parseType(type);
		
		View currentLayout = convertView;
		final View close = BaseViewHolder.get(currentLayout, R.id.item_channel_item_close);
		final TextView title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
		final TextView from = BaseViewHolder.get(currentLayout, R.id.item_channel_item_from);
		
		final View imageLL = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image_ll);
		final ImageView image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
		final ImageView image1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image1);
		final ImageView image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
		final boolean isNoPic = Common.isTrue(SettingManager.getInst().getNoPicModel());
		
		// TODO(close image)
		final boolean closeImage = true;
		if (!closeImage && !isNoPic) {
			switch (itemType) {
			default:
			case OneSmall:
				updateSize(image, mImageOneSmallWidth, mImageOneSamllHeight);
				if (!TextUtils.equals(bean.getContent_type(), String.valueOf(Consts.CONTENT_TYPE_AD))) {
					if (!TextUtils.isEmpty(bean.getImage_list())) {
						image.setVisibility(View.VISIBLE);
						ImageLoader.getInstance().displayImage(bean.getImage_list(), image, getImageOptions(bean));
					} else {
						ImageLoader.getInstance().cancelDisplayTask(image);
						image.setVisibility(View.GONE);
					}
				} 
				
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			case OneSmallVideo:
				updateSize(image, mImageOneSmallWidth, mImageOneSamllHeight);
				if (!TextUtils.isEmpty(bean.getImage_list())) {
					image.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(bean.getImage_list(), image, getImageOptions(bean));
				} else {
					ImageLoader.getInstance().cancelDisplayTask(image);
					image.setVisibility(View.GONE);
				}
				
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			case OneBig:
				if (!TextUtils.isEmpty(bean.getImage_list())) {
					image.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(bean.getImage_list(), image, getImageOptions(bean));
				} else {
					ImageLoader.getInstance().cancelDisplayTask(image);
					image.setVisibility(View.GONE);
				}
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			case OneBigVideo:
				if (!TextUtils.isEmpty(bean.getImage_list())) {
					image.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(bean.getImage_list(), image, getImageOptions(bean));
				} else {
					ImageLoader.getInstance().cancelDisplayTask(image);
					image.setVisibility(View.GONE);
				}
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			case ThreeSmall:
				updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(image1, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
				if (!TextUtils.equals(bean.getContent_type(), String.valueOf(Consts.CONTENT_TYPE_AD))) {
					List<String> imgs = bean.getImage_arr();
					if(!Common.isListEmpty(imgs)&& imgs.size() > 2){
						if (imageLL != null) {
							imageLL.setVisibility(View.VISIBLE);
						}
						image.setVisibility(View.VISIBLE);
						image1.setVisibility(View.VISIBLE);
						image2.setVisibility(View.VISIBLE);
						updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
						updateSize(image1, mImageThreeSmallWidth, mImageThreeSmallHeight);
						updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
						ImageLoader.getInstance().displayImage(imgs.get(0), image, getImageOptions(bean));
						ImageLoader.getInstance().displayImage(imgs.get(1), image1, getImageOptions(bean));
						ImageLoader.getInstance().displayImage(imgs.get(2), image2, getImageOptions(bean));
					} else {
						if (imageLL != null) {
							imageLL.setVisibility(View.GONE);
						}
					}
				}
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
					image1.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
					image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			case OneBigAD:
				break;
			}
		} else {
			ImageLoader.getInstance().cancelDisplayTask(image);
			if(image != null){
				image.setVisibility(View.GONE);
			}
		}

		String contentType = bean.getContent_type();
		
		if((itemType == ChannelItemType.OneSmall || itemType == ChannelItemType.OneBigAD || isNoPic ||
				itemType == ChannelItemType.ThreeSmall) 
				&& TextUtils.equals(contentType, Consts.CONTENT_TYPE_AD)){

			contentLayout.setVisibility(View.GONE);
			// 标记位置，防止异步ad返回后view位置变化
			BaseViewHolder.get(convertView, R.id.item_channel_item_title).setTag(position);
			final ChannelItemBean finalBean = bean;
			
			mAdDataManager.getData(finalBean.getAd_place_id(), position + mAdOffset, new AdDataListener() {
				
				@Override
				public void onData(NativeResponseDummy obj) {
					if(convertView == null){
						return;
					}
					Object tagObj = BaseViewHolder.get(convertView, R.id.item_channel_item_title).getTag();
					if(tagObj == null || !(tagObj instanceof Integer) || (Integer)tagObj != position){
						return;
					}
					
					View contentLayout = convertView;
					contentLayout.setVisibility(View.VISIBLE);
					
					final NativeResponseDummy adBean = obj;

					title.setText(adBean.getTitle());
					title.setTag(adBean);
					if (from != null) {
						from.setText(finalBean.getCategory());
						from.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					}

					if(!(itemType == ChannelItemType.OneBigAD)){
						image.setVisibility(View.GONE);
					}
					if (!isNoPic && (itemType == ChannelItemType.OneBigAD)) {
						int mShowType = finalBean.getShow_type();
						if(mShowType == Consts.SHOW_TYPE_THREE_SMALL){
							if(!Common.isListEmpty(adBean.getMultiPicUrls()) && adBean.getMultiPicUrls().size() > 2){

								if (imageLL != null) {
									imageLL.setVisibility(View.VISIBLE);
								}
								image.setVisibility(View.VISIBLE);
								image1.setVisibility(View.VISIBLE);
								image2.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(0), image, getImageOptions(finalBean));
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(1), image1, getImageOptions(finalBean));
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(2), image2, getImageOptions(finalBean));
							}else{
								ImageLoader.getInstance().cancelDisplayTask(image);
								ImageLoader.getInstance().cancelDisplayTask(image1);
								ImageLoader.getInstance().cancelDisplayTask(image2);
								if (imageLL != null) {
									imageLL.setVisibility(View.GONE);
								}
								image.setVisibility(View.GONE);
								image1.setVisibility(View.GONE);
								image2.setVisibility(View.GONE);
							}
						}else{
							if (!TextUtils.isEmpty(adBean.getImageUrl())) {
								image.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(adBean.getImageUrl(), image, mOptions);
							} else if (!TextUtils.isEmpty(adBean.getIconUrl())) {
								image.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(adBean.getIconUrl(), image, mOptions);
							} else {
								ImageLoader.getInstance().cancelDisplayTask(image);
								image.setVisibility(View.GONE);
							}
						}
					} else {
						ImageLoader.getInstance().cancelDisplayTask(image);
						if(image != null){
							image.setVisibility(View.GONE);
						}
					}
					View tagViewA = convertView.findViewById(R.id.item_channel_item_line);
					tagViewA.setTag(adBean);
					
					adBean.recordImpression(convertView);
					if(close != null){
						close.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								convertView.setVisibility(View.GONE);
							}
						});
					}
					convertView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							View tagView = v.findViewById(R.id.item_channel_item_title);
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
			title.setText(bean.getTitle());
			handleFrom(from,bean);
			convertView.findViewById(R.id.item_channel_item_line).setTag(bean);

//			Common.handleTextViewReaded(getContext(), title,
//					LocalStateDA.getInst(getContext()).isRead(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid()));
			
			convertView.setOnClickListener(clickListener);
		}
		
	}
	
	private void handleFrom(TextView from, ChannelItemBean bean) {
		if (from == null) {
			return;
		}
		from.setText(bean.getFrom());
		from.setCompoundDrawablePadding(getContext().getResources().getDimensionPixelSize(R.dimen.channel_item_left_bottom_drawable_padding));
		from.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tuijian_shouye_list_aritcle_icon, 0, 0, 0);
	}

	private DisplayImageOptions getImageOptions(ChannelItemBean bean){
		int type = ((ChannelItemBean)bean).getShow_type();
		if (type == Consts.SHOW_TYPE_ONE_BIG_VIDEO){
			return mOptionsWithImage;
		} else if (type == Consts.SHOW_TYPE_ONE_SMALL_VIDEO){
			return mOptionsWithImageSmall;
		}
		return mOptions;
	}
	
	
	public void handleBeanClick(ChannelItemBean bean){
		LocalStateDA.getInst(getContext()).setReadStateRead(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid());

		ArticleActivity.launch(getContext(), bean);
	}
	
	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			View tagView = view.findViewById(R.id.item_channel_item_line);
//			Common.handleTextViewReaded(getContext(), view, R.id.item_channel_item_title, true);
			
			ChannelItemBean bean = (ChannelItemBean) tagView.getTag();
			handleBeanClick(bean);
		}
	};
	
}
