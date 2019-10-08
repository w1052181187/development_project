package com.shenyuan.militarynews.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.chengning.common.util.SerializeUtil;
import com.shenyuan.militarynews.base.BaseListServer;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.ArticlesPicBean;
import com.shenyuan.militarynews.beans.data.LocalStateBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.provider.dbContent.table_local_state;

public class LocalStateServer extends BaseListServer<LocalStateBean> {
	
	public static final String PREFIX_CHANNEL_ITEM = "channel_item_";
	public static final String PREFIX_CHANNEL_ITEM_PIC = "channel_item_pic_";
	
	public static final String READ_STATE_READ = "1";
	
	public static final int STATE_TRUE = 1;
	public static final int STATE_FALSE = 0;

	private static LocalStateServer mInst;

	public static LocalStateServer getInst(Context con) {
		if (mInst == null) {
            synchronized (LocalStateServer.class) {
                if(mInst == null){
                    mInst = new LocalStateServer(con);
                }
            }
        }
		return mInst;
	}

	protected LocalStateServer(Context context) {
		super(context, table_local_state.CONTENT_URI);
	}

	@Override
	protected String buildDeleteWhere(LocalStateBean t) {
		return "id = ?";
	}

	@Override
	protected String[] buildDeleteSelectionArgs(LocalStateBean t) {
		return new String[] { String.valueOf(t.getId())};
	}

	@Override
	protected ContentValues buildInsertValues(LocalStateBean t) {
		ContentValues cv = new ContentValues();
		cv.put("id", t.getId());
		cv.put("lasttime", t.getLasttime());
		cv.put("read_state", t.getRead_state());
		cv.put("favorite_state", t.getFavorite_state());
		cv.put("like_state", t.getLike_state());
		cv.put("data_item", t.getData_item());
		cv.put("data_item_article", t.getData_item_article());
		cv.put("tread_state", t.getTread_state());
		cv.put("collect_time", t.getCollect_time());
		return cv;
	}

	@Override
	protected LocalStateBean buildQueryValues(Cursor cursor) {
		LocalStateBean bean = new LocalStateBean();
		bean.setId(cursor.getString(cursor.getColumnIndex("id")));
		bean.setLasttime(cursor.getString(cursor.getColumnIndex("lasttime")));
		bean.setRead_state(cursor.getString(cursor.getColumnIndex("read_state")));
		bean.setFavorite_state(cursor.getInt(cursor.getColumnIndex("favorite_state")));
		bean.setLike_state(cursor.getInt(cursor.getColumnIndex("like_state")));
		bean.setData_item(cursor.getString(cursor.getColumnIndex("data_item")));
		bean.setData_item_article(cursor.getString(cursor.getColumnIndex("data_item_article")));
		bean.setTread_state(cursor.getInt(cursor.getColumnIndex("tread_state")));
		bean.setCollect_time(cursor.getString(cursor.getColumnIndex("collect_time")));
		return bean;
	}
	
	private String getLasttimeString(){
		return String.valueOf(System.currentTimeMillis());
	}
	
	public static LocalStateBean generateLocalStateBean(String prefix, String id){
		LocalStateBean bean = new LocalStateBean();
		bean.setId(prefix + id);
		return bean;
	}
	
