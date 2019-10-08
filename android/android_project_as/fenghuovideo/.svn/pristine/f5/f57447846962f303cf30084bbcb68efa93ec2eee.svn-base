package com.chengning.fenghuovideo.nicevideo;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.JKDebug;
import com.chengning.fenghuovideo.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

/**
 * Created by jiankai on 2017/8/22.
 */

public class FhVideoPlayerHelper {
    
    private static final String TAG = FhVideoPlayerHelper.class.getSimpleName();
    
    private static FhVideoPlayerHelper mInst;
    
    private FhVideoPlayerHelper(){
        
    }
    
    public static synchronized FhVideoPlayerHelper getInst(){
        if(mInst == null){
            mInst = new FhVideoPlayerHelper();
        }
        return mInst;
    }
    
    public AbsListView.OnScrollListener mFhOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
//            Log.e(TAG, "onScrollStateChanged");
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(Consts.DEBUG)Log.d(TAG, "onScroll");

            NiceVideoPlayer np = NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer();
            if(np != null){
                if(np.isPlaying() || np.isBufferingPlaying()){
                    Rect rect = new Rect();
                    boolean notVisible = !np.getGlobalVisibleRect(rect) || (rect.left == 0 && rect.top == 0);
                    
//                    Rect rect2 = new Rect();
//                    boolean visible2 = np.getLocalVisibleRect(rect2);
//                    int[] out1 = new int[2];
//                    Integer[] out3 = new Integer[2];
//                    np.getLocationOnScreen(out1);
//                    int[] out2 = new int[2];
//                    np.getLocationInWindow(out2);
//                    if(Consts.DEBUG)Log.d(TAG, "onScroll visible: " + visible + ", rect: " + rect 
//                            + ", visible2: " + visible2  + ", rect2: " + rect2
//                            + ", out1: " + out1[0] + " " + out1[1]
//                            + ", out2: " + out2[0] + " " + out2[1]);
                    if(notVisible){
                        np.pause();
                    }
                }
            }
        }
    };
    
    public AbsListView.OnScrollListener getOnScrollListener(){
        return mFhOnScrollListener;
    }
    
    public void onMovedToScrapHeap(View view){
        View vRoot = view.findViewById(R.id.video_layout);
        if(vRoot != null) {
            Object tag = vRoot.getTag();
            if (tag != null && tag instanceof FhNiceVideoWidget) {
                FhNiceVideoWidget viw = (FhNiceVideoWidget) tag;
                if(viw.getHolder().getPlayer().isTinyWindow() || viw.getHolder().getPlayer().isFullScreen() 
                        || NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer() == viw.getHolder().getPlayer()){
                    return;
                }
                if(Consts.DEBUG)Log.d(FhVideoPlayerHelper.class.getSimpleName(), "onMovedToScrapHeap release, title: " + viw.getHolder().getData().getTitle());
                viw.release();
            }
        }
    }

    public boolean onBack() {
        NiceVideoPlayer np = NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer();
        if(np != null){
            if(np.isFullScreen()){
                return np.exitFullScreen();
            }
        }
//        return NiceVideoPlayerManager.instance().onBackPressd();
        return false;
    }
}
