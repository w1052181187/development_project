package com.bibinet.biunion.project.utils;

import android.os.CountDownTimer;

import java.text.SimpleDateFormat;

/**
 * Created by 吴昊 on 2017-6-17.
 */

public class TimeUtils {

    /**
     * 取消倒计时
     */
    public void cancel() {
        if(timer!=null){
            timer.cancel();
        }
    }

    private CountDownTimer timer;

    public void startCountDownTime(long totalTime, long interval, final OnTimeUtilsListener onTimeUtilsListener){
        if(timer!=null){
            timer.cancel();
        }
        timer = new CountDownTimer(totalTime, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                onTimeUtilsListener.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                onTimeUtilsListener.onFinish();
            }
        };
        timer.start();
    }

    public interface OnTimeUtilsListener{
        void onTick(long millisUntilFinished);
        void onFinish();
    }

    public static String getTimeFormat(String templet, long time){
        SimpleDateFormat format = new SimpleDateFormat(templet);
        return format.format(time);
    }
}
