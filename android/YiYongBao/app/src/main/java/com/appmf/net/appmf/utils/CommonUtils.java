package com.appmf.net.appmf.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.Toast;

import com.appmf.net.appmf.app.Constants;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wyg
 * @date 2018/11/28
 */

public class CommonUtils {

    /**
     * 泛型转换工具方法 eg:object ==> map<String, String>
     *
     * @param object Object
     * @param <T> 转换得到的泛型对象
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * Show message
     *
     * @param activity Activity
     * @param msg message
     */
    public static void showShortMessage(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void handleFileByRxjava(final Context context, final String fileName, final RxjavaCallback callback) {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        String encodeStr = FileIOUtils.getFromAssets(context, fileName);
                        String decodeStr = EncodeUtils.decodeBykey(encodeStr, Constants.key);
                        emitter.onNext(decodeStr);
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        callback.success(s);
//                    }
//                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.success(s);
                    }
                });
    }

    public interface RxjavaCallback {
        void success(String data);
    }

    /**
     * 启动pp
     * @param context
     */
    public static void launchapp(Context context,String packageName) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, packageName)) {
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
        } else {
            goToMarket(context, packageName);
        }
    }

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 去市场下载页面
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }


    public static long getCurTime10(){
        return System.currentTimeMillis()/1000;
    }
}
