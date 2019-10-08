package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.bean.VerifCodeBean;
import com.bibinet.biunion.project.models.BaseModel;

/**
 * Created by bibinet on 2017-6-16.
 */

public interface RegisterEmit  {
    void onVerifGetSucess(VerifCodeBean verifInfo);
    void onVerifGetFailed(String errMessage);
    void onRegistSucess(BaseModel baseBean);
    void onRegistFailed(String s);
}
