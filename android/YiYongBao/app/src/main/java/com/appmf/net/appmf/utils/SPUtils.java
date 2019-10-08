package com.appmf.net.appmf.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    public static final String APP_CONFIG = "APP_SP_CONFIG";
    public static final String KEY_FIRST_RUN = "FIRST_RUN";
    public static final String KEY_FIRST_INIT_DB = "FIRST_INIT_DB";
    public static final String KEY_AD_DATA = "AD_DATA";
    public static final String KEY_AD_CONFIG = "AD_CONFIG";
    public static final String KEY_TABLE_COLUMNS = "TABLE_COLUMNS";

    private static SPUtils sharedPresUtils = new SPUtils();
    private static SharedPreferences pres = null;
    private static final int MODE = Context.MODE_PRIVATE;

    private SPUtils() {
    }

    public static SPUtils getInstance(Context context, String config) {
        pres = context.getSharedPreferences(config, MODE);
        return sharedPresUtils;
    }

    public String getString(String key, String defValue) {
        return pres.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return pres.getInt(key, defValue);
    }

    public boolean getBoolean(String key, Boolean defValue) {
        return pres.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = pres.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = pres.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = pres.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
