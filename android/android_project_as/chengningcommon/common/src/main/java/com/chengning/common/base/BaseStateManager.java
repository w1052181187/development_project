package com.chengning.common.base;



public abstract class BaseStateManager<T> {
	
	private T mState;
	private T mPreState;
	private OnStateChangeListener mListener;
	
	public BaseStateManager(){
	}
	
	public T getState(){
		return mState;
	}
	
	public T getPreState(){
		return mPreState;
	}

	public void setState(T state){
		setState(state, null);
	}
	
	public void setState(T state, Object obj){
		setState(state, obj, false);
	}
	
	public void setState(T state, Object obj, boolean isForceNotify){
		boolean isChange = !equals(this.mState, state);
		if(isForceNotify || isChange){
			this.mPreState = mState;
			this.mState = state;
			notifyStateChange(state, obj);
		}
	}
	
	public boolean equals(T oldState, T newState){
		boolean isEqual = false;
		if(newState instanceof String){
			if(newState.equals(oldState)){
				isEqual = true;
			}
		}else if(oldState == newState){
			isEqual = true;
		}
		return isEqual;
	}
	
	public void setOnStateChangeListener(OnStateChangeListener listener){
		this.mListener = listener;
	}
	
	public void notifyStateChange(T state, Object obj){
		if(mListener != null){
			mListener.OnStateChange(state, obj);
		}
	}
	
	public static interface OnStateChangeListener<T>{
		public void OnStateChange(T state, Object obj);
	}

}
