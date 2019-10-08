package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bibinet.biunion.project.application.BiUnionApplication;
import com.bibinet.biunion.project.models.MagazineChildModel;

import java.util.HashMap;
import java.util.Map;

import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_BEAUTY_BITEYE;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_BEAUTY_BUFFING;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_BEAUTY_CHEEKPINK;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_BEAUTY_RUDDY;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_BEAUTY_SLIMFACE;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_BEAUTY_WHITE;

/**
 *
 *
 *
 */
public class SharedPresUtils {

    private static SharedPresUtils sharedPresUtils = new SharedPresUtils();

    private static SharedPreferences pres = null;
    private static final int MODE = Context.MODE_PRIVATE;

    public static final String USER_CONFIG = "USER_CONFIG";
    public static final String MAGAZINE_DETAIL_LIGHT = "MAGAZINE_DETAIL_LIGHT";

    public static final String APP_CONFIG = "APP_CONFIG";
    public static final String DATA_CONFIG = "DATA_CONFIG";

    private static final String SHAREDPRE_FILE = "livepush";
    private static final String WHITE = "white";
    private static final String BUFFING = "buffing";
    private static final String RUDDY = "ruddy";
    private static final String CHEEKPINK = "cheekpink";
    private static final String BRIGHTNESS = "brightness";
    private static final String SLIMFACE = "slimface";
    private static final String SHORTENFACE = "shortenface";
    private static final String BIGEYE = "bigeye";
    private static final String AUTOFOCUS = "autofocus";
    private static final String PREVIEW_MIRROR = "previewmirror";
    private static final String PUSH_MIRROR = "pushmirror";
    private static final String TARGET_BIT = "target_bit";
    private static final String MIN_BIT = "min_bit";
    private static final String SHOWGUIDE = "guide";
    private static final String BEAUTYON = "beautyon";
    private static final String HINT_TARGET_BIT = "hint_target_bit";
    private static final String HINT_MIN_BIT = "hint_min_bit";
    private static final String DISPLAY_FIT = "display_fit";
    private static final String RECORDON = "recordon";
    private static final String FANZHUANON = "jingxiangon";

    private SharedPresUtils() {
    }

    public static SharedPresUtils getsSharedPresUtils(String config) {
        pres = BiUnionApplication.getInstance().getSharedPreferences(config, MODE);
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

    public Map loadUserInfo() {
        Map map = new HashMap();

        map.put("rememberflag", pres.getBoolean("rememberflag", false));
        map.put("autoflag", pres.getBoolean("autoflag", false));
        map.put("username", pres.getString("username", ""));
        map.put("userpwd", pres.getString("userpwd", ""));

        return map;
    }

    public void putString(String key, String value) {
        Editor editor = pres.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, Boolean value) {
        Editor editor = pres.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        Editor editor = pres.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    private final String KEY_LOCATION_CITY = "location_city";
    private final String KEY_LOCATION_PROVINCE = "location_province";

    public void saveLocationCity(String city) {
        Editor editor = pres.edit();
        editor.putString(KEY_LOCATION_CITY, city);
        editor.commit();
    }

    public String getLocationCity() {
        return pres.getString(KEY_LOCATION_CITY, "请定位");
    }
    public String getKEY_LOCATION_PROVINCE() {
        return pres.getString(KEY_LOCATION_PROVINCE, "");
    }

    public void saveLocationProvince(String province) {
        Editor editor = pres.edit();
        editor.putString(KEY_LOCATION_PROVINCE, province);
        editor.commit();
    }


    private final String MAGAZINE_ID = "MAGAZINE_ID";
    private final String MAGAZINE_NAME = "MAGAZINE_NAME";
    private final String MAGAZINE_COVER = "MAGAZINE_COVER";
    private final String MAGAZINE_CONTENT = "MAGAZINE_CONTENT";
    private final String MAGAZINE_PERIOD = "MAGAZINE_PERIOD";

    public void saveReading(MagazineChildModel magazineChildModel) {
        Editor editor = pres.edit();
        editor.clear();
        editor.putString(MAGAZINE_ID, magazineChildModel.getObjectId());
        editor.putString(MAGAZINE_NAME, magazineChildModel.getMagazineName());
        editor.putString(MAGAZINE_COVER, magazineChildModel.getMagazineCover());
        editor.putString(MAGAZINE_CONTENT, magazineChildModel.getMagazineContent());
        editor.putString(MAGAZINE_PERIOD, magazineChildModel.getMagazinePeriods());
        editor.commit();
    }

    public MagazineChildModel loadReading(){
        MagazineChildModel magazineChildModel = new MagazineChildModel();
        magazineChildModel.setObjectId(pres.getString(MAGAZINE_ID, null));
        magazineChildModel.setMagazineName(pres.getString(MAGAZINE_NAME, null));
        magazineChildModel.setMagazineCover(pres.getString(MAGAZINE_COVER, null));
        magazineChildModel.setMagazineContent(pres.getString(MAGAZINE_CONTENT, null));
        magazineChildModel.setMagazinePeriods(pres.getString(MAGAZINE_PERIOD, null));
        return magazineChildModel;
    }


    public static void setWhiteValue(Context context, int white) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(WHITE, white);
        editor.commit();
    }

    public static int getWhiteValue(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int white = sharedPreferences.getInt(WHITE, DEFAULT_VALUE_INT_BEAUTY_WHITE);
        return white;
    }

    public static void setBuffing(Context context, int buffing) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(BUFFING, buffing);
        editor.commit();
    }

