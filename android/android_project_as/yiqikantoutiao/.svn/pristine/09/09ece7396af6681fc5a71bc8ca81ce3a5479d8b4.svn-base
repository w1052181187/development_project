package com.chengning.yiqikantoutiao.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ModifyPwdSuccessDialog extends DialogFragment {
	private ModifyPwdSunccessListener mListener;

	public void setData(ModifyPwdSunccessListener litener) {
		this.mListener = litener;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder
		.setTitle("提示")
		.setMessage("密码修改成功，请重新登录")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				mListener.confirm();
				dismissAllowingStateLoss();
			}
		});
		return builder.create();
	}
	
	public interface ModifyPwdSunccessListener {
		void confirm();
	}
}
