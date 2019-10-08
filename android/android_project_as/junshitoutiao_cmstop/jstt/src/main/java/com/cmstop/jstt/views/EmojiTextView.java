package com.cmstop.jstt.views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class EmojiTextView extends TextView {
	
	public final  static String EMOJI_COMPILE_STR = "\\[emoji\\](.+?)\\[/emoji\\]";

	public EmojiTextView(Context context) {
		super(context);
	}
	public EmojiTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		String temp = text.toString();
		Matcher emotionMatcher = Pattern.compile(EMOJI_COMPILE_STR).matcher(temp);
		try {
			while (emotionMatcher.find()) {
				
				String key = emotionMatcher.group(0);
				String value = emotionMatcher.group(1);
	
				temp = temp.replace(key, getEmojiStringByUnicode(Integer.valueOf(value,16)));
			}
		} catch(Exception e) {
		}
		text = temp;
		super.setText(text, type);
	}
	
	private String getEmojiStringByUnicode(int unicode){
		return new String(Character.toChars(unicode));
	}

}
