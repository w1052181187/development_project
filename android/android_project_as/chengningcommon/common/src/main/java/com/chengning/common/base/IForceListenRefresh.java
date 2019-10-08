package com.chengning.common.base;

public interface IForceListenRefresh extends IForceRefresh {
	public void forceRefresh();
	public void forceCheckRefresh();
	public RefreshState getRefreshState();
	public void setOnRefreshStateListener(OnRefreshStateListener listener);
	
	public static enum RefreshState{
		Refreshing,
		RefreshComplete,
		;
	}
	
	public static interface OnRefreshStateListener{
		public void onStart();
		public void onFinish();
	}
}
