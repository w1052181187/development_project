package com.cmstop.jstt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cmstop.jstt.activity.MyCommentActivity;
import com.cmstop.jstt.beans.data.PushLCBean;
import com.cmstop.jstt.utils.SPHelper;

public class PushMsgManager {

	private static PushMsgManager pushMsgManager = null;

	public static final String STR_0 = "0";
	public static final String STR_MORE = "...";

	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_AT_NEW = 1; // @我的
	public static final int TYPE_COMMENT_NEW = 2; // 评论
	public static final int TYPE_DIG_NEW = 3; // 赞我的
	/** @deprecated not used */
	public static final int TYPE_DYNAMIC_NEW = 4; // 动态
	public static final int TYPE_FANS_NEW = 5; // 关注
	public static final int TYPE_PM_NEW = 6; // 消息
	/** @deprecated not used */
	public static final int TYPE_QUN_NOTICE_NEW = 7;
	/** @deprecated not used */
	public static final int TYPE_NOTICE_NEW = 8;
	/** @deprecated not used */
	public static final int TYPE_MY_NEW = 9;

	public static final int[] ARRAY_TYPE = { TYPE_AT_NEW, TYPE_COMMENT_NEW,
			TYPE_DIG_NEW, TYPE_DYNAMIC_NEW, TYPE_FANS_NEW, TYPE_PM_NEW,
			TYPE_QUN_NOTICE_NEW, TYPE_NOTICE_NEW, TYPE_MY_NEW };

	private static final int TIME_ADD_LISTENER_EVENT_DELAYED = 2;

	private HashSet<Integer> mChangedTypes;
	private HashMap<Integer, HashSet<PushMsgEventListener>> mListenerMap;

	private Context mContext;
	private Handler mHandler;
	private PushMsgUserDataBean mBean;

	private boolean hasInited = false;

	public static PushMsgManager getInstance() {
		if (pushMsgManager == null) {
			pushMsgManager = new PushMsgManager();
		}
		return pushMsgManager;
	}

	private PushMsgManager() {
		mChangedTypes = new HashSet<Integer>();
		mListenerMap = new HashMap<Integer, HashSet<PushMsgEventListener>>();
	}

	public void init(Context context) {
		mContext = context;
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
			}
		};

		destroy();

		boolean hasListener = false;
		Iterator iterator = mListenerMap.keySet().iterator();
		while (iterator.hasNext() && !hasListener) {
			Integer key2 = (Integer) iterator.next();
			hasListener = mListenerMap.get(key2).size() != 0;
		}

		hasInited = true;
		
		mBean = new PushMsgUserDataBean();
	}
	
	public static boolean isUserLogin(PushLCBean lc){
		return LoginManager.getInst().isLogin() 
				&&  LoginManager.getInst().getLoginDbBean().getUserinfo().getUid().equals(lc.getExtension().getUid());
	}
	
