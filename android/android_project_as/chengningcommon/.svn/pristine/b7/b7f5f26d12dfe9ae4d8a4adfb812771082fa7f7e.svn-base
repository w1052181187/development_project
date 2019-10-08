package com.chengning.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class DisplayUtil {
	
	public static float[] COLOR_FLIP_BACKGROUND = {0.2706f, 0.2706f, 0.2706f, 1f};

	private static DisplayUtil displayUtil;

	private int screenWidth;
	private int screenHeight;
	private float density;
	private int statusBarHeight;
	
	private boolean isInited;

	private DisplayUtil() {

	}

	public static DisplayUtil getInst() {
		if (displayUtil == null) {
			displayUtil = new DisplayUtil();
		}
		return displayUtil;
	}

	public void init(Activity context) {
		WindowManager windowManager = context.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		density = context.getResources().getDisplayMetrics().density;

		statusBarHeight = computeStatusBarHeight(context);
		
		isInited = true;
	}
	
	public void checkInit(){
		if(!isInited){
			throw new IllegalStateException("DisplayUtil is not inited.");
		}
	}

	private int computeStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	public int getScreenWidth() {
		checkInit();
		return screenWidth;
	}

	public int getScreenHeight() {
		checkInit();
		return screenHeight;
	}

	public float getDensity() {
		checkInit();
		return density;
	}

	public int getStatusBarHeight() {
		checkInit();
		return statusBarHeight;
	}

	public static Bitmap takeViewshot(View view, Bitmap.Config config) {
		// Bitmap.Config.RGB_565

		int width = view.getWidth();
		int height = view.getHeight();

		if (view != null && width > 0 && height > 0) {
			Bitmap bitmap = Bitmap.createBitmap(width, height, config);
			Canvas canvas = new Canvas(bitmap);
			view.draw(canvas);

			return bitmap;
		} else {
			return null;
		}
	}
	
	public int dip2px(float dipValue){
		checkInit();
		return (int) (dipValue * density + 0.5f);
	}
	
	public int px2dip(float pxValue){
		checkInit();
		return (int) (pxValue / density + 0.5f);
	}	
	
	public static int[] measureView(View view){
		int[] size = new int[2]; 
		int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
		int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		view.measure(width,height); 
		size[0]=view.getMeasuredWidth(); 
		size[1]=view.getMeasuredHeight();
		return size;
	}
	
	public static void recyleBitmap(Bitmap bitmap){
		if(bitmap != null && !bitmap.isRecycled()){
			bitmap.recycle();
		}
	}
	
	public static TarBitmap creatTopScaleBitmap(Bitmap src, int srcWidth, int srcHeight, int tarWidth, int maxHeight){
		TarBitmap tarBitmap = new TarBitmap();
		float scale =  ((float)tarWidth) / srcWidth;
		int height = (int) (maxHeight / scale);
		height = Math.min(height, srcHeight);
		int tarHeight = (int) (height * scale);
		if(height == 0){
			tarBitmap.bitmap = null;
			tarBitmap.width = tarWidth;
			tarBitmap.height = tarHeight;
		}else{
			Matrix m = new Matrix();
			m.postScale(scale, scale);
			Bitmap tar = Bitmap.createBitmap(src, 0, 0, srcWidth, height, m, false);
			tarBitmap.bitmap = tar;
			tarBitmap.width = tarWidth;
			tarBitmap.height = tarHeight;
		}
		return tarBitmap;
	}
	
	public static TarBitmap createCenterCropBitmap(Bitmap src, int srcWidth, int srcHeight, int boundWidth, int boundHeight){
		int width = Math.min(srcWidth, boundWidth);
		int height = Math.min(srcHeight, boundHeight);

		int x = srcWidth / 2 - width / 2;
		int y = srcHeight / 2 - height / 2;
		
		Bitmap tar = Bitmap.createBitmap(src, x, y, width, height);
		TarBitmap tarBitmap = new TarBitmap();
		tarBitmap.bitmap = tar;
		tarBitmap.width = width;
		tarBitmap.height = height;
		return tarBitmap;
	}
	
	public boolean needTrimWoScreenSize(Bitmap bitmap){
		checkInit();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		boolean needTrim = width > screenWidth
				|| height > screenHeight;
		return needTrim;
	}
	
	public TarBitmap trimToScreenSize(Bitmap bitmap){
		checkInit();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		TarBitmap tarBitmap = createCenterCropBitmap(bitmap, width, height, screenWidth, screenHeight);
		return tarBitmap;
	}
	
	public static class TarBitmap{
		Bitmap bitmap;
		int width;
		int height;
		
		public Bitmap getBitmap(){
			return bitmap;
		}
		
		public int getWidth(){
			return width;
		}
		
		public int getHeight(){
			return height;
		}
	}
}
