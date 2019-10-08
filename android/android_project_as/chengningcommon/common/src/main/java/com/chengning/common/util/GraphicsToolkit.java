package com.chengning.common.util;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class GraphicsToolkit {
	/**
	 * 计算等比缩放的图像的大小
	 * @param aimWidth 目标的最大宽度
	 * @param aimHeight 目标的最大高度
	 * @param imgWidth 原始图像的宽度
	 * @param imgHeight 图像的原始高度
	 * @param outSize 计算后输出的图像大小
	 * */
	public static void computeEqualScaleSize(int aimWidth, int aimHeight, int imgWidth, int imgHeight, int[] outSize)
	{
		if(aimWidth == 0 || aimHeight == 0 || imgWidth == 0 || imgHeight == 0)
		{
			outSize[0] = 0;
			outSize[1] = 0;
			return;
		}
		
		float wscale = 0;
		float hscale = 0;
		
		if(imgWidth >= aimWidth)
		{
			wscale = aimWidth * 1.0f / imgWidth;
			
			if(imgHeight >= aimHeight)
			{
				hscale = aimHeight * 1.0f / imgHeight;
				
				if(wscale < hscale)
				{
					outSize[0] = (int)(imgWidth * wscale);
					outSize[1] = (int)(imgHeight * wscale);
				}
				else
				{
					outSize[0] = (int)(imgWidth * hscale);
					outSize[1] = (int)(imgHeight * hscale);
				}
			}
			else
			{
				outSize[0] = (int)(imgWidth * wscale);
				outSize[1] = (int)(imgHeight * wscale);
			}
		}
		else
		{
			if(imgHeight >= aimHeight)
			{
				hscale = aimHeight * 1.0f / imgHeight;
				
				outSize[0] = (int)(imgWidth * hscale);
				outSize[1] = (int)(imgHeight * hscale);
			}
			else
			{
				outSize[0] = imgWidth;
				outSize[1] = imgHeight;
			}
		}
	}
}
