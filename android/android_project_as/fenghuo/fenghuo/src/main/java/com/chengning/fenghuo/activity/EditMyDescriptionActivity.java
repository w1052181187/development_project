package com.chengning.fenghuo.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chengning.common.base.BaseActivity;
import com.chengning.common.util.HttpUtil;
import com.chengning.fenghuo.App;
import com.chengning.fenghuo.MyJsonHttpResponseHandler;
import com.chengning.fenghuo.R;
import com.chengning.fenghuo.data.bean.UserInfoBean;
import com.chengning.fenghuo.util.Common;
import com.chengning.fenghuo.util.JUrl;
import com.chengning.fenghuo.util.UIHelper;
import com.chengning.fenghuo.widget.TitleBar;
import com.loopj.android.http.RequestParams;

public class EditMyDescriptionActivity extends BaseActivity {
	private static final String TAG = EditMyDescriptionActivity.class
			.getSimpleName();
	private EditText mContentEt;
	private EditMyDescriptionActivity mContext;
	private UserInfoBean mUserInfoBean;
	private TitleBar titleBar;

	public static void create(Activity from) {
		Intent intent = new Intent(from, EditMyDescriptionActivity.class);
		from.startActivityForResult(intent, 113);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Common.setTheme(getActivity());
		setContentView(R.layout.activity_aboutedit);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void processHandlerMessage(Message msg) {

	}

	@Override
	public void initViews() {
		mContentEt = (EditText) this.findViewById(R.id.aboutedit_content);
		mContext = this;
		titleBar = new TitleBar(mContext, true);
		titleBar.setTitle("编辑简介");
		titleBar.showDefaultBack();
		titleBar.setRightButton("完成");

		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.showSoftInput(mContentEt, 0);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	protected void EditAboutMe(final String about) {
		RequestParams params = new RequestParams();
		params.put("aboutme", about);
		HttpUtil.post(mContext, JUrl.SITE + JUrl.URL_EDIT_MYDESCRIPTION, params,
				new MyJsonHttpResponseHandler() {
			
					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) {
						Common.showHttpFailureToast(mContext);
						super.onFailure(statusCode, headers, throwable, errorResponse);
					}
					@Override
					public void onDataSuccess(int status, String mod,
							String message, String data, JSONObject obj) {
						setResultOK();
						mUserInfoBean.setAboutme(about);
						mContext.finish();

					}

					@Override
					public void onDataFailure(int status, String mod,
							String message, String data, JSONObject obj) {
						UIHelper.showToast(mContext, message);
					};
				});
	}

	@Override
	public void initDatas() {
		mUserInfoBean = App.getInst().getUserInfoBean();
		mContentEt.setText(mUserInfoBean.getAboutme());
		mContentEt.selectAll();
	}

	private void setResultOK() {
		Intent intent = new Intent();
		intent.putExtra("about", true);
		setResult(MyprofileActivity.ABOUT_REQUEST_CHANGE, intent);
		mContext.finish();
	}

	@Override
	public void installListeners() {
		titleBar.setRightButtonOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = mContentEt.getText().toString();
				EditAboutMe(content);

			}
		});
	}

	@Override
	public void uninstallListeners() {

	}

	@Override
	public Activity getActivity() {
		return this;
	}

}
