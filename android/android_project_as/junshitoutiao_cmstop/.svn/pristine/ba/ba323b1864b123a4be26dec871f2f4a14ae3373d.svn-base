package com.cmstop.jstt.adapter;


import java.util.List;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.cmstop.jstt.beans.data.DirectorateTaskBean;
import com.cmstop.jstt.R;

public class PointHintNoCompleteTaskAdapter extends BasePageListAdapter {

	public PointHintNoCompleteTaskAdapter(Activity activity, List list) {
		super(activity, list);
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_dialog_upgrade;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		DirectorateTaskBean bean = (DirectorateTaskBean)obj;
		TextView des = BaseViewHolder.get(convertView, R.id.item_upgrade_des);
		TextView num = BaseViewHolder.get(convertView, R.id.item_upgrade_num);
		SpannableStringBuilder builder= new SpannableStringBuilder();
		builder.append(String.valueOf(position + 1)).append(".").append(bean.getRulename());
		des.setText(builder);
		num.setText("+"+bean.getExtcredits2());
		
	}

}
