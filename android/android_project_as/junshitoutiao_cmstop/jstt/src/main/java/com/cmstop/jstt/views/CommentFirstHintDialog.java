package com.cmstop.jstt.views;

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
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.cmstop.jstt.MyStatusResponseHandler;
import com.cmstop.jstt.Const.LikeAction;
import com.cmstop.jstt.beans.data.CommentItemBean;
import com.cmstop.jstt.utils.ArticleManagerUtils;
import com.cmstop.jstt.utils.CommentCheckUtil;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.UIHelper;
import com.cmstop.jstt.utils.ArticleManagerUtils.LikeState;
import com.loopj.android.http.RequestParams;
import com.cmstop.jstt.R;

public class CommentFirstHintDialog extends BaseDialogFragment {
	
	private static final String TAG = CommentFirstHintDialog.class.getSimpleName();
	
	private View mView;
	private View mCancel;


	@Override
	public BaseFragmentActivity getContext() {
		return (BaseFragmentActivity)getActivity();
	}

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
