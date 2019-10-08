package com.bibinet.biunion.project.animations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

/**
 * Created by Wh on 2017-9-15.
 */

public class AnimationManager {
    public static void topToBottom(View view, int pxDis, int duration) {
        Animation animation = new TranslateAnimation(0, 0, -pxDis, 0);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void bottomToTop(View view, int pxDis, int duration) {
        Animation animation = new TranslateAnimation(0, 0, pxDis, 0);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void rightToLeft(View view, int pxDis, int duration) {
        Animation animation = new TranslateAnimation(pxDis, 0, 0, 0);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void hideToShow(View view, float to, float from, int duration) {
        Animation animation = new AlphaAnimation(to, from);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private final static long TIME = 800;

    public interface OnOpenBookListener {
        void onOpenFinish();

        void onCloseFinish();
    }

    private static AbsoluteLayout wmRootView;
    private static WindowManager mWindowManager;
    private static ImageView cover;
    private static ImageView content;
    private static ContentScaleAnimation contentAnimation;
    private static Rotate3DAnimation coverAnimation;

    private static void destory() {
        if (wmRootView != null && cover != null && content != null && mWindowManager != null) {
            //动画结束执行
            wmRootView.removeView(cover);
            wmRootView.removeView(content);
            mWindowManager.removeView(wmRootView);
        }
        cover = null;
        content = null;
        wmRootView = null;
        mWindowManager = null;
    }

    public static void closeBook() {
        if (contentAnimation != null && coverAnimation != null && content != null && cover != null) {
            //因为书本打开后会移动到第一位置，所以要设置新的位置参数
            //动画逆向运行
            if (!contentAnimation.getMReverse()) {
                contentAnimation.reverse();
            }
            if (!coverAnimation.getMReverse()) {
                coverAnimation.reverse();
            }
            //清除动画再开始动画
            content.clearAnimation();
            content.startAnimation(contentAnimation);
            cover.clearAnimation();
            cover.startAnimation(coverAnimation);
        }
    }

    public static void openBook(Activity activity, final View main, final OnOpenBookListener onOpenBookListener) {
        mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        //实例化动画控件
        cover = new ImageView(activity);
        content = new ImageView(activity);
        //设置封面和底部图片
        Bitmap coverB = loadBitmapFromViewBySystem(main);
        cover.setImageBitmap(coverB);
        Bitmap contentB = Bitmap.createBitmap(coverB);
        Canvas canvas = new Canvas(contentB);
        canvas.drawColor(Color.WHITE);
        content.setImageBitmap(contentB);
        //计算位置
        final int[] location = new int[2];
        main.getLocationInWindow(location);
        //套用位置
        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(
                //不用采用原控件自带的LayoutParams会带出来一些自带的间距 造成误差
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        params.x = location[0];
        params.y = location[1];
        //添加控件
        wmRootView = new AbsoluteLayout(activity);
        mWindowManager.addView(wmRootView, getDefaultWindowParams());
        wmRootView.addView(content, params);
        wmRootView.addView(cover, params);
        //准备动画
        AccelerateInterpolator interpolator = new AccelerateInterpolator();
        float scale1 = activity.getWindow().getDecorView().getMeasuredWidth() / (float) main.getMeasuredWidth();
        float scale2 = activity.getWindow().getDecorView().getMeasuredHeight() / (float) main.getMeasuredHeight();
        float scaleTimes = scale1 > scale2 ? scale1 : scale2;  //计算缩放比例
        coverAnimation = new Rotate3DAnimation(0, -180, location[0], location[1], scaleTimes, false);
        coverAnimation.setInterpolator(interpolator);
        coverAnimation.setDuration(TIME);
        coverAnimation.setFillAfter(true);
        //coverAnimation.setAnimationListener(this);

        //启动动画（提前做处理）
        if (coverAnimation.getMReverse()) {
            coverAnimation.reverse();
        }
        cover.clearAnimation();
        cover.startAnimation(coverAnimation);

        contentAnimation = new ContentScaleAnimation(location[0], location[1], scaleTimes, false);
        contentAnimation.setInterpolator(interpolator);  //设置插值器
        contentAnimation.setDuration(TIME);
        contentAnimation.setFillAfter(true);  //动画停留在最后一帧
        contentAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!contentAnimation.getMReverse()) {
                    onOpenBookListener.onOpenFinish();
                } else {
                    onOpenBookListener.onCloseFinish();
                    destory();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (contentAnimation.getMReverse()) {
            contentAnimation.reverse();
        }
        content.clearAnimation();
        content.startAnimation(contentAnimation);
    }

    public static Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }

    private static WindowManager.LayoutParams getDefaultWindowParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0, 0,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,//windown类型,有层级的大的层级会覆盖在小的层级
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.RGBA_8888);

        return params;
    }
}
