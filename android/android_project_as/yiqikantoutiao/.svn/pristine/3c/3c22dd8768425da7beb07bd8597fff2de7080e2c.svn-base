package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpCacheControl;
import com.chengning.common.util.HttpUtil;
import com.chengning.common.util.SerializeUtil;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.LoginManager;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.data.bean.AdStartBean;
import com.chengning.yiqikantoutiao.data.bean.InitBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.SPHelper;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

public class SplashActivity extends BaseActivity{
	
	private static final long TIME_WAIT = 3000;
	private static final int MSG_WAIT = 10;
	private static final int MSG_NEXT = 11;
	private ImageView mImage;
	private String adUrl;
	private String imgUrl;
	private View root;
	private String defaultImage ;
	private DisplayImageOptions option;
	private boolean isShowing = false;
	private HttpCacheControl mCacheControl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_splash);
		super.onCreate(savedInstanceState);
	
	}
	@Override
	public Activity getActivity() {
		return SplashActivity.this;
	}

	@Override
	public void initViews() {
		 root = findViewById(R.id.root);
		 mImage = (ImageView) findViewById(R.id.splash_image);
		 root.setBackgroundResource(R.color.splash_bg);
		
	}

	@Override
	public void initDatas() {

		String defaultSavedImage = SPHelper.getInst().getString(SPHelper.KEY_AD_START_IMAGE);
		if(TextUtils.isEmpty(defaultSavedImage)){
			defaultSavedImage = "drawable://" + R.drawable.splash_pic;
			SPHelper.getInst().saveString(SPHelper.KEY_AD_START_IMAGE, defaultSavedImage);
		}
		defaultImage = defaultSavedImage;
		option = new DisplayImageOptions.Builder()
		.showStubImage(R.color.transparent)
		.showImageForEmptyUri(R.color.transparent)
		.showImageOnFail(R.color.transparent)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.considerExifParams(true)
		.displayer(new FadeInBitmapDisplayer(200))
		.build();
		
		
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			UIHelper.showToast(getActivity(),"未找到SD卡，部分功能不能使用");
		}
		getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_WAIT), TIME_WAIT);

