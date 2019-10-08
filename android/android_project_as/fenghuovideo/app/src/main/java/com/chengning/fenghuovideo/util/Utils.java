package com.chengning.fenghuovideo.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.data.bean.CommentItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class Utils {

    public final static DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.loading)
            .showImageForEmptyUri(R.mipmap.home_circle_default_avatar)
            .showImageOnFail(R.mipmap.home_circle_default_avatar)
            .resetViewBeforeLoading(false).cacheInMemory(true)
            .cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
//			.displayer(new RoundedBitmapDisplayer(0))
            .build();

    public final static DisplayImageOptions mCircleOptions = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.loading)
            .showImageForEmptyUri(R.mipmap.home_circle_default_avatar)
            .showImageOnFail(R.mipmap.home_circle_default_avatar)
            .resetViewBeforeLoading(false).cacheInMemory(true)
            .cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(360))
            .build();

    /**
     * 设置图片为圆形
     *
     * @param imageUri
     *            （String 类型）
     * @param image
     *            imageUri = "http://site.com/image.png"; // from Web imageUri =
     *            "file:///mnt/sdcard/image.png"; // from SD card imageUri =
     *            "content://media/external/audio/albumart/13"; // from content
     *            provider imageUri = "assets://image.png"; // from assets
     *
     */
    public static void handleDefaultAvatar(String imageUri, ImageView image) {
        handleAvatar(imageUri,image,mOptions);
    }

    public static void handleAvatar(String imageUri, ImageView image, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(imageUri, image, options);
    }

    public static String getGenderString(int gender) {
        String str;
        switch (gender) {
            case Consts.GENDER_MALE:
                str = "男";
                break;
            case Consts.GENDER_FEMALE:
                str = "女";
                break;
            case Consts.GENDER_UNKNOWN:
            default:
                str = "未填写";
                break;
        }
        return str;
    }

    public static String handleDynamicContentConvert(BaseArticlesBean bean) {
        String content = null;
        if (bean instanceof CommentItemBean){
            ArrayList<String> strings = ((CommentItemBean)bean).getContent();
            content = !Common.isListEmpty(strings) ? strings.get(0) : "";
        } else if (bean instanceof ChannelItemBean){
            ArrayList<String> strings = ((ChannelItemBean)bean).getContent();
            content = !Common.isListEmpty(strings) ? strings.get(0) : "";
        }
        if (content == null) {
            content = "";
        }
        return content;
    }

}
