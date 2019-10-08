package com.shenyuan.militarynews.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenyuan.militarynews.Const;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.adapter.ArticleRelativeItemAdapter2;
import com.shenyuan.militarynews.beans.data.ArticlesBean;
import com.shenyuan.militarynews.beans.data.MChannelItemBean;
import com.shenyuan.militarynews.utils.Common;

import java.util.ArrayList;

public class ArticleRelativeBottom2 {

	private FragmentActivity mContext;

	private View mLayout;
	private LinearLayout mList;

	private String aid;

	private ArticlesBean mArticleBean;

	private MChannelItemBean mChannelBean;


	// 文章页相关文章
	public ArticleRelativeBottom2(FragmentActivity activity, final View root){
		this.mContext = activity;
		mLayout = root.findViewById(R.id.article_relative_bottom2_layout);
		mList = (LinearLayout) root.findViewById(R.id.article_relative_bottom2_list);

	}

	public void setChannelBean(final MChannelItemBean bean){
		this.mChannelBean = bean;
		this.aid = bean.getAid();
	}

	public void setData(ArticlesBean bean){
		this.mArticleBean = bean;
		mArticleBean.setTid(aid);
		ArrayList<MChannelItemBean> dataList = bean.getTop_relations();

		boolean isHasRelative = !Common.isListEmpty(dataList);
		if (isHasRelative ) {
			mLayout.setVisibility(View.VISIBLE);
			handleRelative(mList, dataList);
		}else{
			mLayout.setVisibility(View.GONE);
		}
	}

	private void handleRelative(LinearLayout layout, ArrayList<MChannelItemBean> dataList) {
		if(!Common.isListEmpty(dataList)){
			layout.removeAllViews();
			ArticleRelativeItemAdapter2 mAdapter = new ArticleRelativeItemAdapter2(mContext, dataList);
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

}
