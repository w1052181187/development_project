package com.cmstop.jstt.activity;

import java.util.List;

import org.apache.http.cookie.Cookie;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.cmstop.jstt.App;
import com.cmstop.jstt.R;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.UmengShare;
import com.loopj.android.http.PersistentCookieStore;

/**
 * copy form AdDetailActivity.java
 * 请求网址时带了本地cookie，关闭按钮不返回到主页
 * 
 * @author lijian
 *
 */

public class ShopActivity extends BaseFragmentActivity {
	
	public static final String WEB_URL = "web_url";
	
	
	private ImageView iv_shop_close;
	private TextView tv_shop_title;
	private WebView view;
	
	private String url;
	private String mTitle;
	private ImageView back;
	private ImageView forward;
	private ImageView refresh;
	private ImageView close;
	private ImageView share;
	private CookieManager cookieManager;
	
	private boolean isFirst = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Common.setTheme(this);
		setContentView(R.layout.activity_shop_webview);
		url = JUrl.URL_DEFAULT_SHOPURL;
		iv_shop_close = (ImageView) findViewById(R.id.shop_close);
		tv_shop_title = (TextView) findViewById(R.id.shop_title);
		
		view = (WebView) this.findViewById(R.id.ad_webview);
		back = (ImageView) this.findViewById(R.id.back);
		forward = (ImageView) this.findViewById(R.id.forward);
		refresh = (ImageView) this.findViewById(R.id.refresh);
		close = (ImageView) this.findViewById(R.id.close);
		share = (ImageView) this.findViewById(R.id.share);
		
		url = getIntent().getStringExtra(WEB_URL);
		
		CookieSyncManager.createInstance(getActivity());    
	    cookieManager = CookieManager.getInstance();
		
		if(App.getInst().isLogin()){
			PersistentCookieStore myCookieStore = new PersistentCookieStore(App.getInst());
			List<Cookie> cookies = myCookieStore.getCookies();  
			if(!Common.isListEmpty(cookies)){
			    cookieManager.removeAllCookie();  
			    cookieManager.setAcceptCookie(true);  
			    for (int i = 0; i < cookies.size(); i++) {  
			        String cookieString = cookies.get(i).getName() + "=" + cookies.get(i).getValue() +  
			                ";domain="+ cookies.get(i).getDomain();    
			        cookieManager.setCookie(cookies.get(i).getDomain(), cookieString);    
			    }  
			    CookieSyncManager.getInstance().sync();  
			}
		}else{
			//TODO 根据需求决定是直接清除cookie还是直接传给后台
			String cookie = cookieManager.getCookie(url);
			cookieManager.setCookie(url, cookie);
			
		}
		cookieManager.setAcceptCookie(true);
		view.setWebChromeClient(new myWebChromeClient());
		view.setWebViewClient(new myWebViewClient());
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		view.loadUrl(url);
		tv_shop_title.setText(getResources().getString(R.string.myorder));
		this.setClickListener();
		
	}	

	@Override
	public void initViews() {

	}

	@Override
	public void initDatas() {

	}

	@Override
	public void installListeners() {
		
	}

	@Override
	public void processHandlerMessage(Message msg) {
	}
	
	private class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url1) {
			if(url1.startsWith("http:") || url1.startsWith("https:")){
				url = url1;
				view.loadUrl(url1);
				return true;
			}else{
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));  
		            startActivity(intent); 
				} catch (Exception e) {
					return true;
				}
				return false;
			}
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

	}
	private class myWebChromeClient extends WebChromeClient{
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
//			tv_shop_title.setText(title);
			mTitle = title;
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
//			if(newProgress == 100){
//				//避免因导航无法显示刷新时导致的闪屏问题..
//				if(!isFirst && view.getVisibility() == View.INVISIBLE)
//					view.setVisibility(View.VISIBLE);
//				if(isFirst && url.equals("http://m.shop.xinjunshi.com")){
//					//这样处理为了有时进入导航无法显示的问题..
//					view.reload();
//					isFirst = false;
//				}
//			}
		}
		
	}
	private void setClickListener(){
		iv_shop_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(view.canGoBack()){
					view.goBack();
				}
			}
		});
		forward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (view.canGoForward()) {
					view.goForward();
				}
			}
		});
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				view.reload();
			}
		});
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		
				finish();
			}
		});
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				PicArticleDialog dialog = new PicArticleDialog();
//				dialog.setData(url, imgUrl, mTitle);
//				dialog.show(getSupportFragmentManager(),
//						PicArticleDialog.class.getSimpleName());
			}
		});
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public Activity getActivity() {
		return this;
	}
}
