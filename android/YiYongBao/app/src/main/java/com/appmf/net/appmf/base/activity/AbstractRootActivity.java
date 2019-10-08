package com.appmf.net.appmf.base.activity;

import android.support.v7.app.AppCompatDelegate;

import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.presenter.AbstractPresenter;
import com.appmf.net.appmf.base.view.BaseView;
import com.appmf.net.appmf.di.component.ActivityComponent;
import com.appmf.net.appmf.di.component.DaggerActivityComponent;
import com.appmf.net.appmf.di.module.ActivityModule;
import com.appmf.net.appmf.utils.CommonUtils;

import javax.inject.Inject;

/**
 * MVP模式的Base Activity
 *
 * @author wyg
 * @date 2018/11/27
 */

public abstract class AbstractRootActivity<T extends AbstractPresenter> extends AbstractSimpleActivity implements BaseView {

    @Inject
    protected T mPresenter;

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApp.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        CommonUtils.showShortMessage(this, errorMsg);
    }

    /**
     * 注入当前Activity所需的依赖
     */
    protected abstract void initInject();

}
