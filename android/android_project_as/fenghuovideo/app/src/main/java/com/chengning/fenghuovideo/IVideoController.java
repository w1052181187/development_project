package com.chengning.fenghuovideo;

import android.view.View;
import android.widget.AbsListView;

public interface IVideoController{
    public void    callStop(IVideoController controller);
    public void    start(IVideoController controller);
    public void    pause(IVideoController controller);
    public int     getDuration(IVideoController controller);
    public int     getCurrentPosition(IVideoController controller);
    public void    seekTo(IVideoController controller, int pos);
    public boolean isPlaying(IVideoController controller);
    public int     getBufferPercentage(IVideoController controller);
    public boolean canPause(IVideoController controller);
//        public boolean canSeekBackward();
//        public boolean canSeekForward();

    public void onScroll(IVideoController controller, AbsListView view);
    public boolean onBack(IVideoController controller);
    public void onMovedToScrapHeap(IVideoController controller, View view);
}
