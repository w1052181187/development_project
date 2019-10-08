package com.shenyuan.militarynews.views;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.util.HttpUtil;
import com.loopj.android.http.RequestParams;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.Const.PointActionType;
import com.shenyuan.militarynews.LoginManager;
import com.shenyuan.militarynews.MyStatusResponseHandler;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.SPHelper;
import com.shenyuan.militarynews.utils.TaskUpdateUtil;
import com.shenyuan.militarynews.utils.UIHelper;
import com.umeng.analytics.MobclickAgent;

public class CommentInputDialog extends BaseDialogFragment {

	private static final String TAG = CommentInputDialog.class.getSimpleName();
	
	private View mView;
	private View mEmpty;
	private View mCancel;
	private View mPublish;
	private EditText mInput;
	private EditText mName;
	
	private boolean isPublish;
	private String mStrAid;
	
	public void setAid(String aid){
		Bundle args = new Bundle();
		args.putString("aid", aid);
		setArguments(args);
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
		
		isPublish = false;
		mStrAid = getArguments().getString("aid");
		String content = SPHelper.getInst().getString(mStrAid);
		if(!TextUtils.isEmpty(content)){
			mInput.setText(content);
			mInput.setSelection(content.length());
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
				mLoginType.put("article_id", mStrAid);
				MobclickAgent.onEvent(getContext(), "article_comment", mLoginType);
				mPublish.setEnabled(false);
				publish(mStrAid);
			}
		});
	}
	
	public void publish(String aid){
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
//			String url = JUrl.URL_DO_COMMENT_PUBLISH;
			String url = JUrl.SITE + JUrl.URL_DO_COMMENT_REPLY_PUBLISH;
			RequestParams params = new RequestParams();
//			params.setContentEncoding("gbk");
//			params.setContentEncoding("utf-8");
			params.put("aid", aid);
			params.put("msg", input);
			if (!App.getInst().isLogin()) {
				params.put("username", name);
			}
			
			HttpUtil.getClient().post(url, params, new MyStatusResponseHandler() {
				
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
					try{
						TaskUpdateUtil.showHints((FragmentActivity) getContext(), data,PointActionType.REPLY);
					}catch (Exception e) {
						e.printStackTrace();
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
	public void onDestroy() {
		if(!isPublish){
			String input = mInput.getText().toString();
			if(!TextUtils.isEmpty(input)){
				SPHelper.getInst().saveString(mStrAid, input);
			}
		}
		super.onDestroy();
	}
	
}
