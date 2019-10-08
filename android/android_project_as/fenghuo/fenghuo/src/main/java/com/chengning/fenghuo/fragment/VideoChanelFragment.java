package com.chengning.fenghuo.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.base.AbstractChannelItemListFragment;
import com.chengning.fenghuo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.SPHelper;

/**
 * @description 视频
 * @author wyg
 *
 */
public class VideoChanelFragment extends AbstractChannelItemListFragment {
	
	public static final String KEY_BEAN = "bean";
	private BaseAdapter mAdapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
//		Bundle bundle = getArguments();
//		if(mBean == null)mBean = (ChanBean) bundle.getSerializable(KEY_BEAN);
        super.onCreate(savedInstanceState); 
    }
	
	@Override
	public String buildTAG() {
		return VideoChanelFragment.class.getSimpleName();
	}

	@Override
	public String buildChannel() {
		return "tab_video";
	}

	@Override
	public String buildUrl(int tarPage) {
		String url = JUrl.appendPage(JUrl.SITE + JUrl.URL_GET_VIDEO_CHANNEL, tarPage);
		url = appendMaxid(url, tarPage);
		return url.toString();
	}

	@Override
	public String buildChannelSlide() {
		return buildChannel() + "_slide";
	}
	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<BaseChannelItemBean> list) {
		mAdapter = super.buildAdapter(activity, list);
		return mAdapter;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (SettingManager.getInst().getNoPicModel() != SPHelper.getInst().getInt(SPHelper.KEY_HOME_VIDEO_NO_PIC_MODEL)) {
			if (null != mAdapter) {
				mAdapter.notifyDataSetChanged();
			}
			SPHelper.getInst().saveInt(SPHelper.KEY_HOME_VIDEO_NO_PIC_MODEL,
					SettingManager.getInst().getNoPicModel());
		}
	}
}