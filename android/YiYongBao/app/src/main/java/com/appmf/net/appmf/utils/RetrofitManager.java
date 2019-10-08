package com.appmf.net.appmf.utils;

import android.content.Context;
import android.text.TextUtils;

import com.appmf.net.appmf.base.BaseRetrofitCallBack;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager mRetrofitManager;

    public void setRetrofit(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    private Retrofit mRetrofit;

    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }

    public OkHttpClient.Builder getClientBuilder() {
        return clientBuilder;
    }

    public void setRetrofitBuilder(Retrofit.Builder retrofitBuilder) {
        this.retrofitBuilder = retrofitBuilder;
    }

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

    public static class CookieManager implements CookieJar {

        private Context context;
        private SPUtils sp;
        private List<okhttp3.Cookie> cookies;

        public CookieManager(Context context) {
            this.context = context;
            initSp(context);
        }

        private void initSp(Context context) {
            sp = SPUtils.getInstance(context,SPUtils.APP_CONFIG);
        }

        @Override
        public void saveFromResponse(HttpUrl url, List<okhttp3.Cookie> cookies) {
            saveCookies(url.toString(), url.host(), cookies);
        }

        public void saveCookies(final String url, final String host, List<okhttp3.Cookie> cookies) {
            List<okhttp3.Cookie> newSavedCookies = new ArrayList<>();
            List<okhttp3.Cookie> oldCookies = new ArrayList<>();
            List<okhttp3.Cookie> delCookies = new ArrayList<>();
            if (CommonUtils.isListEmpty(cookies)) {
                return;
            }
            if (!CommonUtils.isListEmpty(this.cookies)) {
                newSavedCookies.addAll(this.cookies);
            }
//            boolean isChanged = false;
            for (okhttp3.Cookie cookie : cookies) {
                if (!TextUtils.equals(cookie.value(), "deleted")) {
                    for (okhttp3.Cookie saveCookie : newSavedCookies) {
                        if (TextUtils.equals(saveCookie.name(), cookie.name())) {
                            oldCookies.add(saveCookie);
                        }
                    }
                    newSavedCookies.removeAll(oldCookies);
                    newSavedCookies.add(cookie);
//                    isChanged = true;
                } else {
                    for (okhttp3.Cookie saveCookie : newSavedCookies) {
                        if (TextUtils.equals(saveCookie.name(), cookie.name())) {
                            delCookies.add(saveCookie);
                        }
                    }
                    newSavedCookies.removeAll(delCookies);
                }
            }

//            if (CommonUtils.isListEmpty(newSavedCookies) || !isChanged) {
//                return;
//            }

            Gson gson = new Gson();
            String cookieStr = gson.toJson(newSavedCookies);

            if (!TextUtils.isEmpty(host)) {
                sp.putString(host, cookieStr);
            }
        }

        @Override
        public List<okhttp3.Cookie> loadForRequest(HttpUrl url) {
            this.cookies = getCookies(url.toString(),url.host());
            return cookies;
        }

        public List<okhttp3.Cookie> getCookies(String url, String host) {
            String decodeCookies = "";
            List<okhttp3.Cookie> cookies;
            decodeCookies = sp.getString(host, "");
            cookies = handleCookie(decodeCookies);
            return cookies;
        }

        List<Cookie> handleCookie(String decodeCookies) {
            String cookie = decodeCookies;
            ArrayList<okhttp3.Cookie> cookies = new Gson().fromJson(cookie,new TypeToken<ArrayList<Cookie>>(){}.getType());
            return cookies != null
                    ? Collections.unmodifiableList(cookies)
                    : Collections.<okhttp3.Cookie>emptyList();
        }
    }

}
