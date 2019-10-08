package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.models.BaseModel;

/**
 * Created by bibinet on 2017-6-23.
 */

public interface TenderHelpEmit {
    void onSetTenderHelpSuccess(BaseModel bean);
    void onSetTenderHelpFail(String msg);
}
