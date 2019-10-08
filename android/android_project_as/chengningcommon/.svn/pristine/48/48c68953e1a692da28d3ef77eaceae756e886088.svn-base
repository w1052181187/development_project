package com.chengning.common.base;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BasePageListAdapter extends BaseAdapter {
	private List mList;

	private Activity mContext;

	public BasePageListAdapter(Activity activity, List list) {
		this.mList = list;
		this.mContext = activity;
	}
	
	public void setList(List list){
		this.mList = list;
	}
	
	public List getList(){
		return mList;
	}
	
	public Activity getContext(){
		return mContext;
	}

	public int getCount() {
		if (mList == null) {
			return 0;
		}else{
			return mList.size();
		}
	}

	public Object getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, buildLayoutId(), null);
		}
		handleLayout(convertView, position, getItem(position));
		return convertView;
	}

	public abstract int buildLayoutId();

	public abstract void handleLayout(View convertView, int position, Object obj);

}
