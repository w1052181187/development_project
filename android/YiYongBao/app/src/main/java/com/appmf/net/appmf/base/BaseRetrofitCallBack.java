package com.appmf.net.appmf.base;

import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.bean.BaseModel;
import com.appmf.net.appmf.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseRetrofitCallBack implements Observer<JsonElement> {

    public static final int CODE_SUCCESS = 1;
    public static final String CODE_3333 = "3333";
    public static final String CODE_1111 = "1111";

    private final Gson gson;
    private Disposable disposable;

    public BaseRetrofitCallBack() {
        this.gson = new Gson();
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext(JsonElement response) {
//        Class<BaseModel<T>> entityClass = (Class<BaseModel<T>>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        BaseModel model = gson.fromJson(response, BaseModel.class);
        if (model == null) {
            onError(new Throwable("数据为空"));
            return;
        }
        if(model.getStatus() == 1){
            JsonElement jsonElement = model.getData();
            String data = "";
            if (jsonElement.isJsonPrimitive()) {
                data = jsonElement.getAsJsonPrimitive().getAsString();
            } else {
                data = jsonElement.toString();
            }
            onRequestSuccess(data);
        }else{
            onRequestFail(model.getMsg());
        }

//        String str= null;
//        if (jsonElement.isJsonPrimitive()) {
//            str = jsonElement.getAsJsonPrimitive().getAsString();
//        } else {
//            str = jsonElement.toString();
//        }

    }

    @Override
    public void onError(Throwable throwable) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        if (!CommonUtils.isNetworkConnected(MyApp.getInstance())) {
            onRequestFail("网络已断开，请连网后重试");
        } else if (throwable instanceof SocketTimeoutException) {
            onRequestFail("连接超时，请检查网络后重试");
        } else {
            onRequestFail(throwable.getMessage());
        }
    }

    protected abstract void onRequestSuccess(String data);
    protected abstract void onRequestFail(String resMessage);

    @Override
    public void onComplete() {

    }
}
