package com.appmf.net.appmf.di.component;


import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.di.module.AppModule;
import com.appmf.net.appmf.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author wyg
 * @date 2018/11/27
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    /**
     * 注入app实例
     *
     * @param app MyApp
     */
    void inject(MyApp app);

    /**
     * 提供App的Context
     *
     * @return GeeksApp context
     */
    MyApp getContext();

//    /**
//     * 数据中心
//     *
//     * @return DataManager
//     */
//    DataManager getDataManager();

}
