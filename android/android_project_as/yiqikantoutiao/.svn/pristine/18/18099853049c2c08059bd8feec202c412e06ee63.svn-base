package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.UmengShare;
import com.chengning.yiqikantoutiao.widget.PicArticleDialog;

import java.util.List;

public class AdDetailActivity extends BaseFragmentActivity {
	
	public final static String TYPE_ARTICLE = "article";
	public final static String TYPE_AD = "ad";
	
	private WebView view;
	private String url;
	private String imgUrl;
	private String mTitle;
	private ImageView back;
	private ImageView forward;
	private ImageView refresh;
	private ImageView close;
	private ImageView share;
	private String mType;
	private ImageView iv_ad_close;
	private TextView tv_ad_title;
	
	
	public static void launch(Activity from, String type, String url, String imgUrl){
		Intent intent = new Intent(from, AdDetailActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("adurl", url);
		intent.putExtra("imgurl", imgUrl);
		from.startActivity(intent);
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putString("type", mType);
    	savedInstanceState.putString("adurl", url);
    	savedInstanceState.putString("imgurl", imgUrl);
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    	mType  = savedInstanceState.getString("type");
    	url  = savedInstanceState.getString("adurl");
    	imgUrl  = savedInstanceState.getString("imgurl");
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Common.setTheme(this);
		setContentView(R.layout.activity_ad_webview);
		
		mType = this.getIntent().getExtras().getString("type");
		url = this.getIntent().getExtras().getString("adurl");
		imgUrl = this.getIntent().getExtras().getString("imgurl");
		
		iv_ad_close = (ImageView) findViewById(R.id.ad_close);
		tv_ad_title = (TextView) findViewById(R.id.ad_title);
		view = (WebView) this.findViewById(R.id.ad_webview);
		back = (ImageView) this.findViewById(R.id.back);
		forward = (ImageView) this.findViewById(R.id.forward);
		refresh = (ImageView) this.findViewById(R.id.refresh);
		close = (ImageView) this.findViewById(R.id.close);
		share = (ImageView) this.findViewById(R.id.share);
		view.setWebChromeClient(new myWebChromeClient());
		view.setWebViewClient(new myWebViewClient());
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl(url);
		this.setClickListener();
		
	}	
	private class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if( url.startsWith("http:") || url.startsWith("https:") ) {  
                return false;  
            }
			 PackageManager packageManager = getPackageManager();
			 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			 List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
			 boolean isValid = !activities.isEmpty();
			 if (isValid) {
			     startActivity(intent);
//			     return true;
			 }
			return true;
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

	}
	private class myWebChromeClient extends WebChromeClient {
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			tv_ad_title.setText(title);
			mTitle = title;
		}
		
	}
	private void setClickListener(){
		iv_ad_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleActivityClose(mType);
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
				if(view.canGoForward()){
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
				handleActivityClose(mType);
			}
		});
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PicArticleDialog dialog = new PicArticleDialog();
				dialog.setData(url,imgUrl,mTitle);
				dialog.show(getSupportFragmentManager(),
						PicArticleDialog.class.getSimpleName());
			}
		});
	}
	
	public void nextActivity()
	{
		MainActivity.launch(this);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		UmengShare.getInstance().onActivityResult(arg0, arg1, arg2);
	}
	@Override
	public void onBackPressed() {
		if(view.canGoBack()){
			view.goBack();
		}else{
			handleActivityClose(mType);
		}
	}
	
	protected void handleActivityClose(String type) {
		if (TextUtils.equals(type, TYPE_AD)) {
			nextActivity();
		}
		finish();
	}

	@Override
	public BaseFragmentActivity getActivity() {
		return AdDetailActivity.this;
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
}
