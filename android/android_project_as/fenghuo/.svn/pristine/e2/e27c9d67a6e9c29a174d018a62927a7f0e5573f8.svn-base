package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.util.ChatClientManager;
import com.google.gson.Gson;

public class ChatConversationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7627299051705175548L;
	
	private String uid;
	private String con_id;
	private String creator;
	private String con_name;
	private String con_face;
	private String role_name;
//	private String con_newest_msg;
	private ArrayList<String> con_members;
	private int msg_no_readed_num;
//	private String con_newest_time;
	private ChatMessageBean newest_msg;
//	private String attr;
	private String update_time;
	
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getCon_name() {
		return con_name;
	}
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}
	public String getCon_face() {
		return con_face;
	}
	public void setCon_face(String con_face) {
		this.con_face = con_face;
	}
	public ArrayList<String> getCon_members() {
		return con_members;
	}
	public void setCon_members(ArrayList<String> con_members) {
		this.con_members = con_members;
	}
	public int getMsg_no_readed_num() {
		return msg_no_readed_num;
	}
	public void setMsg_no_readed_num(int msg_no_readed_num) {
		this.msg_no_readed_num = msg_no_readed_num;
	}
	
	public static ChatConversationBean convertToBean(String conId) {
		AVIMConversation  conversation = ChatClientManager.getInst().getmClient().getConversation(conId);
		ChatConversationBean bean = new ChatConversationBean();
		JSONObject attr = new JSONObject();
		String cAttr = (String) conversation.getAttribute("creat_bean");
		String tAttr = (String) conversation.getAttribute("target_bean");
		String str = (String) (ChatClientManager.getInst().getClientId().equals(conversation.getCreator()) ? tAttr : cAttr);
		
		Gson gson = new Gson();
		ConversationBean bean1 =  gson .fromJson(str, ConversationBean.class);
		if (null != bean1) {
			bean.setCon_face(bean1.getFace());
			bean.setCon_name(bean1.getNickname());
		}
		
		ArrayList<String> mList = new ArrayList<String>();
		List<String> list = conversation.getMembers();
		mList.addAll(list);
		bean.setCon_members(mList);
		bean.setCon_id(conId);
		bean.setCreator(conversation.getCreator());
		bean.setUid(App.getInst().getUserInfoBean().getUid());
		Date date = null == conversation.getLastMessageAt() ? conversation.getCreatedAt() : conversation.getLastMessageAt();
//		Date date = conversation.getUpdatedAt();
		bean.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		
		return bean;
		
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public ChatMessageBean getNewest_msg() {
		return newest_msg;
	}
	public void setNewest_msg(ChatMessageBean newest_msg) {
		this.newest_msg = newest_msg;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
}
