package com.cmstop.jstt.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.L;

public class MyRoundedBitmapDisplayer implements BitmapDisplayer {
	private final int roundPixels;

	public MyRoundedBitmapDisplayer(int roundPixels) {
		this.roundPixels = roundPixels;
	}

	public Bitmap display(Bitmap bitmap, ImageAware imageAware,
			LoadedFrom loadedFrom) {
		if (!(imageAware instanceof ImageViewAware)) {
			throw new IllegalArgumentException(
					"ImageAware should wrap ImageView. ImageViewAware is expected.");
		}
		Bitmap roundedBitmap = null;
		try {
			int realRoundPixels = calculateRoundPixels(bitmap, imageAware, loadedFrom, this.roundPixels);
			roundedBitmap =  RoundedBitmapDisplayer.roundCorners(bitmap,
					(ImageViewAware) imageAware, realRoundPixels);
		} catch (Exception e) {
			e.printStackTrace();
			
			if(roundedBitmap == null)roundedBitmap = bitmap;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();

			if(roundedBitmap == null)roundedBitmap = bitmap;
		}
		imageAware.setImageBitmap(roundedBitmap);
		return roundedBitmap;
	}
	
	private int calculateRoundPixels(Bitmap bitmap, ImageAware imageAware,
			LoadedFrom loadedFrom, int roundPixels){
		int pixels = roundPixels;
	    ImageView imageView = (ImageView) imageAware.getWrappedView();
	    if (imageView == null) {
	      L.w("View is collected probably. Can't round bitmap corners without view properties.", new Object[0]);
	      return pixels;
	    }
	    try {
		    int bw = bitmap.getWidth();
		    int bh = bitmap.getHeight();
		    int vw = imageAware.getWidth();
		    int vh = imageAware.getHeight();
		    if(vw <= 0 || vh <= 0){
		    	return pixels;
		    }
		    if(bw <= 0 || bh <= 0){
		    	return pixels;
		    }
		    float wf = (float)vw / (float)bw;
		    float hf = (float)vh / (float)bh;
		    float rate = wf > hf ? wf : hf;
		    pixels = (int) ((float)roundPixels / rate + 0.5f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pixels;
	}

}
