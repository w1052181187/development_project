package com.shenyuan.militarynews.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chengning.common.util.DisplayUtil;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.SettingManager;
import com.shenyuan.militarynews.SettingManager.FontSize;
import com.shenyuan.militarynews.activity.PhotoPageArticleActivity;
import com.shenyuan.militarynews.activity.VideoActivity;
import com.shenyuan.militarynews.activity.VideoActivity.VideoData;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.ArticleVideoView.FullScreenShowListener;
import com.shenyuan.militarynews.utils.ImageCacheLoader.OnImageCacheListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint("NewApi")
public class ArticleWebView {
	
	private static final String TAG = ArticleWebView.class.getSimpleName();
	
	// 模板
	public static final String FILE_TEMPLATE = "template_article_content.html";
	public static final String TEMPLATE_CONTENT = "TAG_TEMPLATE_CONTENT";
	public static final String TEMPLATE_CONFIG_FONT = "TAG_TEMPLATE_CONFIG_FONT";
	public static final String TEMPLATE_CONFIG_NIGHT_MODE = "TAG_TEMPLATE_CONFIG_NIGHT_MODE";
	
	// day
	public static final String URL_IMAGE_DEFAULT = "chengning://img_default.jpg";
	public static final String URL_IMAGE_LOADING = "chengning://img_loading.jpg";
	public static final String URL_IMAGE_ERROR = "chengning://img_error.jpg";
	public static final String URL_IMAGE_DEFAULT_VIDEO = "chengning://img_default_video.jpg";
	// night
	public static final String URL_IMAGE_DEFAULT_NIGHT = "chengning://img_default_night.jpg";
	public static final String URL_IMAGE_LOADING_NIGHT = "chengning://img_loading_night.jpg";
	public static final String URL_IMAGE_ERROR_NIGHT = "chengning://img_error_night.jpg";
	public static final String URL_IMAGE_DEFAULT_VIDEO_NIGHT = "chengning://img_default_video_night.jpg";
	
	// html标签
	public static final String TAG_NEWLINE = "<br />";
	public static final String TAG_P_START = "<p>";
	public static final String TAG_P_END = "</p>";
	// img
	public static final String TAG_IMAGE_START = "<div class=\"cont_img\"><img src=\"" + URL_IMAGE_LOADING
			+ "\" src_default=\"" + URL_IMAGE_DEFAULT
			+ "\" src_loading=\"" + URL_IMAGE_LOADING
			+ "\" src_error=\"" + URL_IMAGE_ERROR
			+ "\" src_origin=\"";
	public static final String TAG_IMAGE_END = "\" noimage=\"0\" onclick=\"imgclick(this)\" onerror=\"imgerror(this)\" /></div>";

	public static final String TAG_NO_PIC_IMAGE_START = "<div class=\"cont_img\"><img src=\"" + URL_IMAGE_DEFAULT
			+ "\" src_default=\"" + URL_IMAGE_DEFAULT
			+ "\" src_loading=\"" + URL_IMAGE_LOADING
			+ "\" src_error=\"" + URL_IMAGE_ERROR
			+ "\" src_origin=\"";
	public static final String TAG_NO_PIC_IMAGE_END = "\" noimage=\"1\" onclick=\"noimgclick(this)\" onerror=\"imgerror(this)\" /></div>";
	// video img
	public static final String TAG_VIDEO_IMAGE_START = "<div class=\"cont_img\"><img src=\"" + URL_IMAGE_LOADING
			+ "\" src_default=\"" + URL_IMAGE_DEFAULT_VIDEO
			+ "\" src_loading=\"" + URL_IMAGE_LOADING
			+ "\" src_error=\"" + URL_IMAGE_DEFAULT_VIDEO
			+ "\" src_origin=\"";
	public static final String TAG_VIDEO_IMAGE_END = "\" noimage=\"0\" onclick=\"videoimgclick(this)\" onerror=\"imgerror(this)\" /></div>";
	public static final String TAG_NO_PIC_VIDEO_IMAGE_START = "<div class=\"cont_img\"><img src=\"" + URL_IMAGE_DEFAULT_VIDEO
			+ "\" src_default=\"" + URL_IMAGE_DEFAULT_VIDEO
			+ "\" src_loading=\"" + URL_IMAGE_LOADING
			+ "\" src_error=\"" + URL_IMAGE_DEFAULT_VIDEO
			+ "\" src_origin=\"";
	public static final String TAG_NO_PIC_VIDEO_IMAGE_END = "\" noimage=\"1\" onclick=\"videoimgclick(this)\" onerror=\"imgerror(this)\" /></div>";
	
