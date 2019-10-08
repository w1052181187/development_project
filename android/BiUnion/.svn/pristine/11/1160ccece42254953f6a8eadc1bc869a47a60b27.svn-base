package com.bibinet.biunion.project.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Created by Wh on 2017-7-6.
 */

public class AlipayUtils {
    private Activity activity;
    private OnAlipayUtilsListener onAlipayUtilsListener;

    public void startPay(final Activity activity, final String orderInfo, OnAlipayUtilsListener onAlipayUtilsListener){
        this.activity = activity;
        this.onAlipayUtilsListener = onAlipayUtilsListener;
        //orderInfo 直接从服务器取
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                onAlipayUtilsListener.onFinish(payResult);
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                onAlipayUtilsListener.onFail(payResult);
            }
            //将结果反馈到服务器

         }
    };

    public interface OnAlipayUtilsListener{
        void onFinish(PayResult payResult);
        void onFail(PayResult payResult);
    }
}
