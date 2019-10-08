package com.bibinet.biunion.project.builder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by bibinet on 2017-6-2.
 */

public class JPushReciver extends BroadcastReceiver {
    public static final String MESSAGE_RECEIVED_ACTION = "com.bibinet.biunion.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private TextView textView;

    public JPushReciver(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!TextUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
                Log.i("TAG","guangbo++++++++++++++++++++++++"+showMsg.toString());
            }
        } catch (Exception e){
        }
    }
    private void setCostomMsg(String msg){
        if (null != textView) {
            textView.setText(msg);
           // textView.setVisibility(android.view.View.VISIBLE);
        }
    }
    }

