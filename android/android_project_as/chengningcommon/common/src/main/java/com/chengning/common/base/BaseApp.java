package com.chengning.common.base;

import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.request.target.ViewTarget;
import com.chengning.common.R;
import com.chengning.common.base.util.GlideHelper;

public class BaseApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * Glide Exception:"You must not call setTag() on a view Glide is targeting"
         * 异常原因：Glide加载的iamgeView调用了setTag()方法，因为Glide已经默认为ImageView设置了Tag。
         */
        ViewTarget.setTagId(R.id.tag_glide);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        GlideHelper.getInst().onTrimMemory(this,level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GlideHelper.getInst().clearCacheMemory(this);
    }
}
