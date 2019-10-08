package com.chengning.yiqikantoutiao.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.View;

import com.chengning.common.base.BasePageListAdapter;
import com.chengning.common.base.BaseViewHolder;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.data.bean.CommentItemBean;
import com.chengning.yiqikantoutiao.util.Common;
import com.chengning.yiqikantoutiao.util.Utils;
import com.chengning.yiqikantoutiao.widget.DynamicTextView;

import java.util.ArrayList;

public class ArticleCommentItemParentAdapter extends BasePageListAdapter {

	public ArticleCommentItemParentAdapter(Activity activity,
			ArrayList<CommentItemBean> parentsList) {
		super(activity, parentsList);
		
	}

	@Override
	public int buildLayoutId() {
		return R.layout.item_article_comment_parent_item;
	}
	
	@Override
	public int getCount() {
		int size = super.getCount();
		if (3 < size) {
			return 3;
		} else {
			return size;
		}
	}

	@Override
	public void handleLayout(View convertView, int position, Object obj) {

		DynamicTextView content;

		content = (DynamicTextView) BaseViewHolder.get(convertView, R.id.item_article_comment_item_content);
	
		CommentItemBean bean = (CommentItemBean) obj;
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
			buffer.setSpan(new URLSpan("com.chengning.yiqikantoutiao.userinfo://"+ bean.getNickname()), 0,
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

		buffer.append(": ");

		buffer.append(Utils.handleDynamicContentConvert(bean)).append("  ");

		content.setContent(buffer, ImageSpan.ALIGN_BOTTOM);
	}
}
