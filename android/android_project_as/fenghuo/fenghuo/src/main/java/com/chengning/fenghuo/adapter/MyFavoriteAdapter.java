package com.chengning.fenghuo.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseTypeListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.adapter.ChannelItemRecommendAdapter.ChannelItemType;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.fragment.MyFavoriteNewsFragment.DeleteListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.MyCoverRoundedBitmapDisplayer;
import com.chengning.fenghuo.util.MyRoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
			case ThreeSmall:
				view = View.inflate(getContext(), R.layout.item_my_favorite_three_small, null);
				break;
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
		
		ImageView img1 = BaseViewHolder.get(convertView, R.id.item_favorite_img1);
		ImageView img2 = BaseViewHolder.get(convertView, R.id.item_favorite_img2);
		ImageView img3 = BaseViewHolder.get(convertView, R.id.item_favorite_img3);
		
		BaseArticlesBean bean= (BaseArticlesBean) obj;
		if (Common.isTrue(SettingManager.getInst().getNoPicModel())) {
			ImageLoader.getInstance().cancelDisplayTask(img);
			img.setVisibility(View.GONE);
			LayoutParams params = mNoImagView.getLayoutParams();
			params.height = 24;
			mNoImagView.setLayoutParams(params);
		} else {	
			if(itemType == FavItemType.ThreeSmall){
				img.setVisibility(View.GONE);
				updateSize(img1, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(img2, mImageThreeSmallWidth, mImageThreeSmallHeight);
				updateSize(img3, mImageThreeSmallWidth, mImageThreeSmallHeight);
				if(bean instanceof ChannelItemBean){
					ImageLoader.getInstance().displayImage(((ChannelItemBean)bean).getImage_arr().get(0), img1, mOptions);
					ImageLoader.getInstance().displayImage(((ChannelItemBean)bean).getImage_arr().get(1), img2, mOptions);
					ImageLoader.getInstance().displayImage(((ChannelItemBean)bean).getImage_arr().get(2), img3, mOptions);
				}else if(bean instanceof ArticlesBean){
					ImageLoader.getInstance().displayImage(((ArticlesBean)bean).getImage_arr().get(0), img1, mOptions);
					ImageLoader.getInstance().displayImage(((ArticlesBean)bean).getImage_arr().get(1), img2, mOptions);
					ImageLoader.getInstance().displayImage(((ArticlesBean)bean).getImage_arr().get(2), img3, mOptions);
				}
			}else{
				img.setVisibility(View.VISIBLE);
				String imgUrl = (String)bean.getImage_list();
				if (bean instanceof ChannelItemBean) {
					int type1 = ((ChannelItemBean)bean).getShow_type();
					if (2 == type1) {
						imgUrl = ((ChannelItemBean)bean).getImage_arr().get(0);
					}
				}	
				updateSize(img, mImageOneSmallWidth, mImageOneSmallHeight);
				if(bean.getContent_type() != null){
					String type1 = bean.getContent_type();
					if(type1.equals(Consts.CONTENT_TYPE_VIDEO)){
						ImageLoader.getInstance().displayImage(imgUrl, img, mOptionsWithImageSmall);
					}else{
						ImageLoader.getInstance().displayImage(imgUrl, img, mOptions);
					}
				}else{
					ImageLoader.getInstance().displayImage(imgUrl, img, mOptions);
				}
			}
			
		}
		title.setText(bean.getTitle());
		handleTime(date, bean);
		mCmtBuffer.setLength(0);
		mCmtBuffer.append("评论：").append(bean.getReplys());
		
		delete.setOnClickListener(listener);
		delete.setTag(position);
		delete.setVisibility(isDeleteFlag ? View.VISIBLE : View.GONE);
		date.setVisibility(!isDeleteFlag ? View.VISIBLE : View.GONE);
	}
	
	private void updateSize(View v, int width, int height){
		LayoutParams lp = v.getLayoutParams();
		lp.width = width;
		lp.height = height;
		v.setLayoutParams(lp);
	}

	private void handleTime(TextView date, BaseArticlesBean bean) {
		String dateLine = bean.getDateline();
		
		long time = Common.dateToLong(dateLine);
		if (time == 0) {
			dateLine = Common.getDateYYYYMMDDNotNull(dateLine);
		} else {
			dateLine = Common.getDateYYYYMMDDNotNull(time);
		}
//		if (!TextUtils.isEmpty(dateLine) && TextUtils.isDigitsOnly(dateLine)) {
//			dateLine = Common.getDateYYYYMMDDNotNull(Long.parseLong(dateLine));
//		} else {
//			dateLine = Common.getDateYYYYMMDDNotNull(dateLine);
//		}
		date.setText(dateLine);
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
