package com.cmstop.jstt.beans.data;

public class PushLCBean {
	private String title;
	private String alert;
	private Extension extension;
	private String badge;

	public static class Extension {
		private String note;
		private int type;
		private int tid;
		private int t_type;
		
		//文章类型 （1普通文章2图文 3视频 4自定义）
		private int arc_type;
		private String uid;
		private String tz_url;

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

		public int getT_type() {
			return t_type;
		}

		public void setT_type(int t_type) {
			this.t_type = t_type;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getTz_url() {
			return tz_url;
		}

		public void setTz_url(String tz_url) {
			this.tz_url = tz_url;
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
