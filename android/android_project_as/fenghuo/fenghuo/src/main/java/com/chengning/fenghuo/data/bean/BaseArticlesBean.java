package com.chengning.fenghuo.data.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.chengning.fenghuo.util.Common;

public class BaseArticlesBean<T> extends BaseChannelItemBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2806916267792115168L;
	//收藏id
	String id;
	String tid;
	String uid;
	String username;
	String title;
	String roottid;
	int replys;
	int forwards;
	int digcounts;
	private int badcounts;
	String totid;
	String touid;
	String dateline;
	
	//文章详情来源
	String from;
//	int type;
	String type;
	int hitcounts;
	int sharecounts;
	int is_dig;
	private int is_bad;
	int is_favor;
	String nickname;
	String face;
	String role_name;
	String link;
	T image_list;
	//TODO 服务器增加
	int news_show_type;
	String nav;
	String local_nav;
	String more;
	String video_photo;
	String video_play;
	private String video_html;
	private ArrayList<CommentItemBean> reply_list;
	private String content_type;
//	private int show_type;
	
	//本地
	private int isReplyClicked = Common.FALSE;
	private int local_del_flag = Common.FALSE;

	//列表来源（由于from是数据库关键字，数据储存时不能使用）
	private String list_from;
	
	public T getImage_list(){
		return image_list;
	}

	public void setImage_list(T t){
		this.image_list = t;
	}

	public String getTouid() {
		return touid;
	}

	public void setTouid(String touid) {
		this.touid = touid;
	}

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

//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}

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

	public String getLink() {
		return link;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<CommentItemBean> getReply_list() {
		return reply_list;
	}

	public void setReply_list(ArrayList<CommentItemBean> reply_list) {
		this.reply_list = reply_list;
	}

	public int getNews_show_type() {
		return news_show_type;
	}

	public void setNews_show_type(int news_show_type) {
		this.news_show_type = news_show_type;
	}

	public String getNav() {
		return nav;
	}

	public void setNav(String nav) {
		this.nav = nav;
	}

	public String getLocal_nav() {
		return local_nav;
	}

	public void setLocal_nav(String local_nav) {
		this.local_nav = local_nav;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVideo_photo() {
		return video_photo;
	}

	public void setVideo_photo(String video_photo) {
		this.video_photo = video_photo;
	}

	public String getVideo_play() {
		return video_play;
	}

	public void setVideo_play(String video_play) {
		this.video_play = video_play;
	}

	public int getIsReplyClicked() {
		return isReplyClicked;
	}

	public void setIsReplyClicked(int isReplyClicked) {
		this.isReplyClicked = isReplyClicked;
	}

	public String getList_from() {
		return list_from;
	}

	public void setList_from(String list_from) {
		this.list_from = list_from;
	}

	public String getVideo_html() {
		return video_html;
	}

	public void setVideo_html(String video_html) {
		this.video_html = video_html;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public int getBadcounts() {
		return badcounts;
	}

	public void setBadcounts(int badcounts) {
		this.badcounts = badcounts;
	}

	public int getIs_bad() {
		return is_bad;
	}

	public void setIs_bad(int is_bad) {
		this.is_bad = is_bad;
	}

	public int getLocal_del_flag() {
		return local_del_flag;
	}

	public void setLocal_del_flag(int local_del_flag) {
		this.local_del_flag = local_del_flag;
	}

//	public int getShow_type() {
//		return show_type;
//	}
//
//	public void setShow_type(int show_type) {
//		this.show_type = show_type;
//	}

}
