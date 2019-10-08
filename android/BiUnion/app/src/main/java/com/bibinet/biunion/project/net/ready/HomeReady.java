package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-9-18.
 */

public class HomeReady extends BaseReady {

    public void getBannerUrl(MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_BANNER_GET, true, callback);
    }

    public void getFiveProjectInfoData(String provinceId, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_NEW_PROJECT, true, callback,
                "pageNum", "1",
                "provinceId", provinceId);
    }

    public void getFiveTenderProjcetInfoData(String provinceId, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_NEW_TENDER, true, callback,
                "pageNum", "1",
                "provinceId", provinceId);
    }

    public void getFiveBuyProjcetInfoData(String provinceId, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_NEW_PURCHASE, true, callback,
                "pageNum", "1",
                "provinceId", provinceId);
    }

    public void getHomeHotTenderInfoData(MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_HOT_TENDER, true, callback,
                "pageNum", "1",
                "_type", "5",
                "pageSize", NetConfig.PAGE_SIZE_HOME_HOT_TENDER,
                "dateRange", "0");
    }
}
