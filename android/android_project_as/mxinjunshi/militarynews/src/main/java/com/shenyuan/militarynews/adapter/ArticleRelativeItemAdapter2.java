package com.shenyuan.militarynews.adapter;

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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.activity.ArticleActivity;
import com.shenyuan.militarynews.activity.HomeActivity;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.data.access.LocalStateServer;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.MyCoverRoundedBitmapDisplayer;
import com.shenyuan.militarynews.utils.MyRoundedBitmapDisplayer;

import java.util.List;

public class ArticleRelativeItemAdapter2 extends BaseTypeListAdapter {

	private Activity mContext;
	private DisplayImageOptions mOptions;


	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSamllHeight;
	private DisplayImageOptions mOptionsWithImage;
	private DisplayImageOptions mOptionsWithImageSmall;

	public ArticleRelativeItemAdapter2(Activity activity, List list) {
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
			return ChannelItemType.parseShowType(bean.getNews_show_type()).getType();
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
			case OneBig:
				view = View.inflate(getContext(), R.layout.item_article_relative_one_big, null);
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
		MChannelItemBean bean = (MChannelItemBean) getItem(position);

		// type
		View contentLayout = convertView;
		contentLayout.setVisibility(View.VISIBLE);
		final ChannelItemType itemType = ChannelItemType.parseType(type);

		View currentLayout = convertView;
		final TextView title = BaseViewHolder.get(currentLayout, R.id.item_channel_item_title);
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
			}
		} else {
			ImageLoader.getInstance().cancelDisplayTask(image);
			if(image != null){
				image.setVisibility(View.GONE);
			}
		}
		title.setText(bean.getTitle());
		boolean isChangeItemTvColor = Common.isTrue(bean.getIs_color());
		if (isChangeItemTvColor) {
			title.setTextColor(getContext().getResources().getColor(R.color.article_relative_top_title_color));
		}
		convertView.findViewById(R.id.item_channel_item_line).setTag(bean);
		convertView.setOnClickListener(clickListener);

	}

	private DisplayImageOptions getImageOptions(MChannelItemBean bean){
		if(Const.CHANNEL_ARTICLE_VIDEO.equals(bean.getChannel())){
			if(Const.NEWS_TYPE_ONE_BIG_PIC == bean.getNews_show_type()){
				return mOptionsWithImage;
			}else{
				return mOptionsWithImageSmall;
			}
		}else{
			return mOptions;
		}
	}


	public void handleBeanClick(MChannelItemBean bean, String area){
		LocalStateServer.getInst(getContext()).setReadStateRead(LocalStateServer.PREFIX_CHANNEL_ITEM, bean.getAid());

		boolean isVideo = Common.isTrue(bean.getIs_redirect());
		if (!isVideo) {
			ArticleActivity.launch(getContext(), bean, area);
		} else {
			HomeActivity.launch(getContext());
			HomeActivity.setChangeView(1);
		}

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

			MChannelItemBean bean = (MChannelItemBean) tagView.getTag();
			handleBeanClick(bean, "assoc");
		}
	};

}
