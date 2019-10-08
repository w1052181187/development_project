package com.cmstop.jstt.wxapi;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cmstop.jstt.Const;
import com.cmstop.jstt.R;
import com.cmstop.jstt.event.PayResultEvent;
import com.cmstop.jstt.utils.Common;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Common.setTheme(this);
        setContentView(R.layout.activity_tencent_login);
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Const.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		EventBus.getDefault().post(new PayResultEvent(resp.errCode));
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
			}
		}, 500);
		
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("Demo");
//			builder.setMessage("支付回调信息 ： " + String.valueOf(resp.errCode));
//			builder.show();
//		}
	}
}