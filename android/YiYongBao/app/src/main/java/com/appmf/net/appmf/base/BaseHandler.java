package com.appmf.net.appmf.base;

import android.os.Handler;
import android.os.Message;

public class BaseHandler extends Handler {

    private HandlerMsgCallback callback;

    public BaseHandler(HandlerMsgCallback callback) {
        this.callback = callback;
    }


    @Override
    public void handleMessage(Message msg) {
        if (callback != null) {
            callback.handleMessage(msg);
        }
        super.handleMessage(msg);
    }

    public interface HandlerMsgCallback {
        void handleMessage(Message msg);
    }
}

