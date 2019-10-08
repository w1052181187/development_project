package com.chengning.yiqikantoutiao.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.SettingManager;
import com.chengning.yiqikantoutiao.data.bean.BaseArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.data.bean.CommentItemBean;
import com.chengning.yiqikantoutiao.data.bean.UserInfoBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.conn.ConnectTimeoutException;

import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class Utils {

	final static int BUFFER_SIZE = 4096;

	public final static DisplayImageOptions mCircleOptions = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.loading)
			.showImageForEmptyUri(R.drawable.home_default_avatar)
			.showImageOnFail(R.drawable.home_default_avatar)
			.resetViewBeforeLoading(false).cacheInMemory(true)
			.cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
//			.displayer(new RoundedBitmapDisplayer(0))
			.build();

	/**
	 * 设置图片为圆形
	 * 
	 * @param imageId
	 *            （int类型）
	 * @param image
	 *            imageId = R.drawable.image // from drawables (only images,
	 *            non-9patch)
	 * 
	 */
	public static void setCircleImage(int imageId, ImageView image) {
		ImageLoader.getInstance().displayImage("drawable://" + imageId, image,
				mCircleOptions);
	}

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
	public static void setCircleImage(String imageUri, ImageView image) {
		ImageLoader.getInstance().displayImage(imageUri, image, mCircleOptions);
	}

	public static void showFace(String imageUri, ImageView image) {
		Utils.setCircleImage(imageUri, image);
	}


	/**
	 * 请求http失败处理
	 * 
	 * @param context
	 * @param throwable
	 */
	public static void handleHttpFailure(Activity context, Throwable throwable) {
		if (Common.hasNet()) {
			if (throwable != null
					&& throwable.getClass().isInstance(
							new ConnectTimeoutException())) {
				Toast.makeText(context, R.string.intent_timeout,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, R.string.server_fail,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, R.string.intnet_fail, Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static void showFace(Activity context, UserInfoBean bean,
                                ImageView imageView, boolean isOriginFirst) {
		if (context == null || bean == null) {
			return;
		}
		
		String s = bean.getFace();
		String o = bean.getFace_original();
		if (!TextUtils.isEmpty(o) && isOriginFirst) {
			Utils.setCircleImage(o, imageView);
		} else {
			showFace(s, imageView);
		}
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			imageView.setColorFilter(
					context.getResources().getColor(R.color.night_img_color),
					PorterDuff.Mode.MULTIPLY);
		}
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
	
	
	
	/**
     * 返回载入中界面
     * @param activity
     * @param parent
     * @param rootId
     */
	public static View addProssBar(Activity activity, View parent, int rootId){
		removeProssBar(parent, rootId);
		View wait = parent.inflate(parent.getContext(), R.layout.wait, null);
		RelativeLayout root = (RelativeLayout) parent.findViewById(rootId);
		root.addView(wait);
		return wait;
	}
	public static void removeProssBar(View parent, int rootId){
		RelativeLayout root = (RelativeLayout) parent.findViewById(rootId);
		View v = parent.findViewById(R.id.waitLayout_root);
		if(v!=null)
			root.removeView(v);
	}
	
	/**
	 * touch 刷新的view
	 * @param activity
	 * @param parent
	 * @param rootId
	 * @return
	 */
	public static View addTouchPhoto(Activity activity, View parent, int rootId){
		removeTouchPhoto(parent, rootId);
		View wait = parent.inflate(activity, R.layout.touch_refresh, null);
		RelativeLayout root = (RelativeLayout) parent.findViewById(rootId);
		root.addView(wait);
		return wait;
	}
	public static void removeTouchPhoto(View parent, int rootId){
		RelativeLayout root = (RelativeLayout) parent.findViewById(rootId);
		View v = parent.findViewById(R.id.touch_refresh_root);
		if(v!=null)
			root.removeView(v);
	}
	
	public static void copyToClipboardManager(Activity activity, String str){
		ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData myClip = ClipData.newPlainText("text", str);
		cm.setPrimaryClip(myClip);
	}

}
