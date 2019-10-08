package com.appmf.net.appmf.base.presenter;

import com.appmf.net.appmf.base.BaseRetrofitCallBack;
import com.appmf.net.appmf.base.view.BaseView;
import com.google.gson.JsonElement;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Base Presenter
 * 管理事件流订阅的生命周期
 *
 * @author quchao
 * @date 2017/11/28
 */

public class BasePresenter<T extends BaseView> implements AbstractPresenter<T> {

    protected T mView;
    private CompositeDisposable compositeDisposable;

//    protected void addSubscribe(Disposable disposable) {
//        if (compositeDisposable == null) {
//            compositeDisposable = new CompositeDisposable();
//        }
//        compositeDisposable.add(disposable);
//    }

    public void subcribe(Observable<JsonElement> observable, BaseRetrofitCallBack callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
//        if (compositeDisposable != null) {
//            compositeDisposable.clear();
//        }
    }

    @Override
    public boolean getNightModeState() {
        return false;
    }

}
