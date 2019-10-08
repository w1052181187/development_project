package com.chengning.fenghuo.widget;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.activity.TuwenCommentListActivity;
import com.chengning.fenghuo.data.bean.ArticlesPicBean;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.util.Common;

public class PhotoPageCommentBottom {
	private Activity mContext;
	
	private View mCommentLayout;
	private View mInput;
	private View mComment;
	private TextView mCommentCount;
	
	private BaseArticlesBean mArticleBean = new BaseArticlesBean();
	
	public PhotoPageCommentBottom(Activity activity, final View root){
		this.mContext = activity;
		mCommentLayout = activity.findViewById(R.id.photopage_comment_bottom_layout);
		mInput = mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_input);
		mComment = mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_comment);
		mCommentCount = (TextView) mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_comment_count);
		
	}
	
	public void setAid(final String aid){
		mInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentInputDialog dialog = new CommentInputDialog();
				dialog.setBean((BaseFragmentActivity)mContext, mArticleBean, null);
				dialog.show(((BaseFragmentActivity)mContext).getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
			}
		});
		mComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TuwenCommentListActivity.launch(mContext, mArticleBean);
			}
		});
	}
	
	public void setData(ArticlesPicBean bean){
		
		mArticleBean.setTid(bean.getTid());
		mArticleBean.setTitle(bean.getTitle());
		mArticleBean.setReplys(Common.strToInt(bean.getReplys()));
		
		if(bean != null){
			mCommentLayout.setVisibility(View.VISIBLE);
			if (!bean.getReplys().equals("0")) {
				mCommentCount.setText(String.valueOf(bean.getReplys()));
				mCommentCount.setVisibility(View.VISIBLE);
			} else {
				mCommentCount.setVisibility(View.GONE);
			}
		}
	}

}
