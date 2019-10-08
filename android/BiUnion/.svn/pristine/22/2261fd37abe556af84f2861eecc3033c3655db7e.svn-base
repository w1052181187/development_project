package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-14.
 */

public class MoreProjectReady extends BaseReady {

    public void getMoreProjectInfo(int pageNum, String _type, String dateRange, String trad, String provinceId,
                                   BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MORE_PROJECT, false, callBack,
                "pageNum", String.valueOf(pageNum),
                "_type", _type,
                "dateRange", dateRange,
                "tradeFirst", trad,
                "provinceId", provinceId);
    }

    public void getMoreTenderInfo(int pageNum, String _type, String dateRange, String trad, String provinceId,
                                  BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MORE_TENDER, false, callBack,
                "pageNum", String.valueOf(pageNum),
                "_type", _type,
                "dateRange", dateRange,
                "tradeFirst", trad,
                "provinceId", provinceId);
    }

    public void getMorePurchaseInfo(int pageNum, String _type, String dateRange, String trad, String provinceId,
                                    BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MORE_PURCHASE, false, callBack,
                "pageNum", String.valueOf(pageNum),
                "_type", _type,
                "dateRange", dateRange,
                "tradeFirst", trad,
                "provinceId", provinceId);
    }

    public void getMorePpProjectInfo(int pageNum, String dateRange, String trad, String provinceId,
                                     BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MORE_PPP, false, callBack,
                "pageNum", String.valueOf(pageNum),
                "dateRange", dateRange,
                "tradeFirst", trad,
                "provinceId", provinceId);
    }

}
