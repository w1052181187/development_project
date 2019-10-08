package com.chengning.fenghuo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 上下滚动
 *
 */
public class ScrollBarExtendViewPager extends ScrollView{
	
	private static final String TAG = ScrollBarExtendViewPager.class.getSimpleName();

	private float xDistance, yDistance, xLast, yLast;
	
	private boolean canScroll = true;
	private Handler handler;
	private View view; 
	
	private OnScrollListener onScrollListener;
	
	private ViewGroup childScrollView;
	private OnChildScrollListener childScrollListener;

	private boolean mHasChildTopMargin;
	private int mChildTopMargin;

	private boolean mHasPull = false;
	
	public ScrollBarExtendViewPager(Context context, AttributeSet attrs) {
		super(context, attrs); 
	}
	public ScrollBarExtendViewPager(Context context) {
		super(context);  
	}

	@Override
	protected boolean onRequestFocusInDescendants(int direction,
			Rect previouslyFocusedRect) {
		return true;
	}
	
	public void setCanScroll(boolean canScroll) {
		this.canScroll = canScroll;
	}

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener; 
	}
	
	public void setChildScrollView(ViewGroup childScrollView, OnChildScrollListener childScrollListener, boolean hasPull){
		this.mHasPull = hasPull;
		setChildScrollView(childScrollView, childScrollListener, false, 0);
	}
	public void setChildScrollView(ViewGroup childScrollView, OnChildScrollListener childScrollListener){
		setChildScrollView(childScrollView, childScrollListener, false, 0);
	}
	
	public void setChildScrollView(ViewGroup childScrollView, OnChildScrollListener childScrollListener, boolean hasChildTopMargin, int childTopMargin){
		this.childScrollView  = childScrollView;
		this.childScrollListener = childScrollListener;
		this.mHasChildTopMargin = hasChildTopMargin;
		this.mChildTopMargin = childTopMargin;
	}
	
	public void init(View child) {
		view = child;
		this.setOnTouchListener(onTouchListener);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// process incoming messages here
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					if (view.getMeasuredHeight() <= getScrollY() + getHeight()) {
						if (onScrollListener != null) {
							onScrollListener.onBottom();
						}

					} else if (getScrollY() == 0) {
						if (onScrollListener != null) {
							onScrollListener.onTop();
						}
					} else {
						if (onScrollListener != null) {
							onScrollListener.onScroll();
						}
					}
					break;
				default:
					break;
				}
			}
		};

	}
    
    public View getChildScrollView() {
        return childScrollView;  
    } 
	
	 /** 
    * 覆写onInterceptTouchEvent方法，点击操作发生在ChildScrollView的区域的时候, 
    * 返回false让ScrollView的onTouchEvent接收不到MotionEvent，而是把Event传到下一级的控件中 
    */  
   @Override  
   public boolean onInterceptTouchEvent(MotionEvent ev) {
	   boolean isActionDown = false;
	   switch (ev.getAction()) {  
       case MotionEvent.ACTION_DOWN:  
           xDistance = yDistance = 0f;  
           xLast = ev.getRawX();  
           yLast = ev.getRawY();  
           
           isActionDown = true;
           break;  
       case MotionEvent.ACTION_MOVE:  
           final float curX = ev.getRawX();  
           final float curY = ev.getRawY();  
             
           xDistance += (curX - xLast);  
           yDistance += (curY - yLast);  
           xLast = curX;  
           yLast = curY;  
           break; 
       case MotionEvent.ACTION_UP:  
           isActionDown = true;
           break;  
	   }
	   
	   boolean isChildTouch = isChildTouch(ev);
       boolean value = super.onInterceptTouchEvent(ev);
       return value && !isChildTouch;
   }  
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(this.canScroll)
			return super.onTouchEvent(ev);
		else
			return canScroll;
	}  

    private boolean isChildTouch(MotionEvent ev){
    	if(childScrollView == null || childScrollListener == null){
    		return false;
    	}
    	if(!checkArea(childScrollView, ev)){
    		return false;
    	}
    	boolean isChildTouch = false;
    	if(yDistance == 0){
    		// 静止
			isChildTouch = true;
    	}else if(yDistance > 0){
    		// 向下
    		if(childScrollListener.childY() <= childScrollListener.height()){
        		if(childScrollListener.subChildY() < 0){
        			isChildTouch = true;
        		}else {
//        			childScrollListener.setTitleBarBackground(false);
        		}
    		}else{
    			
        		if(childScrollListener.subChildY() != 0){
        			isChildTouch = true;
        		}else if(mHasChildTopMargin && childScrollListener.childY() == mChildTopMargin){
        			isChildTouch = true;
        		}else if (getScrollY() == 0 && mHasPull){//可以下拉刷新
        			isChildTouch = true;
        		}
    		}
    	}else{
    		// 向上
    		if(childScrollListener.childY() <= childScrollListener.height()){
//    			childScrollListener.setTitleBarBackground(true);
    			isChildTouch = true;
    		}
    	}
    	return isChildTouch;
    }
    
    /** 
     *  测试view是否在点击范围内 
     * @param locate 
     * @param v 
     * @return 
     */  
    private boolean checkArea(View v, MotionEvent event){  
        float x = event.getRawX();  
        float y = event.getRawY();  
        int[] locate = new int[2];  
        v.getLocationOnScreen(locate);  
        int l = locate[0];  
        int r = l + v.getWidth();  
        int t = locate[1];  
        int b = t + v.getHeight();  
        if (l < x && x < r && t < y && y < b) {  
            return true;  
        }  
        return false;  
    }  

	OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_UP: 
				break; 
			case MotionEvent.ACTION_MOVE:
				if (view != null && onScrollListener != null) {
					handler.sendMessageDelayed(handler.obtainMessage(1), 50);
				}
				break;
			default:
				break;
			}
			return false;
		}

	};
	
	public static int getChildY(View child, View parent){
		int[] location = new int[2];
		child.getLocationOnScreen(location);

		int[] location2 = new int[2];
		parent.getLocationOnScreen(location2);
		return location[1] - location2[1];
	}
	
	public static int getSubChildY(Object obj){
		int value = 0;
		if(obj instanceof PullToRefreshListView){
			PullToRefreshListView pullListView = (PullToRefreshListView_FootLoad) obj;
			ListView listview = pullListView.getRefreshableView();
			if(listview.getChildCount() == 0){
				return 0;
			}
			
			int firstPosition = listview.getFirstVisiblePosition();
			if(firstPosition > 0){
				return -1;
			}
			
			int[] location = new int[2];
			listview.getChildAt(0).getLocationOnScreen(location);

			int[] location2 = new int[2];
			pullListView.getLocationOnScreen(location2);
			return location[1] - location2[1];
		}else if(obj instanceof ListView){
			ListView listView = (ListView) obj;
			int firstPosition = listView.getFirstVisiblePosition();
			if(firstPosition > 0){
				return -1;
			}
			
			int[] location = new int[2];
			listView.getChildAt(0).getLocationOnScreen(location);

			int[] location2 = new int[2];
			listView.getLocationOnScreen(location2);
			return location[1] - location2[1];
		}
		return value;
	}
	
	public static void refreshChildHeight(ScrollBarExtendViewPager scroll, View child){
		int scrollHeight = scroll.getHeight();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, scrollHeight); 
		child.setLayoutParams(layoutParams);	
	}
	
	public static void refreshChildHeight(ScrollBarExtendViewPager scroll, View child, int height){
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height); 
		child.setLayoutParams(layoutParams);	
	}

	public interface OnScrollListener {
		void onBottom();

		void onTop();

		void onScroll();
	}

	public static interface OnChildScrollListener{
		int childY();
		void setTitleBarBackground(boolean b);
		int subChildY();
		int height();
	}
	 
}
