package com.shenyuan.militarynews.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;

@SuppressLint("NewApi")
public class MinimumHeightWebView extends WebView {

	public MinimumHeightWebView(Context context, AttributeSet attrs,
			int defStyle, boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
	}

	public MinimumHeightWebView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public MinimumHeightWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MinimumHeightWebView(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int height = getMeasuredHeight();
	    int minimumHeight = getMinimumHeight();
	    if (height < minimumHeight) {
	        setMeasuredDimension(getMeasuredWidth(), minimumHeight);
	    }
	}

}
