package com.chengning.fenghuo.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.util.BaseUmengShare;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.activity.AccountBindActivity;
import com.chengning.fenghuo.activity.LoginActivity;
import com.chengning.fenghuo.activity.MainActivity;
import com.chengning.fenghuo.activity.TaskWebActivity;
import com.chengning.fenghuo.base.BaseWebviewFragmentActivity;
import com.chengning.fenghuo.base.IForceListenRefreshExtend;
import com.chengning.fenghuo.base.IScrollCallBack;
import com.chengning.fenghuo.event.ShareImgEvent;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.SPHelper;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.util.UmengShare;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.TitleBar;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 收徒
 */
public class InviteFragment extends BaseFragment {

	private View mView;
	private TitleBar mTitleBar;

	private WebView mWebView;
	private ProgressBar mProgressBar;

	private CookieManager cookieManager;
	private String mCurErrorUrl;
	private IWXAPI api;
	private boolean mLastLoginState;
	private int nightMode;
	private View mWebViewBg;

	@Override
	public void initViews() {
		mTitleBar = new TitleBar(getContext(), mView);

		mWebView = (WebView) mView.findViewById(R.id.base_webview);
		mWebViewBg = mView.findViewById(R.id.base_webview_bg);
		mProgressBar = (ProgressBar) mView.findViewById(R.id.content_progress);
	}
	

	@Override
	public void initDatas() {

		nightMode = SettingManager.getInst().getNightModel();

		mWebView.setVerticalScrollBarEnabled(true);
		mWebView.setVerticalScrollbarOverlay(true);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setHorizontalScrollbarOverlay(false);

		//去掉X5的小滑块
		IX5WebViewExtension extension = mWebView.getX5WebViewExtension();
		if (extension != null) {
			//此方法滑动条也会不显示
//            extension.setScrollBarFadingEnabled(false);
//            extension.setVerticalScrollBarDrawable(null);
			extension.setVerticalTrackDrawable(null);;
		}

		mWebView.setMinimumHeight(1000);
		mWebView.setWebChromeClient(buildWebChromeClient());
		mWebView.setWebViewClient(buildWebViewClient());

		WebSettings webSettings = mWebView.getSettings();

		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		mWebView.addJavascriptInterface(new BaseJsInterface(),"chengning");
		CookieSyncManager.createInstance(getActivity());
		cookieManager = CookieManager.getInstance();
		initCookie();
		cookieManager.setAcceptCookie(true);
		String ua = mWebView.getSettings().getUserAgentString();
		webSettings.setUserAgentString(ua + " " + Common.getUAAndroid(getActivity(), Consts.USER_AGENT_PREFIX ));
		mWebView.loadUrl(buildUrl());

		mLastLoginState = App.getInst().isLogin();

	}

	private String buildUrl() {
		String url = SPHelper.getInst().getString(SPHelper.KEY_MY_PRENTICE_URL);
		url = url + "?viewtype=" + nightMode;
		return url;
	}

	public WebViewClient buildWebViewClient() {
		return new BaseWebviewClient();
	}

	public WebChromeClient buildWebChromeClient() {
		return new BaseWebChromeClient();
	}

	private void initCookie(){
		PersistentCookieStore myCookieStore = new PersistentCookieStore(App.getInst());
		List<Cookie> cookies = myCookieStore.getCookies();
		cookieManager.removeAllCookie();
		if(!Common.isListEmpty(cookies)){
			cookieManager.setAcceptCookie(true);
			for (int i = 0; i < cookies.size(); i++) {
				String cookieString = cookies.get(i).getName() + "=" + cookies.get(i).getValue() +
						";domain="+ cookies.get(i).getDomain();

				cookieManager.setCookie(cookies.get(i).getDomain(), cookieString);
			}

		}
		CookieSyncManager.getInstance().sync();
	}

