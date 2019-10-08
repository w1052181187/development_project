package com.bibinet.biunion.project.net.firing;

import android.content.Context;

import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.bean.CompanyInfoBean;
import com.bibinet.biunion.project.bean.CompanyUpImageBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.models.CollectModel;
import com.bibinet.biunion.project.models.CollectResultModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.UrlConfig;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.CollectEmit;
import com.bibinet.biunion.project.net.emit.CompanyInfoEmit;
import com.bibinet.biunion.project.net.emit.InformationDetailEmit;
import com.bibinet.biunion.project.net.emit.MyEmit;
import com.bibinet.biunion.project.net.ready.MyReady;
import com.bibinet.biunion.project.utils.RetrofitManager;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by bibinet on 2017-6-24.
 */

public class MyFiring extends BaseFiring {
    private MyReady myReady;
    private InformationDetailEmit informationDetailEmit;
    private MyEmit myEmit;
    private CollectEmit collectEmit;
    private CompanyInfoEmit companyInfoEmit;

    public MyFiring(InformationDetailEmit informationDetailEmit) {
        super(informationDetailEmit);
        this.informationDetailEmit = informationDetailEmit;
        this.myReady = new MyReady();
    }

    public MyFiring(CollectEmit collectEmit) {
        super(collectEmit);
        this.collectEmit = collectEmit;
        this.myReady = new MyReady();
    }

    public MyFiring(MyEmit myEmit, Context context) {
        super(context);
        this.myEmit = myEmit;
        this.myReady = new MyReady();
    }

    public MyFiring(CompanyInfoEmit companyInfoEmit) {
        super(companyInfoEmit);
        this.companyInfoEmit = companyInfoEmit;
        this.myReady = new MyReady();
    }

    public void setCollect(String userId, String projectId, String type) {
        myReady.setCollect(userId, projectId, type, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                informationDetailEmit.onSetCollectFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                informationDetailEmit.onSetCollectSuccess(model);
            }

        });
    }

    public void isCollect(String customerId, String code) {
        myReady.isCollect(customerId, code, new MyRetrofitResponseCallback<CollectResultModel>() {
            @Override
            protected void onRequestFail(String resMessage) {

            }

            @Override
            protected void onRequestSuccess(CollectResultModel model) {
                informationDetailEmit.onIsCollectSuccess(model);
            }

        });
    }

    public void deleteCollect(String userId, String relatedCode) {
        myReady.deleteCollect(userId, relatedCode, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                informationDetailEmit.onDeleteCollectFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                informationDetailEmit.onDeleteCollectSuccess(model);
            }

        });
    }

    public void getCollect(String userId, String type, int index) {
        myReady.getCollect(userId, type, index, new MyRetrofitResponseCallback<CollectModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                collectEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(CollectModel model) {
                collectEmit.loadMoreSuccess(model.getItem());
            }

        });
    }

    public void setUserIcon(String userId, String enterpriseId, String logofile) {
        waitDialog.open();
        myReady.setUserIcon(userId, enterpriseId, logofile, new BaseRetrofitCallBack() {

            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                waitDialog.close();
                try {
                    LoginResultBean loginInfo = gson.fromJson(s, LoginResultBean.class);
                    myEmit.onSetUserIconSuccess(loginInfo, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onRequestFail(String resMessage) {
                waitDialog.close();
                myEmit.onSetUserIconFail(resMessage);
            }
        });
    }

    public void setCompanyInfo(String userId, String companyId,String operateType, CompanyInfoBean companyInfoBean) {
        waitDialog.open();
        myReady.setCompanyInfo(userId, companyId, operateType, companyInfoBean, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                LoginResultBean updataInfo = gson.fromJson(s, LoginResultBean.class);
                companyInfoEmit.onSetCompanyInfoSuccess(updataInfo, s);
                waitDialog.close();
            }

            @Override
            protected void onRequestFail(String resMessage) {
                companyInfoEmit.onSetCompanyInfoFail(resMessage);
                waitDialog.close();
            }
        });
    }
    public void getCompanyInfo(String userId, BaseRetrofitCallBack callBack) {

        HashMap<String, String> requestParams = new HashMap<>();
        requestParams.put("userId", userId);
        Observable<JsonElement> observable = BiUnionApplication.getInstance().getApiInterface()
                .get(UrlConfig.MY_GET_COMPANY_INFO, requestParams);
        RetrofitManager.subcribe(observable, callBack);
    }

    public void revokeChangeCompanyInfo(String enterpriseId, BaseRetrofitCallBack callBack) {

        HashMap<String, String> requestParams = new HashMap<>();
        requestParams.put("enterpriseId", enterpriseId);
        Observable<JsonElement> observable = BiUnionApplication.getInstance().getApiInterface()
                .get(UrlConfig.MY_COMPANY_INFO_REVOKE_CHANGE, requestParams);
        RetrofitManager.subcribe(observable, callBack);
    }

    //上传营业执照
    public void setBusinessLicense(File file) {
        waitDialog.open();
        myReady.setBusinessLicense(file, new MyRetrofitResponseCallback<CompanyUpImageBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                waitDialog.close();
                companyInfoEmit.onSetBusinessLicenseFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(CompanyUpImageBean model) {
                companyInfoEmit.onSetBusinessLicenseSuccess(model);
                waitDialog.close();
            }

        });
    }
}
