package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.models.InformationModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.MoreProjectEmit;
import com.bibinet.biunion.project.net.ready.MoreProjectReady;

/**
 * Created by bibinet on 2017-6-14.
 */

public class MoreProjectFiring extends BaseFiring {
    private MoreProjectReady moreProjectReady;
    private MoreProjectEmit moreProjectEmit;


    public MoreProjectFiring(MoreProjectEmit moreProjectEmit) {
        super(moreProjectEmit);
        this.moreProjectEmit = moreProjectEmit;
        this.moreProjectReady = new MoreProjectReady();
    }

    public void getMoreProjectInfo(int pageNum, String type, String dateRange, String trad, String provinceId) {
        moreProjectReady.getMoreProjectInfo(pageNum, type, dateRange, trad, provinceId,
                new MyRetrofitResponseCallback<InformationModel>() {

                    @Override
                    protected void onRequestFail(String resMessage) {
                        waitDialog.close();
                        moreProjectEmit.loadMoreFail(resMessage);
                    }

                    @Override
                    protected void onRequestSuccess(InformationModel model) {
                        moreProjectEmit.loadMoreSuccess(model.getItems());
                    }

        });
    }

    public void getMoreTenderInfo(int pageNum, String type, String dateRange, String trad, String provinceId) {
        moreProjectReady.getMoreTenderInfo(pageNum, type, dateRange, trad, provinceId, new MyRetrofitResponseCallback<InformationModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                moreProjectEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(InformationModel model) {
                moreProjectEmit.loadMoreSuccess(model.getItems());
            }

        });
    }

    public void getMorePurchaseInfo(int pageNum, String type, String dateRange, String trad, String provinceId) {
        moreProjectReady.getMorePurchaseInfo(pageNum, type, dateRange, trad, provinceId,
                new MyRetrofitResponseCallback<InformationModel>() {
                    @Override
                    protected void onRequestFail(String resMessage) {
                        moreProjectEmit.loadMoreFail(resMessage);
                    }

                    @Override
                    protected void onRequestSuccess(InformationModel model) {
                        moreProjectEmit.loadMoreSuccess(model.getItems());
                    }

        });
    }

    public void getMorePpProjectInfo(int pageNum, String dateRange, String trad, String provinceId) {
        moreProjectReady.getMorePpProjectInfo(pageNum, dateRange, trad, provinceId, new MyRetrofitResponseCallback<InformationModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                moreProjectEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(InformationModel model) {
                moreProjectEmit.loadMoreSuccess(model.getItems());
            }

        });
    }

}
