package com.bibinet.biunion.project.net.firing;

import com.bibinet.biunion.project.bean.FastLoginVerifCodeBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.net.BaseRetrofitCallBack;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.base.BaseFiring;
import com.bibinet.biunion.project.net.emit.LoginEmit;
import com.bibinet.biunion.project.net.emit.LoginFastEmit;
import com.bibinet.biunion.project.net.ready.LoginReady;

/**
 * Created by bibinet on 2017-6-12.
 */

public class LoginFiring extends BaseFiring {
    private LoginReady loginReady;
    private LoginEmit loginEmit;
    private LoginFastEmit loginFastEmit;

    public LoginFiring(LoginEmit loginEmit) {
        super(loginEmit);
        this.loginEmit = loginEmit;
        loginReady = new LoginReady();
    }

    public LoginFiring(LoginFastEmit loginFastEmit) {
        super(loginFastEmit);
        this.loginFastEmit = loginFastEmit;
        this.loginReady = new LoginReady();
    }

    public void login(String account, String password) {
        toastUtils.setText("登录中，请稍后...");
        toastUtils.open();
        loginReady.login(account, password, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                loginEmit.onLoginSuccess(s);
                toastUtils.close();
            }

            @Override
            protected void onRequestFail(String resMessage) {
                loginEmit.onLoginFail(resMessage);
                toastUtils.close();
            }

        });
    }


    public void getCode(String phone) {
        toastUtils.setText("获取验证码中，请稍后...");
        toastUtils.open();
        loginReady.getCode(phone, new MyRetrofitResponseCallback<FastLoginVerifCodeBean>() {
            @Override
            protected void onRequestFail(String resMessage) {
                loginFastEmit.onGetCodeFail(resMessage);
                toastUtils.close();
            }

            @Override
            protected void onRequestSuccess(FastLoginVerifCodeBean model) {

                loginFastEmit.onGetCodeSuccess(model);
                toastUtils.close();
            }
        });
    }

    public void loginFast(String phone, String verifCode) {
        toastUtils.setText("登录中，请稍后...");
        toastUtils.open();
        loginReady.loginFast(phone, verifCode, new BaseRetrofitCallBack() {
            @Override
            public void onRequestSuccess(String s) {
                super.onRequestSuccess(s);
                LoginResultBean fastLoginInfo = gson.fromJson(s, LoginResultBean.class);
                loginFastEmit.onLoginFastSuccess(fastLoginInfo, s);
                toastUtils.close();
            }

            @Override
            protected void onRequestFail(String resMessage) {
                loginFastEmit.onLoginFastFail(resMessage);
                toastUtils.close();
            }

        });
    }
}
