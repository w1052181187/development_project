package com.shenyuan.militarynews.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

public class SyDeviceUtils
{
  public static DisplayMetrics getDisplayMetrics(Activity paramActivity)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics;
  }
  
  /**
   * 获得 android raw 大小
   */
  public static String getTotalMemory() {  
      String str1 = "/proc/meminfo";  
      String str2="";  
      try {  
          FileReader fr = new FileReader(str1);  
          BufferedReader localBufferedReader = new BufferedReader(fr, 8192);  
          while ((str2 = localBufferedReader.readLine()) != null) {  
              Log.i("android rawmemory", "---" + str2);  
              
          }  
          return  str2;
      } catch (IOException e) {  
    	  e.printStackTrace();
    	  return  str2;
      }  
      
  } 

  public static int getApiLevel()
  {
    return Build.VERSION.SDK_INT;
  }

  public static boolean isCompatible(int paramInt)
  {
    return Build.VERSION.SDK_INT >= paramInt;
  }

  public static String getCpuAbi()
  {
    if (isCompatible(4))
    {
      try
      {
        return Build.class.getField("CPU_ABI")
          .get(null).toString();
      }
      catch (Exception localException)
      {
//        SyLog.getInstance().w(localException, 
//          "Announce to be android 1.6 but no CPU ABI field", new Object[0]);
      }
    }

    return "armeabi";
  }

  public static final int getNumCores()
  {
    try
    {
      return new File("/sys/devices/system/cpu/")
        .listFiles(new FileFilter()
      {
        public boolean accept(File paramFile)
        {
          return Pattern.matches("cpu[0-9]", paramFile.getName());
        }

      }).length;
    }
    catch (Exception localException)
    {
    }

    return Runtime.getRuntime().availableProcessors();
  }

  public static boolean isInstalledOnSdCard(Context paramContext)
  {
	  PackageManager packageManager = null;
    if (isCompatible(8))
    {
    	packageManager = paramContext.getPackageManager();
    }

    try
    {
      return (((PackageManager)packageManager).getPackageInfo(paramContext.getPackageName(), 0).applicationInfo.flags & 
        0x40000) == 
        262144;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      try
      {
    	  String temp = "";
        if ((
        		temp = paramContext.getFilesDir().getAbsolutePath())
          .startsWith("/data/"))
        {
          return false;
        }

        if (((String)temp).contains(
          Environment.getExternalStorageDirectory().getPath()))
        {
          return true;
        }
      }
      catch (Throwable localThrowable)
      {
      }
    }

    return false;
  }
}