package com.bibinet.biunion.project.ui.manager;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.builder.MyViewPager;
import com.bibinet.biunion.project.event.BannerClickEvent;
import com.bibinet.biunion.project.models.BannerModel;
import com.bibinet.biunion.project.net.UrlConfig;
import com.bibinet.biunion.project.ui.activity.BannerActivity;
import com.bibinet.biunion.project.ui.activity.MagazineActivity;
import com.bibinet.biunion.project.utils.Common;
import com.bibinet.biunion.project.utils.DensityUtil;
import com.bibinet.biunion.project.utils.GlideHelper;
import com.bibinet.biunion.project.utils.StatisticsUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bibinet on 2017-5-17.
 */

public class BannerManager {

    private final long interval = 3000;
    private MyViewPager viewPager;
    private boolean isRuning;
    private Activity context;
    private List<BannerModel.ItemBean> urls;
    private List<ImageView> imgList;
    private int lastPosition;
    private LinearLayout groupContain;
    private RequestOptions op;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int pos = viewPager.getCurrentItem();
            if (pos == imgList.size() - 1) {
                pos = 0;
            } else {
                pos +=1;
            }
            viewPager.setCurrentItem(pos);
            if (isRuning) {
                handler.sendEmptyMessageDelayed(0, interval);
            }
        }
    };
    private UrlConfig urlConfig;
    String bannerName;

    public BannerManager(Activity context, MyViewPager viewPager, LinearLayout groupContain, List<BannerModel.ItemBean> urls) {
        this.viewPager = viewPager;
        this.urls = urls;
        this.groupContain = groupContain;
        this.context = context;
        this.urlConfig = new UrlConfig();

        imgList = new ArrayList<ImageView>();
        op = GlideHelper.getInst().getBannerOptions();
    }
    private void initIv(){
        imgList.clear();

        for(int i=0 ; i< urls.size() ; i++){
            if(Common.isTrue(urls.get(i).getIsShow())) {
                continue;
            }
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final int finalI = i;
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int tPos = updatePos(finalI);
                    bannerName = urls.get(tPos).getBannerName();
                    StatisticsUtils.bannerClick(tPos, urls.get(tPos).getBannerName());
                    if(TextUtils.isEmpty(urls.get(tPos).getBannerDetail())){
                        return;
                    }
                    if (TextUtils.equals(bannerName,"招标采购经理人杂志")) {
                        MagazineActivity.start(context);
                    } else if (TextUtils.equals(bannerName,"看我想看、声价百万")) {
                        EventBus.getDefault().post(new BannerClickEvent());
                    } else {
                        BannerActivity.start(context, urls.get(tPos).getBannerDetail(), bannerName);
                    }

                }
            });
            iv.setTag(R.id.image_glide_uri, i);
            imgList.add(iv);
        }
    }
    public void startPlayBanner() {
        addPoint();
        initIv();
        MyPageAdapter adapter = new MyPageAdapter();
        viewPager.setAdapter(adapter);
        if (!isRuning && imgList.size() > 1) {
            isRuning = true;
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, interval);
            viewPager.setOnViewPagerTouchListioner(new MyViewPager.OnTouchListioner() {
                @Override
                public void onActionDown() {
                    handler.removeMessages(0);
                }

                @Override
                public void onActionMove() {
                    handler.removeMessages(0);
                }

                @Override
                public void onActionUp() {
                    handler.removeMessages(0);
                    handler.sendEmptyMessageDelayed(0, interval);
                }
            });
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        if (Common.isListEmpty(imgList)) {
                            return;
                        } else {
                            int tPos = updatePos(position);
                            groupContain.getChildAt(tPos).setEnabled(true);
                            groupContain.getChildAt(lastPosition).setEnabled(false);
                            lastPosition = tPos;
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    private void addPoint() {
        groupContain.removeAllViews();
        for (int i = 0; i < imgList.size(); i++) {
            ImageView iv = new ImageView(context);
            iv.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                iv.setEnabled(true);
            } else {
                iv.setEnabled(false);
            }
            //因为要把view添加到一个线性布局，故需要一个线性布局的LayoutParams
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 8), DensityUtil.dip2px(context, 8));
            params.rightMargin = DensityUtil.dip2px(context, 12);
            iv.setLayoutParams(params);
            groupContain.addView(iv);
        }
    }

    private int updatePos(int position) {
        int tPos = -1;
        if (position < imgList.size()) {
            tPos = position;
        } else {
            tPos = position % imgList.size();
        }
        return tPos;
    }

    public synchronized void destroy() {
        handler.removeMessages(0);
    }

    public synchronized void resume() {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, interval);
    }

    public synchronized void pause() {
        handler.removeMessages(0);
    }

    class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
//            if (imgList == null || imgList.size() < 2) {
                return imgList.size();
//            } else {
//                return Integer.MAX_VALUE;
//            }

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // TODO Auto-generated method stub
            int tPos = updatePos(position);
//            ImageView iv = null;
//            try {
//                iv = imgList.get(tPos);
//            } catch (Exception e) {
//                iv = new ImageView(context);
//                iv.setScaleType(ImageView.ScaleType.FIT_XY);
//                iv.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        int tPos = updatePos(position);
//                        StatisticsUtils.bannerClick(tPos, urls.get(tPos).getDescribe());
//                    }
//                });
//                imgList.add(iv);
//            }
            ImageView iv = imgList.get(tPos);
            int pos = (int) iv.getTag(R.id.image_glide_uri);
            String url = urlConfig.HOME_BANNER_DISPLAY + urls.get(pos).getBannerCover();

            Glide.with(context).load(url)
                    .apply(op).into(iv);
            ViewGroup parent = (ViewGroup) iv.getParent();
            //Log.i("ViewPaperAdapter", parent.toString());
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            try{
                int tPos = updatePos(position);
                container.removeView(imgList.get(tPos));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface BannerCallback {}

}
