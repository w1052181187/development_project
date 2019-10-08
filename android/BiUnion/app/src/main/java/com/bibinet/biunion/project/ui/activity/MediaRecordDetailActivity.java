package com.bibinet.biunion.project.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.models.MediaRecordDetailModel;
import com.bibinet.biunion.project.net.MyRetrofitResponseCallback;
import com.bibinet.biunion.project.net.ready.MediaLiveReady;
import com.bibinet.biunion.project.nicevideo.FhVideoPlayerController;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.ui.expand.TitleActivity;
import com.bibinet.biunion.project.utils.StatusBarUtil;
import com.bibinet.biunion.project.utils.ToastUtils;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import butterknife.BindView;

public class MediaRecordDetailActivity extends TitleActivity{

    final int MSG_SUCCESS = 1;
    final int MSG_FAIL = 2;

    @BindView(R.id.nice_video_player)
    NiceVideoPlayer mVideoPlayer;

    private String mRecoedObjeciId;
    private String mLiveName;
    private FhVideoPlayerController mController;
    private WaitDialog waitDialog;

    public static void launch(Context context, String liveName, String recordObectId) {
        Intent intent = new Intent(context, MediaRecordDetailActivity.class);
        intent.putExtra(Constants.MEDIA_RECORD_DETAIL_DATA_NAME, liveName);
        intent.putExtra(Constants.MEDIA_RECORD_DETAIL_DATA_RECORD_PBJECTID, recordObectId);
        context.startActivity(intent);
    }

    @Override
    protected int getTitleLayoutId() {
        return R.layout.activity_media_video_play;
    }

    @Override
    protected int getTitleString() {
        return 0;
    }

    @Override
    protected void onTitleCreate(Bundle savedInstanceState) {
        setTitleBackgroundColor(R.color.black);
        StatusBarUtil.setLightBar(this, false);
        hideTitleBar();

        waitDialog = new WaitDialog(this);
        mRecoedObjeciId = getIntent().getStringExtra(Constants.MEDIA_RECORD_DETAIL_DATA_RECORD_PBJECTID);
        mLiveName = getIntent().getStringExtra(Constants.MEDIA_RECORD_DETAIL_DATA_NAME);

        mVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);
        mController = new FhVideoPlayerController(this);
        mController.setTitle(mLiveName);
        mController.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mVideoPlayer.setController(mController);
        mVideoPlayer.release();
        waitDialog.open();
        new MediaLiveReady().getRecordPlayUrl(mRecoedObjeciId, new MyRetrofitResponseCallback<MediaRecordDetailModel>() {
            @Override
            protected void onRequestFail(String resMessage) {
                mHandler.obtainMessage(MSG_FAIL).sendToTarget();
            }

            @Override
            protected void onRequestSuccess(MediaRecordDetailModel model) {
                mHandler.obtainMessage(MSG_SUCCESS,model).sendToTarget();
            }

        });


    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    waitDialog.close();
                    MediaRecordDetailModel model = (MediaRecordDetailModel) msg.obj;
                    if (model != null && !TextUtils.isEmpty(model.getRecordUrl())) {
                        mVideoPlayer.setUp(model.getRecordUrl(), null);
                        mVideoPlayer.start();
                        mController.setLenght(mVideoPlayer.getDuration());
                    }
                    break;
                    case MSG_FAIL:
                        waitDialog.close();
                        ToastUtils.showShort("播放失败，请重新播放！");
                        finish();
                        break ;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onResume() {
        mVideoPlayer.restart();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mVideoPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mVideoPlayer.exitTinyWindow();
        mVideoPlayer.setTinyWindowParent(null);
        mVideoPlayer.release();
        super.onDestroy();
    }

}
