package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-26.
 */

public class SettingReady extends BaseReady {
    public void submitAdvice(String content, String phone, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.ADVICE_SUBMIT, false, callBack,
                "content", content,   //提交信息
                "cellPhone", phone);  //手机号
    }
}
