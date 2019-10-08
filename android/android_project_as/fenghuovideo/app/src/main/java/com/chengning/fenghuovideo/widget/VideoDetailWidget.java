package com.chengning.fenghuovideo.widget;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.view.ViewGroup;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.activity.VideoDetailBottomFragment;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.event.CommentSuccessEvent;

public class VideoDetailWidget {
    private BaseFragmentActivity mActivity;
    private View mRoot;
    private ViewGroup mVideoLayout;

    private TitleBar mTitleBar;
    private VideoDetailBottomFragment mFragment;
    
    private VideoItemWidget mVideoWidget;

    private TitleBar.BackOnClickListener mBackOnClickListener;

    public VideoDetailWidget(BaseFragmentActivity activity) {
        this.mActivity = activity;
        mRoot = activity.findViewById(R.id.article_content_content_layout);
        mVideoLayout = (ViewGroup) mRoot.findViewById(R.id.video_layout);
    }

    public void launch(View view, BaseArticlesBean bean, String type, boolean isFromPush) {
        mRoot.setVisibility(View.VISIBLE);

        mTitleBar = new TitleBar(mActivity, mRoot);
        mTitleBar.setBackgroundColor(mActivity.getResources().getColor(
                R.color.transparent));
        mTitleBar.setBackText("");
        mTitleBar.setBackText("", mActivity.getResources().getColor(R.color.transparent),
                mActivity.getResources().getDrawable(R.drawable.fv_back));
        mTitleBar.setBackTextBackgroundColor(mActivity.getResources().getColor(R.color.transparent));
        mTitleBar.setBackOnClickListener(new TitleBar.BackOnClickListener() {
            @Override
            public void onClick() {
                onBackPressed();
            }
        }, false);
        
        ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
        DisplayUtil.getInst().init(mActivity);
        layoutParams.height = DisplayUtil.getInst().getScreenWidth() * 9 /16;
        mVideoLayout.setLayoutParams(layoutParams);
//        ViewGroup.LayoutParams layoutParams2 = mVideoContent.getLayoutParams();
//        layoutParams2.height = DisplayUtil.getInst().getScreenWidth() * 9 /16;
//        mVideoContent.setLayoutParams(layoutParams2);
        
        // removeView
//        VideoItemWidget mVideoWidget = VideoItemWidget.createVideoItemWidget(mActivity, view, null, null);
        mVideoWidget = VideoItemWidget.createVideoItemWidget(mActivity, view, bean.getVideo_address(), null);
        mVideoWidget.enterFloatingDetail(mVideoLayout);
//        mVideoWidget.setUrlNotStart(bean.getVideo_address());

        mFragment = VideoDetailBottomFragment.newInstance(bean, type, isFromPush);
        FragmentManager mFragmentManager = mActivity.getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.contentLayout_video_detail, mFragment)
                .commitAllowingStateLoss();
    }

    public void onEventMainThread(CommentSuccessEvent event) {
        if(mFragment != null){
            mFragment.onEventMainThread(event);
        }
    }

    public void onExit() {
        mVideoWidget.exitFloatingDetail();
        mRoot.setVisibility(View.INVISIBLE);
        // addView
    }
    
    public void setOnBackListener(TitleBar.BackOnClickListener listener){
        this.mBackOnClickListener = listener;
    }
    
    public boolean onBackPressed() {
        if(mFragment != null && mActivity != null){
            onExit();
            
            FragmentManager mFragmentManager = mActivity.getSupportFragmentManager();
            mFragmentManager.beginTransaction().remove(mFragment).commitAllowingStateLoss();

            mFragment = null;

            if(mBackOnClickListener != null)mBackOnClickListener.onClick();
            return true;
        }
        return false;
    }

}
