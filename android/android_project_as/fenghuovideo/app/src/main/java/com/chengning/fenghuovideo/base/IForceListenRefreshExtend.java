package com.chengning.fenghuovideo.base;

import com.chengning.common.base.IForceListenRefresh;

;

public interface IForceListenRefreshExtend extends IForceListenRefresh {
	public void forceSetPageSelected(boolean isSelected);
	public void forceTop();
}
