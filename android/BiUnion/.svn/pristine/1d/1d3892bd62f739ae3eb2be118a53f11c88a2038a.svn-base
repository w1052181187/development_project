package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.RetrofitPageCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-12.
 */

public class MediaMusicReady extends BaseReady {
    public void getMusicListData(String pageNum, RetrofitPageCallBack xPageCallBack) {
        baseReady(urlConfig.MEDIA_MUSIC_GET, true, xPageCallBack,
                "pageSize", NetConfig.PAGE_SIZE_MEDIO_MUSIC,
                "pageNum", pageNum);
    }

}
