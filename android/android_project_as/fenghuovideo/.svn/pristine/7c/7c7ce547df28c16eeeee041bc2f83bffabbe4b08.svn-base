package com.chengning.fenghuovideo.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseFragmentActivity;

public class CommonHintDialog extends BaseDialogFragment {
	private CommonDialogLitener mListener;

	public void setData(String title, String message, boolean onlyPositiveBtn, boolean onlyNegativeBtn, CommonDialogLitener litener) {
		Bundle args = new Bundle();
		args.putSerializable("title", title);
		args.putSerializable("message", message);
		args.putSerializable("onlyPositiveBtn", onlyPositiveBtn);
		args.putSerializable("onlyNegativeBtn", onlyNegativeBtn);
		setArguments(args);
		this.mListener = litener;
	}
	public void setData(String title, String message, CommonDialogLitener litener) {
		setData(title, message, true, true, litener);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		final boolean onlyPositiveBtn= bundle.getBoolean("onlyPositiveBtn");
		final boolean onlyNegativeBtn= bundle.getBoolean("onlyNegativeBtn");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder
		.setTitle(bundle.getString("title"))
		.setMessage(bundle.getString("message"));
		if (onlyPositiveBtn) {
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (null != mListener) {
						mListener.confirm();
					}
					
					dismissAllowingStateLoss();
				}
			});
		}
		if (onlyNegativeBtn) {
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (null != mListener) {
						mListener.cancle();
					}
					
					dismissAllowingStateLoss();
				}
			});
		}
		
		return builder.create();
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

	public interface CommonDialogLitener {
		void confirm();
		void cancle();
	}
}
