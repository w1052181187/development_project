package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.RetrofitPageCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-12.
 */

public class MediaVideoReady extends BaseReady {
    public void getVideoListData(String pageNum, RetrofitPageCallBack pageCallBack) {
        baseReady(urlConfig.MEDIA_VIDEO_GET, true, pageCallBack,
                "pageSize", NetConfig.PAGE_SIZE_MEDIO_VIDEO,
                "pageNum", pageNum);

//        HashMap<String, String> params = new HashMap<>();
//        params.put("pageSize", NetConfig.PAGE_SIZE_MEDIO_VIDEO);
//        params.put("pageNum", pageNum);
//        Observable<JsonElement> observable = BiUnionApplication.getInstance().getApiInterface().getElement(UrlConfig.MEDIA_VIDEO_GET, params);
//
//        RetrofitManager.subcribe(observable, pageCallBack);
    }

    public void getVideoTeacher(String code, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MEDIA_VIDEO_TEACHER_GET, true, callBack,
                "code", code);
    }

    public void getVideoChapter(String code, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.MEDIA_VIDEO_CHAPTER_GET, true, callBack,
                "code", code);
    }
}
