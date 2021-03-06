package com.chengning.fenghuo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.Consts.MessageType;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.ArticleActivity;
import com.chengning.fenghuo.activity.DynamicDetailActivity;
import com.chengning.fenghuo.activity.PhotoPageActivity;
import com.chengning.fenghuo.activity.PhotoPageCircleActivity;
import com.chengning.fenghuo.activity.VideoDetailActivity;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.data.bean.Image;
import com.chengning.fenghuo.util.ArticleCommentListeners;
import com.chengning.fenghuo.util.ArticleCommentListeners.DynamicCommentOnClickListener;
import com.chengning.fenghuo.util.ArticleCommentListeners.DynamicCommentOnLongClickListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.DynamicTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

public abstract class MessagelItemAdapter extends BaseTypeListAdapter {
	
	private static final int TYPE_GRAY_ONE = 0;
	private static final int TYPE_WHITE_TWO = 1;
	private DisplayImageOptions mOptions;
	private DisplayImageOptions mOptionsForIcon;
	private int mNoPicModel;
	private Activity mContext;
	
	private MessageType mType;
	
	private List<ImageView> mImageViewList;
	
	public MessagelItemAdapter(Activity activity, List list, MessageType type) {
		super(activity, list);
		this.mContext = activity;
		this.mNoPicModel = SettingManager.getInst().getNoPicModel();
		mOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)
				.showImageForEmptyUri(R.drawable.loading)
				.showImageOnFail(R.drawable.loading)
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();
		mOptionsForIcon = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.share_default_logo)
			.showImageForEmptyUri(R.drawable.share_default_logo)
			.showImageOnFail(R.drawable.share_default_logo)
			.resetViewBeforeLoading(true).cacheInMemory(true)
			.cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
			.build();
		DisplayUtil.getInst().init(activity);
		this.mType = type;
		mImageViewList = new ArrayList<ImageView>();
	}

	@Override
	public int getItemViewType(int position) {
		DynamicItemBean bean = (DynamicItemBean) getItem(position);
		// has parent and root
		if(bean.getRoot_list() != null){
			return TYPE_WHITE_TWO;
		}
		return TYPE_GRAY_ONE;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int buildLayoutId(int position, int type) {
		return R.layout.item_message_item_gray_one;
//		switch (type) {
//		default:
//		case TYPE_GRAY_ONE:
//			return R.layout.item_message_item_gray_one;
//		case TYPE_WHITE_TWO:
//			return R.layout.item_message_item_white_two;
//		}
	}

	@Override
	public void handleLayout(View convertView, final int position, Object obj,
			int type) {
		final ImageView head = BaseViewHolder.get(convertView,
				R.id.item_message_head);
		TextView name = BaseViewHolder.get(convertView,
				R.id.item_message_name);
		TextView time = BaseViewHolder.get(convertView,
				R.id.item_message_time);
		TextView from = BaseViewHolder.get(convertView,
				R.id.item_message_from);
		DynamicTextView content = BaseViewHolder.get(convertView,
				R.id.item_message_content);
		ImageView summaryImage = BaseViewHolder.get(convertView,
				R.id.item_message_summary_image);
		TextView summaryTitle = BaseViewHolder.get(convertView,
				R.id.item_message_summary_title);
		
		// image
		View view1  = BaseViewHolder.get(convertView,
				R.id.item_message_content_image_ll1);
		View view2  = BaseViewHolder.get(convertView,
				R.id.item_message_content_image_ll2);
		View view3  = BaseViewHolder.get(convertView,
				R.id.item_message_content_image_ll3);
		final ImageView image1 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image1);
		ImageView image2 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image2);
		ImageView image3 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image3);
		ImageView image4 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image4);
		ImageView image5 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image5);
		ImageView image6 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image6);
		ImageView image7 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image7);
		ImageView image8 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image8);
		ImageView image9 = BaseViewHolder.get(convertView,
				R.id.item_message_content_image9);
		mImageViewList.removeAll(mImageViewList);
		mImageViewList.add(image1);
		mImageViewList.add(image2);
		mImageViewList.add(image3);
		mImageViewList.add(image4);
		mImageViewList.add(image5);
		mImageViewList.add(image6);
		mImageViewList.add(image7);
		mImageViewList.add(image8);
		mImageViewList.add(image9);
		
		View summaryL = BaseViewHolder.get(convertView,
				R.id.item_message_summary_ll);
		
		TextView summarySummary = BaseViewHolder.get(convertView,
				R.id.item_message_summary_summary);
		final TextView comment_tv = BaseViewHolder.get(convertView,
				R.id.item_message_comment_tv);

		final DynamicItemBean bean = (DynamicItemBean) obj;
		final DynamicItemBean summaryBean = type == TYPE_WHITE_TWO ? bean.getRoot_list() : bean.getParent_list();
		
		// main
		Utils.showFace(bean.getFace(), head);
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			head.setColorFilter(getContext().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
		}
		Common.handleUserNameDisplay(getContext(), bean, name);
		name.setVisibility(!TextUtils.isEmpty(bean.getNickname()) ? View.VISIBLE
				: View.GONE);
		time.setText(Common.getDateMMDDHHMMNotNull(bean.getDateline()));
		from.setText("来自" + bean.getFrom());
		
		String contentTxt = Utils.handleDynamicContentConvert(bean);
		if(mType == MessageType.COMMENT){
			content.setTag(summaryBean);
			content.setOnClickListener(mSummaryOnClickListener);
			
			content.setContent(contentTxt, ImageSpan.ALIGN_BOTTOM);
			content.setOnLongClickListener(new DynamicCommentOnLongClickListener(mContext,null, contentTxt,true));
		}else{
			content.setText(contentTxt);
			content.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
		}
		comment_tv.setText("");
		
		boolean hasImage = false;
		if (bean.getImage_list() != null && bean.getImage_list().size() > 0) {
			hasImage = true;
		} 
		switch (SettingManager.getInst().getNoPicModel()) {
		case Common.TRUE:
			setImageViewGone();
			for (ImageView o : mImageViewList) {
				ImageLoader.getInstance().cancelDisplayTask(o);
			}
			break;
		case Common.FALSE:
			setImageViewGone();
			if (hasImage) {
				int size = bean.getImage_list().size();
				view1.setVisibility(View.VISIBLE);
				view2.setVisibility(size > 3 ? View.VISIBLE : View.GONE);
				view3.setVisibility(size > 6 ? View.VISIBLE : View.GONE);
				
				if(size == 1){
					image1.setVisibility(View.VISIBLE);
					
					Image mImageBean = bean.getImage_list().get(0);
					
					int w = mImageBean.getImage_width();
					int h = mImageBean.getImage_height();
					updateSizeForOneBig(image1, w, h);
					ImageLoader.getInstance().displayImage(mImageBean.getImage_middle(), image1, mOptions);
					if (Common.isTrue(SettingManager.getInst()
							.getNightModel())) {
						image1.setColorFilter(
								mContext.getResources().getColor(
										R.color.night_img_color),
								PorterDuff.Mode.MULTIPLY);
					}
					image1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mContext, PhotoPageCircleActivity.class);
							intent.putExtra("index", (Integer)image1.getTag());
							intent.putExtra("imgList", bean.getImage_list());
							mContext.startActivity(intent);
							mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);  
						}
					});
					
				}else{
					for (int i = 0; i < bean.getImage_list().size(); i++) {
						int j = i;
						if(size == 4 && i > 1){
							j++;
						}
						final ImageView o = mImageViewList.get(j);
						o.setTag(i);
						o.setVisibility(View.VISIBLE);
						updateSize(o, mContext.getResources().getDimensionPixelSize(R.dimen.circle_list_img_width),
								mContext.getResources().getDimensionPixelSize(R.dimen.circle_list_img_width));
						ImageLoader.getInstance().displayImage(
								bean.getImage_list().get(i).getImage_middle(),
								o, mOptions);
						if (Common.isTrue(SettingManager.getInst()
								.getNightModel())) {
							o.setColorFilter(
									mContext.getResources().getColor(
											R.color.night_img_color),
									PorterDuff.Mode.MULTIPLY);
						}
						
						o.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(mContext, PhotoPageCircleActivity.class);
								intent.putExtra("index", (Integer)o.getTag());
								intent.putExtra("imgList", bean.getImage_list());
								mContext.startActivity(intent);
								mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);  
							}
						});
					}
				}
			} else {
				view1.setVisibility(View.GONE);
				view2.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
		
		// parent
//		if(type == TYPE_WHITE_TWO && summaryBean != null){
//			View parentL = BaseViewHolder.get(convertView, R.id.item_message_summary_parent_ll);
//			parentL.setTag(bean.getRoot_list());
//			parentL.setOnClickListener(mParentOnClickListener);
//			
//			DynamicTextView contentParent = BaseViewHolder.get(convertView,
//					R.id.item_message_content_parent);
//			// TODO
//			SpannableStringBuilder buffer = new SpannableStringBuilder();
//			buffer.append(bean.getNickname());
//			buffer.setSpan(new URLSpan("com.chengning.fenghuo.userinfo://"), 0,
//					buffer.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
//			buffer.append(": ");
//			buffer.append((!Common.isListEmpty(bean.getContent()) 
//					? bean.getContent().get(0) 
//					: ""));
//			contentParent.setContent(buffer, ImageSpan.ALIGN_BOTTOM);
//			bean.setIsReplyClicked(Common.TRUE);
//		}

		// summary
		if(summaryBean != null){
			summaryL.setVisibility(View.VISIBLE);

			switch (mNoPicModel) {
			case Common.TRUE:
				ImageLoader.getInstance().cancelDisplayTask(summaryImage);
				summaryImage.setVisibility(View.GONE);
				break;
			case Common.FALSE:
				
				if (summaryBean.getImage_list() != null && summaryBean.getImage_list().size() > 0) {
					summaryImage.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(summaryBean.getImage_list().get(0).getImage_middle(), summaryImage, mOptionsForIcon);
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						summaryImage.setColorFilter(mContext.getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
					}
				}else{
					summaryImage.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage("drawable://" + R.drawable.share_default_logo, summaryImage, mOptionsForIcon);
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						summaryImage.setColorFilter(mContext.getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
					}
				}
				
				if (summaryBean.getImage() != null){
					summaryImage.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(summaryBean.getImage(), summaryImage, mOptionsForIcon);
					if (Common.isTrue(SettingManager.getInst().getNightModel())) {
						summaryImage.setColorFilter(mContext.getResources().getColor(R.color.night_img_color),PorterDuff.Mode.MULTIPLY);
					}
				}
				break;
			default:
				break;
			}
			
			if(!TextUtils.isEmpty(summaryBean.getTitle())){
				summaryTitle.setText(summaryBean.getTitle());
			}else{
				String txt = Utils.handleDynamicContentConvert(summaryBean);
				summaryTitle.setText(!TextUtils.isEmpty(txt) 
						? txt.trim() : "原文章已被删除");
			}
			summarySummary.setText(Utils.handleDynamicContentConvert(summaryBean));
			content.setTag(summaryBean);
		}else{
			summaryL.setVisibility(View.GONE);
			content.setTag(bean);
		}
		
//		bean.setIsReplyClicked(Common.TRUE);
		
		head.setTag(bean);
		head.setOnClickListener(new ArticleCommentListeners.NameOnClickListener(getContext()));
		comment_tv.setTag(bean);
		comment_tv.setOnClickListener(new DynamicCommentOnClickListener(getContext(),null,null));

		summaryL.setTag(summaryBean);
		summaryL.setOnClickListener(mSummaryOnClickListener);
		
		//抵消点击事件往下传,从而让别的控件控件产生点击效果..
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		configItemView(convertView, position, obj, type);
	}

	private void updateSize(View v, int width, int height) {
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}
	
	private void setImageViewGone() {
		for (ImageView o : mImageViewList) {
			o.setVisibility(View.GONE);
		}
	}
	
	private void updateSizeForOneBig(View v, int w, int h){
		float min = 45.0f;
		float max = 200.25f;
		float miniHWScale = min/max;
		float curH = 0;
		float curW = 0;
		
		if( w > h ){
			float scale = h/(w * 1.0f);
			if(scale < miniHWScale){
				curH = min;
				curW = curH/h * w;
			}else{
				curW = max;
				curH = curW/w * h;
			}
		}else if( w < h ){
			float scale = w/(h * 1.0f);
			if(scale < miniHWScale){
				curW = min;
				curH = curW/w * h;
			}else{
				curH = max;
				curW = curH/h * w;
			}
		}else{
			curW = max;
			curH = max;
		}
		if(curW > max) curW = max;
		if(curH > max) curH = max;
		

		curW = DisplayUtil.getInst().dip2px(curW);
		curH = DisplayUtil.getInst().dip2px(curH);
		updateSize(v, (int)curW, (int)curH);
	}
	
	private OnClickListener mSummaryOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			DynamicItemBean bean = (DynamicItemBean) v.getTag();
			if (bean == null || TextUtils.isEmpty(bean.getTid())) {
				return;
			}
			if (!Common.isTrue(bean.getIs_subscribe())) {
				DynamicDetailActivity.launch(getContext(), bean);
			} else {
				String articleType = bean.getContent_type();

				if(articleType.equals(Consts.CONTENT_TYPE_TUWEN)){
                    ChannelItemBean mChannelBean = new ChannelItemBean();
                    mChannelBean.setTid(bean.getTid());
                    mChannelBean.setTitle(bean.getTitle());
                    PhotoPageActivity.launch(getContext(), mChannelBean,true);
				} else if (TextUtils.equals(articleType,
						Consts.CONTENT_TYPE_VIDEO)) {
					VideoDetailActivity.launch(getContext(), bean);
				} else {
					ArticleActivity.launch(getContext(), bean);
				}
			}
		}
	};
	
	private OnClickListener mParentOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			DynamicItemBean bean = (DynamicItemBean) v.getTag();
			if (bean == null || TextUtils.isEmpty(bean.getTid())) {
				return;
			}
			if (!Common.isTrue(bean.getIs_subscribe())) {
				DynamicDetailActivity.launch(getContext(), bean);
			} else {
				String articleType = bean.getContent_type();

				if(articleType.equals(Consts.CONTENT_TYPE_TUWEN)){
					ChannelItemBean mChannelBean = new ChannelItemBean();
					mChannelBean.setTid(bean.getTid());
					mChannelBean.setTitle(bean.getTitle());
					PhotoPageActivity.launch(getContext(), mChannelBean,true);
				} else if (TextUtils.equals(articleType,
						Consts.CONTENT_TYPE_VIDEO)) {
					VideoDetailActivity.launch(getContext(), bean);
				} else {
					ArticleActivity.launch(getContext(), bean);
				}
			}
		}
	};
	
	abstract public void configItemView(View convertView, int position, Object obj,
			int type);

}
