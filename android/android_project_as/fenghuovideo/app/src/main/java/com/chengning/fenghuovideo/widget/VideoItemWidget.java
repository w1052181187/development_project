package com.chengning.fenghuovideo.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.chengning.common.util.DisplayUtil;
import com.chengning.common.widget.NestRadioGroup;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.IVideoController;
import com.chengning.fenghuovideo.JKDebug;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.VideoModuleManager;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.util.UIHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class VideoItemWidget implements IVideoController {

    private static final String TAG = VideoItemWidget.class.getSimpleName();

    private static final long TIME_DELAY_UPDATE = 100;
    private static final long TIME_DELAY_CLOSE_CTRL = 3000;

    private Activity mActivity;
    private VideoItemWidget mThis;
    private VideoModuleManager mManager;
    private FullScreen mFull;
    private FloatingDetail mFloating;

    private VIWState mState = VIWState.Idle;

    private int mVideoWidth;
    private int mVideoHeight;
    private int mItemWidth;
    private int mItemHeight;
    
    private String mUrl;

    private View mRoot;
    private View mRootContent;
    private ListViewItemData mItemData;
    
    private TextureVideoView mVideoView;
//    private VideoView mVideoView;
    private View mCoverLayout;
    private ImageView mCover;
    private View mCtrlLayout;
    private ImageView mPlayPause;
    private ProgressBar mLoading;
    private ImageView mFullScreen;
    private TextView mTimeCurrent;
    private TextView mTimeTotal;
    private SeekBar mTimeProgress;

    private boolean isPrepared;
    private boolean isFirstPlay = true;
    private boolean isTimeProgressTouching;
    private long mLastStartTime;
    private long mLastCtrlShowTime;
    
    private Handler mHandler;

    private VideoItemWidget(Activity activity, View view, String url, ListViewItemData itemData){
        this.mActivity = activity;
        this.mRoot = view.findViewById(R.id.video_layout);
        this.mItemData = itemData;
        this.mUrl = url;
        init();
    }

    public static VideoItemWidget createVideoItemWidget(Activity activity, View view, String url, ListViewItemData itemData){
        if(view != null){
            View vRoot = view.findViewById(R.id.video_layout);
            if(vRoot != null){
                Object tag = vRoot.getTag();
                if(tag != null && tag instanceof VideoItemWidget){
                    VideoItemWidget viw = (VideoItemWidget)tag;
                    viw.mItemData = itemData;
                    viw.update(url);
                    return viw;
                }
            }
        }
        return new VideoItemWidget(activity, view, url, itemData);
    }

    private void init(){
        if(mRoot == null){
            return;
        }
        this.mRootContent = mRoot.findViewById(R.id.video_content_layout);
        if(mRootContent == null){
            return;
        }
        mRoot.setTag(this);

        mThis = this;
        mManager = VideoModuleManager.getInst();
        mManager.regist(this);
        
//        mVideoView = (VideoView) mRoot.findViewById(R.id.video_videoview);
        mVideoView = (TextureVideoView) mRoot.findViewById(R.id.video_videoview);
        mCoverLayout = mRoot.findViewById(R.id.video_cover_layout);
        mCover = (ImageView) mRoot.findViewById(R.id.video_cover);
        mCtrlLayout = mRoot.findViewById(R.id.video_ctrl_layout);
        mPlayPause = (ImageView) mRoot.findViewById(R.id.video_ctrl_play_pause);
        mLoading = (ProgressBar) mRoot.findViewById(R.id.video_ctrl_loading);
        mFullScreen = (ImageView) mRoot.findViewById(R.id.video_ctrl_fullscreen);
        mTimeCurrent = (TextView) mRoot.findViewById(R.id.video_ctrl_time_current);
        mTimeTotal = (TextView) mRoot.findViewById(R.id.video_ctrl_time_total);
        mTimeProgress = (SeekBar) mRoot.findViewById(R.id.video_ctrl_time_progress);

        // 获取视频第一帧
//        MediaMetadataRetriever retr = new MediaMetadataRetriever();
//        retr.setDataSource(mActivity , mVideoView.getUriWithDrmCheck());
//        retr.setMode(MediaMetadataRetriever.MODE_CAPTURE_FRAME_ONLY);
//        Bitmap bm = retr.captureFrame();
//        int wVideo = bm.getWidth();
//        int hVideo = bm.getHeight();
//        ThumbnailUtils.createVideoThumbnail()

        initListeners();
        
        update();
    }

    private void update(String url){
        if(url != null && !url.equals(mUrl)){
            this.mUrl = url;
            update();
        }
    }

    private void update(){
        if(mHandler != null){
            mHandler.removeCallbacks(mUpdateRunnable);
        }
        
        mFull = new FullScreen();
        mFloating = new FloatingDetail();
        mHandler = new Handler();
        mState = VIWState.Idle;

        isPrepared = false;
        isFirstPlay = true;
        isTimeProgressTouching = false;
        mLastStartTime = 0;
        mLastCtrlShowTime = 0;

        mCoverLayout.setVisibility(View.VISIBLE);
        mCover.setVisibility(View.VISIBLE);

        mCtrlLayout.setVisibility(View.VISIBLE);

        mPlayPause.setSelected(false);
        showLoading(false);
        mTimeCurrent.setText(getPositionToTime(0));
        mTimeTotal.setText(getPositionToTime(0));
        mTimeProgress.setProgress(0);

        mVideoView.setVisibility(View.VISIBLE);
//        mVideoView.release();
//        mVideoView.invalidate();

        ViewGroup.LayoutParams layoutParams = mRootContent.getLayoutParams();
        DisplayUtil.getInst().init(mActivity);
        if(mRoot.getWidth() > 0){
            layoutParams.width = layoutParams.width > mRoot.getWidth() ? layoutParams.width : mRoot.getWidth();
        }
        layoutParams.height = DisplayUtil.getInst().getScreenWidth() * 9 /16;
        mRootContent.setLayoutParams(layoutParams);
//        mRoot.invalidate();

//        if(!TextUtils.isEmpty(mUrl)){
//            mVideoView.destroyDrawingCache();
//            mVideoView.setVideoPath(mUrl);
//        }

        mVideoView.resumeCacheDisplay(new TextureVideoView.OnCacheDisplayListener() {
            @Override
            public void onCache() {
                Log.e(TAG, "OnCacheDisplayListener onCache");

                mCoverLayout.setVisibility(View.VISIBLE);
                mCover.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRefresh() {
                Log.e(TAG, "OnCacheDisplayListener onRefresh");

                mCoverLayout.setVisibility(View.GONE);
                mCover.setVisibility(View.GONE);
            }
        });
    }

    public void destroyVideo(){
        // TODO(debug)
//        mVideoView.stopPlayback();
//        mVideoView.stop();
        mVideoView.stop();
//        mVideoView.release();
        mVideoView.clearCacheDisplay();
//        mVideoView.getSurfaceTexture().release();
//        mVideoView.destroy
        mVideoView.setVisibility(View.INVISIBLE);
        mState = VIWState.Idle;
    }
    
    public void setUrl(String url){
        this.mUrl = url;
        if(!TextUtils.isEmpty(mUrl)){
            mVideoView.setVideoPath(mUrl);
        }
    }

    public void setUrlNotStart(String url){
        this.mUrl = url;
    }

    private void initListeners(){
        mRootContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLastCtrlShowTime = System.currentTimeMillis();
//                Log.e(TAG, "mRootContent onClick mLastCtrlShowTime: " + mLastCtrlShowTime);
                if(mCtrlLayout.getVisibility() != View.VISIBLE){
                    mCtrlLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mState = VIWState.Prepared;
                if(Consts.DEBUG){
                    Log.d(TAG, "mVideoView onPrepared");
                }
                
                isPrepared = true;
                mTimeTotal.setText(getPositionToTime(mVideoView.getDuration()));
                if(mManager.getVideoController() == null || mManager.getVideoController() == mThis){
                    mManager.start(mThis);
                }
                // TODO(debug fullscreen)
                if(!mFull.isFull()){
                    mFloating.onPrepared();
                }
                mFull.onPrepared();
                
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                        if(JKDebug.DEBUG)Log.e(TAG, "onBufferingUpdate percent: " + percent);
                    }
                });
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        if(Consts.DEBUG)Log.d(TAG, "onVideoSizeChanged width: " + width + ", height: " + height);

                        mVideoWidth = width;
                        mVideoHeight = height;

                        if(!mFull.isFull() && !mFloating.isFloatingDetail() && mItemData != null){
                            int tarWidth;
                            int tarHeight;
                            int rootWidth = mItemData.getListView().getWidth();
                            int rootHeight = mItemData.getListView().getHeight();
                            if(((float)width / height) >= ((float)rootWidth / rootHeight)){
                                tarWidth = rootWidth;
                                tarHeight = (int) (height / ((float)width / rootWidth));
                            }else{
                                tarHeight = rootHeight;
                                tarWidth = (int) (width / ((float)height / rootHeight));
                            }
                            mItemWidth = tarWidth;
                            mItemHeight = tarHeight;
                            
                            ViewGroup.LayoutParams layoutParams = mRootContent.getLayoutParams();
                            layoutParams.width = tarWidth;
                            layoutParams.height = tarHeight;
                            mRootContent.setLayoutParams(layoutParams);

                            if(tarHeight == rootHeight){
                                int headerCount = mItemData.getListView().getHeaderViewsCount();
                                mItemData.getListView().setSelection(headerCount + mItemData.getPosition());
                            }
                            
                            if(Consts.DEBUG)Log.d(TAG, "onVideoSizeChanged tarWidth: " + tarWidth + ", tarHeight: " + tarHeight);
                        }
                    }
                });
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, "mVideoView onError what: " + what);
//                mTimeTotal.setText("mVideoView onError what: " + what);
                /* 
                    错误常数 
 
MEDIA_ERROR_IO 
文件不存在或错误，或网络不可访问错误 
值: -1004 (0xfffffc14) 
 
MEDIA_ERROR_MALFORMED 
流不符合有关标准或文件的编码规范 
值: -1007 (0xfffffc11) 
 
MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK 
视频流及其容器不适用于连续播放视频的指标（例如：MOOV原子）不在文件的开始. 
值: 200 (0x000000c8) 
 
MEDIA_ERROR_SERVER_DIED 
媒体服务器挂掉了。此时，程序必须释放MediaPlayer 对象，并重新new 一个新的。 
值: 100 (0x00000064) 
 
MEDIA_ERROR_TIMED_OUT 
一些操作使用了过长的时间，也就是超时了，通常是超过了3-5秒 
值: -110 (0xffffff92) 
 
MEDIA_ERROR_UNKNOWN 
未知错误 
值: 1 (0x00000001) 
 
MEDIA_ERROR_UNSUPPORTED 
比特流符合相关编码标准或文件的规格，但媒体框架不支持此功能 
值: -1010 (0xfffffc0e) 
 
 
what int: 标记的错误类型: 
    MEDIA_ERROR_UNKNOWN 
    MEDIA_ERROR_SERVER_DIED 
extra int: 标记的错误类型. 
    MEDIA_ERROR_IO 
    MEDIA_ERROR_MALFORMED 
    MEDIA_ERROR_UNSUPPORTED 
    MEDIA_ERROR_TIMED_OUT 
    MEDIA_ERROR_SYSTEM (-2147483648) - low-level system error. 
 
* */

                mLastCtrlShowTime = System.currentTimeMillis();
