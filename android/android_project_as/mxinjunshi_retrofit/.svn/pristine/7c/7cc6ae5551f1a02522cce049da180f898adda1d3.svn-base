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
package com.shenyuan.militarynews.views; 
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView_ForceRefresh;

public class PullToRefreshListView_FootLoad extends PullToRefreshListView_ForceRefresh {
	
	private static final String TAG = PullToRefreshListView_FootLoad.class.getSimpleName();
	
	private OnLastItemVisibleListener mOnLastItemVisibleListener;

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

	public PullToRefreshListView_FootLoad(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		initConfig_FootLoad();
	}
	
	private void initConfig_FootLoad(){
	}

	@Override
	public void setFootLoadFull(){
		setFootLoadVisibility(View.GONE);
		super.setFootLoadFull();
	}

	@Override
	public void setFootLoading(){
		setFootLoadVisibility(View.VISIBLE);
		super.setFootLoading();
	}
	
	@Override
	public void setFootLoadVisibility(int visibility){
		super.setFootLoadVisibility(visibility);
	}
	
	public void setOnLastPageVisibleListener(OnLastItemVisibleListener listener){
		this.mOnLastItemVisibleListener= listener; 
		super.setOnLastItemVisibleListener(mOnLastPageVivibleListener);
	}
	
	private OnLastItemVisibleListener mOnLastPageVivibleListener = new OnLastItemVisibleListener() {

		@Override
		public void onLastItemVisible() {
			mOnLastItemVisibleListener.onLastItemVisible();
//			if(mPageIsMoreThanOne && mPageLastDataCount >= mPageFullDataCount){
//				mOnLastItemVisibleListener.onLastItemVisible();
//			}else{
//				setFootLoadFull();
//			}
		}
	};
 
}
