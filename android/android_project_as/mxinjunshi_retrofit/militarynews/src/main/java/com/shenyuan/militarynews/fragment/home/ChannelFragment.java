package com.shenyuan.militarynews.fragment.home;

import android.os.Bundle;

import com.shenyuan.militarynews.beans.data.MChannelNavBean;
import com.shenyuan.militarynews.utils.JUrl;

public class ChannelFragment extends AbstractChannelItemListFragment {
	
	public static final String KEY_BEAN = "bean";

	private MChannelNavBean mBean;
	
	public ChannelFragment(){
	}

	public ChannelFragment(MChannelNavBean bean){
		this.mBean = bean;

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BEAN, bean);
        setArguments(bundle);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if(mBean == null)mBean = (MChannelNavBean) bundle.getSerializable(KEY_BEAN);
        super.onCreate(savedInstanceState); 
    }

	@Override
	public String buildTAG() {
		return ChannelFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "channel_" + mBean.getName();
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.SITE + JUrl.API + mBean.getUrl();
		url = JUrl.appendPage(url, tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}

}