//                Log.e(TAG, "mRootContent onClick mLastCtrlShowTime: " + mLastCtrlShowTime);
                if(mCtrlLayout.getVisibility() != View.VISIBLE){
                    mCtrlLayout.setVisibility(View.VISIBLE);
                }
                pause(mThis);
                if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    //媒体服务器挂掉了。此时，程序必须释放MediaPlayer 对象，并重新new 一个新的。  
                    UIHelper.showToast(mActivity, "网络服务错误");
                } else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                    if (extra == MediaPlayer.MEDIA_ERROR_IO) {
                        //文件不存在或错误，或网络不可访问错误  
                        UIHelper.showToast(mActivity, "网络文件错误");
                    } else if (extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
                        //超时  
                        UIHelper.showToast(mActivity, "网络超时");
                    }
                }
                return false;
            }
        });
//        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//            @Override
//            public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                return false;
//            }
//        });

        mPlayPause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e(TAG, "mPlayPause onClick");
                if(mState == VIWState.Idle){
                    showLoading(true);
                    mManager.setVideoController(mThis);
                    setUrl(mUrl);
                }else{
                    if(mManager.isPlaying(mThis)){

                        mLastCtrlShowTime = System.currentTimeMillis();
//                Log.e(TAG, "mRootContent onClick mLastCtrlShowTime: " + mLastCtrlShowTime);
                        if(mCtrlLayout.getVisibility() != View.VISIBLE){
                            mCtrlLayout.setVisibility(View.VISIBLE);
                        }
                        mManager.pause(mThis);
                    }else{
                        mManager.start(mThis);
                    }
                }
            }
        });
        mFullScreen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e(TAG, "mFullScreen onClick");
                
                mFull.switchFullScreen(mRootContent);
            }
        });
        mTimeProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int tar = (int) (getDuration(mThis) * ((float)progress / 1000));
                if(fromUser){
                    mTimeCurrent.setText(getPositionToTime(tar));
                    mManager.seekTo(mThis, tar);
                }else{
                    mTimeCurrent.setText(getPositionToTime(tar));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTimeProgressTouching = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isTimeProgressTouching = false;
            }
        });
    }

    private Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            if(!isAlive()){
                return;
            }
