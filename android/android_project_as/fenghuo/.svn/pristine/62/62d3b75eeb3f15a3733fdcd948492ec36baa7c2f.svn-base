package com.chengning.fenghuo.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.DigBean;
import com.chengning.fenghuo.util.Utils;

public class LikeAdapter extends BasePageListAdapter {
	private List<DigBean> mDigList;

	public LikeAdapter(Activity activity, List list) {
		super(activity, list);
		mDigList = list;
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_dynamic_detail_item_like;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		DigBean bean = mDigList.get(position);
		ImageView img = BaseViewHolder.get(convertView,
				R.id.item_circle_item_like_img);
		Utils.showFace(bean.getFace(), img);
	}
}