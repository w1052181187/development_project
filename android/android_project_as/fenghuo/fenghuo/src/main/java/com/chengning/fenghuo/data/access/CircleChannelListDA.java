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
import com.chengning.fenghuo.data.bean.CircleBean;
import com.chengning.fenghuo.data.bean.CircleChanBean;
import com.chengning.fenghuo.db.provider.dbContent.table_circle_channel_list;
import com.chengning.fenghuo.db.provider.dbProvider;

public class CircleChannelListDA extends BaseListDA<CircleChanBean> {

	private static CircleChannelListDA mInst;

	public static CircleChannelListDA getInst(Context con) {
		if (mInst == null) {
            synchronized (CircleChannelListDA.class) {
                if (mInst == null) {
                    mInst = new CircleChannelListDA(con);
                }
            }
        }
		return mInst;
	}

	private CircleChannelListDA(Context con) {
		super(con, table_circle_channel_list.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(CircleChanBean t) {
		return "id = ?";
	}

	@Override
	public String[] buildDeleteSelectionArgs(CircleChanBean t) {
		return new String[]{t.getId()};
	}

	@Override
	public ContentValues buildInsertValues(CircleChanBean bean) {
		ContentValues values = new ContentValues();
		values.put("id", bean.getId());
		values.put("name", bean.getName());
		values.put("url", bean.getUrl());
		return values;
	}

	@Override
	public CircleChanBean buildQueryValues(Cursor cursor) {
		CircleChanBean bean = new CircleChanBean();
		bean.setId(cursor.getString(cursor.getColumnIndex("id")));
		bean.setName(cursor.getString(cursor.getColumnIndex("name")));
		bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
		return bean;
	}
	
}
