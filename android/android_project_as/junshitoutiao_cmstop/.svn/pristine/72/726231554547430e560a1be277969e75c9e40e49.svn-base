package com.cmstop.jstt;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengning.common.util.HttpUtil;
import com.cmstop.jstt.activity.LoginActivity;
import com.cmstop.jstt.beans.data.LoginBean;
import com.cmstop.jstt.beans.data.LoginDbBean;
import com.cmstop.jstt.data.access.LoginDA;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.UIHelper;
import com.loopj.android.http.PersistentCookieStore;

public class LoginManager {

	public static final String TAG = LoginManager.class.getSimpleName();
	
	private static LoginManager mLogin = null;
	
	public static LoginManager getInst(){
		
		if (mLogin == null) {
            synchronized (LoginManager.class) {
                if(mLogin == null){
                    mLogin = new LoginManager();
                }
            }
        }
		return mLogin;
	}
	
	private LoginManager(){
		
	}
	
	public void init(){
		// check old login
		// deviceToken...
		App.getInst().saveLoginBean(getLoginDbBean().getUserinfo());
	}

	public static void startLoginActivity(Context context){
		if(context == null){
			return;
		}
		Intent intent = new Intent(context, LoginActivity.class);
		if(!(context instanceof Activity)){
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		context.startActivity(intent);
	}

	public static void startLoginActivityForResult(Context context, Bundle extras, int requestCode){
		if(context == null){
			return;
		}
		Intent intent = new Intent(context, LoginActivity.class);
		if(!(context instanceof Activity)){
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtras(extras);
			context.startActivity(intent);
		}else{
			intent.putExtras(extras);
			((Activity)context).startActivityForResult(intent, requestCode);
		}
	}
	
	public static LoginDbBean getEmptyLoginDbBean(){
		LoginDbBean bean = new LoginDbBean();
		bean.setUserinfo(new LoginBean());
		bean.setLasttime("");
		return bean;
	}
	
	public void logout(){
		clearData();
	}
	
	public void clearData(){
		Context context = App.getInst().getApplicationContext();
		LoginDA.getInst(context).deleteAll();
//		App.getInst().setIsLogin(false);
	}
	
	public boolean isLogin(){
		LoginDbBean bean = getLoginDbBean();
		boolean isLogin = !TextUtils.isEmpty(bean.getLasttime()) && !TextUtils.isEmpty(bean.getUserinfo().getUid());
		return isLogin;
	}
	
	public static void noticeNotLaunched(Context context){
		UIHelper.showToast(context, "您还没有登录，请先登录！");
	}

	public boolean checkLoginWithNotice(Context context){
		boolean isLogin = isLogin();
		if(!isLogin){
			noticeNotLaunched(context);
		}
		return isLogin;
	}

	public boolean checkLoginWithLogin(Context context){
		boolean isLogin = isLogin();
		if(!isLogin){
			startLoginActivity(context);
		}
		return isLogin;
	}
	
	public LoginDbBean getLoginDbBean(){
		LoginDbBean bean = LoginDA.getInst(App.getInst()).getLoginUserBean();
		if(bean == null){
			bean = getEmptyLoginDbBean();
			LoginDA.getInst(App.getInst()).insertOne(bean);
		}
		return bean;
	}
	
	public void saveLoginBean(LoginBean bean){
		LoginDbBean lBean = getLoginDbBean();
		lBean.setUserinfo(bean);
		saveLoginDbBean(lBean, true);
	}
	
	public void saveLoginDbBean(LoginDbBean bean){
		saveLoginDbBean(bean, true);
	}
	
	public void saveLoginDbBean(LoginDbBean bean, boolean isNewTime){
		if(isNewTime && !TextUtils.isEmpty(bean.getUserinfo().getUid())){
			bean.setLasttime(String.valueOf(System.currentTimeMillis()));
		}
		LoginDA.getInst(App.getInst()).deleteAll();
		LoginDA.getInst(App.getInst()).insertOne(bean);
	}
	
	/**
	 * 更新cookie
	 */
	public void updateCookie() {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(App.getInst());
		List<Cookie> cookiesList = new ArrayList<Cookie>();
		List<Cookie> cookies = myCookieStore.getCookies();
		String cookieName;
		String cookieValue;
		for (Cookie cookie : cookies) {
			String domain = Common.getDomainForUrl(JUrl.SITE);
			if (!TextUtils.equals(domain, cookie.getDomain())) {
				cookieName = cookie.getName();  
	            cookieValue = cookie.getValue(); 
	        	BasicClientCookie clientCookie = new BasicClientCookie(cookieName, cookieValue);
	        	clientCookie.setDomain(domain);
	        	clientCookie.setComment(cookie.getComment());
	        	clientCookie.setExpiryDate(cookie.getExpiryDate());
	        	clientCookie.setPath(cookie.getPath());
//	            	clientCookie.setSecure(cookie.getPath);
	        	clientCookie.setVersion(cookie.getVersion());
	        	cookiesList.add(clientCookie);
			}
		}
		for (Cookie cookie : cookiesList) {
			myCookieStore.addCookie(cookie);
		}
		HttpUtil.getClient().setCookieStore(myCookieStore);
	}
}
