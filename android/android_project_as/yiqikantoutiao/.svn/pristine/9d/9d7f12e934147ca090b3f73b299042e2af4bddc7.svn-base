package com.chengning.yiqikantoutiao.data.access;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.chengning.common.base.BaseListDA;
import com.chengning.yiqikantoutiao.App;
import com.chengning.yiqikantoutiao.data.bean.ReadedArticleBean;
import com.chengning.yiqikantoutiao.data.bean.SettingBean;
import com.chengning.yiqikantoutiao.db.provider.dbContent.table_nologin_read_article_number;
import com.chengning.yiqikantoutiao.util.ArticleManagerUtils;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.chengning.yiqikantoutiao.widget.NoLoginReadArticleHintDialog;

import java.util.List;

public class ReadedArticleDA extends BaseListDA<ReadedArticleBean> {

	private static ReadedArticleDA mInst;

	public static ReadedArticleDA getInst(Context con) {
	    if (mInst == null) {
            synchronized (ReadedArticleDA.class) {
                if (mInst == null) {
                    mInst = new ReadedArticleDA(con);
                }
            }
        }
		return mInst;
	}

	private ReadedArticleDA(Context con) {
		super(con, table_nologin_read_article_number.CONTENT_URI);
	}

	@Override
	public String buildDeleteWhere(ReadedArticleBean t) {
		return null;
	}

	@Override
	public String[] buildDeleteSelectionArgs(ReadedArticleBean t) {
		return null;
	}

	@Override
	public ContentValues buildInsertValues(ReadedArticleBean bean) {
		ContentValues values = new ContentValues();
		values.put("number",bean.getNumber());
		values.put("readed_time",bean.getReaded_time());
		return values;
	}

	@Override
	public ReadedArticleBean buildQueryValues(Cursor cursor) {
		ReadedArticleBean bean = new ReadedArticleBean();
		bean.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
		bean.setReaded_time(cursor.getLong(cursor.getColumnIndex("readed_time")));
		return bean;
	}

	public ReadedArticleBean getBean(){
		List<ReadedArticleBean> list = queryAll();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}



	public void addReadNumber(Activity activity, ArticleManagerUtils.DataStateListener listener){
		ReadedArticleBean bean = getBean();
		if (bean == null) {
			bean = new ReadedArticleBean();
			insertOne(bean);
		}
		int num = bean.getNumber();
		num++;
		bean.setNumber(num);

		long lastTime = bean.getReaded_time();
		long curTime = Common.getCurTimeMillis();
		if (!Common.isSameDayOfMillis(lastTime,curTime)) {
			listener.success(num, num * Integer.valueOf(App.getInst().getReadGold()));
		}
		bean.setReaded_time(curTime);
		updateTarget(buildInsertValues(bean), buildDeleteWhere(bean), buildDeleteSelectionArgs(bean));
	}

}
