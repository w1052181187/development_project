package com.bibinet.biunion.project.net.ready;

import android.text.TextUtils;

import com.bibinet.biunion.project.RetrofitRequestInterface.ApiInterface;
import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.UrlConfig;
import com.bibinet.biunion.project.net.base.BaseReady;
import com.bibinet.biunion.project.utils.RetrofitManager;
import com.google.gson.JsonElement;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bibinet.biunion.project.net.UrlConfig.TYPE_PIS;

/**
 * Created by bibinet on 2017-6-28.
 */

public class MessageReady extends BaseReady {
    public void getMessageData(int page, String type, BaseRetrofitCallBack callBack) {
        OkHttpClient.Builder clientBuilder = RetrofitManager.getInst().getDefaultClientBuilder();
        HashMap header = new HashMap<String, String>();
        header.put("authorization", "YmliZW5ldDpiaWJlbmV0MTIzLmNvbQ");
        Interceptor headerInterceptor = new RetrofitManager.HeaderInterceptor(header);
        clientBuilder.addInterceptor(headerInterceptor);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(UrlConfig.getBaseUrl(TYPE_PIS))
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build());
        Retrofit mRetrofit = retrofitBuilder.build();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("_type", "1");
        if (!TextUtils.isEmpty(type)) {
            params.put("infoType", type);
        }
        params.put("pageSize", NetConfig.PAGE_SIZE_INN_MESSAGE);
        params.put("pageNum", String.valueOf(page));
        Observable<JsonElement> observable = mRetrofit.create(ApiInterface.class).get(UrlConfig.INN_MESSAGE, params);
        RetrofitManager.subcribe(observable, callBack);
    }
}