	public static final int REQUEST_PHOTO_PAGE_CODE = 1;
	
	public static final long TIME_OUT = 1000 * 10;
	
	private Activity mContext;
	private ArticlesBean mBean;
	private OnWebViewListener mListener;
	private Handler mHandler;
	
	private WebView mWebView;
	private HashSet<String> mImageUrls;
	private HashSet<String> mVideoImageUrls;
	
	private boolean mIsPageFinishedCalled = false;
	
	private MChannelItemBean mChannelBean;

	private FullScreenShowListener mFullScreenListener;

	private ArticleVideoView articleVideoView;
	
	public LinearLayout build(Activity context, ArticlesBean bean, OnWebViewListener listener, Handler handler){
		mContext = context;
		mBean = bean;
		mListener = listener;
		mHandler = handler;
		mImageUrls = new HashSet<String>();
		mVideoImageUrls = new HashSet<String>();
		
		LinearLayout linearLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.topMargin = context.getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin);
		linearLayout.setLayoutParams(lp);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		ArrayList<String> contentList = bean.getContent();
		final ArrayList<String> picsList = bean.getPics();
		int cLength = contentList != null ? contentList.size() : 0;
		int pLength = picsList != null ? picsList.size() : 0;
		
		StringBuilder sb = new StringBuilder();
		
		// 视频
		if(!TextUtils.isEmpty(bean.getVideo_photo())){
			mVideoImageUrls.add(bean.getVideo_photo());

			if(Common.isTrue(SettingManager.getInst().getNoPicModel())
					){
//					&& !Common.hasImageCache(bean.getVideo_photo())){
				// 无图模式
				sb
				.append(TAG_NO_PIC_VIDEO_IMAGE_START)
				.append(replceImgDefaultWithNightBg(URL_IMAGE_DEFAULT_VIDEO))
				.append(TAG_NO_PIC_VIDEO_IMAGE_END);
			}else{
				sb
				.append(replceImgDefaultWithNightBg(TAG_VIDEO_IMAGE_START))
				.append(bean.getVideo_photo())
				.append(TAG_VIDEO_IMAGE_END);
			}
		}
		
		// 图文对应
		for(int i = 0; i < cLength; i++){
			// text
			String content = contentList.get(i);
			if(!TextUtils.isEmpty(content)){
				// 分段
				if(content.endsWith("\r\n")){
					content = content.substring(0, content.length() - 2);
				}
				String[] array = content.split("\n\n");
				for(String s : array){
					sb
					.append(TAG_P_START)
					.append(s.replace("\n", TAG_NEWLINE))
					.append(TAG_P_END);
					sb.append(TAG_P_START).append(TAG_P_END);
				}
			}
			
			// image
			if(i >= pLength){
				continue;
			}
			String image = picsList.get(i);
			if(!TextUtils.isEmpty(image)){
				mImageUrls.add(image);

				if(Common.isTrue(SettingManager.getInst().getNoPicModel())
						){
//						&& !Common.hasImageCache(image)){
					// 无图模式
					sb
					.append(replceImgDefaultWithNightBg(TAG_NO_PIC_IMAGE_START))
					.append(image)
					.append(TAG_NO_PIC_IMAGE_END);
				}else{
					sb
					.append(replceImgDefaultWithNightBg(TAG_IMAGE_START))
					.append(image)
					.append(TAG_IMAGE_END);
				}
			}
			
		}
		if(pLength > cLength){
			// 多余的img
			for(int i = cLength; i< pLength; i++){
				String image = picsList.get(i);
				if(!TextUtils.isEmpty(image)){
					mImageUrls.add(image);

					if(Common.isTrue(SettingManager.getInst().getNoPicModel())
							){
//							&& !Common.hasImageCache(image)){
						// 无图模式
						sb
						.append(replceImgDefaultWithNightBg(TAG_NO_PIC_IMAGE_START))
						.append(image)
						.append(TAG_NO_PIC_IMAGE_END);
					}else{
						sb
						.append(replceImgDefaultWithNightBg(TAG_IMAGE_START))
						.append(image)
						.append(TAG_IMAGE_END);
					}
				}
			}
		}

