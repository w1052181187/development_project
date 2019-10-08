package com.chengning.fenghuovideo.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.data.bean.ArticlesBean;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.fragment.MyFavoriteNewsFragment.DeleteListener;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.MyRoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class MyFavoriteAdapter extends BaseTypeListAdapter {

	private boolean isDeleteFlag = false;
	private DeleteListener listener;
	private StringBuffer mCmtBuffer = new StringBuffer();
	private int mImageThreeSmallWidth;
	private int mImageThreeSmallHeight;
	private int mImageOneSmallWidth;
	private int mImageOneSmallHeight;
	
	private DisplayImageOptions mOptionsWithImageSmall;
	private DisplayImageOptions mOptions;
	
	public MyFavoriteAdapter(Activity activity, List list, DeleteListener deleteListener, boolean isDeleteFlag) {
		super(activity, list);
		this.isDeleteFlag = isDeleteFlag;
		this.listener = deleteListener;
		DisplayUtil.getInst().init(activity);
		int gapWidth = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		int gapThreeSmall = activity.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin_three_small); 
		float imageLayoutWidth = (float)(DisplayUtil.getInst().getScreenWidth() - gapWidth * 2);
		
		mImageThreeSmallWidth =  ((int)imageLayoutWidth - gapThreeSmall * 2) / 3;
		mImageThreeSmallHeight = (int) (mImageThreeSmallWidth / 1.5);
		mImageOneSmallWidth = mImageThreeSmallWidth;
		mImageOneSmallHeight = mImageThreeSmallHeight;
		
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
			.displayer(new MyRoundedBitmapDisplayer(App.getInst().getResources().getDimensionPixelSize(R.dimen.common_round_corner)))
			.build();
		
	}

	@Override
	public int buildLayoutId(int position, int show_type) {
		return -1;
//		if (Common.isTrue(SettingManager.getInst().getNoPicModel())) {
//			return R.layout.item_my_favorite;
//		}
//		if(show_type == Consts.SHOW_TYPE_THREE_SMALL || show_type == Consts.SHOW_TYPE_ONE_BIG_TWO_SMALL){
//			return R.layout.item_my_favorite_three_small;
//		}else{
//			return R.layout.item_my_favorite;
//		}
	}
	
	@Override
	public View buildLayoutView(int position,int type){
		FavItemType itemType = FavItemType.parseType(type);
		View view = null;
		switch(itemType){
			default:
			case OneSmall:
				view = View.inflate(getContext(), R.layout.item_my_favorite, null);
				break;
//			case ThreeSmall:
//				view = View.inflate(getContext(), R.layout.item_my_favorite_three_small, null);
//				break;
		}
		return view;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj, int type) {
		FavItemType itemType = FavItemType.parseType(type);
		
		ImageView delete = BaseViewHolder.get(convertView, R.id.item_favorite_delete);
		ImageView img = BaseViewHolder.get(convertView, R.id.item_favorite_img);
		TextView title = BaseViewHolder.get(convertView, R.id.item_favorite_title);
		View mNoImagView = BaseViewHolder.get(convertView, R.id.item_favorite_no_img_view);
		TextView date = BaseViewHolder.get(convertView, R.id.item_favorite_date);

		BaseArticlesBean bean= (BaseArticlesBean) obj;
		if (Common.isTrue(SettingManager.getInst().getNoPicModel())) {
			ImageLoader.getInstance().cancelDisplayTask(img);
			img.setVisibility(View.GONE);
			LayoutParams params = mNoImagView.getLayoutParams();
			params.height = 24;
			mNoImagView.setLayoutParams(params);
		} else {
			img.setVisibility(View.VISIBLE);
			String imgUrl = (String)bean.getImage_list();
			if (bean instanceof ChannelItemBean) {
				int type1 = ((ChannelItemBean)bean).getShow_type();
				if (2 == type1) {
					imgUrl = ((ChannelItemBean)bean).getImage_arr().get(0);
				}
			}
			updateSize(img, mImageOneSmallWidth, mImageOneSmallHeight);
			ImageLoader.getInstance().displayImage(imgUrl, img, mOptions);

		}
		handleTitle(title, bean);

		handleTime(date, bean);
		mCmtBuffer.setLength(0);
		mCmtBuffer.append("评论：").append(bean.getReplys());
		handleDelete(delete, position);
		ImageView imageVideo = BaseViewHolder.get(convertView, R.id.item_favorite_img_video);
		handleImageVideo(imageVideo, bean);
	}

	private void handleTitle(TextView title, BaseArticlesBean bean) {
		if (title == null) {
			return;
		}
		title.setText(bean.getTitle());
	}

	private void handleDelete(ImageView delete, int position) {
		if (delete == null) {
			return;
		}
		delete.setOnClickListener(listener);
		delete.setTag(position);
		delete.setVisibility(isDeleteFlag ? View.VISIBLE : View.GONE);
	}

	private void handleImageVideo(ImageView imageVideo, final BaseArticlesBean bean) {
		if (imageVideo == null) {
			return;
		}
		String type = bean.getContent_type();
		if(type.equals(Consts.CONTENT_TYPE_VIDEO)){
			imageVideo.setVisibility(View.VISIBLE);
			return;
		}
		imageVideo.setVisibility(View.GONE);
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

	private void handleTime(TextView date, BaseArticlesBean bean) {
		if (date == null) {
			return;
		}
		String dateLine = bean.getDateline();
		
		long time = Common.dateToLong(dateLine);
		if (time == 0) {
			dateLine = Common.getDateYYYYMMDDNotNull(dateLine);
		} else {
			dateLine = Common.getDateYYYYMMDDNotNull(time);
		}
		date.setText(dateLine);
		date.setVisibility(!isDeleteFlag ? View.VISIBLE : View.GONE);
	}
	
	public void setDelFlag(boolean isEditFlag) {
		isDeleteFlag = isEditFlag;
	}

	@Override
	public int getItemViewType(int position) {
		BaseArticlesBean bean = (BaseArticlesBean) getItem(position);
		FavItemType itemType = FavItemType.OneSmall;
		if(bean instanceof ChannelItemBean){
			itemType = FavItemType.parseItemType(((ChannelItemBean)bean).getShow_type(), bean);
		}else if(bean instanceof ArticlesBean){
			itemType = FavItemType.parseItemType(((ArticlesBean)bean).getShow_type(), bean);
		}
//		FavItemType itemType = FavItemType.parseItemType(bean.getShow_type(), bean);
		if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
			return FavItemType.OneSmall.getType();
		}else{
			return itemType.getType();
		}
	}

	@Override
	public int getViewTypeCount() {
		if(Common.isTrue(SettingManager.getInst().getNoPicModel())){
			return 1;
		}else{
			return FavItemType.values().length;
		}
	}
	
	
	
	public static enum FavItemType {
		OneSmall(0, Consts.SHOW_TYPE_ONE_SMALL), 
		ThreeSmall(1, Consts.SHOW_TYPE_THREE_SMALL, Consts.SHOW_TYPE_ONE_BIG_TWO_SMALL), 
		;
		private int type;
		private int showType[];
		
		private FavItemType(int type, int... showType){
			this.type = type;
			this.showType = showType;
		}
		public int getType(){
			return type;
		}
		public int[] getShowType(){
			return showType;
		}
		public static FavItemType parseType(int type){
			FavItemType it = FavItemType.OneSmall;
			for(FavItemType c : values()){
				if(c.type == type){
					it = c;
					break;
				}
			}
			return it;
		}
		
		public static FavItemType parseItemType(int showType, Object obj){
			FavItemType it = FavItemType.OneSmall;
			for(FavItemType c : values()){
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

}
