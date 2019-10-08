package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.HelpTenderHistoryReusltBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.TenderHelpEmit;
import com.bibinet.biunion.project.net.emit.TenderHelpHistoryEmit;
import com.bibinet.biunion.project.net.ready.TenderHelpReady;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-23.
 */

public class TenderHelpFiring extends BaseFiring {
    private TenderHelpReady tenderHelpReady;
    private TenderHelpEmit tenderHelpEmit;
    private TenderHelpHistoryEmit tenderHelpHistiryEmit;

    public TenderHelpFiring(TenderHelpEmit tenderHelpEmit) {
        super(tenderHelpEmit);
        this.tenderHelpEmit = tenderHelpEmit;
        this.tenderHelpReady = new TenderHelpReady();
    }

    public TenderHelpFiring(TenderHelpHistoryEmit tenderHelpHistiryEmit) {
        super(tenderHelpHistiryEmit);
        this.tenderHelpHistiryEmit = tenderHelpHistiryEmit;
        this.tenderHelpReady = new TenderHelpReady();
    }

    public void setTenderHelp(String contact, String cellPhone, String content, String customerId) {
        waitDialog.open();
        tenderHelpReady.setTenderHelp(contact, cellPhone, content, customerId, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                tenderHelpEmit.onSetTenderHelpFail(resMessage);
                waitDialog.close();
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                tenderHelpEmit.onSetTenderHelpSuccess(model);
                waitDialog.close();
            }
        });
    }

    public void getTenderHelp(String userId, int pageNumb) {
        tenderHelpReady.getTenderHelp(userId, pageNumb, new MyRetrofitResponseCallback<HelpTenderHistoryReusltBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                tenderHelpHistiryEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(HelpTenderHistoryReusltBean model) {
                tenderHelpHistiryEmit.loadMoreSuccess(model.getItem());
            }
        });
    }

    public void deleteTenderHelp(String objectId){
        waitDialog.open();
        tenderHelpReady.deleteTenderHelp(objectId, new BaseRetrofitCallBack(){
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                Gson gson=new Gson();
                DeleteHistoryBean bean = gson.fromJson(s, DeleteHistoryBean.class);
                tenderHelpHistiryEmit.onDeleteTenderHelpSuccess(bean);
                waitDialog.close();
            }


            @Override
            protected void onRequestFail(String resMessage) {
                tenderHelpHistiryEmit.onDeleteTenderHelpFail(resMessage);
                waitDialog.close();
            }
        });
    }
}
