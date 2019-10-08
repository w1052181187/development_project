package com.chengning.fenghuovideo.fragment;

import android.os.Bundle;

import com.chengning.fenghuovideo.base.AbstractChannelItemListFragment;
import com.chengning.fenghuovideo.data.bean.ChanBean;
import com.chengning.fenghuovideo.util.JUrl;

public class ChannelFragment extends AbstractChannelItemListFragment {
	
	public static final String KEY_BEAN = "bean";

	private ChanBean mBean;
	
	public static ChannelFragment newInstance(ChanBean bean){
		ChannelFragment newFragment = new ChannelFragment();
		Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BEAN, bean);
		newFragment.setArguments(bundle);
		return newFragment;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if(mBean == null)mBean = (ChanBean) bundle.getSerializable(KEY_BEAN);
        super.onCreate(savedInstanceState); 
    }

	@Override
	public String buildTAG() {
		return ChannelFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "channel_" + mBean.getId();
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.appendPage(JUrl.SITE + JUrl.API + mBean.getUrl(), tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}

	@Override
	public String buildChannelSlide() {
		return buildChannel() + "_slide";
	}

}
