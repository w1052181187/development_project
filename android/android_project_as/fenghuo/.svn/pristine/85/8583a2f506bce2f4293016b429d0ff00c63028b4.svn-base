package com.chengning.fenghuo.activity;

import android.app.Activity;
import android.content.Intent;

import com.chengning.fenghuo.base.BaseWebviewFragmentActivity;
import com.chengning.fenghuo.widget.TitleBar;

/**
 * Created by Administrator on 2017/6/26.
 *
 */

public class TaskWebActivity extends BaseWebviewFragmentActivity {

    public static void launch(Activity from,String url) {
        Intent intent = new Intent(from, TaskWebActivity.class);
        intent.putExtra("url",url);
        from.startActivity(intent);
    }

    @Override
    public Activity buildContext() {
        return this;
    }

    @Override
    public void initTitleBar(TitleBar titleBar) {
        titleBar.setBackText("");
    }

    @Override
    public String buildUrl() {
        String url = getIntent().getStringExtra("url");
        return url;
    }


}
