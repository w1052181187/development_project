package com.shenyuan.militarynews.adapter;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.base.util.GlideHelper;
import com.chengning.common.util.DisplayUtil;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchNewsAdapter extends BaseTypeListAdapter{
	
	private static final String TAG = SearchNewsAdapter.class.getSimpleName(); 

	private Activity mContext;
	private GlideHelper.GlideImageType mImageTypeWithImage;
	private GlideHelper.GlideImageType mImageType;
	private GlideHelper.GlideImageType mImageTypeWithSmallImage;

	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneTwoBigWidth;
	private int mImageOneTwoBigHeight;
	private int mImageOneTwoSmallWidth;
	private int mImageOneTwoSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSamllHeight;
	private OnClickListener mMoreListener;
	
	private boolean mIsHomeRecom = false;
	
	private String[] mStrKeywords;
	
	public SearchNewsAdapter(Activity activity, List list, OnClickListener moreOnClickListenner) {
		super(activity, list);

		mContext = activity;
		mMoreListener = moreOnClickListenner;

		initImageType();
		
		DisplayUtil.getInst().init(activity);
		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		int gap2Width = activity.getResources().getDimensionPixelSize(R.dimen.tuwen_image_gap);
		int gapThreeSmall = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin_three_small); 
		float imageLayoutWidth = (float)(DisplayUtil.getInst().getScreenWidth() - gapWidth * 2);
		
		mImageThreeSmallWidth =  ((int)imageLayoutWidth - gapThreeSmall * 2) / 3;
		mImageThreeSmallHeight = (int) (mImageThreeSmallWidth / 1.5);
		mImageOneSmallWidth = mImageThreeSmallWidth;
		mImageOneSamllHeight = mImageThreeSmallHeight;
		
		mImageOneTwoBigWidth = (int) (imageLayoutWidth * 0.615f + 0.5f);
		mImageOneTwoSmallWidth = (int) (imageLayoutWidth - gap2Width - mImageOneTwoBigWidth);
		mImageOneTwoSmallHeight = (int) (((float)mImageOneTwoSmallWidth) / 1.338f + 0.5f);
		mImageOneTwoBigHeight = mImageOneTwoSmallHeight * 2 + gap2Width;
		
	}

	private void initImageType() {
		mImageType = GlideHelper.GlideImageType.defaulted;
		mImageTypeWithImage = GlideHelper.GlideImageType.covered;
		mImageTypeWithImage.setRadius(0);
		mImageTypeWithImage.setResId(R.drawable.video_list_icon);
		mImageTypeWithSmallImage = GlideHelper.GlideImageType.covered;
		mImageTypeWithSmallImage.setRadius(0);
		mImageTypeWithSmallImage.setResId(R.drawable.video_list_icon);
	}

	private OnClickListener refreshListener;

	public static enum ChannelItemType {
		/**
		 * OneSmall，1个小图，默认type，type必须从0开始
		 */
		OneSmall(0, Const.NEWS_TYPE_COMMON, Const.NEWS_TYPE_COMMON_NO_PIC),	// 1个小图
		OneBig(1, Const.NEWS_TYPE_ONE_BIG_PIC),	// 1个大图
		OneBigTwoSmall(2, Const.NEWS_TYPE_ONE_BIG_TWO_SMALL_PIC),	// 1大图2小图
		ThreeSmall(3, Const.NEWS_TYPE_THREE_SMALL_PIC, Const.NEWS_TYPE_SIX_SMALL_PIC),	// 3小图
		TwoBig(4, Const.NEWS_TYPE_TWO_BIG_PIC), // 2大图
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
		MChannelItemBean bean = (MChannelItemBean) getItem(position);
		ChannelItemType itemType = ChannelItemType.parseShowType(bean.getNews_show_type());
		if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
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
	public View buildLayoutView(int position, int type) {
		View view = View.inflate(getContext(), R.layout.item_channel_item_recommend, null);
		ViewStub stub = (ViewStub) view.findViewById(R.id.item_channel_item_stub);
		ChannelItemType ct = ChannelItemType.parseType(type);
		switch (ct) {
		default:
		case OneSmall:
			stub.setLayoutResource(R.layout.item_channel_item_one_small_right);
			break;
		case OneBig:
			stub.setLayoutResource(R.layout.item_channel_item_one_big);
			break;
		case OneBigTwoSmall:
			stub.setLayoutResource(R.layout.item_channel_item_one_big_two_small);
			break;
		case ThreeSmall:
			stub.setLayoutResource(R.layout.item_channel_item_three_small);
			break;
		case TwoBig:
			stub.setLayoutResource(R.layout.item_channel_item_two_big);
			break;
		}
		stub.inflate();
		return view;
	}

	@Override
	public int buildLayoutId(int position, int type) {
		return -1;
	}

	@Override
	public void handleLayout(final View convertView, final int position, Object obj,
			int type) {
		// type
		View contentLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_content_layout);
		final ChannelItemType itemType = ChannelItemType.parseType(type);

		MChannelItemBean bean = (MChannelItemBean) getItem(position);
		MChannelItemBean bean2 = null;
		
		{
			contentLayout.setVisibility(View.VISIBLE);

			int dataPosition = position;
			if(itemType == ChannelItemType.TwoBig){
				// 奇偶成对，只显示一个
				int preIndex = dataPosition - 1;
				int nextIndex = dataPosition + 1;
				int anotherIndex =  0;
				List<MChannelItemBean> list = getList();
				if(preIndex >= 0 && list.get(preIndex).getNews_show_type() == Const.NEWS_TYPE_TWO_BIG_PIC){
					// 前一个则还原显示顺序
					anotherIndex = preIndex;
					bean2 = bean;
					bean = list.get(preIndex);
				}else if(nextIndex < list.size() && list.get(nextIndex).getNews_show_type() == Const.NEWS_TYPE_TWO_BIG_PIC){
					anotherIndex = nextIndex;
					bean2 = list.get(nextIndex);
				}else{
					contentLayout.setVisibility(View.GONE);
					return;
				}
				if(anotherIndex % 2 == 0){
					contentLayout.setVisibility(View.GONE);
					return;
				}else{
					contentLayout.setVisibility(View.VISIBLE);
				}
			}
			
			TextView title = null;
			TextView category = null;
			TextView topTitle;
			ImageView image;
			TextView tag;
			TextView time;
			TextView like;
			TextView comment;
			ImageView image2 = null;
			ImageView image3 = null;
			View moreLayout;
			TextView more;
			TextView refresh;
			View divider;

			View currentLayout = convertView;
			topTitle = BaseViewHolder.get(convertView, R.id.item_channel_item_top_title);
			moreLayout = BaseViewHolder.get(convertView, R.id.item_channel_item_more_ll);
			more = BaseViewHolder.get(convertView, R.id.item_channel_item_more_tv);
			refresh = BaseViewHolder.get(convertView, R.id.item_channel_item_click_refresh);
			
			if (!TextUtils.isEmpty(bean.getNav())) {
				topTitle.setVisibility(View.VISIBLE);
				topTitle.setText(bean.getNav());
			} else {
				topTitle.setVisibility(View.GONE);
				if (!(Const.NEWS_TYPE_COMMON == bean.getNews_show_type() || Const.NEWS_TYPE_COMMON_NO_PIC == bean.getNews_show_type())) {
					if (dataPosition > 0 ){
						String nav = ((List<MChannelItemBean>)getList()).get(dataPosition - 1).getNav();
						String Localnav = ((List<MChannelItemBean>)getList()).get(dataPosition - 1).getLocal_nav();
						if (!TextUtils.isEmpty(nav)) {
							bean.setLocal_nav(nav);
						} else if (!TextUtils.isEmpty(Localnav)) {
								bean.setLocal_nav(Localnav);
						}
					}
				}
			}
			
			if (!TextUtils.isEmpty(bean.getMore())) {
				moreLayout.setVisibility(View.VISIBLE);
				more.setText(bean.getMore());
				
				if (!TextUtils.isEmpty(bean.getNav())) {
					moreLayout.setTag(bean.getNav());
				} else if (!TextUtils.isEmpty(bean.getLocal_nav())) {
					moreLayout.setTag(bean.getLocal_nav());
				}
				moreLayout.setOnClickListener(mMoreListener);
			} else if (bean2 != null && !TextUtils.isEmpty(bean2.getMore())) {
				moreLayout.setVisibility(View.VISIBLE);
				more.setText(bean2.getMore());
				moreLayout.setTag(bean.getNav());
				moreLayout.setOnClickListener(mMoreListener);
			}else {
				moreLayout.setVisibility(View.GONE);
			}
			
			//设置刚刚看到这里，点击刷新
			refresh.setVisibility(!TextUtils.isEmpty(bean.getTips()) ? View.VISIBLE : View.GONE);
			refresh.setOnClickListener(refreshListener);
			
			switch (itemType) {
			default:
			case OneSmall:
				title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_small);
				break;
			case OneBig:
				title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_big);
				category = BaseViewHolder.get(currentLayout, R.id.item_channel_item_category);
				break;
			case OneBigTwoSmall:
				title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_one_big_two_small);
				image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
				image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
				
				break;
			case ThreeSmall:
				title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_three_small);
				image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
				image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
				break;
			case TwoBig:
				View layoutTB1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_list_two_big_1);
				image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image_1);
				TextView titleTB1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_two_big_1);
				TextView tagTB1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_tag_1);
				TextView timeTB1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time_1);
				TextView likeTB1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_like_1);
				TextView commentTB1 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment_1);
				
				View layoutTB2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_list_two_big_2);
				image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image_2);
				TextView titleTB2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title_two_big_2);
				TextView tagTB2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_tag_2);
				TextView timeTB2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time_2);
				TextView likeTB2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_like_2);
				TextView commentTB2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment_2);

				titleTB1.setTag(bean);
				titleTB1.setText(bean.getTitle());
				handleTime(timeTB1, bean);
				timeTB1.setTag(position);
				handleTag(tagTB1, bean);
				likeTB1.setText(bean.getDigg());
				commentTB1.setText(bean.getPl());

				layoutTB1.setOnClickListener(twoBigListener1);
				
				Common.handleTextViewReaded(mContext, titleTB1,
						LocalStateServer.getInst(getContext()).isRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid()));
				
				if (!TextUtils.isEmpty(bean.getImage()) && !Common.isTrue(SettingManager.getInst().getNoPicModel())) {
					image2.setVisibility(View.VISIBLE);
					GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage(), image2 ,getImageOptions(bean));
				} else {
					GlideHelper.getInst().clear(getContext(), image2);
					image2.setVisibility(View.GONE);
				}
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}

				titleTB2.setTag(bean2);
				titleTB2.setText(bean2.getTitle());
				handleTime(timeTB2, bean2);
				timeTB2.setTag(position);
				handleTag(tagTB2, bean2);
				likeTB2.setText(bean2.getDigg());
				commentTB2.setText(bean2.getPl());

				layoutTB2.setOnClickListener(twoBigListener2);
				Common.handleTextViewReaded(mContext, titleTB2,
							LocalStateServer.getInst(getContext()).isRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean2.getAid()));
				if (!TextUtils.isEmpty(bean2.getImage()) && !Common.isTrue(SettingManager.getInst().getNoPicModel())) {
					image3.setVisibility(View.VISIBLE);
					GlideHelper.getInst().loadImageWithPlace(getContext(),bean2.getImage(), image3 ,getImageOptions(bean));
				} else {
					GlideHelper.getInst().clear(getContext(), image3);
					image3.setVisibility(View.GONE);
				}
				if (Common.isTrue(SettingManager.getInst().getNightModel())) {
					image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
				}
				break;
			}
			
			image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
			tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_tag);
			time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
			comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);
			like = BaseViewHolder.get(currentLayout, R.id.item_channel_item_like);
			divider = BaseViewHolder.get(convertView, R.id.item_channel_item_divider);

			if (!Common.isTrue(SettingManager.getInst().getNoPicModel())) {
				switch (itemType) {
				default:
				case OneSmall:
					updateSize(image, mImageOneSmallWidth, mImageOneSamllHeight);
					if (!TextUtils.isEmpty(bean.getImage())) {
						image.setVisibility(View.VISIBLE);
						GlideHelper.getInst().loadImageWithPlace(getContext(), bean.getImage(), image ,getImageOptions(bean));
					} else {
						GlideHelper.getInst().clear(getContext(), image);
						image.setVisibility(View.GONE);
					}
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
					}
					break;
				case OneBig:
					if (!TextUtils.isEmpty(bean.getImage())) {
						image.setVisibility(View.VISIBLE);
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage(), image ,getImageOptions(bean));
					} else {
						GlideHelper.getInst().clear(getContext(), image);
						image.setVisibility(View.GONE);
					}
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
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
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage_arr().get(0), image ,getImageOptions(bean, true));
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage_arr().get(1), image2 ,getImageOptions(bean));
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage_arr().get(2), image3 ,getImageOptions(bean));
						if (Common.isTrue(SettingManager.getInst().getNightModel())) {
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
						}
					}else{
						GlideHelper.getInst().clear(getContext(), image);
						GlideHelper.getInst().clear(getContext(), image2);
						GlideHelper.getInst().clear(getContext(), image3);
						image.setVisibility(View.GONE);
						image2.setVisibility(View.GONE);
						image3.setVisibility(View.GONE);
					}
					break;
				case ThreeSmall:
					updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
					updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
					updateSize(image3, mImageThreeSmallWidth, mImageThreeSmallHeight);
					if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
						image.setVisibility(View.VISIBLE);
						image2.setVisibility(View.VISIBLE);
						image3.setVisibility(View.VISIBLE);
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage_arr().get(0), image ,getImageOptions(bean));
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage_arr().get(1), image2 ,getImageOptions(bean));
						GlideHelper.getInst().loadImageWithPlace(getContext(),bean.getImage_arr().get(2), image3 ,getImageOptions(bean));
						if (Common.isTrue(SettingManager.getInst().getNightModel())) {
							image.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							image2.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
							image3.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
						}
					}else{
						GlideHelper.getInst().clear(getContext(), image);
						GlideHelper.getInst().clear(getContext(), image2);
						GlideHelper.getInst().clear(getContext(), image3);
						image.setVisibility(View.GONE);
						image2.setVisibility(View.GONE);
						image3.setVisibility(View.GONE);
					}
					break;
					
				case TwoBig:
					// do nothing.
					break;
				}
			} else {
				if(image != null){
					GlideHelper.getInst().clear(getContext(), image);
					image.setVisibility(View.GONE);
				}
				if (itemType == ChannelItemType.OneBigTwoSmall || itemType == ChannelItemType.ThreeSmall
						|| itemType == ChannelItemType.TwoBig) {
					if (image2 != null) {
						GlideHelper.getInst().clear(getContext(), image2);
						image2.setVisibility(View.GONE);
					}
					if (image3 != null) {
						GlideHelper.getInst().clear(getContext(), image3);
						image3.setVisibility(View.GONE);
					}
				}
			}
			
			if (itemType != ChannelItemType.TwoBig) {
				title.setText(matcherSearchTitle(getContext().getResources().getColor(R.color.red),  bean.getTitle(), mStrKeywords));
				handleTime(time, bean);
				like.setText(bean.getDigg());
				comment.setText(bean.getPl() + "评");
				contentLayout.setTag(bean);

				Common.handleTextViewReaded(getContext(), title,
						LocalStateServer.getInst(getContext()).isRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid()));
			}
			handleTag(tag, bean);
			handleCategory(category, bean);
		}
		
	}
	
	private SpannableString matcherSearchTitle(int color, String text,  String[] keyword) {  
		SpannableString s = new SpannableString(text);  
		for (int i = 0; i < keyword.length; i++) {  
			Pattern p = Pattern.compile(keyword[i]);  
			Matcher m = p.matcher(s);  
			while (m.find()) {  
				int start = m.start();  
				int end = m.end();  
				s.setSpan(new ForegroundColorSpan(color), start, end,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
			}
		}
		return s;  
	}  

	private void handleCategory(TextView category, MChannelItemBean bean) {
		if (category == null) {
			return;
		}
		category.setText(bean.getCategory());
	}

	private GlideHelper.GlideImageType getImageOptions(MChannelItemBean bean){
		return getImageOptions(bean, false);
	}
	
	private GlideHelper.GlideImageType getImageOptions(MChannelItemBean bean, boolean forceBig){
		if(Const.CHANNEL_ARTICLE_VIDEO.equals(bean.getChannel())){
			if(forceBig || Const.NEWS_TYPE_ONE_BIG_PIC == bean.getNews_show_type()){
				return mImageTypeWithImage;
			}else{
				return mImageTypeWithSmallImage;
			}
		}else{
			return mImageType;
		}
	}
	
	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	
	private OnClickListener twoBigListener1 = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			TextView title = (TextView) v.findViewById(R.id.item_channel_item_title_two_big_1);
			Common.handleTextViewReaded(getContext(), title, true);
			
			int position = (Integer) v.findViewById(R.id.item_channel_item_time_1).getTag();
			ChannelItemType type = ChannelItemType.TwoBig;
			MChannelItemBean bean = (MChannelItemBean) title.getTag();
			handleBeanClick(type, bean, position);
		}
	};
	
	private OnClickListener twoBigListener2 = new OnClickListener() {
		@Override
		public void onClick(View v) {
			TextView title = (TextView) v.findViewById(R.id.item_channel_item_title_two_big_2);
			Common.handleTextViewReaded(getContext(), title, true);

			int position = (Integer) v.findViewById(R.id.item_channel_item_time_2).getTag();
			ChannelItemType type = ChannelItemType.TwoBig;
			MChannelItemBean bean = (MChannelItemBean) title.getTag();
			handleBeanClick(type, bean, position);
		}
	};

	private void handleBeanClick(ChannelItemType type, MChannelItemBean bean, int position){
		LocalStateServer.getInst(getContext()).setReadStateRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid());
		String area = (mIsHomeRecom && position >= 0 && position < 30) ? "new30" : "normal";
		Utils.handleBeanClick(getContext(), bean, area);
	}
	
	private void handleTime(TextView tv, MChannelItemBean bean){
		tv.setText(Common.formatTimestmpByJs(bean.getPubTimestamp()));
	}
	
	private void handleTag(TextView tv, MChannelItemBean bean){
		if(tv == null){
			return;
		}
		String tag = bean.getRed_tag();
		if(!TextUtils.isEmpty(tag)){
			tv.setText(tag);
			tv.setVisibility(View.VISIBLE);
		}else{
			tv.setVisibility(View.GONE);
		}
	}
	
	public void setKeywords(String[] values){
		mStrKeywords = values;
	}

}
