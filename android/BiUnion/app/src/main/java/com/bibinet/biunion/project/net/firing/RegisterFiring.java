package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.bean.RegisterBean;
import com.bibinet.biunion.project.bean.VerifCodeBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.RegisterEmit;
import com.bibinet.biunion.project.net.ready.RegisterReady;

/**
 * Created by bibinet on 2017-6-16.
 */

public class RegisterFiring extends BaseFiring {
    private RegisterReady registerReady;
    private RegisterEmit registerEmit;

    public RegisterFiring(RegisterEmit registerEmit) {
        super(registerEmit);
        this.registerEmit = registerEmit;
        this.registerReady = new RegisterReady();
    }

    public void getCode(String phone) {
        waitDialog.open();
        registerReady.getCode(phone, new MyRetrofitResponseCallback<VerifCodeBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                registerEmit.onVerifGetFailed(resMessage);
                waitDialog.close();
            }

            @Override
            protected void onRequestSuccess(VerifCodeBean model) {
                registerEmit.onVerifGetSucess(model);
                waitDialog.close();
            }
        });
    }

    public void register(RegisterBean registerBean) {
        waitDialog.open();
        registerReady.register(registerBean, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                registerEmit.onRegistFailed(resMessage);
                waitDialog.close();
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                registerEmit.onRegistSucess(model);
                waitDialog.close();
            }

        });

    }
}
