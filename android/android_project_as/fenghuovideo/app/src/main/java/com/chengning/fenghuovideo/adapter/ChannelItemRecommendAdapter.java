package com.chengning.fenghuovideo.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.base.Clearable;
import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.IIsRecom;
import com.chengning.fenghuovideo.JKDebug;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.activity.MainActivity;
import com.chengning.fenghuovideo.activity.VideoDetailActivity;
import com.chengning.fenghuovideo.base.AbstractChannelItemListFragment;
import com.chengning.fenghuovideo.data.access.ChannelItemListDA;
import com.chengning.fenghuovideo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.nicevideo.FhItemData;
import com.chengning.fenghuovideo.nicevideo.FhNiceVideoWidget;
import com.chengning.fenghuovideo.nicevideo.FhVideoDetailWidget;
import com.chengning.fenghuovideo.nicevideo.FhVideoPlayerController;
import com.chengning.fenghuovideo.util.ArticleCommentListeners;
import com.chengning.fenghuovideo.util.ArticleManagerUtils;
import com.chengning.fenghuovideo.util.ArticleManagerUtils.DataStateListener;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.Utils;
import com.chengning.fenghuovideo.widget.ArticleShareDialog;
import com.chengning.fenghuovideo.widget.VideoItemWidget;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

public class ChannelItemRecommendAdapter extends BaseTypeListAdapter implements Clearable, IIsRecom {
	
	// is_special 专栏类型为1
	public static final int TYPE_SPECIAL_COLUMN = 1;
    private final ChannelItemListDA mItemDA;

    public String isFromRecommend;
	private DisplayImageOptions mOptions;
	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSmallHeight;
	
	private List<ImageView> mImageViewList;

//	private AdDataManager mAdDataManager;

	private int mPreListSize;
	
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
	
	private View mListView;

	public ChannelItemRecommendAdapter(Activity activity, List list, String isFromRecommend, View listView) {
		super(activity, list);
		this.isFromRecommend = isFromRecommend;
		this.mListView = listView;
		mImageViewList = new ArrayList<ImageView>();
		
		mOptions = new DisplayImageOptions.Builder() 
		.showStubImage(R.mipmap.loading)
		.showImageForEmptyUri(R.mipmap.loading)
		.showImageOnFail(R.mipmap.loading)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
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
		
//		int gap2Width = activity.getResources().getDimensionPixelSize(R.dimen.tuwen_image_gap);
//		mImageOneTwoBigWidth = (int) (imageLayoutWidth * 0.615f + 0.5f);
//		mImageOneTwoSmallWidth = (int) (imageLayoutWidth - gap2Width - mImageOneTwoBigWidth);
//		mImageOneTwoSmallHeight = (int) (((float)mImageOneTwoSmallWidth) / 1.338f + 0.5f);
//		mImageOneTwoBigHeight = mImageOneTwoSmallHeight * 2 + gap2Width;
		
//		mAdDataManager = new AdDataManager(getContext());

        mItemDA = ChannelItemListDA.getInst(getContext());
	}

	public View getListView() {
		return mListView;
	}

	public void setListView(View mListView) {
		this.mListView = mListView;
	}
	
	@Override
	public void notifyDataSetChanged(){
		int size = getCount();
//		if(size > mPreListSize){
//			// 分页增加
//			mAdDataManager.resetListenerMap();
//		}else{
//			mAdDataManager.reset();
//		}
//		for(int i = 0; i < getCount(); i++){
//			ChannelItemBean bean = (ChannelItemBean) getItem(i);
//			if(TextUtils.equals(Consts.CONTENT_TYPE_AD, bean.getContent_type())){
//				// ad
//				mAdDataManager.getData(bean.getAd_place_id(), i, mEmptyAdDataListener);
//			}
//		}
		
		mPreListSize = size;
//		if(JKDebug.DEBUG){
//			// TODO(debug)
//			ArrayList listTmp = new ArrayList();
//			listTmp.addAll(getList());
//			getList().addAll(listTmp);
//		}
		super.notifyDataSetChanged();
	}
	
