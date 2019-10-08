package com.shenyuan.militarynews.views;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.GetReverse;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.ad.AdDataManager;
import com.shenyuan.militarynews.adapter.ArticleRelativeItemAdapter;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.Common;

public class ArticleAdBottom {
	
	private Activity mContext;
	
	private View mLayout;
	private LinearLayout mList;

	private String aid;

	private ArticlesBean mArticleBean;

	private MChannelItemBean mChannelBean;

	private LinearLayout mAdBigLayout;

	private TextView mTitle;
	
	// 文章页精彩推荐
	public ArticleAdBottom(Activity activity, final View root){
		this.mContext = activity;
		mLayout = root.findViewById(R.id.article_ad_bottom_layout);
		mAdBigLayout = (LinearLayout) root.findViewById(R.id.article_ad_bottom_ad_big_layout);
		
	}
	
	public void setChannelBean(final MChannelItemBean bean){
		this.mChannelBean = bean;
		this.aid = bean.getAid();
	}
	
	public void setData(ArticlesBean bean){
		this.mArticleBean = bean;
		mArticleBean.setTid(aid);
		MChannelItemBean adBig = bean.getAdd_code_big();
		
		boolean isHasRelative = (adBig != null) && (!TextUtils.isEmpty(adBig.getAid()));
		if (isHasRelative ) {
			mLayout.setVisibility(View.VISIBLE);
			// ad
			AdDataManager ad = new AdDataManager(mContext);
			ArrayList<MChannelItemBean> adBigLists = new ArrayList<MChannelItemBean>();
			if(adBig != null){
				adBigLists.add(adBig);
				handleRelative(mAdBigLayout,adBigLists, ad);
			}
		}else{
			mLayout.setVisibility(View.GONE);
		}
	}
	
	private void handleRelative(LinearLayout layout,
			ArrayList<MChannelItemBean> dataList, AdDataManager ad){
		handleRelative(layout, dataList, ad, 0);
	}
	
	private void handleRelative(LinearLayout layout,
			ArrayList<MChannelItemBean> dataList, AdDataManager ad, int adOffset) {
		if(!Common.isListEmpty(dataList)){
			
			LayoutParams lp = layout.getLayoutParams();
			lp.width = LayoutParams.MATCH_PARENT;
			lp.height = LayoutParams.WRAP_CONTENT;
			layout.setLayoutParams(lp);
			
			layout.removeAllViews();
			ArticleRelativeItemAdapter mAdapter = new ArticleRelativeItemAdapter(mContext, dataList, ad, adOffset);
			int size = mAdapter.getCount();
			for(int i = 0; i < size; i++){
				View v = mAdapter.getView(i, null, null);
				if(i == size - 1){
					v.findViewById(R.id.item_channel_item_line).setVisibility(View.INVISIBLE);
				}
				layout.addView(v);
			}
		}
	}
	
	public void show(boolean isShowed) {
		mLayout.setVisibility(isShowed ? View.VISIBLE : View.GONE);
	}
	
	public void setTitle(String title){
		mTitle.setText(title);
	}
	
	/**
	 * 
	 * @param titleId R.String.xxx
	 */
	public void setTitle(int titleId){
		mTitle.setText(mContext.getString(titleId));
	}

}
