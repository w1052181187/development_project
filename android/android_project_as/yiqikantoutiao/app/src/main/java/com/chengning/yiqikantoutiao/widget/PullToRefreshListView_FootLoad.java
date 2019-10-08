/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.chengning.yiqikantoutiao.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView_ForceRefresh;

public class PullToRefreshListView_FootLoad
		extends
			PullToRefreshListView_ForceRefresh implements NestedScrollingChild {

	private static final String TAG = PullToRefreshListView_FootLoad.class
			.getSimpleName();

	private OnLastItemVisibleListener mOnLastItemVisibleListener;
	private NestedScrollingChildHelper mNestedScrollingChildHelper;

	private ListView mRefreshableView;

	public PullToRefreshListView_FootLoad(Context context) {
		super(context);
		initConfig_FootLoad();
	}

	public PullToRefreshListView_FootLoad(Context context, AttributeSet attrs) {
		super(context, attrs);
		initConfig_FootLoad();
	}

	public PullToRefreshListView_FootLoad(Context context, Mode mode) {
		super(context, mode);
		initConfig_FootLoad();
	}

	public PullToRefreshListView_FootLoad(Context context, Mode mode,
                                          AnimationStyle style) {
		super(context, mode, style);
		initConfig_FootLoad();
	}

	private void initConfig_FootLoad() {
		mRefreshableView = getRefreshableView();
		mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
		setNestedScrollingEnabled(true);
	}

	@Override
	public void setFootLoadFull() {
		setFootLoadVisibility(View.GONE);
		super.setFootLoadFull();
	}

	@Override
	public void setFootLoading() {
		setFootLoadVisibility(View.VISIBLE);
		super.setFootLoading();
	}

	@Override
	public void setFootLoadVisibility(int visibility) {
		super.setFootLoadVisibility(visibility);
	}

	public void setOnLastPageVisibleListener(OnLastItemVisibleListener listener) {
		this.mOnLastItemVisibleListener = listener;
		super.setOnLastItemVisibleListener(mOnLastPageVivibleListener);
	}

	private OnLastItemVisibleListener mOnLastPageVivibleListener = new OnLastItemVisibleListener() {

		@Override
		public void onLastItemVisible() {
			mOnLastItemVisibleListener.onLastItemVisible();
			// if(mPageIsMoreThanOne && mPageLastDataCount >=
			// mPageFullDataCount){
			// mOnLastItemVisibleListener.onLastItemVisible();
			// }else{
			// setFootLoadFull();
			// }
		}
	};

	 @Override
	    public void setNestedScrollingEnabled(boolean enabled) {  
	        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);  
	    }  
	  
	    @Override
	    public boolean isNestedScrollingEnabled() {  
	        return mNestedScrollingChildHelper.isNestedScrollingEnabled();  
	    }  
	  
	    @Override
	    public boolean startNestedScroll(int axes) {  
	        return mNestedScrollingChildHelper.startNestedScroll(axes);  
	    }  
	  
	    @Override
	    public void stopNestedScroll() {  
	        mNestedScrollingChildHelper.stopNestedScroll();  
	    }  
	  
	    @Override
	    public boolean hasNestedScrollingParent() {  
	        return mNestedScrollingChildHelper.hasNestedScrollingParent();  
	    }  
	  
	    @Override
	    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {  
	        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);  
	    }  
	  
	    @Override
	    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {  
	        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);  
	    }  
	  
	    @Override
	    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {  
	        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);  
	    }  
	  
	    @Override
	    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {  
	        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);  
	    }  
}
