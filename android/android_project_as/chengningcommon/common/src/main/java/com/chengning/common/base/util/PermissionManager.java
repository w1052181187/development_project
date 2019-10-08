package com.chengning.common.base.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/8/14.
 */

public class PermissionManager {

    private int mPermissionRequestCode;
    private PermisstionCallback mCallback;

    public void init(Activity activity, String needPermission, int permissionRequestCode, PermisstionCallback callback) {
        this.mPermissionRequestCode = permissionRequestCode;
        this.mCallback = callback;
        if (mCallback == null) {
            return ;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestPermission(activity, needPermission)) {
                mCallback.success();
            }
        } else {
            mCallback.success();
        }
    }

    private boolean requestPermission(Activity activity, String needPermission) {
        if (ContextCompat.checkSelfPermission(activity, needPermission)
                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, needPermission)) {
//                //若权限没有开启，则请求权限
//            }
            ActivityCompat.requestPermissions(activity,
                    new String[]{ needPermission }, mPermissionRequestCode);
            return false;
        }
        return true;

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == mPermissionRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //请求权限成功,做相应的事情
                mCallback.success();
            } else {
                mCallback.failure();
            }
        }
    }

    public interface PermisstionCallback {
        void success();
        void failure();
    }
}
