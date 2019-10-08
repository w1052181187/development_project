package com.shenyuan.militarynews.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.chengning.common.base.BaseDialogFragment;
import com.shenyuan.militarynews.R;

public class CommentFirstHintDialog extends BaseDialogFragment {
	
	private static final String TAG = CommentFirstHintDialog.class.getSimpleName();
	
	private View mView;
	private View mCancel;
	

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment_first_hint, null);
		return mView;
	}

	@Override
	public void initView() {
		mCancel = mView.findViewById(R.id.dialog_comment_hint_click_btn);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {
		mView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
    }
	
}
