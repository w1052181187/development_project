package com.bibinet.biunion.project.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.utils.DensityUtil;

/**
 * Created by Wh on 2017-7-13.
 */

public class MusicWaitView extends View {
    private boolean isStart = false;
    private int cnt = 0;

    private final static int csp = 6;
    private final static int sp = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setRotation(cnt);
            cnt += csp;
            if (isStart) {
                handler.sendEmptyMessageDelayed(0, sp);
            }
        }
    };

    public synchronized void start() {
        if (!isStart) {
            isStart = true;
            handler.sendEmptyMessageDelayed(0, sp);
        }
    }

    public synchronized void stop() {
        isStart = false;
    }

    private int w = 3;

    public MusicWaitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(DensityUtil.dip2px(w));
        //设置渐变
        LinearGradient lg = new LinearGradient(0, 0, 200, 150, Color.parseColor("#fec710"), Color.WHITE, Shader.TileMode.MIRROR);
        paint.setShader(lg);
    }

    private Paint paint;

    private int height;
    private int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, width / 2 - DensityUtil.dip2px(w), paint); //参数3为画圆的半径，类型为float型。
    }
}
