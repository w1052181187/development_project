package com.chengning.fenghuo;

import com.chengning.fenghuo.activity.ChatActivity;
import com.chengning.fenghuo.util.ChatClientManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (ChatClientManager.getInst().getmClient() == null) {
			gotoLoginActivity(context);
		} else {
			String conversationId = intent
					.getStringExtra(Consts.CONVERSATION_ID);
			if (!TextUtils.isEmpty(conversationId)) {
				gotoSingleChatActivity(context, intent);
			}
		}
	}

	/**
	 * 如果 app 上下文已经缺失，则跳转到登陆页面，走重新登陆的流程
	 * 
	 * @param context
	 */
	private void gotoLoginActivity(Context context) {
		// Intent startActivityIntent = new Intent(context,
		// AVLoginActivity.class);
		// startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(startActivityIntent);
	}

	/**
	 * 跳转至广场页面
	 * 
	 * @param context
	 * @param intent
	 */
	private void gotoSquareActivity(Context context, Intent intent) {
		// Intent startActivityIntent = new Intent(context,
		// AVSquareActivity.class);
		// startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivityIntent.putExtra(Constants.CONVERSATION_ID,
		// intent.getStringExtra(Constants.CONVERSATION_ID));
		// context.startActivity(startActivityIntent);
	}

	/**
	 * 跳转至单聊页面
	 * 
	 * @param context
	 * @param intent
	 */
	private void gotoSingleChatActivity(Context context, Intent intent) {

		Intent startActivityIntent = new Intent(context, ChatActivity.class);
		startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityIntent.putExtra(Consts.CHAT_ENTRY, Consts.ChatAction.LIST);
		startActivityIntent.putExtra(Consts.CONVERSATION_ID,
				intent.getStringExtra(Consts.CONVERSATION_ID));
		context.startActivity(startActivityIntent);
	}
}
