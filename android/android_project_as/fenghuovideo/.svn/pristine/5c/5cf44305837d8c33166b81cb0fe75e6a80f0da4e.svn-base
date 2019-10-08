package com.chengning.fenghuovideo.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.umeng.socialize.media.Base;

public class ModifyPwdSuccessDialog extends BaseDialogFragment {

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

	public interface ModifyPwdSunccessListener {
		void confirm();
	}
}
