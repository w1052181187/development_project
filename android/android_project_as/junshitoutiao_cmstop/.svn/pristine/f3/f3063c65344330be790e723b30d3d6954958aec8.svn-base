package com.cmstop.jstt.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.cmstop.jstt.beans.data.RankBean;
import com.cmstop.jstt.R;


public class RankIntroductionAdapter extends BasePageListAdapter {

	public RankIntroductionAdapter(Activity context,
			List<RankBean> list) {
		super(context, list);
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_directorate_task_help_grade;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		TextView medal = BaseViewHolder.get(convertView, R.id.item_directorate_task_help_medal);
		TextView grade = BaseViewHolder.get(convertView, R.id.item_directorate_task_help_grade);
		TextView point = BaseViewHolder.get(convertView, R.id.item_directorate_task_help_point);
		
		RankBean bean = (RankBean) obj;
		
		medal.setText(bean.getTitles());
		grade.setText(bean.getIcon());
		point.setText(bean.getIntegral());
	}
}
