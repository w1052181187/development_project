package com.bibinet.biunion.project.utils.cityselectutil;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */

public class SharedPreferencesUtils {
    public static SharedPreferencesUtils instance = null;
    public SharedPreferences settings = null;

    public static SharedPreferencesUtils getInstence() {
        if (instance == null) {
            instance = new SharedPreferencesUtils();
        }
        return instance;
    }

    /**
     * 添加历史城市数据
     */
    public void putArrayData(Context ctx, List<String> list) {
        settings = ctx.getSharedPreferences(Constance.SharedPreferences_URL, 0);
        SharedPreferences.Editor mEdit1 = settings.edit();
        mEdit1.putInt(Constance.PICKED_CITY_SIZE, list.size());
        for (int i = 0; i < list.size(); i++) {
            mEdit1.putString(Constance.PICKED_CITY + i, list.get(i));
        }
        mEdit1.commit();


    }

    /**
     * 获取历史城市数据
     * @param ctx
     * @param pickedCity
     * @return
     */
    public void setCheckCodeAgainSendTime(Context ctx, long time) {
        settings = ctx.getSharedPreferences(Constance.CHECK_CODE_TIME, 0);
        settings.edit().putLong(Constance.CHECK_CODE_TIME, time).commit();
    }


    public long getCheckCodeAgainSendTime(Context ctx) {
        settings = ctx.getSharedPreferences(Constance.CHECK_CODE_TIME, 0);
        long l = settings.getLong(Constance.CHECK_CODE_TIME, -1);
        return l;
    }
}
