package com.bibinet.biunion.project.builder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by bibinet on 2017-10-17.
 */

public class CustomImageView extends AppCompatImageView {
    private boolean isClickable;
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCanclick(){
        isClickable=true;
    }
    public void setNotclick(){
        isClickable=false;
    }
    public void setdisClick(){
        isClickable=false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable) {
        			return super.onTouchEvent(event);
        		}else {
            return false;
        }
    }

}
