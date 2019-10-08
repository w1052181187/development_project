package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-9-8.
 */

public class PasswordReady extends BaseReady {
    public void getPasswordUpdateCode(String cellphone, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PASSWORD_UPDATE_CODE, false, callBack,
                "cellphone", cellphone);
    }

    public void setPasswordUpdate(String cellphone, String newPassword, String smsCode, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PASSWORD_UPDATE, false, callBack,
                "cellphone", cellphone,
                "newPassword", newPassword,
                "smsCode", smsCode);
    }

    public void getPasswordForgetCode(String cellPhone, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PASSWORD_FORGET_CODE, false, callBack,
                "cellphone", cellPhone);
    }

    public void checkPasswordForgetCode(String cellPhone, String smsCode, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PASSWORD_FORGET_CODE_CHECK, false, callBack,
                "cellphone", cellPhone,
                "smsCode", smsCode);
    }

    public void setPasswordForget(String cellphone, String newPassword, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.PASSWORD_FORGET, false, callBack,
                "cellphone", cellphone,
                "newPassword", newPassword);
    }
}
