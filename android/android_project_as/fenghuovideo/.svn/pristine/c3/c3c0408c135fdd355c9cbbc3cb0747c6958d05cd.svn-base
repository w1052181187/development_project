package com.chengning.fenghuovideo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.fragment.HomeFragment;
import com.chengning.fenghuovideo.fragment.MyFragment;
import com.chengning.fenghuovideo.util.Common;

/**
 * Created by Administrator on 2017/8/15.
 */

public class WodeActivity extends BaseFragmentActivity {

    private MyFragment mFragment;

    public static void launch(Activity from) {
        Intent intent = new Intent(from,WodeActivity.class);
        from.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Common.setTheme(getActivity());
        setContentView(R.layout.activity_wode);
        super.onCreate(savedInstanceState);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {
        mFragment = new MyFragment();
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.contentLayout, mFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void installListeners() {

    }

    @Override
    public void processHandlerMessage(Message msg) {

    }
}
