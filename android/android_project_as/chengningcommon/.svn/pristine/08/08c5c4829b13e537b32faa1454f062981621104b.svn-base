package com.chengning.common.widget;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;

import com.chengning.common.util.GraphicsToolkit;

/**
 * 本类为顶部带一个图片的单选钮控件
 * */
@SuppressLint({ "DrawAllocation", "RtlHardcoded" }) 
public class ImageRadioButton extends RadioButton{

	/**行距*/
	private float mLineSpace;
	
	/**绘制过程中的缩放图像大小*/
	private int[] mScaledImgSize = new int[]{0 , 0};

	public ImageRadioButton(Context context) {
		super(context);
	}

	public ImageRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setLineSpacing(float add, float mult) {
		mLineSpace = add * mult;

		super.setLineSpacing(add, mult);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		LayoutParams lp = getLayoutParams();
		int pt = getPaddingTop();
		int pl = getPaddingLeft();
		int pr = getPaddingRight();
		int pb = getPaddingBottom();

		int wMode = MeasureSpec.getMode(widthMeasureSpec);
		int hMode = MeasureSpec.getMode(heightMeasureSpec);

		int widthSrc = MeasureSpec.getSize(widthMeasureSpec);
		int heightSrc = MeasureSpec.getSize(heightMeasureSpec);

		int width = 0;
		int height = 0;

		String text = getText().toString();
		TextPaint paint = getPaint();

		/*********计算文本的宽度与高度***********/
		final FontMetricsInt fm = new FontMetricsInt();
		paint.getFontMetricsInt(fm);
		int textWidth = (int)Layout.getDesiredWidth(text, paint);
		int textHeight = fm.bottom - fm.ascent;

		/**********计算整个控件的宽度****************/
		if(wMode == MeasureSpec.AT_MOST)
		{
			if(lp != null && lp.width == LayoutParams.MATCH_PARENT)
			{
				width = widthSrc;
			}
			else
			{
				width = textWidth + pl + pr;
				if(width > widthSrc)
					width = widthSrc;
			}
		}
		else if(wMode == MeasureSpec.EXACTLY)
		{
			width = widthSrc;
		}
		else
		{
			if(lp.width == LayoutParams.MATCH_PARENT)
				width = widthSrc;
			else if(lp.height == LayoutParams.WRAP_CONTENT)
				width = textWidth + pl + pr;
			else
				width = lp.width;
		}

		Drawable topDrawable = getTopDrawable();
		int drawableHeight = 0;
		if(topDrawable != null)
			drawableHeight = topDrawable.getIntrinsicHeight();

		int lineSpace = (int)mLineSpace;

		/***********计算整个控件的高度***********************/
		if(hMode == MeasureSpec.AT_MOST)
		{
			if(lp != null && lp.height == LayoutParams.MATCH_PARENT)
			{
				height = heightSrc;
			}
			else
			{
				height = textHeight + pt + pb;
				if(drawableHeight > 0)
					height = drawableHeight + lineSpace;

				if(height > heightSrc)
					height = heightSrc;
			}
		}
		else if(hMode == MeasureSpec.EXACTLY)
		{
			height = heightSrc;
		}
		else
		{
			if(lp.height == LayoutParams.MATCH_PARENT)
				height = heightSrc;
			else if(lp.height == LayoutParams.WRAP_CONTENT)
			{
				height = textHeight + pt + pb;
				if(drawableHeight > 0)
					height = drawableHeight + lineSpace;
			}
			else
				height = heightSrc;
		}

		setMeasuredDimension(width, height);
	}

	/**
	 * 返回topdrawable
	 * */
	public Drawable getTopDrawable()
	{
		Drawable[] drawables = getCompoundDrawables();
		if(drawables == null || drawables.length < 2)
			return null;

		return drawables[1];
	}
	
	/**
	 * 返回bottomDrawable
	 * 
	 * */
	public Drawable getBottomDrawable()
	{
		Drawable[] drawables = getCompoundDrawables();
		if(drawables == null || drawables.length < 4)
			return null;

		return drawables[3];
	}

