package com.bibinet.biunion.project.net.ready;

import com.bibinet.biunion.project.bean.RegisterBean;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.base.BaseReady;

/**
 * Created by bibinet on 2017-6-16.
 */

public class RegisterReady extends BaseReady {
    public void getCode(String phone, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.REGISTER_CODE, false, callBack,
                "cellPhone", phone);
    }

    public void register(RegisterBean registerBean, BaseRetrofitCallBack callBack) {
        baseReady(urlConfig.REGISTER, false, callBack,
                "enterpriseName", registerBean.getCompany(),
                "userName", registerBean.getName(),
                "email", registerBean.getEmail(),
                "job", registerBean.getPosition(),
                "cellPhone", registerBean.getPhone(),
                "password", registerBean.getPassword(),
                "checkCode", registerBean.getCode());
    }
}
