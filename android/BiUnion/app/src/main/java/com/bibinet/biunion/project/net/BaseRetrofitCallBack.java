package com.bibinet.biunion.project.net;

import android.text.TextUtils;

import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.utils.NetworkUtils;
import com.google.gson.JsonElement;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseRetrofitCallBack implements Observer<JsonElement> {

    @Override
    public void onSubscribe(Disposable disposable) {
    }

    @Override
    public void onNext(JsonElement jsonElement) {
        String str= null;
        if (jsonElement.isJsonPrimitive()) {
            str = jsonElement.getAsJsonPrimitive().getAsString();
        } else {
            str = jsonElement.toString();
        }
        if (!TextUtils.isEmpty(str)) {
            onRequestSuccess(str);
        } else {
            onError(new Throwable("数据为空"));
        }

    }

    public void onRequestSuccess(String str) {

    }

    @Override
    public void onError(Throwable throwable) {
        if (!NetworkUtils.isNetworkAvailable(BiUnionApplication.getInstance())) {
            onRequestFail("网络已断开，请连网后重试");
        } else if (throwable instanceof SocketTimeoutException) {
            onRequestFail("连接超时，请检查网络后重试");
        } else {
            onRequestFail(throwable.getMessage());
        }
    }

    protected abstract void onRequestFail(String resMessage);

    @Override
    public void onComplete() {

    }
}
