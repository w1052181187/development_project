package com.cmstop.jstt.fragment.home;

import java.util.HashMap;
import java.util.Map;

import com.cmstop.jstt.Const;
import com.cmstop.jstt.utils.JUrl;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.umeng.analytics.MobclickAgent;

public class RecommendFragment extends AbstractChannelItemListFragment {

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
		String url = JUrl.SITE + JUrl.URL_GET_CHANNEL_RECOMMEND;
		url =JUrl.appendPage(url, tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}
	
	@Override
	public void handleHomeEventAnalytics(int page) {
		if (page <= 10) {
			Map<String, String> mHashMap = new HashMap<String, String>();
			mHashMap.put("page", String.valueOf(page));
			MobclickAgent.onEvent(getContext(), "home_page", mHashMap);
		}
		
		super.handleHomeEventAnalytics(page);
	}
	
	@Override
	public void configPullToRefreshListView_FootLoad(PullToRefreshListView v){
		super.configPullToRefreshListView_FootLoad(v);
		v.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener() {

			@Override
			public void onPullEvent(PullToRefreshBase refreshView, State state,
					Mode direction) {
				if(state == State.REFRESHING && direction == Mode.PULL_FROM_START){
					MobclickAgent.onEvent(getContext(), Const.UMEVENT_HOME_MANLOAD);
				}
			}});

	}
	
}
