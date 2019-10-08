package com.shenyuan.militarynews;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.shenyuan.militarynews.activity.LoginActivity;
import com.shenyuan.militarynews.beans.data.LoginBean;
import com.shenyuan.militarynews.beans.data.LoginDbBean;
import com.shenyuan.militarynews.data.access.LoginDA;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UIHelper;

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
	 * @param oldDomain 
	 */
	public void updateCookie(String oldDomain) {
		String newDomain = URI.create(JUrl.SITE).getHost();
		if (TextUtils.equals(oldDomain, newDomain)) {
			return;
		}
		
		String oldRootDomain = Common.getRootDomain(oldDomain);
		String newRootDomain = Common.getRootDomain(newDomain);
		
		PersistentCookieStore myCookieStore = new PersistentCookieStore(App.getInst());
		List<Cookie> cookiesList = new ArrayList<Cookie>();
		List<Cookie> cookies = myCookieStore.getCookies();
		String cookieName;
		String cookieValue;
		String curDomain = oldDomain;
		
		for (Cookie cookie : cookies) {
			if (TextUtils.equals(oldRootDomain, cookie.getDomain())) {
				curDomain = newRootDomain;
			} else if (TextUtils.equals(oldDomain, cookie.getDomain())) {
				curDomain = newDomain;
			} else {
				continue;
			}
			cookieName = cookie.getName();  
            cookieValue = cookie.getValue(); 
        	BasicClientCookie clientCookie = new BasicClientCookie(cookieName, cookieValue);
        	clientCookie.setDomain(curDomain);
        	clientCookie.setComment(cookie.getComment());
        	clientCookie.setExpiryDate(cookie.getExpiryDate());
        	clientCookie.setPath(cookie.getPath());
//            	clientCookie.setSecure(cookie.getPath);
        	clientCookie.setVersion(cookie.getVersion());
        	cookiesList.add(clientCookie);
		}
		if (!Common.isListEmpty(cookiesList)) {
			for (Cookie cookie : cookiesList) {
				myCookieStore.addCookie(cookie);
			}
		}
		
		HttpUtil.getClient().setCookieStore(myCookieStore);
	}
}
