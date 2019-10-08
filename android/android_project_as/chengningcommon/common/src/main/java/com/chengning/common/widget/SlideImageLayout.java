package com.chengning.common.widget;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.chengning.common.R;

public class SlideImageLayout {

	private ArrayList<ImageView> mImageList = null;
	private Context mContext = null;
	private ImageView[] mImageViews = null;
	private ImageView mImageView = null;
	private int pageIndex = 0;

	public SlideImageLayout(Context context) {
		this.mContext = context;
		mImageList = new ArrayList<ImageView>();
	}

	public View getSlideImageLayout(ImageView img) {
		LinearLayout imageLinerLayout = new LinearLayout(mContext);
		imageLinerLayout.addView(img);
		mImageList.add(img);

		return imageLinerLayout;
	}

	public View getSlideImageLayout(ImageView img, LinearLayout.LayoutParams lp) {
		LinearLayout imageLinerLayout = new LinearLayout(mContext);
		imageLinerLayout.addView(img, lp);
		mImageList.add(img);
		return imageLinerLayout;
	}

	public View getLinearLayout(View view, int width, int height) {
		LinearLayout linerLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
				width, height, 1);
		linerLayout.setPadding(2, 0, 2, 0);
		linerLayout.addView(view, linerLayoutParames);

		return linerLayout;
	}

	public void setCircleImageLayout(int size) {
		mImageViews = new ImageView[size];
	}

	public ImageView getCircleImageLayout(int index) {
		mImageView = new ImageView(mContext);
		mImageView.setLayoutParams(new LayoutParams(10, 10));
		mImageView.setScaleType(ScaleType.FIT_XY);

		mImageViews[index] = mImageView;

		if (index == 0) {
			mImageViews[index].setBackgroundResource(R.drawable.dot_focused);
		} else {
			mImageViews[index].setBackgroundResource(R.drawable.dot_normal);
		}

		return mImageViews[index];
	}

	public void setPageIndex(int index) {
		pageIndex = index;
	}
}
