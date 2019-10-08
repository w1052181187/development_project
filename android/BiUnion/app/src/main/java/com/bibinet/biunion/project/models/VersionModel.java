package com.bibinet.biunion.project.models;

import java.util.List;

public class VersionModel {
	private int versionCode;   //版本号 例如：1.0.0
	private String versionName; //版本名 1.0.0
	private String url;  //新版本app包位置 要网络下载这个包
	private String description; //版本信息列表 新版本多了哪些功能等等

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
