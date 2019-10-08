package com.cmstop.jstt.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import com.cmstop.jstt.R;

public class CommonHintDialog extends DialogFragment {
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
		ContextThemeWrapper contextThemeWrapper = 
    		    new ContextThemeWrapper(getActivity(), R.style.dialog_list_style);
		AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
		
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
	
	public interface CommonDialogLitener {
		void confirm();
		void cancle();
	}
}