	@Override
	public void clear() {
//		mAdDataManager.reset();
	}
	
//	private AdDataListener mEmptyAdDataListener = new AdDataListener() {
//
//		@Override
//		public void onData(NativeResponseDummy obj) {
//
//		}
//	};

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
//				 stub.setLayoutResource(R.layout.item_channel_item_one_small);
				 break;
			 case OneBigVideo:
				 stub.setLayoutResource(R.layout.item_channel_item_video);
				 break;
			 case ThreeSmall:
//				 stub.setLayoutResource(R.layout.item_channel_item_three_small);
				 break;
			 case OneBig:
//				 stub.setLayoutResource(R.layout.item_channel_item_one_big);
				 break;
			 case OneSmallVideo:
				 stub.setLayoutResource(R.layout.item_channel_item_one_small);
				 break;
			 case OneBigTwoSmall:
//				stub.setLayoutResource(R.layout.item_channel_item_one_big_two_small);
				break;
			 case Ad:
//				stub.setLayoutResource(R.layout.item_channel_item_one_small);
				break;
			 case AdOneBig:
//				stub.setLayoutResource(Common.isTrue(SettingManager.getInst()
//						.getNoPicModel()) ? R.layout.item_channel_item_one_small
//								: R.layout.item_channel_item_one_big);
				break;
			 case AdThreeSmall:
//				stub.setLayoutResource(Common.isTrue(SettingManager.getInst()
//						.getNoPicModel()) ? R.layout.item_channel_item_one_small
//								: R.layout.item_channel_item_three_small);
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
		
//		if((itemType == ChannelItemType.Ad || itemType == ChannelItemType.AdOneBig || itemType == ChannelItemType.AdThreeSmall || isNoPic)
//				&& TextUtils.equals(contentType, Consts.CONTENT_TYPE_AD)){
//			contentLayout.setVisibility(View.GONE);
//			// 标记位置，防止异步ad返回后view位置变化
//			BaseViewHolder.get(convertView, R.id.item_channel_item_time).setTag(position);
//			final ChannelItemBean finalBean = bean;
//
//			mAdDataManager.getData(bean.getAd_place_id(), position, new AdDataListener() {
//
//				@Override
//				public void onData(NativeResponseDummy obj) {
//					if(convertView == null){
//						return;
//					}
//					Object tagObj = BaseViewHolder.get(convertView, R.id.item_channel_item_time).getTag();
//					if(tagObj == null || !(tagObj instanceof Integer) || (Integer)tagObj != position){
//						return;
//					}
//					View contentLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_content_layout);
//					contentLayout.setVisibility(View.VISIBLE);
//
//					NativeResponseDummy adBean = obj;
//
////					Log.e(TAG, "aditem 2 position: " + position + ", title: " + adBean.getTitle());
//
//					TextView title = null;
//					ImageView image;
//					ImageView image2 = null;
//					ImageView image3 = null;
//					TextView tag;
//					TextView author;
//					TextView comment;
//					TextView time;
////					View moreLayout;
////					TextView more;
//
//					View currentLayout = convertView;
////					topTitle = BaseViewHolder.get(convertView, R.id.item_channel_item_top_title);
////					moreLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_more_ll);
////					more = BaseViewHolder.get(convertView, R.id.item_channel_item_more_tv);
//
////					title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_small);
//					title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
//					if(itemType == ChannelItemType.AdThreeSmall && !Common.isTrue(SettingManager.getInst().getNoPicModel())){
//						// 三图广告且不在无图模式下
////						title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_three_small);
//						image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
//						image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
//					}else if(itemType == ChannelItemType.AdOneBig && !Common.isTrue(SettingManager.getInst().getNoPicModel())){
//						// 大图广告且不在无图模式下
////						title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_big);
//					}else{
////						title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_small);
//					}
//
//					image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
//
//					author = BaseViewHolder.get(currentLayout, R.id.item_channel_item_author);
//					comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);
//					time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
//					tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_ad_tag);
//
//					if (!Common.isTrue(SettingManager.getInst().getNoPicModel())) {
//						if(itemType == ChannelItemType.AdThreeSmall){
//							updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
//							updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
//							updateSize(image3, mImageThreeSmallWidth, mImageThreeSmallHeight);
//							if(!Common.isListEmpty(adBean.getMultiPicUrls()) && adBean.getMultiPicUrls().size() > 2){
//								image.setVisibility(View.VISIBLE);
//								image2.setVisibility(View.VISIBLE);
//								image3.setVisibility(View.VISIBLE);
//								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(0), image, mOptions);
//								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(1), image2, mOptions);
//								ImageLoader.getInstance().displayImage(adBean.getMultiPicUrls().get(2), image3, mOptions);
//								if (Common.isTrue(SettingManager.getInst().getNightModel())) {
//									image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//									image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//									image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//								}
//							}else{
//								ImageLoader.getInstance().cancelDisplayTask(image);
//								ImageLoader.getInstance().cancelDisplayTask(image2);
//								ImageLoader.getInstance().cancelDisplayTask(image3);
//								image.setVisibility(View.GONE);
//								image2.setVisibility(View.GONE);
//								image3.setVisibility(View.GONE);
//							}
//						}else{
//							if(itemType == ChannelItemType.Ad){
//								updateSize(image, mImageOneSmallWidth, mImageOneSmallHeight);
//							}
//							if (!TextUtils.isEmpty(adBean.getImageUrl())) {
//								image.setVisibility(View.VISIBLE);
//								ImageLoader.getInstance().displayImage(adBean.getImageUrl(), image, mOptions);
//							} else if (!TextUtils.isEmpty(adBean.getIconUrl())) {
//								image.setVisibility(View.VISIBLE);
//								ImageLoader.getInstance().displayImage(adBean.getIconUrl(), image, mOptions);
//							} else {
//								ImageLoader.getInstance().cancelDisplayTask(image);
//								image.setVisibility(View.GONE);
//							}
//							if (Common.isTrue(SettingManager.getInst().getNightModel())) {
//								image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//							}
//						}
//					} else {
//						ImageLoader.getInstance().cancelDisplayTask(image);
//						if(image != null){
//							image.setVisibility(View.GONE);
//						}
//						if(itemType == ChannelItemType.AdThreeSmall){
//							if(image2 != null){
//								image2.setVisibility(View.GONE);
//							}
//							if(image3 != null){
//								image3.setVisibility(View.GONE);
//							}
//						}
//					}
//
//					if(tag != null){
//						tag.setVisibility(View.VISIBLE);
//					}
//					if(comment != null){
//						comment.setVisibility(View.GONE);
//					}
//					title.setText(adBean.getTitle());
//					String username = finalBean.getUsername();
//					author.setText(username);
//					author.setVisibility(TextUtils.isEmpty(username) ? View.GONE : View.VISIBLE);
////					time.setText(finalBean.getCategory());
//					handleTime(time, finalBean);
//					title.setTag(adBean);
//					contentLayout.setTag(adBean);
//
//					adBean.recordImpression(convertView);
//
//				}
//			});
//		} else {
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
//					image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
//					image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
					break;
				case OneBig:
					break;
				case OneBigTwoSmall:
//					image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
//					image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
					break;
			}
			
			title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
			image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
			tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_ad_tag);
			time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
			author = BaseViewHolder.get(currentLayout, R.id.item_channel_item_author);
			comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);
			refresh = BaseViewHolder.get(convertView, R.id.item_channel_item_click_refresh);

			ImageView imageVideo = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image_video);
			handleImageVideo(imageVideo, bean);

		// video
		FhItemData itemData = null;
		if(mListView != null && mListView instanceof PullToRefreshBase){
			ListView listView = (ListView) ((PullToRefreshBase) mListView).getRefreshableView();
			itemData = new FhItemData(listView, position, bean);
		}else{
			itemData = new FhItemData(null, position, bean);
		}
