package com.chengning.fenghuo.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.Consts.ArticleType;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.access.LocalStateDA;
import com.chengning.fenghuo.data.bean.BaseArticlesBean;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.ArticleManagerUtils.CollectState;
import com.chengning.fenghuo.util.ArticleManagerUtils.DeleteListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.ArticleCommentBottom.FavChangeListenner;

public class DynamicSettingDialog extends BaseDialogFragment {

	private View mSetting;

	private View mTop;
	private View mConfirm;

	private TextView mCollect;

	private TextView mReport;

	private BaseArticlesBean mBean;

	private boolean isSelfDynamic;

	private boolean isFav;

	private ArticleManagerUtils mArticleManagerUtils;

	private FavChangeListenner mListener;

	protected boolean isFavBtnClick;

	@Override
	public void initData() {
		mBean = (BaseArticlesBean) getArguments().getSerializable("bean");
		App app = App.getInst();
		isSelfDynamic = app.isLogin()
				&& mBean.getUid().equals(app.getUserInfoBean().getUid());
		if (isSelfDynamic) {
			mReport.setText("删除");
			mReport.setTextColor(getResources().getColor(
					R.color.common_item_tag_text));
		} else {
			mReport.setText("举报");
		}

		mArticleManagerUtils = new ArticleManagerUtils(new CollectState() {

			@Override
			public void setState(boolean state) {
				// mFav.setSelected(state);
				isFav = state;
				mCollect.setText(isFav ? "取消收藏" : "加入收藏");
				// mBean.setIs_favor(state ? 1 : 0);
				if (state && isFavBtnClick) {
					UIHelper.showToast(
							getContext(),
							"圈子"
									+ getContext().getResources().getString(
											R.string.collect_hint));
				}
				mListener.setChange(state);
			}
		});

		if (App.getInst().isLogin()) {
			setCollectState(Common.isTrue(mBean.getIs_favor()));
		} else {
			setCollectState(LocalStateDA.getInst(getContext()).isFavorite(
					LocalStateDA.PREFIX_CHANNEL_ITEM, mBean.getTid()));
		}

	}

	@Override
	public void initView() {
		mTop = mSetting.findViewById(R.id.dynamic_setting_top);
		mCollect = (TextView) mSetting
				.findViewById(R.id.dynamic_setting_collect);
		mReport = (TextView) mSetting.findViewById(R.id.dynamic_setting_report);
		mConfirm = mSetting.findViewById(R.id.dynamic_setting_confirm);
	}

	@Override
	public void initListener() {
		mTop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});

		mConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});

		mCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isFavBtnClick = true;
				if (isFav) {
					mArticleManagerUtils.cancleCollect(getContext(), mBean);
				} else {
					mArticleManagerUtils.doCollect(getContext(), mBean,
							ArticleType.DYNAMIC);
				}
				dismissAllowingStateLoss();
			}
		});

		mReport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleReport();
				dismissAllowingStateLoss();
			}
		});
	}

	/**
	 * 处理举报
	 */
	protected void handleReport() {
		if (isSelfDynamic) {
			mArticleManagerUtils.deleteDynamic(getActivity(), mBean.getTid(),new DeleteListener() {

						@Override
						public void deleteSuccess(String data) {
							getContext().finish();
						}
					});
		} else {
			mArticleManagerUtils.getReport(getActivity(), mBean.getTid());
		}
	}

	public void setData(BaseArticlesBean bean,
			FavChangeListenner mFavChangeListener) {
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		setArguments(args);
		this.mListener = mFavChangeListener;
	}

	public void setCollectState(boolean isFavState) {
		mArticleManagerUtils.setCollectState(isFavState);

	}

	public void shouldRefreshUI() {
		if (mSetting != null) {
			mSetting.postInvalidate();
		}
	}

	@Override
	public View configContentView() {
		mSetting = LayoutInflater.from(getContext()).inflate(
				R.layout.dialog_dynamic_setting, null);
		return mSetting;
	}

	@Override
	public void onDestroy() {
		mArticleManagerUtils.destoryFavHandleThread();
		super.onDestroy();
	}
}
