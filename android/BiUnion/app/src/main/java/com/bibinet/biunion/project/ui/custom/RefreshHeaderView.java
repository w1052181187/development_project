package com.bibinet.biunion.project.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IHeaderCallBack;
import com.bibinet.biunion.R;
import com.bibinet.biunion.project.utils.ScreenShotUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wh on 2017-7-13.
 */

public abstract class RefreshHeaderView<T> extends RelativeLayout implements IHeaderCallBack {

    private final double secondaryThreshold = 1.3;
    private final long stagnationTime = 2000;
    private final int STATE_READY  = 10000;
    private final int STATE_SECONDARY_READY  = 10001;
    private final int STATE_NORMAL = 10002;
    private final int STATE_REFRESHING = 10003;
    private final int STATE_FINISH = 10004;
    private boolean isSecondary = false;
    private int currentState = STATE_NORMAL;

    private boolean isSecondaryReady = false;
    private long lastTime = -1;
    private XRefreshView xRefreshView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            xRefreshView.stopRefresh();
        }
    };

    private Context context;

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(){
        //初始化view
        ButterKnife.bind(this, this);
    }

    public abstract void onSecondaryStateReady();

    public abstract void onStateReady();

    public abstract void onRefreshing();

    public abstract void onSecondaryRefreshing();

    public abstract void onRefreshStateNormal();

    public abstract void onRefreshStateFinish(boolean success);

    @Override
    public final void onStateNormal() {
        currentState = STATE_NORMAL;
        onRefreshStateNormal();
    }

    @Override
    public final void onStateRefreshing() {
        Log.e("onStateRefreshing","-");
        lastTime = System.currentTimeMillis();
        currentState = STATE_REFRESHING;
        //判断是一级刷新中还是二级刷新中
        if(isSecondaryReady){
            onSecondaryRefreshing();
        }else{
            onRefreshing();
        }


    }

    @Override
    public final void onStateFinish(boolean success) {
        Log.e("onStateFinish","-"+success);
        currentState = STATE_FINISH;

        onRefreshStateFinish(success);
    }

    @Override
    public final void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
        Log.e("onHeaderMove","-"+offsetY + "-" + deltaY +"-" +headerMovePercent);
        if(headerMovePercent>=secondaryThreshold && isSecondary && (currentState==STATE_NORMAL || currentState == STATE_READY)){
            currentState = STATE_SECONDARY_READY;
            onSecondaryStateReady();
            isSecondaryReady = true;
        }else if(headerMovePercent>=1 && currentState==STATE_NORMAL){
            currentState = STATE_READY;
            onStateReady();
            isSecondaryReady = false;
        }else{
//            currentState = STATE_NORMAL;
        }
    }

    @Override
    public void setRefreshTime(long lastRefreshTime) {
        Log.e("setRefreshTime","-");
    }

    @Override
    public void hide() {
        Log.e("hide","-");

    }

    @Override
    public void show() {
        Log.e("show","-");
    }

    public boolean isSecondary() {
        return isSecondary;
    }

    public void setSecondary(boolean secondary) {
        isSecondary = secondary;
    }

    //停止刷新的方法 不调用XRefreshView默认的方法
    public void stopRefresh(XRefreshView xRefreshView) {
        this.xRefreshView = xRefreshView;
        long t = System.currentTimeMillis() - lastTime;
        if(t<stagnationTime && isSecondary && isSecondaryReady){
            handler.sendEmptyMessageDelayed(0, stagnationTime-t);
        }else{
            if(xRefreshView!=null && currentState != STATE_FINISH){
                xRefreshView.stopRefresh();
            }
        }
    }

    public final  T getLayoutView(){
        return (T) LayoutInflater.from(context).inflate(getLayoutId(), null, false);
    }

    protected abstract int getLayoutId();
}
