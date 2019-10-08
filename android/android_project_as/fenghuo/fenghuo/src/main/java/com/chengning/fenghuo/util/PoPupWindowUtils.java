package com.chengning.fenghuo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chengning.fenghuo.R;

public class PoPupWindowUtils {

	private TextView popLeftView;
	private TextView popRightView;
	private View view;
	private PopupWindow popupWindow;
	private String leftText;
	private String rightText;
	private OnClickListener mLeftListener;
	private OnClickListener mRightListener;
	
	public enum PopupLocation {
		TOP,
		LEFT
		;
	}
	
	public void showPopupWindow(final Activity activity, View v, final int layoutId, PopupLocation popupLoc) {
		
		if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            view = layoutInflater.inflate(layoutId, null);
            popLeftView = (TextView) view.findViewById(R.id.poppupwindow_left);
            popRightView = (TextView) view.findViewById(R.id.poppupwindow_right);
            
            // 加载数据
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            
        }
		
        if (null != leftText) {
        	popLeftView.setText(leftText);
        }
        
        if (null != rightText) {
        	popRightView.setText(rightText);
        }
 
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 显示的位置为:屏幕的宽度的一半
        
        int[] location = new int[2];  
        v.getLocationOnScreen(location); 
        
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupHeight =  view.getMeasuredHeight();
        int popupWidth =  view.getMeasuredWidth();
        int width = v.getMeasuredWidth();
        int height = v.getMeasuredHeight();
 
        switch (popupLoc) {
		case TOP:
			popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - (popupWidth / 2) + width / 2 , location[1] - popupHeight);
			break;
		case LEFT:
			popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - popupWidth - width / 2 , location[1] - height/2);
			break;

		default:
			break;
		}
        
        if (mLeftListener != null) {
        	popLeftView.setOnClickListener(mLeftListener);
        }
        
        if (mRightListener != null) {
        	popRightView.setOnClickListener(mRightListener);
        }
        
		
	}
	
	public void setOnLeftClick(OnClickListener listener) {
		this.mLeftListener = listener;
	}
	
	public void setOnRightClick(OnClickListener listener) {
		this.mRightListener = listener;
	}
	
	public void setLeftText(String left) {
		this.leftText = left;
	}
	
	public void setRightText(String right) {
		this.rightText = right;
	}
	
	public View getLeftView(){
		return popLeftView;
	}
	
	public View getRightView(){
		return popRightView;
	}
	
	public void dismiss(){
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}
	
}
