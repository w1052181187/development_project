package com.chengning.common.update;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import com.chengning.common.base.util.BaseCommon;
import com.chengning.common.base.util.BaseCommon.NetType;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class UpdateUtil {

	public static String getVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			int version = info.versionCode;
			return String.valueOf(version);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getApplicationName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			ApplicationInfo info = manager.getApplicationInfo(
					context.getPackageName(), 0);
			return manager.getApplicationLabel(info).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getApplicationMD5(Context context) {
		String path = context.getPackageResourcePath();
		return getFileMD5(new File(path));
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
	
	public static boolean isWifi(Context context){
		return BaseCommon.IsHaveInternet(context) == NetType.NET_WIFI;
	}
}
