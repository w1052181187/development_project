package com.chengning.fenghuovideo.data.access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuovideo.data.bean.UserInfoBean;
import com.chengning.fenghuovideo.db.provider.dbContent.table_userinfo_other;

import java.util.ArrayList;
import java.util.List;

public class UserinfoOtherServer extends BaseListDA<UserInfoBean> {

	private static UserinfoOtherServer mInst;

	public static UserinfoOtherServer getInst(Context con) {
		if (mInst == null) {
            synchronized (UserinfoOtherServer.class) {
                if (mInst == null) {
                    mInst = new UserinfoOtherServer(con);
                }
            }
        }
		return mInst;
	}

	private UserinfoOtherServer(Context con) {
		super(con, table_userinfo_other.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(UserInfoBean t) {
		return "uid = ?";
	}

	@Override
	public String[] buildDeleteSelectionArgs(UserInfoBean t) {
		return new String[] { String.valueOf(t.getUid()) };
	}

	@Override
	public ContentValues buildInsertValues(UserInfoBean bean) {
		ContentValues values = new ContentValues();
		values.put("uid", bean.getUid());
		values.put("username", bean.getUsername());
		values.put("nickname", bean.getNickname());
		values.put("face", bean.getFace());
		values.put("face_small", bean.getFace_small());
		values.put("face_original", bean.getFace_original());
		values.put("gender", bean.getGender());
		values.put("topic_count", bean.getTopic_count());
		values.put("at_count", bean.getAt_count());
		values.put("follow_count", bean.getFollow_count());
		values.put("fans_count", bean.getFans_count());
		values.put("email", bean.getEmail());
		values.put("phone", bean.getPhone());
		values.put("aboutme", bean.getAboutme());
		values.put("aboutmetime", bean.getAboutmetime());
		values.put("province", bean.getProvince());
		values.put("city", bean.getCity());
		values.put("area", bean.getArea());
		values.put("from_area", bean.getFrom_area());
		values.put("gender_ta", bean.getGender_ta());
		values.put("level", bean.getLevel());
		values.put("vip_info", bean.getVip_info());
		values.put("vip_pic", bean.getVip_pic());
		values.put("is_follow", bean.getIs_follow());
		values.put("extcredits1", bean.getExtcredits1());
		values.put("extcredits2", bean.getExtcredits2());
		values.put("role_name", bean.getRole_name());
		values.put("subscribe", bean.getSubscribe());
		values.put("extcredits3", bean.getExtcredits3());
		return values;
	}

	@Override
	public UserInfoBean buildQueryValues(Cursor cursor) {
		UserInfoBean bean = new UserInfoBean();
		bean.setUid(cursor.getString(cursor.getColumnIndex("uid")));
		bean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
		bean.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
		bean.setFace(cursor.getString(cursor.getColumnIndex("face")));
		bean.setFace_small(cursor.getString(cursor.getColumnIndex("face_small")));
		bean.setFace_original(cursor.getString(cursor
				.getColumnIndex("face_original")));
		bean.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
		bean.setTopic_count(cursor.getInt(cursor.getColumnIndex("topic_count")));
		bean.setAt_count(cursor.getInt(cursor.getColumnIndex("at_count")));
		bean.setFollow_count(cursor.getInt(cursor
				.getColumnIndex("follow_count")));
		bean.setFans_count(cursor.getInt(cursor.getColumnIndex("fans_count")));
		bean.setEmail(cursor.getString(cursor.getColumnIndex("email")));
		bean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
		bean.setAboutme(cursor.getString(cursor.getColumnIndex("aboutme")));
		bean.setAboutmetime(cursor.getString(cursor
				.getColumnIndex("aboutmetime")));
		bean.setProvince(cursor.getString(cursor.getColumnIndex("province")));
		bean.setCity(cursor.getString(cursor.getColumnIndex("city")));
		bean.setArea(cursor.getString(cursor.getColumnIndex("area")));
		bean.setFrom_area(cursor.getString(cursor.getColumnIndex("from_area")));
		bean.setGender_ta(cursor.getString(cursor.getColumnIndex("gender_ta")));
		bean.setLevel(cursor.getString(cursor.getColumnIndex("level")));
		bean.setVip_info(cursor.getString(cursor.getColumnIndex("vip_info")));
		bean.setVip_pic(cursor.getString(cursor.getColumnIndex("vip_pic")));
		bean.setIs_follow(cursor.getInt(cursor.getColumnIndex("is_follow")));
		bean.setExtcredits1(cursor.getInt(cursor.getColumnIndex("extcredits1")));
		bean.setExtcredits2(cursor.getInt(cursor.getColumnIndex("extcredits2")));
		bean.setRole_name(cursor.getString(cursor.getColumnIndex("role_name")));
		bean.setSubscribe(cursor.getInt(cursor.getColumnIndex("subscribe")));
		bean.setExtcredits3(cursor.getString(cursor.getColumnIndex("extcredits3")));
		return bean;
	}
	
	public UserInfoBean queryTargetUid(String uid) {
		String selection = "uid = ?";
		String[] selectionArgs = new String[] {uid};
		List<UserInfoBean> list = queryTarget(selection, selectionArgs, null);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public UserInfoBean queryTargetNickname(String nickname) {
		String selection = "nickname = ?";
		String[] selectionArgs = new String[] {nickname};
		List<UserInfoBean> list = queryTarget(selection, selectionArgs, null);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void updateTargetNickname(UserInfoBean bean) {
		String selection = "nickname = ?";
		String[] selectionArgs = new String[] {bean.getNickname()};
		updateTarget(buildInsertValues(bean), selection, selectionArgs);
	}
	
	public void updateTargetUid(UserInfoBean bean) {
		String selection = "uid = ?";
		String[] selectionArgs = new String[] {bean.getUid()};
		updateTarget(buildInsertValues(bean), selection, selectionArgs);
	}

	public String queryNicknameByUid(String uid) {
		String selection = "uid = ?";
		String[] selectionArgs = new String[] {uid};
		List<UserInfoBean> list = queryTarget(selection, selectionArgs, null);
		if(list.size() > 0){
			return list.get(0).getNickname();
		}
		return "";
	}
	
}
