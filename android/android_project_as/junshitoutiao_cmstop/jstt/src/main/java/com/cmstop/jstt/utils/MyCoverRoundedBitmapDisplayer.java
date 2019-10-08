package com.cmstop.jstt.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.cmstop.jstt.App;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.utils.L;

public class MyCoverRoundedBitmapDisplayer implements BitmapDisplayer {
	private final int roundPixels;
	private final int resId;
	
	private float mRate;

	public MyCoverRoundedBitmapDisplayer(int roundPixels, int resId) {
		this.roundPixels = roundPixels;
		this.resId = resId;
	}

	public Bitmap display(Bitmap bitmap, ImageAware imageAware,
			LoadedFrom loadedFrom) {
		if (!(imageAware instanceof ImageViewAware)) {
			throw new IllegalArgumentException(
					"ImageAware should wrap ImageView. ImageViewAware is expected.");
		}
		
		Bitmap outBitmap = null;
		try {
			int realRoundPixels = calculateRoundPixels(bitmap, imageAware, loadedFrom, this.roundPixels);
			Bitmap roundedBitmap =  RoundedBitmapDisplayer.roundCorners(bitmap,
					(ImageViewAware) imageAware, 0);
			outBitmap = roundedBitmap;
			Bitmap tempBitmap = Common.bitmapWithImage(App.getInst(), roundedBitmap, resId, mRate, roundPixels);
			outBitmap = tempBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(outBitmap == null)outBitmap = bitmap;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			
			if(outBitmap == null)outBitmap = bitmap;
		}
		imageAware.setImageBitmap(outBitmap);
		return outBitmap;
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
		    mRate = rate;
		    pixels = (int) ((float)roundPixels / rate + 0.5f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pixels;
	}

}
