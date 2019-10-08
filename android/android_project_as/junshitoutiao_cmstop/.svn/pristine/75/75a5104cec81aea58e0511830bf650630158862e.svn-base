package com.cmstop.jstt.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageCacheLoader {

	private static ImageCacheLoader loader = null;

	private ImageCacheLoader() {

	}

	public static ImageCacheLoader getInst() {
		if (loader == null) {
			loader = new ImageCacheLoader();
		}
		return loader;
	}

	public Bitmap loadImageSync(String url, OnImageCacheListener listener) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		MemoryCacheAware cache = ImageLoader.getInstance().getMemoryCache();
		Bitmap bmp = (Bitmap) cache.get(url);
		if (bmp != null && !bmp.isRecycled()) {
			// do nothing
		} else {
			bmp = listener.getBitmap();
			cache.put(url, bmp);
		}
		return bmp;
	}

	public static interface OnImageCacheListener {
		public Bitmap getBitmap();
	}

}
