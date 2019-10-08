package com.chengning.fenghuo.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.chengning.fenghuo.adapter.ChannelItemDailyAdapter;
import com.chengning.fenghuo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuo.data.bean.ChanBean;

public class DailyFragment extends ChannelFragment {
	
	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<BaseChannelItemBean> list) {
//		return new ChannelItemAdapter(activity, list, model);
		return new ChannelItemDailyAdapter(activity,list);
	}
}
