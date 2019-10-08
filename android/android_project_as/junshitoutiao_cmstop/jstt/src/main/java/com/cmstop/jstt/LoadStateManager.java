package com.cmstop.jstt;

import com.chengning.common.base.BaseStateManager;
import com.cmstop.jstt.LoadStateManager.LoadState;

public class LoadStateManager extends BaseStateManager<LoadState> {
	
	public static enum LoadState{
		Init,
		Success,
		Failure,
		NoData
		;
	}

}
