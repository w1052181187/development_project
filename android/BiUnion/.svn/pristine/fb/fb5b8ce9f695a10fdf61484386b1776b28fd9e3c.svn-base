package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.event.LoginSuccessEvent;
import com.bibinet.biunion.project.net.emit.LoginEmit;
import com.bibinet.biunion.project.net.firing.LoginFiring;
import com.bibinet.biunion.project.ui.expand.TitleActivity;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.bibinet.biunion.project.utils.ToastUtils;
import com.bibinet.biunion.project.utils.ViewCheckUtils;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-7.
 */

public class LoginActivity extends TitleActivity implements LoginEmit, View.OnClickListener {

    @BindView(R.id.inputPhoneNumber)
    EditText inputPhoneNumber;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.act_login_show_password)
    View showPasswrod;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.fastLogin)
    Button fastLogin;

    @Override
    protected int getTitleLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleString() {
        return R.string.str_login;
    }

    @Override
    protected void onTitleCreate(@Nullable Bundle savedInstanceState) {
        setRightBtn(R.string.str_register, -1, this);
        ViewCheckUtils.inputConfirm(btnLogin, inputPhoneNumber, inputPassword);
        //展示密码
        ViewCheckUtils.showPassword(showPasswrod, inputPassword);
        String phone = getIntent().getStringExtra("phone");
        if (!TextUtils.isEmpty(phone)) {
            inputPhoneNumber.setText(phone);
//            inputPassword.requestFocus();
            inputPassword.setText("");
        } else {
//            inputPhoneNumber.requestFocus();
        }
    }

    @OnClick({R.id.btn_login, R.id.fastLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.fastLogin:
                startActivityForResult(new Intent(LoginActivity.this, LoginFastActivity.class), LoginFastActivity.requestCode);
                break;
        }
        Common.hideInput(this);
    }

    private void doLogin() {

        String phoneNumber = inputPhoneNumber.getText().toString().trim();
        String phonePassword = inputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber) && TextUtils.isEmpty(phonePassword)) {
            ToastUtils.showShort("手机号或者密码为空");
        } else if (!PhoneNumberUtils.isMobileNumber(phoneNumber)) {
            ToastUtils.showShort("手机号格式不正确");
        } else {
            LoginFiring loginFiring = new LoginFiring(this);
            loginFiring.login(phoneNumber, phonePassword);
        }
    }

    @Override
    public void onLoginSuccess(String loginString) {
        Gson gson = new Gson();
        LoginResultBean loginResultInfo = gson.fromJson(loginString, LoginResultBean.class);
        String reslutCode = loginResultInfo.getResCode();
        switch (Integer.parseInt(reslutCode)) {
            case 0000:
                ToastUtils.showShort("登录成功");
                LoginSuccessEvent event = new LoginSuccessEvent(loginResultInfo.getUser());
                Constants.loginresultInfo = loginResultInfo;
                SharedPresUtils sharedPresUtils = SharedPresUtils.getsSharedPresUtils(SharedPresUtils.DATA_CONFIG);
                sharedPresUtils.putString("loginResultData", loginString);
                MobclickAgent.onProfileSignIn(loginResultInfo.getUser().getUserId());//账号登录统计
                EventBus.getDefault().post(event);
                finish();
                break;
            default:
                Toast.makeText(this, loginResultInfo.getResMessage(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onLoginFail(String msg) {
        ToastUtils.showShort(msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RegisterActivity.requestCode && resultCode == RegisterActivity.resultCode){
            //带回注册成功的手机号
            String phone = data.getStringExtra("phone");
            inputPhoneNumber.setText(phone);
            inputPassword.requestFocus();
            inputPassword.setText("");
        }
        if(requestCode == LoginFastActivity.requestCode && resultCode == LoginFastActivity.resultCode){
            //快速注册成功回调
            finish();
        }
    }

    @OnClick(R.id.act_login_forget_password)
    void forgetPassword(){
        startActivity(new Intent(this, PasswordForgetActivity.class));
        Common.hideInput(this);
    }

    //注册
    @Override
    public void onClick(View view) {
        //注册成功返回结果
        startActivityForResult(new Intent(this, RegisterActivity.class), RegisterActivity.requestCode);
        Common.hideInput(this);
    }

}
