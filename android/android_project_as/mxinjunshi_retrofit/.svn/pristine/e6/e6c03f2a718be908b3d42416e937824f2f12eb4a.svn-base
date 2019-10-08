package com.shenyuan.militarynews.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;

public class SettingOfflineProgressCancleDialog extends BaseDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final OnSOPCListener listener = getOnSOPCListener();
        return new AlertDialog.Builder(getContext())
                .setMessage("不想继续下载了吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener != null){
                            listener.onSOPCConfirm();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }
    
    public OnSOPCListener getOnSOPCListener(){
        if(getContext() != null && getContext() instanceof OnSOPCListener){
            return (OnSOPCListener)getContext();
        }
        return null;
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
    
    public static interface OnSOPCListener{
        public void onSOPCConfirm();
    }
}
