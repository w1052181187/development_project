package com.bibinet.biunion.project.RetrofitRequestInterface;

import com.bibinet.biunion.project.models.BaseModel;
import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

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

    /**
     * 下载音乐
     *
     * @param fileUrl
     * @return
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Observable<ResponseBody> downloadMusicFile(@Url String fileUrl);

}
