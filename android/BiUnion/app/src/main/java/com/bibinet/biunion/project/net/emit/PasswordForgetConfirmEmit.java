package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.models.BaseModel;

/**
 * Created by bibinet on 2017-9-7.
 */

public interface PasswordForgetConfirmEmit {
    void onSetPasswordForgetSuccess(BaseModel resetInfo);
    void onSetPasswordForgetFail(String msg);
}