		// webview
		String contentStr = sb.toString();
		String template = getFromAssets(context, FILE_TEMPLATE);
		String webData = template;
		// 字体
		int fontConfig = 2;
		FontSize fontSize = FontSize.getFontSize(SettingManager.getInst().getFontSize());
		switch (fontSize) {
		case Small:
			fontConfig = 1;
			break;
		case Middle:
			fontConfig = 2;
			break;
		case Large:
			fontConfig = 3;
			break;
		case ExtraLarge:
			fontConfig = 4;
			break;
		default:
			break;
		}
		webData = webData.replace(TEMPLATE_CONFIG_FONT, String.valueOf(fontConfig));

		int nightMode = Common.isTrue(SettingManager.getInst().getNightModel()) ? 2 : 1; 
		webData = webData.replace(TEMPLATE_CONFIG_NIGHT_MODE, String.valueOf(nightMode));
		
		// content
		webData = webData.replace(TEMPLATE_CONTENT, contentStr);

		mWebView = new WebView(context);

		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setVerticalScrollbarOverlay(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setHorizontalScrollbarOverlay(false);
		mWebView.setMinimumHeight(1000);
		mWebView.requestFocus();
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.setWebViewClient(new MyWebViewClient());
		WebSettings webSettings = mWebView.getSettings();
		
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setJavaScriptEnabled(true);
		// 设置无图模式
//		webSettings.setLoadsImagesAutomatically(!Common.isTrue(SettingManager.getInst().getNoPicModel()));
		// 阻止获取图片，onPageFinished后再加载
//		webSettings.setBlockNetworkImage(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setRenderPriority(RenderPriority.HIGH);
		
		// 处理超时
		mHandler.removeCallbacks(timeoutRunnable);
		mHandler.postDelayed(timeoutRunnable , TIME_OUT);

		mWebView.loadData(webData, "text/html", "utf-8");
		
		mWebView.addJavascriptInterface(new JavascriptInterface(context, bean), "imagelistner");
		
		linearLayout.removeAllViews();
//		if(ArticleVideoView.isBeanVideo(mBean)){
//			// video
//			articleVideoView = new ArticleVideoView();
//			View videoView = articleVideoView.build(context, bean, listener, handler);
//			articleVideoView.setFullScreenListener(mFullScreenListener);
//			linearLayout.addView(videoView);
//		} else {
			linearLayout.addView(mWebView);
//		}

		return linearLayout;
	}
	
	public WebView getWebView(){
		return mWebView;
	}
	
	public void setChannelBean(MChannelItemBean bean) {
		this.mChannelBean = bean;
	}
	
