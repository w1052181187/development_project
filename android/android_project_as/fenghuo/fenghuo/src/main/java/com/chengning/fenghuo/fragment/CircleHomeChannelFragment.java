package com.chengning.fenghuo.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.chengning.fenghuo.adapter.CircleChannelItemRecommendAdapter;
import com.chengning.fenghuo.base.BasePageCircleListFragment;
import com.chengning.fenghuo.data.bean.CircleChanBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CircleHomeChannelFragment extends BasePageCircleListFragment<DynamicItemBean> {
	
	private OnRecommendHttpListener mListener;
	private CircleChannelItemRecommendAdapter mCircleAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
    }

	@Override
	public String buildTAG() {
		return CircleHomeChannelFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "circlechannel_home";
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.appendPage(JUrl.SITE + JUrl.URL_CIRCLE_CHANNEL, tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}

	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<DynamicItemBean> list) {
		mCircleAdapter = new CircleChannelItemRecommendAdapter(activity, list, false , false);
		return mCircleAdapter;
	}

	
	
	public CircleChannelItemRecommendAdapter getCircleAdapter() {
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
	public void onHttpSuccess(Gson gson, String data, String message, int page) {

		if (page == JUrl.PAGE_START) {
			try {
				JSONObject mObj = new JSONObject(data);
				ArrayList<CircleChanBean> chanList = new ArrayList<CircleChanBean>();
				chanList = gson.fromJson(mObj.optString("chan_list"), new TypeToken<List<CircleChanBean>>(){}.getType());
				if (mListener != null) {
					mListener.onHttpSucess(chanList);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
			
	}
	
	public void setOnRecommendHttpListener(OnRecommendHttpListener listener) {
		this.mListener = listener;
	}

	public static interface OnRecommendHttpListener {
		public void onHttpSucess(ArrayList<CircleChanBean> chanList);
	}

	@Override
	public void onListItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

}
