package com.chengning.fenghuovideo.emotion.weibo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.chengning.fenghuovideo.App;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.task.TaskException;
import com.chengning.fenghuovideo.task.WorkTask;
import com.chengning.fenghuovideo.util.EmotionHelper;
import com.chengning.fenghuovideo.util.OrderedProperties;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

public class EmotionsDB {

	private static final String TAG = EmotionsDB.class.getSimpleName();

	private static SQLiteDatabase emotionsDb;
	private static OrderedProperties properties;
	private static int emotionSize;
	private static int mModifyEmotionSize;
	private static DisplayImageOptions options;
	static DisplayImageOptions mModifyOptions = new DisplayImageOptions.Builder()
	.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
	.cacheOnDisc(true)
	.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
	.bitmapConfig(Bitmap.Config.ARGB_8888)
	.considerExifParams(true)  
	.preProcessor(new BitmapProcessor() {
		
		@Override
		public Bitmap process(Bitmap arg0) {
			Bitmap b = zoomBitmap(arg0, mModifyEmotionSize);
			return b;
		}
	})
	.build();
	

	// 创建表情库
	static {
		emotionsDb = App.getInst().openOrCreateDatabase("emotions", Context.MODE_PRIVATE, null);
	}

	public static void checkEmotions() {
		
		emotionSize = App.getInst().getResources().getDimensionPixelSize(R.dimen.emotion_size);
		
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.ARGB_8888)
		.considerExifParams(true)  
		.preProcessor(new BitmapProcessor() {
			
			@Override
			public Bitmap process(Bitmap arg0) {
				Bitmap b = zoomBitmap(arg0, emotionSize);
				return b;
			}
		})
		.build();
		
