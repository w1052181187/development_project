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
import com.chengning.fenghuovideo.R;

public class RemovePhoneBindDialog extends BaseDialogFragment {
	private RemovePhoneBindOkLitener mListener;

	public void setData(RemovePhoneBindOkLitener litener) {
		this.mListener = litener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String[] items = new String[] {getActivity().getResources().getString(R.string.activity_bind_change_phone),
				getActivity().getResources().getString(R.string.activity_bind_cancle_phone)};
    	DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				//更换绑定
				case 0:
					mListener.confirm();
					dismissAllowingStateLoss();
					break;
				//取消绑定
				case 1:
					mListener.cancle();
					dismissAllowingStateLoss();
					break;
				default :
					break;
				
				}
			}
		};
		
		return new AlertDialog.Builder(getActivity()).setItems(items,
				click).create();
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

	public interface RemovePhoneBindOkLitener {
		void confirm();
		void cancle();
	}
}
