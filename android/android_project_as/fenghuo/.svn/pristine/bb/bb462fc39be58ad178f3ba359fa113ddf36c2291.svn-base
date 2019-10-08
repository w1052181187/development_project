package com.chengning.fenghuo.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class CommentReportDialog extends DialogFragment {

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
				// 注意
//				final AlertDialog confirmDialog = new AlertDialog.Builder(getActivity())
//				.setTitle("注意")
//				.setMessage("确定举报吗？")
//				.setPositiveButton("确定", new DialogInterface.OnClickListener(){
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//						mListener.confirm(position);
//					}
//
//				})
//				.setNegativeButton("取消", new DialogInterface.OnClickListener(){
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				})
//				.create();
//				confirmDialog.show();
			}
		}).setTitle("举报")
		.create();
		return dialog;
	}
	
	public interface CommentReportConfirmListener {
		void confirm(int pos);
	}
}
