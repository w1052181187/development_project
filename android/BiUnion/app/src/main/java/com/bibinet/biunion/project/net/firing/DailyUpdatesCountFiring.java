package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.models.DailyUpdatesModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.DailyUpdatesCountEmit;
import com.bibinet.biunion.project.net.ready.DailyUpdatesCountReady;

/**
 * Created by bibinet on 2017-6-1.
 */
public class DailyUpdatesCountFiring extends BaseFiring {
    private DailyUpdatesCountReady dailyUpdatesCountReady;
    private DailyUpdatesCountEmit dailyUpdatesCountEmit;

    private MyRetrofitResponseCallback xUtilsCallBack = new MyRetrofitResponseCallback<DailyUpdatesModel>() {
        @Override
        protected void onRequestFail(String resMessage) {
            dailyUpdatesCountEmit.onGetCountFail(resMessage);
        }

        @Override
        protected void onRequestSuccess(DailyUpdatesModel model) {
            dailyUpdatesCountEmit.onGetCountSuccess(model);
        }

    };

    public DailyUpdatesCountFiring(DailyUpdatesCountEmit dailyUpdatesCountEmit) {
        super(null);
        this.dailyUpdatesCountEmit = dailyUpdatesCountEmit;
        this.dailyUpdatesCountReady = new DailyUpdatesCountReady();
    }

    public void getTotalCount(String date) {
        dailyUpdatesCountReady.getTotalCount(date, xUtilsCallBack);
    }

    public void getPartCount(String type, String date) {
        dailyUpdatesCountReady.getPartCount(type, date, xUtilsCallBack);
    }

}
