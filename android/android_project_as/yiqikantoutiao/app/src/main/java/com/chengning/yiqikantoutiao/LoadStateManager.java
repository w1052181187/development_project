package com.chengning.yiqikantoutiao;

import com.chengning.common.base.BaseStateManager;
import com.chengning.yiqikantoutiao.LoadStateManager.LoadState;

public class LoadStateManager extends BaseStateManager<LoadState> {
	
	public static enum LoadState{
		Init,
		Success,
		Failure,
		NoData
		;
	}

}
