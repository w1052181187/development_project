package com.appmf.net.appmf.base.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.appmf.net.appmf.R;
import com.appmf.net.appmf.base.presenter.BasePresenter;


/**
 * @author wyg
 * @date 2018/11/27
 */

public abstract class BaseActivity<T extends BasePresenter> extends AbstractRootActivity<T> {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    private LottieAnimationView mLoadingAnimation;
    private View mLoadingView;

    public View getNormalView() {
        return mNormalView;
    }

    private View mNormalView;

    private int currentState = NORMAL_STATE;
    private View mErrorView;

    @Override
    protected void initEventAndData() {
        mNormalView = (View) findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "mNormalView's ParentView should be a ViewGroup.");
        }
        ViewGroup mParent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.loading_view, mParent);
        View.inflate(this, R.layout.error_view, mParent);
        mLoadingView = mParent.findViewById(R.id.loading_group);
        mErrorView = mParent.findViewById(R.id.error_group);
        TextView reloadTv = mErrorView.findViewById(R.id.error_reload_tv);
        reloadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        mLoadingAnimation = mLoadingView.findViewById(R.id.loading_animation);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (mLoadingAnimation != null) {
            mLoadingAnimation.cancelAnimation();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingAnimation.setAnimation("loading_bus.json");
        mLoadingAnimation.loop(true);
        mLoadingAnimation.playAnimation();
    }

    @Override
    public void showError() {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingAnimation.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
            default:
                break;
        }
    }

    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
        super.onBackPressedSupport();
    }

    @Override
    public void reload() {

    }
}