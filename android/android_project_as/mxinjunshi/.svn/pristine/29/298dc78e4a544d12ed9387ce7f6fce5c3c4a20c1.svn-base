package com.shenyuan.militarynews.fragment.home;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.BaseStateManager;
import com.loopj.android.http.PersistentCookieStore;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.LoadStateManager;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.activity.LoginActivity;
import com.shenyuan.militarynews.base.IForceListenRefreshExtend;
import com.shenyuan.militarynews.interf.IFragmentBackListener;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.views.PicArticleDialog;
import com.shenyuan.militarynews.views.ProgressRefreshView;

import org.apache.http.cookie.Cookie;

import java.util.List;


/**
 * copy form AdDetailActivity.java
 * 请求网址时带了本地cookie，关闭按钮不返回到主页
 * 
 * @author lijian
 *
 */

public class FeedsFragment extends BaseFragment implements IForceListenRefreshExtend, IFragmentBackListener {
	
	private RefreshState mRefreshState = RefreshState.RefreshComplete;;
	private OnRefreshStateListener mOnRefreshStateListener;
	
	private View mView;
	
	private TextView mTop;
	private WebView view;
	private String url;
	private String imgUrl;
	private String mTitle;
	private ImageView top_back;
	private ImageView back;
	private ImageView forward;
	private ImageView refresh;
	private ImageView close;
	private ImageView share;
	private CookieManager cookieManager;
	private LoadStateManager mLoadStateManager;
	private View mContentLayout;
	private ProgressRefreshView mProgressRefresh;
	private boolean isError;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void initViews() {
		url = JUrl.URL_DEFAULT_FEEDS;
		
		mTop = (TextView) mView.findViewById(R.id.ad_top);
		view = (WebView) mView.findViewById(R.id.ad_webview);
//		view.setVisibility(View.INVISIBLE);
		top_back = (ImageView) mView.findViewById(R.id.top_back);
		back = (ImageView) mView.findViewById(R.id.back);
		forward = (ImageView) mView.findViewById(R.id.forward);
		refresh = (ImageView) mView.findViewById(R.id.refresh);
		close = (ImageView) mView.findViewById(R.id.close);
		share = (ImageView) mView.findViewById(R.id.share);
		mProgressRefresh = new ProgressRefreshView(getActivity(), mView);
		mContentLayout = mView.findViewById(R.id.content_layout);
		
		mTop.setSelected(true);
		mTop.setText("视频");
	}

	@Override
	public void initDatas() {
		String feedsUrl = SPHelper.getInst().getString(SPHelper.KEY_VIDEO_URL);
		if(!TextUtils.isEmpty(feedsUrl)) {
			url = feedsUrl;
		}
		initLoadState();
		CookieSyncManager.createInstance(getActivity());    
	    cookieManager = CookieManager.getInstance();
	    initCookie();
		cookieManager.setAcceptCookie(true);
		view.getSettings().setJavaScriptEnabled(true);
		view.addJavascriptInterface(new JsInterface(), "chengning");
		view.setWebChromeClient(new myWebChromeClient());
		view.setWebViewClient(new myWebViewClient());
		view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		String ua = view.getSettings().getUserAgentString();
		view.getSettings().setUserAgentString(ua + " " + Common.getUAAndroid(getActivity(), Const.USER_AGENT_PREFIX ));
		loadData(url);
		this.setClickListener();
		
		top_back.setVisibility(View.INVISIBLE);
	}

	private void loadData(String url) {
		if (!Common.hasNet()) {
			UIHelper.showToast(getContext(), "加载失败，请检查网络");
			mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
			return;
		}
		view.loadUrl(url);
	}

