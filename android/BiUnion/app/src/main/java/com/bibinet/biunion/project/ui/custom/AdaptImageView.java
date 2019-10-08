package com.bibinet.biunion.project.ui.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Wh on 2018-1-9.
 */

public class AdaptImageView extends ImageView {

    private Context context;

    private Bitmap bitmap;
    private int height = 0;

    public void setInitImage(int id) {
        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        }
    }

    public AdaptImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(bitmap != null) {
            if (height == 0) {
                height = getMeasuredWidth() * bitmap.getHeight() / bitmap.getWidth();
                android.util.Log.e("heighit", bitmap.getWidth() + "-" + getMeasuredWidth() + "-" + bitmap.getHeight() + "-" + height);
                setMeasuredDimension(widthMeasureSpec, height);
            } else {
                setMeasuredDimension(widthMeasureSpec, height);
            }
        }
    }

}
