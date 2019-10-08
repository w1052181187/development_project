package com.chengning.fenghuo.data.access;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.chengning.fenghuo.data.bean.LoginUserBean;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.db.provider.dbContent.table_login;

public class LoginDA extends BaseListDA<LoginUserBean> {

	private static LoginDA mInst;

	public static LoginDA getInst(Context con) {
	    if (mInst == null) {
            synchronized (LoginDA.class) {
                if (mInst == null) {
                    mInst = new LoginDA(con);
                }
            }
        }
		return mInst;
	}

	private LoginDA(Context con) {
		super(con, table_login.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(LoginUserBean t) {
		return null;
	}

	@Override
	public String[] buildDeleteSelectionArgs(LoginUserBean t) {
		return null;
	}
	
	@Override
	public ContentValues buildInsertValues(LoginUserBean bean) {
		ContentValues values = new ContentValues();
//		values.put("credits", SerializeUtil.serialize(bean.getCredits()));
		values.put("userinfo", SerializeUtil.serialize(bean.getUserinfo()));
		values.put("pwd", bean.getPwd());
		values.put("sina_token", bean.getSina_token());
		values.put("qq_token", bean.getQq_token());
		values.put("weixin_token", bean.getWeixin_token());
		values.put("cookie", bean.getCookie());
		values.put("lasttime", bean.getLasttime());
		return values;
	}

	@Override
	public LoginUserBean buildQueryValues(Cursor cursor) {
		LoginUserBean bean = new LoginUserBean();
//		CreditsBean cBean = SerializeUtil.deSerialize(
//				cursor.getString(cursor.getColumnIndex("credits")),
//				CreditsBean.class);
//		bean.setCredits(cBean);
		UserInfoBean uBean = SerializeUtil.deSerialize(
				cursor.getString(cursor.getColumnIndex("userinfo")),
				UserInfoBean.class);
		bean.setUserinfo(uBean);
		bean.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
		bean.setSina_token(cursor.getString(cursor.getColumnIndex("sina_token")));
		bean.setQq_token(cursor.getString(cursor.getColumnIndex("qq_token")));
		bean.setWeixin_token(cursor.getString(cursor
				.getColumnIndex("weixin_token")));
		bean.setCookie(cursor.getString(cursor
				.getColumnIndex("cookie")));
		bean.setLasttime(cursor.getString(cursor
				.getColumnIndex("lasttime")));
		return bean;
	}

	public LoginUserBean getLoginUserBean() {
		List<LoginUserBean> list = queryAll();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
