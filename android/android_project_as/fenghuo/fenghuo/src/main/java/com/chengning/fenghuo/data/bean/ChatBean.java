package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatBean implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1888279499930424000L;
	int pmid;
	String msgfrom;
	private int msgfromid;
	String msgnickname;
	String msgto;
	private int msgtoid;
	String tonickname;
	String face;
	String dateline; 
	List<String> message;
	ArrayList<ChatImageBean> image_list; 
	String image_list_json;
	public String getImage_list_json() {
		return image_list_json;
	}
	public void setImage_list_json(String image_list_json) {
		this.image_list_json = image_list_json;
	}
	int message_state;
	
	public int getPmid() {
		return pmid;
	}
	public void setPmid(int pmid) {
		this.pmid = pmid;
	}
	public String getMsgfrom() {
		return msgfrom;
	}
	public void setMsgfrom(String msgfrom) {
		this.msgfrom = msgfrom;
	}
	public String getMsgnickname() {
		return msgnickname;
	}
	public void setMsgnickname(String msgnickname) {
		this.msgnickname = msgnickname;
	}
	public String getMsgto() {
		return msgto;
	}
	public void setMsgto(String msgto) {
		this.msgto = msgto;
	}
	public String getTonickname() {
		return tonickname;
	}
	public void setTonickname(String tonickname) {
		this.tonickname = tonickname;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	} 
	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	} 
	public ArrayList<ChatImageBean> getImage_list() {
		return image_list;
	}
	public void setImage_list(ArrayList<ChatImageBean> image_list) {
		this.image_list = image_list;
	}
	public int getMessage_state() {
		return message_state;
	}
	public void setMessage_state(int message_state) {
		this.message_state = message_state;
	}
	
	
	@Override
	public ChatBean clone(){
		// TODO Auto-generated method stub
		ChatBean bean = null;
		try{  
			bean = (ChatBean) super.clone(); 
        }catch(CloneNotSupportedException e){  
            e.printStackTrace();  
        } 
		return bean;
	}
	public int getMsgfromid() {
		return msgfromid;
	}
	public void setMsgfromid(int msgfromid) {
		this.msgfromid = msgfromid;
	}
	public int getMsgtoid() {
		return msgtoid;
	}
	public void setMsgtoid(int msgtoid) {
		this.msgtoid = msgtoid;
	}

}
