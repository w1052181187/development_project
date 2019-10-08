package com.chengning.common.base;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.chengning.common.base.BasePageListAdapter;

public abstract class BaseTypeListAdapter extends BasePageListAdapter {
	public BaseTypeListAdapter(Activity activity, List list) {
		super(activity, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (convertView == null) {
			convertView = buildLayoutView(position, type);
		}
		handleLayout(convertView, position, getItem(position), type);
		return convertView;
	}

	@Override
	public int buildLayoutId() {
		return 0;
	};

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
	};

	public View buildLayoutView(int position, int type) {
		return View.inflate(getContext(), buildLayoutId(position, type), null);
	}

	@Override
	public abstract int getItemViewType(int position);

	@Override
	public abstract int getViewTypeCount();

	public abstract int buildLayoutId(int position, int type);

	public abstract void handleLayout(View convertView, int position,
			Object obj, int type);

}
