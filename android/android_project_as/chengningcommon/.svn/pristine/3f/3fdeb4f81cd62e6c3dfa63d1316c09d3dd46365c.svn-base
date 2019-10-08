package com.chengning.common.widget.extend;
//viewpage touch 冲突
 
import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
/***
 * 可滑动的 viepager 放 image大图
 * @author swh
 *
 */
public class ViewPagerExtend extends ViewPager {
	private float xDistance, yDistance, lastX, lastY;
	private final int mSlop;
	
	public ViewPagerExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
		mSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
	}

	
	
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		super.onInterceptTouchEvent(ev);
//		return false;
//	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			lastX = ev.getX();
			lastY = ev.getY();
			//禁止 intercept
			getParent().requestDisallowInterceptTouchEvent(true);
//			Log.e(toString(), "MotionEvent.ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();
			xDistance = Math.abs(curX - lastX);
			yDistance = Math.abs(curY - lastY);

			if (mSlop > xDistance && mSlop > yDistance) {
				break;
			}
			//对于纵向scroll，开放intercept			
			if (xDistance < yDistance || (getCurrentItem() == 0 && curX-lastX > 0)
					|| (getCurrentItem() == getAdapter().getCount()-1 && curX-lastX < 0)) {
//				Log.e(toString(), "MotionEvent.ACTION_MOVE;"+xDistance+":"+yDistance+"-"+curX+":"+curY);
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			
			lastX = curX;
			lastY = curY;
		}

		return super.onInterceptTouchEvent(ev);
	}

}

