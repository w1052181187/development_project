package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.WriteTenderBookBean;
import com.bibinet.biunion.project.bean.WriteTenderBookHistoryBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.WriteTenderBookEmit;
import com.bibinet.biunion.project.net.emit.WriteTenderHistoryEmit;
import com.bibinet.biunion.project.net.ready.WriteTenderBookReady;

/**
 * Created by bibinet on 2017-6-22.
 */

public class WriteTenderBookFiring extends BaseFiring {
    private WriteTenderBookEmit writeTenderBookEmit;
    private WriteTenderBookReady writeTenderBookReady;
    private WriteTenderHistoryEmit writeTenderHistoryEmit;

    public WriteTenderBookFiring(WriteTenderBookEmit writeTenderBookEmit) {
        super(writeTenderBookEmit);
        this.writeTenderBookEmit = writeTenderBookEmit;
        this.writeTenderBookReady = new WriteTenderBookReady();
    }

    public WriteTenderBookFiring(WriteTenderHistoryEmit writeTenderHistoryEmit) {
        super(writeTenderHistoryEmit);
        this.writeTenderHistoryEmit = writeTenderHistoryEmit;
        this.writeTenderBookReady = new WriteTenderBookReady();
    }

    public void setWriteTenderBook(String customerId, WriteTenderBookBean writeTenderBookBean) {
        waitDialog.open();
        writeTenderBookReady.setWriteTenderBook(customerId, writeTenderBookBean, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                waitDialog.close();
                writeTenderBookEmit.onSetWriteTenderBookFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                waitDialog.close();
                writeTenderBookEmit.onSetWriteTenderBookSuccess(model);
            }

        });
    }

    public void getWriteTenderBook(String customerId,String pageNum){
        writeTenderBookReady.getWriteTenderBook(customerId,pageNum ,new MyRetrofitResponseCallback<WriteTenderBookHistoryBean>(){
            @Override
            protected void onRequestFail(String resMessage) {
                writeTenderHistoryEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(WriteTenderBookHistoryBean model) {
                writeTenderHistoryEmit.loadMoreSuccess(model.getItem());
            }
        });
    }

    public void deleteWriteTenderBook(String objectId){
        waitDialog.open();
        writeTenderBookReady.deleteWriteTenderBook(objectId, new BaseRetrofitCallBack(){
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                DeleteHistoryBean bean = gson.fromJson(s, DeleteHistoryBean.class);
                writeTenderHistoryEmit.onDeleteWriteTenderBookSuccess(bean);
                waitDialog.close();
            }

            @Override
            protected void onRequestFail(String resMessage) {
                writeTenderHistoryEmit.onDeleteWriteTenderBookFail(resMessage);
                waitDialog.close();
            }
        });
    }

}