//	public static boolean isInPushTime(){
//		String start = SettingManager.getInst().getSettingBean().getConfig_push_start_time();
//		String end = SettingManager.getInst().getSettingBean().getConfig_push_end_time();
//		String current = new SimpleDateFormat("HH").format(new Date(System.currentTimeMillis()));
//		int startInt = getTimeInt(start);
//		int endInt = getTimeInt(end);
//		int currentInt = getTimeInt(current);
//		return  currentInt >= startInt && currentInt <= endInt;
//	}
	
	public static int getTimeInt(String str){
		int a = 0;
		try {
			String subStr = null;
			if(str.contains(":")){
				subStr = str.substring(0, str.indexOf(":"));
			}else if(str.length() > 2){
				subStr = str.substring(0, 2);
			}else{
				subStr = str.substring(0, str.length());
			}
			a = Integer.valueOf(subStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public Object[] filterLCPush(Context context, int type){
		Object[] rets = new Object[2];
		boolean filtered = false;
		Intent resultIntent = null;
		switch (type) {
		case PushReceiver.TYPE_FANS:
//			if(!Common.isTrue(SettingManager.getInst().getSettingBean().getConfig_push_is_fan()) || !isUserLogin(lc) || !isInPushTime()){
//				filtered = true;
//				break;
//			}
//			mBean.setFans_new(mBean.getFans_new() + 1);
//			resultIntent = new Intent(context, MyFansListActivity.class);
			break;
		case PushReceiver.TYPE_AT:
//			if(!Common.isTrue(SettingManager.getInst().getSettingBean().getConfig_push_is_at()) || !isUserLogin(lc) || !isInPushTime()){
//				filtered = true;
//				break;
//			}
//			mBean.setAt_new(mBean.getAt_new() + 1);
//			resultIntent = new Intent(context, AtMeListActivity.class);
			break;
		case PushReceiver.TYPE_COMMENT:
//			if(!Common.isTrue(SettingManager.getInst().getSettingBean().getConfig_push_is_reply()) || !isUserLogin(lc) || !isInPushTime()){
//				filtered = true;
//				break;
//			}
			mBean.setComment_new(mBean.getComment_new() + 1);
			resultIntent = new Intent(context, MyCommentActivity.class);
			resultIntent.putExtra("isPush", true);
			resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			break;
		case PushReceiver.TYPE_DIG:
//			if(!Common.isTrue(SettingManager.getInst().getSettingBean().getConfig_push_is_dig()) || !isUserLogin(lc) || !isInPushTime()){
//				filtered = true;
//				break;
//			}
//			mBean.setDig_new(mBean.getDig_new() + 1);
//			resultIntent = new Intent(context, MyDigListActivity.class);
			break;
		case PushReceiver.TYPE_MSG:
//			if(!Common.isTrue(SettingManager.getInst().getSettingBean().getConfig_push_is_pm()) || !isUserLogin(lc) || !isInPushTime()){
//				filtered = true;
//				break;
//			}
//			mBean.setNewpm(mBean.getNewpm() + 1);
//			resultIntent = new Intent(context, ChatListActivity.class);
			break;
		default:
			filtered = false;
			break;
		}
		rets[0] = filtered;
		rets[1] = resultIntent;
		return rets;
	}

	public void destroy() {
		hasInited = false;
	}

	public PushMsgUserDataBean getPushUserDataBean() {
		return mBean;
	}

	public void addListener(final int type, final PushMsgEventListener listener) {
		Integer key = Integer.valueOf(type);
		if (!mListenerMap.containsKey(key)) {
			HashSet<PushMsgEventListener> set = new HashSet<PushMsgManager.PushMsgEventListener>();
			mListenerMap.put(key, set);
		}
		mListenerMap.get(key).add(listener);
		if (!hasInited) {
			return;
		}

		if (mBean != null && mContext != null) {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (mContext != null && listener != null) {
						forceHandlePushData(type, mBean);
					}
				}
			}, TIME_ADD_LISTENER_EVENT_DELAYED);
		}
	}

	public void removeListener(int type, PushMsgEventListener listener) {
		Integer key = Integer.valueOf(type);
		if (mListenerMap.containsKey(key)) {
			HashSet<PushMsgEventListener> set = mListenerMap.get(key);
			if (set.contains(listener)) {
				set.remove(listener);
			}
		}
		boolean hasListener = false;
		Iterator iterator = mListenerMap.keySet().iterator();
		while (iterator.hasNext() && !hasListener) {
			Integer key2 = (Integer) iterator.next();
			hasListener = mListenerMap.get(key2).size() != 0;
		}
		if (!hasInited) {
			return;
		}
	}

	private int notifyPushEvent(final int type, final PushMsgUserDataBean bean) {
		Integer key = Integer.valueOf(type);
		if (mListenerMap.containsKey(key)) {
			HashSet<PushMsgEventListener> set = mListenerMap.get(key);
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				final PushMsgEventListener listener = (PushMsgEventListener) iterator
						.next();
				if (listener != null) {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							listener.onEvent(type, bean);
						}
					});
				}
			}
			return set.size();
		} else {
			return 0;
		}
	}

	private void handlePushData(PushMsgUserDataBean bean) {
		if (mContext == null) {
			destroy();
			return;
		}
		if (bean == null) {
			return;
		}

		mBean = bean;
		int handled = 0;
		mChangedTypes.clear();

		for (int type : ARRAY_TYPE) {
			handled += notifyPushEvent(type, bean);
		}
	}

	public void forceHandlePushData(int type, PushMsgUserDataBean bean) {
		if (mContext == null) {
			destroy();
			return;
		}
		if (bean == null) {
			return;
		}

		int handled = 0;

		for (int t : ARRAY_TYPE) {
			if (t == type) {
				handled += notifyPushEvent(t, bean);
			}
		}
	}

	public static String minusValue(String s, String t) {
		String ret = STR_0;
		if (TextUtils.isEmpty(t)) {
			ret = s;
		} else if (!TextUtils.isEmpty(s) && !STR_0.equals(s)
				&& !STR_0.equals(t)) {
			try {
				int sn = Integer.parseInt(s);
				int tn = Integer.parseInt(t);
				int retn = sn - tn;
				if (retn <= 0) {
					ret = STR_0;
				} else {
					ret = Integer.toString(retn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public abstract static class SimplePushMsgEventListener implements
			PushMsgEventListener {
		private TextView mRedDot;
		private View[] mOther;

		public SimplePushMsgEventListener(TextView red, View... other) {
			this.mRedDot = red;
			this.mOther = other;
		}

		public abstract long configValue(int type, PushMsgUserDataBean bean);

		@Override
		public int onEvent(int type, PushMsgUserDataBean bean) {
			String value = String.valueOf(configValue(type, bean));
			if (PushMsgManager.STR_0.equals(value) || TextUtils.isEmpty(value)) {
				if (mOther != null) {
					for (View v : mOther) {
						v.setVisibility(View.VISIBLE);
					}
				}
				mRedDot.setVisibility(View.INVISIBLE);
			} else {
				if (value.length() > 2) {
					value = PushMsgManager.STR_MORE;
				}

				if (mOther != null) {
					for (View v : mOther) {
						v.setVisibility(View.INVISIBLE);
					}
				}
				mRedDot.setVisibility(View.VISIBLE);
				mRedDot.setText(value);
			}
			return 0;
		}
	}
	
	public static long getCount(int type){
		long count = SPHelper.getInst().getLong(SPHelper.KEY_PUSH_MSG_COUNT_PREFIX + type);
		if(count < 0){
			// 默认值-1，处理为0
			count = 0;
		}
		return count;
	}
	
	public static void setCount(int type, long count){
		SPHelper.getInst().saveLong(SPHelper.KEY_PUSH_MSG_COUNT_PREFIX + type, count);
	}
	
	public static void clearCount(int type){
		SPHelper.getInst().saveLong(SPHelper.KEY_PUSH_MSG_COUNT_PREFIX + type, 0);
	}

	public static interface PushMsgEventListener {
		public int onEvent(int type, PushMsgUserDataBean bean);
	}
	
	public static class PushMsgUserDataBean{

		public long getAt_new() {
			return getCount(TYPE_AT_NEW);
		}

		public void setAt_new(long at_new) {
			setCount(TYPE_AT_NEW, at_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_AT_NEW, this);
		}

		public long getComment_new() {
			return getCount(TYPE_COMMENT_NEW);
		}

		public void setComment_new(long comment_new) {
			setCount(TYPE_COMMENT_NEW, comment_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_COMMENT_NEW, this);
		}

		public long getDig_new() {
			return getCount(TYPE_DIG_NEW);
		}

		public void setDig_new(long dig_new) {
			setCount(TYPE_DIG_NEW, dig_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_DIG_NEW, this);
		}

		public long getDynamic_new() {
			return getCount(TYPE_DYNAMIC_NEW);
		}

		public void setDynamic_new(long dynamic_new) {
			setCount(TYPE_DYNAMIC_NEW, dynamic_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_DYNAMIC_NEW, this);
		}

		public long getFans_new() {
			return getCount(TYPE_FANS_NEW);
		}

		public void setFans_new(long fans_new) {
			setCount(TYPE_FANS_NEW, fans_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_FANS_NEW, this);
		}

		public long getNewpm() {
			return getCount(TYPE_PM_NEW);
		}

		public void setNewpm(long newpm) {
			setCount(TYPE_PM_NEW, newpm);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_PM_NEW, this);
		}

		public long getQun_notice_new() {
			return getCount(TYPE_QUN_NOTICE_NEW);
		}

		public void setQun_notice_new(long qun_notice_new) {
			setCount(TYPE_QUN_NOTICE_NEW, qun_notice_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_QUN_NOTICE_NEW, this);
		}

		public long getNotice_new() {
			return getCount(TYPE_NOTICE_NEW);
		}

		public void setNotice_new(long notice_new) {
			setCount(TYPE_NOTICE_NEW, notice_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_NOTICE_NEW, this);
		}

		public long getMy_new() {
			return getCount(TYPE_MY_NEW);
		}

		public void setMy_new(long my_new) {
			setCount(TYPE_MY_NEW, my_new);
			PushMsgManager.getInstance().notifyPushEvent(TYPE_MY_NEW, this);
		}
		
	}
}