	public LocalStateBean queryLocalState(String prefix, String id){
		List<LocalStateBean> list = queryTarget("id = ?", new String[] { String.valueOf(prefix + id)}, null);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public LocalStateBean queryLocalStateNotNull(String prefix, String id){
		LocalStateBean bean = queryLocalState(prefix, id);
		if(bean == null){
			bean = generateLocalStateBean(prefix, id);
		}
		return bean;
	}
	
	// read
	public boolean isRead(String prefix, String id){
		return READ_STATE_READ.equals(queryLocalStateNotNull(prefix, id).getRead_state());
	}
	
	public void setReadStateRead(String prefix, String id){
		LocalStateBean bean = queryLocalStateNotNull(prefix, id);
		bean.setRead_state(READ_STATE_READ);
		bean.setLasttime(getLasttimeString());
		insertOne(bean);
	}
	
	// like
	public boolean isLike(String prefix, String id){
		return STATE_TRUE == queryLocalStateNotNull(prefix, id).getLike_state();
	}
	
	public void setLikeStateTrue(String prefix, String id){
		LocalStateBean bean = queryLocalStateNotNull(prefix, id);
		bean.setLike_state(STATE_TRUE);
		bean.setLasttime(getLasttimeString());
		insertOne(bean);
	}
	// tread
	public boolean isTread(String prefix, String id){
		return STATE_TRUE == queryLocalStateNotNull(prefix, id).getLike_state();
	}
	
	public void setTreadStateTrue(String prefix, String id){
		LocalStateBean bean = queryLocalStateNotNull(prefix, id);
		bean.setLike_state(STATE_TRUE);
		bean.setLasttime(getLasttimeString());
		insertOne(bean);
	}
	
	// favorite
	public boolean isFavorite(String prefix, String id){
		return STATE_TRUE == queryLocalStateNotNull(prefix, id).getFavorite_state();
	}
	
	//article 
	public boolean isArticleSaved(String prefix,String id){
		String aStr = queryLocalStateNotNull(prefix,id).getData_item_article();
		if(!TextUtils.isEmpty(aStr)){
			try {
				ArticlesBean bean = SerializeUtil.deSerialize(aStr, ArticlesBean.class);
				if(bean != null && !TextUtils.isEmpty(bean.getTid())){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	//articleOfTuWen
	public boolean isArticleOfTuWenSaved(String prefix,String id){
		String aStr = queryLocalStateNotNull(prefix,id).getData_item_article();
		if(!TextUtils.isEmpty(aStr)){
			try {
				ArticlesPicBean bean = SerializeUtil.deSerialize(aStr, ArticlesPicBean.class);
				if(bean != null && !TextUtils.isEmpty(bean.getTid())){
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public void setArticle(String prefix, String id, String data){
		LocalStateBean bean = queryLocalStateNotNull(prefix, id);
		bean.setData_item_article(data);
		bean.setLasttime(getLasttimeString());
		insertOne(bean);
	}
	
	public void clearArticle(){
		ContentValues values = new ContentValues();
		values.put("data_item_article", "");
		updateTarget(values, null, null);
	}
	
	/**
	 * 保存收藏文章
	 * @param prefix
	 * @param id
	 * @param data
	 */
	public void saveFavArticle(String prefix, String id, String data){
		LocalStateBean bean = queryLocalStateNotNull(prefix, id);
		bean.setData_item(data);
		bean.setFavorite_state(STATE_TRUE);
		bean.setLasttime(getLasttimeString());
		bean.setCollect_time(getLasttimeString());
		insertOne(bean);
	}
	
	/**
	 * 删除收藏文章
	 * @param prefix
	 * @param id
	 */
	public void deleteFavArticle(String prefix, String id) {
		StringBuilder ids = new StringBuilder();
		ids.append(prefix).append(id);
		ContentValues values = new ContentValues();
		values.put("favorite_state", STATE_FALSE);
		updateTarget(values, "id = ?", new String[] {ids.toString()});
	}
	
	/**
	 * 分页获取收藏的文章
	 * @param offset
	 * @return
	 */
	public ArrayList<MChannelItemBean> getFavArticles(int offset) {
		String limit = table_local_state.Columns.COLLECT_TIME + " desc limit 20 offset " + offset;
//		List<LocalStateBean> list = queryTarget("favorite_state = ?", new String[] { String.valueOf(STATE_TRUE)}, "collect_time desc");
		List<LocalStateBean> list = queryTarget("favorite_state = ?", new String[] { String.valueOf(STATE_TRUE)}, limit);
		ArrayList<MChannelItemBean> beans = new ArrayList<MChannelItemBean>();
		String data = null;
		for (LocalStateBean bean:list) {
			data = bean.getData_item();
			if(!TextUtils.isEmpty(data)){
				try {
					MChannelItemBean itemBean = SerializeUtil.deSerialize(data, MChannelItemBean.class);
					beans.add(itemBean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return beans;
	}
	
	/**
	 * 获取收藏的全部文章
	 * @return
	 */
	public ArrayList<MChannelItemBean> getAllFavArticles() {
		List<LocalStateBean> list = queryTarget("favorite_state = ?", new String[] { String.valueOf(STATE_TRUE)}, "collect_time desc");
		ArrayList<MChannelItemBean> beans = new ArrayList<MChannelItemBean>();
		String data = null;
		for (LocalStateBean bean:list) {
			data = bean.getData_item();
			if(!TextUtils.isEmpty(data)){
				try {
					MChannelItemBean itemBean = SerializeUtil.deSerialize(data, MChannelItemBean.class);
					beans.add(itemBean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return beans;
	}
}
