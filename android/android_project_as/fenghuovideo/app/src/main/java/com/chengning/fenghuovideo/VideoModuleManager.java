package com.chengning.fenghuovideo;

import android.view.View;
import android.widget.AbsListView;

import com.chengning.fenghuovideo.widget.VideoItemWidget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;

public class VideoModuleManager implements IVideoController {

    private static VideoModuleManager mManager;

    private IVideoController mController;

    private LinkedList<WeakReference<VideoItemWidget>> mWorkList;

    public static VideoModuleManager getInst(){
        if(mManager == null){
            mManager = new VideoModuleManager();
        }
        return mManager;
    }

    private VideoModuleManager(){
        mWorkList = new LinkedList<>();
    }
    
    public void regist(IVideoController controller){
        WeakReference wr = new WeakReference(controller);
        mWorkList.add(wr);
    }

    public void setVideoController(IVideoController controller){
        lastCallStop(controller);
        mController = controller;
    }

    public IVideoController getVideoController(){
        return mController;
    }

    public void removeVideoController(IVideoController controller){
        if(mController == controller){
            mController = null;
        }
    }

    private void lastCallStop(IVideoController controller){
        if(mController != null && mController != controller){
            mController.callStop(mController);
        }
    }


    @Override
    public void callStop(IVideoController controller) {
        if(controller != null){
            controller.callStop(controller);
        }else if(mController != null){
            mController.callStop(controller);
        }
    }


    @Override
    public void start(IVideoController controller) {
        lastCallStop(controller);
        mController = controller;
        if(mController != null){
            mController.start(controller);
        }
    }

    @Override
    public void pause(IVideoController controller) {
        if(controller != null){
            controller.pause(mController);
        }else if(mController != null){
            mController.pause(mController);
        }
    }

    @Override
    public int getDuration(IVideoController controller) {
        if(controller != null){
            return controller.getDuration(controller);
        }else if(mController != null){
            return mController.getDuration(controller);
        }
        return 0;
    }

    @Override
    public int getCurrentPosition(IVideoController controller) {
        if(controller != null){
            return controller.getCurrentPosition(controller);
        }else if(mController != null){
            return mController.getCurrentPosition(controller);
        }
        return 0;
    }

    @Override
    public void seekTo(IVideoController controller, int pos) {
        lastCallStop(controller);
        if(controller != null){
            controller.seekTo(controller, pos);
        }else if(mController != null){
            mController.seekTo(controller, pos);
        }
    }

    @Override
    public boolean isPlaying(IVideoController controller) {
        if(controller != null){
            return controller.isPlaying(controller);
        }else if(mController != null){
            return mController.isPlaying(controller);
        }
        return false;
    }

    @Override
    public int getBufferPercentage(IVideoController controller) {
        if(controller != null){
            return controller.getBufferPercentage(controller);
        }else if(mController != null){
            return mController.getBufferPercentage(controller);
        }
        return 0;
    }

    @Override
    public boolean canPause(IVideoController controller) {
        if(controller != null){
            return controller.canPause(controller);
        }else if(mController != null){
            return mController.canPause(controller);
        }
        return false;
    }

    @Override
    public void onScroll(IVideoController controller, AbsListView view) {
        if(controller != null){
            controller.onScroll(mController, view);
        }else if(mController != null){
            mController.onScroll(mController, view);
        }
    }

    @Override
    public boolean onBack(IVideoController controller) {
        if(controller != null){
            return controller.onBack(controller);
        }else if(mController != null){
            return mController.onBack(controller);
        }
        return false;
    }

    @Override
    public void onMovedToScrapHeap(IVideoController controller, View view) {
        if(controller != null){
            controller.onMovedToScrapHeap(controller, view);
        }else if(mController != null){
            mController.onMovedToScrapHeap(controller, view);
        }
        if(controller == null && !mWorkList.isEmpty()){
            for(WeakReference wr : mWorkList){
                if(wr != null){
                    Object wrObj = wr.get();
                    if(wrObj != null && wrObj instanceof IVideoController){
                        IVideoController wRontroller = (IVideoController) wrObj;
                        wRontroller.onMovedToScrapHeap(controller, view);
                    }
                }
            }
        }
    }

    public static class OnVideoModuleScrollListener implements AbsListView.OnScrollListener{
        private AbsListView.OnScrollListener listener;

        public OnVideoModuleScrollListener(){
        }
        
        public OnVideoModuleScrollListener(AbsListView.OnScrollListener listener){
            this.listener = listener;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(listener != null){
                listener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(listener != null){
                listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            VideoModuleManager.getInst().onScroll(null, view);
        }
    }
    
}
