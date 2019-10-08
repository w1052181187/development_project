package com.chengning.fenghuo.data.access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuo.data.bean.CircleBean;
import com.chengning.fenghuo.data.bean.DynamicItemBean;
import com.chengning.fenghuo.db.provider.dbContent.table_circle_item;

@Deprecated
public class CircleItemDA extends BaseListDA<CircleBean> {

	private static CircleItemDA mInst;

	public static CircleItemDA getInst(Context con) {
		if (mInst == null) {
            synchronized (CircleItemDA.class) {
                if (mInst == null) {
                    mInst = new CircleItemDA(con);
                }
            }
        }
		return mInst;
	}

	private CircleItemDA(Context con) {
		super(con, table_circle_item.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(CircleBean t) {
		return null;
	}

	@Override
	public String[] buildDeleteSelectionArgs(CircleBean t) {
		return null;
	}

	@Override
	public ContentValues buildInsertValues(CircleBean bean) {
		ContentValues values = new ContentValues();
		values.put("qid", bean.getQid());
		values.put("name", bean.getName());
		values.put("icon", bean.getIcon());
		values.put("member_num", bean.getMember_num());
		values.put("thread_num", bean.getThread_num());
		values.put("desc", bean.getDesc());
		values.put("gview_perm", bean.getGview_perm());
		values.put("join_type", bean.getJoin_type());
		values.put("dateline", bean.getDateline());
		values.put("is_qun_member", bean.getIs_qun_member());
		values.put("is_founder", bean.getIs_founder());
		values.put("face", bean.getFace());
		values.put("nickname", bean.getNickname());
		values.put("founderuid", bean.getFounderuid());
		values.put("topic_list", SerializeUtil.serialize(bean.getTopic_list()));
		values.put("id", bean.getId());
		values.put("uid", bean.getUid());
		values.put("message", bean.getMessage());
		values.put("action", bean.getAction());
		values.put("is_slip", bean.getIs_slip());
		values.put("apply_time", bean.getApply_time());
		values.put("aboutme", bean.getAboutme());
		values.put("list_size", bean.getList_size());
		values.put("local_type", bean.getLocal_type());
		values.put("local_page", bean.getLocal_page());
		return values;
	}

	@Override
	public CircleBean buildQueryValues(Cursor cursor) {
		CircleBean bean = new CircleBean();
		bean.setQid(cursor.getString(cursor.getColumnIndex("qid")));
		bean.setName(cursor.getString(cursor.getColumnIndex("name")));
		bean.setMember_num(cursor.getString(cursor.getColumnIndex("member_num")));
		bean.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
		bean.setThread_num(cursor.getString(cursor.getColumnIndex("thread_num")));
		bean.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
		bean.setGview_perm(cursor.getInt(cursor.getColumnIndex("gview_perm")));
		bean.setJoin_type(cursor.getInt(cursor.getColumnIndex("join_type")));
		bean.setDateline(cursor.getString(cursor.getColumnIndex("dateline")));
		bean.setIs_qun_member(cursor.getInt(cursor.getColumnIndex("is_qun_member")));
		bean.setIs_founder(cursor.getInt(cursor.getColumnIndex("is_founder")));
		bean.setFace(cursor.getString(cursor.getColumnIndex("face")));
		bean.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
		bean.setFounderuid(cursor.getString(cursor.getColumnIndex("founderuid")));
		bean.setLocal_type(cursor.getString(cursor.getColumnIndex("local_type")));
		 
		ArrayList<DynamicItemBean> topic_list = (ArrayList<DynamicItemBean>) SerializeUtil.deSerialize(cursor.getString(cursor.getColumnIndex("topic_list")));
		bean.setTopic_list(topic_list);
		bean.setId(cursor.getString(cursor.getColumnIndex("id")));
		bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
		bean.setMessage(cursor.getString(cursor.getColumnIndex("message")));
		bean.setAction(cursor.getInt(cursor.getColumnIndex("action")));
		bean.setIs_slip(cursor.getInt(cursor.getColumnIndex("is_slip")));
		bean.setApply_time(cursor.getString(cursor.getColumnIndex("apply_time")));
		bean.setAboutme(cursor.getString(cursor.getColumnIndex("aboutme")));
		bean.setList_size(cursor.getString(cursor.getColumnIndex("list_size")));
		bean.setLocal_page(cursor.getInt(cursor.getColumnIndex("local_type")));
		return bean;
	}
	
//	public void setChannelAndPage(List<CircleBean> list, String type, int page){
//		if(list != null){
//			for(CircleBean b : list){
//				b.setLocal_type(type);
//				b.setLocal_page(page);
//			}
//		}
//	}
	
	public void clearChannel(String type){
		deleteTarget("local_type = ?",
				new String[]{type});
	}
	
	public void insertCircle(CircleBean bean, String type, int page){
		bean.setLocal_type(type);
		bean.setLocal_page(page);
		insertOne(bean);
	}
	
	public CircleBean queryChannelItem(String type, int page){
		List<CircleBean> list = queryTarget("local_type = ? and local_page = ?",
				new String[]{type, String.valueOf(page)}, null);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
