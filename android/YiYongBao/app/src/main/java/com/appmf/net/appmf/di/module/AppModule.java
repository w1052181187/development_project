package com.appmf.net.appmf.di.module;


import com.appmf.net.appmf.app.MyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author wyg
 * @date 2018/11/27
 */

@Module
public class AppModule {

    private final MyApp application;

    public AppModule(MyApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MyApp provideApplicationContext() {
        return application;
    }

}
