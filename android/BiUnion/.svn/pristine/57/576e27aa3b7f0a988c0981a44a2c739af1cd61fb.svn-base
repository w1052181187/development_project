package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-26.
 */

public class SearchReady extends BaseReady{
    public void getHotWords(BaseRetrofitCallBack callBack){
        baseReady(urlConfig.SEARCH_HOT_WORDS, false, callBack);
    }

    public void getSearchData(int pageNumb, String content, String type, String area, String time, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.SEARCH_RESULT, true, callBack,
                "pageNum",String.valueOf(pageNumb),
                "messageLikes",content,
                "pageSize", NetConfig.PAGE_SIZE_SEARCH,
                "dataTypes",type,
                "provinceIds",area,
                "dateRange",time);
    }

    public void getYesterdayCount(String content, String type, String area, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.SEARCH_RESULT, true, callBack,
                "pageNum","1",
                "messageLikes",content,
                "pageSize", NetConfig.PAGE_SIZE_SEARCH,
                "dateRange","24",
                "dataTypes",type,
                "provinceIds",area);
    }

    public void getSearchDetail(String userId, String objectId, String code, String dataType, MyRetrofitResponseCallback callBack) {
        baseReady(urlConfig.SEARCH_DETAIL, true, callBack,
                "userId",userId,
                "objectId",objectId,
                "code",code,
                "dataType", dataType);
    }
}
