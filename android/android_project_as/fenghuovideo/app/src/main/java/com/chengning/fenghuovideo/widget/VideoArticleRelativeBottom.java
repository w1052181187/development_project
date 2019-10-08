package com.chengning.fenghuovideo.widget;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.activity.VideoDetailActivity;
import com.chengning.fenghuovideo.adapter.ChannelItemRecommendAdapter;
import com.chengning.fenghuovideo.base.AbstractChannelItemListFragment;
import com.chengning.fenghuovideo.data.bean.ArticlesBean;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.ChannelItemBean;
import com.chengning.fenghuovideo.nicevideo.FhItemData;
import com.chengning.fenghuovideo.util.Common;

import java.util.ArrayList;

public class VideoArticleRelativeBottom {
	
	private FragmentActivity mContext;
	
	private View mLayout;
	private LinearLayout mList;
	private View mNoData;

	private String aid;

	private ArticlesBean mArticleBean;

//	private AdDataManager ad;

	private BaseArticlesBean mChannelBean;

	private LinearLayout mAdBigLayout;

	private View mMore;
	
	private static final int COUNT_RELATIVE_SHOW = 8;

	// 文章页相关文章
	public VideoArticleRelativeBottom(FragmentActivity activity, final View root){
		this.mContext = activity;
		mLayout = root.findViewById(R.id.article_relative_bottom_layout);
		mList = (LinearLayout) root.findViewById(R.id.article_relative_bottom_list);
		mNoData = root.findViewById(R.id.article_relative_bottom_no_data);
		mMore = root.findViewById(R.id.article_relative_bottom_more);
		
		mNoData.setVisibility(View.GONE);

		mMore.setVisibility(View.GONE);
		mMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMore();
			}
		});
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
//
		boolean isHasRelative = !Common.isListEmpty(dataList) || adBig != null;
		if (isHasRelative ) {
			mLayout.setVisibility(View.VISIBLE);
			// ad
//			ad = new AdDataManager(mContext);
			ArrayList<ChannelItemBean> adBigLists = new ArrayList<ChannelItemBean>();		
			handleRelative(mList, dataList, adBigLists.size(), COUNT_RELATIVE_SHOW);
			if(dataList.size() <= COUNT_RELATIVE_SHOW){
				mMore.setVisibility(View.GONE);
			}else{
				mMore.setVisibility(View.VISIBLE);
			}
		}else{
			mLayout.setVisibility(View.GONE);
		}
	}
	
	private void handleRelative(LinearLayout layout,
								final ArrayList<ChannelItemBean> dataList, int adOffset, int limit) {
		if(!Common.isListEmpty(dataList)){
			
			//			mNoData.setVisibility(View.INVISIBLE);
			LayoutParams lp = layout.getLayoutParams();
			lp.width = LayoutParams.MATCH_PARENT;
			lp.height = LayoutParams.WRAP_CONTENT;
			layout.setLayoutParams(lp);
			
			if(adOffset == 0){
				layout.removeAllViews();
			}
			ChannelItemRecommendAdapter mAdapter = new ChannelItemRecommendAdapter(mContext, dataList, "", layout);
			mAdapter.setIsReadEnable(false);
			int size = mAdapter.getCount();
			int end = limit < size ? limit : size;
			View v;
			View divider;
			for(int i = adOffset; i < end; i++){
				final ChannelItemBean bean = (ChannelItemBean) mAdapter.getItem(i);
				v = mAdapter.getView(i, null, null);
//				if((i == size - 1) && (dataList.get(size - 1).getShow_type() == Consts.SHOW_TYPE_ONE_BIG_AD)){
//					v.findViewById(R.id.item_channel_item_line).setVisibility(View.INVISIBLE);
//				}
				if (v == null) {
					continue;
				}
				divider = v.findViewById(R.id.item_channel_item_divider);
				if (divider != null) {
					divider.setVisibility(View.INVISIBLE);
				}

				v.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						FhItemData itemData = new FhItemData(null, 0, bean);
						AbstractChannelItemListFragment.handleVodeoDetailItemClick(mContext, v, itemData);
					}
				});
				layout.addView(v);
			}
		}
	}
	
	private void loadMore(){
		if(mArticleBean == null || mArticleBean.getRelations() == null){
			return;
		}
		int childCount = mList.getChildCount();
		int relativeCount = mArticleBean.getRelations().size();
		if(childCount != 0 && childCount < relativeCount){
			handleRelative(mList, mArticleBean.getRelations(), childCount, relativeCount);
		}
		mMore.setVisibility(View.GONE);
	}
	
	public void setTitle(String title){
//		mTitle.setText(title);
	}
	
	/**
	 * 
	 * @param titleId R.String.xxx
	 */
	public void setTitle(int titleId){
//		mTitle.setText(mContext.getString(titleId));
	}

}
