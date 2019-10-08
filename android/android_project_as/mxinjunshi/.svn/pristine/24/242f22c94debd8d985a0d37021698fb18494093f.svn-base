package com.shenyuan.militarynews.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

@SuppressLint("NewApi")
public class MyGifView extends View {
	private long movieStart;
	private Movie movie;
	private Matrix mDrawMatrix;

	public MyGifView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setGifResource(int resId) {
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

		// 以文件流（InputStream）读取进gif图片资源
		movie = Movie.decodeStream(getResources().openRawResource(resId));
		movieStart = 0;
		calculateMatrix();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (movie != null) {
			int saveCount = canvas.getSaveCount();
			if (mDrawMatrix != null && !mDrawMatrix.isIdentity()) {
				canvas.concat(mDrawMatrix);
			}
			
			long curTime = android.os.SystemClock.uptimeMillis();
			// 第一次播放
			if (movieStart == 0) {
				movieStart = curTime;
			}
			
			int duraction = movie.duration();
			int relTime = (int) ((curTime - movieStart) % duraction);
			movie.setTime(relTime);
			movie.draw(canvas, 0, 0);
			// 强制重绘
			invalidate();
			
			canvas.restoreToCount(saveCount);
		}else{
			super.onDraw(canvas);
		}
	}

	private void calculateMatrix() {
		if (movie == null) {
			return;
		}

		int movieWidth = movie.width();// 实际像素宽高
		int movieHeight = movie.height();

		LayoutParams lp = getLayoutParams();
		int vWidth = lp.width;
		int vHeight = lp.height;
		
		mDrawMatrix = new Matrix();
		float scale;

		scale = Math.min((float) vWidth / (float) movieWidth, (float) vHeight
				/ (float) movieHeight);
		float dx = (int) ((vWidth - movieWidth * scale) * 0.5f + 0.5f);
		float dy = (int) ((vHeight - movieHeight * scale) * 0.5f + 0.5f);
		mDrawMatrix.setScale(scale, scale); // 设置缩放
		mDrawMatrix.postTranslate(dx, dy); // 设置平移
	}
}
