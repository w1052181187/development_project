package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bibinet.biunion.R;

/**
 * Created by bibinet on 2017-4-22.
 */

public class DialogUtils {
    private DialogListioner dialogListioner;
    private View view;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private View bottomView;
    private Dialog bottomDialog;

    public View getView() {
        return view;
    }

    public View getBottomView() {
        return bottomView;
    }

    public void diloagShow(Context context, int ResuoreceId) {
        AlertDialog.Builder dialogBuider = new AlertDialog.Builder(context);
        dialog = dialogBuider.create();
        view = LayoutInflater.from(context).inflate(ResuoreceId, null);
        dialog.setView(view);
        dialog.show();
    }

    public void dialogDismiss() {
        dialog.dismiss();
    }

    public void setDialoglistioner(DialogListioner listioner) {
        this.dialogListioner = listioner;
    }

    public interface DialogListioner {
        void onDialogClickListioner(View view);
    }

    public void showBottomDiaglog(Context context, int layoutId) {
        bottomDialog = new Dialog(context);
        bottomView = LayoutInflater.from(context).inflate(layoutId, null, false);
        bottomDialog.setContentView(bottomView);
        Window window = bottomDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager m = ((Activity) context).getWindowManager();
        Display display = m.getDefaultDisplay();
        int widith = (int) (display.getWidth());
        int hight = (int) (WindowManager.LayoutParams.WRAP_CONTENT);
        params.width = widith;
        params.height = hight;
        window.setAttributes(params);
        bottomDialog.show();
        bottomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListioner.onDialogClickListioner(v);
            }
        });
    }

    public void disBottomDiaglog() {
        bottomDialog.dismiss();
    }

    public void showProgressDialog(Context context, String dscrp) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(dscrp);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setIcon(R.mipmap.app_icon);//
        progressDialog.show();
    }

    public void disProgressDialog() {
        progressDialog.dismiss();
    }
}
