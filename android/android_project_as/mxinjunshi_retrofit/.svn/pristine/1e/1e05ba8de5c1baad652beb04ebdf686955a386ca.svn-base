package com.shenyuan.militarynews.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;

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

		Bitmap bmp = null;
//		try {
//			bmp = Picasso.with(App.getInst()).load(url).get();
//		} catch (IOException e) {
//			bmp = null;
//		}
//		if (bmp != null && !bmp.isRecycled()) {
//			// do nothing
//		} else {
			bmp = listener.getBitmap();
//		}
		return bmp;
	}

	public static interface OnImageCacheListener {
		public Bitmap getBitmap();
	}

}
