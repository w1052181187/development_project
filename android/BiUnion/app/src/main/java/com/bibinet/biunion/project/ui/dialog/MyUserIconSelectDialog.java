package com.bibinet.biunion.project.ui.dialog;

import android.content.Context;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.base.BaseDialog;

import butterknife.OnClick;

/**
 * Created by Wh on 2017-7-6.
 */

public class MyUserIconSelectDialog extends BaseDialog {

    private OnMyUserIconSelectDialogListener listener;

    public MyUserIconSelectDialog(Context context, OnMyUserIconSelectDialogListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onBaseCreate() {

    }

    @OnClick(R.id.camera)
    void camera(){
        listener.onCamera();
        dismiss();
    }

    @OnClick(R.id.photo)
    void photo(){
        listener.onPhoto();
        dismiss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_my_user_icon_select;
    }

    public interface OnMyUserIconSelectDialogListener{
        void onCamera();
        void onPhoto();
    }

}
