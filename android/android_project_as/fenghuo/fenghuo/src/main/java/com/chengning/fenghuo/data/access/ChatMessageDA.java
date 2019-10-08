package com.chengning.fenghuo.data.access;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.fenghuo.data.bean.ChatMessageBean;
import com.chengning.fenghuo.db.provider.dbContent.table_chat_message_list;

public class ChatMessageDA extends BaseListDA<ChatMessageBean> {

	private static ChatMessageDA  mInst;

	public static ChatMessageDA  getInst(Context con) {
		if (mInst == null) {
		    synchronized (ChatMessageDA.class) {
                if (mInst == null) {
                    mInst = new ChatMessageDA(con);
                }
            }
        }
		return mInst;
	}
	
	public ChatMessageDA (Context context) {
		super(context, table_chat_message_list.CONTENT_URI);
	}
	
	@Override
	public String buildDeleteWhere(ChatMessageBean t) {
		return "con_id = ?";
	}

	@Override
	public String[] buildDeleteSelectionArgs(ChatMessageBean t) {
		return new String[]{t.getCon_id()};
	}

	@Override
	public ContentValues buildInsertValues(ChatMessageBean bean) {
		ContentValues values = new ContentValues();
		values.put("uid", bean.getUid());
		values.put("con_id", bean.getCon_id());
		values.put("msg_from", bean.getMsg_from());
		values.put("time", bean.getTime());
		values.put("face", bean.getFace());
		values.put("content", bean.getContent());
		values.put("msg_type", bean.getMsg_type());
		values.put("msg_id", bean.getMsg_id());
		values.put("msg_status", bean.getMsg_status());
		return values;
	}

	@Override
	public ChatMessageBean buildQueryValues(Cursor cursor) {
		ChatMessageBean bean = new ChatMessageBean();
		bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
		bean.setCon_id(cursor.getString(cursor.getColumnIndex("con_id")));
		bean.setContent(cursor.getString(cursor.getColumnIndex("content")));
		bean.setFace(cursor.getString(cursor.getColumnIndex("face")));
		bean.setMsg_from(cursor.getString(cursor.getColumnIndex("msg_from")));
		bean.setTime(cursor.getString(cursor.getColumnIndex("time")));
		bean.setMsg_id(cursor.getString(cursor.getColumnIndex("msg_id")));
		bean.setMsg_status(cursor.getInt(cursor.getColumnIndex("msg_status")));
		bean.setMsg_type(cursor.getInt(cursor.getColumnIndex("msg_type")));
		return bean;
	}
	
	/**
	 * 创建消息列表
	 * @param bean
	 */
	public void creatMsgList(ChatMessageBean bean) {
//		updateTarget(buildInsertValues(bean), buildDeleteWhere(bean), buildDeleteSelectionArgs(bean));
		insertOne(bean);
	}

	/**
	 * 清空聊天记录
	 * @param con_id
	 */
	public void clearAllMsg(String uid, String con_id) {
		String selection = "uid = ? and con_id = ?";
		deleteTarget(selection, new String[]{uid, con_id});
	}

	/**
	 * 获取聊天消息
	 * @param con_id
	 * @param offset
	 * @return
	 */
	public List<ChatMessageBean> queryMessage(String uid, String con_id, int offset){
		String selection = "uid = ? and con_id = ?";
		String limit = table_chat_message_list.Columns.TIME + " desc limit 20 offset " + offset;
		List<ChatMessageBean> list = queryTarget(selection, new String[]{uid, con_id}, limit);
		return list;
	}
	
}
