package com.cmstop.jstt.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.cmstop.jstt.beans.data.DirectorateTaskBean;
import com.cmstop.jstt.R;


public class DirectorateTaskAdapter extends BasePageListAdapter {

	private List<Drawable> iconList = new ArrayList<Drawable>();
	private StringBuffer buffer = new StringBuffer();
	public DirectorateTaskAdapter(Activity activity, List list) {
		super(activity, list);
		setIcon(list);
	}

	public void setIcon(List<DirectorateTaskBean> list) {
		if (null != list) {
			iconList.clear();
			for (int i = 0;i < list.size();i++) {
				switch (i) {
				case 0:
					iconList.add(i, getContext().getResources().getDrawable(R.drawable.task_btn_daily_login));
					break;
				case 1:
					iconList.add(i, getContext().getResources().getDrawable(R.drawable.task_btn_share));
					break;
				case 2:
					iconList.add(i, getContext().getResources().getDrawable(R.drawable.task_btn_comment));
					break;
	
				default:
					break;
				}
			}
		} 
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_directorate_task;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		TextView name = BaseViewHolder.get(convertView, R.id.item_directorate_task_name);
		TextView progress = BaseViewHolder.get(convertView, R.id.item_directorate_task_complete_progress);
		ImageView taskImage = BaseViewHolder.get(convertView,R.id.item_directorate_class_img);
		ImageView progressImage = BaseViewHolder.get(convertView,R.id.item_directorate_status_img);
		
//		TextView complete = BaseViewHolder.get(convertView, R.id.item_directorate_task_complete);
		
		DirectorateTaskBean bean = (DirectorateTaskBean) obj;
		
		name.setText(bean.getRulename());
		iconList.get(position).setBounds(0, 0, iconList.get(position).getMinimumWidth(),
				iconList.get(position).getMinimumHeight());
		taskImage.setBackgroundDrawable(iconList.get(position));
		buffer.setLength(0);
		buffer.append(bean.getCyclenum()).append("/").append(bean.getRewardnum());
		progress.setText(buffer.toString());
		setItemResource(bean,progressImage);
//		if ("完成".equals(bean.getComplete())) {
//			
//			complete.setTextColor(getContext().getResources().getColor(R.color.home_bottom_txt));
//		}
//		complete.setText(bean.getComplete());
//		complete.setTag(bean);
	}
	public void setItemResource(DirectorateTaskBean mBean ,ImageView imageView){
		if(mBean != null){
			if(mBean.getAction() != null){
				if(mBean.getAction().equals("login")){
					if(mBean.getComplete().equals("完成")){
						imageView.setBackgroundResource(R.drawable.task_fini_login);
					}else{
						imageView.setBackgroundResource(R.drawable.task_unfini_login);
					}
				}else if(mBean.getAction().equals("share")){
					if(mBean.getComplete().equals("完成")){
						imageView.setBackgroundResource(R.drawable.task_fini_share);
					}else{
						imageView.setBackgroundResource(R.drawable.task_unfini_share);
					}
				}else if(mBean.getAction().equals("reply")){
					if(mBean.getComplete().equals("完成")){
						imageView.setBackgroundResource(R.drawable.task_fini_comment);
					}else{
						imageView.setBackgroundResource(R.drawable.task_unfini_comment);
					}
				}
			}
		}
	}

}
