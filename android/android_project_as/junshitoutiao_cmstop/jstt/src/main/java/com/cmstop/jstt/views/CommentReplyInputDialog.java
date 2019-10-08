package com.cmstop.jstt.views;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseFragmentActivity;
import com.chengning.common.util.HttpUtil;
import com.cmstop.jstt.App;
import com.cmstop.jstt.LoginManager;
import com.cmstop.jstt.MyStatusResponseHandler;
import com.cmstop.jstt.Const.PointActionType;
import com.cmstop.jstt.beans.data.CommentItemBean;
import com.cmstop.jstt.utils.Common;
import com.cmstop.jstt.utils.JUrl;
import com.cmstop.jstt.utils.SPHelper;
import com.cmstop.jstt.utils.TaskUpdateUtil;
import com.cmstop.jstt.utils.UIHelper;
import com.loopj.android.http.RequestParams;
import com.cmstop.jstt.R;
import com.umeng.analytics.MobclickAgent;

public class CommentReplyInputDialog extends BaseDialogFragment {
	
	private static final String TAG = CommentReplyInputDialog.class.getSimpleName();
	
	private View mView;
	private View mEmpty;
	private View mCancel;
	private View mPublish;
	private EditText mInput;
	private EditText mName;

	private CommentItemBean bean;
	
	public void setData(CommentItemBean bean){
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		setArguments(args);
	}


	@Override
	public BaseFragmentActivity getContext() {
		return (BaseFragmentActivity)getActivity();
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment_input, null);
		return mView;
	}

	@Override
	public void initView() {
		mEmpty = mView.findViewById(R.id.comment_input_empty);
		mCancel = mView.findViewById(R.id.comment_input_cancel);
		mPublish = mView.findViewById(R.id.comment_input_publish);
		mInput = (EditText) mView.findViewById(R.id.comment_input_input);
		mName = (EditText) mView.findViewById(R.id.comment_input_name);
	}

	@Override
	public void initData() {

		boolean isLogin = App.getInst().isLogin();
		mName.setVisibility(isLogin ? View.GONE : View.VISIBLE);
		mName.setText(isLogin ? 
				LoginManager.getInst().getLoginDbBean().getUserinfo().getUname()
				: SPHelper.getInst().getString(SPHelper.KEY_COMMENT_USERNAME));
		
		bean = (CommentItemBean) getArguments().getSerializable("bean");
		String rName = bean.getUsername();
		if(!TextUtils.isEmpty(rName)){
			int start = 0;
			SpannableStringBuilder buffer = new SpannableStringBuilder();
			// 回复
			buffer.append("回复");
			start = buffer.length();
				buffer.append(rName);
				// TODO(URLSpan("com.chengning.fenghuo.userinfo://"))
				buffer.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.common_blue)), start,
						buffer.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
			buffer.append("...");
			mInput.setHint(buffer);
		}
	}

	@Override
	public void initListener() {
		mEmpty.setOnClickListener(new OnClickListener() {
			
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
		mPublish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Map<String, String> mLoginType = new HashMap<String, String>();
				if (App.getInst().isLogin()) {
					mLoginType.put("usertype", "loginuser");
				} else {
					mLoginType.put("usertype", "anonymous");
				}
				mLoginType.put("article_id", bean.getAid());
				MobclickAgent.onEvent(getContext(), "article_comment", mLoginType);
				mPublish.setEnabled(false);
				publish(bean);
			}
		});
	}
	
	public void publish(CommentItemBean bean){
		String name = mName.getText().toString();
		String input = mInput.getText().toString();
		
		SPHelper.getInst().saveString(SPHelper.KEY_COMMENT_USERNAME, name);
		
		// check input
		if(TextUtils.isEmpty(input) || input.length() < 2){
			UIHelper.showToast(getContext(), "请至少输入两个字符");
			mPublish.setEnabled(true);
		}else{
			if(!Common.hasNet()){
	         	Common.showHttpFailureToast(getContext());
	         	mPublish.setEnabled(true);
	         	return;
			}
			// publish
//			String aid = getArguments().getString("aid");
			boolean isCommentChild = Common.handleStrToInteger(bean.getRid()) > 0 ? true : false;
			String url = JUrl.SITE + JUrl.URL_DO_COMMENT_REPLY_PUBLISH;
			RequestParams params = new RequestParams();
//			params.setContentEncoding("gbk");
//			params.setContentEncoding("utf-8");
//			aid username msg rid
			params.put("aid", bean.getAid());
			params.put("msg", input);
//			params.put("username", name);
//			2016/4/22 10:27:22 @薛全德 评论说明：
//			1、第一层评论。不写rid和toid
			if(isCommentChild){
//				3、某人回复子评论，rid填写被评论的rid，toid为上级评论的id
				params.put("rid", bean.getRid());
				params.put("toid", bean.getId());
			}else{
//				2、第二层评论，rid填上级评论的id，toid空
				params.put("rid", bean.getId());
			}
			
			if (!App.getInst().isLogin()) {
				params.put("username", name);
			}
			HttpUtil.getClient().post(url, params, new MyStatusResponseHandler() {
				
		         public void onFailure(int statusCode, Header[] headers,
			    			Throwable throwable, JSONObject error) {
		            Log.e(TAG, "HttpUtil onFailure");

		         	Common.showHttpFailureToast(getContext());
		         	mPublish.setEnabled(true);
		         };
				
				@Override
				public void onDataSuccess(int status, String mod, String message,
						String data, JSONObject obj) {
					UIHelper.showToast(getContext(), message);
					dismissAllowingStateLoss();
					mPublish.setEnabled(true);
					try{
						TaskUpdateUtil.showHints((FragmentActivity) getContext(), data,PointActionType.REPLY);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onDataFailure(int status, String mod, String message,
						String data, JSONObject obj) {
		             Log.e(TAG, "HttpUtil onDataFailure");
		             
		             UIHelper.showToast(getContext(), message);
		             mPublish.setEnabled(true);
				}
			});
		}
	}
	
}
