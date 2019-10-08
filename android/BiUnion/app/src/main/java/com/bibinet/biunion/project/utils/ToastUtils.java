package com.bibinet.biunion.project.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bibinet.biunion.project.application.BiUnionApplication;

/**
 * Created by Wh on 2017-8-11.
 */

public class ToastUtils {
    private static ToastUtils utils;
    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            utils.close();
        }
    };
    private final int DEFAULT=2000;
    private Context mContext = null;
    private Toast mToast = null;
    private Handler mHandler = null;
    private int duration = 0;
    private int currDuration = 0;
    private Runnable mToastThread = new Runnable() {

        public void run() {
            mToast.show();
            mHandler.postDelayed(mToastThread, DEFAULT);// 每隔2秒显示一次
            if (duration != 0) {
                if (currDuration <= duration) {
                    currDuration += DEFAULT;
                }
                else {
                    close();
                }
            }

        }
    };

    public ToastUtils(String txt){
        mContext = BiUnionApplication.context;
//        currDuration=DEFAULT;
        mHandler = new Handler(mContext.getMainLooper());
        mToast = Toast.makeText(mContext, txt, Toast.LENGTH_LONG);
    }

    public static void showShort(String txt){
        if(utils == null){
            utils = new ToastUtils("");
        }
        utils.setText(txt);
        utils.open();
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    public static void showLong(String txt){
        Toast.makeText(BiUnionApplication.context, txt, Toast.LENGTH_LONG).show();
    }

    public void setText(String text) {
        mToast.setText(text);
    }

    public void open() {
        mHandler.post(mToastThread);
    }

    public void close(){
        mHandler.removeCallbacks(mToastThread);// 先把显示线程删除
        mToast.cancel();// 把最后一个线程的显示效果cancel掉，就一了百了了
        currDuration = DEFAULT;
    }

}
