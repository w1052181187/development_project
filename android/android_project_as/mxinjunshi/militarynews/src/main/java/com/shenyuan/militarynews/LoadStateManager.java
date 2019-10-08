package com.shenyuan.militarynews;

import com.chengning.common.base.BaseStateManager;
import com.shenyuan.militarynews.LoadStateManager.LoadState;

public class LoadStateManager extends BaseStateManager<LoadState> {
	
	public static enum LoadState{
		Init,
		Success,
		Failure,
		NoData
		;
	}

}
