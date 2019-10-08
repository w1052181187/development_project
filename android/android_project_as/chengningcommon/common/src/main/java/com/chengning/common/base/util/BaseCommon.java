package com.chengning.common.base.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static okhttp3.internal.Util.canonicalizeHost;
import static okhttp3.internal.Util.verifyAsIpAddress;

public class BaseCommon {

	public static final int TRUE = 1;
	public static final int FALSE = 0;
	
	public static final long MINUTE= 60 * 1000;
	public static final long HOUR= 60 * MINUTE;
	public static final long DAY = 24 * HOUR;

	public static boolean isTrue(int i) {
		return i == TRUE;
	}

	public static boolean isListEmpty(List list) {
		return list == null || list.isEmpty();
	}

	/**
	 * 判断是否快速网络
	 */
	public static boolean IsFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false; // ~ 14-64 kbps
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return false; // ~ 50-100 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true; // ~ 400-1000 kbps
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true; // ~ 600-1400 kbps
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return false; // ~ 100 kbps
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return true; // ~ 2-14 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return true; // ~ 700-1700 kbps
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return true; // ~ 1-23 Mbps
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return true; // ~ 400-7000 kbps
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return true; // ~ 1-2 Mbps
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return true; // ~ 5 Mbps
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return true; // ~ 10-20 Mbps
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return false; // ~25 kbps
		case TelephonyManager.NETWORK_TYPE_LTE:
			return true; // ~ 10+ Mbps
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}
	}

	/**
	 * 判断是否联网 0 没联网 1 联网 2 mobile 3 wifi 4 2g 5 3g 6 4g
	 *
	 *
	 * @return
	 */
	public interface NetType {
		int NET_UNCONECTION = 0;
		int NET_CONECTION = 1;
		int NET_MOBILE = 2;
		int NET_WIFI = 3;
		int NET_2G = 4;
		int NET_3G = 5;
	}

	public static int IsHaveInternet(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null) {
			return 0;
		}

		NetworkInfo info = connManager.getActiveNetworkInfo();
		int mState = NetType.NET_UNCONECTION;
		if (info != null && info.isConnected()) {
			mState = NetType.NET_CONECTION;
		} else {
			mState = NetType.NET_UNCONECTION;
			return mState;
		}

		boolean mIsWifi = false;
		boolean mIsMobile = false;
		if (connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null) {
			mIsWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.isConnected();
		}
		if (connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
			mIsMobile = connManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).isConnected();
		}
		if (mIsWifi) {
			mState = NetType.NET_WIFI;
		} else if (mIsMobile) {
			mState = NetType.NET_MOBILE;
			if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
					|| info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE
					|| info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA) {
				mState = NetType.NET_2G;
			} else if (IsFastMobileNetwork(context) == true) {
				mState = NetType.NET_3G;
			}
		}
		return mState;
	}
	

    public static String getDateMMDDNotNull(long time){
        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        return getDateMMDDNotNull(str);
    }
    
    /**
     * 格式化时间 （由yyyy-MM-dd HH:mm格式化为MM-DD）
     * （同一年则不显示年，否则显示年）
     * @param dateStr
     * @return
     */
    public static String getDateMMDDNotNull(String dateStr){
        if(TextUtils.isEmpty(dateStr)){
            return dateStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date now = new Date();
            Date date = sdf.parse(dateStr);
            String curYear = new SimpleDateFormat("yyyy").format(now);
            String year = new SimpleDateFormat("yyyy").format(date);
            if (curYear.equals(year)) {
                return new SimpleDateFormat("MM-dd").format(date);
            } else {
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }
    public static String getDateMMDDHHMMNotNull(String dateStr){
        if(TextUtils.isEmpty(dateStr)){
            return dateStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date now = new Date();
            Date date = sdf.parse(dateStr);
            String curYear = new SimpleDateFormat("yyyy").format(now);
            String year = new SimpleDateFormat("yyyy").format(date);
            String curDay = new SimpleDateFormat("yyyy MM-dd").format(now);
            String day = new SimpleDateFormat("yyyy MM-dd").format(date);
            if (curYear.equals(year)) {
                 if(curDay.equals(day)) {
                    return new SimpleDateFormat("HH:mm").format(date);
                }else{
                    return new SimpleDateFormat("MM-dd").format(date);
                }
            } else {
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }
    
    public static String dateCompareNow(long time){
        String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        return dateCompareNow(str);
    }
    
      public static String dateCompareNow(String str) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime=new Date();
            Date beginTime = null;
            if(str==null||str.equals(""))
            {
                return "";
            }

            try {
                beginTime = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                return str;
            }
            
            Calendar c1 = Calendar.getInstance();
            c1.setTime(currentTime);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(beginTime);
            int h1 = c1.get(Calendar.HOUR_OF_DAY);
            int h2 = c2.get(Calendar.HOUR_OF_DAY);
            int m1 = c1.get(Calendar.MINUTE);
            int m2 = c2.get(Calendar.MINUTE);
            int s1 = c1.get(Calendar.SECOND);
            int s2 = c2.get(Calendar.SECOND);

            if(c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
            {
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String temp = sdf2.format(beginTime);
                return temp;
            }
            else if(c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR))
            {
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
                String temp = sdf2.format(beginTime);
                return temp;
            }
            else if(h1 != h2)
            {
                if(h1 > h2){
                    return String.valueOf(h1 - h2)+"小时前";
                }else{
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    String temp = sdf2.format(beginTime);
                    return temp;
                }
            }
            else if(m1 != m2)
            {
                if(m1 > m2){
                    return String.valueOf(m1 - m2)+"分钟前";
                }else{
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                    String temp = sdf2.format(beginTime);
                    return temp;
                }
            }
            else if(s1 != s2)
            {
                return "刚刚";
            }
            else 
            {
                return "刚刚";
            }
        }
        


    /**
     * 格式化时间（由yyyy-MM-DD格式化为MM-DD）
     * @param pubDate
     * @return
     */
    public static String getDateMMDDNotNull2(String pubDate) {
        if(TextUtils.isEmpty(pubDate)){
            return pubDate;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date now = new Date();
            Date date = sdf.parse(pubDate);
            String curYear = new SimpleDateFormat("yyyy").format(now);
            String year = new SimpleDateFormat("yyyy").format(date);
            if (curYear.equals(year)) {
                return new SimpleDateFormat("MM-dd").format(date);
            } else {
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
            return pubDate;
        }
    }
    /**
     * 格式化时间（由yyyy-MM-dd HH:mm格式化为yyyy-MM-DD）
     * @param dateStr
     * @return
     */
    public static String getDateYYYYMMDDNotNull(String dateStr){
        if(TextUtils.isEmpty(dateStr)){
            return dateStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(dateStr);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        
    }
    
    /**
     * 获取当前应用版本（versionName）
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 获取当前应用版本号（versionCode）
     * @param context
     * @return
     */
    public static String getVersionCode(Context context){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = String.valueOf(info.versionCode);
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
//    /**
//     * imageloader中是否已缓存该图片
//     * @param url
//     * @return
//     */
//    public static boolean hasImageCache(String url){
//        boolean hasCache = false;
//        if(TextUtils.isEmpty(url)){
//            return false;
//        }
//        if(!hasCache){
//            hasCache = ImageLoader.getInstance().getMemoryCache().get(url) != null;
//        }
//        if(!hasCache){
//            File file = ImageLoader.getInstance().getDiscCache().get(url);
//            hasCache = file != null && file.isFile();
//        }
//        return hasCache;
//    }
    
    /**
     * 字符串转化为整型
     * @param str
     * @return
     */
    public static int handleStrToInteger (String str) {
        if(!TextUtils.isEmpty(str)){
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException  e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    /**
	 * 计算数据库偏移量
	 * 
	 * @param list
	 * @return
	 */
	public static int calculateOffset(List list) {
		int offset = 0;
		if (!isListEmpty(list)) {
			offset = list.size();
		}
		return offset;
	}
	
	/**
	 * 将时间戳格式化为"距现在多久之前"的字符串
	 * 规则：24小时以内显示（几小时前、几分钟前、小于一分钟显示刚刚），大于24小时（同一年显示月日、不同年显示年）
	 * @param timeInt 时间戳
	 * @return
	 */
	public static String formatTimeHoursMinutesBefore(long timeInt) {
		
		if (timeInt == 0) {
			return "";
		}
		
		long timeLong = timeInt * 1000;
		Date currentTime = new Date();
		Date beginTime = new Date(timeLong);
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(currentTime);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(beginTime);
		
		StringBuffer sb = new StringBuffer();
		
//		long time = System.currentTimeMillis() - (timeLong);
		long time = currentTime .getTime() - beginTime .getTime();
//		long mill = (long) Math.floor(time / 1000);// 秒前

		long day = (long) Math.floor(time / DAY );// 天前

		if (day >= 1) {
			if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				sb.append(sdf2.format(beginTime));
			} else if (c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR)) {
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
				sb.append(sdf2.format(beginTime));
			}
		} else {
			long hour = (long) Math.floor(time / HOUR);// 小时
			if (hour >= 1) {
				sb.append(hour).append("小时前");
			} else {
				long minute = (long) Math.floor(time / MINUTE);// 分钟前
				if (minute >= 1) {
					sb.append(minute).append("分钟前");
				} else {
					sb.append("刚刚");
				}
			}
		}
//		else if (mill - 1 > 0) {
//			if (mill == 60) {
//				sb.append("1分钟");
//			} else {
//				sb.append(mill + "秒");
//			}
//		}
	
		return sb.toString();
	}
	
	public static String getUAAndroid(Context context, String prefix){
		StringBuffer sb = new StringBuffer();
		sb.append(prefix).append("/").append(getVersionName(context)).append("(android)");
		String ua = sb.toString();
		return ua;
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
			Bundle bundle = getAppMetaDataBundle(ctx);
			if(bundle.containsKey(key)){
				Object obj = bundle.get(key);
				if(obj != null){
					resultData = obj.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}

	/**
	 * 获取application中指定的meta-data
	 * 
	 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
	 */
	public static int getAppMetaDataInt(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return 0;
		}
		int resultData = 0;
		try {
			Bundle bundle = getAppMetaDataBundle(ctx);
			if(bundle.containsKey(key)){
				resultData = bundle.getInt(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}
	
	public static Bundle getAppMetaDataBundle(Context ctx) {
		Bundle bundle = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(ctx.getPackageName(),
								PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						bundle = applicationInfo.metaData;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(bundle == null){
			bundle = new Bundle();
		}
		return bundle;
	}

	public static String getUmengChannel(Context context) {
		return getAppMetaData(context, "UMENG_CHANNEL");
	}
	
	/**
	 * 获取随机数
	 * @return
	 */
	public static int getRandomNum() {
		Random random = new Random(System.currentTimeMillis());
		return random.nextInt();
	}
	
	/**
	 * 获取当前时间
	 */
	public static long getCurTimeMillis() {
    	return System.currentTimeMillis();
    } 
	
	/**
     * 根据URL获取domain
     * @param url
     * @return
     */
    public static String getDomainForUrl(String url){
        String domainUrl = null;
        if (url == null) {
            return null;
        } else {
            Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv|us|tw|hk|biz|eu)",Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            while(matcher.find()){
                domainUrl = matcher.group();
            }
            return domainUrl;
        }
    }
    
    /**
	 * 通过正则获取根域名程序.
	 *
	 **/
	public static String getRootDomain(String url) {
	      String domain = "";
	      Pattern pattern = Pattern.compile("[\\w-]+\\.(com.cn|net.cn|gov.cn|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*");
	      Matcher matcher = pattern.matcher(url);
	      if (matcher.find()) {
	    	  domain = matcher.group();
	      }
	      return domain;
	}

	public static boolean domainMatch(String urlHost, String domain) {
		if (urlHost.equals(domain)) {
			return true; // As in 'example.com' matching 'example.com'.
		}

		if (urlHost.endsWith(domain)
				&& urlHost.charAt(urlHost.length() - domain.length() - 1) == '.'
				&& !verifyAsIpAddress(urlHost)) {
			return true; // As in 'example.com' matching 'www.example.com'.
		}

		return false;
	}

	public static Bitmap bitmapWithImage(Context context, Bitmap bitmap, int resId, float scale, int roundPixels) {
		if(bitmap == null){
			return null;
		}
		try {
			int width, height;
			height = bitmap.getHeight();
			width = bitmap.getWidth();

			int tW = (int) (width * scale);
			int tH = (int) (height * scale);

			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			Bitmap scaleBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			Canvas c = new Canvas(scaleBitmap);
			Paint paint = new Paint();
			paint.setAntiAlias(true);

			// image
			Bitmap imgBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
			c.drawBitmap(imgBitmap, (tW / 2 - imgBitmap.getWidth() / 2), (tH / 2 - imgBitmap.getHeight() / 2), paint);

			Bitmap outBitmap;
			if(roundPixels == 0){
				outBitmap = scaleBitmap;
			}else{
				outBitmap = Bitmap.createBitmap(tW, tH, Bitmap.Config.ARGB_8888);
				Canvas c2 = new Canvas(outBitmap);
				Paint paint2 = new Paint();
				Rect srcRectF = new Rect(0, 0, tW, tH);
				RectF destRectF = new RectF(0, 0, tW, tH);
				paint2.setAntiAlias(true);
				c2.drawARGB(0, 0, 0, 0);
				paint2.setColor(-16777216);
				c2.drawRoundRect(destRectF, roundPixels, roundPixels, paint2);
				paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
				c2.drawBitmap(scaleBitmap, srcRectF, destRectF, paint2);
			}

			return outBitmap;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
