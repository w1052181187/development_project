package com.chengning.yiqikantoutiao.fragment;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.chengning.yiqikantoutiao.adapter.ChannelItemDailyAdapter;
import com.chengning.yiqikantoutiao.data.bean.BaseChannelItemBean;

import java.util.List;

public class DailyFragment extends ChannelFragment {
	
	@Override
	public BaseAdapter buildAdapter(Activity activity,
                                    List<BaseChannelItemBean> list) {
//		return new ChannelItemAdapter(activity, list, model);
		return new ChannelItemDailyAdapter(activity,list);
	}
}