	private void initLoadState() {
		mLoadStateManager = new LoadStateManager();
		mLoadStateManager.setOnStateChangeListener(new BaseStateManager.OnStateChangeListener<LoadStateManager.LoadState>() {

			@Override
			public void OnStateChange(LoadStateManager.LoadState state, Object obj) {
				switch (state) {
					case Init:
						mContentLayout.setVisibility(View.INVISIBLE);
						mProgressRefresh.setWaitVisibility(true);
						mProgressRefresh.setRefreshVisibility(false);
						mProgressRefresh.setNoDataTvVisibility(false);
						break;
					case Success:
						mContentLayout.setVisibility(View.VISIBLE);
						mProgressRefresh.setRootViewVisibility(false);
						break;
					case Failure:
						mContentLayout.setVisibility(View.INVISIBLE);
						mProgressRefresh.setWaitVisibility(false);
						mProgressRefresh.setRefreshVisibility(true);
						mProgressRefresh.setNoDataTvVisibility(false);
						break;
					default:
						break;
				}
			}
		});

		mLoadStateManager.setState(LoadStateManager.LoadState.Init);
	}

	@Override
	public void installListeners() {
		mProgressRefresh.setRefreshOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mLoadStateManager.setState(LoadStateManager.LoadState.Init);
				loadData(url);
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_mall, null);
		return mView;
	}
	
	private class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url1) {
			top_back.setVisibility(View.VISIBLE);
			// 如果是首页，直接隐藏..
			if(url1.equals(url)){
				top_back.setVisibility(View.INVISIBLE);
				view.clearHistory();
			}
			if(url1.startsWith("http:") || url1.startsWith("https:")){
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
			isError = true;
			handleUrlLoadError(failingUrl);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		@Override
		public void onPageStarted(WebView view, String url1, Bitmap favicon) {
			super.onPageStarted(view, url1, favicon);
			handleTopBack(url1);
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			if (!isError) {
				//回调成功后的相关操作
				if (view.getProgress() >= 80) {
					mLoadStateManager.setState(LoadStateManager.LoadState.Success);
				}
			}
			isError = false;
			super.onPageFinished(view, url);
		}

	}

	private void handleUrlLoadError(String failingUrl) {
		UIHelper.showToast(getContext(), "加载失败，请检查网络");
		mLoadStateManager.setState(LoadStateManager.LoadState.Failure);
		url = failingUrl;
	}

	private class myWebChromeClient extends WebChromeClient{
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			mTitle = title;
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if(newProgress == 100){
				mRefreshState = RefreshState.RefreshComplete;
			}
		}
		
	}
	private void setClickListener(){
		top_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(view.canGoBack()){
					view.goBack();
				}
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
				
			}
		});
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PicArticleDialog dialog = new PicArticleDialog();
				dialog.setData(url, imgUrl, mTitle);
				dialog.showAllowingStateLoss((BaseFragmentActivity) getContext(), getChildFragmentManager(),
						PicArticleDialog.class.getSimpleName());
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void forceRefresh() {
		if(view != null && mRefreshState == RefreshState.RefreshComplete){
			view.loadUrl(url);
			mRefreshState = RefreshState.Refreshing;
			top_back.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void forceCheckRefresh() {
		
	}

	@Override
	public void forceTop() {
		
	}
	
	@Override
	public RefreshState getRefreshState() {
		return mRefreshState;
	}

	@Override
	public void setOnRefreshStateListener(OnRefreshStateListener listener) {
		this.mOnRefreshStateListener = listener;
	}

	@Override
	public void forceSetPageSelected(boolean isSelected) {
		
	}

	@Override
	public boolean canGoBack() {
		if(view != null){
			String url1 = view.getUrl();
			if(url1 != null && url1.equals(url + "/")){
				return false;
			}
			if(view.canGoBack()){
				return true;
			}
		}
		return false;
	}

	@Override
	public void goBack() {
		if(view != null){
			view.goBack();	
		}
	}
	
	private void handleTopBack(String url1){
		if(url1 != null && url1.equals(url)){
			top_back.setVisibility(View.INVISIBLE);
			view.clearHistory();
		}
	}
	
	private void initCookie(){
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
//			cookieManager.removeAllCookie();
		}
	}
	
	private class JsInterface {
        @JavascriptInterface
        public void goTologinAction() {
        	startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

	
}
