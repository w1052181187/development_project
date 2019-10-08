package com.bibinet.biunion.project.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by Wh on 2018-1-9.
 */

public abstract class BaseDialog extends Dialog{

    public BaseDialog(Context context) {
        super(context);
        initDialog();
        View view = LayoutInflater.from(context).inflate(getLayoutId(), null, false);
        setContentView(view);
        ButterKnife.bind(this);
        onBaseCreate();
    }

    protected abstract void onBaseCreate();

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
    }

    protected abstract int getLayoutId();

}
