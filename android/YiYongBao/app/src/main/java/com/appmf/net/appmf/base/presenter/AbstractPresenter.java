package com.appmf.net.appmf.base.presenter;

import com.appmf.net.appmf.base.view.BaseView;

/**
 * Presenter 基类
 *
 * @author wyg
 * @date 2018/11/27
 */

public interface AbstractPresenter<T extends BaseView> {

    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();


    /**
     * Get night mode state
     *
     * @return if is night mode
     */
    boolean getNightModeState();

}
