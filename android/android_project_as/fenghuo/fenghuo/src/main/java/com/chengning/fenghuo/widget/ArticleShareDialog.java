package com.chengning.fenghuo.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.util.BaseUmengShare.BaseUMShareListener;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.util.UmengShare;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class ArticleShareDialog extends BaseDialogFragment {
	
	private View mSetting;

	private View mTop;
	private View mShareWeixin;
	private View mSharePyq;
	private View mShareWeibo;
	private View mShareQQ;
	private View mCancel;
	
	private BaseArticlesBean mArticleBean;

	public void setBean(BaseArticlesBean bean){
		Bundle arg = new Bundle();
		arg.putSerializable("bean", bean);
		setArguments(arg);
	}

	@Override
	public View configContentView() {
		mSetting = LayoutInflater.from(getContext()).inflate(R.layout.dialog_article_share, null);
		return mSetting;
	}

	@Override
	public void initListener() {
		mTop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		// share
		mShareWeixin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					UmengShare.getInstance().shareToWeixin(getActivity(),
							mArticleBean);
				}
			}
		});
		mSharePyq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					UmengShare.getInstance().shareToCircle(getActivity(),
							mArticleBean);
				}
			}
		});
		mShareWeibo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					UmengShare.getInstance().shareToWeibo(getActivity(), mArticleBean, 
							new BaseUMShareListener(getActivity()) {
					    @Override
					    public void onResult(SHARE_MEDIA platform) {
					    	if (App.getInst().isLogin()) {
								UmengShare.getInstance()
										.getPointAndMoneyByWeibo(
												getActivity(), mArticleBean);
							}
					    	super.onResult(platform);
					    }

					});
				}
			}
		});
		mShareQQ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					UmengShare.getInstance().shareToQq(getActivity(),
							mArticleBean);
				}
			}
		});
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
	}

	@Override
	public void initView() {
		mTop = mSetting.findViewById(R.id.article_more_top);
		mShareWeixin = mSetting.findViewById(R.id.article_more_share_weixin);
		mSharePyq = mSetting.findViewById(R.id.article_more_share_pyq);
		mShareWeibo = mSetting.findViewById(R.id.article_more_share_weibo);
		mShareQQ = mSetting.findViewById(R.id.article_more_share_qq);
		mCancel = mSetting.findViewById(R.id.article_more_cancel);
	}

	@Override
	public void initData() {
		Bundle arg = getArguments();
		mArticleBean = (BaseArticlesBean) arg.getSerializable("bean");
	}
	
	public void shouldRefreshUI(){
		if(mSetting != null){
			mSetting.postInvalidate();
		}
	}
	
}
