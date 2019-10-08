package com.chengning.yiqikantoutiao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengning.yiqikantoutiao.base.AbstractChannelItemListFragment;
import com.chengning.yiqikantoutiao.util.JUrl;

public class RecommendFragment extends AbstractChannelItemListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void initDatas(){
		super.initDatas();
		setHomeRecom(true);
	}

	@Override
	public String buildTAG() {
		return RecommendFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "recommend";
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = null;
//		if(urlMore){
//			url = JUrl.appendPage(JUrl.URL_GET_CHANNEL_ITEM_LIST_MORE, tarPage);
//			url = appendMaxid(url, tarPage , false);
//		}else{
			url = JUrl.appendPage(JUrl.SITE + JUrl.URL_GET_CHANNEL_RECOMMEND, tarPage);
			url = appendMaxid(url, tarPage);
//		}		
		return url.toString();
	}

	@Override
	public String buildChannelSlide() {
		return "recommend_slide";
	}
	
}
