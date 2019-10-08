package com.chengning.fenghuo.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.chengning.common.base.IForceRefresh;
import com.chengning.fenghuo.adapter.CircleChannelItemRecommendAdapter;
import com.chengning.fenghuo.base.BasePageCircleListFragment;
import com.chengning.fenghuo.data.bean.CircleChanBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.google.gson.Gson;

public class CircleChannelFragment extends BasePageCircleListFragment<DynamicItemBean> {
	
	public static final String KEY_BEAN = "bean";

	private CircleChanBean mBean;

	private CircleChannelItemRecommendAdapter mCircleAdapter;

	public static CircleChannelFragment newInstance(CircleChanBean bean){
		CircleChannelFragment newFragment = new CircleChannelFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(KEY_BEAN, bean);
		newFragment.setArguments(bundle);
		return newFragment;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if(mBean == null)mBean = (CircleChanBean) bundle.getSerializable(KEY_BEAN);
        super.onCreate(savedInstanceState); 
    }

	@Override
	public String buildTAG() {
		return CircleChannelFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "circlechannel_" + mBean.getId();
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.appendPage(JUrl.SITE + JUrl.API + mBean.getUrl(), tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}

	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<DynamicItemBean> list) {
		mCircleAdapter = new CircleChannelItemRecommendAdapter(activity, list, false , false);
		return mCircleAdapter;
	}

	public String appendMaxid(String url, int tarPage){
		String cidUrl = url;
		if(tarPage != JUrl.PAGE_START){
			List<DynamicItemBean> list = getDataList();
			if(!Common.isListEmpty(list)){
				String id = list.get(0).getTid();
				cidUrl = JUrl.appendMaxid(url, id);
			}
		}
		return cidUrl;
	}

	@Override
	public void onListItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	public CircleChannelItemRecommendAdapter getCircleAdapter() {
		return mCircleAdapter;
	}

	@Override
	public void onHttpSuccess(Gson gson, String data, String message, int page) {
		
	}
	
}
