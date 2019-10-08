package com.chengning.fenghuo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.chengning.fenghuo.data.access.ChatConversationDA;
import com.chengning.fenghuo.data.access.ChatMessageDA;
import com.chengning.fenghuo.data.bean.ChatConversationBean;
import com.chengning.fenghuo.data.bean.ChatMessageBean;

public class MyConversationHandler extends AVIMConversationEventHandler {

	@Override
	public void onInvited(AVIMClient arg0, AVIMConversation arg1, String arg2) {

	}

	@Override
	public void onKicked(AVIMClient arg0, AVIMConversation arg1, String arg2) {

	}

	@Override
	public void onMemberJoined(AVIMClient arg0, AVIMConversation arg1,
			List<String> arg2, String arg3) {

	}

	@Override
	public void onMemberLeft(AVIMClient arg0, AVIMConversation arg1,
			List<String> arg2, String arg3) {

	}

	@Override
	public void onOfflineMessagesUnread(AVIMClient client,
			final AVIMConversation conversation, final int unreadCount) {
		if (unreadCount > 0) {
			// 可以根据 readCount 更新 UI
			AVIMConversation con = client.getConversation(conversation
					.getConversationId());
			client.getQuery().findInBackground(
					new AVIMConversationQueryCallback() {

						@Override
						public void done(List<AVIMConversation> arg0,
								AVIMException arg1) {
							List list = arg0;
							for (AVIMConversation avimConversation : arg0) {
								if (avimConversation.getConversationId()
										.equals(conversation
												.getConversationId())) {
									final ChatConversationBean bean = ChatConversationBean
											.convertToBean(conversation
													.getConversationId());
									bean.setMsg_no_readed_num(unreadCount);
									avimConversation
											.getLastMessage(new AVIMSingleMessageQueryCallback() {

												@Override
												public void done(
														AVIMMessage arg0,
														AVIMException arg1) {
													ChatConversationDA .getInst(App.getInst() .getBaseContext())
															.updateLatestMessage( bean, ChatMessageBean
																			.convertToMessageBean((AVIMTypedMessage) arg0));
												}
											});
									break;
								}
							}
						}
					});
			// final ChatConversationBean bean =
			// ChatConversationBean.convertToBean(conversation.getConversationId());
			// bean.setMsg_no_readed_num(unreadCount);
			// 也可以拉取对应的未读消息
			conversation.queryMessages(unreadCount,
					new AVIMMessagesQueryCallback() {

						@Override
						public void done(List<AVIMMessage> list, AVIMException e) {
							if (e == null) {
								// 获得对应的未读消息
								List<ChatMessageBean> messageBeans = new ArrayList<ChatMessageBean>();
								for (AVIMMessage message : list) {
									messageBeans.add(ChatMessageBean
											.convertToMessageBean((AVIMTypedMessage) message));
								}
								Context context = App.getInst()
										.getBaseContext();
								ChatMessageDA.getInst(context).insertList(
										messageBeans);
								// ChatConversationDA.getInst(context).updateLatestMessage(bean,
								// messageBeans.get(unreadCount - 1));
							}
						}
					});
		}

		super.onOfflineMessagesUnread(client, conversation, unreadCount);
	}

}
