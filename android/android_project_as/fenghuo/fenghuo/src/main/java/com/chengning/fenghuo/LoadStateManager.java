package com.chengning.fenghuo;

import com.chengning.common.base.BaseStateManager;
import com.chengning.fenghuo.LoadStateManager.LoadState;

public class LoadStateManager extends BaseStateManager<LoadState> {
	
	public static enum LoadState{
		Init,
		Success,
		Failure,
		NoData
		;
	}

}
