package com.chengning.fenghuovideo.util;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.chengning.common.base.BaseFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.CommentItemBean;
import com.chengning.fenghuovideo.util.ArticleManagerUtils.DeleteListener;
import com.chengning.fenghuovideo.util.ArticleManagerUtils.DataStateListener;
import com.chengning.fenghuovideo.widget.ArticleCommentClickDialog;
import com.chengning.fenghuovideo.widget.CommentInputDialog;

/**
 * 文章或者讨论评论部分相关的监听（用户名、头像、回复、赞、举报、删除等等）
 * @author Administrator
 *
 * @param <T>
 */
public class ArticleCommentListeners<T> {

	public static class CommentOnClickListener implements OnClickListener{

		private BaseFragmentActivity activity;

		public CommentOnClickListener(BaseFragmentActivity context) {
			this.activity = context;
		}

		@Override
		public void onClick(View v) {
//				if (LoginManager.getInst().checkLoginWithNotice(mContext)) {
			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
			CommentInputDialog dialog = new CommentInputDialog();
			dialog.setBean(bean, Consts.ArticleCommentType.REPLY);
			dialog.setIsThreeReply(true);
			dialog.showAllowingStateLoss(activity, activity.getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
//				}
		}

	}

	public static class ReportOnClickListener implements OnClickListener{

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

	public static class LikeOnClickListener implements OnClickListener{

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

	public static class NotLikeOnClickListener implements OnClickListener{

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
	
	public static class NameOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO 跳到用户界面
//			BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
//			UserInfoActivity.launch(mContext, bean.getNickname());
		}
	}

	/**
	 * 文章评论长按
	 * @author Administrator
	 *
	 */
	public static class ArticleCommentOnLongClickListener implements OnLongClickListener{

		private BaseFragmentActivity activity;
		private DeleteListener listener;
		private boolean isOnlyCopy = false;
		private String copyContent;

		public ArticleCommentOnLongClickListener(BaseFragmentActivity activity,
												 DeleteListener listener, String copyContent, boolean isOnlyCopy) {
			init(activity,listener,copyContent,isOnlyCopy);
		}

		public ArticleCommentOnLongClickListener(BaseFragmentActivity activity,
												 DeleteListener listener, String copyContent) {
			init(activity,listener,copyContent,false);
		}

		private void init(BaseFragmentActivity activity,
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
			commentClickDialog.setData(bean, copyContent, false, listener, isOnlyCopy);
			commentClickDialog.showAllowingStateLoss(activity, activity.getSupportFragmentManager(),
					ArticleCommentClickDialog.class.getSimpleName());
			return true;
		}

	}
	
}
