package com.bibinet.biunion.project.ui.expand;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.manager.StatusBarManager;
import com.bibinet.biunion.project.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by bibinet on 2017-6-7.
 */

public abstract class TitleFragment extends BaseFragment {

    @OnClick(R.id.v_title_exit)
    void exit(){
        getActivity().finish();
    }

    @BindView(R.id.v_title_background)
    View backgroundV;

    @BindView(R.id.v_title_exit)
    ImageView leftIV;
    @BindView(R.id.v_title_right_main)
    View rightView;
    @BindView(R.id.v_title_right_main_img)
    ImageView rightImg;

    @BindView(R.id.v_title_txt)
    TextView titleTV;

    @BindView(R.id.v_title)
    LinearLayout mainLL;

    @Override
    protected final int getLayoutId() {
        return R.layout.view_title;
    }

    @Override
    protected View getLayoutView() {
        View view = createView(getLayoutId());
        FrameLayout mainFL = (FrameLayout) findView(view, R.id.v_title_main);
        mainFL.addView(createView(getTitleLayoutId()));
        return view;
    }

    protected abstract int getTitleLayoutId();

    @Override
    protected final void onBaseViewCreated(View view, Bundle savedInstanceState) {
        if(getTitleString() != 0){
            StatusBarManager.addTitleBar(getActivity(), mainLL);
            titleTV.setText(getTitleString());
        }else{
            backgroundV.setVisibility(View.GONE);
        }
        onTitleViewCreated(view, savedInstanceState);
    }

    protected abstract int getTitleString();

    protected void setLeftBtn(int img, View.OnClickListener onClickListener){
        if(onClickListener == null){
            leftIV.setVisibility(View.GONE);
        }else{
            leftIV.setVisibility(View.VISIBLE);
            leftIV.setImageResource(img);
            leftIV.setOnClickListener(onClickListener);
        }
    }

    protected void setRightBtn(int img, View.OnClickListener onClickListener){
        if(onClickListener == null){
            rightView.setVisibility(View.GONE);
        }else{
            rightView.setVisibility(View.VISIBLE);
            rightImg.setImageResource(img);
            rightView.setOnClickListener(onClickListener);
        }
    }

    protected abstract void onTitleViewCreated(View view, Bundle savedInstanceState);
}
