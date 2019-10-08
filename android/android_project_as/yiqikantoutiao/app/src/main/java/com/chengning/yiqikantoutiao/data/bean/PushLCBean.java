package com.chengning.yiqikantoutiao.data.bean;

import java.io.Serializable;

public class PushLCBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1069685118772336289L;
	private String title;
	private String alert;
	private Extension extension;
	private String badge;

	public static class Extension implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4524545590938135264L;
		private String note;
		private int type;
		private int tid;
		private String uid;
		//文章类型 （1普通文章2图文 3视频 4自定义）
		private int arc_type;

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public int getTid() {
			return tid;
		}

		public void setTid(int tid) {
			this.tid = tid;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public int getArc_type() {
			return arc_type;
		}

		public void setArc_type(int arc_type) {
			this.arc_type = arc_type;
		}

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Extension getExtension() {
		return extension;
	}

	public void setExtension(Extension extension) {
		this.extension = extension;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

}
