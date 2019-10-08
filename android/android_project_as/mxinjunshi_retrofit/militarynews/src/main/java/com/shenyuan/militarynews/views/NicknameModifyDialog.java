package com.shenyuan.militarynews.views;

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

import com.chengning.common.base.BaseDialogFragment;
import com.chengning.common.base.BaseResponseBean;
import com.chengning.common.base.MyRetrofitResponseCallback;
import com.chengning.common.base.util.RetrofitManager;
import com.shenyuan.militarynews.App;
import com.shenyuan.militarynews.R;
import com.shenyuan.militarynews.event.NicknameModifyEvent;
import com.shenyuan.militarynews.utils.Common;
import com.shenyuan.militarynews.utils.JUrl;
import com.shenyuan.militarynews.utils.UIHelper;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import io.reactivex.Observable;

public class NicknameModifyDialog extends BaseDialogFragment{
	
	private View mReward;
	private EditText mEditText;
	private Button mBtnClear;
	private Button mBtnCancle;
	private Button mBtnConfirm;
	
	private String mNickname;
	private String mOldNickname;
	
	public NicknameModifyDialog(String mNickname) {
		super();
		Bundle bundle = new Bundle();
		bundle.putString("name", mNickname);
		setArguments(bundle);
	}

	@Override
	public void initView() {
		mEditText = (EditText) mReward.findViewById(R.id.dialog_modify_nickname_edittext);
		mBtnClear= (Button) mReward.findViewById(R.id.dialog_modify_nickname_btn_clear);
		mBtnCancle = (Button) mReward.findViewById(R.id.nickname_btn_cancle);
		mBtnConfirm = (Button) mReward.findViewById(R.id.nickname_btn_confirm);
	}
	
	@Override
	public void initListener() {
		mBtnCancle.setOnClickListener(new OnClickListener() {
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
		
		mBtnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEditText.setText("");
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
				if(TextUtils.isEmpty(mNickname)){
					if(mBtnClear.getVisibility() != View.GONE){
						mBtnClear.setVisibility(View.GONE);
						mBtnConfirm.setEnabled(false);
					}
				}else{
					if(mBtnClear.getVisibility() != View.VISIBLE){
						mBtnClear.setVisibility(View.VISIBLE);
						mBtnConfirm.setEnabled(true);
					}
				}
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
		
		HashMap params = new HashMap<String, String>();
		params.put("uname", name);
		Observable<BaseResponseBean> observable
				= App.getInst().getApiInterface().post(JUrl.URL_EDIT_NICKNAME, params);
		RetrofitManager.subcribe(observable, new MyRetrofitResponseCallback() {

			@Override
			public void onDataSuccess(int status, String mod, String message, String data, BaseResponseBean response) {
				NicknameModifyEvent event = new NicknameModifyEvent();
				event.setNickname(name);
				EventBus.getDefault().post(event);
				mBtnConfirm.setEnabled(true);
				dismissAllowingStateLoss();
			}

			@Override
			public void onDataFailure(int status, String mod, String message, String data, BaseResponseBean response) {
				UIHelper.showToast(getContext(), message);
				mBtnConfirm.setEnabled(true);
			}

			@Override
			public void onError(Throwable t) {
				Common.showHttpFailureToast(getContext());
				mBtnConfirm.setEnabled(true);
				super.onError(t);
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
			mBtnClear.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public View configContentView() {
		mReward = LayoutInflater.from(getContext()).inflate(R.layout.dialog_nickname_modify, null);
		return mReward;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

}
