package com.chengning.fenghuovideo.nicevideo;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.DisplayUtil;
import com.chengning.common.util.StatusBarUtil;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.activity.VideoDetailBottomFragment;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.event.CommentSuccessEvent;
import com.chengning.fenghuovideo.widget.TitleBar;
import com.chengning.fenghuovideo.widget.VideoItemWidget;

public class FhVideoDetailWidget {
    private BaseFragmentActivity mActivity;
    private View mRoot;
    private ViewGroup mVideoLayout;
    
    private ViewGroup mVideoLayout2;
    private FhNiceVideoWidget mVideoWidget2;

//    private TitleBar mTitleBar;
    private VideoDetailBottomFragment mFragment;
    
    private FhNiceVideoWidget mVideoWidget;

    private TitleBar.BackOnClickListener mBackOnClickListener;

    public FhVideoDetailWidget(BaseFragmentActivity activity) {
        this.mActivity = activity;
        mRoot = activity.findViewById(R.id.article_content_content_layout);
        mVideoLayout = (ViewGroup) mRoot.findViewById(R.id.video_layout1);
        mVideoLayout2 = (ViewGroup) mRoot.findViewById(R.id.video_layout2);
    }

    public void launch(View view, FhItemData data, String type, boolean isFromPush) {
        mRoot.setVisibility(View.VISIBLE);
//
//        mTitleBar = new TitleBar(mActivity, mRoot);
//        mTitleBar.setBackgroundColor(mActivity.getResources().getColor(
//                R.color.transparent));
//        mTitleBar.setBackText("");
//        mTitleBar.setBackText("", mActivity.getResources().getColor(R.color.transparent),
//                mActivity.getResources().getDrawable(R.drawable.fv_back));
//        mTitleBar.setBackTextBackgroundColor(mActivity.getResources().getColor(R.color.transparent));
//        mTitleBar.setBackOnClickListener(new TitleBar.BackOnClickListener() {
//            @Override
//            public void onClick() {
//                onBackPressed();
//            }
//        }, false);
        
        ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
        DisplayUtil.getInst().init(mActivity);
        layoutParams.height = DisplayUtil.getInst().getScreenWidth() * 9 /16;
        mVideoLayout.setLayoutParams(layoutParams);
        
        ViewGroup.LayoutParams layoutParams2 = mVideoLayout2.getLayoutParams();
        layoutParams.height = DisplayUtil.getInst().getScreenWidth() * 9 /16;
        mVideoLayout2.setLayoutParams(layoutParams);
        mVideoWidget2 = FhNiceVideoWidget.createVideoItemWidget(mActivity, mVideoLayout2, data.getBean().getVideo_address(), data, FhVideoPlayerController.CtrlMode.Detail);
        mVideoWidget2.getHolder().getController().setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoWidget.getHolder().getPlayer().isTinyWindow()){
                    onBackPressed();
                    mVideoWidget.getHolder().getController().setBackListener(null);
                }
            }
        });
        
//        ViewGroup.LayoutParams layoutParams2 = mVideoContent.getLayoutParams();
//        layoutParams2.height = DisplayUtil.getInst().getScreenWidth() * 9 /16;
//        mVideoContent.setLayoutParams(layoutParams2);
        
        // removeView
        
//        VideoItemWidget.ListViewItemData itemData = null;
//        VideoItemWidget mVideoWidget = VideoItemWidget.createVideoItemWidget(mActivity, view, null, null);
        mVideoWidget = FhNiceVideoWidget.findVideoItemWidget(mActivity, view, data.getBean().getVideo_address(), data, FhVideoPlayerController.CtrlMode.Detail);
        mVideoWidget.getHolder().getController().setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoWidget.getHolder().getPlayer().isTinyWindow()){
                    onBackPressed();
                    mVideoWidget.getHolder().getController().setBackListener(null);
                }
            }
        });
        StatusBarUtil.setBar(mActivity, mActivity.getResources().getColor(R.color.black), false);
        mVideoWidget.getHolder().getPlayer().setTinyWindowParent(mVideoLayout);
        mVideoWidget.getHolder().getPlayer().enterTinyWindow();
//        mVideoWidget.setUrlNotStart(bean.getVideo_address());

        mFragment = VideoDetailBottomFragment.newInstance(data.getBean(), type, isFromPush);
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

    private void onExit() {
        StatusBarUtil.setBar(mActivity, mActivity.getResources().getColor(R.color.home_red), false);
        mVideoWidget.getHolder().getPlayer().exitTinyWindow();
        mVideoWidget.getHolder().getPlayer().setTinyWindowParent(null);

        mVideoWidget2.release();
        mRoot.setVisibility(View.INVISIBLE);
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
