package com.shenyuan.militarynews.views;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.avos.avoscloud.AVInstallation;
import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.RequestParams;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.Const.LikeAction;
import com.shenyuan.militarynews.beans.data.CommentItemBean;
import com.shenyuan.militarynews.utils.ArticleManagerUtils;
import com.shenyuan.militarynews.utils.CommentCheckUtil;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UIHelper;
import com.shenyuan.militarynews.utils.ArticleManagerUtils.LikeState;

public class CommentFirstHintDialog extends BaseDialogFragment {
	
	private static final String TAG = CommentFirstHintDialog.class.getSimpleName();
	
	private View mView;
	private View mCancel;
	

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment_first_hint, null);
		return mView;
	}

	@Override
	public void initView() {
		mCancel = mView.findViewById(R.id.dialog_comment_hint_click_btn);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {
		mView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
    }
	
}
