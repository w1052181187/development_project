package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.bean.WriteTenderBookBean;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-22.
 */

public class WriteTenderBookReady extends BaseReady {
    public void setWriteTenderBook(String customerId, WriteTenderBookBean writeTenderBookBean, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.INN_WRITE_TENDER_BOOK_SET, false, callBack,
                "tenderSelection", writeTenderBookBean.getBookSelect().getId(),
                "projectType", writeTenderBookBean.getProjectType().getId(),
                "tenderMode", writeTenderBookBean.getTenderStyle().getId(),
                "tenderType", writeTenderBookBean.getBookType().getId(),
                "contact", writeTenderBookBean.getName(),
                "cellPhone", writeTenderBookBean.getPhone(),
                "email", writeTenderBookBean.getEmail(),
                "customerId", customerId);
    }

    public void getWriteTenderBook(String customerId, String pageNum, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.INN_WRITE_TENDER_BOOK_GET, false, callBack,
                "customerId", customerId,
                "pageNum", pageNum);
    }

    public void deleteWriteTenderBook(String objectId, BaseRetrofitCallBack callBack){
        baseReady(urlConfig.INN_WRITE_TENDER_BOOK_DELETE, false, callBack,
                "objectId", objectId);
    }
}
