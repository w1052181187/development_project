package com.bibinet.biunion.project.utils;

import android.content.Context;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder clientBuilder;

    public void initRetrofit(Context context, String baseUrl, Interceptor... interceptors) {
        clientBuilder = getDefaultClientBuilder();
        for (Interceptor interceptor : interceptors) {
            clientBuilder.addInterceptor(interceptor);
        }
        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build());
        mRetrofit = retrofitBuilder.build();
    }

    public <T> T createService(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }

    public static RetrofitManager getInst() {
        if( mRetrofitManager == null){
            synchronized (RetrofitManager.class) {
                if( mRetrofitManager == null){
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return  mRetrofitManager;
    }

    public OkHttpClient.Builder getDefaultClientBuilder() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);
        return httpClientBuilder;
    }

    public static void subcribe(Observable<JsonElement> observable, BaseRetrofitCallBack callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

//    public static void subcribe(Observable<String> observable, BaseRetrofitCallBack callback) {
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(callback);
//    }

    public static class HeaderInterceptor implements Interceptor {

        private HashMap<String, String> headers;

        public HeaderInterceptor(HashMap<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder request = chain.request()
                    .newBuilder();
            Set<String> set = headers.keySet();
            for (String  key : set) {
                request.removeHeader(key);
                request.addHeader(key, headers.get(key));
            }
            return chain.proceed(request.build());
        }
    }

}
