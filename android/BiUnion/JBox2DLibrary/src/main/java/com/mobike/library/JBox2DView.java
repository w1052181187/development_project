package com.mobike.library;


import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by kimi on 2017/7/8 0008.
 * Email: 24750@163.com
 */

public class JBox2DView extends FrameLayout {

    private JBox2D jBox2D;

    public JBox2DView(@NonNull Context context) {
        this(context,null);
    }

    public JBox2DView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        jBox2D = new JBox2D(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        jBox2D.onSizeChanged(w,h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        jBox2D.onLayout(changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        jBox2D.onDraw(canvas);
    }

    public JBox2D getjBox2D(){
        return this.jBox2D;
    }
}
