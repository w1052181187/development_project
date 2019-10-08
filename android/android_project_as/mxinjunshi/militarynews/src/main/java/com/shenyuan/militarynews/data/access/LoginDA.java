package com.shenyuan.militarynews.data.access;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.common.util.SerializeUtil;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.beans.data.LoginDbBean;
import com.shenyuan.militarynews.provider.dbContent.table_login;

public class LoginDA extends BaseListDA<LoginDbBean> {

	private static LoginDA mInst;

	public static LoginDA getInst(Context con) {
		if (mInst == null) {
            synchronized (LoginDA.class) {
                if(mInst == null){
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
	public String buildDeleteWhere(LoginDbBean t) {
		return null;
	}

	@Override
	public String[] buildDeleteSelectionArgs(LoginDbBean t) {
		return null;
	}
	
	@Override
	public ContentValues buildInsertValues(LoginDbBean bean) {
		ContentValues values = new ContentValues();
		values.put("userinfo", SerializeUtil.serialize(bean.getUserinfo()));
		values.put("pwd", bean.getPwd());
		values.put("cookie", bean.getCookie());
		values.put("lasttime", bean.getLasttime());
		return values;
	}

	@Override
	public LoginDbBean buildQueryValues(Cursor cursor) {
		LoginDbBean bean = new LoginDbBean();
		LoginBean lBean = SerializeUtil.deSerialize(
				cursor.getString(cursor.getColumnIndex("userinfo")),
				LoginBean.class);
		bean.setUserinfo(lBean);
		bean.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
		bean.setCookie(cursor.getString(cursor
				.getColumnIndex("cookie")));
		bean.setLasttime(cursor.getString(cursor
				.getColumnIndex("lasttime")));
		return bean;
	}

	public LoginDbBean getLoginUserBean() {
		List<LoginDbBean> list = queryAll();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
