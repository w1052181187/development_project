package com.chengning.yiqikantoutiao.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
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
import com.chengning.common.util.DisplayUtil;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.data.access.LocalStateDA;
import com.chengning.yiqikantoutiao.data.bean.BaseChannelItemBean;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.MyCoverRoundedBitmapDisplayer;
import com.chengning.yiqikantoutiao.util.MyRoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchNewsAdapter extends BaseTypeListAdapter{
	
	private static final String TAG = SearchNewsAdapter.class.getSimpleName();

	private Activity mContext;
	private DisplayImageOptions mOptions;
	private DisplayImageOptions mOptionsWithImage;
	private DisplayImageOptions mOptionsWithImageSmall;

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

	private OnClickListener refreshListener;

	public static enum ChannelItemType {
		/**
		 * OneSmall，1个小图，默认type，type必须从0开始
		 */
		OneSmall(0, Consts.SHOW_TYPE_ONE_SMALL),	// 1个小图
		OneBig(1, Consts.SHOW_TYPE_ONE_BIG),	// 1个大图
		OneBigTwoSmall(2, Consts.SHOW_TYPE_ONE_BIG_TWO_SMALL),	// 1大图2小图
		ThreeSmall(3, Consts.SHOW_TYPE_THREE_SMALL),	// 3小图
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
		ChannelItemBean bean = (ChannelItemBean) getItem(position);
		ChannelItemType itemType = ChannelItemType.parseShowType(bean.getShow_type());
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
		View view = View.inflate(getContext(), R.layout.item_channel_item_container, null);
		ViewStub stub = (ViewStub) view.findViewById(R.id.item_channel_item_stub);
		ChannelItemType ct = ChannelItemType.parseType(type);
		switch (ct) {
		default:
		case OneSmall:
			stub.setLayoutResource(R.layout.item_channel_item_one_small);
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

		ChannelItemBean bean = (ChannelItemBean) getItem(position);
		ChannelItemBean bean2 = null;
		
		contentLayout.setVisibility(View.VISIBLE);

		int dataPosition = position;

		TextView title = null;
		ImageView image;
		TextView tag;
		TextView time;
		TextView comment;
		ImageView image2 = null;
		ImageView image3 = null;

		TextView author;
		ImageView face = null;

		View currentLayout = convertView;

		switch (itemType) {
		default:
		case OneSmall:
			break;
		case OneBig:
//				category = BaseViewHolder.get(currentLayout, R.id.item_channel_item_category);
			break;
		case OneBigTwoSmall:
			image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
			image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);

			break;
		case ThreeSmall:
			image2 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image2);
			image3 = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image3);
			break;
		}

		title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
		image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
		tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_tag);
		face = BaseViewHolder.get(convertView, R.id.item_channel_item_face);
		time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
		author = BaseViewHolder.get(currentLayout, R.id.item_channel_item_author);
		comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);

		if (!Common.isTrue(SettingManager.getInst().getNoPicModel())) {
			switch (itemType) {
			default:
			case OneSmall:
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
			case OneBigTwoSmall:
				updateSize(image, mImageOneTwoBigWidth, mImageOneTwoBigHeight);
				updateSize(image2, mImageOneTwoSmallWidth, mImageOneTwoSmallHeight);
				updateSize(image3, mImageOneTwoSmallWidth, mImageOneTwoSmallHeight);
				if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
					image.setVisibility(View.VISIBLE);
					image2.setVisibility(View.VISIBLE);
					image3.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), image, getImageOptions(bean));
					ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), image2, getImageOptions(bean));
					ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), image3, getImageOptions(bean));
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
			case ThreeSmall:
				updateSize(image, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(image2, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(image3, mImageThreeSmallWidth, mImageThreeSmallHeight);
				if(!Common.isListEmpty(bean.getImage_arr()) && bean.getImage_arr().size() > 2){
					image.setVisibility(View.VISIBLE);
					image2.setVisibility(View.VISIBLE);
					image3.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(bean.getImage_arr().get(0), image, getImageOptions(bean));
					ImageLoader.getInstance().displayImage(bean.getImage_arr().get(1), image2, getImageOptions(bean));
					ImageLoader.getInstance().displayImage(bean.getImage_arr().get(2), image3, getImageOptions(bean));
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
		} else {
			ImageLoader.getInstance().cancelDisplayTask(image);
			if(image != null){
				image.setVisibility(View.GONE);
			}
			if (itemType == ChannelItemType.OneBigTwoSmall) {
				image2.setVisibility(View.GONE);
				image3.setVisibility(View.GONE);
			}else if(itemType == ChannelItemType.ThreeSmall) {
				image2.setVisibility(View.GONE);
				image3.setVisibility(View.GONE);
			}
		}

		title.setText(matcherSearchTitle(getContext().getResources().getColor(R.color.red),  bean.getTitle(), mStrKeywords));
		Common.handleTextViewReaded(getContext(), title, LocalStateDA.getInst(getContext()).isRead(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid()));
		// item
		if(!TextUtils.isEmpty(bean.getCh_name())){

			if(tag != null){
				tag.setText(bean.getCh_name());
				tag.setVisibility(View.VISIBLE);
			}

			time.setVisibility(View.GONE);
		}else{
			if(tag != null){
				tag.setVisibility(View.GONE);
			}

//				time.setText(Common.dateCompareNow(bean.getDateline()));
			time.setVisibility(View.VISIBLE);
			handleTime(time, bean);

		}
		if (face != null) {
			face.setVisibility(View.GONE);
		}

		handleFrom(author,bean);
		handleComment(comment,bean);
		contentLayout.setTag(bean);

		title.setTag(bean);

	}
	
	private SpannableString matcherSearchTitle(int color, String text, String[] keyword) {
		if(TextUtils.isEmpty(text)){
			return new SpannableString("");
		}
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

	private void handleComment(TextView comment, ChannelItemBean bean) {
		if (comment == null) {
			return;
		}
		int count = bean.getReplys();
		comment.setText(count + "评论");
		comment.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
	}

	private void handleFrom(TextView author, ChannelItemBean bean) {
		// TODO Auto-generated method stub
		// 推荐下第一页来源显示栏目名称，否则显示来源
		if (author == null) {
			return;
		}
		String from = bean.getList_from();
		author.setVisibility(TextUtils.isEmpty(from) ? View.GONE : View.VISIBLE);
//		author.setText((isRecom && bean.getLocal_page() == JUrl.PAGE_START) ?
//				bean.getCategory() : bean.getList_from());
		author.setText(from);
	}
	
	private void handleCategory(TextView category, ChannelItemBean bean) {
		if (category == null) {
			return;
		}
		category.setText(bean.getCategory());
	}

	private DisplayImageOptions getImageOptions(ChannelItemBean bean){
		return getDisplayOptions(bean);
	}
	
	public DisplayImageOptions getDisplayOptions(BaseChannelItemBean  mBean){
		int type = ((ChannelItemBean)mBean).getShow_type();
		if (type == Consts.SHOW_TYPE_ONE_BIG_VIDEO){
			return mOptionsWithImage;
		} else if (type == Consts.SHOW_TYPE_ONE_SMALL_VIDEO){
			return mOptionsWithImageSmall;
		}
		return mOptions;
	}
	
	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	
	public void handleTime(TextView tv, ChannelItemBean bean){
		long time = Common.dateToLong(bean.getDateline());
		tv.setText(Common.formatTimestmpByJs(time));
	}
	
	public void setKeywords(String[] values){
		mStrKeywords = values;
	}

}