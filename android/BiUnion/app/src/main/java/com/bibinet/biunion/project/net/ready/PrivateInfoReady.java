package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.bean.subscribe.ProjectInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.PurchaseInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.TenderInfoDesignBean;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.TYPE_PROJECT;
import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.TYPE_PURCHASE;
import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.TYPE_TENDER;

/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivateInfoReady extends BaseReady {

    //项目信息
    public void setProjectInfoDesignData(String customerId, ProjectInfoDesignBean projectInfoDesignBean,
                                         BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PRIVATE_INFO_SET_DESIGN, false, callBack,
                "customerId", customerId,
                "infoType", String.valueOf(TYPE_PROJECT),
                "projectType", projectInfoDesignBean.getProjectType(),  //工程货物服务
                "infoSubType", projectInfoDesignBean.getTypeSelect(),   //类型选择
                "tradeFirst", projectInfoDesignBean.getIndustry(),  //行业
                "dateRange", projectInfoDesignBean.getPubdate(),  //日期
                "projectStage", projectInfoDesignBean.getProjectStage(),  //项目阶段
                "regionCode", projectInfoDesignBean.getArea());  //地区
    }

    //招标信息
    public void setTenderInfoDesignData(String customerId, TenderInfoDesignBean tenderInfoDesignBean, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PRIVATE_INFO_SET_DESIGN, false, callBack,
                "customerId", customerId,
                "infoType", String.valueOf(TYPE_TENDER),
                "projectType", tenderInfoDesignBean.getProjectType(),  //工程货物服务
                "infoSubType", tenderInfoDesignBean.getTypeSelect(),   //类型选择
                "tradeFirst", tenderInfoDesignBean.getIndustry(),  //行业
                "dateRange", tenderInfoDesignBean.getPubdate(),  //日期
                "regionCode", tenderInfoDesignBean.getArea());  //地区

    }

    //采购信息
    public void setPurchaseInfoDesignData(String customerId, PurchaseInfoDesignBean purchaseInfoDesignBean, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PRIVATE_INFO_SET_DESIGN, false, callBack,
                "customerId", customerId,
                "infoType", String.valueOf(TYPE_PURCHASE),
                "projectType", purchaseInfoDesignBean.getProjectType(),  //工程货物服务
                "infoSubType", purchaseInfoDesignBean.getTypeSelect(),   //类型选择
                "tradeFirst", purchaseInfoDesignBean.getIndustry(),  //行业
                "dateRange", purchaseInfoDesignBean.getPubdate(),  //日期
                "regionCode", purchaseInfoDesignBean.getArea());  //地区
    }


    public void getPrivateInfoDesignData(String customerId, int infoType, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PRIVATE_INFO_GET_DESIGN, false, callBack,
                "customerId", customerId,
                "infoType", String.valueOf(infoType));
    }


    public void getPrivateInfoShowData(int pageNum, String customerId, int type, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PRIVATE_INFO_GET_DATA, false, callBack,
                "pageNum", String.valueOf(pageNum),
                "infoType", String.valueOf(type),
                "customerId", customerId);
    }
}