//			VideoItemWidget viw = VideoItemWidget.createVideoItemWidget(getContext(), convertView, bean.getVideo_address(), itemData);
		FhNiceVideoWidget viw = FhNiceVideoWidget.createVideoItemWidget(getContext(), convertView, bean.getVideo_address(), itemData, FhVideoPlayerController.CtrlMode.ListItem);
			if(!isNoPic){
				switch(itemType){
					default:
					case OneSmall:
//						updateSize(image, mImageOneSmallWidth, mImageOneSmallHeight);
						break;
					case OneBigVideo:
						if(!TextUtils.isEmpty(((ChannelItemBean) bean).getImage_list())){
							image.setVisibility(View.VISIBLE);
							ImageLoader.getInstance().displayImage(bean.getImage_list(), image,getDisplayOptions(bean));

							if(viw.getCover() != null){
								viw.getCover().setVisibility(View.VISIBLE);
								ImageLoader.getInstance().displayImage(bean.getImage_list(), viw.getCover(),getDisplayOptions(bean));
							}
						}else{
							ImageLoader.getInstance().cancelDisplayTask(image);
							image.setVisibility(View.GONE);

							if(viw.getCover() != null){
								ImageLoader.getInstance().cancelDisplayTask(viw.getCover());
								viw.getCover().setVisibility(View.GONE);
							}
						}
						if(Common.isTrue(SettingManager.getInst().getNightModel())){
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);

							if(viw.getCover() != null){
								viw.getCover().setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
							}
						}
						break;
					case ThreeSmall:
//						updateSize(image,mImageThreeSmallWidth,mImageThreeSmallHeight);
//						updateSize(image2,mImageThreeSmallWidth,mImageThreeSmallHeight);
//						updateSize(image3,mImageThreeSmallWidth,mImageThreeSmallHeight);
//						if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
//							image.setVisibility(View.VISIBLE);
//							image2.setVisibility(View.VISIBLE);
//							image3.setVisibility(View.VISIBLE);
//							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), image ,getDisplayOptions(bean));
//							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), image2 ,getDisplayOptions(bean));
//							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), image3 ,getDisplayOptions(bean));
//							if(Common.isTrue(SettingManager.getInst().getNightModel())){
//								image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
//								image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
//								image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
//							}
//						}else{
//							ImageLoader.getInstance().cancelDisplayTask(image);
//							ImageLoader.getInstance().cancelDisplayTask(image2);
//							ImageLoader.getInstance().cancelDisplayTask(image3);
//							image.setVisibility(View.GONE);
//							image2.setVisibility(View.GONE);
//							image3.setVisibility(View.GONE);
//						}
						break;
					case OneBig:
//						if(!TextUtils.isEmpty(((ChannelItemBean) bean).getImage_list())){
//							image.setVisibility(View.VISIBLE);
//							ImageLoader.getInstance().displayImage(bean.getImage_list(), image,getDisplayOptions(bean));
//						}else{
//							ImageLoader.getInstance().cancelDisplayTask(image);
//							image.setVisibility(View.GONE);
//						}
//						if(Common.isTrue(SettingManager.getInst().getNightModel())){
//							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
//						}
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
//						updateSize(image, mImageOneTwoBigWidth, mImageOneTwoBigHeight);
//						updateSize(image2, mImageOneTwoSmallWidth, mImageOneTwoSmallHeight);
//						updateSize(image3, mImageOneTwoSmallWidth, mImageOneTwoSmallHeight);
//						if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
//							image.setVisibility(View.VISIBLE);
//							image2.setVisibility(View.VISIBLE);
//							image3.setVisibility(View.VISIBLE);
//							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), image, getDisplayOptions(bean));
//							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), image2, getDisplayOptions(bean));
//							ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), image3, getDisplayOptions(bean));
//							if (Common.isTrue(SettingManager.getInst().getNightModel())) {
//								image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//								image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//								image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
//							}
//						}else{
//							ImageLoader.getInstance().cancelDisplayTask(image);
//							ImageLoader.getInstance().cancelDisplayTask(image2);
//							ImageLoader.getInstance().cancelDisplayTask(image3);
//							image.setVisibility(View.GONE);
//							image2.setVisibility(View.GONE);
//							image3.setVisibility(View.GONE);
//						}
						break;
				}
			}else{
				ImageLoader.getInstance().cancelDisplayTask(image);
				if(image != null){
					image.setVisibility(View.GONE);
				}
				if(viw.getCover() != null){
					ImageLoader.getInstance().cancelDisplayTask(viw.getCover());
					viw.getCover().setVisibility(View.GONE);
				}
//				if(itemType == ChannelItemType.ThreeSmall){
//					image2.setVisibility(View.GONE);
//					image3.setVisibility(View.GONE);
//				}
			}
			handleTitle(title,bean);

			if(tag != null){
				tag.setVisibility(View.GONE);
			}
			handleTime(time, bean);
			handleFrom(author,bean);
			handleComment(comment,bean, convertView, itemData);
			contentLayout.setTag(bean);

			//设置刚刚看到这里，点击刷新
			handleRefresh(refresh, bean);

		
		// video

		ImageView avatar = BaseViewHolder.get(convertView, R.id.item_channel_item_avatar);
		if(avatar != null ){
			if(!isNoPic){
				avatar.setVisibility(View.VISIBLE);
				Utils.handleAvatar(bean.getFace(),
						avatar, Utils.mCircleOptions);
			}else{
				avatar.setVisibility(View.GONE);
			}
		}
		TextView share = BaseViewHolder.get(convertView, R.id.item_channel_item_share);
		handleShare((BaseFragmentActivity) getContext(), share, bean);

		TextView zan = BaseViewHolder.get(convertView, R.id.item_channel_item_zan);
		handleZan(zan, bean);
