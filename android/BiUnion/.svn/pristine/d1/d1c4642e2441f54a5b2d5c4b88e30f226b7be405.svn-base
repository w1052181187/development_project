package com.bibinet.biunion.project.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.manager.BrightnessManager;
import com.bibinet.biunion.project.ui.activity.MagazineDetailActivity;
import com.bibinet.biunion.project.ui.base.BasePop;
import com.bibinet.biunion.project.ui.custom.RadioLayout;
import com.bibinet.biunion.project.ui.manager.MagazineDetailManager;
import com.bibinet.biunion.project.utils.LogUtils;

import butterknife.BindView;

/**
 * Created by Wh on 2018-1-3.
 */

public class MagazineLightPop extends BasePop implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.p_magazine_light_seek)
    SeekBar lightSB;

    private MagazineDetailManager magazineDetailManager;
    private MagazineDetailActivity magazineDetailActivity;

    public MagazineLightPop(Context context, MagazineDetailManager magazineDetailManager) {
        super(context);
        this.magazineDetailActivity = (MagazineDetailActivity) context;
        this.magazineDetailManager = magazineDetailManager;
        LogUtils.e("ddx=", "res="+this.magazineDetailManager);
    }

    @Override
    protected void onCreate() {
        LogUtils.e("dd=", "res="+magazineDetailManager);
        lightSB.setOnSeekBarChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_magazine_light;
    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        lightSB.setProgress(magazineDetailManager.getBrightness());
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) {
            int progress = seekBar.getProgress();
            //设置当前 Activity 的亮度
            BrightnessManager.setBrightness(magazineDetailActivity, progress);
            //存储亮度的进度条
            magazineDetailManager.setBrightness(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
