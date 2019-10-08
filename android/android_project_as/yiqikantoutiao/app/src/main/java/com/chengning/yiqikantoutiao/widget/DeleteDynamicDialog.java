package com.chengning.yiqikantoutiao.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.chengning.yiqikantoutiao.widget.RemovePhoneBindDialog.RemovePhoneBindOkLitener;

public class DeleteDynamicDialog extends DialogFragment {
	private RemovePhoneBindOkLitener mListener;

	public void setData(RemovePhoneBindOkLitener litener) {
		this.mListener = litener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String[] items = new String[] {"删除"};
    	DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				//删除
				case 0:
					mListener.confirm();
					dismissAllowingStateLoss();
					break;
				//取消
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
}
