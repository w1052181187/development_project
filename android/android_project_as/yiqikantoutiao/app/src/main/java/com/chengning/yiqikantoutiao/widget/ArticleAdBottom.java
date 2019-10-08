package com.chengning.yiqikantoutiao.widget;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.chengning.yiqikantoutiao.Consts;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.activity.ArticleActivity;
import com.chengning.yiqikantoutiao.ad.AdDataManager;
import com.chengning.yiqikantoutiao.adapter.ArticleRelativeItemAdapter;
import com.chengning.yiqikantoutiao.data.bean.ArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.BaseArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.ChannelItemBean;
import com.chengning.yiqikantoutiao.util.Common;

import java.util.ArrayList;

public class ArticleAdBottom {
	
	private ArticleActivity mContext;
	
	private View mLayout;
	private LinearLayout mList;
	private View mNoData;

	private String aid;

	private ArticlesBean mArticleBean;

	private BaseArticlesBean mChannelBean;

	private LinearLayout mAdBigLayout;

	// 文章页相关推荐
	public ArticleAdBottom(ArticleActivity activity, final View root){
		this.mContext = activity;
		mLayout = root.findViewById(R.id.article_ad_bottom_layout);
		mAdBigLayout = (LinearLayout) root.findViewById(R.id.article_ad_bottom_ad_big_layout);
	}
	
	public void setChannelBean(final BaseArticlesBean bean){
		this.mChannelBean = bean;
		this.aid = bean.getTid();
	}
	
	public void setData(ArticlesBean bean){
		this.mArticleBean = bean;
		mArticleBean.setTid(aid);
		ChannelItemBean adBig = bean.getAdd_code_big();
		
		boolean isHasRelative = (adBig != null) && (!TextUtils.isEmpty(adBig.getTid()));
		if (isHasRelative ) {
			mLayout.setVisibility(View.VISIBLE);
			// ad
			AdDataManager ad = new AdDataManager(mContext);
			ArrayList<ChannelItemBean> adBigLists = new ArrayList<ChannelItemBean>();
			if (adBig != null) {
				adBigLists.add(adBig);
				handleRelative(mAdBigLayout,adBigLists, ad);
			}
		}else{
			mLayout.setVisibility(View.GONE);
		}
	}

	private void handleRelative(LinearLayout layout,
                                ArrayList<ChannelItemBean> dataList, AdDataManager ad){
		handleRelative(layout, dataList, ad, 0);
	}
	
	private void handleRelative(LinearLayout layout,
                                ArrayList<ChannelItemBean> dataList, AdDataManager ad, int adOffset) {
		if(!Common.isListEmpty(dataList)){
			
			//			mNoData.setVisibility(View.INVISIBLE);
			LayoutParams lp = layout.getLayoutParams();
			lp.width = LayoutParams.MATCH_PARENT;
			lp.height = LayoutParams.WRAP_CONTENT;
			layout.setLayoutParams(lp);
			
			layout.removeAllViews();
			ArticleRelativeItemAdapter mAdapter = new ArticleRelativeItemAdapter(mContext, dataList, ad, adOffset);
			int size = mAdapter.getCount();
			for(int i = 0; i < size; i++){
				View v = mAdapter.getView(i, null, null);
				if((i == size - 1) && (dataList.get(size - 1).getShow_type() == Consts.SHOW_TYPE_ONE_BIG_AD)){
					View line = v.findViewById(R.id.item_channel_item_line);
					if(line != null){
						line.setVisibility(View.INVISIBLE);
					}
				}
				layout.addView(v);
			}
		}
	}

}
