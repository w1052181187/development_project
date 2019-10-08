package com.chengning.fenghuo.adapter;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.LoginManager;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.SettingManager;
import com.chengning.fenghuo.data.bean.CommentItemBean;
import com.chengning.fenghuo.util.ArticleCommentListeners;
import com.chengning.fenghuo.util.ArticleCommentListeners.DynamicCommentOnClickListener;
import com.chengning.fenghuo.util.ArticleCommentListeners.DynamicCommentOnLongClickListener;
import com.chengning.fenghuo.util.ArticleManagerUtils;
import com.chengning.fenghuo.util.ArticleManagerUtils.DeleteListener;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.DeleteDynamicDialog;
import com.chengning.fenghuo.widget.DynamicCommentInputDialog.DialogCommentListener;
import com.chengning.fenghuo.widget.DynamicTextView;
import com.chengning.fenghuo.widget.RemovePhoneBindDialog.RemovePhoneBindOkLitener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DynamicCommentItemAdapter extends BasePageListAdapter {
	
	/**
	 * 显示删除对话框
	 * @param bean
	 * @param parent_tid
	 */
	protected void showDeleteDialog(final FragmentActivity context, final CommentItemBean bean, final int is_reply, final String parent_tid) {
		
		if (!LoginManager.getInst().checkLoginWithNotice(context)) {
			 return;
		}
		boolean isCanDelete = bean.getUid().equals(App.getInst().getUserInfoBean().getUid()) || mIsLou ;
		if (isCanDelete){
			DeleteDynamicDialog dialog = new DeleteDynamicDialog();
			dialog.setData(new RemovePhoneBindOkLitener() {

				@Override
				public void confirm() {
					ArticleManagerUtils mArticleManagerUtils = new ArticleManagerUtils();
					mArticleManagerUtils.deleteDynamic(context, bean.getTid(), new DeleteListener() {
						
						@Override
						public void deleteSuccess(String data) {
							if (TextUtils.isEmpty(data)) {
								return;
							}
							Gson gson = new Gson();
							CommentItemBean bean = gson.fromJson(data, CommentItemBean.class);
							ArrayList<CommentItemBean> list = (ArrayList<CommentItemBean>) getList();
							int pos = 0;
							for (CommentItemBean itemBean : list){
								if (bean.getTid().equals(itemBean.getTid())) {
									pos = list.indexOf(itemBean);
									break;
								}
							}
							list.set(pos, bean);
							setList(list);
							notifyDataSetChanged(mIsLou);
						}
					});
				}

				@Override
				public void cancle() {
				}
			});
			dialog.show(context.getSupportFragmentManager(),
					DeleteDynamicDialog.class.getSimpleName());
		}

	}

	public DynamicCommentItemAdapter(Activity activity, List list) {
		super(activity, list);
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_dynamic_comment;
	}

	@Override
	public void handleLayout(View convertView, final int position, Object obj) {
		View mView;
		ImageView commenticon;
		ImageView avatar;
		TextView name;
		DynamicTextView content;
		TextView time;
		TextView comment;
		TextView floor;
		final TextView like;
		ImageView owner;

		final TextView more;
		mView = BaseViewHolder.get(convertView,
				R.id.root);
		commenticon = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_comment_icon);
		avatar = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_user_image);
		name = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_user_name);
		// medal = BaseViewHolder.get(convertView,
		// R.id.item_dynamic_top_user_medal, R.id.item_dynamic_top_user_image);
		content = BaseViewHolder.get(convertView,
				R.id.item_article_comment_content_text);
		time = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_time);
		comment = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_comment);
		floor = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_floor);
		like = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_like);
		owner = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_ower);
		final LinearLayout listView = BaseViewHolder.get(convertView,
				R.id.item_article_comment_content_listview);
		more = BaseViewHolder.get(convertView,
				R.id.item_article_comment_top_more);

		final CommentItemBean commentBean = (CommentItemBean) obj;
		Utils.showFace(commentBean.getFace(), avatar);
		if (Common.isTrue(SettingManager.getInst().getNightModel())) {
			avatar.setColorFilter(
					getContext().getResources().getColor(
							R.color.night_img_color), PorterDuff.Mode.MULTIPLY);
		}
		Common.handleUserNameDisplay(getContext(), commentBean, name);

		SpannableStringBuilder buffer = new SpannableStringBuilder();
		if (Common.isTrue(commentBean.getIs_reply()) && !TextUtils.isEmpty(commentBean.getTousername())) {
			int huifuStart = buffer.length();
			buffer.append("回复 ");
			int huifuend = buffer.length();
			ForegroundColorSpan huifuSpan = new ForegroundColorSpan(getContext()
					.getResources().getColor(R.color.article_time));
			buffer.setSpan(huifuSpan, huifuStart, huifuend,
					SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
			buffer.append(commentBean.getTousername());
			buffer.setSpan(new URLSpan("com.chengning.fenghuo.userinfo://" + commentBean.getTousername()), huifuend,
					buffer.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
			buffer.append(": ");
		}

		String cmtContent = Utils.handleDynamicContentConvert(commentBean);
		buffer.append(cmtContent).append("  ");
		content.setContent(buffer, ImageSpan.ALIGN_BOTTOM);

		commenticon.setVisibility(View.INVISIBLE);
		if(position == 0){
			commenticon.setVisibility(View.VISIBLE);
		}

		time.setText(Common.dateCompareNow(commentBean.getDateline()));

		avatar.setTag(commentBean);
		avatar.setOnClickListener(new ArticleCommentListeners.NameOnClickListener(getContext()));
		mView.setTag(commentBean);
		mView.setOnClickListener(new DynamicCommentOnClickListener(getContext(), new DeleteListener() {

			@Override
			public void deleteSuccess(String data) {
				getList().remove(position);
				notifyDataSetChanged(mIsLou);
			}
		},
		new DialogCommentListener() {
			@Override
			public void onCommentFail() {
			}
			@Override
			public void onCommentSuccess(CommentItemBean iBean) {
				getList().add(iBean);
				notifyDataSetChanged(mIsLou);
			}
		}));
		mView.setOnLongClickListener(new DynamicCommentOnLongClickListener(getContext(),new DeleteListener() {

			@Override
			public void deleteSuccess(String data) {
				getList().remove(position);
				notifyDataSetChanged(mIsLou);
			}
		},cmtContent));
//		comment.setTag(commentBean);
//		comment.setOnClickListener(mCommentListener.getDynamicCommentListener());
		like.setTag(commentBean);
		// like.setEnabled(true);
		// like.setSelected(Common.isTrue(mList.get(position).getIs_dig()));
		like.setText(Common.trimZero(commentBean.getDigcounts()));
		ArticleManagerUtils.DataStateListener likeState = new ArticleManagerUtils.DataStateListener() {

			@Override
			public void success(Object... objects) {
				if (objects == null) {
					return;
				}
				String digCounts = (String) objects[0];
				like.setText(Common.trimZero(digCounts));
				like.setEnabled(true);
			}

			@Override
			public void init() {
				like.setEnabled(false);
			}

			@Override
			public void failure() {
				like.setEnabled(true);
			}
		};
		like.setOnClickListener(new ArticleCommentListeners.LikeOnClickListener(
				getContext(), new ArticleManagerUtils(), likeState)

		);
		// convertView.setTag(bean);
		/**
		 * 是否是楼主
		 */
		owner.setVisibility(Common.isTrue(commentBean.getIs_lou()) ? View.VISIBLE
				: View.GONE);

		if (position == 0) {
			floor.setText("沙发");
		} else {
			floor.setText((position + 1) + "楼");
		}

	}
	

	private boolean mIsLou;


	public void notifyDataSetChanged(boolean isSelfDynamic) {
		this.mIsLou = isSelfDynamic;
		super.notifyDataSetChanged();
	}
	

}