	@Override
	public void installListeners() {
		mWebView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				return true;
			}
		});
	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_invite, container, false);
		return mView;
	}
	
	/**
	 * 刷新视频
	 */
	public void refresh() {
		if (mWebView != null) {
			mWebView.reload();
		}
	}

	@Override
	public void onResume() {
		if (mLastLoginState != App.getInst().isLogin()) {
			initCookie();
			refresh();
			mLastLoginState = App.getInst().isLogin();
		}
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mWebView != null) {
			try {
				final ViewGroup parent = (ViewGroup) mWebView.getParent();
				if (parent != null) {
					parent.removeView(mWebView);
				}
				mWebView.stopLoading();
				mWebView.removeAllViews();
				mWebView.destroy();
				mWebView = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public class BaseWebviewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

//			handleTitleBarClose(mTitleBar);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
			super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
			if(webResourceRequest.isForMainFrame()){
				onReceivedError(webView, webResourceResponse.getStatusCode(), webResourceResponse.getReasonPhrase(), webResourceRequest.getUrl().toString());
			}
		}

		@Override
		public void onReceivedError(WebView webView, int i, String s, final String s1) {
			super.onReceivedError(webView, i, s, s1);
			getHandler().post(new Runnable() {
				@Override
				public void run() {
					mWebView.loadUrl("file:///android_asset/404.html");
					mCurErrorUrl = s1;
				}
			});
		}

	}

	public class BaseWebChromeClient extends WebChromeClient{
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			setTitleBarTitle(mTitleBar, title);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress >= 90) {
				mProgressBar.setVisibility(View.GONE);
			} else {
				if (View.VISIBLE != mProgressBar.getVisibility()) {
					mProgressBar.setVisibility(View.VISIBLE);
				}
				if (View.VISIBLE != mWebViewBg.getVisibility()) {
					mWebViewBg.setVisibility(View.VISIBLE);
				}
				mProgressBar.setProgress(newProgress);
			}
			if (newProgress == 100) {
				getHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mWebViewBg.setVisibility(View.GONE);
					}
				},200);
			}
			super.onProgressChanged(view, newProgress);
		}

	}

	public class BaseJsInterface {
		@android.webkit.JavascriptInterface
		public void refreshPage(){
			getHandler().post(new Runnable() {
				@Override
				public void run() {
					// 不直接刷新，触发back刷新，使用时注意
//                    refreshPageForce();
					int mode = mWebView.getSettings().getCacheMode();
					mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
					Log.e("webview",mWebView.canGoBack() + "");
					if (mWebView.canGoBack()){
						mWebView.goBack();
					} else {
						refreshPageForce();
					}
					mWebView.getSettings().setCacheMode(mode);
//                    mWebView.reload();
				}
			});
		}

		@android.webkit.JavascriptInterface
		public void refreshPageForce(){
			getHandler().post(new Runnable() {
				@Override
				public void run() {
					mWebView.loadUrl(mCurErrorUrl);
				}
			});
		}

		@android.webkit.JavascriptInterface
		public void toapp(String str) {
			JSONObject obj = null;
			try {
				obj = new JSONObject(str);
				final  String url = obj.getString("redirect_url");
				final  String blank = obj.getString("blank");
				if (TextUtils.isEmpty(url)) {
					return;
				}
				getHandler().post(new Runnable() {
					@Override
					public void run() {
						if (TextUtils.equals(blank, "1")) {
							TaskWebActivity.launch(getActivity(), url);
						} else if (TextUtils.equals(blank, "0"))  {
							mWebView.loadUrl(url);
						}

					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@android.webkit.JavascriptInterface
		public void registerApp(){
			LoginActivity.launch(getActivity());
		}
		@android.webkit.JavascriptInterface
		public void bindWeixin(){
			startActivity(new Intent(getActivity(), AccountBindActivity.class));
		}

		@android.webkit.JavascriptInterface
		public void readArticle(){
			getHandler().post(new Runnable() {
				@Override
				public void run() {
					MainActivity.setChangeView(0);
					getActivity().finish();
				}
			});

		}
		@android.webkit.JavascriptInterface
		public void goodReply(){
			getHandler().post(new Runnable() {
				@Override
				public void run() {
					MainActivity.setChangeView(0);
					getActivity().finish();
				}
			});
		}

		@android.webkit.JavascriptInterface
		public void copyInvitecode(String str){
			JSONObject obj = null;
			try {
				obj = new JSONObject(str);
				String code = obj.optString("invitecode");
				if (!TextUtils.isEmpty(code)) {
					Utils.copyToClipboardManager(getActivity(), code);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@android.webkit.JavascriptInterface
		public void sharePlatform(String str){
            JSONObject obj = null;
            try {
                obj = new JSONObject(str);
                final JSONArray images = obj.optJSONArray("shareimg");
                final String imageUrl = obj.optString("shareimg");
                final String title = obj.optString("share_title");
                final String desc = obj.optString("share_content");
                final String url = obj.optString("url");
                final int type = obj.optInt("type");
                switch (type) {
                    case Consts.TASK_SHARE_FRIENDS_QUAN:
                        //朋友圈
						handleFriendsQuan(images, desc, url, imageUrl, type);
                        break;
                    case Consts.TASK_SHARE_FRIENDS_QUN:
                        //朋友群
                        handleFriendsOrQun(title, desc, url, imageUrl, type);
                        break;
                    case Consts.TASK_SHARE_FRIENDS:
                        //朋友
                        handleFriendsOrQun(title, desc, url, imageUrl,type);
                        break;
                    case 4:
                        //QQ
                        handleShareQQ(url, title, desc, imageUrl);
                        break;
                    case 5:
                        //微博
                        handleShareSina(url, title, desc, imageUrl);
                        break;
                }

            } catch (Exception e) {
                closePd();
            }

		}

	}

	private void handleShareSina(String url, String title, String desc, String imageUrl) {
		if (!TextUtils.isEmpty(url)) {
			UmengShare.getInstance().shareToWeibo(getActivity(), url, "", title, null);
		}
	}

	private void handleFriendsQuan(JSONArray images, String desc, String url, String imageUrl, int type) throws Exception {
        showPd();
		if (images != null) {
			int size = images.length();
			ArrayList<String> list = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				list.add(images.optString(i));
			}
			if (!Common.isListEmpty(list)) {
				UmengShare.getInstance().shareMultiImgToWxq(getActivity(), desc + url, list);
            }
		} else if (!TextUtils.isEmpty(imageUrl)) {
			if (!TextUtils.isEmpty(url)) {
				UmengShare.getInstance().setShareType(type);
			}else {
				UmengShare.getInstance().setShareType(Consts.TASK_SHARE_FRIENDS);
			}
			File file = Common.creatFile(JUrl.SHARE_IMG_FILE_PATH, "shareImg_income" +".png");
			file = Common.saveImageToSdCard(file, ImageLoader.getInstance().loadImageSync(imageUrl));
			if (file != null && file.length() > 0) {
				UmengShare.getInstance().shareImgToCircle(getActivity(), file);
			}
		}
        closePd();
	}
	public void showPd() {
		getHandler().post(new Runnable() {
			@Override
			public void run() {
				UIHelper.addPD(getActivity(), "正在加载...");
			}
		});
	}
	public void closePd() {
		getHandler().post(new Runnable() {
			@Override
			public void run() {
				UIHelper.removePD();
			}
		});
	}

	private void handleShareQQ(String url, String title, String desc, String imageUrl) {
		if (!TextUtils.isEmpty(url)) {
			UmengShare.getInstance().shareToQq(getActivity(), url, "", title, desc, null);
		} else if (!TextUtils.isEmpty(imageUrl)) {
			UmengShare.getInstance().shareImgToQq(getActivity(), imageUrl, null);
		}

	}

	private void handleFriendsOrQun(String title, String desc, String url, String imageUrl,int type) {
		if (!TextUtils.isEmpty(url)) {
			UmengShare.getInstance().setShareType(type);
			UmengShare.getInstance().shareToWeixin(getActivity(), url, "", title, desc);
		} else if (!TextUtils.isEmpty(imageUrl)) {
			UmengShare.getInstance().setShareType(type);
			UmengShare.getInstance().shareImgToWeixin(getActivity(), imageUrl);
		}
	}

	/**
	 * 设置标题
	 * @param titleBar
	 * @param title
	 */
	public void setTitleBarTitle(TitleBar titleBar, String title) {
		titleBar.setTitle(title);
		Resources resources = getResources();
		titleBar.setBackTextBackgroundColor(resources.getColor(R.color.transparent));

		if (TextUtils.equals(title,"任务系统")) {
//			titleBar.setBackTextDrawable(resources.getDrawable(R.drawable.nav_back_white));
			titleBar.setBackgroundColor(resources.getDrawable(R.drawable.rwxt_title_bg));
			titleBar.setTitleColor(resources.getColor(R.color.white));
		} else if (TextUtils.equals(title,"收入排行榜")) {
			titleBar.setTitle("");
//			titleBar.setBackTextDrawable(resources.getDrawable(R.drawable.nav_back_white));
			titleBar.setBackgroundColor(resources.getColor(R.color.my_income_money_color));
			titleBar.setTitleColor(resources.getColor(R.color.white));
		} else {
			boolean isNight = Common.isTrue(SettingManager.getInst().getNightModel());
//			titleBar.setBackTextDrawable(resources.getDrawable(R.drawable.nav_back_state));
			if (isNight) {
				titleBar.setBackgroundColor(resources.getDrawable(R.drawable.nav_bg_night));
				titleBar.setTitleColor(resources.getColor(R.color.night_text_color));
			} else {
//				titleBar.setBackTextBackgroundColor(resources.getDrawable(R.drawable.nav_click_bg_state));
				titleBar.setBackgroundColor(resources.getDrawable(R.drawable.nav_bg));
				titleBar.setTitleColor(resources.getColor(R.color.article_title));
			}

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UmengShare.getInstance().onActivityResult(requestCode, resultCode, data);
	}

}