	@Override
	public void draw(Canvas canvas) {
		//		super.draw(canvas);

		dispatchDraw(canvas);
		canvas.save();

		int[] status = getDrawableState();
		int width = getWidth();
		int height = getHeight();

		Drawable bg = getBackground();
		Drawable topDrawable = getTopDrawable();

		String text = getText().toString();
		final TextPaint paint = getPaint();
		ColorStateList textColors = getTextColors();

		/**step1: draw the background image*/
		if(bg != null)
		{
			bg.setState(status);
			bg.setBounds(0, 0, width, height);
			bg.draw(canvas);
		}

		int pt = getPaddingTop();
		int pb = getPaddingBottom();
		int pl = getPaddingLeft();
		int pr = getPaddingRight();

		int compoundPad = getCompoundDrawablePadding();
		
		/**step2: compute and draw text*/
		int gravity = getGravity();
		final FontMetricsInt fm = new FontMetricsInt();
		paint.getFontMetricsInt(fm);

		int textWidth = (int)Layout.getDesiredWidth(text, paint);
		int textHeight = -fm.ascent - fm.descent;

		int textX = 0;
		int textY = 0;

		int textColor = textColors.getColorForState(status, 0xffffffff);
		int imgLeft = 0, imgTop = 0, imgRight = 0, imgBottom = 0;
		paint.setTextAlign(Align.LEFT);

		int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
		int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
		
		int imgWidth = 0;	//原始图像大小 
		int imgHeight = 0;
		
		int validImgWidth = 0;	//图像最大大小
		int validImgHeight = 0;
		
		/*****compute and draw bottom image******/
		Drawable btnImg = getBottomDrawable();
		if(btnImg != null)
		{
			validImgWidth = width;
			validImgHeight = height;
			
			imgWidth = btnImg.getIntrinsicWidth();
			imgHeight = btnImg.getIntrinsicHeight();
			
			GraphicsToolkit.computeEqualScaleSize(validImgWidth, validImgHeight, imgWidth, imgHeight, mScaledImgSize);
			imgLeft = ((validImgWidth - mScaledImgSize[0]) >> 1);
			imgTop = height - mScaledImgSize[1];
			btnImg.setBounds(imgLeft, imgTop, imgLeft + mScaledImgSize[0], imgTop + mScaledImgSize[1]);
			btnImg.setState(status);
			btnImg.draw(canvas);
		}
		
		
		/*****compute text and top image coordination*****/
		if(text.length() != 0)
		{
			switch(horizontalGravity)
			{
			case Gravity.LEFT:
				textX = pl;
				break;
			case Gravity.CENTER_HORIZONTAL:
				textX = ((width - textWidth) >> 1);
				break;
			case Gravity.RIGHT:
				textX = width - pr - textWidth;
				break;
			default:
				break;
			}

			if(topDrawable == null)
			{
				switch(verticalGravity)
				{
				case Gravity.TOP:
					textY = pt + textHeight;
					break;
				case Gravity.BOTTOM:
					textY = height - pb;
					break;
				case Gravity.CENTER_VERTICAL:
					textY = (height >> 1) - (textHeight >> 1) + textHeight;
					break;
				default:
					break;
				}
			}
			else
			{
				textY = height - pb;

				/*******compute and draw top image********/
				imgWidth = topDrawable.getIntrinsicWidth();
				imgHeight = topDrawable.getIntrinsicHeight();
				
				imgLeft = pl + compoundPad;
				imgRight = width - pr - compoundPad;
				imgTop = pt + compoundPad;
				imgBottom = textY - (int)mLineSpace - (fm.bottom - fm.ascent) - compoundPad;
				
				validImgWidth = imgRight - imgLeft;
				validImgHeight = imgBottom - imgTop;

				GraphicsToolkit.computeEqualScaleSize(validImgWidth, validImgHeight, imgWidth, imgHeight, mScaledImgSize);
				
				mScaledImgSize[0] = mScaledImgSize[0] + 5;
				mScaledImgSize[1] = mScaledImgSize[1] + 5;
				
				imgLeft += ((validImgWidth - mScaledImgSize[0]) >> 1);
				imgRight = imgLeft + mScaledImgSize[0];
				imgTop += ((validImgHeight - mScaledImgSize[1]) >> 1);
				imgBottom = imgTop + mScaledImgSize[1];
				
				topDrawable.setBounds(imgLeft, imgTop, imgRight, imgBottom);
				topDrawable.draw(canvas);
			}

			//draw text
			paint.setColor(textColor);
			canvas.drawText(text, textX, textY, paint);
		}
		else	//draw top image directly
		{
			if(topDrawable != null)
			{
				/*******compute and draw top image********/
				imgWidth = topDrawable.getIntrinsicWidth();
				imgHeight = topDrawable.getIntrinsicHeight();
				
				imgLeft = pl + compoundPad;
				imgRight = width - pr - compoundPad;
				imgTop = pt + compoundPad;
				imgBottom = height - pb - compoundPad;
				
				validImgWidth = imgRight - imgLeft;
				validImgHeight = imgBottom - imgTop;

				GraphicsToolkit.computeEqualScaleSize(validImgWidth, validImgHeight, imgWidth, imgHeight, mScaledImgSize);
				
				imgLeft += ((validImgWidth - mScaledImgSize[0]) >> 1);
				imgRight = imgLeft + mScaledImgSize[0];
				imgTop += ((validImgHeight - mScaledImgSize[1]) >> 1);
				imgBottom = imgTop + mScaledImgSize[1];
				
				topDrawable.setBounds(imgLeft, imgTop, imgRight, imgBottom);
				topDrawable.draw(canvas);
			}
		}

		canvas.restore();
	}
}