//		handleAdStart();

		if (Common.hasNet()) {
			mCacheControl = new HttpCacheControl();
			mCacheControl.init(App.getInst(), JUrl.URL_CHANGE_SITE, new HttpCacheControl.CacheControlMaxAgeListener() {

				@Override
				public void success() {
					handleAdStart();
				}

				@Override
				public void failure() {
					getSite();
				}
			});

		} else {
			ImageLoader.getInstance().displayImage(defaultImage, mImage);
		}
	}

	/**
	 * 获取域名
	 */
	private void getSite() {
		HttpUtil.get(JUrl.URL_CHANGE_SITE, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  JSONObject response) {
				String oldDomain = URI.create(JUrl.SITE).getHost();
				JUrl.SITE = response.optString("api_url");
				SPHelper.getInst().saveString(SPHelper.KEY_CACHE_SITE, JUrl.SITE);
				mCacheControl.saveCacheControlMaxAge(JUrl.URL_CHANGE_SITE, headers);
				LoginManager.getInst().updateCookie(oldDomain);
				handleAdStart();
				super.onSuccess(statusCode, headers, response);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
				handleAdStart();
				super.onFailure(statusCode, headers, responseString, throwable);
			}

		});
	}

	@Override
	public void installListeners() {
		mImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(adUrl)) {
					getHandler().removeMessages(MSG_NEXT);
					AdDetailActivity.launch(getActivity(), AdDetailActivity.TYPE_AD, adUrl, imgUrl);
					finish();
				}
			}

		});
	}
	private void handleAdStart(){
		RequestParams params = new RequestParams();
		params.put("type", "android");
		params.put("channel", Common.getUmengChannel(getActivity()));
		HttpUtil.post(getActivity(), JUrl.SITE + JUrl.URL_GET_AD_START, params, new MyJsonHttpResponseHandler() {

			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				getHandler().removeMessages(MSG_WAIT);
				
				Gson gson = new Gson();
				InitBean initBean = gson.fromJson(data, InitBean.class);

				// 时间格式的js代码
				SPHelper.getInst().saveString(SPHelper.KEY_DATEFORMAT, initBean.getDateformat());
				
				JSONObject json;
				try {
					json = new JSONObject(data);
					JSONObject reason = json.optJSONObject("reason_list");
					App.getInst().setReportReason(reason);
					SPHelper.getInst().saveString(SPHelper.KEY_REPORT_REASON, reason.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String keywords = SPHelper.getInst().getString(SPHelper.SEARCH_NEWS_KEYWORDS);
				String keywordsNew = SerializeUtil.serialize(initBean.getHot_keywords());
				if (!TextUtils.equals(keywords, keywordsNew)) {
					SPHelper.getInst().saveString(SPHelper.SEARCH_NEWS_KEYWORDS, keywordsNew);
				}
				
				String imageSaveStr = SPHelper.getInst().getString(SPHelper.KEY_AD_START_IMAGE);
				AdStartBean adBean = initBean.getAd();
				if (adBean != null) {
					if (!TextUtils.isEmpty(adBean.getUrl())){
						adUrl = adBean.getUrl();

					}
					if (!TextUtils.isEmpty(adBean.getImages())){
						imgUrl=adBean.getImages();
						if (!imageSaveStr.equals(adBean.getImages())) {
							SPHelper.getInst().saveString(SPHelper.KEY_AD_START_IMAGE,adBean.getImages());
						}
						displayImage(adBean.getImages(),mImage,option);
					} else {
						displayImage(imageSaveStr,mImage,option);
					}
				}else{
					if (!TextUtils.isEmpty(imageSaveStr)) {
						displayImage(imageSaveStr,mImage,option);
					} else {
						getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_NEXT), TIME_WAIT);
					}
				}
				
				//是否轮询查看最新文章个数
				SPHelper.getInst().saveInt(SPHelper.BADGE_KEY_NEW_ARTICLE_IS_POLLING, initBean.getIs_polling());
				
				//轮询查看最新文章时间
				SPHelper.getInst().saveInt(SPHelper.BADGE_KEY_NEW_ARTICLE_POLLING_TIME, initBean.getCheck_news_interval());
				
				//是否显示积分弹窗
				SPHelper.getInst().saveInt(SPHelper.POINT_DIALOG_KEY_IS_SHOW, initBean.getIs_alert_credits());

				//首次引导下载
				SPHelper.getInst().saveString(SPHelper.KEY_GUIDE_DOWNLOAD_IMAGE_URL, SerializeUtil.serialize(initBean.getBoot_images()));
				SPHelper.getInst().saveInt(SPHelper.KEY_GUIDE_DOWNLOAD_SHOW, initBean.getIs_boot());

				//未登录注册奖励金额
				SPHelper.getInst().saveString(SPHelper.KEY_REGIST_MONEY, initBean.getReg_money());

				//未登录阅读每篇金币奖励金额
				SPHelper.getInst().saveString(SPHelper.KEY_NO_LOGIN_READ_GOLD, initBean.getRead_gold());
				App.getInst().setReadGold(initBean.getRead_gold());

//				getHandler().obtainMessage(MSG_VERIFY_COOKIE, initBean.getIs_login()).sendToTarget();

				InitBean.InitWapUrlBean wapUrlBean = initBean.getWap_url();
				handleWapUrl(wapUrlBean);

			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				getHandler().removeMessages(MSG_WAIT);
				ImageLoader.getInstance().displayImage(defaultImage, mImage,option);
				getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_NEXT),TIME_WAIT);
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
				getHandler().removeMessages(MSG_WAIT);
				ImageLoader.getInstance().displayImage(defaultImage, mImage,option);
				getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_NEXT), TIME_WAIT);
			}
		});
		
	}

	private void handleWapUrl(InitBean.InitWapUrlBean wapUrlBean) {
		if (wapUrlBean == null) {
			return;
		}
		String mPrenticeUrl = wapUrlBean.getMy_prentice();
		String mIncomeUrl = wapUrlBean.getMy_income();
		String mTaskUrl = wapUrlBean.getTask();
		String mExchangeUrl = wapUrlBean.getExchange();
		String mExchangeListUrl = wapUrlBean.getExchange_list();

		if (!TextUtils.isEmpty(mPrenticeUrl)) {
			SPHelper.getInst().saveString(SPHelper.KEY_MY_PRENTICE_URL,mPrenticeUrl);
		}
		if (!TextUtils.isEmpty(mIncomeUrl)) {
			SPHelper.getInst().saveString(SPHelper.KEY_MY_INCOME_URL,mIncomeUrl);
		}
		if (!TextUtils.isEmpty(mTaskUrl)) {
			SPHelper.getInst().saveString(SPHelper.KEY_MY_TASK_URL,mTaskUrl);
		}
		if (!TextUtils.isEmpty(mExchangeUrl)) {
			SPHelper.getInst().saveString(SPHelper.KEY_MY_EXCHANGE_URL,mExchangeUrl);
		}
		if (!TextUtils.isEmpty(mExchangeListUrl)) {
			SPHelper.getInst().saveString(SPHelper.KEY_MY_EXCHANGE_LIST_URL,mExchangeListUrl);
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		isShowing = true;
		getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_WAIT), TIME_WAIT);
	}

	@Override
	public void onPause() {
		super.onPause();
		isShowing  = false;
		getHandler().removeMessages(MSG_WAIT);
	}
	
	@Override
	public void processHandlerMessage(Message msg) {
		switch(msg.what){
		case MSG_WAIT :
			getHandler().obtainMessage(MSG_NEXT).sendToTarget();
			break;
		case MSG_NEXT :
			nextActivity();
			break;
		default:
			break;
		}
	}

	public void nextActivity() {
		MainActivity.launch(getActivity());
		finish(); 
	}
	private void displayImage(String imageUrl , ImageView view, DisplayImageOptions options){
		ImageLoader.getInstance().displayImage(imageUrl, view,options,new ImageLoadingListener(){

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				if (isShowing) {
					getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_NEXT), TIME_WAIT);
				}
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				if (isShowing) {
					getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_NEXT), TIME_WAIT);
				}
			}

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				if (isShowing) {
					getHandler().sendMessageDelayed(getHandler().obtainMessage(MSG_NEXT), TIME_WAIT);
				}
			}
			
		});
	}

}
