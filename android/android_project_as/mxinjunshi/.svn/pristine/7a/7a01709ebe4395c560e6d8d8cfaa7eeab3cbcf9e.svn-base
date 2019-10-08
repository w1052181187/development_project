package com.shenyuan.militarynews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class LoadFullListView extends ListView {

	public LoadFullListView(Context context) {
		super(context);
	}

	public LoadFullListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LoadFullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
