package com.chengning.fenghuo.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.chengning.fenghuo.R;

public class TitleBar {
	
	private static final int TITLEBAR_TYPE_TEXTVIEW = 1;
	private static final int TITLEBAR_TYPE_IMAGEBUTTON = 2;

	private RelativeLayout mLayout;
	
	private TextView mTitle;
	private View mBack;
	private TextView mBackText;
	private TextView mCloseText;
	
	private View mLeftButton;
	private View mRightButton;
	private View mSecondRightButton;
	
	private Activity mContext;
	private LayoutInflater mInflater;

	private Drawable drawable;
	
	public TitleBar(Activity context, View root){
		mContext = context;
		mLayout = (RelativeLayout) root.findViewById(R.id.title_bar_layout);
		
		mTitle = (TextView) mLayout.findViewById(R.id.title_bar_title);
		mBack = mLayout.findViewById(R.id.title_bar_back);
		mBackText = (TextView) mLayout.findViewById(R.id.title_bar_back_text);
		mCloseText = (TextView) mLayout.findViewById(R.id.title_bar_close_text);
		mCloseText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext.finish();
			}
		});
	}
	
	public TitleBar(Activity context, boolean isSingleInActivity){
		mContext = context;
		mLayout = (RelativeLayout) context.findViewById(R.id.title_bar_layout);
		
		mTitle = (TextView) mLayout.findViewById(R.id.title_bar_title);
		mBack = mLayout.findViewById(R.id.title_bar_back);
		mBackText = (TextView) mLayout.findViewById(R.id.title_bar_back_text);
		mCloseText = (TextView) mLayout.findViewById(R.id.title_bar_close_text);
		mCloseText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext.finish();
			}
		});
	}
	
	public TitleBar() {
	}

	public Activity getContext(){
		return mContext;
	}
	
	public RelativeLayout getLayout(){
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
	public void setBackgroundColor(Object color){
		if (color instanceof Drawable) {
			mLayout.setBackgroundDrawable((Drawable)color);
//			mLayout.setBackground((Drawable)color);
		} else if (color instanceof Integer) {
			mLayout.setBackgroundColor((Integer)color);
		}
		
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
		setTitleColor(color);
	}
	public void setTitleColor(int color){
		if (mTitle == null) {
			return;
		}
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
		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mContext.finish();
			}
		});
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
	 *
	 * @param drawable
	 */
	public void setBackTextDrawable(Drawable drawable){
		if (mBackText == null) {
			return;
		}
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		mBackText.setCompoundDrawables(drawable, null, null, null);
	}
	
	/**
	 * 设置返回的背景
	 * @param obj （int color or Drawable drawable）
	 */
	public void setBackTextBackgroundColor(Drawable obj) {
			mBack.setBackgroundDrawable(obj);
		
	}
	public void setBackTextBackgroundColor(int backColorId) {
			mBack.setBackgroundColor(backColorId);
		
	}
	
	/**
	 * 显示默认的返回按钮
	 */
	public void showDefaultBack(){
		setBackText("返回");
	}

	/**
	 * 显示默认的关闭按钮
	 */
	public void setCloseVisibility(boolean isVisiable){
		if (mCloseText == null) {
			return;
		}
		mCloseText.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
	}

	public void setCloseTextColor(int color){
		if (mCloseText == null) {
			return;
		}
		mCloseText.setTextColor(color);
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
	 * @param isText 1:文字  2：图形
	 */
	public void showDefaultLeftButton(int isText) {
		checkLayoutInflater();
		removeViewIfCan(mLeftButton);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		switch (isText) {
		case TITLEBAR_TYPE_TEXTVIEW:
			mLeftButton = mInflater.inflate(R.layout.title_bar_textview, null);
			break;
		case TITLEBAR_TYPE_IMAGEBUTTON:
			mLeftButton = mInflater.inflate(R.layout.title_bar_imagebutton, null);
			break;
		default:
			break;
		}
		
		mLayout.addView(mLeftButton, lp);
	}
	
	public void setLeftButton(String text){
		showDefaultLeftButton(TITLEBAR_TYPE_TEXTVIEW);
		((TextView)mLeftButton).setText(text);
	}

	public void setLeftButton(int drawableId){
		showDefaultLeftButton(TITLEBAR_TYPE_IMAGEBUTTON);
		((ImageButton)mLeftButton).setImageResource(drawableId);
	}
	
	public void setLeftImage(Bitmap drawable){
		showDefaultLeftButton(TITLEBAR_TYPE_IMAGEBUTTON);
		((ImageButton)mLeftButton).setImageBitmap(drawable);
	}
	
	public void setLeftButtonOnClickListener(OnClickListener listener){
		if (mLeftButton == null) {
			return;
		}
		mLeftButton.setOnClickListener(listener);
	}
	
	public void hideLeftButton(){
		mLeftButton.setVisibility(View.GONE);
	}
	
	public void showLeftButton(){
		mLeftButton.setVisibility(View.VISIBLE);
	}
	
	// right button
	
	/**
	 * 创建一个右边的按钮
	 * @param isText 1:文字  2：图形
	 */
	private void showDefaultRightButton(int isText) {
		checkLayoutInflater();
		removeViewIfCan(mRightButton);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		switch (isText) {
		case TITLEBAR_TYPE_TEXTVIEW:
			mRightButton = mInflater.inflate(R.layout.title_bar_textview, null);
			break;
		case TITLEBAR_TYPE_IMAGEBUTTON:
			mRightButton = mInflater.inflate(R.layout.title_bar_imagebutton, null);
			break;
		default:
			break;
		}
		
		mLayout.addView(mRightButton, lp);
	}
	
	public void setRightButton(String text){
		showDefaultRightButton(TITLEBAR_TYPE_TEXTVIEW);
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
		showDefaultRightButton(TITLEBAR_TYPE_IMAGEBUTTON);
		((ImageButton)mRightButton).setImageResource(drawableId);
		
	}
	
	public void setRightButton(Bitmap bitmap){
		showDefaultRightButton(TITLEBAR_TYPE_IMAGEBUTTON);
		((ImageButton)mRightButton).setImageBitmap(bitmap);
		
	}

	public void setRightButtonBackgroundColor(Drawable obj) {
		if (mRightButton == null) {
			return;
		}
		mRightButton.setBackgroundDrawable(obj);

	}
	public void setRightButtonBackgroundColor(int backColorId) {
		if (mRightButton == null) {
			return;
		}
		mRightButton.setBackgroundColor(backColorId);

	}
	
	public void setRightButtonOnClickListener(OnClickListener listener){
		if (mRightButton == null) {
			return;
		}
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
		
		mRightButton.setId(R.id.id_title_bar_imagebutton);
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
	
	/**
	 * 获取titltbar的高度
	 * @return
	 */
	public int getHeight(){
		mLayout.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		return mLayout.getMeasuredHeight();
	}

	public void setVisibility(int visibility) {
		mLayout.setVisibility(visibility);
	}
}
