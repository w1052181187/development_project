package com.chengning.yiqikantoutiao.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.emotion.weibo.EmotionsDB;
import com.chengning.yiqikantoutiao.highlighttext.ClickableTextViewMentionLinkOnTouchListener;
import com.chengning.yiqikantoutiao.highlighttext.MyURLSpan;
import com.chengning.yiqikantoutiao.task.TaskException;
import com.chengning.yiqikantoutiao.task.WorkTask;
import com.chengning.yiqikantoutiao.util.KeyGenerator;
import com.chengning.yiqikantoutiao.util.LruMemoryCache;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 加载表情，添加链接两个功能<br/>
 * 
 * @author wangdan
 *
 */
public class DynamicTextView extends TextView {

	static final String TAG = "DynamicTextView";
	
	private static final int CORE_POOL_SIZE = 5;
	/**
	 * 默认执行最大线程是128个
	 */
	private static final int MAXIMUM_POOL_SIZE = 128;
	
	private static final int KEEP_ALIVE = 1;
	
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			int count = mCount.getAndIncrement();
			return new Thread(r, "AisenTextView #" + count);
		}
	};
	
	/**
	 * 执行队列，默认是10个，超过10个后会开启新的线程，如果已运行线程大于 {@link #MAXIMUM_POOL_SIZE}，执行异常策略
	 */
	private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);
	
	private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
			sPoolWorkQueue, sThreadFactory);
	
	private static LruMemoryCache<String, SpannableStringBuilder> stringMemoryCache;
	
	private EmotionTask emotionTask;
	
	private SpannableStringBuilder content;
	private int verticalAlignment;
	
	public DynamicTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DynamicTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DynamicTextView(Context context) {
		super(context);
	}
	
	public void setImageSpanAlign(int verticalAlignment){
		this.verticalAlignment = verticalAlignment;
	}

	public synchronized void setContent(SpannableStringBuilder buffer) {
		setContent(buffer, ImageSpan.ALIGN_BASELINE);
	}
	
	public synchronized void setContent(SpannableStringBuilder buffer, int verticalAlignment) {
		if (stringMemoryCache == null) {
			stringMemoryCache = new LruMemoryCache<String, SpannableStringBuilder>(200) {
			};
		}
		
		if (TextUtils.isEmpty(buffer)) {
			super.setText(buffer);
			return;
		}
		
		if (!TextUtils.isEmpty(content) && content.equals(buffer)){
			String key = KeyGenerator.generateMD5(buffer.toString());
			SpannableStringBuilder spannableString = stringMemoryCache.get(key);
			if (spannableString != null) {
				super.setText(spannableString);
			}
			return;
		}
		
		content = buffer;
		setImageSpanAlign(verticalAlignment);
		if (emotionTask != null)
			emotionTask.cancel(true);
		String key = KeyGenerator.generateMD5(buffer.toString());
		SpannableStringBuilder spannableString = stringMemoryCache.get(key);
		if (spannableString != null) {
			
			super.setText(spannableString);
		} else {
			
//			super.setText(buffer);
			emotionTask = new EmotionTask(buffer);
			emotionTask.executeOnExecutor(THREAD_POOL_EXECUTOR);
		}
		
		setClickable(false);
		setOnTouchListener(onTouchListener);
	}
	
	class EmotionTask extends WorkTask<Void, SpannableStringBuilder, Boolean> {

		private SpannableStringBuilder buffer;

		public EmotionTask(SpannableStringBuilder buffer) {
			this.buffer = buffer;
		}

		@Override
		public Boolean workInBackground(Void... params) throws TaskException {
			if (TextUtils.isEmpty(buffer)) {
				return false;
			}
			
			SpannableStringBuilder spannableString = SpannableStringBuilder.valueOf(buffer);
			Matcher emotionMatcher = Pattern.compile(Consts.EMOTION_COMPILE_STR).matcher(spannableString);
			while (emotionMatcher.find()) {
				if (isCancelled()){
					break;
				}
				
				String key = emotionMatcher.group(0);

				key = key.replace("/", "");
				int k = emotionMatcher.start();
				int m = emotionMatcher.end();
				
				Bitmap bitmap = null;
				
				if (emotionSize != 0) {
					bitmap = EmotionsDB.getEmotionBitmap(key, emotionSize);
				} else {
					bitmap = EmotionsDB.getEmotionBitmap(key);
				}
				
				if(bitmap == null){
					continue;
				}
				ImageSpan l = new ImageSpan(getContext(), bitmap, verticalAlignment);
				setSpan(spannableString, l, k, m, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			if (isCancelled()){
				return null;
			}
			publishProgress(spannableString);
			
			// @用户名称
			Pattern nicknamePattern = Pattern.compile(Consts.NICKNAME_COMPILE_STR);
			String scheme = Consts.BASE_SCHEME + "userinfo://";
			Linkify.addLinks(spannableString, nicknamePattern, scheme);
			
//			ForegroundColorSpan span = new ForegroundColorSpan(getContext().getResources().getColor(R.color.home_bottom_txt_sel));
//			setSpan(spannableString,span, 0, nickName.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//			Linkify.add

			// 网页链接
//			Linkify.addLinks(spannableString, Pattern.compile("((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)"), null);
			
			Pattern sitePattern = Pattern.compile(Consts.SITE_COMPILE_STR);
			Linkify.addLinks(spannableString, sitePattern, null);
			Matcher siteMatcher = sitePattern.matcher(spannableString);
			while (siteMatcher.find()) {
				if (isCancelled()){
					break;
				}
				String key = siteMatcher.group(1);
				spannableString = spannableString.replace(siteMatcher.start(), siteMatcher.end(), key);
				siteMatcher = sitePattern.matcher(spannableString);
				
			}
			
			// 话题
			Pattern topicPattern = Pattern.compile(Consts.TOPIC_COMPILE_STR);
			scheme = Consts.BASE_SCHEME + "topic://";
			Linkify.addLinks(spannableString, topicPattern, scheme);

			URLSpan[] urlSpans = spannableString.getSpans(0, spannableString.length(), URLSpan.class);
			for (URLSpan urlSpan : urlSpans) {
				if (isCancelled()){
					break;
				}
				replaceSpan(spannableString, urlSpan);
			}
			if (isCancelled()){
				return null;
			}
			
			publishProgress(spannableString);
			
			String key = KeyGenerator.generateMD5(spannableString.toString());
			if (isCancelled()){
				return null;
			}
			stringMemoryCache.put(key, spannableString);
			return null;
		}
		
		@Override
		protected void onProgressUpdate(SpannableStringBuilder... values) {
			super.onProgressUpdate(values);
			
			try {
				if (values != null && values.length > 0)
					setText(values[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void setSpan(final SpannableStringBuilder spannableString, final Object what, final int start, final int end, final int flags){
		spannableString.setSpan(what, start, end, flags);
	}
	
	private void replaceSpan(final SpannableStringBuilder spannableString, final URLSpan urlSpan){
		MyURLSpan weiboSpan = new MyURLSpan(urlSpan.getURL());
		int start = spannableString.getSpanStart(urlSpan);
		int end = spannableString.getSpanEnd(urlSpan);
		spannableString.removeSpan(urlSpan);
		spannableString.setSpan(weiboSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

		ClickableTextViewMentionLinkOnTouchListener listener = new ClickableTextViewMentionLinkOnTouchListener();

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return listener.onTouch(v, event);
		}
	};

	private int emotionSize;

//	private String nickName;
//
//	public void setContent(String nickname, String text) {
//		this.nickName = nickname;
//		SpannableStringBuilder buffer = new SpannableStringBuilder();
//		buffer.append(nickname).append(":  ").append(text);
//		setContent(buffer);
//	}

	public void setContent(String text) {
		setContent(new SpannableStringBuilder(text));
	}

	public void setContent(String string, int alignBottom) {
		setContent(new SpannableStringBuilder(string), alignBottom);
	}

	public void setEmotionSize(int size) {
		this.emotionSize = size;
	}
}
