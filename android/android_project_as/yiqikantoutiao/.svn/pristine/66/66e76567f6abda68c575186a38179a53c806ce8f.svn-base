package com.chengning.yiqikantoutiao.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager.OnStateChangeListener;
import com.chengning.yiqikantoutiao.LoadStateManager;
import com.chengning.yiqikantoutiao.LoadStateManager.LoadState;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.widget.ProgressRefreshView;

import java.io.Serializable;

@SuppressLint("NewApi")
public class VideoActivity extends BaseFragmentActivity {
	
	private static final int MSG_DISMISS_TITLE_BAR = 1;
	private static final long TIME_DELAY = 3000;
	
	private VideoData mData;

	private ProgressRefreshView mProgressRefresh;
	private View mContentLayout;
	private WebView mWebView;
	private View mTitleBarLayout;
	private View mBack;
	
	private LoadStateManager mLoadStateManager;

	public static void launch(Activity from, VideoData video){
		Intent intent = new Intent(from, VideoActivity.class);
		intent.putExtra("video", video);
		from.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState){
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_video);
		super.onCreate(savedInstanceState);
	}

	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public void initViews() {
		mProgressRefresh = new ProgressRefreshView(getActivity(), true);
		mContentLayout = findViewById(R.id.content);
		mWebView = (WebView) findViewById(R.id.video_webview);
		mTitleBarLayout = findViewById(R.id.title_bar_layout);
		mBack = findViewById(R.id.title_bar_back);
	}

	@Override
	public void initDatas() {
        mData = (VideoData) getIntent().getSerializableExtra("video");
		
        mLoadStateManager = new LoadStateManager();
        mLoadStateManager.setOnStateChangeListener(new OnStateChangeListener<LoadState>() {
			@Override
			public void OnStateChange(LoadState state, Object obj) {
				switch (state) {
				case Init:
					mProgressRefresh.setWaitVisibility(true);
					mProgressRefresh.setRefreshVisibility(false);
					mProgressRefresh.setNoDataTvVisibility(false);
					mContentLayout.setVisibility(View.INVISIBLE);
					break;
				case Success:
					mProgressRefresh.setRootViewVisibility(false);
					mContentLayout.setVisibility(View.VISIBLE);
					break;
				case Failure:
					mProgressRefresh.setWaitVisibility(false);
					mProgressRefresh.setRefreshVisibility(true);
					mProgressRefresh.setNoDataTvVisibility(false);
					mContentLayout.setVisibility(View.INVISIBLE);
					break;
				}
			}
        	
		});
        
		mLoadStateManager.setState(LoadState.Init);

		mWebView.setBackgroundColor(Color.parseColor("#00000000"));
		mWebView.setBackgroundResource(R.color.transparent);
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.setWebViewClient(new MyWebViewClient());

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setLoadWithOverviewMode(false);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
		webSettings.setAllowFileAccess(true); 
		webSettings.setPluginState(PluginState.ON);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		
		// 4.0 打开硬件加速 ，硬件加速有可能导致闪白屏
//		if (getPhoneAndroidSDK() >= 14) {
//            getActivity().getWindow().setFlags(0x1000000, 0x1000000);
//            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        }
		
		if(mData.isUseUrl()){
			mWebView.loadUrl(mData.getUrl());
		}else{
			mWebView.loadData(mData.getHtml(), "text/html", "utf-8");
		}
	}

	@Override
	public void installListeners() {
		mWebView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					if(!(mTitleBarLayout.getVisibility() == View.VISIBLE)){
						getHandler().removeMessages(MSG_DISMISS_TITLE_BAR);
						
						mTitleBarLayout.setVisibility(View.VISIBLE);
						getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_DISMISS_TITLE_BAR), TIME_DELAY);
					}else{
						mTitleBarLayout.setVisibility(View.INVISIBLE);
					}
				}
				return false;
			}
		});
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
		switch (msg.what) {
		case MSG_DISMISS_TITLE_BAR:
			getHandler().removeMessages(MSG_DISMISS_TITLE_BAR);
			mTitleBarLayout.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				try {
					mWebView.onResume();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 100);
	}

	@Override
	public void onPause(){
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				try {
					mWebView.onPause();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 100);
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mWebView.removeAllViews();
		mWebView.destroy();
		mWebView = null;
		System.exit(0);
	}
	
	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putSerializable("video", mData); 
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mData = (VideoData) getIntent().getSerializableExtra("video");
    }
    
    public class MyWebChromeClient extends WebChromeClient {
		@Override
    	//播放网络视频时全屏会被调用的方法
		public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
		{			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		
		@Override
		//视频播放退出全屏会被调用的
		public void onHideCustomView() {
			// Hide the custom view. 
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
    
    private class MyWebViewClient extends WebViewClient {
    	
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url); 
        }
 
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
    		mLoadStateManager.setState(LoadState.Success);

			getHandler().removeMessages(MSG_DISMISS_TITLE_BAR);
			mTitleBarLayout.setVisibility(View.VISIBLE);
			getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_DISMISS_TITLE_BAR), TIME_DELAY);
        } 
 
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon); 
        } 
 
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl); 
    		mLoadStateManager.setState(LoadState.Success);
        } 
    } 
	
	public static int getPhoneAndroidSDK() {  
        int version = 0;  
        try {  
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();  
        }  
        return version;  
    } 
	
	public static class VideoData implements Serializable {
		boolean isUseUrl;
		String url;
		String html;
		
		public boolean isUseUrl() {
			return isUseUrl;
		}

		public void setUseUrl(boolean isUseUrl) {
			this.isUseUrl = isUseUrl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getHtml() {
			return html;
		}

		public void setHtml(String html) {
			this.html = html;
		}
	}

}
