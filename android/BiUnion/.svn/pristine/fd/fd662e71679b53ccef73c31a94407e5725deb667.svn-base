package com.bibinet.biunion.project.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.bibinet.biunion.project.bean.LoginResultBean;

public class CompanyInfoFailHintDialog extends DialogFragment {

    private LoginResultBean.UserBean.EnterpriseBean mModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = null;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
            builder = new AlertDialog.Builder(getActivity(), getTheme());
        }else{
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("提示")
                .setMessage(Html.fromHtml(mModel.getCheckOptions()).toString())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissAllowingStateLoss();
                    }
                });
        return builder.create();
    }

    public void setData(LoginResultBean.UserBean.EnterpriseBean model) {
        this.mModel = model;
    }
}
