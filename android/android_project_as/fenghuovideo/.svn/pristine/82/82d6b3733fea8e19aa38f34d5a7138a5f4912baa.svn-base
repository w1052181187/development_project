package com.chengning.fenghuovideo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.MediaController;

/**
 * Created by Administrator on 2017/8/19 0019
 */

public class MyTextureVideoView extends TextureView implements TextureView.SurfaceTextureListener, MediaController.MediaPlayerControl {

    private MediaPlayer mp;

    private String mUrl;


    public MyTextureVideoView(Context context) {
        super(context);
    }

    public MyTextureVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextureVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MyTextureVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void setVideoPath(String url){
        this.mUrl = url;
    }

    public void openVideo(){
        try {
            if (mp != null) {
                mp.release();
            }
            mp = new MediaPlayer();
            mp.setDataSource(getContext(), Uri.parse(mUrl));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {

    }

    @Override
    public void start() {
        mp.start();
    }

    @Override
    public void pause() {
        mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mp.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
