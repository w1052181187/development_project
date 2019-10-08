package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.RetrofitPageCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-26.
 */

public class MagazineReady extends BaseReady {
    public void getMagazine(RetrofitPageCallBack xPageCallBack, String pageNum) {
        baseReady(urlConfig.INN_MAGAZINE_GET, true, xPageCallBack,
                "pageSize", NetConfig.PAGE_SIZE_MAGAZINE,
                "pageNum", pageNum
        );
    }
}
