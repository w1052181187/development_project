package com.shenyuan.militarynews.fragment.home;

import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;

import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.activity.HomeActivity;
import com.shenyuan.militarynews.adapter.AdChannelItemRecommendAdapter;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.JUrl;


public class ReDianFragment extends AbstractChannelItemListFragment {

	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<MChannelItemBean> list) {
		return new AdChannelItemRecommendAdapter(activity, list, new MoreOnClickListenner());
	}

	@Override
	public String buildTAG() {
		return ReDianFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "channel_" + Const.CHANNEL_REDIAN;
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.SITE + JUrl.URL_GET_CHANNEL_REDIAN;
		url = JUrl.appendPage(url, tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}
	
	class MoreOnClickListenner implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			boolean isNav = false;
			String channel = (String) v.getTag();
			if(TextUtils.isEmpty(channel )){
				isNav = false;
			}else {
				isNav = true;
			}
			
			if (isNav) {
				((HomeActivity) getContext()).onNav(channel);
			}
		}
		
	}
}
