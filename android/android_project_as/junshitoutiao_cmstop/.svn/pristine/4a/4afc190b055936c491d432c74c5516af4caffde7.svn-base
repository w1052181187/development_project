package com.cmstop.jstt.utils;

import java.io.InputStream;
import java.io.OutputStream;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 此类为常用功能封装类。
 * */
public final class GeneralToolkit {

	/**
	 * 将文本转换为字符串
	 * @param str 文本字符串
	 * @param defaultVal 默认值
	 * @return 如果格式正确，返回转换的int值，否则返回defaultVal所指定的值
	 * */
	public static int parseInt(String str, int defaultVal)
	{
		if(str == null || str.length() == 0)
			return defaultVal;

		try
		{
			return Integer.parseInt(str);
		}catch(Exception e)
		{
			e.printStackTrace();
			return defaultVal;
		}
	}

	/**
	 * 将字符串boolean值转换为boolean
	 * */
	public static boolean parseBoolean(String text, boolean defaultVal)
	{
		try
		{
			return Boolean.parseBoolean(text);
		}catch(Exception e)
		{
			e.printStackTrace();
			return defaultVal;
		}
	}
	
	/**
	 * 将布尔值转换为c格式整数值
	 * */
	public static int booleanToInt(boolean value)
	{
		if(value)
			return 1;
		else
			return 0;
	}
	
	/**
	 * 将c样式的1或0转化为布尔类型
	 * */
	public static boolean intToBoolean(int value)
	{
		if(value == 0)
			return false;
		else
			return true;
	}

	/**
	 * 将字符串转换为int型存放入sharedPrefrence
	 * @param editor {@link android.content.SharedPreferences.Editor}
	 * @param key 存储的关键字
	 * @param text 要转化的文本，如果为空，则根据defaultVal值确定删除或填写默认editor中的相关内容
	 * @param defaultVal 默认值，如果不给出，则默认删除editor中的相关字段，否则当写入的int值非法，则使用此默认值
	 * @return 如果参数text为非法整数值，则返回false，否则返回true
	 * */
	public static boolean putIntegerStrToSharedPrefrence(Editor editor, String key, String text, int...defaultVal)
	{
		boolean success = false;

		try
		{
			if(text != null && text.length() != 0)
			{
				editor.putInt(key, Integer.parseInt(text));
				success = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		if(!success)
		{
			if(defaultVal == null || defaultVal.length == 0)
				editor.remove(key);
			else
				editor.putInt(key, defaultVal[0]);
		}

		return false;
	}

	/**
	 * 关闭输入流
	 * @param inputStream {@link java.io.InputStream}
	 * */
	public static void closeInputStream(InputStream inputStream)
	{
		if(inputStream == null)
			return;

		try
		{
			inputStream.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 关闭输出流
	 * @param outputStream {@link java.io.OutputStream}
	 * */
	public static void closeOutputStream(OutputStream outputStream)
	{
		if(outputStream == null)
			return;

		try
		{
			outputStream.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据Content获取应用对象
	 * @param context {@link android.content.Context}
	 * @return {@link android.app.Application}
	 * */
	public static Application getApplication(Context context)
	{
		if(context == null)
			return null;

		if(context instanceof Application)
			return (Application)context;

		return (Application)context.getApplicationContext();
	}

	/**
	 * 隐藏软键盘
	 * @param view {@link android.view.View}
	 * */
	public static void hideInputMetod(View view)
	{
		if(view == null)
			return;

		((InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 写出文本到shanredprefrence文件当中
	 * @param editor {@link android.content.SharedPreferences.Editor}
	 * @param key 键名
	 * @param value 键值，如果为null，则删除此键
	 * */
	public static void writeStringToSharedPrefrence(SharedPreferences.Editor editor, String key, String value)
	{
		if(value == null)
			editor.remove(key);
		else
			editor.putString(key, value);
	}
}
