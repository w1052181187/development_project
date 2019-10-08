package com.appmf.net.appmf.di.module;

import android.app.Activity;

import com.appmf.net.appmf.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author wyg
 * @date 2018/11/27
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return mActivity;
    }

}
