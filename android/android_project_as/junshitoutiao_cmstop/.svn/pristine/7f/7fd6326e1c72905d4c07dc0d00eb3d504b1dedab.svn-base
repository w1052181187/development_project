package com.cmstop.jstt.views;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.app.ActivityInfo.ActivityState;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.base.IBaseActivity;
import com.cmstop.jstt.Const.LikeAction;
import com.cmstop.jstt.R;
import com.cmstop.jstt.activity.ArticleCommentActivity;
import com.cmstop.jstt.beans.data.ArticlesPicBean;
import com.cmstop.jstt.beans.data.GoodBean;
import com.cmstop.jstt.utils.ArticleManagerUtils;
import com.cmstop.jstt.utils.ArticleManagerUtils.LikeState;
import com.cmstop.jstt.utils.CommentCheckUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PhotoPageCommentBottom {
	private Activity mContext;
	
	private View mCommentLayout;
	private View mInput;
	private View mComment;
	private TextView mCommentCount;
	private View mLike;
	private View mTread;

	private String aid;
	private TextView mLikeCount;

	private TextView mTreadCount;

	private ArticlesPicBean picBean;

	private LikeState mLikeState;

	private ArticleManagerUtils mArticleManagerUtils;

	public PhotoPageCommentBottom(Activity activity, final View root){
		this.mContext = activity;
		mCommentLayout = activity.findViewById(R.id.photopage_comment_bottom_layout);
		mInput = mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_input);
		mComment = mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_comment);
		mCommentCount = (TextView) mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_comment_count);
		
		mLike = mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_like);
		mLikeCount = (TextView) mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_like_count);
		mTread = mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_tread);
		mTreadCount = (TextView) mCommentLayout.findViewById(R.id.photopage_comment_bottom_toolbar_tread_count);
		
	}
	
	public void setAid(final String aid){
		this.aid = aid;
		mInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(((IBaseActivity)mContext).getActivityInfo().getActivityState() == ActivityState.SaveInstanceStated){
					return;
				}
//				if(CommentCheckUtil.checkLoginNotNeededOfComment(mContext)){
					CommentInputDialog dialog = new CommentInputDialog();
					dialog.setAid(aid);
					dialog.showAllowingStateLoss((BaseFragmentActivity)mContext, ((FragmentActivity)mContext).getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
//				}
			}
		});
		mComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArticleCommentActivity.launch(mContext, aid);
			}
		});
		
		mLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mArticleManagerUtils.doLikeByHttp(mContext, aid, LikeAction.GOOD);
			}
		});
		
		mTread.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mArticleManagerUtils.doLikeByHttp(mContext, aid, LikeAction.BAD);
			}
		});
	}
	
	public void setData(ArticlesPicBean bean){
		this.picBean = bean;
		
		if(bean != null){
			mCommentLayout.setVisibility(View.VISIBLE);
			
			setLikeAndTreadCount(bean);
			
			if (0 != bean.getComments().getCount()) {
				mCommentCount.setText(String.valueOf(bean.getComments().getCount()));
				mCommentCount.setVisibility(View.VISIBLE);
			} else {
				mCommentCount.setVisibility(View.GONE);
			}
			
		}
		initLikeOrThread();
	}

	/**
	 * 初始化顶和踩
	 */
	private void initLikeOrThread() {
		mLikeState = new ArticleManagerUtils.LikeState(){

			@Override
			public void init() {
				mLike.setEnabled(false);
				mTread.setEnabled(false);
			}

			@Override
			public void success(String data) {
				mLike.setEnabled(true);
				mTread.setEnabled(true);
				Gson g = new Gson(); 
           	 	GoodBean b = g.fromJson(data,new TypeToken<GoodBean>(){}.getType()); 
				if(picBean != null){
               	 	picBean.setGoodpost(b.goodpost);
               	 	picBean.setBadpost(b.badpost);
               	 	setLikeAndTreadCount(picBean);
           	 	}
			}

			@Override
			public void failure() {
				mLike.setEnabled(true);
				mTread.setEnabled(true);
			}
			
		};
		mArticleManagerUtils = new ArticleManagerUtils();
		mArticleManagerUtils.setState(mLikeState);
	}
	
	public void setLikeAndTreadCount(ArticlesPicBean bean){
		if (0 != bean.getGoodpost()) {
			mLikeCount.setText(String.valueOf(bean.getGoodpost()));
			mLikeCount.setVisibility(View.VISIBLE);
		} else {
			mLikeCount.setVisibility(View.GONE);
		}
		if (0 != bean.getBadpost()) {
			mTreadCount.setText(String.valueOf(bean.getBadpost()));
			mTreadCount.setVisibility(View.VISIBLE);
		} else {
			mTreadCount.setVisibility(View.GONE);
		}
	}
}
