package com.chengning.fenghuovideo.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.data.bean.GradeBean;

import java.util.List;

public class GradeDescriptionAdapter extends BasePageListAdapter {

	public GradeDescriptionAdapter(Activity context,
                                   List<GradeBean> list) {
		super(context, list);
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_grade_description;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		TextView medal = BaseViewHolder.get(convertView, R.id.item_directorate_task_help_medal);
		TextView grade = BaseViewHolder.get(convertView, R.id.item_directorate_task_help_grade);
		TextView point = BaseViewHolder.get(convertView, R.id.item_directorate_task_help_point);
		
		GradeBean bean = (GradeBean) obj;
		
		medal.setText(bean.getName());
		grade.setText(bean.getRank());
		point.setText(bean.getCreditshigher());
	}
}
