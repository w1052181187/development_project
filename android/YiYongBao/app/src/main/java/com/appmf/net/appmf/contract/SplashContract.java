package com.appmf.net.appmf.contract;


import com.appmf.net.appmf.base.presenter.AbstractPresenter;
import com.appmf.net.appmf.base.view.BaseView;
import com.appmf.net.appmf.bean.AdContentBean;

/**
 * @author wyg
 * @date 2018/11/28
 */

public interface SplashContract {

    interface View extends BaseView {
        void launchMain();
        void launchAd(AdContentBean bean, boolean isHasCache);
    }

    interface Presenter extends AbstractPresenter<View> {
       
    }

}
