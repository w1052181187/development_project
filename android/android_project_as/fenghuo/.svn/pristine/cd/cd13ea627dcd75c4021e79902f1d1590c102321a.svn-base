package com.chengning.fenghuo.util;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chengning.common.util.DisplayUtil;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.activity.VideoActivity.VideoData;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.util.ArticleWebView.OnWebViewListener;

public class ArticleVideoView {
	
	private static final String TAG = ArticleVideoView.class.getSimpleName();
	
	private Activity mContext;
	private ArticlesBean mBean;
	private OnWebViewVideoListener mListener;
	private Handler mHandler;
	
	private WebView mWebView;
	
	private boolean mIsPageFinishedCalled = false;

	private boolean mIsFullScreen;

	private ImageView viewFullscreen;

	private ImageView viewFullscreenDisplay;
	private boolean isFirstClick;

	@SuppressLint("NewApi")
	public RelativeLayout build(Activity context, ArticlesBean bean, final OnWebViewVideoListener listener, Handler handler){
		mContext = context;
		mBean = bean;
		mListener = listener;
		mHandler = handler;

		mWebView = new WebView(mContext);
		mWebView.setBackgroundColor(Color.parseColor("#00000000")); 
		mWebView.setBackgroundResource(R.color.transparent);
		mWebView.setWebChromeClient(new MyWebChromeClient());
		mWebView.setWebViewClient(new MyWebViewClient());
		
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {  
//			mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);  
//		}  
		
		
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
		
//
//		mWebView.loadData(webData, "text/html", "utf-8");
//		
//		mWebView.addJavascriptInterface(new JavascriptInterface(context, bean), "imagelistner");
		
		RelativeLayout relativeLayout = new RelativeLayout(mContext);
//		int height = mContext.getResources().getDimensionPixelSize(R.dimen.video_normal_height);
		DisplayUtil.getInst().init(mContext);
		int height = DisplayUtil.getInst().getScreenWidth()*9/16;
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
		relativeLayout.setLayoutParams(rlp);

		RelativeLayout.LayoutParams wlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		
		viewFullscreen = new ImageView(mContext);
		viewFullscreen.setBackgroundResource(R.color.transparent);
		
		int viewFullscreenHeight = mContext.getResources().getDimensionPixelSize(R.dimen.button_video_fullscreen_height_transparent);
		
		RelativeLayout.LayoutParams vlp = new RelativeLayout.LayoutParams(viewFullscreenHeight, viewFullscreenHeight);
		vlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		vlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		viewFullscreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mContext != null){
					handleFullScreenClick(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
				}
			}
		});
		
		relativeLayout.addView(mWebView, wlp);
		relativeLayout.addView(viewFullscreen, vlp);
		
		loadVideo(relativeLayout);
		return relativeLayout;
	}
	
	protected void handleFullScreenClick(boolean b) {
		this.mIsFullScreen = b;
		if (viewFullscreenDisplay != null) {
			viewFullscreenDisplay.setSelected(b);
		}
		if(b){
			mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mFullScreenListener.showFullScreen();
			Log.d(TAG, "viewFullscreen to SCREEN_ORIENTATION_LANDSCAPE");
		}else{
			mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			mFullScreenListener.hideFullScreen();
			Log.d(TAG, "viewFullscreen to SCREEN_ORIENTATION_PORTRAIT");
		}
	}

	public static boolean isBeanVideo(ArticlesBean bean){
		return !TextUtils.isEmpty(bean.getVideo_photo());
	}
	
	private void loadVideo(ViewGroup relativeLayout){
//		// video html ：video标签poster属性可能与video_photo一样，导致图片缓存冲突，解决办法为新开进程
		VideoData video = new VideoData();
		video.setUseUrl(!TextUtils.isEmpty(mBean.getVideo_play()));
		video.setUrl(mBean.getVideo_play());
		video.setHtml(mBean.getVideo_html());
		String url = mBean.getVideo_play();
//		url = "http://v.qq.com/iframe/player.html?vid=u0348dbvmdc&tiny=0&auto=0";
		if(video.isUseUrl()){
			mWebView.loadUrl(url);
		}else{
			url = video.getHtml();
			mWebView.loadData(url, "text/html", "utf-8");
		}
		if (!TextUtils.isEmpty(url) && url.contains("open.iqiyi.com")) {
			viewFullscreenDisplay = new ImageView(mContext);
			viewFullscreenDisplay.setBackgroundResource(R.drawable.full_screen_selector);
			int viewFullscreenHeightDisplay = mContext.getResources().getDimensionPixelSize(R.dimen.button_video_fullscreen_height);
			RelativeLayout.LayoutParams vlpDisplay = new RelativeLayout.LayoutParams(viewFullscreenHeightDisplay, viewFullscreenHeightDisplay);
			vlpDisplay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			vlpDisplay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			vlpDisplay.bottomMargin = mContext.getResources().getDimensionPixelSize(R.dimen.button_video_fullscreen_margin);
			vlpDisplay.rightMargin = vlpDisplay.bottomMargin;
			relativeLayout.addView(viewFullscreenDisplay, vlpDisplay);
		}
	}
	
	public WebView getWebView(){
		return mWebView;
	}
	
	public void destroy(){
		try {
			mListener = null;
		    mWebView.removeAllViews();
		    mWebView.destroy();
		    mWebView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 视频全屏参数 */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
    		ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

	private FullScreenShowListener mFullScreenListener;
	
	 /** 视频播放全屏 **/
    private void showCustomView(View view, CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        FrameLayout decor = (FrameLayout) mContext.getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(mContext);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
        mWebView.setVisibility(View.GONE);
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) mContext.getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        mWebView.setVisibility(View.VISIBLE);
    }

    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }
    
    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mContext.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    
    public void setFullScreenListener(FullScreenShowListener listener){
    	this.mFullScreenListener = listener;
    }

    
    public boolean onBackPressed() {
    	/** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
//        if (customView != null) {
//            hideCustomView();
//        } else if (mWebView != null && mWebView.canGoBack()) {
//        	mWebView.goBack();
//        }
        if (mIsFullScreen) {
        	handleFullScreenClick(!mIsFullScreen);
        	return true;
        }
		return false;
	}
    
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
            onBackPressed();
            return true;
        }
        return false;
    }
    
    public class MyWebChromeClient extends WebChromeClient{
		@Override
    	//播放网络视频时全屏会被调用的方法
		public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
		{			
			mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
			showCustomView(view, callback);
		}
		
		@Override
		//视频播放退出全屏会被调用的
		public void onHideCustomView() {
			// Hide the custom view. 
			mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
			hideCustomView();
		}
	}

    private class MyWebViewClient extends WebViewClient {
    	
    	private ExecutorService executorService;  
    	
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            return super.shouldOverrideUrlLoading(view, url); 
        } 
 
        @Override 
        public void onPageFinished(WebView view, String url) { 
            super.onPageFinished(view, url);
            mIsPageFinishedCalled = true;
            
            if(mListener != null){
            	mListener.onPageFinished();
            }
            
            if(mWebView != null){
        		mWebView.getSettings().setBlockNetworkImage(false);
            }
//            loadImage();
        } 
 
        @Override 
        public void onPageStarted(WebView view, String url, Bitmap favicon) { 
            super.onPageStarted(view, url, favicon); 
    		
            if(mListener != null){
            	mListener.onPageStarted();
            }
        } 
 
        @Override 
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) { 
            super.onReceivedError(view, errorCode, description, failingUrl); 
        } 
    }
    
    public void onResume() {
    	try {
			
			mWebView.getClass().getMethod("onResume").invoke(mWebView,(Object[])null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void onPause() {
    	try {
    		mWebView.getClass().getMethod("onPause").invoke(mWebView,(Object[])null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public interface FullScreenShowListener {
    	void showFullScreen();
    	void hideFullScreen();
    }

	public static interface OnWebViewVideoListener  extends ArticleWebView.OnWebViewListener{
//		void onFirstClick();
	}
}
