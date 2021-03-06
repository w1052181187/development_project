package com.chengning.fenghuo.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.MilitaryGradeBean;

public class DirectorateTaskHelpGradeAdapter extends BasePageListAdapter {

	public DirectorateTaskHelpGradeAdapter(Activity context,
			List<MilitaryGradeBean> list) {
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
		
		MilitaryGradeBean bean = (MilitaryGradeBean) obj;
		
		medal.setText(bean.getName());
		grade.setText(bean.getRank());
		point.setText(bean.getCreditshigher());
	}
}
