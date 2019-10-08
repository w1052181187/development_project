package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.NetConfig;
import com.bibinet.biunion.project.net.RetrofitPageCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.RetrofitManager;
import com.google.gson.JsonElement;

import io.reactivex.Observable;

/**
 * Created by bibinet on 2018-6-22.
 */

public class MediaLiveReady extends BaseReady {

    public void getLiveRecordListData(String pageNum, RetrofitPageCallBack xPageCallBack) {
        baseReady(urlConfig.MEDIA_LIVE_URL_RECORD_LIST, true, xPageCallBack,
                "pageSize", NetConfig.PAGE_SIZE_MEDIO_LIVE,
                "pageNum", pageNum);
    }

    public void getLiveListUrl(MyRetrofitResponseCallback callBack) {
        Observable<JsonElement> observable =
                BiUnionApplication.getInstance().getApiInterface().get(urlConfig.MEDIA_LIVE_PUSH_URL_GET);

        RetrofitManager.subcribe(observable, callBack);
    }

    /**
     * 获取直播播放地址
     * @param objectId
     * @param userId
     * @param callBack
     */
    public void getLivePlayUrl(String objectId, String userId, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        params.put("userId", userId);
//        Observable<JsonElement> observable =
//                BiUnionApplication.getInstance().getApiInterface().get(urlConfig.MEDIA_LIVE_PLAY_URL_GET, params);
//
//        RetrofitManager.subcribe(observable, callBack);
        baseReady(urlConfig.MEDIA_LIVE_PLAY_URL_GET, true, callBack,
                "objectId", objectId,"userId", userId);
    }

    /**
     * 直播完成
     * @param objectId
     * @param callBack
     */
    public void finishLive(String objectId, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        Observable<JsonElement> observable =
//                BiUnionApplication.getInstance().getApiInterface().get(urlConfig.MEDIA_LIVE_FINISH_URL_GET, params);
//
//        RetrofitManager.subcribe(observable, callBack);
        baseReady(urlConfig.MEDIA_LIVE_FINISH_URL_GET, true, callBack,
                "objectId", objectId);
    }

    /**
     * 直播配置
     * @param cover
     * @param title
     * @param userId
     * @param pushUrl
     * @param callBack
     */
    public void addLivePushUrl(String cover, String title, String userId, String pushUrl, BaseRetrofitCallBack callBack) {

//        HashMap<String,String> params = new HashMap<>();
//        params.put("liveCover", ImageUtils.getImageStr(cover));
//        params.put("liveName", title);
//        params.put("userId", userId);
//        params.put("plugUrl", pushUrl);
//        Observable<JsonElement> observable =
//                BiUnionApplication.getInstance().getApiInterface().get(urlConfig.MEDIA_LIVE_URL_ADD, params);
//        RetrofitManager.subcribe(observable, callBack);
        baseReady(urlConfig.MEDIA_LIVE_URL_ADD, true, callBack,
                "liveCover", ImageUtils.getImageStr(cover)
                ,"liveName", title
                ,"userId", userId
                ,"plugUrl", pushUrl);

    }

    public void checkLiveName(String title, BaseRetrofitCallBack callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("liveName", title);
//        Observable<JsonElement> observable =
//                BiUnionApplication.getInstance().getApiInterface().get(urlConfig.MEDIA_LIVE_URL_CHECK_LIVE_NAME, params);
//        RetrofitManager.subcribe(observable, callBack);
        baseReady(urlConfig.MEDIA_LIVE_URL_CHECK_LIVE_NAME, true, callBack
                ,"liveName", title);
    }


    /**
     * 添加录播配置
     * @param objectId
     * @param streamName
     * @param callBack
     */
    public void addLiveRecord(String objectId, String streamName, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        params.put("streamName", streamName);
//        Observable<JsonElement> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_RECORD_ADD, params);
//        RetrofitManager.subcribe(observable, callBack);

        baseReady(urlConfig.MEDIA_LIVE_URL_RECORD_ADD, true, callBack
                ,"objectId", objectId
                ,"streamName", streamName);
    }

    public void startOrStopLiveRecord(String commond, String streamName, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("command", commond);
//        params.put("streamName", streamName);
//        Observable<JsonElement> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_RECORD_START, params);
//        RetrofitManager.subcribe(observable, callBack);

        baseReady(urlConfig.MEDIA_LIVE_URL_RECORD_START, true, callBack
                ,"command", commond
                ,"streamName", streamName);
    }

    public void leaveLivePlay(String objectId,String userId, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        params.put("userId", userId);
//        Observable<JsonElement> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_LEAVE_PLAY, params);
//        RetrofitManager.subcribe(observable, callBack);

        baseReady(urlConfig.MEDIA_LIVE_URL_LEAVE_PLAY, true, callBack
                ,"objectId", objectId
                ,"userId", userId);
    }

    /**
     *
     * @param userName
     * @param userId
     * @param content
     * @param liveId
     * @param flag  0 评论  1 查询
     * @param callBack
     */
    public void handleLiveComment(String userName,String userId, String content, String liveId, String flag,
                                  MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("userName", userName);
//        params.put("userId", userId);
//        params.put("content", content);
//        params.put("liveId", liveId);
//        params.put("flag", flag);
//        Observable<JsonElement> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_COMMENT, params);
//        RetrofitManager.subcribe(observable, callBack);

        baseReady(urlConfig.MEDIA_LIVE_URL_COMMENT, true, callBack
                ,"userName", userName
                ,"userId", userId
                ,"content", content
                ,"liveId", liveId
                ,"flag", flag);
    }

    public void getOnlineUser(String objectId, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        Observable<JsonElement> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_ONLINE_USER, params);
//        RetrofitManager.subcribe(observable, callBack);
        baseReady(urlConfig.MEDIA_LIVE_URL_ONLINE_USER, true, callBack
                ,"objectId", objectId);
    }

    public void checkLiveStatus(String objectId, BaseRetrofitCallBack callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        Observable<JsonElement> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_CHECK_LIVE_STATUS, params);
//        RetrofitManager.subcribe(observable, callBack);

        baseReady(urlConfig.MEDIA_LIVE_URL_CHECK_LIVE_STATUS, true, callBack
                ,"objectId", objectId);
    }
    public void getRecordPlayUrl(String objectId, MyRetrofitResponseCallback callBack) {
//        HashMap<String,String> params = new HashMap<>();
//        params.put("objectId", objectId);
//        Observable<String> observable =BiUnionApplication.getInstance().
//                getApiInterface().get(urlConfig.MEDIA_LIVE_URL_RECORD_PLAY_GET, params);
//        RetrofitManager.subcribe(observable, callBack);

        baseReady(urlConfig.MEDIA_LIVE_URL_RECORD_PLAY_GET, true, callBack
                ,"objectId", objectId);
    }

}
