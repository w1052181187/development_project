package com.chengning.common.base.util;

public class BaseJUrl {
	
	public static final int PAGE_START = 1;
	public static final int DEFAULT_FONTSIZE = 18;
	
	public static final String KEY_PAGE = "page"; 
	public static final String KEY_MAXID = "maxid";
	
	public static String append(String str, String key, String value){
		StringBuilder builder = new StringBuilder(str);
		builder.append("&").append(key).append("=").append(value);
		return builder.toString();
	}
	
	public static String appendMaxid(String str, String cid){
		return append(str, KEY_MAXID, String.valueOf(cid));
	} 
	
	public static String appendPage(String str, int pageId){
		return append(str, KEY_PAGE, String.valueOf(pageId));
	}

}
