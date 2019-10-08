package com.shenyuan.militarynews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class PhotoContentRelativeLayout extends RelativeLayout {
	
	private OnTouchListener onDispatchTouchEventListener;
	
	public PhotoContentRelativeLayout(Context context) {
		super(context);
	}

	public PhotoContentRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PhotoContentRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		if(onDispatchTouchEventListener != null)onDispatchTouchEventListener.onTouch(this, ev);
		return super.dispatchTouchEvent(ev);
	}
	
	public void setOnDispatchTouchEventListener(OnTouchListener listener){
		this.onDispatchTouchEventListener = listener;
	}

}
