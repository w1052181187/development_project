package com.appmf.net.appmf.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.appmf.net.appmf.R;
import com.appmf.net.appmf.app.Constants;
import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.base.BaseHandler;
import com.appmf.net.appmf.base.activity.BaseActivity;
import com.appmf.net.appmf.bean.AdConfigBean;
import com.appmf.net.appmf.bean.AdContentBean;
import com.appmf.net.appmf.contract.SplashContract;
import com.appmf.net.appmf.dao.AdStatisticsModel;
import com.appmf.net.appmf.dao.AdStatisticsModelDao;
import com.appmf.net.appmf.dao.DaoSession;
import com.appmf.net.appmf.presenter.SplashPresenter;
import com.appmf.net.appmf.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;

    private final int MSG_AD_STOP = 1;

    private AdContentBean adContentBean;
    private int adFormat;
    private DaoSession daoSession;

    @BindView(R.id.normal_view)
    View rootLayout;
    @BindView(R.id.splash_img)
    ImageView splashImg;
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

    private RequestListener<Drawable> requestListener;
    private RequestListener<File> requestFileListener;
    private boolean isPaused = false;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // 隐藏状态栏
        //将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        showNormal();
        mPresenter.attachView(this);
        initListener();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPresenter.handleSysConfigFile();
//            }
//        },2000);
        initAnimation();
    }

    private void initAnimation() {
        Animation animation = new AlphaAnimation(1.0f, 1.0f);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPresenter.handleAdConfigFile();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootLayout.startAnimation(animation);
        animation.start();
    }

    private void initListener() {
        adTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.obtainMessage(MSG_AD_STOP).sendToTarget();
            }
        });
        requestListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                launchMain();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                showLeftTime();
                return false;
            }
        };
        requestFileListener = new RequestListener<File>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                launchMain();
                return false;
            }

            @Override
            public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                showLeftTime();
                return false;
            }
        };
    }

    BaseHandler handler = new BaseHandler(new BaseHandler.HandlerMsgCallback() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AD_STOP:
                    adTime = 0;
                    handler.removeCallbacks(updateThread);
                    launchMain();
                    break;
            }
        }
    });

    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            if (isPaused) {
                return;
            }
            adTime--;
            if (adTime > 0) {
                adTimeTv.setText("跳过广告  " + adTime);
                handler.postDelayed(this, 1000);
            } else {
                handler.obtainMessage(MSG_AD_STOP).sendToTarget();
            }
        }
    };

    private void handleAdShow(int adType, final AdContentBean adContentBean) throws Exception{
        switch (adType) {
            case 1:
                final ArrayList<String> adImages = (ArrayList<String>) adContentBean.getImg();
                if (adImages == null) {
                    launchMain();
                    return;
                }
                handler.postDelayed(updateThread, 1000);
                if (adImages.size() == 1) {
                    adImgSingle.setVisibility(View.VISIBLE);
                    String url = adImages.get(0);
                    if (adHasCache) {
                        final String name = new File(url).getName();
                        final String path = Constants.PATH_CACHE_AD_IMAGE + "/" + name;
                        url = path;
                    }
                    Glide.with(adImgSingle).load(url).listener(requestListener).into(adImgSingle);
                    adImgSingle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleAdClick(adContentBean);
                        }
                    });
                } else {
                    PageBean bean = new PageBean.Builder<String>()
                            .setDataObjects(adImages)
//                        .setIndicator(zoomIndicator)
                            .builder();
                    adImgLayout.setVisibility(View.VISIBLE);
                    mViewPager.setPageListener(bean, R.layout.item_ad_img, new PageHelperListener() {
                        @Override
                        public void getItemView(View view, Object o) {
                            ImageView imageView = view.findViewById(R.id.item_ad_img);
                            String url = (String) o;
                            if (adHasCache) {
                                final String name = new File(url).getName();
                                final String path = Constants.PATH_CACHE_AD_IMAGE + "/" + name;
                                url = path;
                            }
                            Glide.with(imageView).load(url).listener(requestListener).into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    handleAdClick(adContentBean);
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
                    final String path = Constants.PATH_CACHE_AD_VIDEO + "/" + name;
                    adVideoUrl = new File(path).getPath();
                }
                if (TextUtils.isEmpty(adVideoUrl)) {
                    launchMain();
                    return;
                }
                initeVideoViewListener();
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
                adVideoLayout.setVisibility(View.VISIBLE);
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
                adVideoView.setBackgroundColor(Color.TRANSPARENT);
                showLeftTime();
                handler.postDelayed(updateThread, 1000);
            }
        });
        adVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                adVideoLayout.setVisibility(View.GONE);
                stopPlaybackVideo();
                launchMain();
                return true;
            }
        });
        adVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.i("advideoView",what + "");
                return false;
            }
        });
        adVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (adVideoView.isPlaying()) {
                    handleAdClick(adContentBean);
                }
                return false;
            }
        });
    }

    private void showLeftTime() {
        adTimeTv.setVisibility(adTimeStatus == 1 ? View.VISIBLE : View.GONE);
        splashImg.setVisibility(View.GONE);
    }

    private void handleAdClick(AdContentBean contentBean) {
        if (TextUtils.equals(adSource, "appmf")) {
            handleStatistics(contentBean,"click");
            return;
        }
        String type = contentBean.getType();
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
    private void handleStatistics(AdContentBean bean, String type) {
        if (adStatistics != 1) {
            return;
        }
        daoSession = MyApp.getInstance().getDaoSession();
        AdStatisticsModel adStatisticsBean = daoSession.getAdStatisticsModelDao().queryBuilder()
                .where(AdStatisticsModelDao.Properties.Id.eq(adId)).unique();
        if (adStatisticsBean == null) {
            adStatisticsBean = new AdStatisticsModel(adId, 0, 0, 0,0);
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
        adStatisticsBean.setEnd_time(bean.getEnd_time());
        adStatisticsBean.setTime(CommonUtils.getCurTime10());
        daoSession.insertOrReplace(adStatisticsBean);
    }

    @Override
    public void launchMain() {
        MainActivity.launch(this);
        finish();
    }

    @Override
   public void launchAd(AdContentBean bean, boolean isHasCache) {
        adContentBean = bean;
        adHasCache = isHasCache;
        adId = bean.getId();
        adSource = bean.getSource();
        adFormat = bean.getFormat();
        AdConfigBean adConfigBean = MyApp.getInstance().getAdConfigBean();
        adStatistics = adConfigBean.getStatistics();
        adTime = adConfigBean.getTime();
        if (bean.getCount_down() > 0) {
            adTime = bean.getCount_down();
        }
        adTimeStatus = adConfigBean.getTimeStatus();
        adTimeTv.setText("跳过广告  " + adTime);

        handleStatistics(bean,"show");
        try {
            handleAdShow(adFormat, bean);
        } catch (Exception e) {
            launchMain();
        }
    }

    @Override
    public void onResume() {
        isPaused = false;
        if (adVideoView != null) {
            adVideoView.start();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        isPaused = true;
        if (adVideoView != null) {
            adVideoView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adTime = 0;
        handler.removeCallbacks(updateThread);
        stopPlaybackVideo();
    }

    private void stopPlaybackVideo() {
        try {
            if (adVideoView != null) {
                adVideoView.stopPlayback();
                adVideoView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
