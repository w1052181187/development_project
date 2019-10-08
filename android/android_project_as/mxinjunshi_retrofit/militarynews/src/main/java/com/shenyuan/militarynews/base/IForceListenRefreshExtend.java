package com.shenyuan.militarynews.base;

import com.chengning.common.base.IForceListenRefresh;;

public interface IForceListenRefreshExtend extends IForceListenRefresh {
	public void forceTop();
	public void forceSetPageSelected(boolean isSelected);
}
