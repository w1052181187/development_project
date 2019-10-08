package com.chengning.yiqikantoutiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.adapter.ArticleCommentItemAdapter;
import com.chengning.yiqikantoutiao.base.BaseListBean;
import com.chengning.yiqikantoutiao.base.BasePageListActivity;
import com.chengning.yiqikantoutiao.data.bean.ArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.BaseArticlesBean;
import com.chengning.yiqikantoutiao.data.bean.CommentItemBean;
import com.chengning.yiqikantoutiao.data.bean.CommentListBean;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.widget.CommentInputDialog;
import com.google.gson.Gson;

import java.util.List;

/**
 * @deprecated
 */
public class ArticleCommentActivity extends BasePageListActivity<CommentItemBean>{
	
	private View mInput;
	private BaseArticlesBean mArticlesBean;

	private View mView;
	
	public static void launch(Activity from, BaseArticlesBean bean){
		Intent intent = new Intent(from, ArticleCommentActivity.class);
		intent.putExtra("bean", bean);
		from.startActivity(intent);
	}

	@Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    	savedInstanceState.putSerializable("bean", mArticlesBean); 
    }
 
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mArticlesBean = (ArticlesBean) savedInstanceState.getSerializable("bean");
    }

	@Override
	public BaseFragmentActivity buildContext() {
		return ArticleCommentActivity.this;
	}

	@Override
	public String buildUrl() {
		return JUrl.appendTid(JUrl.SITE + JUrl.URL_GET_ARTICLE_COMMENT, mArticlesBean.getTid());
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		return new ArticleCommentItemAdapter(activity, list);
	}

	@Override
	public String buildMaxId(List list) {
		return ((CommentItemBean)list.get(0)).getTid();
	}

	@Override
	public String configTitle() {
		mArticlesBean = (BaseArticlesBean) getIntent().getSerializableExtra("bean");
		
		StringBuffer mTitleStr = new StringBuffer();
		mTitleStr.append("全部评论(").append(mArticlesBean.getReplys()).append(")");
		return mTitleStr.toString();
	}

	@Override
	public String configNoData() {
		return "暂时还没有评论";
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_article_comment, null, false);
		return mView;
	}

	@Override
	public void initExtraView() {
		mInput = mView.findViewById(R.id.article_comment_input);

	}

	@Override
	public void initExtraData() {

	}

	@Override
	public void initExtraListener() {

		mInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (LoginManager.getInst().checkLoginWithNotice(getActivity())) {
					CommentInputDialog dialog = new CommentInputDialog();
					dialog.setBean(getActivity(), mArticlesBean, null);
					dialog.show(getActivity().getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());
			
				}
//			}
		});

	}

	@Override
	public void handleItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
		View v = view.findViewById(R.id.item_article_comment_top_user_image);
		BaseArticlesBean bean = (BaseArticlesBean) v.getTag();
		ArticleReplyDetailActivity.launch(getActivity(), bean);
	}

	@Override
	public BaseListBean<CommentItemBean> handleHttpSuccess(Gson gson,
			String data) {
		CommentListBean bean = gson.fromJson(data, CommentListBean.class);
		return bean;
	}

}
