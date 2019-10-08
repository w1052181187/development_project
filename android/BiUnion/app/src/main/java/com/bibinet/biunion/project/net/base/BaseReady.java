package com.bibinet.biunion.project.net.base;

import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.UrlConfig;
import com.bibinet.biunion.project.utils.RetrofitManager;
import com.google.gson.JsonElement;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by bibinet on 2017-6-23.
 */

public class BaseReady {
    protected UrlConfig urlConfig;

    public BaseReady() {
        this.urlConfig = new UrlConfig();
    }

    protected final void baseReady(String url, boolean isGet, BaseRetrofitCallBack callBack, String... request) {
        HashMap<String, String> params = new HashMap<String, String>();
        //基础
        for (int i = 0; i < request.length / 2; i++) {
            String key = request[2 * i];
            String value = request[2 * i + 1];
            if(value!=null){
                params.put(key, value);
            }
        }
        Observable<JsonElement> observable = null;

        if (isGet) {
            observable
                    = BiUnionApplication.getInstance().getApiInterface().get(url, params);
        } else {
            observable
                    = BiUnionApplication.getInstance().getApiInterface().post(url, params);
        }
        RetrofitManager.subcribe(observable, callBack);
    }

//    protected final void baseReady(String url, boolean isGet, MyRetrofitResponseCallback xCallBack, String... request) {
//        RequestParams requestParams = new RequestParams(url);
//        //基础
//        for (int i = 0; i < request.length / 2; i++) {
//            String key = request[2 * i];
//            String value = request[2 * i + 1];
//            if(value!=null){
//                requestParams.addBodyParameter(key, value);
//            }
//        }
//        if (isGet) {
//            x.http().get(requestParams, xCallBack);
//        } else {
//            x.http().post(requestParams, xCallBack);
//        }
//    }
}
