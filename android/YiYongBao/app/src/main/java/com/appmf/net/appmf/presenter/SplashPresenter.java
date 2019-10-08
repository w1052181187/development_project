package com.appmf.net.appmf.presenter;

import android.text.TextUtils;

import com.appmf.net.appmf.app.Constants;
import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.BaseRetrofitCallBack;
import com.appmf.net.appmf.base.presenter.BasePresenter;
import com.appmf.net.appmf.bean.AdConfigBean;
import com.appmf.net.appmf.bean.AdContentBean;
import com.appmf.net.appmf.bean.AdDataBean;
import com.appmf.net.appmf.contract.SplashContract;
import com.appmf.net.appmf.dao.AdStatisticsModel;
import com.appmf.net.appmf.dao.AdStatisticsModelDao;
import com.appmf.net.appmf.dao.DaoSession;
import com.appmf.net.appmf.utils.CommonUtils;
import com.appmf.net.appmf.utils.RetrofitManager;
import com.appmf.net.appmf.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.appmf.net.appmf.app.Constants.ASSERTS_FILE_CONFIG_AD_NAME;


/**
 * @author wyg
 * @date 2018/12/27
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    private MyApp context;
    private SPUtils shareutil;

    private AdConfigBean adConfigBean;

    @Inject
    SplashPresenter() {
        this.context = MyApp.getInstance();
        shareutil = SPUtils.getInstance(context, SPUtils.APP_CONFIG);
    }

    public void handleAdConfigFile() {
        String data = shareutil.getString(SPUtils.KEY_AD_CONFIG,"");
        if (TextUtils.isEmpty(data)) {
            String mFileName = ASSERTS_FILE_CONFIG_AD_NAME;
            CommonUtils.handleFileByRxjava(context, mFileName, new CommonUtils.RxjavaCallback() {
                @Override
                public void success(String data) {
                    shareutil.putString(SPUtils.KEY_AD_CONFIG,data);
                    handleAdData(data);
                }
            });
        } else {
            handleAdData(data);
        }
    }

    /**
     * 根据广告配置文件进行相应的处理
     *
     * @param data
     */
    private void handleAdData(final String data) {
        Gson gson = new Gson();
        adConfigBean = gson.fromJson(data, AdConfigBean.class);
        context.setAdConfigBean(adConfigBean);
        if (CommonUtils.isNetworkConnected(context)) {
            String adData = shareutil.getString(SPUtils.KEY_AD_DATA,"");
            AdDataBean dataBean = gson.fromJson(adData, AdDataBean.class);
            String appId = context.getAppId();
            if (TextUtils.isEmpty(appId)) {
                handleCache(adConfigBean);
                return;
            }
            Map<String, String> params = new HashMap<>();
            if (dataBean != null  && dataBean.getAd() != null && dataBean.getAd().getId() != null) {
                params.put("id", dataBean.getAd().getId());
            }
            params.put("appId", appId);
            Observable<JsonElement> observer = MyApp.getInstance().getApiInterface().get(adConfigBean.getUrl(), params);
            RetrofitManager.subcribe(observer, new BaseRetrofitCallBack() {
                @Override
                protected void onRequestSuccess(String data) {
                    AdDataBean dataBean = new Gson().fromJson(data, AdDataBean.class);
                    shareutil.putString(SPUtils.KEY_AD_DATA, data);
                    handleAdShow(dataBean.getAd(), false);
                }

                @Override
                protected void onRequestFail(String resMessage) {
                    handleCache(adConfigBean);
                }

            });
        } else {
            handleCache(adConfigBean);
        }
    }

    /**
     * 根据广告缓存进行相应处理
     */
    void handleCache(AdConfigBean bean) {
        String data = shareutil.getString(SPUtils.KEY_AD_DATA,"");
        AdContentBean contentBean = null;
        if (TextUtils.isEmpty(data)) {
            contentBean = bean.getAd();
        } else {
            AdDataBean dataBean = new Gson().fromJson(data, AdDataBean.class);
            contentBean = dataBean.getAd();
        }
        if (contentBean != null && !TextUtils.isEmpty(contentBean.getId()) && isHasCache(contentBean.getFormat(),contentBean) ) {
            handleAdShow(contentBean, true);
        } else {
            mView.launchMain();
        }

    }

    private boolean isHasCache(int type, AdContentBean contentBean) {
        if (contentBean != null) {
            switch (type) {
                case 1:
                    ArrayList<String> images = (ArrayList<String>) contentBean.getImg();
                    if (images == null || images.isEmpty()) {
                        return true;
                    }
                    for(String url : images) {
                        final String name = new File(url).getName();
                        final String l = Constants.PATH_CACHE_AD_IMAGE + "/" + name;
                        if (new File(l).exists()) {
                            return true;
                        }
                    }
                    break;
                case 2:
                    String videoPath = contentBean.getVideo();
                    if (TextUtils.isEmpty(videoPath)) {
                        return false;
                    }
                    final String name = new File(videoPath).getName();
                    final String l = Constants.PATH_CACHE_AD_VIDEO + "/" + name;
                    if (new File(l).exists()) {
                        return true;
                    }
                    break;
            }
            return false;
        }
        return false;
    }

    void handleAdShow(AdContentBean bean, boolean isHasCache) {
        if (adConfigBean == null) {
            return;
        }
        if (bean != null) {
            long adEndTime = bean.getEnd_time();
            long curTime = CommonUtils.getCurTime10();
            if (curTime <= adEndTime) {
                mView.launchAd(bean, isHasCache);
                return;
            } else {
                deleteStatics();
            }
        }
        mView.launchMain();
    }

    /**
     * 广告过期，删除统计表中的广告
     */
    private void deleteStatics() {
        DaoSession daoSession = MyApp.getInstance().getDaoSession();
        List<AdStatisticsModel> adStatisticsBeans = daoSession.getAdStatisticsModelDao().queryBuilder()
                .where(AdStatisticsModelDao.Properties.Click.eq(0),
                        AdStatisticsModelDao.Properties.Shows.eq(0),
                        AdStatisticsModelDao.Properties.End_time.lt(CommonUtils.getCurTime10()))
                .list();
        if (!CommonUtils.isListEmpty(adStatisticsBeans)) {
            daoSession.delete(adStatisticsBeans);
        }
    }

//    public void handleSysConfigFile() {
//        CommonUtils.handleFileByRxjava(context, ASSERTS_FILE_CONFIG_SYS_NAME, new CommonUtils.RxjavaCallback() {
//            @Override
//            public void success(String data) {
//                context.setSysConfigData(data);
//                handleAdConfigFile();
//            }
//        });
//    }

}
