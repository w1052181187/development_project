package com.appmf.net.appmf.di.component;

import android.app.Activity;

import com.appmf.net.appmf.base.activity.BaseWebviewActivity;
import com.appmf.net.appmf.view.AdActivity;
import com.appmf.net.appmf.view.MainActivity;
import com.appmf.net.appmf.di.module.ActivityModule;
import com.appmf.net.appmf.di.scope.ActivityScope;
import com.appmf.net.appmf.view.SplashActivity;

import dagger.Component;


/**
 * @author wyg
 * @date 2018/11/27
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    /**
     * 获取Activity实例
     *
     * @return Activity
     */
    Activity getActivity();

    /**
     * 注入MAinActivity所需的依赖
     *
     * @param mainActivity MainActivity
     */
    void inject(MainActivity mainActivity);
    /**
     * SplashActivity
     *
     * @param splashActivity SplashActivity
     */
    void inject(SplashActivity splashActivity);

    /**
     *
     * @param adActivity
     */
    void inject(AdActivity adActivity);

}
