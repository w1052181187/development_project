package com.chengning.yiqikantoutiao.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.data.bean.ChanBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class ChannelTabAdapter extends BasePageListAdapter {


	public ChannelTabAdapter(Activity activity, List list) {
		super(activity, list);
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_channel_tab;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		ImageView delete = BaseViewHolder.get(convertView, R.id.item_channel_tab_delete);
		TextView name = BaseViewHolder.get(convertView, R.id.item_channel_tab_name);
		
		ChanBean bean = (ChanBean) obj;
		delete.setVisibility(View.INVISIBLE);
		name.setText(bean.getName());
		name.setTag(bean);
	}

}
