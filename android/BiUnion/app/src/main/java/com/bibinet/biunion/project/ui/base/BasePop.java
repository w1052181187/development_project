package com.bibinet.biunion.project.ui.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.bibinet.biunion.R;

import butterknife.ButterKnife;

/**
 * Created by Wh on 2018-1-3.
 */

public abstract class BasePop extends PopupWindow implements PopupWindow.OnDismissListener {

    private Context context;
    private View layoutView;

    public BasePop(Context context) {
        super(context);
        this.context = context;
        initPop();
    }

    private void initPop() {
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        layoutView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        setContentView(layoutView);
        //点击popupWindow以外的区域自动关闭popupWindow
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOnDismissListener(this);
        setAnimationStyle(R.anim.tr_void);
        ButterKnife.bind(this, layoutView);
        onCreate();
    }
    public Context getContext() {
        return context;
    }

    protected abstract void onCreate();

    protected abstract int getLayoutId();
}