	public void destroy(){
		try {
			mHandler.removeCallbacks(timeoutRunnable);
			mListener = null;
		    mWebView.removeAllViews();
		    mWebView.destroy();
		    mWebView = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Runnable timeoutRunnable = new Runnable() {
		
		@Override
		public void run() {
			if(!mIsPageFinishedCalled){
				if(mListener != null){
					mListener.onTimeout();
				}
			}
		}
	};

	public void setFont(FontSize font){
		int fontConfig = 2;
		FontSize fontSize = FontSize.getFontSize(SettingManager.getInst().getFontSize());
		switch (fontSize) {
		case Small:
			fontConfig = 1;
			break;
		case Middle:
			fontConfig = 2;
			break;
		case Large:
			fontConfig = 3;
			break;
		case ExtraLarge:
			fontConfig = 4;
			break;
		default:
			break;
		}
		if(mWebView != null){
			mWebView.loadUrl("javascript:fontchange('" + fontConfig + "')");
		}
	}

	public void loadImage(){
		if(mWebView != null){
			mWebView.loadUrl("javascript:loadimage()");
		}
	}
	
    public class JavascriptInterface{
		private Activity context;
		private ArticlesBean bean;

		public JavascriptInterface(Activity context, ArticlesBean bean){
			this.context = context;
			this.bean = bean;
		}
		
		/**
		 * js图片点击
		 */
		@android.webkit.JavascriptInterface
		public void openImage(String image){
			int index = 0;
			ArrayList<String> picsList = bean.getPics();
			for(int i = 0; i < picsList.size(); i++){
				if(image.equals(picsList.get(i))){
					index = i;
					break;
				}
			}
			
			Intent intent = new Intent(context, PhotoPageArticleActivity.class);
			intent.putExtra("index", index);
			intent.putExtra("articleBean", bean);
			intent.putExtra("channelBean", mChannelBean);
		    context.startActivityForResult(intent, REQUEST_PHOTO_PAGE_CODE);
		}
		
		@android.webkit.JavascriptInterface
		public void openVideo(String src){
			// video html ：video标签poster属性可能与video_photo一样，导致图片缓存冲突，解决办法为新开进程
			VideoData video = new VideoData();
			video.setUseUrl(!TextUtils.isEmpty(mBean.getVideo_play()));
			video.setUrl(mBean.getVideo_play());
			video.setHtml(mBean.getVideo_html());
			VideoActivity.launch(context, video);
		}
		
	}
	
	// 监听 
    private class MyWebViewClient extends WebViewClient {
    	
    	private ExecutorService executorService;  
    	
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
            return super.shouldOverrideUrlLoading(view, url); 
        }

		@Override
        public WebResourceResponse shouldInterceptRequest(WebView view, final String url){
//			Log.d(TAG, "shouldInterceptRequest: " + url);

        	WebResourceResponse response = null;
            if(mImageUrls.contains(url) 
            		|| mVideoImageUrls.contains(url) 
            		|| URL_IMAGE_DEFAULT.equals(url) 
            		|| URL_IMAGE_LOADING.equals(url) 
            		|| URL_IMAGE_ERROR.equals(url) 
            		|| URL_IMAGE_DEFAULT_VIDEO.equals(url)
            		|| URL_IMAGE_DEFAULT_NIGHT.equals(url) 
            		|| URL_IMAGE_LOADING_NIGHT.equals(url) 
            		|| URL_IMAGE_ERROR_NIGHT.equals(url) 
            		|| URL_IMAGE_DEFAULT_VIDEO_NIGHT.equals(url)
            		){
                try {
                    final PipedOutputStream out = new PipedOutputStream();
                    final PipedInputStream in = new PipedInputStream(out);
                    response = new WebResourceResponse("image/*", "utf-8", in);
                    
                    if(executorService == null){
                    	executorService = Executors.newCachedThreadPool();
                    }

                    executorService.execute(new Runnable() {
						
						@Override
						public void run() {
//							boolean isImageLoaderCache = false;
				        	final boolean isVideo = mVideoImageUrls.contains(url);
                            Bitmap tempBitmap = null;
				        	if(URL_IMAGE_DEFAULT.equals(url) || URL_IMAGE_DEFAULT_NIGHT.equals(url)){
				        		tempBitmap = ImageCacheLoader.getInst().loadImageSync(url, new OnImageCacheListener() {
									
									@Override
									public Bitmap getBitmap() {
										return getEmptyBitmap(R.layout.no_pic_press, R.id.no_pic_inside, R.id.no_pic_inside2);
									}
								});
				        	}else if(URL_IMAGE_LOADING.equals(url) || URL_IMAGE_LOADING_NIGHT.equals(url)){
				        		tempBitmap = ImageCacheLoader.getInst().loadImageSync(url, new OnImageCacheListener() {
									
									@Override
									public Bitmap getBitmap() {
										return getEmptyBitmap(R.layout.no_pic_loading, R.id.no_pic_inside, R.id.no_pic_inside2);
									}
								});
				        	}else if(URL_IMAGE_ERROR.equals(url) || URL_IMAGE_ERROR_NIGHT.equals(url)){
				        		tempBitmap = ImageCacheLoader.getInst().loadImageSync(url, new OnImageCacheListener() {
									
									@Override
									public Bitmap getBitmap() {
										return getEmptyBitmap(R.layout.no_pic_error, R.id.no_pic_inside, R.id.no_pic_inside2);
									}
								});
				        	}else if(URL_IMAGE_DEFAULT_VIDEO.equals(url) || URL_IMAGE_DEFAULT_VIDEO_NIGHT.equals(url)){
				        		tempBitmap = ImageCacheLoader.getInst().loadImageSync(url, new OnImageCacheListener() {
									
									@Override
									public Bitmap getBitmap() {
										return getEmptyBitmapBig(R.layout.no_pic_video, R.id.no_pic_inside, R.id.no_pic_inside2);
									}
								});
				        	}else{
//				        		isImageLoaderCache = true;
								Bitmap bitmap = null;
								try {
									bitmap = Glide.with(mContext).asBitmap().load(url)
                                            .apply(RequestOptions.centerCropTransform()).submit().get();
								} catch (InterruptedException e) {
									e.printStackTrace();
									bitmap = null;
								} catch (ExecutionException e) {
									e.printStackTrace();
									bitmap = null;
								}
								tempBitmap = Common.isTrue(SettingManager.getInst().getNightModel()) ? bitmapWithColorfilter(bitmap) : bitmap;

				        	}
                            handleResponse(out,tempBitmap,url,isVideo);
						}
					});
                }catch (Exception e){
                    e.printStackTrace();

                	response = super.shouldInterceptRequest(view, url);
                }
            }else{
            	response = super.shouldInterceptRequest(view, url);
            }
            return response;
        }
 
        @Override 
        public void onPageFinished(WebView view, String url) { 
            super.onPageFinished(view, url);
            if (view.getProgress() >= 60) {
				mIsPageFinishedCalled = true;

				if(mListener != null){
					mListener.onPageFinished();
				}

				if(mWebView != null){
					mWebView.getSettings().setBlockNetworkImage(false);
				}
				loadImage();
			}
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

    void handleResponse(PipedOutputStream out, Bitmap tempBitmap, String url, boolean isVideo) {
		if((tempBitmap == null || tempBitmap.getWidth() <= 0 || tempBitmap.getHeight() <= 0) && isVideo){
//			isImageLoaderCache = false;
			tempBitmap = ImageCacheLoader.getInst().loadImageSync(url, new OnImageCacheListener() {

				@Override
				public Bitmap getBitmap() {
					return getEmptyBitmapBig(R.layout.no_pic_video, R.id.no_pic_inside, R.id.no_pic_inside2);
				}
			});
		}

		if (tempBitmap != null) {
			try {
				if(isVideo){
					try {
						DisplayUtil.getInst().init(mContext);
						int tenDp = mContext.getResources().getDimensionPixelSize(R.dimen.ten_dp);
						int tarWidth = DisplayUtil.getInst().getScreenWidth() - tenDp * 2;
						float scale = ((float)tarWidth) / ((float)tempBitmap.getWidth());
						Bitmap bitmap2 = Common.bitmapWithImage(mContext, tempBitmap, R.drawable.video_play_icon, scale, 0);
						tempBitmap = bitmap2;
//						isImageLoaderCache = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				out.write(Bitmap2Bytes2(tempBitmap));
				out.close();
				if(!tempBitmap.isRecycled()){
					tempBitmap.recycle();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    private Bitmap getEmptyBitmap(int layoutId, int childId1, int childId2){
		View vL = View.inflate(mContext, layoutId, null);

		View v = vL.findViewById(childId1);
		LayoutParams lp = v.getLayoutParams();
		lp.width = DisplayUtil.getInst().getScreenWidth();
		lp.height = mContext.getResources().getDimensionPixelSize(R.dimen.article_img_empty_height);
		v.setLayoutParams(lp);
		v.setBackgroundResource(Common.isTrue(SettingManager.getInst().getNightModel()) ? 
				R.color.night_bg_color : R.color.common_bg);

		View v2 = vL.findViewById(childId2);
		LayoutParams lp2 = v2.getLayoutParams();
		lp2.width = mContext.getResources().getDimensionPixelSize(R.dimen.article_img_empty_width);
		lp2.height = mContext.getResources().getDimensionPixelSize(R.dimen.article_img_empty_height);
		v2.setLayoutParams(lp2);
		v2.setBackgroundResource(R.color.article_img_empty_bg);
		
		Bitmap bitmap = Common.getViewBitmap_ARGB8888(v, lp.width, lp.height);
    	return bitmap;
    }
    
    private Bitmap getEmptyBitmapBig(int layoutId, int childId1, int childId2){
		View vL = View.inflate(mContext, layoutId, null);

		View v = vL.findViewById(childId1);
		LayoutParams lp = v.getLayoutParams();
		lp.width = DisplayUtil.getInst().getScreenWidth();
		lp.height = mContext.getResources().getDimensionPixelSize(R.dimen.article_img_empty_high_height);
		v.setLayoutParams(lp);
		
		Bitmap bitmap = Common.getViewBitmap_ARGB8888(v, lp.width, lp.height);
    	return bitmap;
    }
	
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	public byte[] Bitmap2Bytes2(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
		return baos.toByteArray();
	}
	
	public String getFromAssets(Context context, String fileName){
		try {
        	InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName) ); 
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
       return "";
    }
	
	public Bitmap bitmapWithColorfilter(Bitmap bitmap) {
		if(bitmap == null){
			return null;
		}
        int width, height;
        height = bitmap.getHeight();
        width = bitmap.getWidth();    

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        PorterDuffColorFilter f = new PorterDuffColorFilter(App.getInst().getResources().getColor(R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
        paint.setColorFilter(f);
        c.drawBitmap(bitmap, 0, 0, paint);
        return bmpGrayscale;
    }
	
	private String replceImgDefaultWithNightBg(String str){
		if(TextUtils.isEmpty(str)){
			return str;
		}
		if(Common.isTrue(SettingManager.getInst().getNightModel())){
			return str.replace(URL_IMAGE_DEFAULT, URL_IMAGE_DEFAULT_NIGHT)
					.replace(URL_IMAGE_LOADING, URL_IMAGE_LOADING_NIGHT)
					.replace(URL_IMAGE_ERROR, URL_IMAGE_ERROR_NIGHT)
					.replace(URL_IMAGE_DEFAULT_VIDEO, URL_IMAGE_DEFAULT_VIDEO_NIGHT);
		}else{
			return str;
		}
	}
	
	public static interface OnWebViewListener{
		public void onPageStarted();
		public void onPageFinished();
		public void onTimeout();
	}
}
