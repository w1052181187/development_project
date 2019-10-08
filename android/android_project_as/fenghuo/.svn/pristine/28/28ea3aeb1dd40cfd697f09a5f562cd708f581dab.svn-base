package com.chengning.fenghuo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.base.Clearable;
import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.IIsRecom;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.ad.AdDataDummy.NativeResponseDummy;
import com.chengning.fenghuo.ad.AdDataListener;
import com.chengning.fenghuo.ad.AdDataManager;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.MyCoverRoundedBitmapDisplayer;
import com.chengning.fenghuo.util.MyRoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ChannelItemRecommendAdapter extends BaseTypeListAdapter implements Clearable, IIsRecom{
	
	// is_special 专栏类型为1
	public static final int TYPE_SPECIAL_COLUMN = 1;
	
	public String isFromRecommend;
	private DisplayImageOptions mOptions;
	private DisplayImageOptions mOptionsWithImage;
	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageTwoSmallWidth;
	private int mImageTwoSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSmallHeight;
	
	private int mImageOneTwoBigWidth;
	private int mImageOneTwoBigHeight;
	private int mImageOneTwoSmallWidth;
	private int mImageOneTwoSmallHeight;
	
	private List<ImageView> mImageViewList;

	private AdDataManager mAdDataManager;

	private int mPreListSize;

	private DisplayImageOptions mOptionsWithImageSmall;
	
	private boolean isRecom = false;
	private boolean mIsHomeRecom = false;
	private boolean mIsReadEnable = true;
	
	private OnClickListener refreshListener;

	public boolean isRecom() {
		return isRecom;
	}

	public void setIsRecom(boolean isRecom) {
		this.isRecom = isRecom;
	}

	public boolean isHomeRecom() {
		return mIsHomeRecom;
	}

	public void setHomeRecom(boolean isHomeRecom) {
		this.mIsHomeRecom = isHomeRecom;
	}
	
	public void setIsReadEnable(boolean enable){
		this.mIsReadEnable = enable;
	}
	
	public ChannelItemRecommendAdapter(Activity activity, List list, String isFromRecommend) {
		super(activity, list);
		this.isFromRecommend = isFromRecommend;
		mImageViewList = new ArrayList<ImageView>();
		
		mOptions = new DisplayImageOptions.Builder() 
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
 		
 		// 烽火v1.4图片宽高
// 		DisplayUtil.getInst().init(activity);
// 		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
// 		
// 		mImageThreeSmallWidth = (DisplayUtil.getInst().getScreenWidth() - gapWidth * 4) / 3 ;
// 		mImageThreeSmallHeight = activity.getResources().getDimensionPixelSize(R.dimen.channel_item_image_three_height);
//		mImageTwoSmallWidth = (DisplayUtil.getInst().getScreenWidth() - gapWidth * 3) / 2;
//		mImageTwoSmallHeight = activity.getResources().getDimensionPixelSize(R.dimen.channel_item_image_two_height);
//		mImageOneSmallWidth = (DisplayUtil.getInst().getScreenWidth() - gapWidth * 2) * 2 / 3;
//		mImageOneSmallHeight = 0;

 		// 环球新军事v2.4图片宽高
		DisplayUtil.getInst().init(activity);
		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		int gapThreeSmall = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin_three_small); 
		float imageLayoutWidth = (float)(DisplayUtil.getInst().getScreenWidth() - gapWidth * 2);
		
		mImageThreeSmallWidth =  ((int)imageLayoutWidth - gapThreeSmall * 2) / 3;
		mImageThreeSmallHeight = (int) ((float)mImageThreeSmallWidth / 1.52f + 0.3f);
		mImageOneSmallWidth = mImageThreeSmallWidth;
		mImageOneSmallHeight = mImageThreeSmallHeight;
		
		int gap2Width = activity.getResources().getDimensionPixelSize(R.dimen.tuwen_image_gap);
		mImageOneTwoBigWidth = (int) (imageLayoutWidth * 0.615f + 0.5f);
		mImageOneTwoSmallWidth = (int) (imageLayoutWidth - gap2Width - mImageOneTwoBigWidth);
		mImageOneTwoSmallHeight = (int) (((float)mImageOneTwoSmallWidth) / 1.338f + 0.5f);
		mImageOneTwoBigHeight = mImageOneTwoSmallHeight * 2 + gap2Width;
		
		mAdDataManager = new AdDataManager(getContext());
	}
	
	@Override
	public void notifyDataSetChanged(){
		int size = getCount();
		if(size > mPreListSize){
			// 分页增加
			mAdDataManager.resetListenerMap();
		}else{
			mAdDataManager.reset();
		}
		for(int i = 0; i < getCount(); i++){
			ChannelItemBean bean = (ChannelItemBean) getItem(i);
			if(TextUtils.equals(Consts.CONTENT_TYPE_AD, bean.getContent_type())){
				// ad
				mAdDataManager.getData(bean.getAd_place_id(), i, mEmptyAdDataListener);
			}
		}
		
		mPreListSize = size;
		super.notifyDataSetChanged();
	}
	
	@Override
	public void clear() {
		mAdDataManager.reset();
	}
	
	private AdDataListener mEmptyAdDataListener = new AdDataListener() {
		
		@Override
		public void onData(NativeResponseDummy obj) {
			
		}
	};

	public static enum ChannelItemType {
		OneSmall(0, false, Consts.SHOW_TYPE_ONE_SMALL), 
		OneBigVideo(1, false, Consts.SHOW_TYPE_ONE_BIG_VIDEO),
		ThreeSmall(2, false, Consts.SHOW_TYPE_THREE_SMALL), 
		OneBig(3, false, Consts.SHOW_TYPE_ONE_BIG), 
		OneSmallVideo(4, false, Consts.SHOW_TYPE_ONE_SMALL_VIDEO), // 视频一小图
		OneBigTwoSmall(5, false, Consts.SHOW_TYPE_ONE_BIG_TWO_SMALL),
		Ad(6, true, Consts.SHOW_TYPE_ONE_SMALL_AD), // 广告一小图
		AdOneBig(7, true, Consts.SHOW_TYPE_ONE_BIG_AD), // 广告一大图
		AdThreeSmall(8, true, Consts.SHOW_TYPE_THREE_SMALL_AD), // 广告三小图
		;
		private int type;
		private int showType[];
		private boolean isAd;
		
		private ChannelItemType(int type, boolean isAd, int... showType){
			this.type = type;
			this.isAd = isAd;
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
		public static ChannelItemType parseItemType(int showType, Object obj){
			ChannelItemType it = ChannelItemType.OneSmall;
			for(ChannelItemType c : values()){
				for(int i : c.showType){
					if(c.isAd){
						if (obj != null
								&& obj instanceof ChannelItemBean
								&& TextUtils.equals(((ChannelItemBean) obj).getContent_type(), Consts.CONTENT_TYPE_AD)) {
							// is ad
						}else{
							continue;
						}
					}
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
		ChannelItemBean bean = (ChannelItemBean) getItem(position);
		ChannelItemType itemType = ChannelItemType.parseItemType(bean.getShow_type(), getItem(position));
		if(itemType.isAd){
			return itemType.getType();
		}else if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
			// 无图模式固定样式
			return ChannelItemType.OneSmall.getType();
		}else{
			return itemType.getType();
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
	public int buildLayoutId(int position, int type) {
		return -1;
	}
	
	@Override
	public View buildLayoutView(int position,int type){
		ChannelItemType itemType = ChannelItemType.parseType(type);
		View view = View.inflate(getContext(), R.layout.item_channel_item_container, null);
		ViewStub stub = (ViewStub) view.findViewById(R.id.item_channel_item_stub);
		switch(itemType){
			 default:
			 case OneSmall:
				 stub.setLayoutResource(R.layout.item_channel_item_one_small);
				 break;
			 case OneBigVideo:
				 stub.setLayoutResource(R.layout.item_channel_item_one_big);
				 break;
			 case ThreeSmall:
				 stub.setLayoutResource(R.layout.item_channel_item_three_small);
				 break;
			 case OneBig:
				 stub.setLayoutResource(R.layout.item_channel_item_one_big);
				 break;
			 case OneSmallVideo:
				 stub.setLayoutResource(R.layout.item_channel_item_one_small);
				 break;
			 case OneBigTwoSmall:
				stub.setLayoutResource(R.layout.item_channel_item_one_big_two_small);
				break;
			 case Ad:
				stub.setLayoutResource(R.layout.item_channel_item_one_small);
				break;
			 case AdOneBig:
				stub.setLayoutResource(Common.isTrue(SettingManager.getInst()
						.getNoPicModel()) ? R.layout.item_channel_item_one_small
								: R.layout.item_channel_item_one_big);
				break;
			 case AdThreeSmall:
				stub.setLayoutResource(Common.isTrue(SettingManager.getInst()
						.getNoPicModel()) ? R.layout.item_channel_item_one_small
								: R.layout.item_channel_item_three_small);
				break;
		}
		stub.inflate();
		return view;
	}
	@Override
	public void handleLayout(final View convertView, final int position, Object obj,
			int type) {
		View contentLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_content_layout);
		final ChannelItemType itemType = ChannelItemType.parseType(type);
		
		ChannelItemBean bean = (ChannelItemBean) getItem(position);
		String contentType = bean.getContent_type();
		
		final boolean isNoPic = Common.isTrue(SettingManager.getInst().getNoPicModel());
		
		if((itemType == ChannelItemType.Ad || itemType == ChannelItemType.AdOneBig || itemType == ChannelItemType.AdThreeSmall || isNoPic) 
				&& TextUtils.equals(contentType, Consts.CONTENT_TYPE_AD)){
			contentLayout.setVisibility(View.GONE);
			// 标记位置，防止异步ad返回后view位置变化
			BaseViewHolder.get(convertView, R.id.item_channel_item_time).setTag(position);
			final ChannelItemBean finalBean = bean;
			
			mAdDataManager.getData(bean.getAd_place_id(), position, new AdDataListener() {
				
				@Override
				public void onData(NativeResponseDummy obj) {
					if(convertView == null){
						return;
					}
					Object tagObj = BaseViewHolder.get(convertView, R.id.item_channel_item_time).getTag();
					if(tagObj == null || !(tagObj instanceof Integer) || (Integer)tagObj != position){
						return;
					}
					View contentLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_content_layout);
					contentLayout.setVisibility(View.VISIBLE);
					
					NativeResponseDummy adBean = obj;

//					Log.e(TAG, "aditem 2 position: " + position + ", title: " + adBean.getTitle());
					
					TextView title = null;
					ImageView image;
					ImageView image2 = null;
					ImageView image3 = null;
					TextView tag;
					TextView author;
					TextView comment;
					TextView time;
//					View moreLayout;
//					TextView more;

					View currentLayout = convertView;
//					topTitle = BaseViewHolder.get(convertView, R.id.item_channel_item_top_title);
//					moreLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_more_ll);
//					more = BaseViewHolder.get(convertView, R.id.item_channel_item_more_tv);
					
//					title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_small);
					title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
					if(itemType == ChannelItemType.AdThreeSmall && !Common.isTrue(SettingManager.getInst().getNoPicModel())){
						// 三图广告且不在无图模式下
//						title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_three_small);
						image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
						image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
					}else if(itemType == ChannelItemType.AdOneBig && !Common.isTrue(SettingManager.getInst().getNoPicModel())){
						// 大图广告且不在无图模式下
//						title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_big);
					}else{
//						title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_small);
					}
					
					image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);

					author = BaseViewHolder.get(currentLayout, R.id.item_channel_item_author);
					comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);
					time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
					tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_ad_tag);

					if (!Common.isTrue(SettingManager.getInst().getNoPicModel())) {
						if(itemType == ChannelItemType.AdThreeSmall){
							updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
							updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
							updateSize(image3, mImageThreeSmallWidth, mImageThreeSmallHeight);
							if(!Common.isListEmpty(adBean.getMultiPicUrls()) && adBean.getMultiPicUrls().size() > 2){
								image.setVisibility(View.VISIBLE);
								image2.setVisibility(View.VISIBLE);
								image3.setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(0), image, mOptions);
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(1), image2, mOptions);
								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(2), image3, mOptions);
								if (Common.isTrue(SettingManager.getInst().getNightModel())) {
									image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
									image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
									image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
								}
							}else{
								ImageLoader.getInstance().cancelDisplayTask(image);
								ImageLoader.getInstance().cancelDisplayTask(image2);
								ImageLoader.getInstance().cancelDisplayTask(image3);
								image.setVisibility(View.GONE);
								image2.setVisibility(View.GONE);
								image3.setVisibility(View.GONE);
							}
						}else{
							if(itemType == ChannelItemType.Ad){
								updateSize(image, mImageOneSmallWidth, mImageOneSmallHeight);
							}
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
							if (Common.isTrue(SettingManager.getInst().getNightModel())) {
								image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							}
						}
					} else {
						ImageLoader.getInstance().cancelDisplayTask(image);
						if(image != null){
							image.setVisibility(View.GONE);
						}
						if(itemType == ChannelItemType.AdThreeSmall){
							if(image2 != null){
								image2.setVisibility(View.GONE);
							}
							if(image3 != null){
								image3.setVisibility(View.GONE);
							}
						}
					}

					if(tag != null){
						tag.setVisibility(View.VISIBLE);
					}
					if(comment != null){
						comment.setVisibility(View.GONE);
					}
					title.setText(adBean.getTitle());
					String username = finalBean.getUsername();
					author.setText(username);
					author.setVisibility(TextUtils.isEmpty(username) ? View.GONE : View.VISIBLE);
//					time.setText(finalBean.getCategory());
					handleTime(time, finalBean);
					title.setTag(adBean);
					contentLayout.setTag(adBean);
					
					adBean.recordImpression(convertView);

				}
			});
		} else {
			contentLayout.setVisibility(View.VISIBLE);
			
			TextView title = null;
			ImageView image;
			TextView tag;
			TextView author;
			TextView comment;
			TextView time;
			ImageView image2 = null;
			ImageView image3 = null;
			View refresh;
			
			View currentLayout = convertView;
			
			switch(itemType){
				default:
				case OneSmall:
					break;
				case OneBigVideo:
					break;
				case ThreeSmall:
					image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
					image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
					break;
				case OneBig:
					break;
				case OneBigTwoSmall:
					image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
					image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
					break;
			}
			
			title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
			image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
			tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_ad_tag);
			time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
			author = BaseViewHolder.get(currentLayout, R.id.item_channel_item_author);
			comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);
			refresh = BaseViewHolder.get(convertView, R.id.item_channel_item_click_refresh);
			
			if(!isNoPic){
				switch(itemType){
					default:
					case OneSmall:
						updateSize(image, mImageOneSmallWidth, mImageOneSmallHeight);
					case OneBigVideo:
						if(!TextUtils.isEmpty(((ChannelItemBean) bean).getImage_list())){
							image.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage_list(), image,getDisplayOptions(bean));
						}else{
							ImageLoader.getInstance().cancelDisplayTask(image);
							image.setVisibility(View.GONE);
						}
						if(Common.isTrue(SettingManager.getInst().getNightModel())){
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);						
						}
						break;
					case ThreeSmall:
						updateSize(image,mImageThreeSmallWidth,mImageThreeSmallHeight);
						updateSize(image2,mImageThreeSmallWidth,mImageThreeSmallHeight);
						updateSize(image3,mImageThreeSmallWidth,mImageThreeSmallHeight);
						if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
							image.setVisibility(View.VISIBLE);
							image2.setVisibility(View.VISIBLE);
							image3.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), image ,getDisplayOptions(bean));
							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), image2 ,getDisplayOptions(bean));
							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), image3 ,getDisplayOptions(bean));
							if(Common.isTrue(SettingManager.getInst().getNightModel())){
								image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
								image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
								image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
							}
						}else{
							ImageLoader.getInstance().cancelDisplayTask(image);
							ImageLoader.getInstance().cancelDisplayTask(image2);
							ImageLoader.getInstance().cancelDisplayTask(image3);
							image.setVisibility(View.GONE);
							image2.setVisibility(View.GONE);
							image3.setVisibility(View.GONE);
						}
						break;
					case OneBig:
						if(!TextUtils.isEmpty(((ChannelItemBean) bean).getImage_list())){
							image.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage_list(), image,getDisplayOptions(bean));
						}else{
							ImageLoader.getInstance().cancelDisplayTask(image);
							image.setVisibility(View.GONE);
						}
						if(Common.isTrue(SettingManager.getInst().getNightModel())){
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);						
						}
						break;
					case OneSmallVideo:
						updateSize(image, mImageOneSmallWidth, mImageOneSmallHeight);
						if(!TextUtils.isEmpty(((ChannelItemBean) bean).getImage_list())){
							image.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage_list(), image,getDisplayOptions(bean));
						}else{
							ImageLoader.getInstance().cancelDisplayTask(image);
							image.setVisibility(View.GONE);
						}
						if(Common.isTrue(SettingManager.getInst().getNightModel())){
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);						
						}
						break;
					case OneBigTwoSmall:
						updateSize(image, mImageOneTwoBigWidth, mImageOneTwoBigHeight);
						updateSize(image2, mImageOneTwoSmallWidth, mImageOneTwoSmallHeight);
						updateSize(image3, mImageOneTwoSmallWidth, mImageOneTwoSmallHeight);
						if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
							image.setVisibility(View.VISIBLE);
							image2.setVisibility(View.VISIBLE);
							image3.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), image, getDisplayOptions(bean));
							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), image2, getDisplayOptions(bean));
							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), image3, getDisplayOptions(bean));
							if (Common.isTrue(SettingManager.getInst().getNightModel())) {
								image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
								image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
								image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							}
						}else{
							ImageLoader.getInstance().cancelDisplayTask(image);
							ImageLoader.getInstance().cancelDisplayTask(image2);
							ImageLoader.getInstance().cancelDisplayTask(image3);
							image.setVisibility(View.GONE);
							image2.setVisibility(View.GONE);
							image3.setVisibility(View.GONE);
						}
						break;
				}
			}else{
				ImageLoader.getInstance().cancelDisplayTask(image);
				if(image != null){
					image.setVisibility(View.GONE);
				}
				if(itemType == ChannelItemType.ThreeSmall){
					image2.setVisibility(View.GONE);
					image3.setVisibility(View.GONE);
				}
			}
			
			title.setText(bean.getTitle());
			if(mIsReadEnable){
				Common.handleTextViewReaded(getContext(), title, LocalStateDA.getInst(getContext()).isRead(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid()));
			}
			// item

			if(tag != null){
				tag.setVisibility(View.GONE);
			}
			handleTime(time, bean);
			handleFrom(author,bean);
			handleComment(comment,bean);
			contentLayout.setTag(bean);
			
			title.setTag(bean);
			//设置刚刚看到这里，点击刷新
			refresh.setVisibility(!TextUtils.isEmpty(bean.getTips()) ? View.VISIBLE : View.GONE);
			refresh.setOnClickListener(refreshListener);
		}
	}
	
	private void handleComment(TextView comment, ChannelItemBean bean) {
		if (comment == null) {
			return;
		}
		int count = bean.getReplys();
		comment.setText(count+"评论");
		comment.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
	}

	private void handleFrom(TextView author, ChannelItemBean bean) {
		// TODO Auto-generated method stub
		// 推荐下第一页来源显示栏目名称，否则显示来源
		if (author == null) {
			return;
		}
		author.setVisibility(View.VISIBLE);
//		author.setText((isRecom && bean.getLocal_page() == JUrl.PAGE_START) ? 
//				bean.getCategory() : bean.getList_from());
		author.setText(bean.getList_from());
//		author.setText(bean.getUsername());
	}

	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	private boolean isCircle(int position){
		if(getItem(position) instanceof DynamicItemBean){
			return true;
		}
		else{
			return false;	
		}
	}
	public DisplayImageOptions getDisplayOptions(BaseChannelItemBean  mBean){
		if(mBean instanceof DynamicItemBean){
			return mOptions;
		}else{
			int type = ((ChannelItemBean)mBean).getShow_type();
			if (type == Consts.SHOW_TYPE_ONE_BIG_VIDEO){
				return mOptionsWithImage;
			} else if (type == Consts.SHOW_TYPE_ONE_SMALL_VIDEO){
				return mOptionsWithImageSmall;
			}
			return mOptions;
		}
	}
	
	public static boolean isColumn(BaseChannelItemBean bean){
		if(bean instanceof ChannelItemBean 
				&& (TYPE_SPECIAL_COLUMN == ((ChannelItemBean) bean).getIs_special())){ 
			// 专栏
			return true;
		}else{
			return false;
		}
	}
	
	private void setImageViewGone(){
		for (ImageView o : mImageViewList) {
			o.setVisibility(View.GONE);
		}
	}
	
	public void handleTime(TextView tv, ChannelItemBean bean){
		long time = Common.dateToLong(bean.getDateline());
//		String date = null;
//		if (time == 0) {
//			date = Common.getDateMMDDNotNull(bean.getDateline());
//		} else {
//			date = Common.formatTimeHoursMinutesBefore(time);
//		}
//		tv.setText(date);
		tv.setText(Common.formatTimestmpByJs(time));
	}
	
	public static String getNewMaxIdNoAd(List<ChannelItemBean> list){
		if(!Common.isListEmpty(list)){
			for(ChannelItemBean bean : list){
				if(!TextUtils.isEmpty(bean.getAd_place_id())){
//					continue;
				}else{
					return bean.getTid();
				}
			}
		}
		return null;
	}
	
	public void  setRefreshListener(OnClickListener listener) {
		this.refreshListener = listener;
	}
}
