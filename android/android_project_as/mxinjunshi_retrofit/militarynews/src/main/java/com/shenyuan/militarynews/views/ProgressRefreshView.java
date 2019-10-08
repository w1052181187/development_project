package com.shenyuan.militarynews.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenyuan.militarynews.R;

@Deprecated
public class ProgressRefreshView {

	private Activity mContext;
	private LayoutInflater mInflater;
	private RelativeLayout mLayout;
	private ImageView mTouchRefresh;
	private ProgressBar mWait;
	private TextView mNoData; 
	
	public ProgressRefreshView(Activity context, boolean isSingleActivity) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		mLayout = (RelativeLayout) mContext.findViewById(R.id.progress_refresh_layout);
		initView();
	}
	
	public ProgressRefreshView(Activity context, View root) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		mLayout = (RelativeLayout) root.findViewById(R.id.progress_refresh_layout);
		initView();
	}

	private void initView() {
		mTouchRefresh = (ImageView) mLayout.findViewById(R.id.touchRefresh); 
		mWait = (ProgressBar) mLayout.findViewById(R.id.wait);
		mNoData = (TextView) mLayout.findViewById(R.id.no_data_tv);
	}
	
	/**
	 * 自定义数据加载view
	 * @param drawable
	 */
	public void setWait(Drawable drawable) {
		if (checkIsNull(mWait)) {
			mWait.setIndeterminateDrawable(drawable);
		}
	}
	
	/**
	 * 自定义服务器没数据时显示的内容 内容 + 大小 + 颜色
	 * @param text 
	 * @param color 
	 * @param size
	 */
	public void setNoDataTvText(String text, int color, float size) {
		if (checkIsNull(mWait)) {
			mNoData.setText(text);
			mNoData.setTextColor(color);
			mNoData.setTextSize(size);
		}
	}
	
	/**
	 * 自定义服务器没数据时显示的内容 内容 + 颜色
	 * @param text
	 * @param color
	 */
	public void setNoDataTvText(String text, int color) {
		if (checkIsNull(mWait)) {
			mNoData.setText(text);
			mNoData.setTextColor(color);
		}
	}
	
	/**
	 * 自定义服务器没数据时显示的内容 内容 + 大小
	 * @param text
	 * @param size
	 */
	public void setNoDataTvText(String text, float size) {
		if (checkIsNull(mWait)) {
			mNoData.setText(text);
			mNoData.setTextSize(size);
		}
	}
	
	/**
	 * 自定义服务器没数据时显示的内容  仅内容
	 * @param text
	 */
	public void setNoDataTvText(String text) {
		if (checkIsNull(mWait)) {
			mNoData.setText(text);
		}
	}
	
	/**
	 * 自定义接触刷新view
	 * @param drawable type: drawable(Drawable) or drawableId(int)
	 */
	public <T> void setRefresh(T drawable) {
		
		if (checkIsNull(mTouchRefresh)) {
			if (drawable instanceof Drawable) {
				mTouchRefresh.setImageDrawable((Drawable)drawable);
			}
			if (drawable instanceof Integer) {
				mTouchRefresh.setImageResource((Integer)drawable);
			}
		}
	}
	
	/**
	 * 设置数据加载view是否可见
	 * @param visible
	 */
	public void setWaitVisibility(boolean visible) {
		setRootViewVisibility(true);
		if (checkIsNull(mWait)) {
			mWait.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
		
	}
	
	/**
	 * 设置接触刷新view是否可见
	 * @param visible
	 */
	public void setRefreshVisibility(boolean visible) {
		setRootViewVisibility(true);
		if (checkIsNull(mTouchRefresh)) {
			mTouchRefresh.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
		
	}
	
	/**
	 * 设置没数据时view是否可见
	 * @param visible
	 */
	public void setNoDataTvVisibility(boolean visible) {
		setRootViewVisibility(true);
		if (checkIsNull(mNoData)) {
			mNoData.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
		
	}
	
	/**
	 * 设置接触刷新view点击监听
	 * @param visiable
	 */
	public void setRefreshOnClickListener(OnClickListener listener) {
		
		if (checkIsNull(mTouchRefresh)) {
			mTouchRefresh.setOnClickListener(listener);
		}
		
	}
	
	/**
	 * 检查view是否为空
	 * @param view
	 * @return
	 */
	private boolean checkIsNull (View view){
		if (null == view) {
			throw new IllegalStateException("view is null");
		} else {
			return true;
		}
	}
	
	/**
	 * 设置整个view的可见性
	 * @param visible
	 */
	public void setRootViewVisibility(boolean visible){
		if (checkIsNull(mLayout)) {
			mLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
		}
	}
	
}
