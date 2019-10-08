package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.bean.ForgetPasswordBean;
import com.bibinet.biunion.project.models.BaseModel;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.PasswordForgetConfirmEmit;
import com.bibinet.biunion.project.net.emit.PasswordForgetEmit;
import com.bibinet.biunion.project.net.emit.PasswordUpdateEmit;
import com.bibinet.biunion.project.net.ready.PasswordReady;

/**
 * Created by bibinet on 2017-9-8.
 */

public class PasswordFiring extends BaseFiring {
    private PasswordReady passwordReady;
    private PasswordUpdateEmit passwordUpdateEmit;
    private PasswordForgetEmit passwordForgetEmit;
    private PasswordForgetConfirmEmit passwordForgetConfirmEmit;

    public PasswordFiring(PasswordUpdateEmit passwordUpdateEmit) {
        super(passwordUpdateEmit);
        this.passwordUpdateEmit = passwordUpdateEmit;
        this.passwordReady = new PasswordReady();
    }

    public PasswordFiring(PasswordForgetEmit passwordForgetEmit) {
        super(passwordForgetEmit);
        this.passwordForgetEmit = passwordForgetEmit;
        passwordReady = new PasswordReady();
    }

    public PasswordFiring(PasswordForgetConfirmEmit passwordForgetConfirmEmit) {
        super(passwordForgetConfirmEmit);
        this.passwordForgetConfirmEmit = passwordForgetConfirmEmit;
        passwordReady = new PasswordReady();
    }

    public void getPasswordUpdateCode(String cellPhone) {
        passwordReady.getPasswordUpdateCode(cellPhone, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
            }

            @Override
            protected void onRequestFail(String resMessage) {

            }
        });
    }

    public void setPasswordUpdate(String cellphone, String newPassword, String smsCode) {
        waitDialog.open();
        passwordReady.setPasswordUpdate(cellphone, newPassword, smsCode, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                passwordUpdateEmit.onSetPasswordUpdateFail(resMessage);
                waitDialog.close();
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                passwordUpdateEmit.onSetPasswordUpdateSuccess(model);
                waitDialog.close();
            }

        });
    }

    public void getPasswordForgetCode(String cellPhone) {
        passwordReady.getPasswordForgetCode(cellPhone, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
            }

            @Override
            protected void onRequestFail(String resMessage) {

            }
        });
    }

    public void checkPasswordForgetCode(String cellPhone, String smsCode) {
        waitDialog.open();
        passwordReady.checkPasswordForgetCode(cellPhone, smsCode, new MyRetrofitResponseCallback<ForgetPasswordBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                passwordForgetEmit.onCheckPasswordForgetCodeFail(resMessage);
                waitDialog.close();
            }

            @Override
            protected void onRequestSuccess(ForgetPasswordBean model) {
                passwordForgetEmit.onCheckPasswordForgetCodeSuccess(model);
                waitDialog.close();
            }

        });
    }

    public void setPasswordForget(String cellphone, String newPassword) {
        passwordReady.setPasswordForget(cellphone, newPassword, new MyRetrofitResponseCallback<BaseModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                passwordForgetConfirmEmit.onSetPasswordForgetFail(resMessage);
            }

            @Override
            protected void onRequestSuccess(BaseModel model) {
                passwordForgetConfirmEmit.onSetPasswordForgetSuccess(model);
            }

        });
    }
}
