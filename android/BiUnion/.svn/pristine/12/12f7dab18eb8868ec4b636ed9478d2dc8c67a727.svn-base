package com.bibinet.biunion.project.net.firing;

import android.util.Log;

import com.bibinet.biunion.project.bean.subscribe.PrivateInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.ProjectInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.PurchaseInfoDesignBean;
import com.bibinet.biunion.project.bean.subscribe.TenderInfoDesignBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.models.PrivateInfoResultModel;
import com.bibinet.biunion.project.models.ProjectInfoDesignModel;
import com.bibinet.biunion.project.models.PurchaseInfoDesignModel;
import com.bibinet.biunion.project.models.TenderInfoDesignModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.PrivateInfoDesignEmit;
import com.bibinet.biunion.project.net.emit.PrivateInfoShowEmit;
import com.bibinet.biunion.project.net.ready.PrivateInfoReady;
import com.google.gson.Gson;

import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.TYPE_PROJECT;
import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.TYPE_PURCHASE;
import static com.bibinet.biunion.project.ui.activity.subscribe.PrivateInfoShowActivity.TYPE_TENDER;


/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivateInfoFiring extends BaseFiring {
    private PrivateInfoReady privateInfoReady;
    private PrivateInfoDesignEmit privateInfoDesignEmit;
    private PrivateInfoShowEmit privateInfoShowEmit;

    private int type = -1;

    private MyRetrofitResponseCallback<BaseModel> xUtilsCallBack = new MyRetrofitResponseCallback<BaseModel>() {

        @Override
        protected void onRequestFail(String resMessage) {
            waitDialog.close();
            privateInfoDesignEmit.onSetDesignFail(resMessage);
        }

        @Override
        protected void onRequestSuccess(BaseModel model) {
            waitDialog.close();
            privateInfoDesignEmit.onSetDesignSuccess(model);
        }
    };

    public PrivateInfoFiring(PrivateInfoDesignEmit privateInfoDesignEmit) {
        super(privateInfoDesignEmit);
        this.privateInfoDesignEmit = privateInfoDesignEmit;
        this.privateInfoReady = new PrivateInfoReady();
    }

    public PrivateInfoFiring(PrivateInfoShowEmit privateInfoShowEmit) {
        super(privateInfoShowEmit);
        this.privateInfoShowEmit = privateInfoShowEmit;
        this.privateInfoReady = new PrivateInfoReady();
    }

    public void setPrivateInfoDesignData(String customerId, PrivateInfoDesignBean privateInfoDesignBean) {
        waitDialog.open();
        if (privateInfoDesignBean instanceof ProjectInfoDesignBean) {
            privateInfoReady.setProjectInfoDesignData(customerId, (ProjectInfoDesignBean) privateInfoDesignBean, xUtilsCallBack);
            type = TYPE_PROJECT;
        } else if (privateInfoDesignBean instanceof TenderInfoDesignBean) {
            privateInfoReady.setTenderInfoDesignData(customerId, (TenderInfoDesignBean) privateInfoDesignBean, xUtilsCallBack);
            type = TYPE_TENDER;
        } else if (privateInfoDesignBean instanceof PurchaseInfoDesignBean) {
            privateInfoReady.setPurchaseInfoDesignData(customerId, (PurchaseInfoDesignBean) privateInfoDesignBean, xUtilsCallBack);
            type = TYPE_PURCHASE;
        }

    }

    //信息类型(1:招标信息；2:项目信息；3：采购信息)
    public void getPrivateInfoDesignData(String customerId, final int type) {
        waitDialog.open();
        privateInfoReady.getPrivateInfoDesignData(customerId, type, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                waitDialog.close();
                Gson gson = new Gson();
                BaseModel bean = null;
                switch (type) {
                    case TYPE_PROJECT:
                        ProjectInfoDesignModel projectInfoDesignModel = gson.fromJson(s, ProjectInfoDesignModel.class);
                        if (projectInfoDesignModel.getMessageFlag().equals("true")) {
                            bean = projectInfoDesignModel;
                        }
                        break;
                    case TYPE_TENDER:
                        TenderInfoDesignModel tenderInfoDesignModel = gson.fromJson(s, TenderInfoDesignModel.class);
                        if (tenderInfoDesignModel.getMessageFlag().equals("true")) {
                            bean = tenderInfoDesignModel;
                        }
                        break;
                    case TYPE_PURCHASE:
                        PurchaseInfoDesignModel purchaseInfoDesignModel = gson.fromJson(s, PurchaseInfoDesignModel.class);
                        if (purchaseInfoDesignModel.getMessageFlag().equals("true")) {
                            bean = purchaseInfoDesignModel;
                        }
                        break;
                }
                privateInfoDesignEmit.onGetDesignSuccess(bean);
            }

            @Override
            protected void onRequestFail(String resMessage) {
                waitDialog.close();
                privateInfoDesignEmit.onGetDesignFail(resMessage);
            }
        });
    }

    public void getPrivateInfoShowData(int pageNum, String customerId, int type) {
        privateInfoReady.getPrivateInfoShowData(pageNum, customerId, type, new MyRetrofitResponseCallback<PrivateInfoResultModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                privateInfoShowEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(PrivateInfoResultModel model) {
                privateInfoShowEmit.loadMoreSuccess(model.getItem());
            }
        });
    }
}
