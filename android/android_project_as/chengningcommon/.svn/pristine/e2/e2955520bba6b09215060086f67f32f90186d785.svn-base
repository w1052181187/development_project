package com.chengning.common.base;

import com.google.gson.JsonElement;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class MyRetrofitResponseCallback implements
        Observer<BaseResponseBean> {

    private Disposable disposable;

    @Override
    public void onError(Throwable t) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(BaseResponseBean response) {
        if(response != null ){
            BaseResponseBean body = response;
            String mod = body.getMod();
            String msg = body.getMessage();
            int status = body.getStatus();
            JsonElement element =  body.getData();
            String data = "";
            if (element.isJsonPrimitive()) {
                data = element.getAsJsonPrimitive().getAsString();
            } else {
                data = body.getData().toString();
            }
            switch (status) {
                case 0:
                    onDataSuccess(status, mod, msg, data, response);
                    break;
                default:
                    onDataFailure(status, mod, msg, data, response);
                    break;
            }
        }else{
            onError(null);
        }

    }

    public abstract void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean respons);
    public abstract void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response);
}
