package com.chengning.yiqikantoutiao.event;

import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.data.bean.CommentItemBean;

public class CommentSuccessEvent {

	private CommentItemBean mBean;
	private Consts.ArticleCommentType type;

	public CommentSuccessEvent(CommentItemBean bean) {
		this.mBean = bean;
	}
	public CommentSuccessEvent(CommentItemBean bean, Consts.ArticleCommentType type) {
		this.mBean = bean;
		this.type = type;
	}

	public CommentItemBean getmBean() {
		return mBean;
	}

	public void setmBean(CommentItemBean mBean) {
		this.mBean = mBean;
	}

	public Consts.ArticleCommentType getType() {
		return type;
	}

	public void setType(Consts.ArticleCommentType type) {
		this.type = type;
	}
}
