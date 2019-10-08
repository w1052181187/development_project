package com.bibinet.biunion.project.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.custom.MusicWaitView;
import com.bibinet.biunion.project.ui.expand.TitleActivity;

import butterknife.BindView;


/**
 * Created by bibinet on 2017-6-8.
 */

public class ResetPasswordActivity extends TitleActivity {

    @BindView(R.id.main)
    MusicWaitView musicWaitView;
    @Override
    protected int getTitleLayoutId() {
        return R.layout.activity_citylist;
    }

    @Override
    protected int getTitleString() {
        return R.string.str_message;
    }

    @Override
    protected void onTitleCreate(Bundle savedInstanceState) {
        musicWaitView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicWaitView.stop();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ResetPasswordActivity.class));
    }
}
