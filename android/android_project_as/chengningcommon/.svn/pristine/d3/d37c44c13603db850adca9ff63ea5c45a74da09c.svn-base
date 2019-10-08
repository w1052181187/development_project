package com.chengning.common.base;

public class BaseActivityConfig {
	
	private static BaseActivityConfig baseActivityConfig = null;
	
	private boolean isBaiduStatUsed;
	
	private BaseActivityConfig(){
		isBaiduStatUsed = true;
	}
	
	public static BaseActivityConfig getInst(){
		if(baseActivityConfig == null){
			baseActivityConfig = new BaseActivityConfig();
		}
		return baseActivityConfig;
	}

	public boolean isBaiduStatUsed() {
		return isBaiduStatUsed;
	}

	public void setBaiduStatUsed(boolean isBaiduStatUsed) {
		this.isBaiduStatUsed = isBaiduStatUsed;
	}

}
