package com.bibinet.biunion.project.ui.expand;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.manager.StatusBarManager;
import com.bibinet.biunion.project.ui.base.BaseActivity;
import com.bibinet.biunion.project.utils.DensityUtil;
import com.bibinet.biunion.project.utils.SoftKeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by bibinet on 2017-6-7.
 */

public abstract class TitleActivity extends BaseActivity {
    @BindView(R.id.v_title_background)
    View backgroundV;

    @BindView(R.id.v_title_right_main)
    View rightV;
    @BindView(R.id.v_title_right_main_img)
    ImageView rightImgIV;
    @BindView(R.id.v_title_right_main_txt)
    TextView rightTxtTV;

    @BindView(R.id.v_title_right_second_main)
    View rightSecondV;
    @BindView(R.id.v_title_right_second_main_img)
    ImageView rightSecondImgIV;
    @BindView(R.id.v_title_right_second_main_txt)
    TextView rightSecondTxtTV;

    @OnClick(R.id.v_title_exit)
    protected void exit() {
        SoftKeyboardUtils.hintKbTwo(this);
        finish();
    }

    @BindView(R.id.v_title_txt)
    TextView titleTV;
    @BindView(R.id.v_title_select_icon)
    View selectIconV;

    protected TextView getTitleTV() {
        return titleTV;
    }

    protected void setSelectIconState(boolean isSelect) {
        selectIconV.setSelected(isSelect);
    }

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
    protected final void onBaseCreate(Bundle savedInstanceState) {
        StatusBarManager.addTitleBar(this, mainLL);
        //左标题按钮 默认显示 默认返回 有需要的自行定制
        //右标题按钮 默认不显示 默认无点击时间 有需要自行定制 分为图片展示和字体展示
        if (getTitleString() != 0) {
            titleTV.setText(getTitleString());
        } else {
            titleTV.setText("");
        }
        onTitleCreate(savedInstanceState);
    }

    protected void setTitleString(String str) {
        titleTV.setText(str);
    }

    protected void setTitleString(int strId) {
        titleTV.setText(strId);
    }


    protected abstract int getTitleString();

    protected abstract void onTitleCreate(Bundle savedInstanceState);

    protected void setTitleBtn(boolean isShowTag, View.OnClickListener onClickListener) {
        if (isShowTag) {
            selectIconV.setVisibility(View.VISIBLE);
        } else {
            selectIconV.setVisibility(View.GONE);
        }
        selectIconV.setOnClickListener(onClickListener);
        titleTV.setOnClickListener(onClickListener);
    }

    protected void setRightBtn(int txt, int img, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            rightV.setVisibility(View.GONE);
        } else {
            rightV.setVisibility(View.VISIBLE);
            rightV.setOnClickListener(onClickListener);
            if (txt == 0) {
                rightImgIV.setImageResource(img);
                rightImgIV.setVisibility(View.VISIBLE);
                rightTxtTV.setVisibility(View.GONE);
            } else {
                rightTxtTV.setText(txt);
                rightImgIV.setVisibility(View.GONE);
                rightTxtTV.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void setRightBtn(int txt, int img, int padding, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            rightV.setVisibility(View.GONE);
        } else {
            rightV.setVisibility(View.VISIBLE);
            rightV.setOnClickListener(onClickListener);
            if (txt == 0) {
                rightImgIV.setImageResource(img);
                int p = DensityUtil.dip2px(this, padding);
                rightImgIV.setPadding(p, p, p, p);
                rightImgIV.setVisibility(View.VISIBLE);
                rightTxtTV.setVisibility(View.GONE);
            } else {
                rightTxtTV.setText(txt);
                rightImgIV.setVisibility(View.GONE);
                rightTxtTV.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void setRightBtnShow() {
        rightV.setVisibility(View.VISIBLE);
    }

    protected void setRightBtnHide() {
        rightV.setVisibility(View.GONE);
    }

    protected void setRightSecondBtn(int txt, int img, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            rightSecondV.setVisibility(View.GONE);
        } else {
            rightSecondV.setVisibility(View.VISIBLE);
            rightSecondV.setOnClickListener(onClickListener);
            if (txt == 0) {
                rightSecondImgIV.setImageResource(img);
                rightSecondImgIV.setVisibility(View.VISIBLE);
                rightSecondTxtTV.setVisibility(View.GONE);
            } else {
                rightSecondTxtTV.setText(txt);
                rightSecondImgIV.setVisibility(View.GONE);
                rightSecondTxtTV.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void setRightSecondState(boolean isSelect) {
        rightSecondV.setSelected(isSelect);
    }

    protected boolean getRightSecondState() {
        return rightSecondV.isSelected();
    }

    protected void setTitleBackgroundColor(int colorId) {
        int color = getResources().getColor(colorId);
        backgroundV.setBackgroundColor(color);
        //大多数需要一体改变 状态栏
        mainLL.getChildAt(0).setBackgroundColor(color);
    }

    //隐藏标题栏，状态栏背景不受影响
    protected void hideTitleBar() {
        backgroundV.setVisibility(View.GONE);
    }
}
