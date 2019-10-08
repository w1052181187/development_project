package com.shenyuan.militarynews.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.chengning.common.base.BaseDialogFragment;

public class SettingOfflineProgressWarningDialog extends BaseDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final OnSOPWListener listener = getOnSOPWListener();
        return new AlertDialog.Builder(getContext())
                .setMessage("离线下载会占用一部分存储空间，点击确定继续下载。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener != null){
                            listener.onSOPWConfirm();   
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
    
    public OnSOPWListener getOnSOPWListener(){
        if(getContext() != null && getContext() instanceof OnSOPWListener){
            return (OnSOPWListener)getContext();
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
    
    public static interface OnSOPWListener{
        public void onSOPWConfirm();
    }
}
