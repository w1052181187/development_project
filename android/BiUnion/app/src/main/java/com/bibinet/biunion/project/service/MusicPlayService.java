package com.bibinet.biunion.project.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    public static final String ACTION_FINISH_SERVICE = "finish__music_service";
    public static final String ACTION_START_MUSIC = "start__music_play";
    public static final String ACTION_MUSIC_PATH = "music_path";

    private MediaPlayer mMediaPlayer;

    private MusicBinder mBinder = new MusicBinder();
    private MusicPlayCallback mMusicPlayCallback;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        /* 监听播放是否完成 */
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FINISH_SERVICE);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                stopSelf();
            }
        };
        registerReceiver(broadcastReceiver, filter);
    }

    /*启动service时执行的方法*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        String path = intent.getStringExtra(ACTION_MUSIC_PATH);
        switch (action) {
            case ACTION_START_MUSIC:
                startPlay(path);
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void startPlay(String url)  {

        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(MusicPlayService.this, Uri.parse(url));//指定音频文件路径
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mMusicPlayCallback != null) {
            mMusicPlayCallback.updatePlayCompletion();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        if (mMusicPlayCallback != null) {
            mMusicPlayCallback.updateUiPrepare();
        }
    }

    public void setMusicPlayCallback(MusicPlayCallback mMusicPlayCallback) {
        this.mMusicPlayCallback = mMusicPlayCallback;
    }


    public class MusicBinder extends Binder {

        public MusicPlayService getService() {
            return MusicPlayService.this;
        }

        public MediaPlayer getMediaPlayer() {
            return mMediaPlayer;
        }

        //判断是否处于播放状态
        public boolean isPlaying(){
            return mMediaPlayer.isPlaying();
        }

        //播放或暂停歌曲
        public void play() {
            if (isPlaying()) {
                pause();
            } else {
                start();
            }
        }

        //返回歌曲的长度，单位为毫秒
        public int getDuration(){
            return mMediaPlayer.getDuration();
        }

        //返回歌曲目前的进度，单位为毫秒
        public int getCurrenPosition(){
            return mMediaPlayer.getCurrentPosition();
        }

        //设置歌曲播放的进度，单位为毫秒
        public void seekTo(int mesc){
            mMediaPlayer.seekTo(mesc);
        }

        public void start() {
            mMediaPlayer.start();
        }
        public void pause() {
            mMediaPlayer.pause();
        }

        public void stop() {
            mMediaPlayer.stop();
        }
    }

    public interface MusicPlayCallback {
        void updateUiPrepare();
        void updatePlayCompletion();
    }
}
