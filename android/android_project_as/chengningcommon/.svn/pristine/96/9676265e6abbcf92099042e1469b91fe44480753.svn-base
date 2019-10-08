package com.chengning.common.base;

import android.util.SparseArray;
import android.view.View;

public class BaseViewHolder {

	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (null == viewHolder) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (null == childView) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);

		}
		return (T) childView;
	}

	public static <T extends View> T get(View view, int id, int key) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag(key);
		if (null == viewHolder) {
			viewHolder = new SparseArray<View>();
			view.setTag(key, viewHolder);
		}
		View childView = viewHolder.get(id);
		if (null == childView) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);

		}
		return (T) childView;
	}
}
