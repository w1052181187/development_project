package com.chengning.yiqikantoutiao.highlighttext;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.provider.Browser;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.AdDetailActivity;

public class MyURLSpan extends ClickableSpan implements ParcelableSpan {

	private final String mURL;
	private final int morenTextColor = App.getInst().getResources().getColor(R.color.article_cmt_name);
	private final int nightTextColor = App.getInst().getResources().getColor(R.color.night_text_color);
	private final int nameTextColor = App.getInst().getResources().getColor(R.color.article_cmt_name);

	public MyURLSpan(String url) {
		mURL = url;
	}

	public MyURLSpan(Parcel src) {
		mURL = src.readString();
	}

	public int getSpanTypeId() {
		return 11;
	}
	
	// 兼容Android 6.0
	public int getSpanTypeIdInternal(){
		return getSpanTypeId();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mURL);
	}
	
	// 兼容Android 6.0
    public void writeToParcelInternal(Parcel dest, int flags){
    	writeToParcel(dest, flags);
    }

	public String getURL() {
		return mURL;
	}
	
	public String getScheme() {
		if (TextUtils.isEmpty(mURL)) {
			return "";
		}
		Uri uri = Uri.parse(mURL);
		if (uri == null) {
			return "";
		}
		return uri.getScheme();
	}

	@Override
	public void onClick(View widget) {
		Context context = widget.getContext();
		String url = getURL();
		int length = url.length();
		
		if (!TextUtils.isEmpty(url)) {
			String startStr = "[URL]";
			String endStr = "[/URL]";
			
			if (url.startsWith(startStr) && url.endsWith(endStr)) {
				url = url.subSequence(startStr.length(), length - endStr.length()).toString();
			}
			
			if (url.startsWith("www.")) {
				url = "http://" + url;
			}
			Uri uri = Uri.parse(url);
			String scheme = uri.getScheme();
			if (!TextUtils.isEmpty(scheme) && scheme.startsWith("http")) {
				AdDetailActivity.launch((Activity)context, AdDetailActivity.TYPE_ARTICLE, url, url);
			} else {
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
					context.startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void onLongClick(View widget) {
		Uri data = Uri.parse(getURL());
		if (data != null) {
			String d = data.toString();
			String newValue = "";
			if (d.startsWith("com.m.ui")) {
				int index = d.lastIndexOf("/");
				newValue = d.substring(index + 1);
			} else if (d.startsWith("http")) {
				newValue = d;
			}
			if (!TextUtils.isEmpty(newValue)) {
				ClipboardManager cm = (ClipboardManager) widget.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setPrimaryClip(ClipData.newPlainText("ui", newValue));
				// TODO(toast)
//				MToast.showMessage(String.format(widget.getContext().getString(R.string.have_copied), newValue));
			}
		}
	}

	@Override
	public void updateDrawState(TextPaint tp) {
//		String scheme = getScheme();
//		if (!TextUtils.isEmpty(scheme ) && scheme.startsWith(Consts.BASE_SCHEME + "userinfo")) {
//			tp.setColor(nameTextColor);
//			return;
//			
//		} 
		tp.setColor(morenTextColor);
		
	}
	
}
