package com.bibinet.biunion.project.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.models.MediaLiveFinishModel;
import com.bibinet.biunion.project.ui.expand.TitleActivity;
import com.bibinet.biunion.project.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MediaLiveFinishActivity extends TitleActivity {

    private static final String LIVE_TYPE = "liveType";
    private static final String LIVE_DATA = "liveData";

    @BindView(R.id.act_media_live_finish_tv)
    TextView mContentTv;
    @BindView(R.id.act_media_live_finish_record_hint)
    TextView mRecordTv;

    private Constants.LiveType mType;
    private MediaLiveFinishModel mModel;

    public static void launch(Activity from, Constants.LiveType type, MediaLiveFinishModel finishModel) {
        Intent intent = new Intent(from,MediaLiveFinishActivity.class);
        intent.putExtra(LIVE_TYPE , type);
        intent.putExtra(LIVE_DATA , finishModel);
        from.startActivity(intent);
    }

    public static void launch(Activity from, Constants.LiveType type) {
        Intent intent = new Intent(from,MediaLiveFinishActivity.class);
        intent.putExtra(LIVE_TYPE , type);
        from.startActivity(intent);
    }

    @Override
    protected int getTitleLayoutId() {
        return R.layout.activity_media_live_finish;
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

        mType = (Constants.LiveType) getIntent().getSerializableExtra(LIVE_TYPE);
        mModel = (MediaLiveFinishModel) getIntent().getSerializableExtra(LIVE_DATA);

        StringBuffer buffer = new StringBuffer("本次直播已结束，");
        switch (mType) {
            case TYPE_ANCHOT:
                if (mModel != null) {
                    buffer.append("直播时长").append(mModel.getLiveDuration()).append("\n观看人数：")
                            .append(!TextUtils.isEmpty(mModel.getUserNum()) ? mModel.getUserNum() : 0);
                    mRecordTv.setVisibility(mModel.isRecorded() ? View.VISIBLE : View.GONE);
                }

                break;
            case TYPE_SPECTATOR:
                buffer.append("录播视频返回后可查看");
                mContentTv.setVisibility(View.GONE);
                mRecordTv.setVisibility(View.GONE);
                break;
        }
        mContentTv.setText(buffer.toString());
    }

    @OnClick({R.id.act_media_live_finish_close, R.id.act_media_live_finish_back})
    void onCloseClick() {
        finish();
    }
}
