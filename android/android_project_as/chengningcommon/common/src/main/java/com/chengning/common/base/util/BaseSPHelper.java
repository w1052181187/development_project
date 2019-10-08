package com.chengning.common.base.util;

import android.content.Context;
import android.content.SharedPreferences;


public class BaseSPHelper {
	
	public static final String KEY_CACHE_CONTROL_MAXAGE = "cache_control_max_age_";
	public static final String KEY_CACHE_CONTROL_START_TIME = "cache_control_start_time_";
	
	private SharedPreferences mSp;
	
	public BaseSPHelper(Context context){
		mSp = context.getSharedPreferences("app_sp", Context.MODE_PRIVATE);
	}
	
	public SharedPreferences getSharedPreferences(){
		return mSp;
	}
	
	public void saveString(String key, String value){
		mSp.edit().putString(key, value).commit();
	}
	
	public String getString(String key){
		return mSp.getString(key, "");
	}
	
	public String getString(String key, String defValue){
		return mSp.getString(key, defValue);
	}
	
	public void saveLong(String key, long value){
		mSp.edit().putLong(key, value).commit();
	}
	
	public long getLong(String key){
		return mSp.getLong(key, -1);
	}
	
	public long getLong(String key, long defValue){
		return mSp.getLong(key, defValue);
	}
	
	public void saveInt(String key, int value){
		mSp.edit().putInt(key, value).commit();
	}
	
	public int getInt(String key){
		return mSp.getInt(key, 0);
	}
	
	public int getInt(String key, int defValue){
		return mSp.getInt(key, defValue);
	}
	
	public void saveBoolean(String key, boolean value){
		mSp.edit().putBoolean(key, value).commit();
	}
	
	public boolean getBoolean(String key){
		return mSp.getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defValue){
		return mSp.getBoolean(key, defValue);
	}

}
