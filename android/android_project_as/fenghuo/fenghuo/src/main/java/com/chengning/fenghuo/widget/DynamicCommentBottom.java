package com.chengning.fenghuo.widget;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.ArticleManagerUtils.DataStateListener;
import com.chengning.fenghuo.util.Common;

public class DynamicCommentBottom {
	private FragmentActivity mContext;
	
	private TextView mInput;
	private ImageButton mLike;
	private TextView mCommentCount;
	private final ImageButton mComment;
	private ImageButton mShare;

	private BaseArticlesBean mArticleBean;
	private ArticleManagerUtils mArticleManagerUtils;
	private ArticleManagerUtils.DataStateListener mLikeState;
	private DataStateListener mDetailLikeState;

	public DynamicCommentBottom(FragmentActivity activity, final View root){
		this.mContext = activity;
		mInput = (TextView) root.findViewById(R.id.article_comment_bottom_toolbar_input);
		mLike = (ImageButton) root.findViewById(R.id.article_comment_bottom_toolbar_fav);
		mCommentCount = (TextView) root.findViewById(R.id.article_comment_bottom_toolbar_comment_count);
		mComment = (ImageButton) root.findViewById(R.id.article_comment_bottom_toolbar_comment);
		mShare = (ImageButton) root.findViewById(R.id.article_comment_bottom_toolbar_share);
		init();
	}
	
	private void init() {
		mComment.setVisibility(View.GONE);
		mCommentCount.setVisibility(View.GONE);
		mInput.setText("回复楼主");
		mLike.setImageResource(R.drawable.dynamic_bottom_like_state);
		
		mLikeState = new DataStateListener() {
			
			@Override
			public void success(Object... objects) {
				mLike.setSelected(true);
				mLike.setEnabled(true);
				mDetailLikeState.success(objects);
			}
			
			@Override
			public void init() {
				mLike.setEnabled(false);
				mDetailLikeState.init();
			}
			
			@Override
			public void failure() {
				mLike.setEnabled(true);
				mDetailLikeState.failure();
			}
		};

		mArticleManagerUtils = new ArticleManagerUtils();
	}

	public void setData(BaseArticlesBean bean){
		this.mArticleBean = bean;
		if (null == mArticleBean) {
			return;
		}
		mLike.setSelected(Common.isTrue(mArticleBean.getIs_dig()));
		
		mInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null != mArticleBean && LoginManager.getInst().checkLoginWithNotice(mContext)) {
					DynamicCommentInputDialog dynamicDialog = new DynamicCommentInputDialog();
					dynamicDialog.setBean(mArticleBean);
					dynamicDialog.show(mContext.getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
					
				}
			}
		});
		mLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!Common.isTrue(mArticleBean.getIs_dig())) {
					mArticleManagerUtils.doLike(mContext, mArticleBean, mLikeState);
				} else {
					mArticleManagerUtils.cancleLike(mContext, mArticleBean, mLikeState);
				}
			}
		});
		
		mShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					
					PicArticleDialog dialog = new PicArticleDialog();
					dialog.setBean(mArticleBean);					
					dialog.show(mContext.getSupportFragmentManager(),
							PicArticleDialog.class.getSimpleName());
					
				}
				
			}
		});
	}
	
	public ImageButton getLikeButton(){
		return mLike;
	}
	
	public void setLikeState(ArticleManagerUtils.DataStateListener likeState){
		mDetailLikeState = likeState;
	}
	
}
