package com.chengning.yiqikantoutiao.data.access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.chengning.yiqikantoutiao.data.bean.UserInfoBean;
import com.chengning.yiqikantoutiao.db.provider.dbContent.table_userinfo_other;

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
		values.put("extcredits2", bean.getExtcredits2());
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
		bean.setExtcredits2(cursor.getInt(cursor.getColumnIndex("extcredits2")));
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
