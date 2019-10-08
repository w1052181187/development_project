package com.chengning.fenghuovideo.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;

public class CommentReportDialog extends BaseDialogFragment {

	private String[] strs;
	private CommentReportConfirmListener mListener;

	public void setData(String[] strs, CommentReportConfirmListener listener) {
		this.strs = strs;
		this.mListener = listener;
	}
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
    	.setItems(strs, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, final int position) {
				if (strs.length - 1 == position){
					return;
				}
				mListener.confirm(position);
			}
		}).setTitle("举报")
		.create();
		return dialog;
	}

	@Override
	public View configContentView() {
		return null;
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}

	public interface CommentReportConfirmListener {
		void confirm(int pos);
	}
}
