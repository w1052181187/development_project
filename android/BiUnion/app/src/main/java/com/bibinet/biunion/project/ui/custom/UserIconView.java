package com.bibinet.biunion.project.ui.custom;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.PathConfig;
import com.bibinet.biunion.project.ui.dialog.MyUserIconSelectDialog;
import com.bibinet.biunion.project.utils.ImagePickHelper;
import com.bibinet.biunion.project.utils.ImageUtils;
import com.bibinet.biunion.project.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Created by Wh on 2018-1-9.
 */

public class UserIconView extends ImageView implements View.OnClickListener
        , MyUserIconSelectDialog.OnMyUserIconSelectDialogListener {
    private static final String PHOTO_USERPHOTO = "userphoto.jpg";//用户头像
    private final int REQUEST_CODE_CAMERA_PERMISSION = 1111;
//    public static final String imagePath = PathConfig.cachePathImage + "/" + PHOTO_USERPHOTO;
    private final String imagePath1 = PathConfig.cachePathImage + "/" + "file" + PHOTO_USERPHOTO;
    private boolean isLogin;
    private MyUserIconSelectDialog myUserIconSelectDialog;
    private Activity activity;
    private OnUserIconViewListener onUserIconViewListener;
    private File businesspic;
    private ImagePickHelper pickHelper;

    public UserIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myUserIconSelectDialog = new MyUserIconSelectDialog(context, this);
        setOnClickListener(this);
    }

    public void init(Activity activity, OnUserIconViewListener onUserIconViewListener) {
        this.activity = activity;
        this.onUserIconViewListener = onUserIconViewListener;
    }

    @Override
    public void onClick(View view) {
        if (!isLogin) {
            onUserIconViewListener.onNotLogin();
            return;
        }
        businesspic = new File(imagePath1);
        pickHelper = new ImagePickHelper(activity,businesspic,true);
        myUserIconSelectDialog.show();
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public void onCamera() {
        selectFromCamera();
    }

    @Override
    public void onPhoto() {
        selectFromPhoto();
    }

    private void selectFromPhoto() {
        pickHelper.handleAbulmClick(activity);
    }

    private void selectFromCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //摄像头权限
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSION);
            } else {
                startCamera();
            }
        } else {
            startCamera();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSION:
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShort("摄像头权限开启失败！部分功能不能正常使用");
                } else {
                    startCamera();
                }
                break;
            default:
                break;
        }
    }

    private void startCamera() {
        pickHelper.handleCameraClick(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            pickHelper.handleActivityResult(requestCode, resultCode, data, new ImagePickHelper.IfImagePickhelperCallback() {
                @Override
                public void cropSuccess(File file) {
                    Bitmap bitmap = ImageUtils.getSmallBitmap(file.getAbsolutePath());
                    ImageUtils.saveBitmap(bitmap, file.getAbsolutePath());
                    //转码
                    String fileCode = ImageUtils.getImageStr(file.getAbsolutePath());
                    onUserIconViewListener.onUserIconFinish(fileCode);
                }
            });
        }
    }

    public void updateUserIcon(String fileCode) {
        try {
            if (fileCode == null || TextUtils.isEmpty(fileCode)) {
                setImageResource(R.mipmap.wode_toux);
                return;
            }
            RequestOptions options = new RequestOptions().circleCrop();
            Glide.with(this).asBitmap().load(ImageUtils.base64ToBitmap(fileCode)).apply(options).into(this);
        } catch (Exception e) {

        }
    }

    public interface OnUserIconViewListener {
        void onUserIconFinish(String fileCode);

        void onNotLogin();
    }
}