    public static int getBuffing(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int buffing = sharedPreferences.getInt(BUFFING, DEFAULT_VALUE_INT_BEAUTY_BUFFING);
        return buffing;
    }

    public static void setRuddy(Context context, int ruddy) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(RUDDY, ruddy);
        editor.commit();
    }

    public static int getRuddy(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int ruddy = sharedPreferences.getInt(RUDDY, DEFAULT_VALUE_INT_BEAUTY_RUDDY);
        return ruddy;
    }

    public static void setCheekPink(Context context, int cheekpink) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(CHEEKPINK, cheekpink);
        editor.commit();
    }

    public static int getCheekpink(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int cheekpink = sharedPreferences.getInt(CHEEKPINK, DEFAULT_VALUE_INT_BEAUTY_CHEEKPINK);
        return cheekpink;
    }

    public static void setSlimFace(Context context, int slimface) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(SLIMFACE, slimface);
        editor.commit();
    }

    public static int getSlimFace(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int slimface = sharedPreferences.getInt(SLIMFACE, DEFAULT_VALUE_INT_BEAUTY_SLIMFACE);
        return slimface;
    }

    public static void setShortenFace(Context context, int shortenface) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(SHORTENFACE, shortenface);
        editor.commit();
    }

    public static int getShortenFace(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int shortenface = sharedPreferences.getInt(SHORTENFACE, DEFAULT_VALUE_INT_BEAUTY_SLIMFACE);
        return shortenface;
    }

    public static void setBigEye(Context context, int saturation) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(BIGEYE, saturation);
        editor.commit();
    }

    public static int getBigEye(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        int bigeye = sharedPreferences.getInt(BIGEYE, DEFAULT_VALUE_INT_BEAUTY_BITEYE);
        return bigeye;
    }

    public static void setBeautyOn(Context context, boolean beautyOn) {
        if (context == null) {
            return;
        }
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(BEAUTYON, beautyOn);
        editor.commit();
    }
    public static void setRecordOn(Context context, boolean beautyOn) {
        if (context == null) {
            return;
        }
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(RECORDON, beautyOn);
        editor.commit();
    }
    public static void setFanzhuanOn(Context context, boolean beautyOn) {
        if (context == null) {
            return;
        }
        SharedPreferences mySharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(FANZHUANON, beautyOn);
        editor.commit();
    }

    public static boolean isBeautyOn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        boolean beautyOn = sharedPreferences.getBoolean(BEAUTYON, true);
        return beautyOn;
    }
    public static boolean isRecordOn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        boolean beautyOn = sharedPreferences.getBoolean(RECORDON, true);
        return beautyOn;
    }
    public static boolean isFanzhuanOn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPRE_FILE,
                Activity.MODE_PRIVATE);
        boolean beautyOn = sharedPreferences.getBoolean(FANZHUANON, true);
        return beautyOn;
    }
}
