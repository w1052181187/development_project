package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.app.Activity;
import android.content.Context;

import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.data.access.ChatConversationDA;
import com.chengning.fenghuo.util.Common;

public class ChatMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6395475646850698220L;

	private String uid;
	private String con_id;
	private String msg_id;
	private String msg_from;
	private String time;
	private String content;
	private int msg_type;
	private String face;
	private int msg_status;
	
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getMsg_from() {
		return msg_from;
	}
	public void setMsg_from(String from) {
		this.msg_from = from;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public int getMsg_status() {
		return msg_status;
	}
	public void setMsg_status(int msg_status) {
		this.msg_status = msg_status;
	}
	
	public static ChatMessageBean convertToMessageBean(AVIMTypedMessage message){
		Map<String, Object> att = null;
		ChatMessageBean bean = new ChatMessageBean();
		bean.setUid(App.getInst().getUserInfoBean().getUid());
		bean.setCon_id(message.getConversationId());
		bean.setMsg_id(message.getMessageId());
		bean.setMsg_status(message.getMessageStatus().getStatusCode());
		bean.setMsg_from(message.getFrom());
		bean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(message.getTimestamp())));
		if  (message instanceof AVIMTextMessage) {
			AVIMTextMessage textMessage = (AVIMTextMessage)message;
			bean.setContent(textMessage.getText());
			bean.setMsg_type(AVIMMessageType.TEXT_MESSAGE_TYPE);
			att = textMessage.getAttrs();
		} else if  (message instanceof AVIMImageMessage) {
			AVIMImageMessage imgMessage = (AVIMImageMessage)message;
			bean.setContent(imgMessage.getFileUrl());
			bean.setMsg_type(AVIMMessageType.IMAGE_MESSAGE_TYPE);
			att = imgMessage.getAttrs();
			
		}
		bean.setFace(null == att ? "" : (String)att.get("face"));
		return bean;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public static ChatMessageBean initMessageBean(
			ChatConversationBean mConversation, AVIMTypedMessage message) {
		Map<String, Object> att = null;
		ChatMessageBean bean = new ChatMessageBean();
		bean.setUid(App.getInst().getUserInfoBean().getUid());
		bean.setCon_id(mConversation.getCon_id());
		bean.setMsg_id(message.getMessageId());
		bean.setMsg_status(Consts.MSG_STATUS_SENDING);
		bean.setMsg_from(message.getFrom());
		bean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
		if  (message instanceof AVIMTextMessage) {
			AVIMTextMessage textMessage = (AVIMTextMessage)message;
			bean.setContent(textMessage.getText());
			bean.setMsg_type(AVIMMessageType.TEXT_MESSAGE_TYPE);
			att = textMessage.getAttrs();
		} else if  (message instanceof AVIMImageMessage) {
			AVIMImageMessage imgMessage = (AVIMImageMessage)message;
			bean.setContent(imgMessage.getFileUrl());
			bean.setMsg_type(AVIMMessageType.IMAGE_MESSAGE_TYPE);
			att = imgMessage.getAttrs();
			
		}
		bean.setFace(null == att ? "" : (String)att.get("face"));
		return bean;
	}
}
