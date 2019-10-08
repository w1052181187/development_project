package com.chengning.fenghuovideo.widget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuovideo.Consts;
import com.chengning.fenghuovideo.MyJsonHttpResponseHandler;
import com.chengning.fenghuovideo.R;
import com.chengning.fenghuovideo.data.bean.BaseArticlesBean;
import com.chengning.fenghuovideo.data.bean.CommentSuccessBean;
import com.chengning.fenghuovideo.event.CommentSuccessEvent;
import com.chengning.fenghuovideo.util.Common;
import com.chengning.fenghuovideo.util.JUrl;
import com.chengning.fenghuovideo.util.SPHelper;
import com.chengning.fenghuovideo.util.UIHelper;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class CommentInputDialog extends BaseDialogFragment {

	private View mView;
	private View mEmpty;
	private View mCancel;
	private View mPublish;
	private EditText mInput;
	private EditText mName;

	private String mRootTid;
	
	private boolean isPublish;
	private String mStrAid;
	private Consts.ArticleCommentType mType;
	private boolean isThreeReply = false;

	public void setBean(BaseArticlesBean bean){
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		setArguments(args);
	}

	public void setBean(BaseArticlesBean bean, Consts.ArticleCommentType type){
		Bundle args = new Bundle();
		args.putSerializable("bean", bean);
		args.putSerializable("comment_type", type);
		setArguments(args);
	}
	
	public void publish(){
		String name = mName.getText().toString();
		String input = mInput.getText().toString();
		// check input
		if(TextUtils.isEmpty(input) || input.length() < 2){
			UIHelper.showToast(getContext(), "请至少输入两个字符");
			mPublish.setEnabled(true);
		}else{
			// publish
			if (!Common.hasNet()) {
	         	Common.showHttpFailureToast(getContext());
	         	mPublish.setEnabled(true);
	         	return;
			}
			BaseArticlesBean bean = (BaseArticlesBean) getArguments().getSerializable("bean") ;
			
			String url = JUrl.SITE + JUrl.URL_DO_COMMENT_PUBLISH;
//			url = App.getInst().isLogin() ? url + JUrl.URL_DO_COMMENT_PUBLISH : url + JUrl.URL_DO_ANON_COMMENT_PUBLISH;
			RequestParams params = new RequestParams();
			params.put("content", input);
			params.put("topictype", "reply"); 
			params.put("totid", bean.getTid());

			params.put("roottid", bean.getTid());
			if (isThreeReply) {
				params.put("roottid", bean.getTotid());
			}

			params.put("touid", bean.getUid()); 
			params.put("from", bean.getFrom());
			HttpUtil.post(url, params, new MyJsonHttpResponseHandler() {
				
				public void onFailure(int statusCode, Header[] headers,
		    			Throwable throwable, JSONObject error) {
	
		         	Common.showHttpFailureToast(getContext());
		         	mPublish.setEnabled(true);
				};
				
				@Override
				public void onDataSuccess(int status, String mod, String message,
						String data, JSONObject obj) {
					isPublish = true;
					SPHelper.getInst().removeByKey(mStrAid);
					UIHelper.showToast(getContext(), message);
					dismissAllowingStateLoss();
					mPublish.setEnabled(true);
					Gson gson = new Gson();
					CommentSuccessBean bean = gson.fromJson(data, CommentSuccessBean.class);
					if (bean != null && mType != null) {
						EventBus.getDefault().post(new CommentSuccessEvent(bean.getList(), mType));
					}

				}
				
				@Override
				public void onDataFailure(int status, String mod, String message,
						String data, JSONObject obj) {
		             
		             UIHelper.showToast(getContext(), message);
		             mPublish.setEnabled(true);
				}
			}); 
		}
	}

	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment_input, null);
		return mView;
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
				mPublish.setEnabled(false);
				publish();
			}
		});
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
		isPublish = false;
		
		BaseArticlesBean bean = (BaseArticlesBean) getArguments().getSerializable("bean") ;
		mStrAid = bean.getTid();
		mType = (Consts.ArticleCommentType) getArguments().getSerializable("comment_type");
		String content = SPHelper.getInst().getString(mStrAid);
		if(!TextUtils.isEmpty(content)){
			mInput.setText(content);
			mInput.setSelection(content.length());
		}
		if (isThreeReply && bean != null && !TextUtils.isEmpty(bean.getNickname())) {
			mInput.setHint("回复 " + bean.getNickname() + ":");
		}
	}
	
	
	@Override
	public void onDestroy() {
		if(!isPublish){
			String input = mInput.getText().toString();
			if(!TextUtils.isEmpty(input)){
				SPHelper.getInst().saveString(mStrAid, input);
			}
		}
		super.onDestroy();
	}

	public void setIsThreeReply(boolean isThreeReply) {
		this.isThreeReply = isThreeReply;
	}
}
