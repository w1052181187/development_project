package com.chengning.fenghuovideo.nicevideo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chengning.fenghuovideo.Consts;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.chengning.fenghuovideo.R;

public class FhVideoViewHolder  {

    public FhVideoPlayerController mController;
    public NiceVideoPlayer mVideoPlayer;

    private FhVideo mVideo;
    
    public FhVideoViewHolder(View itemView) {
//        super(itemView);
        mVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
        params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        mVideoPlayer.setLayoutParams(params);
        mVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
    }

    public void setController(FhVideoPlayerController controller) {
        mController = controller;
        mVideoPlayer.setController(mController);
    }

    public void bindData(FhVideo video) {
        if(mVideo != null && strEquals(mVideo.getTitle(), video.getTitle()) && strEquals(mVideo.getVideoUrl(), video.getVideoUrl())){
            return;
        }
        this.mVideo = video;
        if(Consts.DEBUG) Log.d(FhVideoViewHolder.class.getSimpleName(), "bindData release, title: " + video.getTitle());
        mVideoPlayer.release();
        
        mController.setTitle(video.getTitle());
        mController.setLenght(video.getLength());
//        Glide.with(itemView.getContext())
//                .load(video.getImageUrl())
//                .placeholder(R.drawable.img_default)
//                .crossFade()
//                .into(mController.imageView());
//        mVideoPlayer.release();
        mVideoPlayer.setUp(video.getVideoUrl(), null);
    }
    
    public NiceVideoPlayer getPlayer(){
        return mVideoPlayer;
    }

    public FhVideoPlayerController getController(){
        return mController;
    }
    
    public FhVideo getData(){
        return mVideo;
    }
    
    private boolean strEquals(String s1, String s2){
        if(s1 != null && s2 != null){
            return s1.equals(s2);
        }
        return false;
    }
}