//            mPlayPause.setVisibility(mManager.isPlaying(mThis) ? View.GONE : View.VISIBLE);
//            mPlayPause.setText(mManager.isPlaying(mThis) ? "暂停" : "播放");
            mPlayPause.setSelected(mManager.isPlaying(mThis));
            int total = mManager.getDuration(mThis);
            mTimeTotal.setText(getPositionToTime(total));
            if(!isTimeProgressTouching){
                int currentPosition = mManager.getCurrentPosition(mThis);
                mTimeCurrent.setText(getPositionToTime(currentPosition));

                int progress = (int) (((float)currentPosition / total) * 1000);
                mTimeProgress.setProgress(progress);
            }
            int buffer = mManager.getBufferPercentage(mThis) * 10;
            mTimeProgress.setSecondaryProgress(buffer);
            closeCtrlLayoutIfNeeded();

            mHandler.postDelayed(mUpdateRunnable, TIME_DELAY_UPDATE);
        }
    };
    
    public ImageView getCover(){
        return mCover;
    }

    private String getPositionToTime(int position){
        int mill = (int) (position / 1000);
        String str = String.format("%1$02d:%2$02d", mill / 60, mill % 60);
        return str;
    }
    
    private boolean closeCtrlLayoutIfNeeded(){
        long time = System.currentTimeMillis();
//        Log.e(TAG, "closeCtrlLayoutIfNeeded time: " + time + ", mLastStartTime: " + mLastStartTime +  ", mLastCtrlShowTime: " + mLastCtrlShowTime);
        if((time - mLastStartTime) > TIME_DELAY_CLOSE_CTRL
                && (time - mLastCtrlShowTime) > TIME_DELAY_CLOSE_CTRL){
            if(mCtrlLayout.getVisibility() == View.VISIBLE && isPlaying(mThis)){
                mCtrlLayout.setVisibility(View.INVISIBLE);
                return true;
            }
        }
        return false;
    }
    
    private void showLoading(boolean show){
        mLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        mPlayPause.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

    private boolean isAlive(){
        return mVideoView != null && true;
    }

    @Override
    public void callStop(IVideoController controller) {
        mLastCtrlShowTime = System.currentTimeMillis();
//                Log.e(TAG, "mRootContent onClick mLastCtrlShowTime: " + mLastCtrlShowTime);
        if(mCtrlLayout.getVisibility() != View.VISIBLE){
            mCtrlLayout.setVisibility(View.VISIBLE);
        }
        showLoading(false);
        pause(controller);
    }

    @Override
    public void start(IVideoController controller) {
        if(isAlive()){
            if(isFirstPlay){
                // TODO(debug)
//                mCoverLayout.setVisibility(View.GONE);
//                mCover.setVisibility(View.GONE);
                mHandler.post(mUpdateRunnable);
                isFirstPlay = false;
            }
            showLoading(false);
            mState = VIWState.Started;
            mLastStartTime = System.currentTimeMillis();
            mVideoView.setKeepScreenOn(true);
            // setZOrderMediaOverlay 需要在addview之前调用
//            mVideoView.setZOrderMediaOverlay(true);
            mVideoView.requestLayout();
            mVideoView.start();
        }
    }

    @Override
    public void pause(IVideoController controller) {
        if(isAlive() && canPause(controller)){
            mState = VIWState.Paused;
            mVideoView.setKeepScreenOn(false);
//            mVideoView.setZOrderMediaOverlay(false);
            mVideoView.pause();
        }
    }

    @Override
    public int getDuration(IVideoController controller) {
        return mVideoView != null ? mVideoView.getDuration() : 0;
    }

    @Override
    public int getCurrentPosition(IVideoController controller) {
        return mVideoView != null ? mVideoView.getCurrentPosition() : 0;
    }

    @Override
    public void seekTo(IVideoController controller, int pos) {
        if(isAlive()){
            mLastCtrlShowTime = System.currentTimeMillis();
            mVideoView.seekTo(pos);
        }
    }

    @Override
    public boolean isPlaying(IVideoController controller) {
        return mVideoView != null ? mVideoView.isPlaying() : false;
    }

    @Override
    public int getBufferPercentage(IVideoController controller) {
        // TODO(debug)
//        return mVideoView != null ? mVideoView.getBufferPercentage() : 0;
        return mVideoView != null ? 100 : 0;
    }

    @Override
    public boolean canPause(IVideoController controller) {
        // TODO(debug)
//        return mVideoView != null ? mVideoView.canPause() : false;
        return mVideoView != null ? true : false;
    }

    @Override
    public void onScroll(IVideoController controller, AbsListView view) {
        View r = null;
        if(mItemData != null){
            r = mItemData.getListView();
        }
        if(r != null && r == view && isAlive()){
            if(isPlaying(mThis)){

                Rect mRect = new Rect();
                boolean vr1 = mVideoView.getGlobalVisibleRect(mRect);
                if(!vr1){

                    mLastCtrlShowTime = System.currentTimeMillis();
//                Log.e(TAG, "mRootContent onClick mLastCtrlShowTime: " + mLastCtrlShowTime);
                    if(mCtrlLayout.getVisibility() != View.VISIBLE){
                        mCtrlLayout.setVisibility(View.VISIBLE);
                    }
                    
                    pause(mThis);
//                    callStop(mThis);
                    Log.e(TAG, "onScroll getGlobalVisibleRect " + vr1);
                }
                
//                int[] rL = new int[2];
//                int[] mL = new int[2]; 
//                r.getLocationOnScreen(rL);
//                mVideoView.getLocationOnScreen(mL);
//                
//                int pX = rL[0];
//                int pX2 = pX + r.getHeight();
//                
//                int mX = mL[0];
//                int mX2 = mX + mVideoView.getHeight();
//                
//                int top = (int) mVideoView.getX();
//                int bottom = (int) mVideoView.getY();
//                if(true){
////                    Log.e(TAG, "onScroll " + pX + ", " + pX2 + ", " + mX + ", " + mX2);
//                }
//
//                Rect rRect = new Rect();
//                boolean rr1 = r.getGlobalVisibleRect(rRect);
//                
//                Rect mRect = new Rect();
//                boolean vr1 = mVideoView.getGlobalVisibleRect(mRect);
//                
//                Rect mRect2 = new Rect(rRect);
//                boolean vr2 = mVideoView.getLocalVisibleRect(mRect2);
//                Log.e(TAG, "onScroll 1 " + rRect.top + ", " + rRect.bottom + ", " + rr1);
//                Log.e(TAG, "onScroll 2 " + mRect.top + ", " + mRect.bottom + ", " + vr1);
//                Log.e(TAG, "onScroll 3 " + mRect2.top + ", " + mRect2.bottom + ", " + vr2);
            }
        }
    }

    @Override
    public boolean onBack(IVideoController controller) {
        if(mFull.isFull()){
            mFull.exitFullScreen();
            return true;
        }
        return false;
    }

    @Override
    public void onMovedToScrapHeap(IVideoController controller, View view) {
        if(view != null){
            View vRoot = view.findViewById(R.id.video_layout);
            if(vRoot != null){
                Object tag = vRoot.getTag();
                if(tag != null && tag instanceof VideoItemWidget){
                    VideoItemWidget viw = (VideoItemWidget)tag;
                    viw.destroyVideo();
                }
            }
        }
    }
    
    public void enterFloatingDetail(ViewGroup container){
        if(!mFloating.isFloatingDetail()){
            mFloating.enterFloatingDetail(container);
        }
    }

    public void exitFloatingDetail(){
        if(mFloating.isFloatingDetail()){
            mFloating.exitFloatingDetail();
        }
    }
    
    public class FloatingDetail{
        ViewGroup mParent;
        View mView;
        ViewGroup mContainer;
        
        private boolean mIsFloatingDetail;
        private ViewGroup.LayoutParams mEnterLP;

        private boolean mIsChangeFloating;
        private boolean mIsChangeFloatingPlaying;
        private int mChangeFloatingPosition;

        public boolean isFloatingDetail(){
            return mIsFloatingDetail;
        }

        public void onPrepared(){
            if(mIsChangeFloating){
                mIsChangeFloating = false;
                if(mChangeFloatingPosition != 0){
                    mManager.seekTo(mThis, mChangeFloatingPosition);
                }
                if(mIsChangeFloatingPlaying && !isPlaying(mThis)){
                    mManager.start(mThis);
                }
            }
        }

        public void enterFloatingDetail(ViewGroup container){
            mIsChangeFloating = true;
            mIsChangeFloatingPlaying = mThis.isPlaying(mThis);
            mChangeFloatingPosition = getCurrentPosition(mThis);

            mIsFloatingDetail = true;
            this.mView = mThis.mRootContent;
            this.mContainer = container;

            mEnterLP = new ViewGroup.LayoutParams(mView.getWidth(), mView.getHeight());

            mParent = (ViewGroup) mView.getParent();
            mParent.removeView(mView);
            mContainer.addView(mView);
        }

        public void exitFloatingDetail(){
            mIsChangeFloating = true;
            mIsChangeFloatingPlaying = mThis.isPlaying(mThis);
            mChangeFloatingPosition = getCurrentPosition(mThis);

            mIsFloatingDetail = false;
            mContainer.removeAllViews();

            mParent.removeAllViews();
            mParent.addView(mView, mEnterLP);
//            mParent.postInvalidate();
//            mRoot.requestLayout();
        }
    }

    public class FullScreen{
        /** 视频全屏参数 */
        protected final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        ViewGroup mFullHideLayout;

        ViewGroup mParent;
        View mView;
        
        private boolean mIsFull;
        private ViewGroup.LayoutParams mEnterLP;

        private FullScreenShowListener mFullScreenListener;
        private FullscreenHolder fullscreenContainer;

        private boolean mIsChangeFull;
        private boolean mIsChangeFullPlaying;
        private int mChangeFullPosition;

        FullScreen(){
            mFullHideLayout = (ViewGroup) mActivity.findViewById(R.id.fullscreen_hide_layout);
            mIsFull = false;
            mIsChangeFull = false;
            mIsChangeFullPlaying = false;
        }
        
        public void setFullScreenListener(FullScreenShowListener listener){
            this.mFullScreenListener = listener;
        }

        public boolean isFull(){
            return mIsFull;
        }

        public void onPrepared(){
            if(mIsChangeFull){
                mIsChangeFull = false;
                if(mChangeFullPosition != 0){
                    mManager.seekTo(mThis, mChangeFullPosition);
                }
                if(mIsChangeFullPlaying && !isPlaying(mThis)){
                    mManager.start(mThis);
                }
            }
        }
        
        public void switchFullScreen(View view){
            mManager.setVideoController(mThis);
            mLastCtrlShowTime = System.currentTimeMillis();
            if(mIsFull){
                exitFullScreen();
            }else{
                enterFullScreen(view);
            }
        }
        
        public void enterFullScreen(View view){
            mIsChangeFull = true;
            mIsChangeFullPlaying = mThis.isPlaying(mThis);
            mChangeFullPosition = getCurrentPosition(mThis);
            
            mIsFull = true;
            this.mView = view;
            mParent = (ViewGroup) mView.getParent();
            mParent.removeView(mView);

            mEnterLP = new ViewGroup.LayoutParams(mView.getWidth(), mView.getHeight());

            FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
            fullscreenContainer = new FullscreenHolder(mActivity);
            fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
            decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
            setStatusBarVisibility(false);
            
            mFullHideLayout.setVisibility(View.INVISIBLE);

            if(mFullScreenListener != null){
                mFullScreenListener.showFullScreen();
            }
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        
        public void exitFullScreen(){
            mIsChangeFull = true;
            mIsChangeFullPlaying = mThis.isPlaying(mThis);
            mChangeFullPosition = getCurrentPosition(mThis);
            
            mIsFull = false;
            setStatusBarVisibility(true);
            FrameLayout decor = (FrameLayout) mActivity.getWindow().getDecorView();
            decor.removeView(fullscreenContainer);
            fullscreenContainer.removeAllViews();
            
            mParent.addView(mView, mEnterLP);
//            mParent.postInvalidate();
//            mRoot.requestLayout();
            mFullHideLayout.setVisibility(View.VISIBLE);

            if(mFullScreenListener != null){
                mFullScreenListener.hideFullScreen();
            }
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        private void setStatusBarVisibility(boolean visible) {
            int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
            mActivity.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /** 全屏容器界面 */
    private class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }
    
    public interface FullScreenShowListener {
        void showFullScreen();
        void hideFullScreen();
    }
    
    public enum VIWState{
        Idle,
        Prepared,
        Started,
        Paused,
    }

    public static class ListViewItemData{
        private ListView itemListView;
        private int itemPosition;
        private BaseArticlesBean mBean;

        public ListViewItemData(ListView view, int position, BaseArticlesBean bean){
            this.itemListView = view;
            this.itemPosition = position;
            this.mBean = bean;
        }

        public ListView getListView(){
            return itemListView;
        }

        public int getPosition(){
            return itemPosition;
        }
        
        public BaseArticlesBean getBean(){
            return mBean;
        }
    }
    
}
