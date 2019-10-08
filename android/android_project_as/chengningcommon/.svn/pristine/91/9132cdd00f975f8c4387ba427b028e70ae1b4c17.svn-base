package com.chengning.common.base;

import com.chengning.common.app.ActivityInfo;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public interface IBaseActivity {
	
	/**
	 * 获取上下文
	 */
	public Activity getActivity();
	
	/**
	 * 获取handler
	 */
	public Handler getHandler();
	
	/**
	 * 启动当前Activity
	 */
	public void launchThisActivity(Activity from);
	
	/**
	 * 初始化视图
	 */
	public void initViews();
	
	/**
	 * 初始化数据
	 */
	public void initDatas();
	
	/**
	 * 设置监听器
	 */
	public void installListeners();
	
	/**
	 * 取消监听
	 */
	public void uninstallListeners();
	
	/**
	 * 处理handler消息
	 */
	public void processHandlerMessage(Message msg);
	
	/**
	 * 获取Activity信息
	 */
	public ActivityInfo getActivityInfo();

}
