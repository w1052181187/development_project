package com.shenyuan.militarynews.data.access;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.shenyuan.militarynews.base.BaseListServer;
import com.shenyuan.militarynews.beans.data.SettingBean;
import com.shenyuan.militarynews.provider.dbContent.table_setting;

public class SettingServer extends BaseListServer<SettingBean> {

	private static SettingServer mInst;

	public static SettingServer getInst(Context con) {
		if (mInst == null) {
            synchronized (SettingServer.class) {
                if(mInst == null){
                    mInst = new SettingServer(con);
                }
            }
        }
		return mInst;
	}

	private SettingServer(Context con) {
		super(con, table_setting.CONTENT_URI);
	}

	@Override
	
	protected String buildDeleteWhere(SettingBean t) {
		return null;
	}

	@Override	
	protected String[] buildDeleteSelectionArgs(SettingBean t) {
		return null;
	}

	@Override
	protected ContentValues buildInsertValues(SettingBean bean) {
		ContentValues values = new ContentValues();
		values.put("is_push",bean.getIs_push());
		values.put("fontsize",bean.getFontsize());
		values.put("lasttime",bean.getLasttime());
		values.put("is_no_pic_model", bean.getIs_no_pic_model());
		values.put("is_night_model", bean.getIs_night_model());
		return values;
	}

	@Override
	protected SettingBean buildQueryValues(Cursor cursor) {
		SettingBean bean = new SettingBean();
		bean.setIs_push(cursor.getInt(cursor.getColumnIndex("is_push"))); 
		bean.setFontsize(cursor.getInt(cursor.getColumnIndex("fontsize"))); 
		bean.setLasttime(cursor.getString(cursor.getColumnIndex("lasttime")));
		bean.setIs_no_pic_model(cursor.getInt(cursor.getColumnIndex("is_no_pic_model"))); 
		bean.setIs_night_model(cursor.getInt(cursor.getColumnIndex("is_night_model")));
		return bean;
	}
	
	public SettingBean getSettingBean(){
		List<SettingBean> list = queryAll();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
