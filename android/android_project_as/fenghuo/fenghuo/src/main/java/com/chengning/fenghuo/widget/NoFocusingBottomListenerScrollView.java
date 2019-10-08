package com.chengning.fenghuo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.chengning.fenghuo.util.Common;

import java.util.ArrayList;
import java.util.List;

public class NoFocusingBottomListenerScrollView extends ScrollView {

	private OnScrollToBottomListener onScrollToBottom;
	private boolean isLoading = false;

	public NoFocusingBottomListenerScrollView(Context context) {
		super(context);
	}

	public NoFocusingBottomListenerScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoFocusingBottomListenerScrollView(Context context, AttributeSet attrs,
											  int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected boolean onRequestFocusInDescendants(int direction,
			Rect previouslyFocusedRect) {
		return true;
	}

	private double yDown = 0;
	private double yUp = 0;
	private double mCurY;
	private long timeDown;
	private long timeUp;
	private double sample;
	private ArrayList<Double> sampleList = new ArrayList<>();

	public List getSampleList() {
		return sampleList;
	}
	public void clearSamples() {
		sampleList.clear();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yDown = ev.getY();
				timeDown = Common.getCurTimeMillis();
				break;
			case MotionEvent.ACTION_MOVE:
//				mCurY = event.getY();

				break;
			case MotionEvent.ACTION_UP:
				yUp = ev.getY();
				timeUp = Common.getCurTimeMillis();
				mCurY = yDown - yUp;
				if(mCurY > 0) {
					sample = mCurY /(timeUp - timeDown);
					sampleList.add(0, sample);
				} else if(mCurY < 0) {
					if (!Common.isListEmpty(sampleList)) {
//						sampleList.remove(0);
					}
				}
				break;
			default:
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
								  boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		if (null != onScrollToBottom ) {
			onScrollToBottom.onScrollY(scrollY);
			if(scrollY != 0 && clampedY && !isLoading){
				onScrollToBottom.onScrollBottomListener(clampedY);
			}
		}

	}

	public void setOnScrollToBottomLintener(OnScrollToBottomListener listener){
		onScrollToBottom = listener;
	}

    public void setLoading() {
		this.isLoading = true;
	}
	public void setLoadFinish() {
		this.isLoading = false;
	}

	public interface OnScrollToBottomListener{
		public void onScrollBottomListener(boolean isBottom);
		public void onScrollY(int scrollY);
	}
}
