package com.chengning.fenghuo.widget;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.ad.AdDataManager;
import com.chengning.fenghuo.adapter.ArticleRelativeItemAdapter;
import com.chengning.fenghuo.data.bean.ArticlesBean;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.ChannelItemBean;
import com.chengning.fenghuo.util.Common;

public class ArticleRelativeBottom {
	
	private FragmentActivity mContext;
	
	private View mLayout;
	private LinearLayout mList;
	private View mNoData;

	private String aid;

	private ArticlesBean mArticleBean;

	private BaseArticlesBean mChannelBean;

	private LinearLayout mAdBigLayout;

	private TextView mTitle;

	// 文章页相关文章
	public ArticleRelativeBottom(FragmentActivity activity, final View root){
		this.mContext = activity;
		mLayout = root.findViewById(R.id.article_relative_bottom_layout);
		mAdBigLayout = (LinearLayout) root.findViewById(R.id.article_relative_bottom_ad_big_layout);
		mList = (LinearLayout) root.findViewById(R.id.article_relative_bottom_list);
		mNoData = root.findViewById(R.id.article_relative_bottom_no_data);
		mTitle = (TextView) root.findViewById(R.id.article_relative_bottom_title);
		
		mNoData.setVisibility(View.GONE);

	}
	
	public void setChannelBean(final BaseArticlesBean bean){
		this.mChannelBean = bean;
		this.aid = bean.getTid();
	}
	
	public void setData(ArticlesBean bean){
		this.mArticleBean = bean;
		mArticleBean.setTid(aid);
		ArrayList<ChannelItemBean> dataList = bean.getRelations();
		ChannelItemBean adBig = bean.getAdd_code_big();
		
		boolean isHasRelative = !Common.isListEmpty(dataList) || adBig != null;
		if (isHasRelative ) {
			mLayout.setVisibility(View.VISIBLE);
			// ad
			AdDataManager ad = new AdDataManager(mContext);
			ArrayList<ChannelItemBean> adBigLists = new ArrayList<ChannelItemBean>();		
			handleRelative(mList, dataList, ad,adBigLists.size());
		}else{
			mLayout.setVisibility(View.GONE);
		}
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
					v.findViewById(R.id.item_channel_item_line).setVisibility(View.INVISIBLE);
				}
				layout.addView(v);
			}
		}
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
