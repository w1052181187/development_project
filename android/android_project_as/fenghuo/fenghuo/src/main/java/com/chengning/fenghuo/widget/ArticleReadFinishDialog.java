package com.chengning.fenghuo.widget;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.fenghuo.R;

public class ArticleReadFinishDialog extends BaseDialogFragment{
	
	private View mView;
	private TextView mGoldTv;

	public void setData(String data) {
		Bundle bundle = new Bundle();
		bundle.putString("gold",data);
		setArguments(bundle);
	}

	@Override
	public void initView() {
		mGoldTv = (TextView) mView.findViewById(R.id.dialog_article_read_finish_gold);
	}
	
	@Override
	public void initListener() {

	}

	@Override
	public void initData() {
		String gold = getArguments().getString("gold");
		mGoldTv.setText("阅读奖励\n" + "+" + gold);
	}
	
	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_article_read_finish, null);
		return mView;
	}

	public void cancle(Handler handler) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				dismissAllowingStateLoss();
			}
		}, 1500);
	}
}
