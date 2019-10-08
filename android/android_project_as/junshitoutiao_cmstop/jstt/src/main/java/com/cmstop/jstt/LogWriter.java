package com.cmstop.jstt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class LogWriter {
	
	public static final String LOG_FILE_NAME = "log.txt";
	
	public static void e(String tag, String msg){
		Log.e(tag, msg);
		writeLog(tag, msg);
	}
	
	public static void d(String tag, String msg){
		Log.d(tag, msg);
		writeLog(tag, msg);
	}
	
	public static String getBundleStr(Bundle bundle){
		String str = null;
		if(bundle != null){
			StringBuilder sb = new StringBuilder();
			Set<String> keys = bundle.keySet();
			for(String k : keys){
				Object temp = bundle.get(k);
				sb.append(k).append(": ")
				.append(temp != null ? temp.toString() : "")
				.append(", ");
			}
			str = sb.toString();
		}
		return str;
	}
	
	private static void writeLog(String tag, String str){
		write(getLogFile(), "" + System.currentTimeMillis() + ": " + tag + ": " + str);
	}
	
	private static File getLogFile(){
		File f = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/xinjunshi", LOG_FILE_NAME);
		if(!f.exists()){
			try {
				File p = f.getParentFile();
				if(!p.exists()){
					p.mkdirs();
				}
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	private static void write(File file, String str){
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(str);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
