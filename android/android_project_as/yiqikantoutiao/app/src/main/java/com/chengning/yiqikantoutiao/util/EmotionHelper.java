package com.chengning.yiqikantoutiao.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.emotion.weibo.Emotion;
import com.chengning.yiqikantoutiao.emotion.weibo.EmotionsDB;
import com.chengning.yiqikantoutiao.highlighttext.ClickableTextViewMentionLinkOnTouchListener;
import com.chengning.yiqikantoutiao.highlighttext.MyURLSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmotionHelper {
	
	private static int mEmotionSize;

	public static void handleEmotionInsert(EditText edit, Emotion emotion, int emotionSize){
		mEmotionSize = emotionSize;
		Editable editAble = edit.getEditableText();
		int start = edit.getSelectionStart();
		editAble.insert(start, emotion.getKey());
	}
	
	public static void handleInsert(EditText edit, String str){
		Editable editAble = edit.getEditableText();
		int start = edit.getSelectionStart();
		editAble.insert(start, str);
	}
	
	public static void handleTopicInsert(EditText edit){
        int index = edit.getSelectionStart();
        Editable editable = edit.getText();
        editable.insert(index, "##");
        // 检查##
        if(editable.length() >= index+2){
	        CharSequence temp = editable.subSequence(index, index+2);
	        String tempS = temp.toString();
	        if("##".equals(tempS)){
	        	edit.setSelection(index+1);
	        }
        }
	}

	/**
	 * 内容监听
	 */
	public static TextWatcher generateTextWatcher(final EditText edit, final String[] str){
		TextWatcher editContentWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// 设置长度提示
			String content = edit.getText().toString();
//			txtContentSurplus.setText((MAX_STATUS_LENGTH - AisenUtil.getStrLength(content)) + "");
//			
//			if (AisenUtil.getStrLength(content) > MAX_STATUS_LENGTH) {
//				txtErrorHint.setVisibility(View.VISIBLE);
//				txtErrorHint.setText(String.format(getString(R.string.error_length_too_long), AisenUtil.getStrLength(content) - MAX_STATUS_LENGTH));
//			}
//			else {
//				txtErrorHint.setVisibility(View.GONE);
//			}
			
			str[0] = content;
			
			Log.d(EmotionHelper.class.getSimpleName(), "TextWatcher onTextChanged: " + content);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			MyURLSpan[] a = s.getSpans(0, s.length(), MyURLSpan.class);
	        for (int i = 0; i < a.length; i++) {
	        	MyURLSpan m = a[i];
	        	int start = s.getSpanStart(m);
	        	int end = s.getSpanEnd(m);
	        	if(start < 0 || end <= 0){
	        		continue;
	        	}

	        	String str = s.toString().substring(start, end);
        		String url = m.getURL();
	        	if (url.matches("(.*)" + Consts.NICKNAME_COMPILE_STR)) {
        			int startIndex = url.indexOf("@");
        			String name = url.substring(startIndex);
        			if(!name.equals(str)){
    	        		s.delete(start, end);
        			}
	        	}else if(url.matches("(.*)" + Consts.TOPIC_COMPILE_STR)){
        			if(!str.matches(Consts.TOPIC_COMPILE_STR)){
    	        		s.delete(start, end);
        			}
	        	}
	        }
		}
	};
	return editContentWatcher;
	}

	/**
	 * 输入文本的过滤，根据输入替换库中的表情
	 */
	public static InputFilter generateEmotionFilter(){
		InputFilter emotionFilter = new InputFilter() {

			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				// 是delete直接返回
				if ("".equals(source)) {
					return null;
				}
				// 超链接
				

				// 表情
				Bitmap bitmap = EmotionsDB.getEmotionBitmap(source.toString(),mEmotionSize);
				if (bitmap != null) {
					SpannableString emotionSpanned = new SpannableString(source.toString());
					ImageSpan span = new ImageSpan(App.getInst(), bitmap);
					emotionSpanned.setSpan(span, 0, source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					return emotionSpanned;
				} else {
					return source;
				}
			}

		};
		return emotionFilter;
	}
	
	public static InputFilter generateHyperlinkFilter(){

		InputFilter hyperlinkFilter = new InputFilter() {

			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				// 是delete直接返回
				if ("".equals(source)) {
					return null;
				}

				SpannableStringBuilder spannableString = SpannableStringBuilder.valueOf(source);
				// 用户名称
				Pattern pattern = Pattern.compile(Consts.NICKNAME_COMPILE_STR);
				String scheme = Consts.BASE_SCHEME + "userinfo://";
				Linkify.addLinks(spannableString, pattern, scheme);

				// 网页链接
				Pattern sitePattern = Pattern.compile(Consts.SITE_COMPILE_STR);
				Linkify.addLinks(spannableString, sitePattern, null);
				Matcher siteMatcher = sitePattern.matcher(spannableString);
				while (siteMatcher.find()) {
					String key = siteMatcher.group(1);
					spannableString = spannableString.replace(siteMatcher.start(), siteMatcher.end(), key);
					siteMatcher = sitePattern.matcher(spannableString);
					
				}
//				// 话题
				Pattern dd = Pattern.compile(Consts.TOPIC_COMPILE_STR);
				scheme = Consts.BASE_SCHEME + "topic://";
				Linkify.addLinks(spannableString, dd, scheme);

				URLSpan[] urlSpans = spannableString.getSpans(0, spannableString.length(), URLSpan.class);
				MyURLSpan weiboSpan = null;
				for (URLSpan urlSpan : urlSpans) {
					weiboSpan = new MyURLSpan(urlSpan.getURL());
					int sStart = spannableString.getSpanStart(urlSpan);
					int sEnd = spannableString.getSpanEnd(urlSpan);
					spannableString.removeSpan(urlSpan);
					spannableString.setSpan(weiboSpan, sStart, sEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				return spannableString;
			}
		};
		return hyperlinkFilter;
	}

	public static View.OnTouchListener generateTouchListener(){
		View.OnTouchListener onTouchListener = new View.OnTouchListener() {

			ClickableTextViewMentionLinkOnTouchListener listener = new ClickableTextViewMentionLinkOnTouchListener();

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return listener.onTouch(v, event);

			}
		};
		return onTouchListener;
	}
	
	public static String appendContent() {
		return "";
	}

	public static Bitmap zoomBitmap(Bitmap source, int width) {
		Matrix matrix = new Matrix();
		float scale = width * 1.0f / source.getWidth();
		matrix.setScale(scale, scale);
		Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
		try {
			Log.d(EmotionHelper.class.getSimpleName(), String.format("zoom bitmap, source(%d,%d) result(%d,%d)", source.getWidth(), source.getHeight(), result.getWidth(),
							result.getHeight()));
		} catch (Exception e) {
		}
//		source.recycle();
		return result;
	}
	
}
