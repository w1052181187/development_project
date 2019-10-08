package com.bibinet.biunion.project.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.net.UrlConfig;
import com.bibinet.biunion.project.ui.expand.TitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberServiceActivity extends TitleActivity {

    @BindView(R.id.act_member_service_zuanshi)
    View zuanshiView;
    @BindView(R.id.act_member_service_zuanshi_tv)
    TextView zuanshiTv;
    @BindView(R.id.act_member_service_zuanshi_money)
    TextView zuanshiMoney;
    @BindView(R.id.act_member_service_baijin)
    View baijinView;
    @BindView(R.id.act_member_service_baijin_tv)
    TextView baijinTv;
    @BindView(R.id.act_member_service_baijin_money)
    TextView baijinMoney;
    @BindView(R.id.act_member_service_vip)
    View vipView;
    @BindView(R.id.act_member_service_vip_tv)
    TextView vipTv;
    @BindView(R.id.act_member_service_vip_money)
    TextView vipMoney;
    @BindView(R.id.act_member_service_gaoji)
    View gaojiView;
    @BindView(R.id.act_member_service_gaoji_tv)
    TextView gaojiTv;
    @BindView(R.id.act_member_service_gaoji_money)
    TextView gaojiMoney;
    @BindView(R.id.act_member_service_biaozhun)
    View biaozhunView;
    @BindView(R.id.act_member_service_biaozhun_tv)
    TextView biaozhunTv;
    @BindView(R.id.act_member_service_biaozhun_money)
    TextView biaozhunMoney;

    @BindView(R.id.act_member_service_viewpager)
    ViewPager mViewPager;
    private WebView[] webViews = new WebView[5];

    @Override
    protected int getTitleLayoutId() {
        return R.layout.activity_member_service;
    }

    @Override
    protected int getTitleString() {
        return R.string.member_service;
    }


//    private WebView mWebview;
    @Override
    protected void onTitleCreate(Bundle savedInstanceState) {
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                WebView mWebview = webViews[position];
                if (mWebview == null) {
                    mWebview = handleImage(position);
                }
                handleImage(position);
                ViewGroup parent = (ViewGroup) mWebview.getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }
                container.addView(mWebview);
                webViews[position] = mWebview;
                return mWebview;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(webViews[position]);
            }
            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                mViewPager.resetHeight(position);
                click(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        click(0);
    }

    @OnClick({R.id.act_member_service_zuanshi, R.id.act_member_service_baijin,
            R.id.act_member_service_vip, R.id.act_member_service_gaoji, R.id.act_member_service_biaozhun})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.act_member_service_zuanshi:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.act_member_service_baijin:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.act_member_service_vip:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.act_member_service_gaoji:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.act_member_service_biaozhun:
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    void click(int pos) {
        switch (pos) {
            case 0:
                handleClick(zuanshiView,zuanshiTv,zuanshiMoney);
                break;
            case 1:
                handleClick(baijinView,baijinTv,baijinMoney);
                break;
            case 2:
                handleClick(vipView,vipTv,vipMoney);
                break;
            case 3:
                handleClick(gaojiView,gaojiTv,gaojiMoney);
                break;
            case 4:
                handleClick(biaozhunView,biaozhunTv,biaozhunMoney);
                break;
        }
    }

    private WebView handleImage(int pos) {
        WebView mWebview = new WebView(MemberServiceActivity.this);
//        WebSettings seting = mWebview.getSettings();
//        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
//        seting.setUseWideViewPort(true);//设定支持viewport
//        seting.setLoadWithOverviewMode(true);
//        //设定支持缩放
//        seting.setBuiltInZoomControls(true);
//        seting.setSupportZoom(true);
//        //隐藏缩放控制条
//        seting.setDisplayZoomControls(false);
        int type = 5;
        switch (pos) {
            case 0:
                type = 5;
                break;
            case 1:
                type = 4;
                break;
            case 2:
                type = 3;
                break;
            case 3:
                type = 2;
                break;
            case 4:
                type = 1;
                break;
        }
        mWebview.loadUrl(UrlConfig.MEMBER_SERVICE + type);
        return mWebview;
    }

    private void handleClick(View view, TextView tv, TextView money) {
        resetView();
        view.setSelected(true);
        view.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));
        money.setVisibility(View.VISIBLE);
        switch (tv.getId()) {
            case R.id.act_member_service_zuanshi_tv:
                tv.setText(R.string.member_service_zuanshi);
                money.setText(R.string.member_service_zuanshi_money);
                break;
            case R.id.act_member_service_baijin_tv:
                tv.setText(R.string.member_service_baijin);
                money.setText(R.string.member_service_baijin_money);
                break;
            case R.id.act_member_service_vip_tv:
                tv.setText(R.string.member_service_vip);
                money.setText(R.string.member_service_vip_money);
                break;
            case R.id.act_member_service_gaoji_tv:
                tv.setText(R.string.member_service_gaoji);
                money.setText(R.string.member_service_gaoji_money);
                break;
            case R.id.act_member_service_biaozhun_tv:
                tv.setText(R.string.member_service_biaozhun);
                money.setText(R.string.member_service_biaozhun_money);
                break;
        }
    }

    private void resetView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        zuanshiView.setSelected(false);
        zuanshiView.setLayoutParams(layoutParams);
        zuanshiTv.setText(R.string.member_service_zuanshi_unsel);
        zuanshiMoney.setVisibility(View.GONE);
        baijinView.setSelected(false);
        baijinView.setLayoutParams(layoutParams);
        baijinTv.setText(R.string.member_service_baijin_unsel);
        baijinMoney.setVisibility(View.GONE);
        vipView.setSelected(false);
        vipView.setLayoutParams(layoutParams);
        vipTv.setText(R.string.member_service_vip_unsel);
        vipMoney.setVisibility(View.GONE);
        gaojiView.setSelected(false);
        gaojiView.setLayoutParams(layoutParams);
        gaojiTv.setText(R.string.member_service_gaoji_unsel);
        gaojiMoney.setVisibility(View.GONE);
        biaozhunView.setSelected(false);
        biaozhunView.setLayoutParams(layoutParams);
        biaozhunTv.setText(R.string.member_service_biaozhun_unsel);
        biaozhunMoney.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        for (WebView webview : webViews) {
            if (webview != null) {
                ViewGroup parent = (ViewGroup) webview.getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }
                webview.removeAllViews();
                webview.destroy();
                webview = null;
            }
        }

        super.onDestroy();
    }
}
