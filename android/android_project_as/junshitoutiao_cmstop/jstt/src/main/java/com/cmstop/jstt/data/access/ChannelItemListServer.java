package com.cmstop.jstt.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.util.SerializeUtil;
import com.cmstop.jstt.base.BaseListServer;
import com.cmstop.jstt.beans.data.MChannelItemBean;
import com.cmstop.jstt.provider.dbContent.table_channel_item;
import com.cmstop.jstt.utils.Common;

public class ChannelItemListServer extends BaseListServer<MChannelItemBean> {

	private static ChannelItemListServer mInst;

	public static ChannelItemListServer getInst(Context con) {
		if (mInst == null) {
            synchronized (ChannelItemListServer.class) {
                if(mInst == null){
                    mInst = new ChannelItemListServer(con);
                }
            }
        }
		return mInst;
	}

	private ChannelItemListServer(Context con) {
		super(con, table_channel_item.CONTENT_URI);
	}

	@Override
	protected String buildDeleteWhere(MChannelItemBean t) {
		return null;
	}

	@Override
	protected String[] buildDeleteSelectionArgs(MChannelItemBean t) {
		return null;
	}

	@Override
	protected ContentValues buildInsertValues(MChannelItemBean bean) {
		ContentValues values = new ContentValues();
		values.put("title", bean.getTitle());
		values.put("aid", bean.getAid());
		values.put("description", bean.getDescription());
		values.put("image", bean.getImage());
		values.put("pubDate", bean.getPubDate());
		values.put("category", bean.getCategory());
		values.put("author", bean.getAuthor());
		values.put("image_arr", SerializeUtil.serialize(bean.getImage_arr()));
		values.put("local_channel", bean.getLocal_channel());
		values.put("local_page", bean.getLocal_page());
		values.put("channel", bean.getChannel());
		values.put("digg", bean.getDigg());
		values.put("pl", bean.getPl());
		values.put("nav", bean.getNav());
		values.put("news_show_type", bean.getNews_show_type());
		values.put("more", bean.getMore());
		values.put("red_tag", bean.getRed_tag());
		values.put("ad_place_id", bean.getAd_place_id());
		values.put("pubTimestamp", bean.getPubTimestamp());
		values.put("tips", bean.getTips());
		values.put("redirecturl", bean.getRedirecturl());
		values.put("tz_nav", bean.getTz_nav());
		values.put("ad_type", bean.getAd_type());
		return values;
	}

	@Override
	protected MChannelItemBean buildQueryValues(Cursor cursor) {
		MChannelItemBean bean = new MChannelItemBean();
		bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
		bean.setAid(cursor.getString(cursor.getColumnIndex("aid")));
		bean.setDescription(cursor.getString(cursor.getColumnIndex("description")));
		bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
		bean.setPubDate(cursor.getString(cursor.getColumnIndex("pubDate")));
		bean.setCategory(cursor.getString(cursor.getColumnIndex("category")));
		bean.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
		ArrayList<String> image_arr = SerializeUtil.deSerialize(cursor.getString(cursor.getColumnIndex("image_arr")), ArrayList.class);
		bean.setImage_arr(image_arr);
		bean.setLocal_channel(cursor.getString(cursor.getColumnIndex("local_channel")));
		bean.setLocal_page(cursor.getInt(cursor.getColumnIndex("local_page")));
		bean.setChannel(cursor.getString(cursor.getColumnIndex("channel")));
		bean.setDigg(cursor.getString(cursor.getColumnIndex("digg")));
		bean.setPl(cursor.getString(cursor.getColumnIndex("pl")));
		bean.setNav(cursor.getString(cursor.getColumnIndex("nav")));
		bean.setNews_show_type(cursor.getInt(cursor.getColumnIndex("news_show_type")));
		bean.setMore(cursor.getString(cursor.getColumnIndex("more")));
		bean.setRed_tag(cursor.getString(cursor.getColumnIndex("red_tag")));
		bean.setAd_place_id(cursor.getString(cursor.getColumnIndex("ad_place_id")));
		bean.setPubTimestamp(cursor.getLong(cursor.getColumnIndex("pubTimestamp")));
		bean.setTips(cursor.getString(cursor.getColumnIndex("tips")));
		bean.setRedirecturl(cursor.getString(cursor.getColumnIndex("redirecturl")));
		bean.setTz_nav(cursor.getString(cursor.getColumnIndex("tz_nav")));
		bean.setAd_type(cursor.getInt(cursor.getColumnIndex("ad_type")));
		return bean;
	}
	
	public void setChannelAndPage(List<MChannelItemBean> list, String channel, int page){
		if(!Common.isListEmpty(list)){
			for(MChannelItemBean b : list){
				b.setLocal_channel(channel);
				b.setLocal_page(page);
			}
		}
	}
	
	public void clearChannel(String channel){
		deleteTarget("local_channel = ?",
				new String[]{channel});
	}
	
	public void insertChannelItemList(List<MChannelItemBean> list, String channel, int page){
		deleteTarget("local_channel = ? and local_page = ?",
				new String[]{channel, String.valueOf(page)});
		insertList(list);
	}
	
	public List<MChannelItemBean> queryChannelItemList(String channel, int page){
		List<MChannelItemBean> list = queryTarget("local_channel = ? and local_page = ?",
				new String[]{channel, String.valueOf(page)}, null);
		return list;
	}

}
