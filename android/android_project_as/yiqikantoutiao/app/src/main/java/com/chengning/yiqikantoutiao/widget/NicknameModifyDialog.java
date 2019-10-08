package com.chengning.yiqikantoutiao.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.util.HttpUtil;
import com.chengning.yiqikantoutiao.MyJsonHttpResponseHandler;
import com.chengning.yiqikantoutiao.R;
import com.chengning.yiqikantoutiao.event.NicknameModifyEvent;
import com.chengning.yiqikantoutiao.util.JUrl;
import com.chengning.yiqikantoutiao.util.UIHelper;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;


public class NicknameModifyDialog extends BaseDialogFragment{
	
	private View mView;
	private View mEmptyView;
	private EditText mEditText;
	private TextView mCountTxt;
	private Button mBtnConfirm;
	
	private String mNickname;
	private String mOldNickname;

	public void setData(String mNickname) {
		Bundle bundle = new Bundle();
		bundle.putString("name", mNickname);
		setArguments(bundle);
	}

	@Override
	public void initView() {
		mEmptyView = mView.findViewById(R.id.nickname_modify_empty);
		mEditText = (EditText) mView.findViewById(R.id.dialog_modify_nickname_edittext);
		mCountTxt = (TextView) mView.findViewById(R.id.nickname_count_tv);
		mBtnConfirm = (Button) mView.findViewById(R.id.nickname_btn_confirm);
	}
	
	@Override
	public void initListener() {

		mEmptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismissAllowingStateLoss();
			}
		});
		
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				handleNickname(mNickname);
			}
		});

		
		mEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
				
			}
			@Override
			public void afterTextChanged(Editable s) {
				mNickname = mEditText.getText().toString().trim();
				mBtnConfirm.setEnabled(TextUtils.isEmpty(mNickname) ? false : true);
				mCountTxt.setText(String.valueOf(15 - mNickname.length()));

			}
		});

	}

	/**
	 * 修改昵称
	 * @param name
	 */
	protected void handleNickname(final String name) {
		if (TextUtils.equals(mNickname, mOldNickname)) {
			UIHelper.showToast(getContext(), "新昵称跟旧的相同，请重新输入");
			return;
		}
		mBtnConfirm.setEnabled(false);
		
		RequestParams params = new RequestParams();
		params.put("nickname", name);
		HttpUtil.post(JUrl.SITE + JUrl.URL_EDIT_NICKNAME, params, new MyJsonHttpResponseHandler() {
			@Override
			public void onDataSuccess(int status, String mod, String message,
                                      String data, JSONObject obj) {
				NicknameModifyEvent event = new NicknameModifyEvent();
				event.setNickname(name);
				EventBus.getDefault().post(event);
				mBtnConfirm.setEnabled(true);
				dismissAllowingStateLoss();
			}
			
			@Override
			public void onDataFailure(int status, String mod, String message,
                                      String data, JSONObject obj) {
				UIHelper.showToast(getContext(), message);
				mBtnConfirm.setEnabled(true);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
                                  String responseString, Throwable throwable) {
				mBtnConfirm.setEnabled(true);
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		
	}

	@Override
	public void initData() {
		mNickname = getArguments().getString("name");
		mOldNickname = mNickname;
		if (!TextUtils.isEmpty(mOldNickname)) {
			mEditText.setText(mOldNickname);
			mEditText.setSelection(mOldNickname.length());
			mCountTxt.setText(String.valueOf(15 - mOldNickname.length()));
		}
	}

	
	@Override
	public View configContentView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nickname_modify, null);
		return mView;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

}
