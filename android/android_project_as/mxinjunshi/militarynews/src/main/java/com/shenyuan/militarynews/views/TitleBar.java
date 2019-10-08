package com.shenyuan.militarynews.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.utils.Utils;

// <include android:id="@+id/title_bar_layout" layout="@layout/title_bar" />
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class TitleBar {
	
	private static final int TITLE_LEFT_TX = 1;
	private static final int TITLE_LEFT_BT = 2;
	private static final int TITLE_LEFT_URL_CIRCLE_IMG = 3;

	private RelativeLayout mLayout;
	
	private TextView mTitle;
	private View mBack;
	private TextView mBackText;
	
	private View mLeftButton;
	private View mLeftGap;
	private View mLeftRefreshButton;
	private View mRightButton;
	private View mSecondRightButton;
	private View mTitleGap;
	private View mTitleRefreshButton;
	
	private Activity mContext;
	private LayoutInflater mInflater;

	private Drawable drawable;
	
	public TitleBar(Activity context, View root){
		mContext = context;
		mLayout = (RelativeLayout) root.findViewById(R.id.title_bar_layout);
		
		mTitle = (TextView) mLayout.findViewById(R.id.title_bar_title);
		mBack = mLayout.findViewById(R.id.title_bar_back);
		mBackText = (TextView) mLayout.findViewById(R.id.title_bar_back_text);
	}
	
	public TitleBar(Activity context, boolean isSingleInActivity){
		mContext = context;
		mLayout = (RelativeLayout) context.findViewById(R.id.title_bar_layout);
		
		mTitle = (TextView) mLayout.findViewById(R.id.title_bar_title);
		mBack = mLayout.findViewById(R.id.title_bar_back);
		mBackText = (TextView) mLayout.findViewById(R.id.title_bar_back_text);
	}
	
	public int getHeight(){
		return mLayout.getHeight();
	}
	
	public Activity getContext(){
		return mContext;
	}
	
	RelativeLayout getLayout(){
		return mLayout;
	}
	
	void checkLayoutInflater(){
		if(mInflater == null){
			mInflater = LayoutInflater.from(mContext);
		}
	}

	void removeViewIfCan(View child){  
		if(child != null){
			mLayout.removeView(child);
		}
	}
	
	public void show(){
		mLayout.setVisibility(View.VISIBLE);
	}
	
	public void dismiss(){
		mLayout.setVisibility(View.GONE);
	}
	
	/**
	 * 设置TitleBar的背景色
	 * @param color
	 */
	public void setBackgroundColor(int color){
		mLayout.setBackgroundColor(color);
	}
	
	// title
	/**
	 * 设置标题并显示，不设置则默认不显示
	 */
	public void setTitle(String title){
		mTitle.setText(title);
		mTitle.setVisibility(View.VISIBLE);
	}
	public void setTitle(String title, int color){
		setTitle(title);
		mTitle.setTextColor(color);
	}
	
	public void setTitle(int drawableId){
		drawable = mContext.getResources().getDrawable(drawableId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		mTitle.setCompoundDrawables(null, null, drawable, null);
		mTitle.setVisibility(View.VISIBLE);
	}
	
	public void hideTitle(){
		mTitle.setVisibility(View.GONE);
	}
	
	public TextView getTitle(){
		return mTitle;
	}

	public void setTitleOnClickListener(OnClickListener listener){
		mTitle.setOnClickListener(listener);
	}
	
	// back
	public void setBackText(String back){
		mBackText.setText(back);
		setBackOnClickListener(null, true);
		mBack.setVisibility(View.VISIBLE);
	}
	
	public void setBackText(String back, int color){
		setBackText(back);
		mBackText.setTextColor(color);
	}
	
	/**
	 * 
	 * @param back
	 * @param color
	 */
	public void setBackText(String back, int color, Drawable drawable){
		setBackText(back);
		mBackText.setTextColor(color);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		mBackText.setCompoundDrawables(drawable, null, null, null);
	}
	
	/**
	 * 显示默认的返回按钮
	 */
	public void showDefaultBack(){
		setBackText("");
	}
	
	/**
	 * 设置返回按钮监听
	 * @param listener
	 */
	public void setBackOnClickListener(final BackOnClickListener listener){
		setBackOnClickListener(listener, true);
	}
	/**
	 * 设置返回按钮监听
	 * @param listener
	 */
	public void setBackOnClickListener(final BackOnClickListener listener, final boolean isFinish){
		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onClick();
				}
				if (isFinish) {
					mContext.finish();
				}
				
			}
		});
	}
	
	public static interface BackOnClickListener  {
		 void onClick();
	}
	
	public void hideBack(){
		mBack.setVisibility(View.GONE);
	}
	
	public void showBack(){
		mBack.setVisibility(View.VISIBLE);
	}
	
	public View getRightButton(){
		return mRightButton;
	}
	
	// left button
	
	/**
	 * 创建一个左边（图形）的按钮
	 * @param isText true:文字  false：图形
	 */
	public void showDefaultLeftButton(int isText) {
		checkLayoutInflater();
		removeViewIfCan(mLeftButton);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		switch (isText) {
		case TITLE_LEFT_TX:
			mLeftButton = mInflater.inflate(R.layout.title_bar_textview, null);
			break;
		case TITLE_LEFT_BT:
			mLeftButton = mInflater.inflate(R.layout.title_bar_imagebutton_no_right_margin, null);
			break;
		case TITLE_LEFT_URL_CIRCLE_IMG:
			mLeftButton = mInflater.inflate(R.layout.title_bar_imageview, null);
			break;
		default:
			break;
		}
		
		mLayout.addView(mLeftButton, lp);
	}
	
	public void setLeftButton(String text){
		showDefaultLeftButton(TITLE_LEFT_TX);
		((TextView)mLeftButton).setText(text);
	}

	public void setLeftButton(int drawableId){
		showDefaultLeftButton(TITLE_LEFT_BT);
		((ImageButton)mLeftButton).setImageResource(drawableId);
	}
	
	public void setLeftImage(Bitmap drawable){
		showDefaultLeftButton(TITLE_LEFT_BT);
		((ImageButton)mLeftButton).setImageBitmap(drawable);
	}
	
	public void setLeftCircleImage(String url){
		showDefaultLeftButton(TITLE_LEFT_URL_CIRCLE_IMG);
//		((ImageView)mLeftButton).setImageResource(R.drawable.ic_launcher);
//		ImageLoader.getInstance().displayImage(url, (ImageView)mLeftButton);
		Utils.setCircleImage(url, (ImageView)mLeftButton);
	}
	
	public void setLeftButtonOnClickListener(OnClickListener listener){
		mLeftButton.setOnClickListener(listener);
	}
	
	public void hideLeftButton(){
		mLeftButton.setVisibility(View.GONE);
	}
	
	public void showLeftButton(){
		mLeftButton.setVisibility(View.VISIBLE);
	}
	
	public void setLeftRefreshButton(int drawableId){
		checkLayoutInflater();
		removeViewIfCan(mLeftGap);
		removeViewIfCan(mLeftRefreshButton);
		
		mLeftButton.setId(R.id.id_title_bar_imagebutton_left);
		
		mLeftGap = mInflater.inflate(R.layout.title_bar_left_gap, null);
		mLeftGap.setId(R.id.id_title_bar_imagebutton_left_gap);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_RIGHT, mLeftButton.getId());
		mLayout.addView(mLeftGap, lp);
		
		mLeftRefreshButton = mInflater.inflate(R.layout.title_bar_left_refresh, null);
		LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp2.addRule(RelativeLayout.ALIGN_LEFT, mLeftGap.getId());
		mLayout.addView(mLeftRefreshButton, lp2);
		((ImageView)mLeftRefreshButton.findViewById(R.id.title_bar_left_refresh_image)).setImageResource(drawableId);
	}
	
	public void setLeftRefreshButtonOnClickListener(OnClickListener listener){
		mLeftRefreshButton.setOnClickListener(listener);
	}
	
	public void setLeftRefreshButtonRefreshing(){
		mLeftRefreshButton.setEnabled(false);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
		animation.setInterpolator(new LinearInterpolator());
		mLeftRefreshButton.findViewById(R.id.title_bar_left_refresh_image).startAnimation(animation);
	}
	
	public void setLeftRefreshButtonRefreshComplete(){
		mLeftRefreshButton.setEnabled(true);
		mLeftRefreshButton.findViewById(R.id.title_bar_left_refresh_image).clearAnimation();
	}
	
	// right button
	
	/**
	 * 创建一个右边的按钮
	 * @param isText true:文字  false：图形
	 */
	private void showDefaultRightButton(boolean isText) {
		checkLayoutInflater();
		removeViewIfCan(mRightButton);
		if (isText) {
			mRightButton = mInflater.inflate(R.layout.title_bar_textview, null);
		} else {
			mRightButton = mInflater.inflate(R.layout.title_bar_imagebutton, null);
		}
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mLayout.addView(mRightButton, lp);
	}
	
	public void setRightButton(String text){
		showDefaultRightButton(true);
		((TextView)mRightButton).setText(text);
		
	}
	
	public void setRightButton(String text, int color){
		setRightButton(text);
		((TextView)mRightButton).setTextColor(color);
		
	}

	/**
	 * 设置右侧按钮的图标，支持文字按钮
	 * 
	 */
	public void setRightButton(int drawableId){
		showDefaultRightButton(false);
		((ImageButton)mRightButton).setImageResource(drawableId);
		
	}
	
	public void setRightButton(Bitmap bitmap){
		showDefaultRightButton(false);
		((ImageButton)mRightButton).setImageBitmap(bitmap);
		
	}
	
	public void setRightButtonOnClickListener(OnClickListener listener){
		mRightButton.setOnClickListener(listener);
	}
	
	public void hideRightButton(){
		mRightButton.setVisibility(View.GONE);
	}
	
	public void showRightButton(){
		mRightButton.setVisibility(View.VISIBLE);
	}

	// second right button
	public void setSecondRightButton(int drawableId){
		checkLayoutInflater();
		removeViewIfCan(mSecondRightButton);
		
		mRightButton.setId(R.id.id_title_bar_imagebutton_right);
		mSecondRightButton = mInflater.inflate(R.layout.title_bar_imagebutton, null);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.LEFT_OF, mRightButton.getId());
		mLayout.addView(mSecondRightButton, lp);
		((ImageButton)mSecondRightButton).setImageResource(drawableId);
	}
	
	public void setSecondRightButtonOnClickListener(OnClickListener listener){
		mSecondRightButton.setOnClickListener(listener);
	}
	
	public void hideSecondRightButton(){
		mSecondRightButton.setVisibility(View.GONE);
	}
	
	public void showSecondRightButton(){
		mSecondRightButton.setVisibility(View.VISIBLE);
	}
	
	public void setTitleRefreshButton(int drawableId){
		checkLayoutInflater();
		removeViewIfCan(mTitleGap);
		removeViewIfCan(mTitleRefreshButton);
		
		mTitle.setId(R.id.id_title_bar_title);
		
		mTitleGap = mInflater.inflate(R.layout.title_bar_title_gap, null);
		mTitleGap.setId(R.id.id_title_bar_title_gap);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_RIGHT, mTitle.getId());
		mLayout.addView(mTitleGap, lp);
		
		mTitleRefreshButton = mInflater.inflate(R.layout.title_bar_title_refresh, null);
		LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp2.addRule(RelativeLayout.ALIGN_LEFT, mTitleGap.getId());
		mLayout.addView(mTitleRefreshButton, lp2);
		((ImageView)mTitleRefreshButton.findViewById(R.id.title_bar_title_refresh_image)).setImageResource(drawableId);
	}
	
	public void setTitleRefreshButtonOnClickListener(OnClickListener listener){
		mTitleRefreshButton.setOnClickListener(listener);
	}
	
	public void setTitleRefreshButtonRefreshing(){
		mTitleRefreshButton.setEnabled(false);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
		animation.setInterpolator(new LinearInterpolator());
		mTitleRefreshButton.findViewById(R.id.title_bar_title_refresh_image).startAnimation(animation);
	}
	
	public void setTitleRefreshButtonRefreshComplete(){
		mTitleRefreshButton.setEnabled(true);
		mTitleRefreshButton.findViewById(R.id.title_bar_title_refresh_image).clearAnimation();
	}
	
}
