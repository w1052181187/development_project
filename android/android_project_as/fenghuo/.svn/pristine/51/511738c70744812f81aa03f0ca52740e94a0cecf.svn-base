package com.chengning.fenghuo.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.ChanBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ChannelTabAdapter extends BasePageListAdapter {
	
	private DisplayImageOptions mOptions;

	public ChannelTabAdapter(Activity activity, List list) {
		super(activity, list);
 		mOptions = new DisplayImageOptions.Builder() 
		.showStubImage(R.drawable.column_default)
		.showImageForEmptyUri(R.drawable.column_default)
		.showImageOnFail(R.drawable.column_default)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)  
		.build(); 
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_channel_tab;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		ImageView delete = BaseViewHolder.get(convertView, R.id.item_channel_tab_delete);
		ImageView image = BaseViewHolder.get(convertView, R.id.item_channel_tab_image);
		TextView name = BaseViewHolder.get(convertView, R.id.item_channel_tab_name);
		
		ChanBean bean = (ChanBean) obj;
		delete.setVisibility(View.INVISIBLE);
		ImageLoader.getInstance().displayImage(bean.getPic(), image, mOptions);
		name.setText(bean.getName());
		name.setTag(bean);
	}

}
