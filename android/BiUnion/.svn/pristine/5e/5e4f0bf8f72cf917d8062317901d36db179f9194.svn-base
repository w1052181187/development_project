package com.bibinet.biunion.project.net.emit;

import com.bibinet.biunion.project.bean.FastLoginResultBean;
import com.bibinet.biunion.project.bean.FastLoginVerifCodeBean;
import com.bibinet.biunion.project.bean.LoginResultBean;

/**
 * Created by bibinet on 2017-6-22.
 */

public interface LoginFastEmit {
    void onGetCodeSuccess(FastLoginVerifCodeBean verifCodeInfo);
    void onGetCodeFail(String s);

    void onLoginFastSuccess(LoginResultBean fastLoginInfo, String json);
    void onLoginFastFail(String s);
}
