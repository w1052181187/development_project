package com.shenyuan.militarynews.views;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;

import com.chengning.common.base.BaseDialogFragment;
import com.shenyuan.militarynews.Const.PointActionType;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.activity.HomeActivity;
import com.shenyuan.militarynews.adapter.PointHintNoCompleteTaskAdapter;
import com.shenyuan.militarynews.beans.data.DirectorateTaskBean;

public class UpgradeDialog extends BaseDialogFragment {

	private View mBg;
	private View mTopBg;
	private TextView contentTv;
	private View mCloseBtn;
	private View mView;
	private View mEmpty;
	protected boolean hasMeasured;

	private ArrayList<DirectorateTaskBean> list;
	private TextView mTitleTv;
	private LoadFullListView mListView;
	private TextView YesTv;
	private TextView mMoneyTv;
	private PointActionType action;


	public void setData(PointActionType action,
			ArrayList<DirectorateTaskBean> list,
			final DialogOnClickListener listener) {

		this.action = action;
		this.list = list;
	}

	@Override
	public void initData() {
		if (null != list) {
			initList(list);
		}
	}

	private void initList(ArrayList<DirectorateTaskBean> list) {
		if (list.size() > 0) {
			ArrayList<DirectorateTaskBean> temp = new ArrayList<DirectorateTaskBean>();
			for (DirectorateTaskBean item : list) {
				if (item.getComplete().equals("未完成")) {
					temp.add(item);
				}
				if (item.getAction().equals(action.getAction())) {
					mTitleTv.setText(!TextUtils.isEmpty(item.getRulename()) ? item.getRulename(): "");
					mMoneyTv.setText(!TextUtils.isEmpty(item.getExtcredits2()) ? "+" + item.getExtcredits2(): "");
				}
			}
			if(temp.size() > 0){
				mListView.setAdapter(new PointHintNoCompleteTaskAdapter(getContext(),
						temp));
			}else {
				contentTv.setText("干的不错！ 今天的任务已经全部完成，明天继续努力！");
			}
		} 
	}
	
	@Override
	public void initView() {
		mEmpty = mView.findViewById(R.id.dialog_upgrade_empty);
		mBg = mView.findViewById(R.id.dialog_upgrade_bg);
		mTopBg = mView.findViewById(R.id.dialog_upgrade_bg_rl);
		mCloseBtn = mView.findViewById(R.id.dialog_upgrade_close_btn);
		contentTv = (TextView) mView.findViewById(R.id.dialog_upgrade_content);
		mMoneyTv = (TextView) mView.findViewById(R.id.dialog_upgrade_money);
		mTitleTv = (TextView) mView.findViewById(R.id.dialog_title);
		YesTv = (TextView) mView.findViewById(R.id.dialog_upgrade_ok);
		// mTaskTv = (TextView) mView.findViewById(R.id.dialog_upgrade_content);
		mListView = (LoadFullListView) mView
				.findViewById(R.id.dialog_point_hint_list);
		
	}

	public static interface DialogOnClickListener {
		void onConfirmClick();
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_upgrade, null);
		return mView;
	}


	@Override
	public void initListener() {
		mTopBg.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {

					@Override
					public boolean onPreDraw() {
						if (hasMeasured == false) {

							LayoutParams layoutParams = mBg.getLayoutParams();
							layoutParams.width = mTopBg.getMeasuredWidth();
							mBg.setLayoutParams(layoutParams);
							hasMeasured = true;

						}
						return true;
					}
				});

		mEmpty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 dismissAllowingStateLoss();
			}
		});

		mCloseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		YesTv.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
//				if (!(mContext instanceof HomeActivity)) {
//					mContext.finish();
//					startActivity(new Intent(mContext,HomeActivity.class));
//				}
				
			}
			
		});

	}

}
