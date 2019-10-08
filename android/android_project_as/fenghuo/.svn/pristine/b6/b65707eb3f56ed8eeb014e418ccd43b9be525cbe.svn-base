package com.chengning.fenghuo.widget;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.util.BaseUmengShare;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.util.UmengShare;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class PicArticleDialog extends BaseDialogFragment{
	
	private View mView;
	private View mEmpty;
	
	private View mWeixin;
	private View mPyq;
	private View mWeibo;
//	private View mQzone;
	
	private String url;
	private String imgUrl;
	private String title;
	protected BaseArticlesBean mArticleBean;

	public void setBean(BaseArticlesBean mArticleBean){
		Bundle arg = new Bundle();
		arg.putSerializable("articleBean", mArticleBean);
		setArguments(arg);
	}
	public void setData(String url,String imgUrl,String title){
		Bundle arg = new Bundle();
		arg.putString("url",url);
		arg.putString("imgUrl", imgUrl);
		arg.putString("title", title);
		setArguments(arg);
	}
	
	@Override
	public void initView() {
		mEmpty = mView.findViewById(R.id.dialog_pic_article_share_empty);
		mWeixin = mView.findViewById(R.id.dialog_pic_article_share_weixin);
		mPyq = mView.findViewById(R.id.dialog_pic_article_share_pyq);
		mWeibo = mView.findViewById(R.id.dialog_pic_article_share_weibo);
//		mQzone = mView.findViewById(R.id.dialog_pic_article_share_qzone);
	}
	
	@Override
	public void initListener() {
		mEmpty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		mWeixin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(url)){
					UmengShare.getInstance().shareToWeixin(getContext(), url,imgUrl,title);
				}else{
					UmengShare.getInstance().shareToWeixin(getContext(), mArticleBean);
				}
//				dismissAllowingStateLoss();
			}
		});
		
		mPyq.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(url)){
					UmengShare.getInstance().shareToCircle(getContext(), url,imgUrl,title);
				}else{
					UmengShare.getInstance().shareToCircle(getContext(), mArticleBean);
				}
//				dismissAllowingStateLoss();
			}
		});
		
		mWeibo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(url)){
					UmengShare.getInstance().shareToWeibo(getContext(), url, imgUrl,title,null);
				}else{
					UmengShare.getInstance().shareToWeibo(getActivity(), mArticleBean, new BaseUmengShare.BaseUMShareListener(getActivity()) {
						
						@Override
						public void onResult(SHARE_MEDIA share_media) {
							if (App.getInst().isLogin()) {
								UmengShare.getInstance().getPointAndMoneyByWeibo((FragmentActivity)getContext(), mArticleBean);
							}
							super.onResult(share_media);
						}

					});
					
				}
				
				dismissAllowingStateLoss();
			}
		});
		
//		mQzone.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				new UmengShare().shareToQzone(mContext, mArticleBean);
//			}
//		});	
	}
	
	@Override
	public void initData(){
		Bundle arg = getArguments();
		mArticleBean = (BaseArticlesBean) arg.getSerializable("articleBean");
		url = arg.getString("url");
		imgUrl = arg.getString("imgUrl");		
		title = arg.getString("title");
	}
	
	@Override
	public void onPause() {
		dismissAllowingStateLoss();
		super.onPause();
	}
	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pic_article_share, null);
		return mView;
	}
}
