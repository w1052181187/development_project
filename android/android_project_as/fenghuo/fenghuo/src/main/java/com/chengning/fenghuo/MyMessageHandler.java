package com.chengning.fenghuo;

import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.chengning.fenghuo.data.access.ChatConversationDA;
import com.chengning.fenghuo.data.access.ChatMessageDA;
import com.chengning.fenghuo.data.bean.ChatConversationBean;
import com.chengning.fenghuo.data.bean.ChatMessageBean;
import com.chengning.fenghuo.util.ChatClientManager;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.NotificationUtils;

public class MyMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

	private Context context;
	int count = 0;

	public MyMessageHandler(Context context) {
		this.context = context;
	}

	@Override
	public void onMessage(AVIMTypedMessage message,
			AVIMConversation conversation, AVIMClient client) {
		String clientID = "";
		//TODO 未读数量
		ChatConversationBean conBean = ChatConversationDA.getInst(context).queryConversation(App.getInst().getUserInfoBean().getUid(), conversation.getConversationId());
		count = null == conBean ? 0 : conBean.getMsg_no_readed_num();
		try {
			clientID = ChatClientManager.getInst().getClientId();
			if (client.getClientId().equals(clientID)) {

				// 过滤掉自己发的消息
				if (!message.getFrom().equals(clientID)) {
					count ++;
					ChatConversationBean mConversation = ChatConversationBean
							.convertToBean(conversation.getConversationId());
					mConversation.setMsg_no_readed_num(count);
					ChatMessageBean msgBean = ChatMessageBean
							.convertToMessageBean(message);
					ChatMessageDA.getInst(context).creatMsgList(msgBean);
					ChatConversationDA.getInst(context).updateLatestMessage(
							mConversation, msgBean);
					sendBroast(message, mConversation);
					if (NotificationUtils.isShowNotification(mConversation
							.getCon_id())) {
						sendNotification(message, mConversation);
					}
					// 推送收到的消息留言+1
					PushMsgManager.getInstance().getPushUserDataBean().setNewpm(PushMsgManager.getInstance().getPushUserDataBean().getNewpm() + 1);
				}
			} else {
				client.close(null);
			}
		} catch (IllegalStateException e) {
			client.close(null);
		}
	}

	/**
	 * 因为没有 db，所以暂时先把消息广播出去，由接收方自己处理 稍后应该加入 db
	 * 
	 * @param message
	 * @param mConversation
	 */
	private void sendBroast(AVIMTypedMessage message,
			ChatConversationBean mConversation) {
		Intent intent = new Intent(context.getPackageName()
				+ "ACTION_RECEIVE_MSG");
		intent.putExtra("msg", message);
		intent.putExtra("conversationId", mConversation.getCon_id());
		context.sendBroadcast(intent);
	}

	private void sendNotification(AVIMTypedMessage message,
			ChatConversationBean mConversation) {
		//留言功能是否开启
		if (!Common.isTrue(SettingManager.getInst().getSettingBean().getConfig_push_is_pm()) || !PushMsgManager.isInPushTime()) {
			return;
		}
		NotificationUtils.showNotification(context, message, mConversation);
	}

}
