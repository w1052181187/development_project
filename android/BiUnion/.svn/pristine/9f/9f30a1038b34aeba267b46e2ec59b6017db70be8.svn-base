package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.custom.WaitView;


/**
 * Created by bibinet on 2017-5-3.
 */

public class WaitUtils {
    public WaitView view;

    public WaitUtils(WaitView view){
        this.view = view;
    }

    public void open(){
        view.start();
        view.setVisibility(View.VISIBLE);
    }

    public void close(){
        view.stop();
        view.setVisibility(View.GONE);
    }

}
