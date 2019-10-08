package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-23.
 */

public class TenderHelpReady extends BaseReady {
    public void setTenderHelp(String contact, String cellPhone, String content, String customerId, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.INN_TENDER_HELP_SET, false, callBack,
                "contact", contact,
                "cellPhone", cellPhone,
                "content", content,
                "customerId", customerId);
    }

    public void getTenderHelp(String customerId, int pageNumb, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.INN_TENDER_HELP_GET, false, callBack,
                "customerId", customerId,
                "pageNum", String.valueOf(pageNumb));
    }

    public void deleteTenderHelp(String objectId, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.INN_TENDER_HELP_DELETE, false, callBack,
                "objectId", objectId);
    }

}
