package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

import com.chengning.common.base.BaseActivity;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.widget.TitleBar;

public class UseProtoActivity extends BaseActivity {

	private TitleBar mTitleBar;
	private WebView mWebView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_useproto);
		super.onCreate(savedInstanceState);
	}

	@Override
	public Activity getActivity() {
		return UseProtoActivity.this;
	}

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getActivity(), true);
		mTitleBar.setTitle("注册协议须知");
		mTitleBar.showDefaultBack();

		mWebView = (WebView) findViewById(R.id.useproto_webView);
	}

	@Override
	public void initDatas() {

		mWebView.loadUrl(JUrl.URL_USEPROTO);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
	}

	@Override
	public void installListeners() {
	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

}
