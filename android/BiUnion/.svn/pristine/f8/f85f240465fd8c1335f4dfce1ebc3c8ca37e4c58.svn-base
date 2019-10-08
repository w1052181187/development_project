package com.bibinet.biunion.project.net.base;

import android.content.Context;

import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.utils.ToastUtils;
import com.google.gson.Gson;

/**
 * Created by Wh on 2017-7-17.
 */

public class BaseFiring {
    protected WaitDialog waitDialog;
    protected ToastUtils toastUtils;
    protected Gson gson;
    public BaseFiring(Object context){
        if(context != null){
            waitDialog = new WaitDialog((Context) context);
        }
        gson  = new Gson();
        this.toastUtils = new ToastUtils("");
    }
}
