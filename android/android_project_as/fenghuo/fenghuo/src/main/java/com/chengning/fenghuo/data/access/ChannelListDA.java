package com.chengning.fenghuo.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;

import com.chengning.common.base.BaseListDA;
import com.chengning.fenghuo.data.bean.ChanBean;
import com.chengning.fenghuo.db.provider.dbContent.table_channel_list;
import com.chengning.fenghuo.db.provider.dbProvider;

public class ChannelListDA extends BaseListDA<ChanBean> {

	private static ChannelListDA mInst;

	public static ChannelListDA getInst(Context con) {
		if (mInst == null) {
            synchronized (ChannelListDA.class) {
                if (mInst == null) {
                    mInst = new ChannelListDA(con);
                }
            }
        }
		return mInst;
	}

	private ChannelListDA(Context con) {
		super(con, table_channel_list.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(ChanBean t) {
		return "id = ?";
	}

	@Override
	public String[] buildDeleteSelectionArgs(ChanBean t) {
		return new String[]{t.getId()};
	}

	@Override
	public ContentValues buildInsertValues(ChanBean bean) {
		ContentValues values = new ContentValues();
		values.put("id", bean.getId());
		values.put("name", bean.getName());
		values.put("url", bean.getUrl());
		values.put("pic", bean.getPic());
		values.put("local_type", bean.getLocal_type());
		values.put("local_my_index", bean.getLocal_my_index());
		values.put("local_other_index", bean.getLocal_other_index());
		return values;
	}

	@Override
	public ChanBean buildQueryValues(Cursor cursor) {
		ChanBean bean = new ChanBean();
		bean.setId(cursor.getString(cursor.getColumnIndex("id")));
		bean.setName(cursor.getString(cursor.getColumnIndex("name")));
		bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
		bean.setPic(cursor.getString(cursor.getColumnIndex("pic")));
		bean.setLocal_type(cursor.getString(cursor.getColumnIndex("local_type")));
		bean.setLocal_my_index(cursor.getInt(cursor.getColumnIndex("local_my_index")));
		bean.setLocal_other_index(cursor.getInt(cursor.getColumnIndex("local_other_index")));
		return bean;
	}
	
	public void updateTarget(ChanBean bean){
		ContentValues values = new ContentValues();
		values.put("id", bean.getId());
		values.put("name", bean.getName());
		values.put("url", bean.getUrl());
		values.put("pic", bean.getPic());
		values.put("local_type", bean.getLocal_type());
		values.put("local_my_index", bean.getLocal_my_index());
		values.put("local_other_index", bean.getLocal_other_index());
		updateTarget(values, "id = ?", new String[]{bean.getId()});
	}
	
	public void updateList(List<ChanBean> list){
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		for(ChanBean bean : list){
			ContentValues values = new ContentValues();
			values.put("id", bean.getId());
			values.put("name", bean.getName());
			values.put("url", bean.getUrl());
			values.put("pic", bean.getPic());
			values.put("local_type", bean.getLocal_type());
			values.put("local_my_index", bean.getLocal_my_index());
			values.put("local_other_index", bean.getLocal_other_index());
			ops.add(ContentProviderOperation.newUpdate(getUri()).withSelection("id = ?", new String[]{bean.getId()}).withValues(values).build());
		}
		try {
			getContentResolver().applyBatch(dbProvider.AUTHORITY, ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public List<ChanBean> queryMyChannelList(){
		return queryTarget("local_my_index > ?", new String[]{"0"}, "local_my_index asc");
	}
	
	public List<ChanBean> queryOtherChannelList(){
		return queryTarget(null, null, "local_other_index asc");
	}

}
