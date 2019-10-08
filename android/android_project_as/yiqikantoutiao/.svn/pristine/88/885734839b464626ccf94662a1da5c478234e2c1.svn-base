package com.chengning.yiqikantoutiao.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.chengning.yiqikantoutiao.widget.RemovePhoneBindDialog.RemovePhoneBindOkLitener;

public class DeleteArticleCommentDialog extends DialogFragment {
	private RemovePhoneBindOkLitener mListener;

	public void setData(RemovePhoneBindOkLitener litener) {
		this.mListener = litener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("确认删除此内容？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.confirm();
				dismissAllowingStateLoss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.cancle();
				dismissAllowingStateLoss();
			}
		});
		
		return builder.create();
	}
}
