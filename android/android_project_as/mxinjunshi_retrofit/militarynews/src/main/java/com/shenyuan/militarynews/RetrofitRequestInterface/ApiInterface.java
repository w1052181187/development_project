package com.shenyuan.militarynews.RetrofitRequestInterface;

import com.chengning.common.base.BaseResponseBean;
import com.shenyuan.militarynews.beans.data.DomainBean;
import com.shenyuan.militarynews.utils.JUrl;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {

    /**
     * 更换域名
     * @return
     */
    @GET(JUrl.URL_CHANGE_SITE)
    Observable<Response<DomainBean>> changeDomain();

    @Headers({
            "Accept-Encoding: gzip, deflate",
            "Accept: */*",
            "Accept-Language: zh-cn"
    })
    @POST
    @FormUrlEncoded
    Observable<BaseResponseBean> postArticleDetail(@Url String url, @FieldMap Map<String, String> params);

    @POST
    Observable<BaseResponseBean> post(@Url String url);

    @POST
    @FormUrlEncoded
    Observable<BaseResponseBean> post(@Url String url, @FieldMap Map<String, String> params);

    @POST
    @Multipart
    Observable<BaseResponseBean> post(@Url String url,@Part MultipartBody.Part file);

    @GET
    Observable<BaseResponseBean> get(@Url String url);

    @GET
    Observable<BaseResponseBean> get(@Url String url, @QueryMap Map<String, String> params);

}
