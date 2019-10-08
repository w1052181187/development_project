package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-12.
 */

public class LoginReady extends BaseReady {
    public void login(String account, String password, BaseRetrofitCallBack callback) {
        baseReady(urlConfig.LOGIN, false, callback,
                "account", account,
                "password", password);
    }

    public void getCode(String phone, MyRetrofitResponseCallback callback) {
        baseReady(urlConfig.LOGIN_FAST_CODE, false, callback,
                "cellPhone", phone);
    }

    public void loginFast(String phone, String code, BaseRetrofitCallBack callback) {
        baseReady(urlConfig.LOGIN_FAST, false, callback,
                "cellPhone", phone,
                "checkCode", code);
    }

}
