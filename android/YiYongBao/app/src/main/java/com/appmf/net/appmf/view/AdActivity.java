package com.appmf.net.appmf.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.appmf.net.appmf.R;
import com.appmf.net.appmf.app.Constants;
import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.BaseHandler;
import com.appmf.net.appmf.base.activity.AbstractSimpleActivity;
import com.appmf.net.appmf.bean.AdConfigBean;
import com.appmf.net.appmf.bean.AdContentBean;
import com.appmf.net.appmf.dao.AdStatisticsModel;
import com.appmf.net.appmf.dao.AdStatisticsModelDao;
import com.appmf.net.appmf.dao.DaoSession;
import com.appmf.net.appmf.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

import static com.appmf.net.appmf.app.Constants.KEY_INTENT_AD;
import static com.appmf.net.appmf.app.Constants.KEY_INTENT_AD_CACHE;

public class AdActivity extends AbstractSimpleActivity {

    private final int MSG_AD_STOP = 1;

    private AdContentBean adContentBean;
    private int adFormat;
    private DaoSession daoSession;

    @BindView(R.id.loop_viewpager)
    BannerViewPager mViewPager;
    @BindView(R.id.ad_img_layout)
    View adImgLayout;
//    @BindView(R.id.bottom_scale_layout)
//    ZoomIndicator zoomIndicator;
    @BindView(R.id.ad_time)
    TextView adTimeTv;
    @BindView(R.id.ad_video)
    VideoView adVideoView;
    @BindView(R.id.ad_video_layout)
    View adVideoLayout;
    @BindView(R.id.ad_img_single)
    ImageView adImgSingle;

