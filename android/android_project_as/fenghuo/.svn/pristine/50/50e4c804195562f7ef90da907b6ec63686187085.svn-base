package com.chengning.fenghuo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.adapter.ArticleCommentItemAdapter;
import com.chengning.fenghuo.base.BaseListBean;
import com.chengning.fenghuo.base.BasePageListActivity;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.data.bean.CommentItemBean;
import com.chengning.fenghuo.data.bean.CommentListBean;
import com.chengning.fenghuo.event.CommentSuccessEvent;
import com.chengning.fenghuo.util.ArticleCommentListeners;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.ArticleShareDialog;
import com.chengning.fenghuo.widget.CommentInputDialog;
import com.chengning.fenghuo.widget.DeleteArticleCommentDialog;
import com.chengning.fenghuo.widget.DeleteDynamicDialog;
import com.chengning.fenghuo.widget.PullToRefreshListView_FootLoad;
import com.chengning.fenghuo.widget.RemovePhoneBindDialog;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class TuwenCommentListActivity extends BasePageListActivity{

 	private final static int DATA_SUCCESS = 100;

	private View mView;
	private ImageView mCloseImg;
	private TextView mTitle;
	private ImageButton mLikeBtn;
	private ImageButton mShareBtn;
	private TextView mInputTv;

	private BaseArticlesBean mArticleBean;
	private View mFooter;
	private ArticleCommentItemAdapter mAdapter;

	public static void launch(Activity from, BaseArticlesBean bean) {
		Intent intent = new Intent(from, TuwenCommentListActivity.class);
		intent.putExtra("bean", bean);
		from.startActivity(intent);
	}

	@Override
	public BaseFragmentActivity buildContext() {
		return this;
	}

	@Override
	public String buildUrl() {
		String url = JUrl.SITE + JUrl.URL_GET_ARTICLE_COMMENT;
		url = JUrl.appendTid(url, mArticleBean.getTid());
		return url;
	}

	@Override
	public BaseAdapter buildAdapter(FragmentActivity activity, List list) {
		mAdapter = new ArticleCommentItemAdapter(activity, list);
		return mAdapter;
	}


	@Override
	public String buildMaxId(List list) {
		return ((CommentItemBean)list.get(0)).getTid();
	}

	@Override
	public String configTitle() {
		return "";
	}

	@Override
	public String configNoData() {
		return "暂无评论";
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_article_reply_detail, null);
		return mView;
	}

	@Override
	public void initExtraView() {
		mCloseImg = (ImageView) mView.findViewById(R.id.reply_detail_close);
		mTitle = (TextView) mView.findViewById(R.id.reply_detail_title);
		mLikeBtn = (ImageButton) mView.findViewById(R.id.reply_detail_bottom_toolbar_like);
		mShareBtn = (ImageButton) mView.findViewById(R.id.reply_detail_bottom_toolbar_share);
		mInputTv = (TextView) mView.findViewById(R.id.reply_detail_bottom_toolbar_input);

		handleInputStyle();

		mLikeBtn.setVisibility(View.GONE);
		mShareBtn.setVisibility(View.GONE);
		initFooterView();
	}

	private void handleInputStyle() {
		mInputTv.setText("写评论...");
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mInputTv.getLayoutParams();
		int margin = getResources().getDimensionPixelOffset(R.dimen.common_horizontal_margin);
		layoutParams.rightMargin = margin;
		layoutParams.topMargin = margin;
		layoutParams.bottomMargin = margin;
		mInputTv.setLayoutParams(layoutParams);
	}

	@Override
	public void initExtraData() {
		mArticleBean = (BaseArticlesBean)getIntent().getSerializableExtra("bean");

		getTitleBar().setVisibility(View.GONE);
		getPullListView().setMode(PullToRefreshBase.Mode.DISABLED);
		handleTitle();
		EventBus.getDefault().register(getActivity());
	}

	private void handleTitle() {
		mTitle.setText("评论");
	}

	private void initFooterView() {

		mFooter = LayoutInflater.from(getActivity()).inflate(R.layout.footer_reply_detail,null);
	}

	@Override
	public void initExtraListener() {

		mAdapter.setCommentDeleteListener(new ArticleCommentItemAdapter.ArticleCommentDeleteListener() {
			@Override
			public void deleteSuccess(BaseArticlesBean bean) {
				if (Common.isListEmpty(mAdapter.getList())) {
					getPullListView().getRefreshableView().removeFooterView(mFooter);
				}
			}
		});
		mCloseImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mInputTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != mArticleBean) {
					CommentInputDialog dialog = new CommentInputDialog();
					dialog.setBean(getActivity(), mArticleBean, Consts.ArticleCommentType.ARTICLE);
					dialog.show((getActivity()).getSupportFragmentManager(), CommentInputDialog.class.getSimpleName());

				}
			}
		});

	}

	@Override
	public void handleItemClick(AdapterView parent, View view, int position, long id) {

	}
	@Override
	public BaseListBean<CommentItemBean> handleHttpSuccess(Gson gson,
														   String data) {
		CommentListBean bean = gson.fromJson(data, CommentListBean.class);
		handleData(getActivity(), bean);
		return bean;
	}

	private void handleData(final Activity context, CommentListBean bean) {
		getPullListView().getRefreshableView().removeFooterView(mFooter);
		if (bean != null && !Common.isListEmpty(bean.getList()) && bean.getTotal_page() == 1) {
			getPullListView().getFootView().removeAllViews();
			getPullListView().getRefreshableView().addFooterView(mFooter, null, false);
		}
	}

	@Override
	public void handleNoData() {
		super.handleNoData();
	}

	@Subscribe
	public void onEventMainThread(CommentSuccessEvent event) {
		if (event == null || event.getType() != Consts.ArticleCommentType.ARTICLE) {
			return;
		}
		CommentItemBean bean = event.getmBean();
		List list = mAdapter.getList();
		if (bean != null) {
			if (!Common.isListEmpty(list)) {
				list.add(0, bean);

			} else {
				list.add(bean);
			}
			mAdapter.setList(list);
			mAdapter.notifyDataSetChanged();
			getPullListView().setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(getActivity());
		super.onDestroy();
	}
}
