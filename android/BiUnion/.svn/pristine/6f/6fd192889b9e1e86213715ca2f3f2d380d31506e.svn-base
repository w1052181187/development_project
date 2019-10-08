package com.bibinet.biunion.project.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 软键盘工具
 * Created by 吴昊 on 2017-4-20.
 */

public class SoftKeyboardUtils {
    public static void hintKbTwo(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&activity.getCurrentFocus()!=null){
            if (activity.getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }else {
                imm.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
//    public static void hideKeyBoard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        // 得到InputMethodManager的实例
//        if (imm.isActive()) {
//            // 如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }

    public static void hideInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
