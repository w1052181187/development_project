package com.chengning.common.app;

import com.chengning.common.base.IBaseActivity;

public class ActivityInfo {
	
	private IBaseActivity mBaseActivity;
	private ActivityState mActivityState;
	
	public ActivityInfo(IBaseActivity baseActivity){
		this.mBaseActivity = baseActivity;
		this.mActivityState = ActivityState.Stopped;
	}
	
	public IBaseActivity getBaseActivity() {
		return mBaseActivity;
	}

	public void setActivityState(ActivityState activityState){
		this.mActivityState = activityState;
	}
	
	public ActivityState getActivityState(){
		return mActivityState;
	}
	
	/**
	 * Activity状态
	 */
	public static enum ActivityState{
		
		/**
		 * 一个新的Activity启动入栈后，它在屏幕最前端，处于栈的最顶端，此时它处于可见并可和用户交互的激活状态。
		 */
		Running,
		/**
		 * 当Activity被另一个透明或者Dialog样式的Activity覆盖时的状态。
		 * 此时它依然与窗口管理器保持连接，系统继续维护其内部状态，它仍然可见，但它已经失去了焦点，故不可与用户交互。
		 */
		Paused,
		/**
		 * 当Activity不可见时，Activity处于Stopped状态。
		 * 当Activity处于此状态时，一定要保存当前数据和当前的UI状态，否则一旦Activity退出或关闭时，当前的数据和UI状态就丢失了。
		 */
		Stopped,
		/**
		 * Activity被杀掉以后或者被启动以前，处于Killed状态。
		 * 这是Activity已从Activity堆栈中移除，需要重新启动才可以显示和使用。
		 */
		Killed,
		SaveInstanceStated,
		;
		
	}
	
}
