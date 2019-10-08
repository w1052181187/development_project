package com.chengning.fenghuo.fragment;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.fenghuo.App;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.ColumnistActivity;
import com.chengning.fenghuo.adapter.ColumnistArticleListAdapter;
import com.chengning.fenghuo.adapter.ColumnistArticleListAdapter.NameFollowListener;
import com.chengning.fenghuo.data.bean.BaseChannelItemBean;
import com.chengning.fenghuo.data.bean.ChanBean;
import com.chengning.fenghuo.util.SPHelper;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ColumnFragmentNew extends ChannelFragment {

	private PullToRefreshListView mPull;
	private LinearLayout mPullHeader;
	private View mSlideViewGroup;
	private View mFooter;
	private boolean isFooterSetted;

	@Override
	public BaseAdapter buildAdapter(Activity activity,
			List<BaseChannelItemBean> list) {
		return new ColumnistArticleListAdapter(activity,
				list, SettingManager.getInst().getNoPicModel(), true, new NameFollowListener() {
					
					@Override
					public void followChange() {
						
					}
				});
	}
	
	@Override
	public void configPullToRefreshListView_FootLoad(PullToRefreshListView v) {
		mPull = v;
		mPullHeader = new LinearLayout(getContext());
		mPull.getRefreshableView().addHeaderView(mPullHeader);
	}
	
	@Override
	public void onInitNewsDataUI(){
		// header slide
		setHeader();
		setFooter();
	}
	
	private void setFooter() {
		// TODO Auto-generated method stub
		if (!isFooterSetted && 0 == getAdapter().getCount()) {
			mFooter = LayoutInflater.from(getContext()).inflate(
					R.layout.footer_dynamic_detail, null);
			TextView noData = (TextView) mFooter.findViewById(R.id.article_comment_bottom_no_data);
			noData.setText("没有关注作家的文章");
			//截取footer的onItemClickListener监听事件
			mFooter.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				}
			});
			mPull.getRefreshableView().addFooterView(mFooter);
			isFooterSetted = true;
		} else if (0 != getAdapter().getCount() && null != mFooter) {
			mPull.getRefreshableView().removeFooterView(mFooter);
			isFooterSetted = false;
		}
	}

	private void setHeader() {

		if (mSlideViewGroup != null) {
			mPullHeader.removeView(mSlideViewGroup);
		}
		
		mSlideViewGroup = LayoutInflater.from(getContext()).inflate(
				R.layout.header_fragment_column, null);
		mSlideViewGroup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ColumnistActivity.launch(getContext());
			}
		});
		mPullHeader.addView(mSlideViewGroup);

	}
	
	@Override
	public void onResume() {
		if (SPHelper.getInst().getBoolean(SPHelper.KEY_LAST_LOGIN_STATE_COLUMN) != App.getInst().isLogin()) {
			forceRefresh();
		}
		super.onResume();
	}
	
	@Override
	public void onPause() {
		SPHelper.getInst().saveBoolean(SPHelper.KEY_LAST_LOGIN_STATE_COLUMN, App.getInst().isLogin());
		super.onPause();
	}
}
