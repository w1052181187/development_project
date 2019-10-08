package com.chengning.fenghuo.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuo.data.bean.ChatConversationBean;
import com.chengning.fenghuo.data.bean.ChatMessageBean;
import com.chengning.fenghuo.db.provider.dbContent.table_chat_conversation_list;
import com.chengning.fenghuo.util.Common;

public class ChatConversationDA extends BaseListDA<ChatConversationBean> {

	private static ChatConversationDA mInst;

	public static ChatConversationDA getInst(Context con) {
		if (mInst == null) {
            synchronized (ChatConversationDA.class) {
                if (mInst == null) {
                    mInst = new ChatConversationDA(con);
                }
            }
        }
		return mInst;
	}
	
	public ChatConversationDA(Context context) {
		super(context, table_chat_conversation_list.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(ChatConversationBean t) {
		return "con_id = ?";
	}

	@Override
	public String[] buildDeleteSelectionArgs(ChatConversationBean t) {
		return new String[]{t.getCon_id()};
	}

	@Override
	public ContentValues buildInsertValues(ChatConversationBean bean) {
		
		ContentValues values = new ContentValues();
		values.put("con_id", bean.getCon_id());
		values.put("con_name", bean.getCon_name());
		values.put("con_face", bean.getCon_face());
		values.put("role_name", bean.getRole_name());
		values.put("newest_msg", SerializeUtil.serialize(bean.getNewest_msg()));
		values.put("con_members", SerializeUtil.serialize(bean.getCon_members()));
		values.put("msg_no_readed_num", bean.getMsg_no_readed_num());
		values.put("creator", bean.getCreator());
		values.put("uid", bean.getUid());
		values.put("update_time", bean.getUpdate_time());
		return values;
	}

	@Override
	public ChatConversationBean buildQueryValues(Cursor cursor) {
		ChatConversationBean bean = new ChatConversationBean();
		bean.setCon_id(cursor.getString(cursor.getColumnIndex("con_id")));
		bean.setCon_name(cursor.getString(cursor.getColumnIndex("con_name")));
		bean.setCon_face(cursor.getString(cursor.getColumnIndex("con_face")));
		bean.setRole_name(cursor.getString(cursor.getColumnIndex("role_name")));
		bean.setNewest_msg((ChatMessageBean) SerializeUtil.deSerialize(cursor.getString(cursor.getColumnIndex("newest_msg"))));
		bean.setCon_members((ArrayList<String>) SerializeUtil.deSerialize(cursor.getString(cursor.getColumnIndex("con_members"))));
		bean.setMsg_no_readed_num(cursor.getInt(cursor.getColumnIndex("msg_no_readed_num")));
		bean.setCreator(cursor.getString(cursor.getColumnIndex("creator")));
		bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
		bean.setUpdate_time(cursor.getString(cursor.getColumnIndex("update_time")));
		return bean;
	}
	
	/**
	 * 根据uid查询当前用户的所有对话
	 * @param uid
	 * @return
	 */
	public List<ChatConversationBean> queryAllConByUid(String uid) {
		String limit = table_chat_conversation_list.Columns.UPDATE_TIME + " desc";
		List<ChatConversationBean> list = queryTarget("uid = ?", new String[]{uid}, limit);
		return list;
	}
	
	/**
	 * 创建或更新对话
	 * @param bean
	 */
	public void creatConversationList(ChatConversationBean bean) {
//		updateTarget(buildInsertValues(bean), buildDeleteWhere(bean), buildDeleteSelectionArgs(bean));
		ChatConversationBean con = queryConversation(bean.getUid(), bean.getCon_id());
		if (null == con) {
			insertOne(bean);
		} else {
			updateTarget(buildInsertValues(bean), buildDeleteWhere(bean), buildDeleteSelectionArgs(bean));
		}
	}
	
	/**
	 * 查询对话
	 * @param conId
	 * @return
	 */
	public ChatConversationBean queryConversation(String uid, String conId) {
		String selection = "uid = ? and con_id = ?";
		List<ChatConversationBean> list = queryTarget(selection, new String[] {uid, conId}, null);
		if (Common.isListEmpty(list)){
			return null;
		}
		return list.get(0);
		
	}
	
	/**
	 * 查询对话最新消息
	 * @param id
	 * @return
	 */
	public ChatMessageBean queryLastestMessage(String uid, String id) {
		String selection = "uid = ? and con_id = ?";
		String[] selectionArgs = new String[] {id};
		ChatMessageBean message = null;
		List<ChatConversationBean> list = queryTarget(selection, selectionArgs, null);
		if (!Common.isListEmpty(list)){
			message = list.get(0).getNewest_msg();
		}
		return message;
		
	}
	
	/**
	 * 更新未读对话最新消息
	 * @param bean
	 * @param msg
	 */
	public void updateLatestMessage(ChatConversationBean bean, ChatMessageBean msg) {
		bean.setNewest_msg(msg);
		if (null != msg) { 
			bean.setUpdate_time(msg.getTime());
		}
		creatConversationList(bean);
	}

	/**
	 * 对话未读消息清零
	 * @param bean
	 */
	public void resetUnreadCount(ChatConversationBean bean) {
		bean.setMsg_no_readed_num(0);
		updateTarget(buildInsertValues(bean), buildDeleteWhere(bean), buildDeleteSelectionArgs(bean));
	}
}
