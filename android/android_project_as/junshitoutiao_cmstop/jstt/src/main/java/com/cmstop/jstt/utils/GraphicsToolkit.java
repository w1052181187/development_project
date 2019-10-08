package com.cmstop.jstt.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
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
	
	/**
	 * 将res中的资源图片抽取到sd卡下的文件当中
	 * @param context {@link android.content.Context}
	 * @param resId 图片在apk中的资源id
	 * @param sdPath 图片在sd卡中的路径
	 * @param toJPG 保存的文件是要压缩成jpg文件，false则代表保存为png图片
	 * @return 如果图片保存成功，则返回true，否则返回false 
	 * */
	public static boolean saveDrawableResourceToSdCard(Context context, int resId, String sdPath, boolean toJPG)
	{
		boolean result = false;
		
		FileOutputStream fos = null;
		Bitmap bmp = null;
		
		try
		{
			File file = new File(sdPath);
			if(file.exists() && file.isFile())
				file.delete();
			
			file.createNewFile();
			fos = new FileOutputStream(file);
			bmp = BitmapFactory.decodeResource(context.getResources(), resId);
			
			if(toJPG)
				bmp.compress(CompressFormat.JPEG, 100, fos);
			else
				bmp.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			
			result = true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		GeneralToolkit.closeOutputStream(fos);
		fos = null;
		
		try
		{
			if(bmp != null && !bmp.isRecycled())
				bmp.recycle();
			
			bmp = null;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 判断判断图片是否完整
	 * @param path 图片在sd卡中的路径
	 * @return 如果图片无法被加载，则返回false，否则返回true
	 * */
	public static boolean isImageValid(String path)
	{
		boolean result = false;
		Bitmap bmp = null;
		
		try
		{
			File file = new File(path);
			if(!file.exists())
				return false;
			
			if(file.isDirectory())
				return false;
			
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			bmp = BitmapFactory.decodeFile(path, opts);
			result = true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			if(bmp != null)
				bmp.recycle();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		bmp = null;
		return result;
	}
}
