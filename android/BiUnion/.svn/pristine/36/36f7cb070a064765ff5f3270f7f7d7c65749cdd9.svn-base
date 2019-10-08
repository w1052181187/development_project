package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.models.BannerModel;
import com.bibinet.biunion.project.models.InformationModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.HomeEmit;
import com.bibinet.biunion.project.net.ready.HomeReady;

/**
 * Created by bibinet on 2017-9-18.
 */

public class HomeFiring extends BaseFiring {
    public static final int TYPE_NEW_PROJECT = 0;
    public static final int TYPE_NEW_TENDER = 1;
    public static final int TYPE_NEW_PURCHASE = 2;
    public static final int TYPE_HOT_TENDER = 3;

    private HomeEmit homeEmit;
    private HomeReady homeReady;

    public HomeFiring(HomeEmit homeEmit) {
        super(null);
        this.homeEmit = homeEmit;
        this.homeReady = new HomeReady();
    }

    public void getBannerData() {
        homeReady.getBannerUrl(new MyRetrofitResponseCallback<BannerModel>() {

            @Override
            protected void onRequestFail(String resMessage) {
                homeEmit.onLoadBannerFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(BannerModel model) {
                homeEmit.onLoadBannerSuccess(model.getAppBannerList());
            }

        });
    }

    public void getHomeItemInfoData(String provinceid, int type) {
        switch (type) {
            case TYPE_NEW_PROJECT:
                homeReady.getFiveProjectInfoData(provinceid, new HomeItemDataBack().setType(TYPE_NEW_PROJECT));
                break;
            case TYPE_NEW_TENDER:
                homeReady.getFiveTenderProjcetInfoData(provinceid, new HomeItemDataBack().setType(TYPE_NEW_TENDER));
                break;
            case TYPE_NEW_PURCHASE:
                homeReady.getFiveBuyProjcetInfoData(provinceid, new HomeItemDataBack().setType(TYPE_NEW_PURCHASE));
                break;
            case TYPE_HOT_TENDER:
                homeReady.getHomeHotTenderInfoData(new HomeItemDataBack().setType(TYPE_HOT_TENDER));
                break;
        }
    }

    private class HomeItemDataBack extends MyRetrofitResponseCallback<InformationModel> {
        private int type = TYPE_NEW_PROJECT;

        public MyRetrofitResponseCallback setType(int type) {
            this.type = type;
            return this;
        }

        @Override
        protected void onRequestFail(String resMessage) {
            homeEmit.onLoadItemDataFail(resMessage);
        }

        @Override
        protected void onRequestSuccess(InformationModel model) {
            homeEmit.onLoadItemDataSuccess(model.getItems(), type);
        }
    }
}
