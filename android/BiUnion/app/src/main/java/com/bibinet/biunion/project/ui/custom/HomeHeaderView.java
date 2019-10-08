package com.bibinet.biunion.project.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IHeaderCallBack;
import com.bibinet.biunion.R;
import com.bibinet.biunion.project.utils.ScreenShotUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wh on 2017-7-13.
 */

public class HomeHeaderView extends RefreshHeaderView<HomeHeaderView> implements IHeaderCallBack {

    @BindView(R.id.xrefreshview_header_text)
    View mainV;

    @Nullable
    @BindView(R.id.v_refresh_header_hint)
    TextView hintTV;

    @Nullable
    @BindView(R.id.v_refresh_header_hint_assist)
    TextView assistTV;

    @BindView(R.id.v_refresh_header_img)
    DynamicImageView dynamicImageView;
    private String count = "";
    private String assist = "";

    public HomeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static HomeHeaderView getInstance(Context context){
        return new HomeHeaderView(context, null);
    }

    @Override
    public void onRefreshStateNormal() {
        hintTV.setText("下拉刷新");
        hintTV.setTextColor(Color.parseColor("#666666"));
        hintTV.getPaint().setFlags(Paint.LINEAR_TEXT_FLAG);
        hintTV.getPaint().setAntiAlias(true);
        hintTV.setTextSize(11);
        assistTV.setVisibility(View.GONE);
        dynamicImageView.start();
    }

    @Override
    public void onSecondaryStateReady() {
        hintTV.setText("今日更新");
        hintTV.setTextColor(Color.parseColor("#666666"));
        hintTV.getPaint().setFlags(Paint.LINEAR_TEXT_FLAG);
        hintTV.getPaint().setAntiAlias(true);
        hintTV.setTextSize(11);
        assistTV.setVisibility(View.GONE);
    }

    @Override
    public void onStateReady() {
        hintTV.setText("松手刷新");
        hintTV.setTextColor(Color.parseColor("#666666"));
        hintTV.getPaint().setFlags(Paint.LINEAR_TEXT_FLAG);
        hintTV.getPaint().setAntiAlias(true);
        hintTV.setTextSize(11);
        assistTV.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshing() {
        hintTV.setText("信息加载中...");
        hintTV.setTextColor(Color.parseColor("#666666"));
        hintTV.getPaint().setFlags(Paint.LINEAR_TEXT_FLAG);
        hintTV.getPaint().setAntiAlias(true);
        hintTV.setTextSize(11);
        assistTV.setVisibility(View.GONE);
    }

    @Override
    public void onSecondaryRefreshing() {
        hintTV.setText(count);
        hintTV.setTextColor(Color.parseColor("#3d3d3d"));
        hintTV.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        hintTV.getPaint().setAntiAlias(true);
        hintTV.setTextSize(13);
        assistTV.setVisibility(View.VISIBLE);
        if(!assist.equals("")){
            assistTV.setText(assist);
        }
    }

    @Override
    public void onRefreshStateFinish(boolean success) {
        dynamicImageView.stop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_refresh_header;
    }

    @Override
    public int getHeaderHeight() {
        return mainV.getMeasuredHeight();
    }

    public void setCount(String count) {
//        if(hintTV!=null && hintTV.getText().toString().equals(this.count)){
//            hintTV.setText(count);
//        }
        this.count = count;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }
}
