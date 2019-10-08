package com.appmf.net.appmf.presenter;

import com.appmf.net.appmf.base.presenter.BasePresenter;
import com.appmf.net.appmf.contract.AdContract;

import javax.inject.Inject;


/**
 * @author wyg
 * @date 2018/12/27
 */

public class AdPresenter extends BasePresenter<AdContract.View> implements AdContract.Presenter {

    @Inject
    AdPresenter() {
    }
}
