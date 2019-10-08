package com.chengning.fenghuo.fragment;

import com.chengning.common.base.BaseFragment;

public abstract class AppUiFragment extends BaseFragment {
	
	public AppUiFragmentListener mAppUiFragmentListener;
	
	public void setAppUiFragmentListener(AppUiFragmentListener listener){
		mAppUiFragmentListener = listener;
	}
	
	public void notifyAppUiFragmentOnUpdateStart(){
		if(mAppUiFragmentListener != null){
			mAppUiFragmentListener.onUpdateStart();
		}
	}
	
	public void notifyAppUiFragmentOnUpdateFinish(){
		if(mAppUiFragmentListener != null){
			mAppUiFragmentListener.onUpdateFinish();
		}
	}
	
	public abstract boolean isEmpty();
	
	public static interface AppUiFragmentListener{
		public void onUpdateStart();
		public void onUpdateFinish();
	}

}
