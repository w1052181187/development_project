package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.models.MediaLiveListUrlmodel;

public interface MediaLivePushUrlEmit {

    //读取更多 分页
    void loadSuccess(MediaLiveListUrlmodel model);
    void loadFail(String s);
}
