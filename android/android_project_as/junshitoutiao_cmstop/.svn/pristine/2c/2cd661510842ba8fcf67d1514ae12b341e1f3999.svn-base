package com.cmstop.jstt.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;


public class ReturnTopOnScrollListener implements OnScrollListener {

	private SparseArray recordSp = new SparseArray(0);  
    private int mCurrentfirstVisibleItem = 0;  
    private IScrollCallBack mScrollCallBack;
    private PullToRefreshBase mListView;
	private int mScrollY;
	private int mOldFirstVisibleItem = 0;
	private int height = 0;
    
	public ReturnTopOnScrollListener(IScrollCallBack mScrollCallBack,
			PullToRefreshBase mListView) {
		super();
		this.mScrollCallBack = mScrollCallBack;
		this.mListView = mListView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == SCROLL_STATE_IDLE){
            mScrollY = getScrollY();//滚动距离  
            dealScrollY();
		}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(mScrollCallBack != null){
			mCurrentfirstVisibleItem = firstVisibleItem;  
	        View firstView = view.getChildAt(0);  
	        if (null != firstView) {  
	            ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);  
	            if (null == itemRecord) {  
	                itemRecord = new ItemRecod();  
	            }  
	            itemRecord.height = firstView.getHeight();  
	            itemRecord.top = firstView.getTop();  
	            recordSp.append(firstVisibleItem, itemRecord);  
	        }  
		}
        
	}
	
	public void dealScrollY(){
		if(mScrollCallBack != null && mListView != null){
	        if(mScrollY > mListView.getHeight() * 3){
	        	mScrollCallBack.show();
	        }else{
	        	mScrollCallBack.hidden();
	        }
		}
	}
	
	private int getScrollY() {  
		if(mOldFirstVisibleItem == mCurrentfirstVisibleItem){
	        ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);  
	        if (null == itemRecod) {  
	            itemRecod = new ItemRecod();  
	        }  
	        return height - itemRecod.top;  
		}else{
			mOldFirstVisibleItem = mCurrentfirstVisibleItem;
	        height = 0;  
	        for (int i = 0; i < mCurrentfirstVisibleItem; i++) {  
	            ItemRecod itemRecod = (ItemRecod) recordSp.get(i);  
	            if(itemRecod != null)
	            	height += itemRecod.height;  
	        }  
	        ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);  
	        if (null == itemRecod) {  
	            itemRecod = new ItemRecod();  
	        }  
	        return height - itemRecod.top;  
		}
    }  
  
  
    class ItemRecod {  
        int height = 0;  
        int top = 0;  
    }  

}
