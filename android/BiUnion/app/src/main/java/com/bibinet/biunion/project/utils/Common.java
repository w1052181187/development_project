package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bibinet.biunion.project.models.MediaLiveMsgItemModel;

import java.util.List;

public class Common {

    public static boolean isListEmpty(List list) {
        return list == null || list.size() ==0 ? true : false;
    }
    public static boolean isTrue(int i) {
        return i == 1  ? true :  false;
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if(pinfo !=null) {
            for(int i = 0; i < pinfo.size();i++) {
                String pn = pinfo.get(i).packageName;
                if(pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;

    }

    public static void startQQChat(Activity activity, String qq) {
        String url ="mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
        if (Common.isQQClientAvailable(activity)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
            ToastUtils.showShort("您的QQ版本过低或您当前未安装QQ,请安装最新版QQ后再试");
        }
    }

    public static void startPhone(Activity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        activity.startActivity(intent);
    }


    public static void hideInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
