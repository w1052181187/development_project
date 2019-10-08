package com.appmf.net.appmf.presenter;

import android.Manifest;
import android.app.Activity;
import android.text.TextUtils;

import com.appmf.net.appmf.app.Constants;
import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.BaseRetrofitCallBack;
import com.appmf.net.appmf.base.presenter.BasePresenter;
import com.appmf.net.appmf.bean.AdContentBean;
import com.appmf.net.appmf.bean.AdDataBean;
import com.appmf.net.appmf.contract.MainContract;
import com.appmf.net.appmf.core.http.api.GeeksApis;
import com.appmf.net.appmf.dao.AdStatisticsModel;
import com.appmf.net.appmf.dao.AdStatisticsModelDao;
import com.appmf.net.appmf.dao.DaoSession;
import com.appmf.net.appmf.utils.CommonUtils;
import com.appmf.net.appmf.utils.DeviceUtils;
import com.appmf.net.appmf.utils.FileIOUtils;
import com.appmf.net.appmf.utils.FileUtils;
import com.appmf.net.appmf.utils.RetrofitManager;
import com.appmf.net.appmf.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author wyg
 * @date 2018/11/27
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private SPUtils shareutil;
    private Gson gson;

    @Inject
    MainPresenter() {
        shareutil = SPUtils.getInstance(MyApp.getInstance(), SPUtils.APP_CONFIG);
        gson = new Gson();
    }

    public void saveToCache(final Activity context) {
        RxPermissions rxPermissions = new RxPermissions(context);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            FileUtils.delete(Constants.PATH_CACHE_AD_IMAGE);
                            FileUtils.delete(Constants.PATH_CACHE_AD_VIDEO);
                            String data = shareutil.getString(SPUtils.KEY_AD_DATA,"");
                            AdDataBean dataBean = gson.fromJson(data, AdDataBean.class);
                            if (dataBean == null) {
                                return;
                            }
                            AdContentBean contentBean = dataBean.getAd();
                            int type = contentBean.getFormat();
                            switch (type) {
                                case 1:
                                    ArrayList<String> images = (ArrayList<String>) contentBean.getImg();
                                    if (CommonUtils.isListEmpty(images)) {
                                        return ;
                                    }
                                    for(String url : images) {
                                        final String name = new File(url).getName();
                                        final String path = Constants.PATH_CACHE_AD_IMAGE + "/" + name;
                                        handleDownloadByRx(url, path);
                                    }

                                    break;
                                case 2:
                                    String videoPath = contentBean.getVideo();
                                    if (TextUtils.isEmpty(videoPath)) {
                                        return ;
                                    }
                                    final String name = new File(videoPath).getName();
                                    final String l = Constants.PATH_CACHE_AD_VIDEO + "/" + name;
                                    handleDownloadByRx(videoPath, l);
                                    break;
                            }
                        } else {
                            CommonUtils.showShortMessage(context, "权限申请失败");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    void handleDownloadByRx(String url, final String path) {
        Observable<ResponseBody> observable = MyApp.getInstance().getApiInterface()
                .downloadFile(url);

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {

                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }

                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        if (FileUtils.createOrExistsFile(path)) {
                            FileIOUtils.writeFileFromIS(path, inputStream);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void uploadStatistics() {
        DaoSession daoSession = MyApp.getInstance().getDaoSession();
        ArrayList<AdStatisticsModel> adStatisticsBeans = (ArrayList<AdStatisticsModel>) daoSession.getAdStatisticsModelDao().queryBuilder()
                .whereOr(AdStatisticsModelDao.Properties.Click.gt(0), AdStatisticsModelDao.Properties.Shows.gt(0))
                .list();
        if (CommonUtils.isListEmpty(adStatisticsBeans)) {
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("appId", MyApp.getInstance().getAppId());
        params.put("data", gson.toJson(adStatisticsBeans));
        params.put("deviceId", DeviceUtils.getUniqueDevId(MyApp.getInstance()));
        Observable<JsonElement> observable = MyApp.getInstance().getApiInterface()
                .post(GeeksApis.URL_STATISTICS, params);
        RetrofitManager.subcribe(observable, new BaseRetrofitCallBack() {
            @Override
            protected void onRequestSuccess(String model) {
                JsonObject jsonObject = gson.fromJson(model, JsonObject.class);
                updateStatistics(jsonObject);
            }

            @Override
            protected void onRequestFail(String resMessage) {

            }

        });
    }

    private void updateStatistics(JsonObject jsonObject) {
        if (jsonObject.get("ad").isJsonNull()) {
            return;
        }
        JsonArray ids = jsonObject.getAsJsonArray("ad");
        DaoSession daoSession = MyApp.getInstance().getDaoSession();
        for (JsonElement element : ids) {
            AdStatisticsModel adStatisticsBean = new AdStatisticsModel();
            adStatisticsBean.setId(element.getAsJsonPrimitive().getAsString());
            adStatisticsBean.setClick(0);
            adStatisticsBean.setShows(0);
            adStatisticsBean.setTime(CommonUtils.getCurTime10());
            daoSession.getAdStatisticsModelDao().update(adStatisticsBean);
        }

    }
}
