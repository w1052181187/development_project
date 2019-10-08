package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-23.
 */

public class DailyUpdatesCountReady extends BaseReady {
    public void getTotalCount(String date, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_DAY_COUNT, true, callback,
                "publishDateStart", date);
    }

    public void getPartCount(String type, String date, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.HOME_DAY_COUNT, true, callback,
                "dataTypes", type,
                "publishDateStart", date);
    }
}
