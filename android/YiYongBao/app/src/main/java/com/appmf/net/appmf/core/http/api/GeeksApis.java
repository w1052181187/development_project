package com.appmf.net.appmf.core.http.api;


import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author wyg
 * @date 2018/2/12
 */

public interface GeeksApis {

    String HOST = "http://api.appmf.net";
    String APPMF_URL = "http://api.appmf.net";
    String URL_STATISTICS = "/ad/statistics";

    @POST
    Observable<JsonElement> post(@Url String url);

    @POST
    @FormUrlEncoded
    Observable<JsonElement> post(@Url String url, @FieldMap Map<String, String> params);

    @POST
    @Multipart
    Observable<JsonElement> post(@Url String url, @Part MultipartBody.Part file);

    @POST
    @Multipart
    Observable<JsonElement> postBusinessLicense(@Url String url, @Part("fileType") int fileType, @Part MultipartBody.Part file);

    @GET
    Observable<JsonElement> get(@Url String url);

    @GET
    Observable<JsonElement> get(@Url String url, @QueryMap Map<String, String> params);

    @PUT
    Observable<JsonElement> put(@Url String url);

    @PUT
    Observable<JsonElement> put(@Url String url, @QueryMap Map<String, String> params);

    @HEAD
    Observable<JsonElement> head(@Url String url);

    @HEAD
    Observable<JsonElement> head(@Url String url, @QueryMap Map<String, String> params);

    @DELETE
    Observable<JsonElement> delete(@Url String url);

    @DELETE
    Observable<JsonElement> delete(@Url String url, @QueryMap Map<String, String> params);

    @OPTIONS
    Observable<JsonElement> options(@Url String url);

    @OPTIONS
    Observable<JsonElement> options(@Url String url, @QueryMap Map<String, String> params);

    /**
     * 下载图片或者视频
     *
     * @param fileUrl
     * @return
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
