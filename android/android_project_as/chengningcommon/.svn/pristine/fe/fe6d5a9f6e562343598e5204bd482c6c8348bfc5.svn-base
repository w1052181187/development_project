package com.chengning.common.base;

import java.util.List;

import android.app.Activity;
import android.view.View;

public class SimplePageListAdapter extends BasePageListAdapter {

	private OnPageListAdapterListener mListener;

	public SimplePageListAdapter(Activity activity, List list,
			OnPageListAdapterListener listener) {
		super(activity, list);
		this.mListener = listener;
	}

	@Override
	public int buildLayoutId() {
		return mListener.onLayoutId();
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		mListener.onHandleLayout(convertView, position, obj);
	}

	public static interface OnPageListAdapterListener {
		public int onLayoutId();

		public void onHandleLayout(View convertView, int position, Object obj);
	}

}
