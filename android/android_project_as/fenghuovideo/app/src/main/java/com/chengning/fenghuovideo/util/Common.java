package com.chengning.fenghuovideo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.chengning.common.base.util.BaseCommon;
import com.chengning.common.util.BuildProperties;
import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.SettingManager;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.UserInfoBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

public class Common extends BaseCommon{
	public static final long TIME_WAIT_COMPLETE = 10;
	public static final long TIME_WAIT_REFRESH = 20;

	public final static boolean hasNet() {
		return IsHaveInternet(App.getInst()) != 0;
	}

	public static void showHttpFailureToast(Context context) {
		UIHelper.showToast(context,
				Common.hasNet() ? context.getString(R.string.server_fail)
						: context.getString(R.string.intnet_fail));
	}

	/**
	 * 转换日期格式
	 */
	public static Date stringToDate(String time) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date currentTime;
		try {
			currentTime = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return currentTime;
	}
	
	public static String getCurDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		return sdf.format(currentTime);
	}

	public static boolean isTargetTimeBefore(long oldTime) {
		return isTargetTimeBefore(oldTime, 30 * 60 * 1000);
	}

	public static boolean isTargetTimeBefore(long oldTime, long interval) {
		long c = System.currentTimeMillis();
		long i = Math.abs(c - oldTime);
		
		return i >= interval;
	}

	/**
	 * 请求http失败处理
	 * 
	 * @param context
	 * @param throwable
	 */
	public static void handleHttpFailure(Activity context, Throwable throwable) {
		if (hasNet()) {
			if (throwable != null
					&& throwable.getClass().isInstance(
							new ConnectTimeoutException())) {
				UIHelper.showToast(context, R.string.intent_timeout);
			} else {
				UIHelper.showToast(context, R.string.server_fail);
			}
		} else {
			UIHelper.showToast(context, R.string.intnet_fail);
		}
	}

	/**
	 * 设置主题
	 *
	 * @param activity
	 */
	public static void setTheme(Activity activity) {
		if (isTrue(SettingManager.getInst().getNightModel())) {
			activity.setTheme(R.style.NightTheme);
		} else {
			activity.setTheme(R.style.DayTheme);
		}
	}

	/**
	 * 显示军衔
	 * @param context
	 * @param bean
	 * @param view
	 */
	public static void handleUserNameDisplay(Context context,
											 BaseArticlesBean bean, TextView view) {
		// 此处不要使用view.setTag()
		int spStart = 0;
		int spEnd = 0;
		SpannableStringBuilder nameBuilder = new SpannableStringBuilder();
		String nickname = bean.getNickname();
		if (!TextUtils.isEmpty(nickname)) {
			nameBuilder.append(nickname);
		}
		String roleName = bean.getRole_name();
		if (!TextUtils.isEmpty(roleName)) {
			spStart = nameBuilder.length();
			nameBuilder.append(" (" + roleName + ") ");
			spEnd = nameBuilder.length();
			ForegroundColorSpan sp;
			sp = Common.isTrue(SettingManager.getInst().getNightModel()) ? new ForegroundColorSpan(
					context.getResources().getColor(R.color.night_text_color))
					: new ForegroundColorSpan(context.getResources().getColor(
							R.color.article_time));
			nameBuilder.setSpan(sp, spStart, spEnd,
					Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		view.setText(nameBuilder);
	}
	/**
	 * 显示军衔
	 * @param context
	 * @param bean
	 * @param view
	 */
	public static void handleUserNameDisplay(Context context,
											 UserInfoBean bean, TextView view, boolean isUserInfo) {
		// 此处不要使用view.setTag()
		int spStart = 0;
		int spEnd = 0;
		SpannableStringBuilder nameBuilder = new SpannableStringBuilder();
		String nickname = bean.getNickname();
		if (!TextUtils.isEmpty(nickname)) {
			nameBuilder.append(nickname);
		}
		String roleName = bean.getRole_name();
		if (!TextUtils.isEmpty(roleName)) {
			spStart = nameBuilder.length();
			nameBuilder.append(" (" + roleName + ") ");
			spEnd = nameBuilder.length();
			ForegroundColorSpan sp = null;
			if (isUserInfo) {
				sp = new ForegroundColorSpan(context.getResources().getColor(
						R.color.white));
			} else {
				sp = Common.isTrue(SettingManager.getInst().getNightModel()) ? new ForegroundColorSpan(
						context.getResources().getColor(R.color.night_text_color))
						: new ForegroundColorSpan(context.getResources().getColor(
								R.color.article_time));
			}

			nameBuilder.setSpan(sp, spStart, spEnd,
					Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		view.setText(nameBuilder);
	}


	public static String trimZero(int str) {
		if (str == 0) {
			return "";
		} else {
			return String.valueOf(str);
		}
	}

	public static String trimZero(String str) {
		if (TextUtils.isEmpty(str) || "0".equals(str)) {
			return "";
		} else {
			return str;
		}
	}

	public static long dateToLong(String date) {
		try {
			if (TextUtils.isEmpty(date)) {
				return 0;
			}
			if (TextUtils.isDigitsOnly(date)) {
				return Long.parseLong(date);
			} else {
				return formatYYMMDDHHMMSStoLong(date);
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 格式化时间（由yyyy-MM-dd HH:mm格式化为yyyy-MM-DD）
	 * @param date
	 * @return
	 */
	public static String getDateYYYYMMDDNotNull(long date){

		long timeLong = date * 1000;
		Date time = new Date(timeLong);
		return new SimpleDateFormat("yyyy-MM-dd").format(time);

	}

	/**
	 * date 转时间戳
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long formatYYMMDDHHMMSStoLong(String date) throws ParseException {
		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(date);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse("1970-01-01 08:00:00");
		// 单位为s
		long l = Math.abs(date1.getTime() - date2.getTime()) / 1000;
		return l;
	}

	public static String formatTimestmpByJs(long timestmp){
		String str = null;
		if(JsRhino.getInst().hasEvaluateJs()){
			// 已经解析过js，调用js函数
			str = JsRhino.getInst().runFunction("getdateformat", new String[]{String.valueOf(timestmp)});
		}else{
			String js = SPHelper.getInst().getString(SPHelper.KEY_DATEFORMAT);
			if(TextUtils.isEmpty(js)){
				// 无js，默认格式
				str = formatTimeHoursMinutesBefore(timestmp);
			}else{
				// 解析js
				JsRhino.getInst().evaluateJs(js, App.getInst().getApplicationContext());
				str = JsRhino.getInst().runFunction("getdateformat", new Object[]{timestmp});
			}
		}
		return str;
	}

	public static void saveImageToGallery(final Context context,
			final String url) {
		if (TextUtils.isEmpty(url)) {
			Toast.makeText(context, "无效的图片", Toast.LENGTH_SHORT).show();
			return;
		}
		ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				Toast.makeText(context, "无效的图片", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				saveBitmapToGallery(context, arg2, url);
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		});
	}

	public static void saveBitmapToGallery(Context context, Bitmap bmp,
			String url) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(context, "未找到sd卡，图片无法存储", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(url) || bmp == null || bmp.getWidth() <= 0
				|| bmp.getHeight() <= 0) {
			Toast.makeText(context, "无效的图片", Toast.LENGTH_SHORT).show();
			return;
		}
		// 图片目录
		StringBuffer appDir = new StringBuffer();
		String name = getApplicationName(context);
		appDir.append(
				Environment.getExternalStorageDirectory().getAbsolutePath())
				.append("/").append(name).append("/")
				.append(name).append("_").append("image");
		String imageName = url.substring(url.lastIndexOf("/"), url.length());
		File file = Common.creatFile(appDir.toString(), imageName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.fromFile(file)));
		Toast.makeText(context, "图片已保存至" + file.getAbsolutePath(),
				Toast.LENGTH_LONG).show();
	}
	
	public static String getApplicationName(Context context) { 
        PackageManager packageManager = null; 
        ApplicationInfo applicationInfo = null; 
        try { 
            packageManager = context.getPackageManager(); 
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0); 
        } catch (PackageManager.NameNotFoundException e) { 
            applicationInfo = null; 
        } 
        String applicationName =  
        (String) packageManager.getApplicationLabel(applicationInfo); 
        return applicationName; 
    } 

	public static Bitmap getViewBitmap_ARGB8888(View view, int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.draw(new Canvas(bitmap));
		return bitmap;
	}

	public static Bitmap bitmapWithImage(Context context, Bitmap bitmap,
			int resId, float scale, int roundPixels) {
		if (bitmap == null) {
			return null;
		}
		int width, height;
		height = bitmap.getHeight();
		width = bitmap.getWidth();

		int tW = (int) (width * scale);
		int tH = (int) (height * scale);

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap scaleBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		Canvas c = new Canvas(scaleBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		// image
		Bitmap imgBitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		c.drawBitmap(imgBitmap, (tW / 2 - imgBitmap.getWidth() / 2),
				(tH / 2 - imgBitmap.getHeight() / 2), paint);

		Bitmap outBitmap;
		if (roundPixels == 0) {
			outBitmap = scaleBitmap;
		} else {
			outBitmap = Bitmap.createBitmap(tW, tH, Bitmap.Config.ARGB_8888);
			Canvas c2 = new Canvas(outBitmap);
			Paint paint2 = new Paint();
			Rect srcRectF = new Rect(0, 0, tW, tH);
			RectF destRectF = new RectF(0, 0, tW, tH);
			paint2.setAntiAlias(true);
			c2.drawARGB(0, 0, 0, 0);
			paint2.setColor(context.getResources().getColor(R.color.black));
			c2.drawRoundRect(destRectF, roundPixels, roundPixels, paint2);
			paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			c2.drawBitmap(scaleBitmap, srcRectF, destRectF, paint2);
		}

		return outBitmap;
	}

	public static void handleTextViewReaded(Context context, View root, int id,
			boolean isReaded) {
		View v = root.findViewById(id);
		if (v != null && v instanceof TextView) {
			handleTextViewReaded(context, (TextView) v, isReaded);
		}
	}

	public static void handleTextViewReaded(Context context, TextView view,
			boolean isReaded) {

		if (isReaded) {
			view.setTextColor(Common.isTrue(SettingManager.getInst()
					.getNightModel()) ? context.getResources().getColor(
					R.color.night_tab_sec_color) : context.getResources()
					.getColor(R.color.item_title_color_readed));
		} else {
			view.setTextColor(Common.isTrue(SettingManager.getInst()
					.getNightModel()) ? context.getResources().getColor(
					R.color.night_text_color) : context.getResources()
					.getColor(R.color.item_title_color_normal));
		}

	}

	/**
	 * 检查目录是否存在，不存在则创建
	 * 
	 * @param path
	 * @return
	 */
	public static File checkFileDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File creatFile(String path, String fileName) {
		File file = new File(checkFileDir(path), fileName);

		return file;
	}

	public static String getContentText(List<String> contents) {
		String content = null;
		if (contents != null && contents.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : contents) {
				sb.append(s);
			}
			content = sb.toString();
		}
		return content;
	}

	public static String getReadableNumber(int num) {
		String str = "";
		if (num >= 10000) {
			str = String.valueOf(num / 10000) + "万";
		} else {
			str = String.valueOf(num);
		}
		return str;
	}


	/**
	 * 获取application中指定的meta-data
	 * 
	 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
	 */
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return null;
		}
		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}

			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return resultData;
	}

	public static String getUmengChannel(Context context) {
		return getAppMetaData(context, "UMENG_CHANNEL");
	}
	
	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize; 
        
        List<List<T>> listArray = new ArrayList<List<T>>();
        int fromIndex = 0;
        int toIndex = 0;
        for (int i = 1; i < page; i++) {
            toIndex = i * pageSize;
            List<T> subList = subList(list,fromIndex,toIndex);
            fromIndex = toIndex;
            listArray.add(subList);
        }
        listArray.add(subList(list,fromIndex,listSize));
        return listArray;
    } 
	
	private static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
	        List<T> subList = new ArrayList<T>();
	        for (int i = fromIndex; i < toIndex; i++) {
	            subList.add(list.get(i));
	        }
	        return subList;
	}
	
	/**
	 * @Methods: getFileSize
	 * @Description: 获得文件大小
	 * @param f
	 * 
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public static long getFileSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
	
	public static int clearCacheFolder(File dir, long numDays) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, numDays);
					}

					if (child.lastModified() < numDays) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}


	
	/**
	 * 桌面显示红点
	 * @param context
	 * @param badgeCount
	 */
	public static void showBadge(Context context, int badgeCount){
		int count = SPHelper.getInst().getInt(SPHelper.BADGE_KEY_NEW_ARTICLE_COUNT);
		count += badgeCount;
        boolean success = ShortcutBadger.applyCount(context, count);
        if (success && badgeCount != 0) {
        	SPHelper.getInst().saveInt(SPHelper.BADGE_KEY_NEW_ARTICLE_COUNT,count);
        }
	}
	
	/**
	 * 桌面取消红点
	 * @param context
	 *
	 */
	public static void cancleBadge(Context context){
		if (context == null || SPHelper.getInst().getInt(SPHelper.BADGE_KEY_NEW_ARTICLE_COUNT) == 0) {
			return;
		}
        ShortcutBadger.removeCount(context);
        SPHelper.getInst().saveInt(SPHelper.BADGE_KEY_NEW_ARTICLE_COUNT, 0);
	}

    public static boolean isFirstRun(){
        //加入版本控制（升级后也要引导下载）
        return SPHelper.getInst().getBoolean(getVersionCode(App.getInst()) + SPHelper.KEY_IS_FIRST_RUN, true);
    }

    public static void setIsFirstRun(boolean isFirstRun){
        SPHelper.getInst().saveBoolean(getVersionCode(App.getInst()) + SPHelper.KEY_IS_FIRST_RUN, isFirstRun);
    }

	private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
	private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

	/***
	 * 判断是不是miui系统
	 * @return
	 */
	public static boolean isMIUI() {
		try {
			final BuildProperties prop = BuildProperties.newInstance();
			return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
					|| prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
					|| prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
		} catch (final IOException e) {
			return false;
		}

	}

	public static int strToInt(String str) {
		if (TextUtils.isDigitsOnly(str)) {
			return  Integer.valueOf(str);
		}
		return 0;
	}

}