//		}
		
	}

	private void handleImageVideo(ImageView imageVideo, final ChannelItemBean bean) {
		if (imageVideo == null) {
			return;
		}
		int type = bean.getShow_type();
		if (type == Consts.SHOW_TYPE_ONE_SMALL_VIDEO){
			imageVideo.setVisibility(View.VISIBLE);
			return;
		}
		imageVideo.setVisibility(View.GONE);
	}

	private void handleZan(final TextView zan, final ChannelItemBean bean) {
		if (zan == null) {
			return;
		}
		zan.setSelected(Common.isTrue(bean.getIs_dig()));
		zan.setText(String.valueOf(bean.getDigcounts()));
		zan.setTag(bean);
		DataStateListener likeState = new DataStateListener() {

			@Override
			public void success(Object... objects) {
				Object digCount = objects[0];
				zan.setEnabled(true);
				zan.setSelected(!zan.isSelected());
				if (digCount instanceof String) {
					zan.setText((String)digCount);
					bean.setDigcounts(Common.strToInt((String)digCount));
				}
				bean.setIs_dig(Common.TRUE);
                mItemDA.updateTarget(mItemDA.buildInsertValues(bean), "tid=?", new String[]{
                        bean.getTid()
                });
			}

			@Override
			public void init() {
				zan.setEnabled(false);
			}

			@Override
			public void failure() {
				zan.setEnabled(true);
			}
		};

		ArticleManagerUtils mArticleManagerUtils = new ArticleManagerUtils();
		zan.setOnClickListener(new ArticleCommentListeners.LikeOnClickListener(getContext(), mArticleManagerUtils, likeState));
	}

	private void handleShare(final BaseFragmentActivity activity, TextView share, final ChannelItemBean bean) {
		if (share == null) {
			return;
		}
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != bean) {
					ArticleShareDialog dialog = new ArticleShareDialog();
					dialog.setBean(bean);
					dialog.showAllowingStateLoss(activity, activity.getSupportFragmentManager(),
							ArticleShareDialog.class.getSimpleName());
				}
			}
		});
	}

	private void handleRefresh(View refresh, ChannelItemBean bean) {
		refresh.setVisibility(!TextUtils.isEmpty(bean.getTips()) ? View.VISIBLE : View.GONE);
		refresh.setOnClickListener(refreshListener);
	}

	private void handleTitle(TextView title, ChannelItemBean bean) {
		if (title == null) {
			return;
		}
		title.setTag(bean);
		title.setText(bean.getTitle());
		if(mIsReadEnable){
//				Common.handleTextViewReaded(getContext(), title, LocalStateDA.getInst(getContext()).isRead(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid()));
		}
	}

	private void handleComment(TextView comment, final ChannelItemBean bean, final View view, final FhItemData data) {
		if (comment == null) {
			return;
		}
		int count = bean.getReplys();
		comment.setText("" + count);
		comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO(debug)
				Activity a = getContext();
				if(a != null && a instanceof MainActivity){
					((MainActivity) a).launchVideoDetailWidget(view, data, VideoDetailActivity.TYPE_CLICK_COMMENT);
				}
//				VideoDetailActivity.launch(getContext(), bean, VideoDetailActivity.TYPE_CLICK_COMMENT);
			}
		});
//		comment.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
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
//		author.setText(bean.getList_from());
		author.setText(bean.getNickname());
//		author.setText(bean.getUsername());
	}

	private void updateSize(View v, int width, int height){
		if (v == null) {
			return;
		}
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}

	public DisplayImageOptions getDisplayOptions(BaseChannelItemBean mBean){
//		if(mBean instanceof DynamicItemBean){
//			return mOptions;
//		}else{
//			int type = ((ChannelItemBean)mBean).getShow_type();
//			if (type == Consts.SHOW_TYPE_ONE_SMALL_VIDEO){
//				return mOptionsWithImageSmall;
//			}
			return mOptions;
//		}
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
		if (tv == null) {
			return;
		}
		long time = Common.dateToLong(bean.getDateline());
		tv.setText(Common.formatTimestmpByJs(time));
	}
	
	public static String getNewMaxIdNoAd(List<ChannelItemBean> list){
		if(!Common.isListEmpty(list)){
			for(ChannelItemBean bean : list){
				if(!TextUtils.isEmpty(bean.getAd_place_id())){
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
