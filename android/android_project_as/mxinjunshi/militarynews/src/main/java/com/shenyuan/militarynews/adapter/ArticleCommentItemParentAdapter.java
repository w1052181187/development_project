package com.shenyuan.militarynews.adapter;

import java.util.List;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.beans.data.CommentItemBean;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.CommentItemOnClickListener;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.views.EmojiTextView;

public class ArticleCommentItemParentAdapter extends BasePageListAdapter {
	
	private String mAid;

	public ArticleCommentItemParentAdapter(Activity activity, List list, String aid) {
		super(activity, list);
		this.mAid = aid;
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_article_comment_parent;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		TextView name = BaseViewHolder.get(convertView, R.id.article_comment_item_parent_name);
		TextView time = BaseViewHolder.get(convertView, R.id.article_comment_item_parent_time);
		TextView floor = BaseViewHolder.get(convertView, R.id.article_comment_item_parent_floor);
		EmojiTextView content = BaseViewHolder.get(convertView, R.id.article_comment_item_parent_content);
		View line = BaseViewHolder.get(convertView, R.id.article_comment_item_parent_line);
		
		CommentItemBean bean = (CommentItemBean) obj;
		String username = bean.getUsername();
		String username2 = (TextUtils.isEmpty(username) ? "新军事网友" : username);
		time.setText(Common.getDateMMDDHHMMNotNull(bean.getDtime()));
		floor.setText(bean.getFloor());
		floor.setPadding(0, 0, 
				(TextUtils.isEmpty(bean.getFloor()) ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.common_horizontal_margin)),
				0);
		content.setText(bean.getMsg());
		line.setVisibility((position == getCount() - 1) ? View.GONE : View.VISIBLE);

		int start = 0;
		int end = 0;
		SpannableStringBuilder buffer = new SpannableStringBuilder();
		// username
		buffer.append(username2);
		end = buffer.length();
		// TODO(URLSpan("com.chengning.fenghuo.userinfo://"))
		ForegroundColorSpan userSpan = new ForegroundColorSpan(getContext()
				.getResources().getColor(R.color.article_comment_item_name_color));
		buffer.setSpan(userSpan, start, end,
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 回复
		if (!TextUtils.isEmpty(bean.getReply())) {
			buffer.append("回复 ");
			int huifuStart = buffer.length();
			buffer.append(bean.getReply());
			
			buffer.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.article_comment_item_name_color)), huifuStart,
					buffer.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		}else{
//			buffer.append(" 回复");
		}
		buffer.append(" :");
		name.setText(buffer);
		name.setTag(bean);
		convertView.setOnClickListener(new CommentItemOnClickListener(getContext(), bean, null));
	}

}
