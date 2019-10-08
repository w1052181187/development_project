package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.models.CollectResultModel;

/**
 * Created by bibinet on 2017-6-24.
 */

public interface InformationDetailEmit {
    void onIsCollectSuccess(CollectResultModel collectResultModel);
    void onSetCollectSuccess(BaseModel baseModel);
    void onSetCollectFail(String msg);
    void onDeleteCollectSuccess(BaseModel baseModel);
    void onDeleteCollectFail(String msg);
}
