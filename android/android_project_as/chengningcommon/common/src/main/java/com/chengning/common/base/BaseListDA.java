package com.chengning.common.base;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public abstract class BaseListDA<T> {

	private Context mContext;
	private ContentResolver mResolver;
	private Uri mUri;

	public BaseListDA(Context context, Uri uri) {
		mContext = context;
		mResolver = context.getContentResolver();
		mUri = uri;
	}

	public abstract String buildDeleteWhere(T t);

	/**
	 * 代码示例：
	 * 
	 * <pre>
	 * return new String[]{String.valueOf(t.getMy_int())};
	 * </pre>
	 */
	public abstract String[] buildDeleteSelectionArgs(T t);

	/**
	 * 代码示例：
	 * 
	 * <pre>
	 * ContentValues cv = new ContentValues();
	 * cv.put(&quot;my_string&quot;, bean.getMy_string());
	 * cv.put(&quot;my_object&quot;, SerializeUtil.serialize(bean.getMy_object));
	 * return cv;
	 * </pre>
	 */
	public abstract ContentValues buildInsertValues(T t);

	/**
	 * 代码示例：
	 * 
	 * <pre>
	 * Bean bean = new Bean;
	 * bean.setMy_string(cursor.getString(cursor.getColumnIndex(&quot;my_string&quot;)));
	 * bean.setMy_int(cursor.getInt(cursor.getColumnIndex(&quot;my_int&quot;)));
	 * ArrayList&lt;Serializable&gt; sl = SerializeUtil.deSerialize(
	 * 		cursor.getString(cursor.getColumnIndex(&quot;my_object&quot;)), ArrayList.class);
	 * bean.setMy_object(sl);
	 * return bean;
	 * </pre>
	 */
	public abstract T buildQueryValues(Cursor cursor);

	public Context getContext() {
		return mContext;
	}
	
	public ContentResolver getContentResolver(){
		return mResolver;
	}

	public Uri getUri() {
		return mUri;
	}

	public void delete(T t) {
		try {
			mResolver.delete(mUri, buildDeleteWhere(t),
					buildDeleteSelectionArgs(t));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteTarget(String where, String[] selectionArgs) { 
		try {
			mResolver.delete(mUri, where, selectionArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAll() {
		String where = null;
		String[] selectionArgs = null;
		try {
			mResolver.delete(mUri, where, selectionArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int updateTarget(ContentValues values, String where,
			String[] selectionArgs) {
		int temp = 0;
		try {
			temp = mResolver.update(mUri, values,
					where, selectionArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public int insertOne(T t) {
		List<T> list = new ArrayList<T>();
		list.add(t);
		return privateInsertList(list);
	}

	public int insertList(List<T> list) {
		return privateInsertList(list);
	}
	
	private int privateInsertList(List<T> list) {
		if(list == null){
			return 0;
		}
		ContentValues[] values = new ContentValues[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ContentValues cv = buildInsertValues(list.get(i));
			values[i] = cv;
		}
		int temp = 0;
		try {
			temp = mResolver.bulkInsert(mUri, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public List<T> queryAll() {
		return privateQueryTarget(null, null, null);
	}

	public List<T> queryTarget(String selection, String[] selectionArgs,
			String sortOrder) {
		return privateQueryTarget(selection, selectionArgs, sortOrder);
	}
	
	private List<T> privateQueryTarget(String selection, String[] selectionArgs,
			String sortOrder) {
		List<T> list = new ArrayList<T>();
		try {
			Cursor cursor;
			cursor = mResolver.query(mUri, null, selection, selectionArgs,
					sortOrder);
			if (cursor != null){
				if(cursor.moveToFirst()) {
					do {
						T t = buildQueryValues(cursor);
						list.add(t);
					} while (cursor.moveToNext());
					cursor.close();
				} else {
					cursor.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