		// 读取表情映射
		try {
			InputStream in = App.getInst().getAssets().open("emotions/emotions.properties");
			properties = new OrderedProperties();
			properties.load(new InputStreamReader(in, "utf-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Cursor cursor = null;

		// 检查表是否存在
		boolean tableExist = false;
		try {
			String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='" + EmotionTable.table + "' ";

			cursor = emotionsDb.rawQuery(sql, null);
			if (null != cursor && cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (0 < count)
					tableExist = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor)
				cursor.close();
			cursor = null;
		}

		// 表情表不存在，创建表情表
		if (!tableExist) {
			Log.v(TAG, "create emotions table");

			String sql = String.format("create table %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT , %s TEXT, %s BOLB)", EmotionTable.table,
					EmotionTable.id, EmotionTable.key, EmotionTable.file, EmotionTable.value);
			emotionsDb.execSQL(sql);
		} else {
			Log.v(TAG, "emotions table exist");
		}

		boolean insertEmotions = true;
		// 表情不存在或者不全，插入表情
		try {
			cursor = emotionsDb.rawQuery(" select count(*) as c from " + EmotionTable.table, null);
			if (null != cursor && cursor.moveToFirst()) {
				int count = cursor.getInt(0);
				if (106 == count)
					insertEmotions = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 向数据库插入表情
		if (insertEmotions) {
			Log.v(TAG, "insert emotions");
			new WorkTask<Void, Void, Void>() {

				@Override
				public Void workInBackground(Void... params) throws TaskException {
					try {
						Set<Object> keySet = properties.keySet();

						// 开启事务
						emotionsDb.beginTransaction();
						emotionsDb.execSQL(String.format("delete from %s", EmotionTable.table));
						for (Object key : keySet) {
							String value = properties.getProperty(key.toString());
							value = "emotions/" + value;
							Log.w(TAG, (String.format("emotion's key(%s), value(%s)", key, value)));

							ContentValues values = new ContentValues();
							values.put(EmotionTable.key, key.toString());
							byte[] emotion = readStreamToBytes(App.getInst().getAssets().open(value));
							values.put(EmotionTable.value, emotion);
							values.put(EmotionTable.file, value);

							emotionsDb.insert(EmotionTable.table, EmotionTable.id, values);
						}
						// 结束事务
						emotionsDb.setTransactionSuccessful();
						emotionsDb.endTransaction();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			}.execute();
		} else {
			Log.v(TAG, "emotions exist");
		}
	}

	public static int getEmotionSize(){
		return emotionSize;
	}
	
	public static DisplayImageOptions getOptions(){
		return options;
	}
	
	public synchronized static Bitmap getEmotionBitmap(String key){
		Bitmap bitmap = null;
		String value = null;
		if(properties != null && properties.containsKey(key)){
			value = properties.getProperty(key);
			String assetsEmotionUri = "assets://emotions/" + value; 
			ImageSize imageSize = new ImageSize(emotionSize, emotionSize, 0);
			bitmap = ImageLoader.getInstance().loadImageSync(assetsEmotionUri, imageSize, options);
		}
		return bitmap;
	}
	public synchronized static Bitmap getEmotionBitmap(String key, final int emotionSize){
		Bitmap bitmap = null;
		String value = null;
		if(properties != null && properties.containsKey(key)){
			value = properties.getProperty(key);
			String assetsEmotionUri = "assets://emotions/" + value; 
			mModifyEmotionSize = emotionSize;
			
			if (mModifyEmotionSize == 0) {
				mModifyEmotionSize = Consts.EMOTION_SIZE_MIDDLE;
			}
			ImageSize imageSize = new ImageSize(emotionSize, emotionSize, 0);
			bitmap = ImageLoader.getInstance().loadImageSync(assetsEmotionUri, imageSize, mModifyOptions);
		}
		return bitmap;
	}
	
	public synchronized static String getEmotionUri(String key){
		String assetsEmotionUri = "";
		String value = null;
		if(properties != null && properties.containsKey(key)){
			value = properties.getProperty(key);
			assetsEmotionUri = "assets://emotions/" + value; 
			
		}
		return assetsEmotionUri;
	}

	public static byte[] getEmotion(String key) {
//		if (converter == null)
//			// 繁体转换简体
//			converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
//		key = converter.convert(key);

		Cursor cursor = emotionsDb.rawQuery(" SELECT " + EmotionTable.value + " FROM " + EmotionTable.table + " WHERE " + EmotionTable.key + " = ? ",
				new String[] { key });
		try {
			if (cursor.moveToFirst()) {
				byte[] data = cursor.getBlob(cursor.getColumnIndex(EmotionTable.value));
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return null;
	}

	public static Emotions getEmotions() {
		Emotions emotions = new Emotions();
		emotions.setEmotions(new ArrayList<Emotion>());

 
		Cursor cursor = emotionsDb.rawQuery(" SELECT * FROM " + EmotionTable.table, null);
		try {
			if (cursor.moveToFirst()) {
				do {
					byte[] data = cursor.getBlob(cursor.getColumnIndex(EmotionTable.value));
					String key = cursor.getString(cursor.getColumnIndex(EmotionTable.key));

					Emotion emotion = new Emotion();
					emotion.setData(data);
					emotion.setKey(key);

					emotions.getEmotions().add(emotion);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

		return emotions;
	}
	
	public static byte[] readStreamToBytes(InputStream in) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 8];
		int length = -1;
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}
		out.flush();
		byte[] result = out.toByteArray();
		in.close();
		out.close();
		return result;
	}

	public static Bitmap zoomBitmap(Bitmap source, int width) {
		Matrix matrix = new Matrix();
		float scale = width * 1.0f / source.getWidth();
		matrix.setScale(scale, scale);
		Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
		try {
			Log.d(EmotionHelper.class.getSimpleName(), String.format("zoom bitmap, source(%d,%d) result(%d,%d)", source.getWidth(), source.getHeight(), result.getWidth(),
							result.getHeight()));
		} catch (Exception e) {
		}
//		source.recycle();
		return result;
	}

	public static Bitmap zoomBitmapHeight(Bitmap source, int height) {
		Matrix matrix = new Matrix();
		float scale = height * 1.0f / source.getHeight();
		matrix.setScale(scale, scale);
		Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
		try {
			Log.d(EmotionHelper.class.getSimpleName(), String.format("zoom bitmap, source(%d,%d) result(%d,%d)", source.getWidth(), source.getHeight(), result.getWidth(),
							result.getHeight()));
		} catch (Exception e) {
		}
//		source.recycle();
		return result;
	}

	static class EmotionTable {

		static final String table = "org_aisen_weibo_sina_emotions";

		static final String id = "org_aisen_weibo_sina_id";

		static final String key = "org_aisen_weibo_sina_key";

		static final String file = "org_aisen_weibo_sina_file";

		static final String value = "org_aisen_weibo_sina_value";

	}

}
