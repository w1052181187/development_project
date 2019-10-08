package com.chengning.fenghuo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.CommentItemBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.Utils;
import com.chengning.fenghuo.widget.DynamicTextView;

public class DynamicCommentItemParentAdapter extends BasePageListAdapter {

	private OnClickListener onClickListener;
	private ArrayList<CommentItemBean> list;

	public DynamicCommentItemParentAdapter(Activity activity,
			ArrayList<CommentItemBean> parentsList) {
		super(activity, parentsList);

	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_dynamic_comment_parent_item;
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {
		CommentItemBean bean = (CommentItemBean) obj;
		bean.setIsReplyClicked(Common.TRUE);
		DynamicTextView content;
		content = (DynamicTextView) BaseViewHolder.get(convertView,
				R.id.item_dynamic_comment_item_content);

		if (!Common.isListEmpty(bean.getContent())) {
			handleContent(bean, content);
		}
		content.setTag(bean);
	}

	private void handleContent(CommentItemBean bean, DynamicTextView content) {
		if (bean == null) {
			return;
		}
		SpannableStringBuilder buffer = new SpannableStringBuilder();
		if (!TextUtils.isEmpty(bean.getNickname())) {
			buffer.append(bean.getNickname());
			int oStart = buffer.length();
			buffer.setSpan(new URLSpan("com.chengning.fenghuo.userinfo://"+ bean.getNickname()), 0,
					oStart, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
			if (Common.isTrue(bean.getIs_lou())) {
				buffer.append("   ");
				Drawable d = getContext().getResources().getDrawable(
						R.drawable.louzhu_icon);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				ImageSpan ownerSpan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
				buffer.setSpan(ownerSpan, oStart + 1, oStart + 2,
						SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
	

		if (Common.isTrue(bean.getIs_reply()) && !TextUtils.isEmpty(bean.getTousername())) {
			int huifuStart = buffer.length();
			buffer.append(" 回复 ");
			int huifuend = buffer.length();

			buffer.append(bean.getTousername());
			buffer.setSpan(new URLSpan("com.chengning.fenghuo.userinfo://" + bean.getTousername()), huifuend,
					buffer.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		buffer.append(": ");

		buffer.append(Utils.handleDynamicContentConvert(bean)).append("  ");

		content.setContent(buffer, ImageSpan.ALIGN_BOTTOM);
	}
}