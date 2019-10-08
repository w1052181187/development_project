package com.chengning.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.chengning.common.base.util.BaseCommon;
import com.chengning.common.base.util.BaseSPHelper;

import okhttp3.Headers;

public class HttpCacheControl {

	private BaseSPHelper spHelper;

	public void init(Context context, String siteName, CacheControlMaxAgeListener listener) {
		spHelper = new BaseSPHelper(context);
		String siteMD5 = KeyGenerator.generateMD5(siteName);
		long maxAge = spHelper.getLong(BaseSPHelper.KEY_CACHE_CONTROL_MAXAGE + siteMD5);
		long startTime = spHelper.getLong(BaseSPHelper.KEY_CACHE_CONTROL_START_TIME + siteMD5);
		if ((BaseCommon.getCurTimeMillis() - startTime) < maxAge) {
			listener.success();
		} else {
			listener.failure();
		}
	}
	
	public void saveCacheControlMaxAge(String siteName, Headers headers) {
		final String MAX_AGE = "max-age=";
		for (String name : headers.names()) {
			if (TextUtils.equals(name, "Cache-Control")){
				String[] caches = TextUtils.split(headers.get(name), ",");
				for (String cache : caches) {
					if (cache.contains(MAX_AGE)) {
						String siteMD5 = KeyGenerator.generateMD5(siteName);
						Long time = Long.parseLong(cache.substring(MAX_AGE.length()));
						spHelper.saveLong(BaseSPHelper.KEY_CACHE_CONTROL_MAXAGE + siteMD5, time * 1000);
						spHelper.saveLong(BaseSPHelper.KEY_CACHE_CONTROL_START_TIME + siteMD5, BaseCommon.getCurTimeMillis());
					}
				}
				break;
			}
		}
	}
	
	public interface CacheControlMaxAgeListener {
		
		/**
		 * 处于缓存有效时间内
		 */
		void success();
		
		/**
		 * 超出缓存有效时间
		 */
		void failure();
	}
	
}
