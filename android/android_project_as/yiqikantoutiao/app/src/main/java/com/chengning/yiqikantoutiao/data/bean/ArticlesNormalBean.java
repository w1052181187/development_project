package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ArticlesNormalBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3843075291742129258L;
	String tid;
	String uid;
	String username;
	String title;
	ArrayList<String> content;
	String roottid;
	int replys;
	int forwards;
	int digcounts;
	String totid;
	String dateline;
	String from;
	String type;
	int hitcounts;
	int sharecounts;
	int is_dig;
	int is_favor;
	ArrayList<ImageBean> image_list;
	int is_subscribe;
	String nickname;
	String face;
	String rolename;
	String link;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}

	public String getRoottid() {
		return roottid;
	}

	public void setRoottid(String roottid) {
		this.roottid = roottid;
	}

	public int getReplys() {
		return replys;
	}

	public void setReplys(int replys) {
		this.replys = replys;
	}

	public int getForwards() {
		return forwards;
	}

	public void setForwards(int forwards) {
		this.forwards = forwards;
	}

	public int getDigcounts() {
		return digcounts;
	}

	public void setDigcounts(int digcounts) {
		this.digcounts = digcounts;
	}

	public String getTotid() {
		return totid;
	}

	public void setTotid(String totid) {
		this.totid = totid;
	}

	public String getDateline() {
		return dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHitcounts() {
		return hitcounts;
	}

	public void setHitcounts(int hitcounts) {
		this.hitcounts = hitcounts;
	}

	public int getSharecounts() {
		return sharecounts;
	}

	public void setSharecounts(int sharecounts) {
		this.sharecounts = sharecounts;
	}

	public int getIs_dig() {
		return is_dig;
	}

	public void setIs_dig(int is_dig) {
		this.is_dig = is_dig;
	}

	public int getIs_favor() {
		return is_favor;
	}

	public void setIs_favor(int is_favor) {
		this.is_favor = is_favor;
	}

	public ArrayList<ImageBean> getImage_list() {
		return image_list;
	}

	public void setImage_list(ArrayList<ImageBean> image_list) {
		this.image_list = image_list;
	}

	public int getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(int is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
