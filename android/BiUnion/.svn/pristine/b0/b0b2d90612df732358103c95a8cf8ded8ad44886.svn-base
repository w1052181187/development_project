package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.models.MediaLiveListUrlmodel;
import com.bibinet.biunion.project.models.PageModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.RetrofitPageCallBack;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.MediaLiveEmit;
import com.bibinet.biunion.project.net.emit.MediaLivePushUrlEmit;
import com.bibinet.biunion.project.net.ready.MediaLiveReady;

/**
 * Created by bibinet on 2018-6-19.
 */

public class MediaLiveFiring extends BaseFiring {
    private MediaLivePushUrlEmit mediaLivePushEmit;
    private MediaLiveReady mediaLiveReady;
    private MediaLiveEmit mediaLiveEmit;

    public MediaLiveFiring(MediaLiveEmit mediaLiveEmit) {
        super(null);
        this.mediaLiveEmit = mediaLiveEmit;
        mediaLiveReady = new MediaLiveReady();
    }

    public MediaLiveFiring(MediaLivePushUrlEmit emit) {
        super(null);
        this.mediaLivePushEmit = emit;
        mediaLiveReady = new MediaLiveReady();
    }

    public void getRecordListData(int pageNum) {

        mediaLiveReady.getLiveRecordListData(pageNum + "",new RetrofitPageCallBack<MediaLiveListUrlmodel>() {

            @Override
            protected void onPageRequestFail(String resMessage) {
                mediaLiveEmit.loadMoreFail(resMessage);
            }

            @Override
            protected void onPageRequestSuccess(MediaLiveListUrlmodel model, PageModel.PageChildModel pageChildModel) {
                mediaLiveEmit.loadMoreSuccess(model.getAppLives(), pageChildModel);
            }
        });
    }

    public void getLiveListUrl() {

        mediaLiveReady.getLiveListUrl( new MyRetrofitResponseCallback<MediaLiveListUrlmodel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                mediaLivePushEmit.loadFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(MediaLiveListUrlmodel model) {
                mediaLivePushEmit.loadSuccess(model);
            }

        });

    }

}
