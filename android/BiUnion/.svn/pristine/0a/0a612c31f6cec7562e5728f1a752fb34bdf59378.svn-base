package com.bibinet.biunion.project.net;

import com.bibinet.biunion.project.models.BaseModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;

import io.reactivex.disposables.Disposable;

public abstract class MyRetrofitResponseCallback<T extends BaseModel> extends BaseRetrofitCallBack {

    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_3333 = "3333";
    public static final String CODE_1111 = "1111";
    private Gson gson;

    public MyRetrofitResponseCallback() {
        this.gson = new Gson();
    }

    private Disposable disposable;

    @Override
    public void onError(Throwable t) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onError(t);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(JsonElement response) {

        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T model = gson.fromJson(response, entityClass);
        if(model.getResCode().equals(CODE_SUCCESS)){
            onRequestSuccess(model);
        }else{
            onRequestFail(model.getResMessage());
        }

    }

    protected abstract void onRequestSuccess(T model);
}
