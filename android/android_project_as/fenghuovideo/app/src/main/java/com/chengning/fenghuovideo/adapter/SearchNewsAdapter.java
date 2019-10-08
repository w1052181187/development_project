package com.chengning.fenghuovideo.adapter;

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
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.data.access.LocalStateDA;
import com.chengning.fenghuovideo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.util.Common;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchNewsAdapter extends BaseTypeListAdapter{
	
	private static final String TAG = SearchNewsAdapter.class.getSimpleName();

	private DisplayImageOptions mOptions;

	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSamllHeight;
	private int mImageOneSmallWidth;
	
	private String[] mStrKeywords;
	
	public SearchNewsAdapter(Activity activity, List list, OnClickListener moreOnClickListenner) {
		super(activity, list);

		mOptions = new DisplayImageOptions.Builder() 
		.showStubImage(R.mipmap.loading)
		.showImageForEmptyUri(R.mipmap.loading)
		.showImageOnFail(R.mipmap.loading)
		.resetViewBeforeLoading(true)
		.cacheInMemory()  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
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
		
	}


	public enum ChannelItemType {
		/**
		 * OneSmall，1个小图，默认type，type必须从0开始
		 */
		OneSmall(0, Consts.SHOW_TYPE_ONE_SMALL),	// 1个小图
		OneBig(1, Consts.SHOW_TYPE_ONE_BIG),	// 1个大图
		;
		
		private int type;
		private int showType[];
		
		ChannelItemType(int type, int... showType){
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
		
		contentLayout.setVisibility(View.VISIBLE);

		TextView title = null;
		ImageView image;
		TextView tag;
		TextView time;
		TextView comment;

		TextView author;
		ImageView face = null;

		View currentLayout = convertView;

		title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
		image = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image);
//		tag = BaseViewHolder.get(currentLayout, R.id.item_channel_item_tag);
//		face = BaseViewHolder.get(convertView, R.id.item_channel_item_face);
		time = BaseViewHolder.get(currentLayout, R.id.item_channel_item_time);
		author = BaseViewHolder.get(currentLayout, R.id.item_channel_item_author);
		comment = BaseViewHolder.get(currentLayout, R.id.item_channel_item_comment);

		ImageView imageVideo = BaseViewHolder.get(currentLayout, R.id.item_channel_item_image_video);
		handleImageVideo(imageVideo, bean);

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
			}
		} else {
			ImageLoader.getInstance().cancelDisplayTask(image);
			if(image != null){
				image.setVisibility(View.GONE);
			}
		}

		handelTitle(title, bean);

		// item
		if(!TextUtils.isEmpty(bean.getCh_name())){
			if (time != null) {
				time.setVisibility(View.GONE);
			}
		}else{
			handleTime(time, bean);

		}

		handleFace(face);
		handleFrom(author,bean);
		handleComment(comment,bean);
		contentLayout.setTag(bean);

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

	private void handleFace(ImageView face) {
		if (face == null) {
			return;
		}
		face.setVisibility(View.GONE);
	}

	private void handelTitle(TextView title, ChannelItemBean bean) {
		if (title == null) {
			return;
		}
		title.setTag(bean);
		title.setText(matcherSearchTitle(getContext().getResources().getColor(R.color.red),  bean.getTitle(), mStrKeywords));
		Common.handleTextViewReaded(getContext(), title, LocalStateDA.getInst(getContext()).isRead(LocalStateDA.PREFIX_CHANNEL_ITEM, bean.getTid()));
	}

	private SpannableString matcherSearchTitle(int color, String text,  String[] keyword) {  
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
		comment.setText(count + "");
		comment.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
	}

	private void handleFrom(TextView author, ChannelItemBean bean) {
		// 推荐下第一页来源显示栏目名称，否则显示来源
		if (author == null) {
			return;
		}
		String from = bean.getList_from();
		author.setVisibility(TextUtils.isEmpty(from) ? View.GONE : View.VISIBLE);
		author.setText(from);
	}


	private DisplayImageOptions getImageOptions(ChannelItemBean bean){
		return getDisplayOptions(bean);
	}
	
	public DisplayImageOptions getDisplayOptions(BaseChannelItemBean  mBean){
		return mOptions;
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
	
	public void handleTime(TextView tv, ChannelItemBean bean){
		if (tv == null) {
			return;
		}
		tv.setVisibility(View.VISIBLE);
		long time = Common.dateToLong(bean.getDateline());
		tv.setText(Common.formatTimestmpByJs(time));
	}
	
	public void setKeywords(String[] values){
		mStrKeywords = values;
	}

}