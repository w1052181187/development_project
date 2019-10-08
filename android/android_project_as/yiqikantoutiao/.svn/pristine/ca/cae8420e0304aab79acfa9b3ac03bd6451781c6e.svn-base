package com.chengning.yiqikantoutiao.util;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.activity.MyprofileActivity;
import com.chengning.yiqikantoutiao.data.bean.BaseArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.CommentItemBean;
import com.chengning.yiqikantoutiao.util.ArticleManagerUtils.DataStateListener;
import com.chengning.yiqikantoutiao.util.ArticleManagerUtils.DeleteListener;
import com.chengning.yiqikantoutiao.widget.ArticleCommentClickDialog;
import com.chengning.yiqikantoutiao.widget.CommentInputDialog;

/**
 * 文章或者讨论评论部分相关的监听（用户名、头像、回复、赞、举报、删除等等）
 * @author Administrator
 *
 *
 */
public class ArticleCommentListeners {

	public static class CommentOnClickListener implements OnClickListener {

		private Activity activity;

		public CommentOnClickListener(Activity context) {
			this.activity = context;
		}

		@Override
		public void onClick(View v) {
//				if (LoginManager.getInst().checkLoginWithNotice(mContext)) {
			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
			CommentInputDialog dialog = new CommentInputDialog();
			dialog.setBean((BaseFragmentActivity)activity, bean, Consts.ArticleCommentType.REPLY);
			dialog.setIsThreeReply(true);
			dialog.show(((BaseFragmentActivity)activity).getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
//				}
		}

	}

	public static class ReportOnClickListener implements OnClickListener {

		private Activity activity;
		private ArticleManagerUtils mUtils;

		public ReportOnClickListener(Activity context, ArticleManagerUtils utils) {
			this.activity = context;
			this.mUtils = utils;
		}

		@Override
		public void onClick(View v) {
			CommentItemBean bean = (CommentItemBean) v.getTag();
			mUtils.getReport((BaseFragmentActivity) activity, bean.getTid());
		}

	}

	public static class NameOnClickListener implements OnClickListener {

		private Activity activity;

		public NameOnClickListener(Activity context) {
			this.activity = context;
		}
		@Override
		public void onClick(View v) {
			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
			MyprofileActivity.launchByUid(activity, bean.getUid(),
					"more");
		}
	}

	public static class LikeOnClickListener implements OnClickListener {

		private DataStateListener mState;
		private Activity activity;
		private ArticleManagerUtils mUtils;

		public LikeOnClickListener(Activity context, ArticleManagerUtils utils, DataStateListener state) {
			this.activity = context;
			this.mUtils = utils;
			this.mState = state;
		}

		@Override
		public void onClick(View v) {
//			if (LoginManager.getInst().checkLoginWithNotice(activity)) {
			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
			mUtils.doLike(activity, bean, mState);
//			}
		}

	}

	public static class NotLikeOnClickListener implements OnClickListener {

		private Activity activity;
		private ArticleManagerUtils mUtils;
		private DataStateListener mState;

		public NotLikeOnClickListener(Activity context, ArticleManagerUtils utils, DataStateListener state) {
			this.activity = context;
			this.mUtils = utils;
			this.mState = state;
		}

		@Override
		public void onClick(View v) {
//			if (LoginManager.getInst().checkLoginWithNotice(activity)) {
			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
			mUtils.doNotLike(activity, bean, mState);
//			}
		}

	}

	/**
	 * 文章评论长按
	 * @author Administrator
	 *
	 */
	public static class ArticleCommentOnLongClickListener implements OnLongClickListener {

		private Activity activity;
		private DeleteListener listener;
		private boolean isOnlyCopy = false;
		private String copyContent;

		public ArticleCommentOnLongClickListener(Activity activity,
                                                 DeleteListener listener, String copyContent, boolean isOnlyCopy) {
			init(activity,listener,copyContent,isOnlyCopy);
		}

		public ArticleCommentOnLongClickListener(Activity activity,
												 DeleteListener listener, String copyContent) {
			init(activity,listener,copyContent,false);
		}

		private void init(Activity activity,
                          DeleteListener listener, String copyContent, boolean isOnlyCopy) {
			this.activity = activity;
			this.listener = listener;
			this.copyContent = copyContent;
			this.isOnlyCopy = isOnlyCopy;
		}

		@Override
		public boolean onLongClick(View v) {
			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();

			ArticleCommentClickDialog commentClickDialog = new ArticleCommentClickDialog();
			commentClickDialog.setData(activity, bean, copyContent, false, listener, isOnlyCopy);
			commentClickDialog.show(((BaseFragmentActivity) activity).getSupportFragmentManager(),
					ArticleCommentClickDialog.class.getSimpleName());
			return true;
		}

	}
	
}
