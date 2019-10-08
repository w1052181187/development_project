package com.bibinet.biunion.project.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.ui.base.BasePop;
import com.bibinet.biunion.project.utils.DensityUtil;
import com.bibinet.biunion.project.utils.SharedPresUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Wh on 2018-6-27.
 */

public class MediaLiveMenuPop extends BasePop {

    private Context mContext;
    @BindView(R.id.live_menu_record_root)
    View mRootView;

    @BindView(R.id.live_menu_record_tv)
    TextView mRecordTv;

    @BindView(R.id.live_menu_meiyan_tv)
    TextView mMeiyanTv;

    @BindView(R.id.live_menu_fanzhuan_tv)
    TextView mFanzhuanTv;

    private OnLiveMenuPopListener onLivePopListener;
    private int popupHeight;
    private int popupWidth;
    private OnRecordHanlderListener recordHanlderListener;

    public MediaLiveMenuPop(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        mRootView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = mRootView.getMeasuredHeight();
        popupWidth = mRootView.getMeasuredWidth();

    }

    public void initView(Activity activity) {
        mContext = activity;
        mMeiyanTv.setSelected(SharedPresUtils.isBeautyOn(activity));
        mRecordTv.setSelected(SharedPresUtils.isRecordOn(activity));
        mFanzhuanTv.setSelected(SharedPresUtils.isFanzhuanOn(activity));
        recordHanlderListener = new OnRecordHanlderListener() {
            @Override
            public void onFail() {
                handlerRecordClick(!mRecordTv.isSelected());
            }
        };
    }

    public void showUp(View view) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] - (popupWidth / 2 - view.getWidth() / 2),
                location[1] - popupHeight - DensityUtil.dip2px(getContext(), 30));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_media_live_menu;
    }

    @OnClick(R.id.live_menu_record_tv)
    void onRecordClick() {
        if (onLivePopListener != null) {
            boolean isSelect = mRecordTv.isSelected();
            handlerRecordClick(!isSelect);
            onLivePopListener.onHandleRecord(!isSelect, recordHanlderListener);
        }
    }

    private void handlerRecordClick(boolean isSelected) {
        mRecordTv.setSelected(isSelected);
        SharedPresUtils.setRecordOn(mContext, isSelected);
    }

    @OnClick(R.id.live_menu_meiyan_tv)
    void onMeiyanClick() {
        if (onLivePopListener != null) {
            boolean isSeclect = mMeiyanTv.isSelected();
            mMeiyanTv.setSelected(!isSeclect);
            onLivePopListener.onHandleMeiyan(!isSeclect);
            SharedPresUtils.setBeautyOn(mContext, !isSeclect);
        }
    }

    @OnClick(R.id.live_menu_fanzhuan_tv)
    void onFanzhuanClick() {
        if (onLivePopListener != null) {
            boolean isSeclect = mFanzhuanTv.isSelected();
            mFanzhuanTv.setSelected(!isSeclect);
            onLivePopListener.onHandleFanzhuan(!isSeclect);
            SharedPresUtils.setFanzhuanOn(mContext, !isSeclect);
        }
    }

    @Override
    public void onDismiss() {
        onLivePopListener.onDismiss();
    }

    public void setListener(OnLiveMenuPopListener listener){
        this.onLivePopListener = listener;
    }

    public interface OnLiveMenuPopListener {
        void onDismiss();
        void onHandleRecord(boolean isRecord, OnRecordHanlderListener recordListener);
        void onHandleMeiyan(boolean isMeiyan);
        void onHandleFanzhuan(boolean isFanzhuan);
    }

    public interface OnRecordHanlderListener {
        void onFail();
    }
}
