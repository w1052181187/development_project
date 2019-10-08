package com.bibinet.biunion.project.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.SearchActivityAdapter;
import com.bibinet.biunion.project.application.BiUnionApplication;

/**
 * Created by Wh on 2017-8-5.
 */

public class AppUtils {
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        Context context = BiUnionApplication.context;
        String versionName = "";
        int versionCode;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 返回当前程序版本名
     */
    public static int getAppVersionCode(Context context) {
        String versionName = "";
        int versionCode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