    private String adId;
    private String adSource;
    private int adStatistics;
    private int adTime;
    private int adTimeStatus;
    private boolean adHasCache;
    private String adVideoUrl;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initEventAndData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // 隐藏状态栏
        adContentBean = (AdContentBean) getIntent().getSerializableExtra(KEY_INTENT_AD);
        adHasCache = getIntent().getBooleanExtra(KEY_INTENT_AD_CACHE, false);
        adId = adContentBean.getId();
        adSource = adContentBean.getSource();
        adFormat = adContentBean.getFormat();
        AdConfigBean adConfigBean = MyApp.getInstance().getAdConfigBean();
        adStatistics = adConfigBean.getStatistics();
        adTime = adConfigBean.getTime();
        if (adContentBean.getCount_down() > 0) {
            adTime = adContentBean.getCount_down();
        }
        adTimeStatus = adConfigBean.getTimeStatus();
        adTimeTv.setText("跳过广告  " + adTime);
        initListener();
        handleStatistics("show");
        handleAdShow(adFormat, adContentBean);
    }

    private void initListener() {
        adTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.obtainMessage(MSG_AD_STOP).sendToTarget();
            }
        });
    }

    void lanchMain() {
        MainActivity.launch(AdActivity.this);
        finish();
    }

    BaseHandler handler = new BaseHandler(new BaseHandler.HandlerMsgCallback() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AD_STOP:
                    adTime = 0;
                    handler.removeCallbacks(updateThread);
                    lanchMain();
                    break;
            }
        }
    });

    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            adTime--;
            if (adTime > 0) {
                adTimeTv.setText("跳过广告  " + adTime);
                handler.postDelayed(this, 1000);
            } else {
                handler.obtainMessage(MSG_AD_STOP).sendToTarget();
            }
        }
    };

    private void handleAdShow(int adType, final AdContentBean adContentBean) {
        switch (adType) {
            case 1:
                final ArrayList<String> adImages = (ArrayList<String>) adContentBean.getImg();
                PageBean bean = new PageBean.Builder<String>()
                        .setDataObjects(adImages)
//                        .setIndicator(zoomIndicator)
                        .builder();
                if (adImages == null) {
                    lanchMain();
                    return;
                }
                adTimeTv.setVisibility(adTimeStatus == 1 ? View.VISIBLE : View.GONE);
                handler.postDelayed(updateThread, 1000);
                if (adImages.size() == 1) {
                    mViewPager.stopAnim();
                    adImgSingle.setVisibility(View.VISIBLE);
                    Glide.with(adImgSingle).load(adImages.get(0)).into(adImgSingle);
                    adImgSingle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleAdClick(adContentBean.getType());
                        }
                    });
                } else {
                    adImgLayout.setVisibility(View.VISIBLE);
                    mViewPager.setPageListener(bean, R.layout.item_ad_img, new PageHelperListener() {
                        @Override
                        public void getItemView(View view, Object o) {
                            ImageView imageView = view.findViewById(R.id.item_ad_img);
                            String url = (String) o;
                            if (adHasCache) {
                                final String name = new File(url).getName();
                                final String path = Constants.PATH_CACHE_AD_IMAGE + "/" + name;
                                Glide.with(imageView).asFile().load(new File(path)).into(imageView);
                            } else {
                                Glide.with(imageView).load(url).into(imageView);
                            }
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    handleAdClick(adContentBean.getType());
                                }
                            });
                        }
                    });
                }
                break;
            case 2:
                adVideoUrl = adContentBean.getVideo();
                if (adHasCache) {
                    final String name = new File(adVideoUrl).getName();
                    final String path = Constants.PATH_CACHE_AD_IMAGE + "/" + name;
                    adVideoUrl = path;
                }
                if (TextUtils.isEmpty(adVideoUrl)) {
                    lanchMain();
                    return;
                }
                initeVideoViewListener();
                adVideoLayout.setVisibility(View.VISIBLE);
                adVideoView.setVideoPath(adVideoUrl);
                //  消除白边
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                adVideoView.setLayoutParams(layoutParams);
                adVideoView.start();
                break;
        }
    }

    private void initeVideoViewListener() {
        adVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                adVideoView.setVideoPath(adVideoUrl);
                adVideoView.start();
            }
        });
        adVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                adTimeTv.setVisibility(adTimeStatus == 1 ? View.VISIBLE : View.GONE);
                handler.postDelayed(updateThread, 1000);
            }
        });
        adVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (adVideoView != null) {
                    adVideoView.pause();
                    adVideoView.stopPlayback();
                }
                lanchMain();
                return false;
            }
        });
        adVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (adVideoView.isPlaying()) {
                    handleAdClick(adContentBean.getType());
                }
                return false;
            }
        });
    }

    private void handleAdClick(String type) {
        if (TextUtils.equals(adSource, "appmf")) {
            handleStatistics("click");
            return;
        }
        switch (type) {
            case "explorer" :
                Uri uri = Uri.parse(adSource);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case "app" :
                CommonUtils.launchapp(this, adSource);
                break;
            case "page" :
                String adtion = adSource;
                Intent pageIntent = new Intent(adtion);
                startActivity(pageIntent);
                break;
        }
    }

    /**
     * 广告统计
     */
    private void handleStatistics(String type) {
        if (adStatistics != 1) {
            return;
        }
        daoSession = MyApp.getInstance().getDaoSession();
        AdStatisticsModel adStatisticsBean = daoSession.getAdStatisticsModelDao().queryBuilder()
                .where(AdStatisticsModelDao.Properties.Id.eq(adId)).unique();
        if (adStatisticsBean == null) {
            adStatisticsBean = new AdStatisticsModel(adId, 0, 0, 0, 0);
        }
        switch (type) {
            case "click":
                long click = adStatisticsBean.getShows();
                adStatisticsBean.setShows(++click);
                break;
            case "show":
                long show = adStatisticsBean.getShows();
                adStatisticsBean.setShows(++show);
                break;
        }
        daoSession.insertOrReplace(adStatisticsBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adTime = 0;
        handler.removeCallbacks(updateThread);
        if (adVideoView != null) {
            adVideoView.stopPlayback();
            adVideoView = null;
        }
    }
}
