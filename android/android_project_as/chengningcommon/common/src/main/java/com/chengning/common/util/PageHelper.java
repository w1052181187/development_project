package com.chengning.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.chengning.common.base.util.BaseCommon;
import com.chengning.common.base.util.BaseJUrl;

import android.text.TextUtils;

public class PageHelper {
	
	public static final int PAGE_START = 1;
	
	private boolean mIsRequestRunning;
	private String mRunningUrl;
	
	private LinkedHashMap<Integer, List> mMap;
	
	private int mCurrentPage;
	private int mMaxPage;
	private String mMaxid;

	public PageHelper(){
		clear();
	}
	
	public void clear(){
		mMap = new LinkedHashMap<Integer, List>();
		mCurrentPage = 0;
		mMaxPage = -1;
		mMaxid = null;
	}
	
	public void clearIfDataEmpty(){
		boolean isEmpty = true;
		for(Integer key : mMap.keySet()){
			List list = mMap.get(key);
			if(!BaseCommon.isListEmpty(list)){
				isEmpty = false;
				break;
			}
		}
		if(isEmpty){
			clear();
		}
	}
	
	public LinkedHashMap getMap(){
		return mMap;
	}
	
	public int getCurrentPage(){
		return mCurrentPage;
	}
	
	public void setCurrentPage(int currentPage){
		this.mCurrentPage = currentPage;
	}
	
	public int getMaxPage() {
		return mMaxPage;
	}

	public void setMaxPage(int maxPage) {
		this.mMaxPage = maxPage;
	}
	
	public String getMaxid() {
		return mMaxid;
	}

	public void setMaxid(String maxid) {
		this.mMaxid = maxid;
	}
	
	public boolean hasNextPage(){
		return mMaxPage == -1 || mCurrentPage < mMaxPage;
	}

	public ArrayList getDataList(){
		ArrayList dataList = new ArrayList();
		for(Integer key : mMap.keySet()){
			List list = mMap.get(key);
			if(!BaseCommon.isListEmpty(list)){
				dataList.addAll(list);
			}
		}
		return dataList;
	}
	
	public void setPageData(int page, List list){
		if(!mMap.containsKey(page) && !BaseCommon.isListEmpty(list)){
			mMap.put(page, list);
		}
	}
	
	public String appendFirstPage(String str){
		return BaseJUrl.append(str, BaseJUrl.KEY_PAGE, String.valueOf(PAGE_START));
	}
	
	public String appendNextPageAndMaxid(String str){
		String s = BaseJUrl.append(str, BaseJUrl.KEY_PAGE, String.valueOf(mCurrentPage + 1));
		if(!TextUtils.isEmpty(mMaxid)){
			s = BaseJUrl.append(s, BaseJUrl.KEY_MAXID, mMaxid);
		}
		return s;
	}
	
	public boolean isCurrentPageFirst(){
		return mCurrentPage < PAGE_START;
	}
	
	public boolean equalsRunningUrl(String url){
		return url.equals(mRunningUrl);
	}
	
	public boolean isRequestRunning(){
		return mIsRequestRunning;
	}
	
	public void setRequestStart(String url){
		mRunningUrl = url;
		mIsRequestRunning = true;
	}
	
	public void setRequestEnd(){
		mIsRequestRunning = false;
	}

}
