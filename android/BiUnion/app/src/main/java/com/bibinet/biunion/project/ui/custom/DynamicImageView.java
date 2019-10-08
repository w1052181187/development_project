package com.bibinet.biunion.project.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.bibinet.biunion.R;
import com.bibinet.biunion.project.utils.ScreenShotUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wh on 2017-7-13.
 */

public class DynamicImageView extends ImageView {
    private final long time = 30;
    private int [] resImage = {R.mipmap.img_refresh_icon_1, R.mipmap.img_refresh_icon_2, R.mipmap.img_refresh_icon_3,
            R.mipmap.img_refresh_icon_4, R.mipmap.img_refresh_icon_5, R.mipmap.img_refresh_icon_6, R.mipmap.img_refresh_icon_7,
            R.mipmap.img_refresh_icon_8, R.mipmap.img_refresh_icon_9, R.mipmap.img_refresh_icon_10, R.mipmap.img_refresh_icon_11,
            R.mipmap.img_refresh_icon_12, R.mipmap.img_refresh_icon_13, R.mipmap.img_refresh_icon_14, R.mipmap.img_refresh_icon_15,
            R.mipmap.img_refresh_icon_16, R.mipmap.img_refresh_icon_17, R.mipmap.img_refresh_icon_18, R.mipmap.img_refresh_icon_19,
            R.mipmap.img_refresh_icon_20, R.mipmap.img_refresh_icon_21, R.mipmap.img_refresh_icon_22, R.mipmap.img_refresh_icon_23,
            R.mipmap.img_refresh_icon_24, R.mipmap.img_refresh_icon_25, R.mipmap.img_refresh_icon_26
            };
    private int cnt = 0;
    private boolean isStart;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setImageResource(resImage[cnt%resImage.length]);
            cnt++;
            if(isStart){
                handler.sendEmptyMessageDelayed(1, time);
            }
        }
    };

    public DynamicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public synchronized void start(){
        if(!isStart){
            cnt = 0;
            handler.sendEmptyMessageDelayed(1, time);
            isStart = true;
        }
    }

    public synchronized void stop(){
        isStart = false;
    }

    public synchronized void reset(){
        isStart = false;
        cnt = 0;
        setImageResource(resImage[cnt]);
    }

    public void setNormalState(double sp){
        if(isStart){
            return;
        }
        //1~28变化
        if(sp >= 1){
            setImageResource(resImage[27]);
        }else if(sp <= 0){
            setImageResource(resImage[0]);
        }else{
            int pos = (int) (sp * 28);
            setImageResource(resImage[pos]);
        }
    }

    public void setNormalState(int current, int max){
        if(isStart){
            return;
        }
        int sp = current * 27 / max;
        //1~28变化
        if(sp >= 27){
            setImageResource(resImage[27]);
        }else if(sp <= 0){
            setImageResource(resImage[0]);
        }else{
            setImageResource(resImage[sp]);
        }
    }

}
