package com.chengning.fenghuovideo.util;

import android.app.Activity;
import android.text.TextUtils;

import com.chengning.common.base.util.BaseUmengShare;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.data.bean.ArticlesBean;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;

import java.util.ArrayList;

public class UmengShare extends BaseUmengShare {

	private static UmengShare share;

	public static UmengShare getInstance(){
		if(share == null){
			share = new UmengShare();
		}
		return share;
	}

	public static String handleImageUrlWithBean(BaseArticlesBean bean){
		String imgUrl = bean.getVideo_photo();
		if (TextUtils.isEmpty(imgUrl)) {
			imgUrl = (String) bean.getImage_list();
		}
		return imgUrl;
	}

	public static String handleImageUrl(ArrayList<String> imgUrls){
		String imgUrl = null;
		if(imgUrls != null){
			for(String s : imgUrls){
				if(!TextUtils.isEmpty(s)){
					imgUrl = s;
					break;
				}
			}
		}
		return imgUrl;
	}

	@Override
	public void handleEventAnalytics(Activity activity, String eventtype, Object... objs) {
//		ArticlesBean bean = (ArticlesBean)obj;
//		Map<String, String> mHashMap = new HashMap<String, String>();
//		mHashMap.put("type", eventtype);
//		mHashMap.put("article_id", bean.getTid());
//		MobclickAgent.onEvent(activity, "artilce_share", mHashMap);
	}

	@Override
	public BaseShareBean translateObjectToShareBean(Object... objs) {
		BaseShareBean bean = null;
		if (objs == null || objs.length < 1){
			return null;
		}
		Object obj = objs[0];
		if (obj instanceof ArticlesBean) {
			bean = new BaseShareBean();
			ArticlesBean articlesBean = (ArticlesBean) obj;
			bean.setAid(articlesBean.getTid());
			bean.setTitle(articlesBean.getTitle());
			bean.setLink(articlesBean.getLink());
			bean.setImageUrl(handleImageUrlWithBean(articlesBean));
		} else if (obj instanceof ChannelItemBean) {
			bean = new BaseShareBean();
			ChannelItemBean itemBean = (ChannelItemBean) obj;
			bean.setAid(itemBean.getTid());
			bean.setTitle(itemBean.getTitle());
			bean.setLink(itemBean.getLink());
			bean.setImageUrl(handleImageUrlWithBean(itemBean));
		}
		return bean;
	}

//
//	public void setShareType(int type) {
//		this.mShareType = type;
//	}
//
//	public int getShareType() {
//		return mShareType;
//	}

//	@Override
//	public void setAid(String aid) {
//		setShareType(Consts.TASK_SHARE_ARTICLE);
//		super.setAid(aid);
//	}
}
