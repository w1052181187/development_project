package com.chengning.fenghuovideo.data.access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.db.provider.dbContent.table_channel_item;

import java.util.ArrayList;
import java.util.List;

public class ChannelItemListDA extends BaseListDA<ChannelItemBean> {

	private static ChannelItemListDA mInst;

	public static ChannelItemListDA getInst(Context con) {
		
        if (mInst == null) {
            synchronized (ChannelItemListDA.class) {
                if (mInst == null) {
                    mInst = new ChannelItemListDA(con);
                }
            }
        }
		return mInst;
	}

	private ChannelItemListDA(Context con) {
		super(con, table_channel_item.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(ChannelItemBean t) {
		return null;
	}

	@Override
	public String[] buildDeleteSelectionArgs(ChannelItemBean t) {
		return null;
	}

	@Override
	public ContentValues buildInsertValues(ChannelItemBean bean) {
		ContentValues values = new ContentValues();
		values.put("tid", bean.getTid());
		values.put("nickname", bean.getNickname());
		values.put("dateline", bean.getDateline());
		values.put("replys", bean.getReplys());
		values.put("title", bean.getTitle());
		values.put("image_list", bean.getImage_list());
		values.put("local_channel", bean.getLocal_channel());
		values.put("local_page", bean.getLocal_page());
		values.put("type", bean.getShow_type());
		values.put("url", bean.getUrl());
		values.put("uid", bean.getUid());
		values.put("face", bean.getFace());
		values.put("content_type", bean.getContent_type());
		values.put("redirecturl", bean.getRedirecturl());
		values.put("list_from", bean.getList_from());
		values.put("video_address", bean.getVideo_address());
		values.put("videoid", bean.getVideoid());
		values.put("is_dig", bean.getIs_dig());
		values.put("link", bean.getLink());
		values.put("digcounts", bean.getDigcounts());
		return values;
	}

	@Override
	public ChannelItemBean buildQueryValues(Cursor cursor) {
		ChannelItemBean bean = new ChannelItemBean();
		bean.setTid(cursor.getString(cursor.getColumnIndex("tid")));
		bean.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
		bean.setDateline(cursor.getString(cursor.getColumnIndex("dateline")));
		bean.setReplys(cursor.getInt(cursor.getColumnIndex("replys")));
		bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
		bean.setImage_list(cursor.getString(cursor.getColumnIndex("image_list")));
		bean.setLocal_channel(cursor.getString(cursor.getColumnIndex("local_channel")));
		bean.setLocal_page(cursor.getInt(cursor.getColumnIndex("local_page"))); 
		bean.setShow_type(cursor.getInt(cursor.getColumnIndex("type")));
		bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
		bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
		bean.setFace(cursor.getString(cursor.getColumnIndex("face")));
		bean.setContent_type(cursor.getString(cursor.getColumnIndex("content_type")));
		bean.setRedirecturl(cursor.getString(cursor.getColumnIndex("redirecturl")));
		bean.setList_from(cursor.getString(cursor.getColumnIndex("list_from")));
		bean.setVideo_address(cursor.getString(cursor.getColumnIndex("video_address")));
		bean.setVideoid(cursor.getString(cursor.getColumnIndex("videoid")));
		bean.setIs_dig(cursor.getInt(cursor.getColumnIndex("is_dig")));
		bean.setLink(cursor.getString(cursor.getColumnIndex("link")));
		bean.setDigcounts(cursor.getInt(cursor.getColumnIndex("digcounts")));
		return bean;
	}
	
	public void setChannelAndPage(List<ChannelItemBean> list, String channel, int page){
		if(list != null){
			for(ChannelItemBean b : list){
				b.setLocal_channel(channel);
				b.setLocal_page(page);
			}
		}
	}
	
	public void clearChannel(String channel){
		deleteTarget("local_channel = ?",
				new String[]{channel});
	}
	
	public void insertChannelItemList(List<ChannelItemBean> list, String channel, int page){
		deleteTarget("local_channel = ? and local_page = ?",
				new String[]{channel, String.valueOf(page)});
		insertList(list);
	}
	
	public List<ChannelItemBean> queryChannelItemList(String channel, int page){
		List<ChannelItemBean> list = queryTarget("local_channel = ? and local_page = ?",
				new String[]{channel, String.valueOf(page)}, null);
		return list;
	}

}
