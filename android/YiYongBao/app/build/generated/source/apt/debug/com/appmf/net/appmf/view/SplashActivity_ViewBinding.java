// Generated code from Butter Knife. Do not modify!
package com.appmf.net.appmf.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.appmf.net.appmf.R;
import com.zhengsr.viewpagerlib.view.BannerViewPager;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding implements Unbinder {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    this.target = target;

    target.rootLayout = Utils.findRequiredView(source, R.id.normal_view, "field 'rootLayout'");
    target.splashImg = Utils.findRequiredViewAsType(source, R.id.splash_img, "field 'splashImg'", ImageView.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.loop_viewpager, "field 'mViewPager'", BannerViewPager.class);
    target.adImgLayout = Utils.findRequiredView(source, R.id.ad_img_layout, "field 'adImgLayout'");
    target.adTimeTv = Utils.findRequiredViewAsType(source, R.id.ad_time, "field 'adTimeTv'", TextView.class);
    target.adVideoView = Utils.findRequiredViewAsType(source, R.id.ad_video, "field 'adVideoView'", VideoView.class);
    target.adVideoLayout = Utils.findRequiredView(source, R.id.ad_video_layout, "field 'adVideoLayout'");
    target.adImgSingle = Utils.findRequiredViewAsType(source, R.id.ad_img_single, "field 'adImgSingle'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rootLayout = null;
    target.splashImg = null;
    target.mViewPager = null;
    target.adImgLayout = null;
    target.adTimeTv = null;
    target.adVideoView = null;
    target.adVideoLayout = null;
    target.adImgSingle = null;
  }
}
