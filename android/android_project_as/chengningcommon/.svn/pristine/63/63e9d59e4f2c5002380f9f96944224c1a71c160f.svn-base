package com.handmark.pulltorefresh.library.extras;

import com.handmark.pulltorefresh.library.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

 


@SuppressLint("ResourceAsColor")
public class PullToRefreshFootView extends RelativeLayout  
 {
	static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
	static final int ROTATION_ANIMATION_DURATION = 1200;
	String str; 
	Context context;  
	private TextView mFootText;
	private RelativeLayout mRoot;
	private ImageView mFootImage;
	private RotateAnimation mRotateAnimation;
	private Matrix mHeaderImageMatrix;
	 
	public PullToRefreshFootView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	

	public PullToRefreshFootView(Context context){
		super(context);
		((Activity) getContext()).getLayoutInflater().inflate(R.layout.pull_to_refresh_foot_horizontal, this);
	 
		mRoot = (RelativeLayout)findViewById(R.id.footer_pull_to_refresh_root);
        mFootText = (TextView) findViewById(R.id.footer_pull_to_refresh_text);
        mFootImage = (ImageView) findViewById(R.id.footer_pull_to_refresh_image); 
        mFootImage.setScaleType(ScaleType.MATRIX);
		mHeaderImageMatrix = new Matrix();
		mFootImage.setImageMatrix(mHeaderImageMatrix);

		mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);
		mFootImage.startAnimation(mRotateAnimation);
		SetLoading();
		this.context = context; 
	}
	
	public void SetLoadFull()
	{
        mFootText.setText(R.string.pull_to_refresh_footer_full_label); 
        mFootImage.setVisibility(GONE);
	}
	
//	public void SetClickRefresh()
//	{
//		mFootText.setText(R.string.pull_to_refresh_footer_pull_label_faild);
//        mFootProgressBar.setVisibility(INVISIBLE);
//	}
	public void SetRefresh(OnClickListener click)
	{
		mRoot.setOnClickListener(click);
		mFootImage.setVisibility(GONE);
		mFootText.setText(R.string.pull_to_refresh_touch_label);
	}
	
	public void SetLoading()
	{
		mFootText.setText(R.string.pull_to_refresh_from_bottom_refreshing_label);
		mFootImage.setVisibility(VISIBLE);
		mFootImage.startAnimation(mRotateAnimation);
	}

	 
}
