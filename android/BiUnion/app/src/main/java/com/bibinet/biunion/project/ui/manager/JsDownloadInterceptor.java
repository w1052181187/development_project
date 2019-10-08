package com.bibinet.biunion.project.ui.manager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class JsDownloadInterceptor implements Interceptor {

    private RxjavaDownloadListener downloadListener;

    public JsDownloadInterceptor(RxjavaDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new DownloadResponseBody(response.body(), downloadListener)).build();
    }
}